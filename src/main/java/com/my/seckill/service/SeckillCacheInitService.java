package com.my.seckill.service;

import com.google.gson.Gson;
import com.my.seckill.dao.SeckillGoodsDao;
import com.my.seckill.dto.SeckillGoodsDTO;
import com.my.seckill.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SeckillCacheInitService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    /**
     * 启动后预先缓存部分秒杀信息
     */
    @PostConstruct
    public void initCache(){//生产环境中使用的cache应该进行合理的规划，将seckill和user session的cache分离
        //防止多个结点启动时重复初始化
        if(redisService.getSeckillCacheLockByLua(Constants.SECKILL_CACHE_INIT_LOCK,10)){
            List<SeckillGoodsDTO> lists = seckillGoodsDao.queryAll(0,10);//实际应该分页
            if(!CollectionUtils.isEmpty(lists)){
                for(SeckillGoodsDTO seckillGoodsDTO : lists){
                    long expiredTimes = seckillGoodsDTO.getEndTime().getTime()-System.currentTimeMillis()+Constants.DEFAULT_CACHE_EXPIRED_MILLS;
                    //缓存秒杀商品信息
                    redisService.setSeckillCacheKey(Constants.SECKILL_GOODS_CACHE_PREFIX+seckillGoodsDTO.getId(),new Gson().toJson(seckillGoodsDTO),expiredTimes,TimeUnit.MILLISECONDS);
                    //计数器信息
                    redisService.setSeckillCacheKey(Constants.SECKILL_CACHE_COUNT_PREFIX+seckillGoodsDTO.getId(),String.valueOf(seckillGoodsDTO.getInventoryCount()*2),expiredTimes,TimeUnit.MILLISECONDS);
                }
            }
        }
    }

}
