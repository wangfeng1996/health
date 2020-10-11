package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.service.CheckGroupService;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.CheckGroupAndCheckItemIds;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangfeng
 * 检查组的业务层
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional

public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 获取检查组中的自增id，在根据检查组中的自增id进行添加操作
     *
     * @param checkGroupAndCheckItemIds 包含检查组和检查项的id
     */

    @Override
    public boolean add(CheckGroupAndCheckItemIds checkGroupAndCheckItemIds) throws RuntimeException {

        CheckGroup checkGroup = checkGroupAndCheckItemIds.getCheckGroup();
        boolean addFlag = checkGroupDao.add(checkGroup);
        if (addFlag) {
            Integer gid = checkGroup.getId();
            Integer[] checkItemIds = checkGroupAndCheckItemIds.getCheckitemIds();
            for (Integer cid : checkItemIds) {
                checkGroupDao.groupIntoCheckItemIds(gid, cid);
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 分页查询
     * @param currentPage 页码
     * @param pageSiz     每页记录数
     * @param queryString 查询条件
     * @return
     */

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSiz, String queryString) {
        PageHelper.startPage(currentPage, pageSiz);
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 查询所有记录
     * @return
     */

    @Override
    public List<CheckGroup> finAll() {
        List<CheckGroup> all = checkGroupDao.findAll();
        return all;
    }

    /**
     * 根据id进行查询数据
     * @param id
     * @return
     */

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }


    /**
     * 根据检查组的id查询与之对应的检查项的id的集合
     * @param id
     * @return
     */
    @Override
    public List<Integer> checkGroupIdWithCheckItemIds(Integer id) {
        return checkGroupDao.checkGroupIdWithCheckItemIds(id);
    }

    /**
     * 思路分析：
     * 根据id删除中间表中的信息
     * 然后在进行修改
     * @param checkGroupAndCheckItemIds   检查项id的数据和检查组的集合
     */

    @Override
    public void edit(CheckGroupAndCheckItemIds checkGroupAndCheckItemIds) {
//        获取id
        Integer groupId = checkGroupAndCheckItemIds.getCheckGroup().getId();
//        删除检查项
        checkGroupDao.deleteCheckItemIds(groupId);
        CheckGroup checkGroup = checkGroupAndCheckItemIds.getCheckGroup();
//      修改检查组
        checkGroupDao.edit(checkGroup);
//        获取id
        Integer gid = checkGroup.getId();
        Integer[] checkItemIds = checkGroupAndCheckItemIds.getCheckitemIds();
//        添加中间表
        for (Integer cid : checkItemIds) {
            checkGroupDao.groupIntoCheckItemIds(gid, cid);
        }
    }

    /**
     * 思路：首先判断删除项是否与中间表有关联
     * @param id  根据传过来的id删除数据
     */

    @Override
    public void delete(Integer id) {
        long setMealId = checkGroupDao.findSetMealId(id);
        if (setMealId > 0) {
            throw new RuntimeException("当前检查项被引用，不能删除");
        } else {
            checkGroupDao.deleteCheckGroupId(id);
        }
    }
}
