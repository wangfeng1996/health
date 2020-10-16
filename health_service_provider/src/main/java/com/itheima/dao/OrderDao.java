package com.itheima.dao;

import com.itheima.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    public void add(Order order);

    public List<Order> findByCondition(Order order);

    public Map findById4Detail(Integer id);

    public Integer findOrderCountByDate(String date);

    public Integer findOrderCountAfterDate(String date);

    public Integer findVisitsCountByDate(String date);

    public Integer findVisitsCountAfterDate(String date);

    public List<Map> findHotSetmeal();

    public Integer findThisWeekOrderNumber(@Param("FistDay") String FistDay, @Param("LastDay") String LastDay);


    public Integer findThisWeekVisitsOrderNumber(@Param("FistDay") String FistDay, @Param("LastDay") String LastDay);
}
