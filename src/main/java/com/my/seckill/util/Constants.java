package com.my.seckill.util;

public class Constants {

    /**
     * 缓存秒杀列表页的key
     */
    public static final String SECKILL_LIST_WEB_CACHE_KEY = "seckill_list";
    /**
     * 列表页分布式锁
     */
    public static final String SECKILL_LIST_LIST_WEB_CACHE_LOCK = "seckill_list_lock";
    /**
     * key默认超时时间--10s
     */
    public static final long DEFAULT_CACHE_EXPIRED_MILLS = 10*1000;//
    /**
     * 缓存秒杀商品信息
     */
    public static final String SECKILL_GOODS_CACHE_PREFIX="seckill_goods_cache_";

    /**
     * md5加密串---config.properties
     */
    public static final String MD5_ENCRYPT_KEY = "md5_encrypt_key";

    /**
     * cache初始化的分布式锁key
     */
    public static final String SECKILL_CACHE_INIT_LOCK = "seckill_cache_init_lock";

    /**
     * 商品库存计数器key,初始化时允许两倍的值
     */
    public static final String SECKILL_CACHE_COUNT_PREFIX = "seckill_cache_count_";

    /**
     * 缓存已经秒杀成功的用户
     */
    public static final String SECKILL_SUCCESS_USER_PREFIX = "seckill_success_user_";
    /**
     * 控制用户访问频率
     */
    public static final String SECKILL_USER_ACCESS_RATE_PREFIX ="seckill_user_access_rate_";

    /**
     * 平滑访问数据库频率
     */
    public static final String SECKILL_SMOOTH_FLOW_CONTROLL = "seckill_smooth_flow_controll";
    /**
     * 需要缓存的页面key--config.properties
     */
    public static final String SECKILL_CACHED_WEB_PAGE_LIST = "seckill_cache_web_page";
    /**
     * sessionId
     */
    public static final String SECKILL_SESSION_ID = "x-seckill-sessionId";

}
