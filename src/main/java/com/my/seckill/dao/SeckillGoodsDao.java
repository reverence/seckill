package com.my.seckill.dao;

import com.my.seckill.dto.SeckillGoodsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tufei on 2018/9/2.
 */
public interface SeckillGoodsDao {

    /**
     * 获取秒杀商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<SeckillGoodsDTO> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 获取商品详情
     * @param id
     * @return
     */
    SeckillGoodsDTO queryById(@Param("id")long id);

    /**
     * 扣减库存
     * @param id
     * @return
     */
    int deductingInventory(@Param("id")long id);

    /**
     * 秒杀已结束
     * @param id
     * @return
     */
    int updateFinished(@Param("id") Long id);
}
