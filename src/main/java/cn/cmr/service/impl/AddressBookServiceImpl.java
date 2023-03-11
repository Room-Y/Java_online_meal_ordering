package cn.cmr.service.impl;

import cn.cmr.common.CustomException;
import cn.cmr.dto.SetmealDto;
import cn.cmr.entity.AddressBook;
import cn.cmr.entity.Setmeal;
import cn.cmr.entity.SetmealDish;
import cn.cmr.mapper.AddressBookMapper;
import cn.cmr.mapper.SetmealMapper;
import cn.cmr.service.AddressBookService;
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
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
