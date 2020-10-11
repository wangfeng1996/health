package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.entity.Result;
import com.itheima.service.OrderService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author wangfeng
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    /**
     * 预约是否成功
     *
     * @param map  map是前端传过来的json数据，最后被封装到了
     * @return
     */

    @Override
    public Result order(Map map) {
//        1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");


//        2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
//        3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再
//                次预约
//        4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
//        5、预约成功，更新当日的已预约人数
        return null;
    }
}
