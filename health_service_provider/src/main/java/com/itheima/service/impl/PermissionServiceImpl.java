package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.PermissionDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    public void add(Permission permission) {
        System.out.println(permission);
         permissionDao.add(permission);

    }

    /**
     * 分页查询
     * @param queryPageBean 传入队形通过调用get方法获取相关数值
     * @return 返回结果集对象pageResult 该对象封装了页面总条数以及页面数据
     */
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();//条件
        PageHelper.startPage(currentPage,pageSize);//开始分页
        List<Permission> list = permissionDao.selectByCondition(queryString);
//        return new PageResult(page.getTotal(),page.getResult());
        PageInfo<Permission> permissionPageInfo = new PageInfo<Permission>(list);//获取分页队形pageinfo
//        PageResult pageResult = new PageResult();
//        pageResult.setTotal(permissionPageInfo.getTotal());
//        pageResult.setRows(permissionPageInfo.getList());
           PageResult pageResult = new PageResult(permissionPageInfo.getTotal(), permissionPageInfo.getList());
           return  pageResult;

    }

    /**
     * 查询相关id有没有被关联
     * @param id 参数
     * @return      返回次数
     */

    /**
     * 根据id删除
     * @param id
     */
    public void delete(Integer id) {
        Integer byPermissionId = permissionDao.findByPermissionId(id);
        if (byPermissionId>0){
            throw new RuntimeException("当前检查项被引用，不能删除");
        }
          permissionDao.delete(id);
    }

    /**
     * 查找 为了回显数据
     * @param id
     * @return
     */
    public Permission findById(Integer id) {
        Permission permission = permissionDao.findById(id);
        return permission;
    }


    /**
     * 修改
     * @param permission
     */
    public void edit(Permission permission) {
        permissionDao.edit(permission);
    }

    public List findAll() {
       return permissionDao.findAll();
    }
}
