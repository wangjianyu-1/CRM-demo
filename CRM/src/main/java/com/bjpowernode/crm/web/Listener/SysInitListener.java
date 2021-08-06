package com.bjpowernode.crm.web.Listener;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import com.bjpowernode.crm.settings.service.impl.DicServiceImpl;
import com.bjpowernode.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("开始初始化数据字典");
        ServletContext application = sce.getServletContext();

        DicService ds = (DicService) ServiceFactory.getService(new DicServiceImpl());

        Map<String, List<DicValue>> map = ds.getAllDic();

        Set<String> set = map.keySet();
        for(String s:set){

            application.setAttribute(s,map.get(s));
        }
        System.out.println("初始化数据字典结束");

        //------------------------------------------------------------

        System.out.println("加载可能性键值对");

        Map<String,String> pMap = new HashMap<>();
        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");

        Enumeration<String> e = rb.getKeys();

        while (e.hasMoreElements()){
            String key = e.nextElement();
            String val = rb.getString(key);
            pMap.put(key,val);
        }
        application.setAttribute("pMap",pMap);

    }
}
