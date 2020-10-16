package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.SetmealDao;
import com.itheima.service.ReportService;
import com.itheima.service.SetmealService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangfeng
 * 回显表格中的数据
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private SetmealDao setmealDao;

    @Override
    public Map<String, Object> getBusinessReport() {
//     * reportDate:null,
//     * todayNewMember :0,
//     * totalMember :0,
//     * thisWeekNewMember :0,
//     * thisMonthNewMember :0,
//     * todayOrderNumber :0,
//     * todayVisitsNumber :0,
//     * thisWeekOrderNumber :0,
//     * thisWeekVisitsNumber :0,
//     * thisMonthOrderNumber :0,
//     * thisMonthVisitsNumber :0,
//     * hotSetmeal :[]

//        创建map集合，用于回显数据
        HashMap<String, Object> hashMap = new HashMap<>();

        try {
            Date day = DateUtils.getToday();
            String today = DateUtils.parseDate2String(day);


//            获取本周第一天日期,
            Date dayOfWeek1 = DateUtils.getFirstDayOfWeek(new Date());
            String FistWeekDay = DateUtils.parseDate2String(dayOfWeek1);
//            获取本周最后天日期,
            Date dayOfWeek2 = DateUtils.getLastDayOfWeek(new Date());
            String LastWeekDay = DateUtils.parseDate2String(dayOfWeek2);


//            获取一个月第一天时间
            Date firstDay4ThisMonth = DateUtils.getFirstDay4ThisMonth();
            String FirstOfDay = DateUtils.parseDate2String(firstDay4ThisMonth);
//            获取一个月最后一天时间
            Date lastDay4ThisMonth = DateUtils.getLastDay4ThisMonth();
            String lastOfDay = DateUtils.parseDate2String(lastDay4ThisMonth);


//            当天日期
            hashMap.put("reportDate", today);
            //  统计当日会员增加数量;
            Integer todayNewMember = memberDao.findMemberCountByDate(today);
            hashMap.put("todayNewMember", todayNewMember);

//            统计会员总数
            Integer totalMember = memberDao.findMemberTotalCount();
            hashMap.put("totalMember", totalMember);

//          统计本周新增会员数
            Integer memberCountByWeek = memberDao.findMemberCountByDates(FistWeekDay, LastWeekDay);
            hashMap.put("thisWeekNewMember", memberCountByWeek);


//            统计本月新增会员数
            Integer memberCountByMonth = memberDao.findMemberCountByDates(FirstOfDay, lastOfDay);
            hashMap.put("thisMonthNewMember", memberCountByMonth);


//            获取本日预约人数
            Integer todayOrderNumber = orderDao.findOrderCountByDate(today);
            hashMap.put("todayOrderNumber", todayOrderNumber);
//            获取今日到诊数字
            Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);
            hashMap.put("todayVisitsNumber", todayVisitsNumber);

//            获取本周预约数
            Integer thisWeekOrderNumber = orderDao.findThisWeekOrderNumber(FistWeekDay, LastWeekDay);
            hashMap.put("thisWeekOrderNumber", thisWeekOrderNumber);
//            本周到诊人数
            Integer thisWeekVisitsNumber = orderDao.findThisWeekVisitsOrderNumber(FirstOfDay, LastWeekDay);
            hashMap.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
            //            获取本月预约数
            Integer thisMonthOrderNumber = orderDao.findThisWeekOrderNumber(FistWeekDay, LastWeekDay);
            hashMap.put("thisMonthOrderNumber", thisMonthOrderNumber);
//            本月到诊人数
            Integer thisMonthVisitsNumber = orderDao.findThisWeekVisitsOrderNumber(FirstOfDay, LastWeekDay);
            hashMap.put("thisMonthVisitsNumber", thisMonthVisitsNumber);

            // 获取热门套餐          hotSetmeal :[]
            List<Map> hotSetmeal = orderDao.findHotSetmeal();
            hashMap.put("hotSetmeal", hotSetmeal);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}
