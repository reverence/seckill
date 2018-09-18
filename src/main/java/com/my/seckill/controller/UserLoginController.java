package com.my.seckill.controller;

import com.my.seckill.service.RedisService;
import com.my.seckill.service.UserService;
import com.my.seckill.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by tufei on 2018/9/10.
 */
@Controller
@RequestMapping(value = "/user")
public class UserLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Model model, String actionUrl){
        model.addAttribute("actionUrl",actionUrl);
        return "login";
    }

    @RequestMapping(value = "/do_login",method = RequestMethod.POST)
    public String doLogin(String userName, String password,String actionUrl, HttpServletResponse response,HttpServletRequest request){
        if(userService.login(userName,password)){
            Cookie cookie = new Cookie(Constants.SECKILL_SESSION_ID, UUID.randomUUID().toString());
            cookie.setMaxAge(10*60);//十分钟
            cookie.setPath("/seckill");
            response.addCookie(cookie);
            String id = (String) redisService.getSeckillCacheKey(userName);
            if(id != null){
                removeCookie(id,request);
            }
            redisService.setSeckillCacheKey(cookie.getValue(),userName,10, TimeUnit.MINUTES);//十分钟
            redisService.setSeckillCacheKey(userName,cookie.getValue(),10,TimeUnit.MINUTES);

            if(StringUtils.isEmpty(actionUrl)) {
                //说明直接登录
                return "redirect:/seckill/list";
            }else {
                return "redirect:"+actionUrl;
            }
        }else{
            return "redirect:login";
        }
    }

    private void removeCookie(String id, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            String name = cookie.getName();
            String value = cookie.getValue();
            if(name.equalsIgnoreCase(Constants.SECKILL_SESSION_ID) && value.equalsIgnoreCase(id)){
                cookie.setMaxAge(0);
            }

        }
        redisService.expire(id,1,TimeUnit.MICROSECONDS);//原来的id过期
    }

}
