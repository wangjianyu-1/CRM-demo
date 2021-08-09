package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.Map;

public interface TranService {

    boolean save(Tran t, String customerName);

    Tran getById(String id);


    Map<String, Object> getTranChart();
}
