package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ClueDao;
import com.bjpowernode.crm.workbench.dao.ClueRemarkDao;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueRemark;
import com.bjpowernode.crm.workbench.service.ClueService;

import java.util.List;
import java.util.Map;

public class ClueServiceImpl implements ClueService {
    ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);

    @Override
    public Boolean save(Clue c) {

        boolean flag = true;
        int count = clueDao.save(c);

        if(count != 1){
            flag =false;
        }

        return flag;
    }

    @Override
    public List<Clue> pageList(Map<String, Object> map) {

        List<Clue> clueList = clueDao.pageList(map);

        return clueList;
    }

    @Override
    public Clue detail(String id) {

        Clue clue = clueDao.detail(id);

        return clue;
    }

    @Override
    public List<ClueRemark> getAllClueRemark(String id) {

        List<ClueRemark> remark = clueRemarkDao.getAllClueRemark(id);

        return remark;
    }
}
