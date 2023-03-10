package cn.cmr.service.impl;

import cn.cmr.common.CustomException;
import cn.cmr.entity.Category;
import cn.cmr.entity.Dish;
import cn.cmr.entity.Setmeal;
import cn.cmr.mapper.CategoryMapper;
import cn.cmr.service.CategoryService;
import cn.cmr.service.DishService;
import cn.cmr.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Java_cmr
 * @Date: 2023/3/9 - 16:50
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    /**
     * 根据id删除分类，删除之前判断是否关联菜品或者套餐
     * @param id
     */
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    @Override
    public void remove(Long id) {
        //判断菜品种类
        Category item = super.getById(id);

        if(item.getType() == 1){
            LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();

            //添加查询条件
            dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);

            int count = dishService.count(dishLambdaQueryWrapper);
            if(count > 0){
                throw new CustomException("当前分类下关联了菜品，不能删除");
            }
        }
        else{
            LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
            int count = setmealService.count(setmealLambdaQueryWrapper);
            if(count > 0){
                throw new CustomException("当前分类下关联了套餐，不能删除");
            }
        }
        super.removeById(id);
    }
}
