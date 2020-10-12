package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wnagfeng
 */
public interface OrderSettingService {
    /**
     * 批量导入数据
     *
     * @param data
     */
    public void add(ArrayList<OrderSetting> data);

    /**
     * 根据月份获取时间段
     * @param date
     * @return
     */
    public List<Map> getOrderSettingByMonth(String date);


    /**
     * 设置该天可以预约的人数
     * @param orderSetting
     */
    public void editNumberByDate(OrderSetting orderSetting);

}
