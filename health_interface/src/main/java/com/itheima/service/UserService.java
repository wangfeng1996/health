package com.itheima.service;

import com.itheima.pojo.User;

/**
 * @author wangfeng
 */
public interface UserService {
    /**
     * 根据姓名查找用户的信息
     *
     * @param username 用户的姓名
     * @return
     */
    User findByUsername(String username);
}
