package com.my.seckill.service;

import com.my.seckill.Entity.SeckillResult;
import com.my.seckill.dao.SeckillGoodsDao;
import com.my.seckill.dao.SeckillSuccessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeckillDetailService {
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Autowired
    private SeckillSuccessDao seckillSuccessDao;

    @Transactional
    public SeckillResult doSeckill(Long goodsId, String userName) {
        seckillSuccessDao.insertSuccessKilled(goodsId,userName);
        //扣库存
        int num = seckillGoodsDao.deductingInventory(goodsId);
        if(num == 0){
            throw new OptimisticLockingFailureException("update failed");
        }
        return new SeckillResult("秒杀成功");
    }
}
