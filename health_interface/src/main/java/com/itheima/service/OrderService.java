package com.itheima.service;

import com.itheima.entity.Result;

import java.util.Map;

/**
 * @author wangfeng
 */
public interface OrderService {
    public Result order(Map map) throws Exception;
    public Map findById(Integer id) throws Exception;
}
