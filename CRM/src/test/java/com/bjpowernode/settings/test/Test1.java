package com.bjpowernode.settings.test;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test1 {
    public static void main(String[] args) {

        UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        Map<String,Object> map = new HashMap<>();
        map.put("loginAct","zs");
        map.put("loginPwd","202cb962ac59075b964b07152d234b70");

        User user = userDao.login(map);
        System.out.println(user.getName());

    }
}
