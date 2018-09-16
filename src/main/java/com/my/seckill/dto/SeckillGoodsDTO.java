package com.my.seckill.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tufei on 2018/9/2.
 */
public class SeckillGoodsDTO implements Serializable {
    private Long id;//商品id
    private String goodsName;//商品名称
    private Integer inventoryCount;//库存数量
    private Date startTime;//秒杀开始时间
    private Date endTime;//秒杀结束时间
    private Date createTime;//商品创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(Integer inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
