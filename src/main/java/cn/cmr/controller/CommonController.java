package cn.cmr.controller;

/**
 * @author: Java_cmr
 * @Date: 2023/3/9 - 20:48
 */

import cn.cmr.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * 主要进行文件的上传和下载
 */

@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${picture.path}")
    private String basePath;


    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        //防止覆盖，需要重新生成文件名
        String originFilename = file.getOriginalFilename();
        String suffix = originFilename.substring(originFilename.lastIndexOf('.'));
        String filename = UUID.randomUUID().toString() + suffix;

        //创建目录对象，目录不存在则创建base目录
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        //存储上传文件
        try {
            file.transferTo(new File(basePath + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(filename);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            //输出流，通过输出流，将文件写回浏览器，在浏览器显示图片
            ServletOutputStream outputStream = response.getOutputStream();

            int len = 0;
            byte[] bytes = new byte[1024];
            while((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0, len);
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
