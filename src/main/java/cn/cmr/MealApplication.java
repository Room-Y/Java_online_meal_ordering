package cn.cmr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: Java_cmr
 * @Date: 2023/3/8 - 16:27
 */

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class MealApplication {
    public static void main(String[] args) {
        SpringApplication.run(MealApplication.class, args);
        log.info("运行成功");
    }
}
