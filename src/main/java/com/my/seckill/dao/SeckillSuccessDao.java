package com.my.seckill.dao;

import org.apache.ibatis.annotations.Param;

public interface SeckillSuccessDao {

    int insertSuccessKilled(@Param("goodsId") long goodsId, @Param("userName") String userName);
}
