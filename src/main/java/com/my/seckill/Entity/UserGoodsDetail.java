package com.my.seckill.Entity;

import com.my.seckill.dto.SeckillGoodsDTO;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by tufei on 2018/9/16.
 */
public class UserGoodsDetail {
    private Long id;//商品id
    private String goodsName;//商品名称
    private Integer inventoryCount;//库存数量
    private Date startTime;//秒杀开始时间
    private Date endTime;//秒杀结束时间
    private Date createTime;//商品创建时间
    //便于计算
    private Long startTimeMills;
    private Long endTimeMills;

    private Long currentTimeMills;

    private Boolean userKilled = false;

    private String killUrl = "";

    public UserGoodsDetail(){

    }

    public UserGoodsDetail(SeckillGoodsDTO dto){
        BeanUtils.copyProperties(dto,this);
        this.startTimeMills = dto.getStartTime().getTime();
        this.endTimeMills = dto.getEndTime().getTime();
        this.currentTimeMills = new Date().getTime();
    }

    public String getKillUrl() {
        return killUrl;
    }

    public void setKillUrl(String killUrl) {
        this.killUrl = killUrl;
    }

    public Long getCurrentTimeMills() {
        return currentTimeMills;
    }

    public void setCurrentTimeMills(Long currentTimeMills) {
        this.currentTimeMills = currentTimeMills;
    }

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

    public Long getStartTimeMills() {
        return startTimeMills;
    }

    public void setStartTimeMills(Long startTimeMills) {
        this.startTimeMills = startTimeMills;
    }

    public Long getEndTimeMills() {
        return endTimeMills;
    }

    public void setEndTimeMills(Long endTimeMills) {
        this.endTimeMills = endTimeMills;
    }

    public Boolean getUserKilled() {
        return userKilled;
    }

    public void setUserKilled(Boolean userKilled) {
        this.userKilled = userKilled;
    }
}
