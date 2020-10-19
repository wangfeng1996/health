package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author wangfeng
 */
public interface RoleService {

    public void add(Map<String, Object> map);

    public PageResult pageQuery(QueryPageBean queryPageBean);

    public void delete(Integer id);

    public Role findRoleById(Integer id);

    public List<Integer> findMenuById(Integer id);

    public List<Integer> findPermissionById(Integer id);

    public void edit(Map<String, Object> map);

}