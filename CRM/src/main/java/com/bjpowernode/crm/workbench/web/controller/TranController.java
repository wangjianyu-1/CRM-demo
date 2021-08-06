package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranService;
import com.bjpowernode.crm.workbench.service.impl.ClueServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.CustomerServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TranController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入交易活动控制器");

        String path = request.getServletPath();

        if ("/workbench/transaction/add.do".equals(path)) {
            add(request, response);
        }else if ("/workbench/transaction/getCustomerName.do".equals(path)) {
            getCustomerName(request, response);
        }else if ("/workbench/transaction/save.do".equals(path)) {
            save(request, response);
        }
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("执行创建交易操作");

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");
        String customerName = request.getParameter("customerName");
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String activityId = request.getParameter("activityId");
        String contactsId  = request.getParameter("contactsId");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");

        Tran t = new Tran();

        t.setId(id);
        t.setContactsId(contactsId);
        t.setContactSummary(contactSummary);
        t.setDescription(description);
        t.setNextContactTime(nextContactTime);
        t.setOwner(owner);
        t.setSource(source);
        t.setCreateBy(createBy);
        t.setCreateTime(createTime);
        t.setActivityId(activityId);
        t.setStage(stage);
        t.setExpectedDate(expectedDate);
        t.setName(name);
        t.setMoney(money);
        t.setType(type);

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        boolean flag = ts.save(t,customerName);

        if(flag){
            response.sendRedirect(request.getContextPath()+"/workbench/transaction/index.jsp");
        }


    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行自动补全的模糊查询获取客户列表，返回String[]");

        String name = request.getParameter("name");

        CustomerService cs = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        List<String> names = cs.getCustomerName(name);

        System.out.println(names);

        PrintJson.printJsonObj(response,names);
    }

    private void add(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException  {
        System.out.println("执行获取添加用户信息操作");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        request.setAttribute("uList",uList);

        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);
    }

}
