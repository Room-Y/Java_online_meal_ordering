package cn.cmr.controller;

import cn.cmr.common.R;
import cn.cmr.entity.Employee;
import cn.cmr.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request ,@RequestBody Employee employee){
        // 设置初始密码123456，需要md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));


        //使用自动填充技术维护update insert TableField
//        // 设置创建时间，更新时间
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        // 获得当前登录用户id，设置创建人，更新人
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    /**
     * 分页查询
     * @return
     */
    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name){
        //构造分页构造器
        Page pageinfo = new Page(page, pageSize);

        //构造条件构造器,name为空则不生成条件
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(Strings.isNotEmpty(name), Employee::getName, name);

        //按照更新时间排序
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //查询并返回
        employeeService.page(pageinfo, queryWrapper);
        return R.success(pageinfo);
    }

    /**
     * 根据id修改员工信息
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        if("admin".equals(employeeService.getById(employee.getId()).getUsername()) && employee.getStatus() == 0){
            return R.error("admin用户不可被禁用");
        }
        log.info("线程id为 {}", Thread.currentThread().getId());
        //重新设置最近更新时间和更新人
        //使用自动填充技术维护update insert TableField
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser((long)request.getSession().getAttribute("employee"));
        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        return R.success(employeeService.getById(id));
    }
}
