package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 批量导入预约设置的数据
     *
     * @param data
     */
    public void add(ArrayList<OrderSetting> data) {
//        判断集合中的数据是否为空,如果不为空，则进行下面的操作
        if (data != null && data.size() > 0) {
//            遍历集合，获取集合中的日期元素，然后根据日期元素在进行判断是进行插入的操作还是进行更新的操作；
            for (OrderSetting setting : data) {
//               获取数据库是否有该日期
                long orderDate = orderSettingDao.findCountByOrderDate(setting.getOrderDate());
                if (orderDate > 0) {
//                   已经进行了预约设置，我们要进行更新设置
                    orderSettingDao.updateNumberByOrderDate(setting);
                } else {
//                    进行插入操作
                    orderSettingDao.add(setting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        System.out.println(date);
//        开始时间
        String begin = date + "-1";
//        结束时间
        String end = date + "-31";
        HashMap<String, String> map = new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);
        List<OrderSetting> byMonth = orderSettingDao.getOrderSettingByMonth(map);
        //        创建一个List集合，用于存储map数据
        List<Map> result = new ArrayList<>();
//
        if (byMonth != null && byMonth.size() > 0) {
//            创建map集合，用于存储查询到的数据
            for (OrderSetting setting : byMonth) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", setting.getOrderDate().getDate());
                hashMap.put("number", setting.getNumber());
                hashMap.put("reservations", setting.getReservations());
//                将这些数据加入到集合中
                result.add(hashMap);
            }
        }
        return result;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        //根据日期查询是否已经进行了预约设置
        long count = orderSettingDao.findCountByOrderDate(orderDate);
        if(count > 0){
            //当前日期已经进行了预约设置，需要执行更新操作
            orderSettingDao.updateNumberByOrderDate(orderSetting);
        }else{
            //当前日期没有就那些预约设置，需要执行插入操作
            orderSettingDao.add(orderSetting);
        }

    }
}
