package cn.cmr.service;

import cn.cmr.dto.DishDto;
import cn.cmr.entity.Category;
import cn.cmr.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: Java_cmr
 * @Date: 2023/3/9 - 16:49
 */
public interface DishService extends IService<Dish> {

    //新增菜品，同时插入菜品和对应的口味数据
    public void saveWithFlavor(DishDto dishDto);

    //根据id获取菜品和口味
    public DishDto getByIdWithFlavor(Long id);

    //修改菜品
    public void updateWithFlavor(DishDto dishDto);
}
