package com.atguigu.meinian.mapper;

import com.atguigu.meinian.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface MemberMapper {

    /**
     * 根据手机号查询会员信息
     *
     * @param telephone
     * @return
     * @throws Exception
     */
    Member getMemberByTelephone(@Param("telephone") String telephone) throws Exception;

    /**
     * 添加会员
     *
     * @param member
     * @throws Exception
     */
    void addMember(@Param("member") Member member) throws Exception;

    /**
     * 根据注册时间获取注册会员数
     *
     * @param regTime
     * @return
     */
    Integer findMemberCountBeforeDate(@Param("regTime") String regTime);

    /**
     * 获取当日新增会员数
     *
     * @param regTime
     * @return
     */
    Integer getTodayNewMember(@Param("regTime") Date regTime);

    /**
     * 获取总会员数
     *
     * @return
     */
    Integer getTotalMember();

    /**
     * 获取本周本月新增会员数
     *
     * @param date
     * @return
     */
    Integer getThisWeekAndMonthNewMember(@Param("date") Date date);
}
