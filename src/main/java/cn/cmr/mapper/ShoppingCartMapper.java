package cn.cmr.mapper;

import cn.cmr.entity.Dish;
import cn.cmr.entity.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Java_cmr
 * @Date: 2023/3/9 - 19:18
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
