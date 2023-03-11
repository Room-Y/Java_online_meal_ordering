package cn.cmr.service;

import cn.cmr.dto.SetmealDto;
import cn.cmr.entity.Category;
import cn.cmr.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author: Java_cmr
 * @Date: 2023/3/9 - 16:49
 */
public interface SetmealService extends IService<Setmeal> {

    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);
}
