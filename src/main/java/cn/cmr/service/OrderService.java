package cn.cmr.service;

import cn.cmr.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: Java_cmr
 * @Date: 2023/3/9 - 16:49
 */
public interface OrderService extends IService<Orders> {
    public void submit(Orders orders);
}
