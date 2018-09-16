package com.my.seckill.filter;

import com.my.seckill.Entity.ResponseWrapper;
import com.my.seckill.service.RedisService;
import com.my.seckill.util.ConfigUtils;
import com.my.seckill.util.Constants;
import com.my.seckill.util.SpringApplicationContextUtil;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by tufei on 2018/9/5.
 */
public class SeckillCacheFilter implements Filter{

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        boolean result = false;
        String cacheWebPageList = ConfigUtils.getKey(Constants.SECKILL_CACHED_WEB_PAGE_LIST);
        if(!StringUtils.isEmpty(cacheWebPageList)){
            String[] list = cacheWebPageList.split(",");
            String requestUrl = req.getRequestURI();
            for(String s : list){
                if(s.equalsIgnoreCase(requestUrl)){
                    result = true;
                    break;
                }
            }
        }
        if (!result) {//不需要缓存
            filterChain.doFilter(servletRequest, resp);
            return;
        }

        String html = getHtmlFromCache();
        if (null == html) {
            // 缓存中没有
            if(SpringApplicationContextUtil.getBean(RedisService.class).getSeckillCacheLockByLua(Constants.SECKILL_LIST_LIST_WEB_CACHE_LOCK,Constants.DEFAULT_CACHE_EXPIRED_MILLS/1000)){
                //获取到了锁
                // 截取生成的html并放入缓存
                ResponseWrapper wrapper = new ResponseWrapper(resp);
                filterChain.doFilter(servletRequest, wrapper);
                html = wrapper.getResult();
                putIntoCache(html);
                // 返回响应
                resp.setContentType("text/html; charset=utf-8");
                resp.getWriter().print(html);
            }else {
                //没有获取到锁时提示3s后自动再次访问列表页
                resp.setContentType("text/html; charset=utf-8");
                resp.setHeader("refresh","3;url=/seckill/list");
                String message = "当前访问量过多，3秒后自动再次访问，如果失败请手动刷新";
                resp.getWriter().write(message);
            }
        }else{
            resp.setContentType("text/html; charset=utf-8");
            resp.getWriter().print(html);
        }
    }

    private void putIntoCache(String html) {
        SpringApplicationContextUtil.getBean(RedisService.class).setSeckillCacheKey(Constants.SECKILL_LIST_WEB_CACHE_KEY,html,Constants.DEFAULT_CACHE_EXPIRED_MILLS, TimeUnit.MILLISECONDS);
    }

    public void destroy() {

    }

    private String getHtmlFromCache() {
        String html = (String)SpringApplicationContextUtil.getBean(RedisService.class).getSeckillCacheKey(Constants.SECKILL_LIST_WEB_CACHE_KEY);
        return html;
    }
}
