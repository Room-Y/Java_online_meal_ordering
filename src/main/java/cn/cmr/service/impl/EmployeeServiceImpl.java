package cn.cmr.service.impl;

import cn.cmr.entity.Employee;
import cn.cmr.mapper.EmployeeMapper;
import cn.cmr.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: Java_cmr
 * @Date: 2023/3/8 - 16:59
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
