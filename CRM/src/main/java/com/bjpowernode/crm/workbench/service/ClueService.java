package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueRemark;

import java.util.List;
import java.util.Map;

public interface ClueService {
    Boolean save(Clue c);

    List<Clue> pageList(Map<String, Object> map);

    Clue detail(String id);

    List<ClueRemark> getAllClueRemark(String id);
}
