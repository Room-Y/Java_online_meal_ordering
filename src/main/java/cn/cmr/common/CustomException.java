package cn.cmr.common;

/**
 * @author: Java_cmr
 * @Date: 2023/3/9 - 19:31
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
