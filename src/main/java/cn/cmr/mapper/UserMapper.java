package cn.cmr.mapper;

import cn.cmr.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Java_cmr
 * @Date: 2023/3/11 - 15:05
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
