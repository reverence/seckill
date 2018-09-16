package com.my.seckill.service;

import com.my.seckill.BaseTest;
import com.my.seckill.Entity.SeckillResult;
import com.my.seckill.dao.SeckillGoodsDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by tufei on 2018/9/16.
 */
public class SeckillServiceTest extends BaseTest {

    @Autowired
    private SeckillService seckillService;
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    @Test
    public void test(){//简单测试
        //保证有id为1的数据,在秒杀时间内，假设库存为100
        final Long goodsId = 1l;
        int count = 1000;
        List<Thread> threadList = new ArrayList<Thread>(count);
        final List<String> failed = new ArrayList<String>();
        final CountDownLatch countDownLatch = new CountDownLatch(count);
        for(int i=0;i<count;i++){
            Thread t = new Thread("thread-"+i){
                @Override
                public void run() {
                    String md5 = seckillService.getMD5(goodsId);
                    String userName = "test"+Thread.currentThread().getName().split("-")[1];
                    SeckillResult result = seckillService.executeSeckill(md5,goodsId,userName);
                    if(!result.getResultInfo().equals("秒杀成功")){
                        failed.add(userName);
                    }
                    countDownLatch.countDown();
                }
            };
            threadList.add(t);
        }
        for(Thread thread : threadList){
            thread.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(failed.size() == 900);
    }
}
