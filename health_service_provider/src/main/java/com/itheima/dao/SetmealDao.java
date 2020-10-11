package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealDao {


    /**
     * 添加检查套餐的信息
     *
     * @param setmeal
     */
    public void add(Setmeal setmeal);

    /**
     * 设置套餐和检查组多对多关系
     *
     * @param map
     */
    public void setSetmealAndCheckGroup(Map map);

    /**
     * 分页查询
     *
     * @param queryString
     * @return
     */
    public Page<Setmeal> findByCondition(@Param("value") String queryString);


    /**
     * 查询所有的套餐信息
     *
     * @return
     */
    public List<Setmeal> findAll();

    /**
     * 根据id查询套餐信息
     *
     * @param id
     * @return
     */
    public Setmeal findById(int id);

    /**
     * 根据id查询检查套餐的信息
     * @param id
     * @return
     */
    public Setmeal findId(int id);

}
