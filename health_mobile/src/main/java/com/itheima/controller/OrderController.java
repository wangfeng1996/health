package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author wangfeng
 */

@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;


    @RequestMapping("/submit")
    public Result submitOrder(@RequestBody Map map) {
        /**
         * key           value
         * setmealId      12,
         * idCard         323456433245666774,
         * sex             1,
         * name            小明,
         * validateCode    3456,
         * telephone       17834422993,
         * orderDate      2020-10-10
         */
//       获取手机号码，根据手机号码
        String telephone = (String) map.get("telephone");
//        根据获取的手机号码,获取redis中的验证码
        String code = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
//        获取前端传过来的验证码
        String validateCode = (String) map.get("validateCode");

//        判断传过来的验证码和redis中取出来的验证码是否相等
        if (code != null && validateCode != null && validateCode.equals(code)) {
//            设置预约的类型，类型有电话预约和微信预约
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
//            调用dubbo服务，将map数据传入到后端业务层中
            Result result = null;
            try {
//                预约是否成功
                result = orderService.order(map);
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
//            if (result.isFlag()) {
////                如果预约成功，发送短信给用户
//                //预约成功，可以为用户发送短信
//                try {
////                    SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, (String) map.get("orderDate"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            验证码正确并且预约成功了
            return result;
        } else {
//            验证码验证失败
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

    //根据预约ID查询预约相关信息
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Map map = orderService.findById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }


}
