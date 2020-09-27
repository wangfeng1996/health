package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.CheckGroupService;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.CheckGroupAndCheckItemIds;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangfeng
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    /**
     * dubbo 远程注入
     */
    @Reference
    private CheckGroupService checkGroupService;

    /**
     * http://localhost:82/checkgroup/add.do
     *
     * @param checkGroupAndCheckItemIds 检查组和检查项的id
     * @return
     */

    @PostMapping("/add")
    public Result add(@RequestBody CheckGroupAndCheckItemIds checkGroupAndCheckItemIds) {

        boolean addFlag = checkGroupService.add(checkGroupAndCheckItemIds);
        if (addFlag) {
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } else {
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    /**
     * 分页查询+ 模糊查询
     *
     * @param queryPageBean 将分页查询封装成一个对象
     * @return
     */

    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        return checkGroupService.pageQuery(currentPage, pageSize, queryString);
    }

    /**
     * 查询所有
     *
     * @return
     */

    @GetMapping("/findAll")
    public Result findPage() {
        try {
            List<CheckGroup> checkGroups = checkGroupService.finAll();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroups);
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 根据id进行查询数据
     *
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id) {
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 根据检查项的id，查找检查组对应的多个id
     */
    @GetMapping("/checkGroupIdWithCheckItemIds/{id}")
    public Result checkGroupIdWithCheckItemIds(@PathVariable Integer id) {
        try {
            List<Integer> integers = checkGroupService.checkGroupIdWithCheckItemIds(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, integers);
        } catch (Exception e) {
            return new Result(true, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 修改
     *
     * @param checkGroupAndCheckItemIds 传过来的参数是group的对象和检查项的数组
     * @return
     */

    @PostMapping("/edit")
    public Result edit(@RequestBody CheckGroupAndCheckItemIds checkGroupAndCheckItemIds) {
        try {
            checkGroupService.edit(checkGroupAndCheckItemIds);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    /**
     * 根据id进行删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            System.out.println(id);
            checkGroupService.delete(id);

            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }
}
