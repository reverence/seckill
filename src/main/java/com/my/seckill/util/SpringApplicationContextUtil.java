package com.my.seckill.util;

import com.my.seckill.service.RedisService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext ctx;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return ctx.getBean(beanClass);
    }

    public static Object getBean(String beanName){
        return ctx.getBean(beanName);
    }
}
