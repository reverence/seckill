package com.my.seckill.Entity;

import java.util.Date;

public class SeckillUrlInfo {
    private boolean canStart;
    private String seckillUrl;
    private Long startTime;//秒杀开始时间
    private Long endTime;//秒杀结束时间
    private Long currentTime;//当前时间


    public boolean isCanStart() {
        return canStart;
    }

    public void setCanStart(boolean canStart) {
        this.canStart = canStart;
    }

    public String getSeckillUrl() {
        return seckillUrl;
    }

    public void setSeckillUrl(String seckillUrl) {
        this.seckillUrl = seckillUrl;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }
}
