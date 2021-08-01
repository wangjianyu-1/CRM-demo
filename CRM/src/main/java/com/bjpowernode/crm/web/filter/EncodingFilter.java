package com.bjpowernode.crm.web.filter;


import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入编码问题过滤器");

        //过滤post请求中文乱码
        servletRequest.setCharacterEncoding("utf-8");
        //过滤相应流中文乱码
        servletResponse.setContentType("text/html;charset=utf-8");
        //放行
        filterChain.doFilter(servletRequest,servletResponse);

    }

}
