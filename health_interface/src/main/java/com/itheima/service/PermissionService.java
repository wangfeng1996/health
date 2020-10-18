package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;

import java.util.List;

public interface PermissionService {
    void add(Permission permission);
    PageResult findPage(QueryPageBean queryPageBean);
    void delete(Integer id);

    Permission findById(Integer id);

    void edit(Permission permission);

    List findAll();
}
