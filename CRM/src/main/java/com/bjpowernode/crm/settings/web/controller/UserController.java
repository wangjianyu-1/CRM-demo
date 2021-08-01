package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    //模板模式
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入用户控制器");

        String path=req.getServletPath();

        if("/settings/user/login.do".equals(path)){
            login(req,resp);

        }
        else if("/settings/user/xxx.do".equals(path)){
            //xxx(req,resp)
        }
        System.out.println("userController结束");

    }

    private void login(HttpServletRequest request, HttpServletResponse response){
        System.out.println("进入到验证登入操作");


        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
//        将密码的明文转为秘文
        loginPwd = MD5Util.getMD5(loginPwd);
//       接受浏览器的Ip地址
        String ip = request.getRemoteAddr();
//        创建service对象

        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());


        try {

            User user = userService.login(loginAct,loginPwd,ip);

            request.getSession().setAttribute("user",user);

            //登入成功
            PrintJson.printJsonFlag(response,true);
            System.out.println("登入成功");

        }catch (Exception e){
            e.printStackTrace();
            String msg = e.getMessage();
            System.out.println(msg);
            //登入失败
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }

    }
}
