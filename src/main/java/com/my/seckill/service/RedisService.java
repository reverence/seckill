package com.my.seckill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Created by tufei on 2018/9/7.
 */
@Service
public class RedisService {

    @Autowired
    @Qualifier("seckillCacheRedisTemplate")
    private RedisTemplate seckillCacheRedisTemplate;

    @Resource(name = "redisLockScript")
    private RedisScript<Boolean> redisLockScript;
    @Resource(name = "smoothAccessScript")
    private RedisScript<String> smoothAccessScript;

    /**
     * value为redis直接支持的类型
     * @param key
     * @param value
     * @param EXPIRED_TIME
     * @param unit
     */
    public void setSeckillCacheKey(final  String key,final String value,long EXPIRED_TIME,TimeUnit unit){
        seckillCacheRedisTemplate.opsForValue().set(key,value,EXPIRED_TIME, unit);
    }

    /**
     * value为redis直接支持的类型
     * @param key
     * @return
     */
    public Object getSeckillCacheKey(final String key){
        return seckillCacheRedisTemplate.opsForValue().get(key);
    }

    /**
     *
     * @param key
     * @param seconds
     * @return
     */
    public boolean getSeckillCacheLockByLua(final String key,final long seconds){
        Object result = seckillCacheRedisTemplate.execute(redisLockScript, Collections.singletonList(key),new String("lock"),String.valueOf(seconds));
        return (Boolean)result;
    }

    /**
     * value为redis直接支持的类型
     * @param key
     * @param step
     * @return
     */
    public long increase(String key,long step){
        return seckillCacheRedisTemplate.opsForValue().increment(key,step);
    }

    /**
     *
     * @param key
     * @param timeMills
     * @param limit
     * @return
     */
    public long getNextAccessTimeByLua(final String key,final long timeMills,final int limit){
        long currentTime = System.currentTimeMillis();
        long step = timeMills/limit;
        String result= (String)seckillCacheRedisTemplate.execute(smoothAccessScript,Collections.singletonList(key),String.valueOf(currentTime),String.valueOf(step));
        return Long.parseLong(result);
    }
}
