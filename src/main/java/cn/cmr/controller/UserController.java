package cn.cmr.controller;

import cn.cmr.common.R;
import cn.cmr.dto.DishDto;
import cn.cmr.entity.Category;
import cn.cmr.entity.Dish;
import cn.cmr.service.CategoryService;
import cn.cmr.service.DishFlavorService;
import cn.cmr.service.DishService;
import cn.cmr.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Java_cmr
 * @Date: 2023/3/9 - 21:48
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

}
