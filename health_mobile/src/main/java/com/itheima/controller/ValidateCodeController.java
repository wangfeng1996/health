package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author wangfeng
 * <p>
 * 对传送过来的的手机号码进行发送验证码,随机生成验证码后传入到redis中，最后校验的时候去redis中进行获取
 * <p>
 * <p>
 * 主要是讲前端传过来的号码，然后根据号码进行随机生成一个4位数的有效数字
 * 将有效数字存储到redis中，并在有效的时间内清楚该验证码
 */


@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
//
    /**
     * 自动装配JedisPool
     */
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result Send4Order(String telephone) {

//随机生成4位验证码数据
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        System.out.println(code);
//      给用户进行发送验证码
        /**
         * 第一个参数是:模板信息
         * 第二个参数是:传入的手机号码
         * 第三个参数是:验证码 --将int类型的数据转化成String类型的数据
         */
        try {
            // 向阿里云发送验证
//            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code.toString());
            // 将验证码放入到jedis进行保存,key是电话号码+预约的路径，seconds是验证码的有效时间，最后的验证码
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 300, code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
