package com.my.seckill.service;

import com.google.gson.Gson;
import com.my.seckill.Entity.SeckillResult;
import com.my.seckill.Entity.SeckillUrlInfo;
import com.my.seckill.dao.SeckillGoodsDao;
import com.my.seckill.dto.SeckillGoodsDTO;
import com.my.seckill.util.ConfigUtils;
import com.my.seckill.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by tufei on 2018/9/2.
 */
@Service
public class SeckillService {

    private static final int SIZE = 10;

    private static AtomicBoolean finished = new AtomicBoolean(false);

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SeckillDetailService seckillDetailService;

    public List<SeckillGoodsDTO> getSeckillGoodsList() {
        return seckillGoodsDao.queryAll(0,SIZE);//可以做成分页展示
    }

    public SeckillGoodsDTO getSeckillGoodsById(Long id) {
        //首先从缓存中获取,注意不要使用库存字段
        String seckill = (String)redisService.getSeckillCacheKey(Constants.SECKILL_GOODS_CACHE_PREFIX+id);
        if(!StringUtils.isEmpty(seckill)){
            return new Gson().fromJson(seckill,SeckillGoodsDTO.class);
        }
        //结点启动时已经将商品信息加载到redis中，如果没有获取到，说明已经超过endTime了
        //如果初始化后有新增的秒杀商品，可以使用定时任务将商品信息放入redis
        return null;

        //SeckillGoodsDTO seckillGoodsDTO = seckillGoodsDao.queryById(id);
    }

    public String generateSeckillUrl(Long goodsId) {
        String md5 = getMD5(goodsId);
        String url = "/seckill/"+md5+"/execute";
        return url;

    }

    public String getMD5(long goodsId) {
        String base = goodsId + "-" + ConfigUtils.getKey(Constants.MD5_ENCRYPT_KEY);
        Calendar now = Calendar.getInstance();
        DecimalFormat _2 = new DecimalFormat("00");
        String _yy = (now.get(Calendar.YEAR) + "").substring(2);
        String yy = _2.format(Integer.valueOf(_yy));
        String _mm = now.get(Calendar.MONTH) + 1 + "";
        String mm = _2.format(Integer.valueOf(_mm));
        String _dd = now.get(Calendar.DAY_OF_MONTH) + "";
        String dd = _2.format(Integer.valueOf(_dd));
        String _hh = now.get(Calendar.HOUR_OF_DAY)+"";
        String hh = _2.format(Integer.valueOf(_hh));
        String _mi = now.get(Calendar.MINUTE)+"";
        String mi = _2.format(Integer.valueOf(_mi));
        String _ss = now.get(Calendar.SECOND)+"";
        String ss = _2.format(Integer.valueOf(_ss));//1s变化一次url,可调整
        base = base+yy+mm+dd+hh+mi;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    public SeckillUrlInfo getSeckillUrlInfo(Long goodsId) {
        long currentTime = new Date().getTime();
        String str = (String)redisService.getSeckillCacheKey(Constants.SECKILL_GOODS_CACHE_PREFIX+goodsId);
        SeckillGoodsDTO seckillGoodsDTO = new Gson().fromJson(str,SeckillGoodsDTO.class);
        long startTime = seckillGoodsDTO.getStartTime().getTime();
        long endTime = seckillGoodsDTO.getEndTime().getTime();
        SeckillUrlInfo urlInfo = new SeckillUrlInfo();
        if(startTime>currentTime){
            urlInfo.setCanStart(false);
            urlInfo.setCurrentTime(currentTime);
            urlInfo.setEndTime(endTime);
            urlInfo.setStartTime(startTime);
        }else if(currentTime<endTime){
            urlInfo.setCanStart(true);
            urlInfo.setSeckillUrl(generateSeckillUrl(goodsId));
        }
        return urlInfo;
    }

    public SeckillResult executeSeckill(String md5, Long goodsId, String userName) {
        //校验用户
        if(null == userName){
            return new SeckillResult("秒杀失败");
        }
        //校验md5
        String md5Str = getMD5(goodsId);
        if(!md5Str.equals(md5)){
            return new SeckillResult("秒杀失败");
        }
        //否已经秒杀成功过
        if(alreadyKilled(userName,goodsId)){
            return new SeckillResult("您已成功秒杀过该商品");
        }
        //同一个用户一秒内只允许访问一次
        //这里利用分布式锁即可，key的过期时间为1s
        if(!redisService.getSeckillCacheLockByLua(Constants.SECKILL_USER_ACCESS_RATE_PREFIX+userName+goodsId,1)){
            return new SeckillResult("秒杀失败");
        }

        //是否已达到秒杀计数器上限
        long count = redisService.increase(Constants.SECKILL_CACHE_COUNT_PREFIX+goodsId,-1);
        if(count<0){
            return new SeckillResult("秒杀失败");
        }
        //根据数据库的tps处理能力进行平滑访问流控,未获取到令牌进入等待
        //参见https://blog.csdn.net/chengzhang1989/article/details/79059316的思路
        smoothFlowControll(Constants.SECKILL_SMOOTH_FLOW_CONTROLL,1*1000,500);//1秒五百次
        if(finished.get()){
            return new SeckillResult("秒杀已结束");
        }
        SeckillResult result = new SeckillResult("秒杀失败");
        try {
             result = seckillDetailService.doSeckill(goodsId,userName);
             redisService.setSeckillCacheKey(Constants.SECKILL_SUCCESS_USER_PREFIX+userName+"_"+goodsId,"1",10,TimeUnit.MINUTES);
        }catch (OptimisticLockingFailureException oe){
            oe.printStackTrace();//库存已经没了
            if(!finished.get()){
                seckillGoodsDao.updateFinished(goodsId);
                String str = (String)redisService.getSeckillCacheKey(Constants.SECKILL_GOODS_CACHE_PREFIX+goodsId);
                SeckillGoodsDTO goodsDTO = new Gson().fromJson(str,SeckillGoodsDTO.class);
                goodsDTO.setEndTime(new Date());
                redisService.setSeckillCacheKey(Constants.SECKILL_GOODS_CACHE_PREFIX+goodsId,new Gson().toJson(goodsDTO),20, TimeUnit.MINUTES);
                finished.set(true);
            }
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                //已成功秒杀过
                result = new SeckillResult("您已成功秒杀过该商品");
            }
            e.printStackTrace();
        }
        return result;
    }

    private void smoothFlowControll(String key,long timeMills , int limit) {
        long currentTime = System.currentTimeMillis();
        long result = redisService.getNextAccessTimeByLua(key,timeMills,limit);
        while(currentTime<result){
            LockSupport.parkNanos(10);
            currentTime = System.currentTimeMillis();
        }
    }

    public boolean alreadyKilled(String userName, Long goodsId) {
        return null != redisService.getSeckillCacheKey(Constants.SECKILL_SUCCESS_USER_PREFIX+userName+"_"+goodsId);
    }
}
