package com.yanglei.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.yanglei.content.MessageConstant;
import com.yanglei.entry.Result;
import com.yanglei.mapper.MemberMapper;
import com.yanglei.mapper.OrderMapper;
import com.yanglei.mapper.OrderSettingMapper;
import com.yanglei.pojo.Member;
import com.yanglei.pojo.Order;
import com.yanglei.pojo.OrderInfoVo;
import com.yanglei.pojo.OrderSetting;
import com.yanglei.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderSettingMapper orderSettingMapper;
    @Autowired
    MemberMapper memberMapper;

    @Override
    public Result insertOrder(OrderInfoVo orderInfoVo) {
        Date orderDate = orderInfoVo.getOrderDate();//日期
        String telephone = orderInfoVo.getTelephone();//电话
        String name = orderInfoVo.getName();//姓名
        String idCard = orderInfoVo.getIdCard(); //身份证
        Integer sex = orderInfoVo.getSex(); //性别
        Integer setmealId = orderInfoVo.getSetmealId();//套餐
        String orderType = orderInfoVo.getOrderType();//预约渠道

        //根据用户选择的预约日期查询是否存在预约设置
        OrderSetting orderSetting = orderSettingMapper.queryOrderSettingByDate(orderDate);
        if (orderSetting == null) {
            return new Result(false, "没有档期");
        }

        //判断预约是否已满
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if (reservations >= number) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }

        //根据手机号码查询是否是我们的会员
        Member member = memberMapper.findByTelephone(telephone);
        //如果不是会员，自动注册
        if (member == null) {
            member = new Member();
            member.setName(name);
            member.setPhoneNumber(telephone);
            member.setIdCard(idCard);
            member.setSex(String.valueOf(sex));
            member.setRegTime(new Date());
            memberMapper.add(member);
        }
        //根据会员id，套餐id，预约日期查询是否已经存在预约
        Integer memberId = member.getId();
        Order query = new Order();
        query.setMemberId(memberId);
        query.setSetmealId(setmealId);
        query.setOrderDate(orderDate);
        List<Order> orders = orderMapper.findByCondition(query);
        if (CollectionUtil.isNotEmpty(orders)) {
            return new Result(false, MessageConstant.HAS_ORDERED);
        }
        //创建预约订单
        Order order = new Order();
        order.setOrderDate(orderDate);
        order.setMemberId(memberId);
        order.setSetmealId(setmealId);
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType(orderType);
        orderMapper.add(order);
        int result = orderSettingMapper.updateReservationsVersion(orderSetting.getId(), orderSetting.getVersion());
        //修改已预约人数 + 1
        if (result == 0) {
            throw new RuntimeException("乐观锁生效");
        }
        //返回预约成功
        return new Result(false, MessageConstant.ORDER_SUCCESS);
    }
}
