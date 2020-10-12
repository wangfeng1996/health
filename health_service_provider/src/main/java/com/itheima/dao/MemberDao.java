package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangfeng
 */
public interface MemberDao {
    /**
     * 查询所有的会员信息
     *
     * @return
     */
    public List<Member> findAll();

    /**
     * 根据根据条件查询
     *
     * @param queryString
     * @return
     */
    public Page<Member> selectByCondition(@Param("queryString") String queryString);

    /**
     * 添加会员信息
     *
     * @param member
     */
    public void add(Member member);

    /**
     * 根据id删除会员信息
     *
     * @param id
     */
    public void deleteById(Integer id);

    /**
     * 根据id查询会员信息
     *
     * @param id
     * @return
     */
    public Member findById(Integer id);

    /**
     * 根据电话查询会员信息
     *
     * @param telephone
     * @return
     */
    public Member findByTelephone(String telephone);

    /**
     * 修改会员信息
     *
     * @param member
     */
    public void edit(Member member);

    /**
     * 根据日期统计会员数，统计指定日期之前的会员数
     *
     * @param date
     * @return
     */
    public Integer findMemberCountBeforeDate(String date);

    /**
     * 根据日期统计会员数
     *
     * @param date
     * @return
     */
    public Integer findMemberCountByDate(String date);

    /**
     * 根据日期统计会员数，统计指定日期之后的会员数
     *
     * @param date
     * @return
     */
    public Integer findMemberCountAfterDate(String date);

    /**
     * 总会员数
     *
     * @return
     */
    public Integer findMemberTotalCount();
}
