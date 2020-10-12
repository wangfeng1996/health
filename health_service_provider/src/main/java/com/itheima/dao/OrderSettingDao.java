package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    public void add(@Param("orderSetting") OrderSetting orderSetting);

    public void updateNumberByOrderDate(@Param("orderSetting") OrderSetting orderSetting);

    public long findCountByOrderDate(@Param("orderDate") Date orderDate);

    public List<OrderSetting> getOrderSettingByMonth(Map map);

    //
    public OrderSetting findByOrderDate(Date orderDate);

    //
    public void editReservationsByOrderDate(OrderSetting orderSetting);
}
