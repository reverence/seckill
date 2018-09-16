package com.my.seckill.controller;

import com.my.seckill.Entity.SeckillResponse;
import com.my.seckill.Entity.SeckillResult;
import com.my.seckill.Entity.SeckillUrlInfo;
import com.my.seckill.Entity.UserGoodsDetail;
import com.my.seckill.dto.SeckillGoodsDTO;
import com.my.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by tufei on 2018/9/2.
 */
@Controller
@RequestMapping(value = "/seckill")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        List<SeckillGoodsDTO> list=seckillService.getSeckillGoodsList();
        model.addAttribute("goodsList",list);
        return "list";
    }

    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public String detail(@RequestParam("id") Long id, Model model,HttpServletRequest request){
        if(null == id){
            return "redirect:/seckill/list";
        }
        SeckillGoodsDTO seckillGoodsDTO = seckillService.getSeckillGoodsById(id);
        if(null == seckillGoodsDTO){
            return "redirect:/seckill/list";
        }

        UserGoodsDetail detail = new UserGoodsDetail(seckillGoodsDTO);
        String userName = (String)request.getAttribute("userName");
        if(seckillService.alreadyKilled(userName,id)){
            detail.setUserKilled(true);
        }
        model.addAttribute("seckillGoods", detail);
        return "detail";
    }

    @RequestMapping(value = "/currentTime", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResponse<Long> currentTime() {
        Date now = new Date();
        return new SeckillResponse<Long>(true,now.getTime());
    }

    @RequestMapping(value = "/url", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResponse<SeckillUrlInfo> getSeckillUrl (@RequestParam("goodsId")Long goodsId,HttpServletRequest request) {
        if(null == goodsId){
            return new SeckillResponse<SeckillUrlInfo>(false,"参数错误");
        }
        String userName = (String)request.getAttribute("userName");
        SeckillUrlInfo urlInfo  = seckillService.getSeckillUrlInfo(goodsId);
        return new SeckillResponse<SeckillUrlInfo>(true,urlInfo);
    }

    @RequestMapping(value = "/{md5}/execute", method = RequestMethod.POST)
    @ResponseBody
    public SeckillResponse<SeckillResult> getSeckillUrl (@PathVariable("md5") String md5, @RequestParam("goodsId")Long goodsId, HttpServletRequest request) {
        if(null == md5 || null == goodsId){
            return new SeckillResponse<SeckillResult>(false,"参数错误");
        }
        String userName = (String)request.getAttribute("userName");
        SeckillResult result = seckillService.executeSeckill(md5,goodsId,userName);
        return new SeckillResponse<SeckillResult>(true,result);
    }

}
