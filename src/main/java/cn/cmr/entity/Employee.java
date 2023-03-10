package cn.cmr.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: Java_cmr
 * @Date: 2023/3/8 - 16:50
 */
@Data
public class Employee implements Serializable {
    /**
     * 版本控制号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键 id
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
     * 身份证号
     */
    private String idNumber;

    /**
     * 用户是否被限制
     */
    private Integer status;

    /**
     * 该用户创建的时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 该用户信息最近一次更新的时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 该用户创建人，插入时填充字段
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 最近一次更新该用户信息的人，更新时填充字段
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
