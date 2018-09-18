package com.my.seckill.interceptor;

import com.my.seckill.service.RedisService;
import com.my.seckill.util.Constants;
import com.my.seckill.util.SpringApplicationContextUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by tufei on 2018/9/9.
 */
public class UserLoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String url = request.getRequestURI();
        String queryString = request.getQueryString();
        if(!StringUtils.isEmpty(queryString)){
            url = url+"?"+queryString;
        }

        if(!StringUtils.isEmpty(url)){
            //判断是否登录
            RedisService redisService = (RedisService) SpringApplicationContextUtil.getBean(RedisService.class);
            Cookie[] cookies = request.getCookies();
            String value = null;
            if(null != cookies){
                for(Cookie cookie : cookies){
                    if (Constants.SECKILL_SESSION_ID.equals(cookie.getName())){
                        value = cookie.getValue();
                        break;
                    }
                }
            }
            if(value == null){
                response.sendRedirect("/user/login?actionUrl="+url);
                return false;
            }
            String user = (String)redisService.getSeckillCacheKey(value);
            if(null == user){
                response.sendRedirect("/user/login?actionUrl="+url);
                return false;
            }
            long timeOut = System.currentTimeMillis()/1000+10*60;
            redisService.expire(value,timeOut, TimeUnit.SECONDS);
            redisService.expire(user,timeOut,TimeUnit.SECONDS);
            request.setAttribute("userName",user);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
