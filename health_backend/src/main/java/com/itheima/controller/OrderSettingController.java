package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.DateUtils;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.tools.jconsole.inspector.XObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;


    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {
        try {
//            读取excel文件信息
            List<String[]> list = POIUtils.readExcel(excelFile);
            ArrayList<OrderSetting> arrayList = new ArrayList<>();
            for (String[] strings : list) {
                String orderDate = strings[0];
                String number = strings[1];
                OrderSetting orderSetting = new OrderSetting(new Date(orderDate), Integer.parseInt(number));
//                将文件添加到集合中
                arrayList.add(orderSetting);
            }
//            调用后端的数据，将数据写入到数据库中
            orderSettingService.add(arrayList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }


    //    http://localhost:82/ordersetting/editNumberByDate.do
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }

    //http://localhost:82/ordersetting/getOrderSettingByMonth.do?date=2020-10
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date) {
        try {
//            根据月份查询时间，将时间数据返回给前端的页面
            List<Map> monthList = orderSettingService.getOrderSettingByMonth(date);
            System.out.println(monthList);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, monthList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);

        }
    }

//    @RequestMapping("/meData")
//    public Result meData(@RequestBody List<Object> list) {
//        for (Object o : list) {
//            try {
//                Date date = DateUtils.parseString2Date(o.toString(), "yyyy-MM-dd");
//                System.out.println(DateUtils.parseDate2String(date));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        return null;
//
//    }


}
