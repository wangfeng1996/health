package com.itheima.dao;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

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
    public User findByUsername(@Param("username") String username);

    public Integer findUserNameId(@Param("name") String name);
}
