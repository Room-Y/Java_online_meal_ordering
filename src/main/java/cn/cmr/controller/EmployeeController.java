package cn.cmr.controller;

import cn.cmr.common.R;
import cn.cmr.entity.Employee;
import cn.cmr.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Java_cmr
 * @Date: 2023/3/8 - 19:34
 */

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录，request获取是为了给当前登录设置状态，@RequestBody获取 name 和 password
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        // 获取密码并加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 根据用户名查询数据库获取employee数据（用户名唯一 unique）
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        // 验证是否查询到该用户
        if(emp == null){
            return R.error("用户不存在，查询失败");
        }

        // 验证密码是否正确
        if(!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }

        // 验证员工是否被禁用
        if(emp.getStatus() == 0){
            return R.error("您已被限制登陆权限");
        }

        //当前页面设置employee信息
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //移除employee信息
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
}
