package cn.cmr.controller;

import cn.cmr.common.R;
import cn.cmr.dto.SetmealDto;
import cn.cmr.entity.Category;
import cn.cmr.entity.Dish;
import cn.cmr.entity.Setmeal;
import cn.cmr.entity.SetmealDish;
import cn.cmr.service.CategoryService;
import cn.cmr.service.SetmealDishService;
import cn.cmr.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.Name;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Java_cmr
 * @Date: 2023/3/10 - 19:52
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("套餐添加成功");
    }

    @GetMapping("/page")
    public R<Page> list(int page, int pageSize, String name){
        Page<Setmeal> pageInfo = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, queryWrapper);

        //设置套餐分类
        Page<SetmealDto> page1 = new Page<>();
        BeanUtils.copyProperties(pageInfo, page1, "records");

        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> dtoRecords = records.stream().map(item -> {
            SetmealDto sdto = new SetmealDto();
            BeanUtils.copyProperties(item, sdto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            sdto.setCategoryName(category.getName());
            return sdto;
        }).collect(Collectors.toList());

        page1.setRecords(dtoRecords);

        return R.success(page1);
    }


    /**
     * 删除套餐列表
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("套餐删除成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }
}
