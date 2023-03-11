package cn.cmr.controller;

import cn.cmr.common.R;
import cn.cmr.dto.DishDto;
import cn.cmr.entity.Category;
import cn.cmr.entity.Dish;
import cn.cmr.entity.User;
import cn.cmr.service.CategoryService;
import cn.cmr.service.DishFlavorService;
import cn.cmr.service.DishService;
import cn.cmr.service.UserService;
import cn.cmr.utils.SMSUtils;
import cn.cmr.utils.ValidateCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: Java_cmr
 * @Date: 2023/3/9 - 21:48
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();

        //生成随机的验证码
        if(Strings.isNotEmpty(phone)){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();

            //调用阿里云提供的短信服务api完成发送短信
            SMSUtils.sendMessage("roomcmr的验证码", "SMS_273820140", phone, code);
            log.info("code={}", code);

            //将生成的验证码保存到session
            session.setAttribute(phone, code);

            return R.success("手机验证码发送成功");
        }
        return R.error("验证码发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());

        //获取手机号 验证码
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();

        //获取session中保存的验证码
        Object codeInSession = session.getAttribute(phone);

        //验证码正确
//        if(codeInSession != null && codeInSession.equals(code)){
        if(true){
            //是否需要添加为新用户
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);

            User user = userService.getOne(queryWrapper);
            if(user == null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }

            session.setAttribute("user", user.getId());
            return R.success(user);
        }
        return R.error("验证码错误");
    }

    @PostMapping("/loginout")
    public R<String> loginout(HttpSession session){
        session.removeAttribute("user");
        return R.success("退出成功");
    }
}
