package cn.cmr.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author: Java_cmr
 * @Date: 2023/3/9 - 11:16
 */

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandle(SQLIntegrityConstraintViolationException ex){
//        System.out.println(ex);
//        java.sql.SQLIntegrityConstraintViolationException: Duplicate entry 'sysysy' for key 'employee.idx_username'
        if(ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");
            return R.error(split[2].substring(1, split[2].length()-1) + "已存在");
        }
        return R.error("未知错误");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandle(CustomException ex){
        return R.error(ex.getMessage());
    }
}
