package cn.cmr.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Java_cmr
 * @Date: 2023/3/8 - 16:50
 */
@Data
public class Employee {
    /**
     * 版本控制号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识 id
     */
    private Long id;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 登录时用户名
     */
    private String username;

    /**
     * 登陆密码，会加密
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别
     */
    private String sex;

    /**
     * ？？？
     */
    private String idNumber;

    /**
     * 用户是否被限制
     */
    private Integer status;

    /**
     * 该用户创建的时间
     */
    private LocalDateTime createTime;

    /**
     * 该用户最近一次更新的时间
     */
    private LocalDateTime updateTime;

    /**
     * ？？？
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * ？？？
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updateUser;
}
