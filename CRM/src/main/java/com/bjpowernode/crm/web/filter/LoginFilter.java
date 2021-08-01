package com.bjpowernode.crm.web.filter;

import com.bjpowernode.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("进入到验证是否登录过的过滤器");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String path = request.getServletPath();

        //不应被拦截的请求直接放行
        if("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){
            System.out.println("放行");
            chain.doFilter(req,resp);
        } else {
            System.out.println("不放行");
            //其他页面都要验证
            //user不为空说明登录过
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if(user!=null){
                chain.doFilter(req,resp);
            }else {
                //重定向(转发之后路径会停留在之前的老路径，而不是跳转之后的路径)
                //  crm/login.jsp需要写活
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
        }
    }
}
