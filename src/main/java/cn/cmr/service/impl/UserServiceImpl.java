package cn.cmr.service.impl;

import cn.cmr.entity.User;
import cn.cmr.mapper.UserMapper;
import cn.cmr.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: Java_cmr
 * @Date: 2023/3/11 - 15:06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
