package com.atguigu.meinian.service;

import java.util.List;

public interface MemberService {

    /**
     * 添加会员
     *
     * @param telephone
     */
    void addMember(String telephone) throws Exception;

    /**
     * 根据月份查询所有会员
     *
     * @param monthsList
     * @return
     */
    List<Integer> findMemberCountByMonth(List<String> monthsList);
}
