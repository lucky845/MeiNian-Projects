package com.atguigu.meinian.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.meinian.mapper.MemberMapper;
import com.atguigu.meinian.pojo.Member;
import com.atguigu.meinian.service.MemberService;
import com.atguigu.meinian.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 添加会员
     *
     * @param telephone
     */
    @Override
    public void addMember(String telephone) throws Exception {
        // 根据手机号查询会员信息
        Member member = memberMapper.getMemberByTelephone(telephone);
        // 判断member是否为空
        if (member == null) {
            // 注册为会员
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberMapper.addMember(member);
        }
    }

    /**
     * 根据月份获取会员数
     *
     * @return
     */
    @Override
    public List<Integer> findMemberCountByMonth(List<String> monthsList) {
        List memberCountList = new ArrayList();
        for (String months : monthsList) {
            // 获取指定月份的最后一天
            String regTime = DateUtils.getLastDayOfMonth(months);
            // 迭代过去12个月,获取每个月的会员数
            Integer memeberCount = memberMapper.findMemberCountBeforeDate(regTime);
            memberCountList.add(memeberCount);
        }
        return memberCountList;
    }
}
