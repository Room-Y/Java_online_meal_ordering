package cn.cmr.fliter;

import cn.cmr.common.R;
import com.alibaba.fastjson.JSON;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Java_cmr
 * @Date: 2023/3/8 - 21:01
 */
@WebFilter(filterName = "loginCheckFliter", urlPatterns = "/*")
public class LoginCheckFliter implements Filter {
    //定义匹配器和需要放行的页面
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    public static String[] urls = new String[]{
            "/employee/login",
            "/employee/logout",
            "/backend/**",
            "/front/**"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse rp = (HttpServletResponse) servletResponse;
        HttpServletRequest rq =  (HttpServletRequest) servletRequest;
        System.out.println("拦截到请求！" + rq.getRequestURI());

        // 验证uri是否符合放行规则
        String requestURI = rq.getRequestURI();
        boolean check = check(requestURI);

        // uri符合放行规则  或者  用户已登录 则放行
        if(check || (rq.getSession().getAttribute("employee") != null)){
            filterChain.doFilter(rq, rp);
            return;
        }

        // 未登录
        // 通过输出流，向客户端页面响应数据
        rp.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * 匹配路径是否需要放行
     * @param requestURI
     * @return
     */
    public boolean check(String requestURI){
        for(String url : urls){
            if(PATH_MATCHER.match(url, requestURI)){
                return true;
            }
        }
        return false;
    }
}
