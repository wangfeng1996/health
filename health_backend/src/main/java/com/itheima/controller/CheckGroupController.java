package com.itheima.controller;

import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangfeng
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup) {
        System.out.println(checkGroup.toString());
        return null;
    }

}
