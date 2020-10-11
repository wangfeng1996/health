package com.itheima.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.service.CheckItemService;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;
import com.itheima.dao.CheckItemDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangfeng
 * 检查项的业务层
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    //添加
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    //  分页查询
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSiz, String queryString) {
        PageHelper.startPage(currentPage, pageSiz);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        System.out.println(page.toString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    //删除
    @Override
    public void delete(Integer id) throws RuntimeException {
        long countId = checkItemDao.findCountId(id);
        if (countId > 0) {
            throw new RuntimeException("当前检查项被引用，不能删除");
        } else {
            checkItemDao.delete(id);
        }
    }

    //更新
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }


    // 根据id进行查询
    @Override
    public CheckItem findById(Integer id) {
        System.out.println(checkItemDao.findById(id).toString());
        return checkItemDao.findById(id);
    }

    // 查询所有
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
