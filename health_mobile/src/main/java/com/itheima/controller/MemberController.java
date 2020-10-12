package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author wangfeng
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    /**
     * http://localhost/member/login.do
     *
     * @param httpServletRequest
     * @param map
     * @return
     */

    @RequestMapping("/login")
    public Result Login(HttpServletRequest httpServletRequest, @RequestBody Map map) {

//        获取前端传过来的telephone数据
        String telephone = (String) map.get("telephone");
//        获取前端传过来的验证码
        String validateCode = (String) map.get("validateCode");
//        获取根据telephone和login字符串你获取redis存储的验证码
        String redisValidateCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
//        判断传过来的验证码和redis中存储的验证码是否正确
        if (validateCode != null && redisValidateCode != null && validateCode.equals(redisValidateCode)) {
//            登陆成功
//            1、判断是否是会员，是会员的话直接进行登陆，
//                1.1 查询数据库中是否有是否有该会员的信息
            Member member = memberService.findByTelephone(telephone);

//            2、如果不是会员，则进行自动注册，将会员的信息存储到数据库中
            if (member == null) {
//                如果信息没有查询出来，则说明该用户不是会员
//                创建member对象
                Member member1 = new Member();
//                设置member中的变量值
                member1.setPhoneNumber(telephone);
                member1.setRegTime(new Date());
//                将member对象加入到数据库中
                memberService.add(member1);
            }
//                说明该用户是会员，则登陆成功，并且将该用户的信息写入cookie
//              创建cookie,根据手机号码进行跟踪的操作
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setPath("/");
//                将会员信息保存到redis中，目前主流做法是利用redis作为session管理的实现
            String json = JSON.toJSON(member).toString();
            jedisPool.getResource().setex(telephone, 60 * 30, json);
            return new Result(true, MessageConstant.LOGIN_SUCCESS);

        } else {
//            登陆失败
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }
}
