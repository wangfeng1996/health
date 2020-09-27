package com.itheima.service.impl;



import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.CheckItemService;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;
import com.itheima.dao.CheckItemDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangfeng
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSiz, String queryString) {
        PageHelper.startPage(currentPage, pageSiz);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        System.out.println(page.toString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void delete(Integer id) throws RuntimeException {
        long countId = checkItemDao.findCountId(id);
        if (countId > 0) {
            throw new RuntimeException("当前检查项被引用，不能删除");
        } else {
            checkItemDao.delete(id);
        }
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    @Override
    public CheckItem findById(Integer id) {
        System.out.println(checkItemDao.findById(id).toString());
        return checkItemDao.findById(id);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
