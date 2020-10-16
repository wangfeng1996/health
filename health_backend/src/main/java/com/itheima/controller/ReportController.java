package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import com.itheima.service.SetmealService;
import com.itheima.utils.DateUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wangfeng
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;
    @Reference
    private ReportService reportService;


    /**
     * 会员数量折线图数据
     *
     * @return
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        Map<String, Object> map = new HashMap<>();
        List<String> months = new ArrayList();
        //获得日历对象，模拟时间就是当前时间
        Calendar calendar = Calendar.getInstance();
        //计算过去一年的12个月
        //获得当前时间往前推12个月的时间
        calendar.add(Calendar.MONTH, -12);
        for (int i = 0; i < 12; i++) {
            //获得当前时间往后推一个月日期
            calendar.add(Calendar.MONTH, 1);
            Date date = calendar.getTime();
            months.add(new SimpleDateFormat("yyyy.MM").format(date));
        }
        map.put("months", months);
        List<Integer> memberCount = memberService.findMemberCountByMonths(months);
        map.put("memberCount", memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }

    /**
     * 套餐预约信息饼状图所占比
     *
     * @return
     */

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
// 测试数据
////        "setmealNames":["套餐1", "套餐2", "套餐3"],
////        "setmealCount":[
////        {"name":"套餐1", "value":10},
////        {"name":"套餐2", "value":30},
////        {"name":"套餐3", "value":25}
////        ]
////        },
////        创建list集合用于存储套餐信息
//        HashMap<String, Object> hashMap = new HashMap<>();
//        ArrayList<String> list1 = new ArrayList<>();
//        list1.add("套餐一");
//        list1.add("套餐二");
////        创建第二个list集合，用于存储map类型的数据
//        ArrayList<Map<String, Object>> list2 = new ArrayList<>();
////        创建map几个，用户存储套餐的数量
//        HashMap<String, Object> hashMap1 = new HashMap<>();
//        hashMap1.put("name", "套餐一");
//        hashMap1.put("value", 10);
//
//        HashMap<String, Object> hashMap2 = new HashMap<>();
//        hashMap2.put("name", "套餐二");
//        hashMap2.put("value", 20);
//        list2.add(hashMap1);
//        list2.add(hashMap2);
//
//        hashMap.put("setmealNames", list1);
//        hashMap.put("setmealCount", list2);
//
//        return new Result(true, "success", hashMap);

//        创建map集合，最后将map中的数据返回给前端页面
        HashMap<String, Object> hashMap = new HashMap<>();
//      创建list集合,用于存储查询到的数据中的name字段。
        ArrayList<Object> list = new ArrayList<>();

        List<Map<String, Object>> mapList = setmealService.findSetmealCount();
        for (Map<String, Object> map : mapList) {
            Object name = map.get("name");
//            将name字段加入到list集合中
            list.add(name);
        }
//      将前端所需要的数据加入到map集合中
        hashMap.put("setmealNames", list);
        hashMap.put("setmealCount", mapList);
        return new Result(true, "套餐预约占比查询成功", hashMap);
    }


    /**
     * reportDate:null,
     * todayNewMember :0,
     * totalMember :0,
     * thisWeekNewMember :0,
     * thisMonthNewMember :0,
     * todayOrderNumber :0,
     * todayVisitsNumber :0,
     * thisWeekOrderNumber :0,
     * thisWeekVisitsNumber :0,
     * thisMonthOrderNumber :0,
     * thisMonthVisitsNumber :0,
     * hotSetmeal :[]
     * <p>
     * //         测试数据
     * //        HashMap<String, Object> hashMap = new HashMap<>();
     * //        try {
     * //            hashMap.put("reportDate", DateUtils.parseDate2String(new Date()));
     * //        } catch (Exception e) {
     * //            e.printStackTrace();
     * //        }
     * //        hashMap.put("todayNewMember", 200);
     */


    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        Map<String, Object> result = reportService.getBusinessReport();
        return new Result(true, "success", result);
    }

    //导出运营数据
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> result = reportService.getBusinessReport();
            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            String filePath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
            //基于提供的Excel模板文件在内存中创建一个Excel表格对象
            XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File(filePath)));
            //读取第一个工作表
            XSSFSheet sheet = excel.getSheetAt(0);

            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);//日期

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for (Map map : hotSetmeal) {//热门套餐
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            //使用输出流进行表格下载,基于浏览器作为客户端下载
            OutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");//代表的是Excel文件类型
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");//指定以附件形式进行下载
            excel.write(out);

            out.flush();
            out.close();
            excel.close();
            return null;
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("/exportBusinessReport4PDF")
    public Result exportBusinessReport4PDF(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> result = reportService.getBusinessReport();
            // 取出返回结果数据，准备将报表数据写入到PDF文件中
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");
            System.out.println(hotSetmeal);
            //动态获取模板文件绝对磁盘路径
            String jrxmlPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "health_business3.jrxml";
            String jasperPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "health_business3.jasper";
            //编译模板
            JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
//            填充数据
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, result, new JRBeanCollectionDataSource(hotSetmeal));
            //使用输出流进行表格下载,基于浏览器作为客户端下载
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/pdf");// 代表的是输出的是pdf类型的数据
            response.setHeader("content-Disposition", "attachment;filename=report.pdf");//指定以附件形式进行下载
            //输出文件
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }
}
