package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    //从属性文件中读取要生成的html对应的目录
    @Value(value = "${out_put_path}")
    private String outPutPath;
//    private String outPutPath = "/Users/wangfeng/Workspaces/lntellij IDEA/health_parent/health_mobile/src/main/webapp/pages";

    /**
     * 添加
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        this.setSetmealAndCheckGroup(setmealId, checkgroupIds);
        //将图片名称保存到Redis集合中
        String fileName = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, fileName);
        //当添加套餐后需要重新生成静态页面（套餐列表页面、套餐详情页面）
        generateMobileStaticHtml();


    }

    /**
     * 分页查询
     *
     * @param queryPageBean 封装查询条件  包含当前页和每页的条数
     * @return
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 查询所有的检查套餐信息
     *
     * @return
     */
    @Override
    public List<Setmeal> findAll() {
        List<Setmeal> all = setmealDao.findAll();

        return all;
    }

    /**
     * 根据id查询所有套餐下面所有的检查组，以及检查组所对应的检查项的详细信息
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    @Override
    public Setmeal findId(int id) {
        return setmealDao.findId(id);
    }

    /**
     * 设置套餐和检查组多对多关联关系
     *
     * @param setmealId
     * @param checkgroupIds
     */

    public void setSetmealAndCheckGroup(Integer setmealId, Integer[] checkgroupIds) {
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("setmealId", setmealId);
                map.put("checkgroupId", checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }

    //生成当前方法所需的静态页面
    public void generateMobileStaticHtml() {
        //在生成静态页面之前需要查询数据
        List<Setmeal> list = setmealDao.findAll();

        //需要生成套餐列表静态页面
        generateMobileSetmealListHtml(list);

        //需要生成套餐详情静态页面
        generateMobileSetmealDetailHtml(list);
    }

    //生成套餐列表静态页面
    public void generateMobileSetmealListHtml(List<Setmeal> list) {
        Map map = new HashMap();
        //为模板提供数据，用于生成静态页面
        map.put("setmealList", list);
        generateHtml("mobile_setmeal.ftl", "m_setmeal.html", map);
    }

    //生成套餐详情静态页面（可能有多个）
    public void generateMobileSetmealDetailHtml(List<Setmeal> list) {
//        一个页面里面有多个套餐，每个套餐一个页面
        for (Setmeal setmeal : list) {
            System.out.println(setmeal);
            Map map = new HashMap();
            map.put("setmeal", setmealDao.findById(setmeal.getId()));
            generateHtml("mobile_setmeal_detail.ftl", "setmeal_detail_" + setmeal.getId() + ".html", map);
        }
    }


    /**
     * 用于生成静态的html
     *
     * @param templateName 需要的模板
     * @param htmlPageName 生成出来的html的name
     * @param map          所需要的数据
     */
    public void generateHtml(String templateName, String htmlPageName, Map map) {
//  获得配置对象
        Configuration configuration = freeMarkerConfigurer.getConfiguration();

        try {
            //        获取模板对象
            Template template = configuration.getTemplate(templateName);
//            指定静态html的输出路径
            FileWriter out = new FileWriter(new File(outPutPath + "/" + htmlPageName));
//            将文件输出
            template.process(map, out);
//          释放资源
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
