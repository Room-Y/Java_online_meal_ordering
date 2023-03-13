package cn.cmr.service.impl;

import cn.cmr.common.BaseContext;
import cn.cmr.common.CustomException;
import cn.cmr.entity.*;
import cn.cmr.mapper.OrderMapper;
import cn.cmr.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author: Java_cmr
 * @Date: 2023/3/9 - 16:50
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    UserService userService;

    @Autowired
    AddressBookService addressBookService;

    @Autowired
    OrderDetailService orderDetailService;

    @Transactional
    @Override
    public void submit(Orders orders) {
        //查询当前用户id
        Long currentId = BaseContext.getCurrentId();

        //查询购物车数据
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        List<ShoppingCart> shoppingList = shoppingCartService.list(queryWrapper);

        if(shoppingList == null || shoppingList.size() == 0){
            throw new CustomException("购物车为空，不能下单");
        }

        //获取订单明细数据
        //原子操作，多线程安全

        long orderId = IdWorker.getId();  //生成订单号

        AtomicInteger amount = new AtomicInteger(0);
        List<OrderDetail> orderDetailList = shoppingList.stream().map(item -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

        //查询用户数据
        User user = userService.getById(currentId);

        //查询用户地址
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if(addressBook == null){
            throw new CustomException("地址信息有误，不能下单");
        }

        List<Orders> list = this.list();
        log.info(String.valueOf(list.size()));

        //向订单表插入数据
        orders.setNumber(String.valueOf(orderId));
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserId(currentId);
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName()
                + addressBook.getCityName() == null ? "" : addressBook.getCityName()
                + addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName()
                + addressBook.getDetail() == null ? "" : addressBook.getDetail());
        log.info(String.valueOf(orders));
        log.info("hhhhhh");
        this.save(orders);

        //像订单明细表插入数据
        orderDetailService.saveBatch(orderDetailList);

        //清空购物车
        shoppingCartService.remove(queryWrapper);
    }
}
