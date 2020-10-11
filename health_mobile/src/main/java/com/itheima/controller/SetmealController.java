package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    /**
     * 远程注入
     */
    @Reference
    private SetmealService setmealService;

    /**
     * 获取所有的套餐信息
     *
     * @return
     */
    @RequestMapping("/getAllSetmeal")
    public Result getSetmeal() {
        try {
            List<Setmeal> list = setmealService.findAll();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
    }


    /**
     * 根据套餐ID查询套餐详情（套餐基本信息、套餐对应的检查组信息、检查组对应的检查项信息)
     * 根据套餐id查询套餐组（最终查询到检查项的详细信息）
     *
     * @param id 套餐id   根据套餐id  查询检查组  检查项信息
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(int id) {
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 根据id查询检查套餐的信息
     *
     * @param id
     * @return
     */

    @RequestMapping("/findId")
    public Result findId(int id) {
        try {
            Setmeal setmeal = setmealService.findId(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}


