package cn.cmr.service.impl;

import cn.cmr.common.CustomException;
import cn.cmr.dto.SetmealDto;
import cn.cmr.entity.Category;
import cn.cmr.entity.Setmeal;
import cn.cmr.entity.SetmealDish;
import cn.cmr.mapper.CategoryMapper;
import cn.cmr.mapper.SetmealMapper;
import cn.cmr.service.CategoryService;
import cn.cmr.service.SetmealDishService;
import cn.cmr.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Java_cmr
 * @Date: 2023/3/9 - 16:50
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐，同时保存套餐和菜品的关系
     * @param setmealDto
     */
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐
        this.save(setmealDto);

        //保存套餐菜品关系
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map(item -> {
           item.setSetmealId(setmealDto.getId());
           return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐和菜品的关联数据
     */
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //查询套餐状态是否被删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);

        if(this.count(queryWrapper) > 0){
            throw new CustomException("有套餐正在售卖中，不能删除");
        }

        //删除套餐
        this.removeByIds(ids);

        //删除关系表的数据
        LambdaQueryWrapper<SetmealDish> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(queryWrapper1);

        return;
    }
}
