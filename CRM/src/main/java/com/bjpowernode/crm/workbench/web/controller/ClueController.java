package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueRemark;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入市场活动控制器");

        String path=request.getServletPath();

        if("/workbench/clue/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if("/workbench/clue/save.do".equals(path)){
            save(request,response);
        }else if("/workbench/clue/pageList.do".equals(path)){
            pageList(request,response);
        }
        else if("/workbench/clue/detail.do".equals(path)){
            detail(request,response);
        }else if("/workbench/clue/getAllClueRemark.do".equals(path)){
            getAllClueRemark(request,response);
        }

    }

    private void getAllClueRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入获取ClueRemarkList控制器");

        String clueId = request.getParameter("clueId");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        List<ClueRemark> remarkList = cs.getAllClueRemark(clueId);

        PrintJson.printJsonObj(response,remarkList);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入Clue detail控制器");

        String id = request.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        Clue clue = cs.detail(id);
        request.setAttribute("clue",clue);

        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);


    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入刷新线索页面控制器");

        String pageNo = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        String company = request.getParameter("company");
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String mphone = request.getParameter("mphone");
        String source = request.getParameter("source");
        String state = request.getParameter("state");
        String owner = request.getParameter("owner");

        Integer skipCount =((Integer.parseInt(pageNo)-1)* Integer.parseInt(pageSizeStr));
        Integer pageSize = Integer.parseInt(pageSizeStr);

        Map<String,Object> map = new HashMap<>();

        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        map.put("company",company);
        map.put("fullname",fullname);
        map.put("phone",phone);
        map.put("mphone",mphone);
        map.put("source",source);
        map.put("state",state);
        map.put("owner",owner);


        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        List<Clue> clueList = cs.pageList(map);

        PrintJson.printJsonObj(response,clueList);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入线索保存控制器");

        String id = UUIDUtil.getUUID();
        String fullname =  request.getParameter("fullname");
        String appellation =  request.getParameter("appellation");
        String owner =  request.getParameter("owner");
        String company =  request.getParameter("company");
        String job =  request.getParameter("job");
        String email =  request.getParameter("email");
        String phone =  request.getParameter("phone");
        String website =  request.getParameter("website");
        String mphone =  request.getParameter("mphone");
        String state  =  request.getParameter("state");
        String source =  request.getParameter("source");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String description =  request.getParameter("description");
        String contactSummary =  request.getParameter("contactSummary");
        String nextContactTime =  request.getParameter("nextContactTime");
        String address =  request.getParameter("address");

        Clue clue = new Clue();


        clue.setId(id);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        clue.setNextContactTime(nextContactTime);
        clue.setAddress(address);

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        Boolean flag = cs.save(clue);

        PrintJson.printJsonFlag(response,flag);

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户信息列表操作");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> userList = us.getUserList();

        PrintJson.printJsonObj(response,userList);

    }
}
