package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;

import java.util.List;
import java.util.Map;

public class ClueServiceImpl implements ClueService {
    ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
    ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);

    TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);

    ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);

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

    @Override
    public boolean unbund(String id) {
        boolean flag = true;

        int count = clueActivityRelationDao.unbund(id);

        if(count!=1){
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean bund(String cid, String[] aids) {

        boolean flag = true;
        for(String aid:aids){

            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(aid);
            car.setClueId(cid);

            //??????????????????
            int count =clueActivityRelationDao.bund(car);
            if(count==0){
                flag=false;
            }
        }

        return flag;
    }

    @Override
    public boolean convert(String clueId, Tran t, String createBy) {

        String createTime = DateTimeUtil.getSysTime();

        boolean flag = true;

        // 1)???????????????????????????
        Clue clue = clueDao.getById(clueId);
        // 2)????????????????????????????????????????????????????????????????????????
        String company = clue.getCompany();
        Customer cus = customerDao.getByName(company);
        if(cus==null){
            cus = new Customer();
            cus.setId(UUIDUtil.getUUID());
            cus.setAddress(clue.getAddress());
            cus.setContactSummary(clue.getContactSummary());
            cus.setCreateBy(createBy);
            cus.setCreateTime(createTime);
            cus.setDescription(clue.getDescription());
            cus.setName(company);
            cus.setNextContactTime(clue.getNextContactTime());
            cus.setOwner(clue.getOwner());
            cus.setPhone(clue.getPhone());
            cus.setWebsite(clue.getWebsite());
            //??????customer
            int count1 = customerDao.save(cus);
            if(count1 != 1){
                flag=false;
            }
        }
        // 3)???????????????????????????
        Contacts con = new Contacts();
        con.setId(UUIDUtil.getUUID());
        con.setSource(clue.getSource());
        con.setOwner(clue.getOwner());
        con.setNextContactTime(clue.getNextContactTime());
        con.setMphone(clue.getMphone());
        con.setJob(clue.getJob());
        con.setFullname(clue.getFullname());
        con.setEmail(clue.getEmail());
        con.setDescription(clue.getDescription());
        con.setCustomerId(cus.getId());
        con.setCreateTime(createTime);
        con.setCreateBy(createBy);
        con.setContactSummary(clue.getContactSummary());
        //????????????
        int count2 = contactsDao.save(con);
        if(count2 != 1){
            flag=false;
        }

        //  4)??????????????????????????????????????????????????????
        List<ClueRemark> clueRemarkList = clueRemarkDao.getListByClueId(clueId);
        for (ClueRemark c:clueRemarkList) {
                String noteContent = c.getNoteContent();

                //?????????????????????
                ContactsRemark contactsRemark = new ContactsRemark();
                contactsRemark.setId(UUIDUtil.getUUID());
                contactsRemark.setNoteContent(noteContent);
                contactsRemark.setContactsId(con.getId());
                contactsRemark.setCreateTime(createTime);
                contactsRemark.setCreateBy(createBy);
                contactsRemark.setEditFlag("0");

                int count3 = contactsRemarkDao.save(contactsRemark);
                if(count3 != 1){
                    flag=false;
                }
                //??????????????????
                CustomerRemark customerRemark = new CustomerRemark();
                customerRemark.setId(UUIDUtil.getUUID());
                customerRemark.setNoteContent(noteContent);
                customerRemark.setCustomerId(cus.getId());
                customerRemark.setCreateTime(createTime);
                customerRemark.setCreateBy(createBy);
                customerRemark.setEditFlag("0");

                int count4 = customerRemarkDao.save(customerRemark);
                if(count4 != 1){
                    flag=false;
                }

        }

        //  5???????????????????????????????????????????????????????????????????????????
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getListByClueId(clueId);
        for(ClueActivityRelation clueActivityRelation : clueActivityRelationList){

            String activityId = clueActivityRelation.getActivityId();

            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();

            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(con.getId());

            int count5= contactsActivityRelationDao.save(contactsActivityRelation);
            if(count5 != 1){
                flag=false;
            }
        }

        // 6)???????????????????????????????????????????????????
        if( t != null){

            t.setId(UUIDUtil.getUUID());
            t.setSource(clue.getSource());
            t.setOwner(clue.getOwner());
            t.setNextContactTime(clue.getNextContactTime());
            t.setDescription(clue.getDescription());
            t.setCustomerId(cus.getId());
            t.setContactSummary(clue.getContactSummary());
            t.setContactsId(con.getId());

            int count6 =tranDao.save(t);
            if(count6 != 1){
                flag=false;
            }

            //  7)??????????????????
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setExpectedDate(t.getExpectedDate());
            tranHistory.setMoney(t.getMoney());
            tranHistory.setStage(t.getStage());
            tranHistory.setTranId(t.getId());

            int count7 = tranHistoryDao.save(tranHistory);
            if(count7 != 1){
                flag=false;
            }

        }

       /* // 8)????????????
        for(ClueRemark clueRemark:clueRemarkList){
            int count8 = clueRemarkDao.delete(clueRemark);
            if(count8!=1){
                flag=false;
            }
        }
        //  9)?????????????????????????????????
        for(ClueActivityRelation clueActivityRelation: clueActivityRelationList){
            int count9 = contactsActivityRelationDao.delete(clueActivityRelation);
            if(count9!=1){
                flag=false;
            }
        }

        // 10???????????????
        int count10 = clueDao.delete(clueId);
        if(count10!=1){
            flag=false;
        }*/


        return false;
    }


}
