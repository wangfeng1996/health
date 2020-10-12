package com.itheima.service;

import com.itheima.pojo.Member;

/**
 * @author  wangfeng
 */
public interface MemberService {
    //根据手机号查询会员
    public Member findByTelephone(String telephone);

    //    添加会员
    public void add(Member member);
}
