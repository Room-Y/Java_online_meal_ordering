package cn.cmr.service;

import cn.cmr.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: Java_cmr
 * @Date: 2023/3/9 - 16:49
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
