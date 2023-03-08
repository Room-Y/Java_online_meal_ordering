package cn.cmr.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Java_cmr
 * @Date: 2023/3/8 - 19:38
 */
@Data
public class R<T> {
    /**
     * 结果编码。1成功，其他数字失败
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 结果数据
     */
    private T data;

    /**
     * 动态数据，cookie等
     */
    private Map map = new HashMap();

    /**
     * 响应成功返回
     * @param object
     * @return
     * @param <T>
     */
    public static <T> R<T> success(T object){
        R<T> r = new R<>();
        r.data = object;
        r.code = 1;
        return r;
    }

    /**
     * 响应失败返回
     * @param msg
     * @return
     * @param <T>
     */
    public static <T> R<T> error(String msg){
        R<T> r = new R<>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    /**
     * 操作map
     * @param key
     * @param value
     * @return
     */
    public R<T> add(String key, Object value){
        this.map.put(key, value);
        return this;
    }
}
