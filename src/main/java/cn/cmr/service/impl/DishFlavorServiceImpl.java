package cn.cmr.service.impl;

import cn.cmr.entity.Dish;
import cn.cmr.entity.DishFlavor;
import cn.cmr.mapper.DishFlavorMapper;
import cn.cmr.mapper.DishMapper;
import cn.cmr.service.DishFlavorService;
import cn.cmr.service.DishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: Java_cmr
 * @Date: 2023/3/9 - 16:50
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
