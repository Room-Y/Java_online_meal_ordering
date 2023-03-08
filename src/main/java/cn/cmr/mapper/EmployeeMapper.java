package cn.cmr.mapper;

import cn.cmr.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Java_cmr
 * @Date: 2023/3/8 - 16:57
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
