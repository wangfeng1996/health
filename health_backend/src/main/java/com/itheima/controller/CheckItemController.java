package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.CheckItemService;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangfeng
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    /**
     * 远程注入
     */
    @Reference
    private CheckItemService checkItemService;

    //http://localhost:82/checkitem/add.do

    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
//            添加成功给的响应
        } catch (Exception e) {
            e.printStackTrace();
//            添加失败传给前端的响应
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 分页+模糊查询
     *
     * @param queryPageBean
     * @return
     */
    //http://localhost:82/checkitem/findPage.do

    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        return checkItemService.pageQuery(currentPage, pageSize, queryString);
    }


    /**
     * 删除数据
     *
     * @param id
     * @return
     */

    //    http://localhost:82/checkitem/delete/108.do

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            checkItemService.delete(id);
//            添加成功给的响应
        } catch (Exception e) {
            e.printStackTrace();
//            添加失败传给前端的响应
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 修改数据
     *
     * @param checkItem
     * @return
     */
    //http://localhost:82/checkitem/update.d

    @PutMapping("/update")
    public Result update(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.update(checkItem);
//            添加成功给的响应
        } catch (Exception e) {
            e.printStackTrace();
//            添加失败传给前端的响应
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }


    /**
     * 根据id进行查询数据
     *
     * @param id
     * @return
     */
    //http://localhost:82/checkitem/findById/108.do
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id) {

        try {

            CheckItem checkItem = checkItemService.findById(id);
            System.out.println(checkItem.toString());
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }


    /**
     * 查询所有的数据
     *
     * @return
     */
    //http://localhost:82/checkitem/findAll.do
    @GetMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckItem> all = checkItemService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, all);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}
