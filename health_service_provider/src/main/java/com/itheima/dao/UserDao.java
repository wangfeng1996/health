package com.itheima.dao;

import com.itheima.pojo.User;

/**
 * @author wangfeng
 */
public interface UserDao {


    /**
     * 根据用户名查询用户的所有信息
     *
     * @param username
     * @return
     */
    public User findByUsername(String username);
}
