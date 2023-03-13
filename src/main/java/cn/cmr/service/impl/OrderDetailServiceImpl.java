package cn.cmr.service.impl;

import cn.cmr.common.BaseContext;
import cn.cmr.common.CustomException;
import cn.cmr.entity.*;
import cn.cmr.mapper.OrderDetailMapper;
import cn.cmr.mapper.OrderMapper;
import cn.cmr.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
