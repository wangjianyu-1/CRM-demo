package com.bjpowernode.crm.settings.domain;

public class User {

    /*关于登入
     User user = select * from tbl_user where loginAct = ? and loginPwd= ？
     if(user==null) 密码错误
     else  说明账号密码错误
        需要继续向下验证其他信息
            验证失效时间、锁定状态、浏览器段ip

    */
    private String id;      //id
    private String loginAct;    //登入账号
    private String name;        //用户真是姓名
    private String loginPwd;    //登入密码
    private String email;       //邮箱
    private String expireTime;  //失效时间 19位
    private String lockState;   //锁定状态 0锁定 1启用
    private String deptno;      //部门编号
    private String allowIps;    //允许访问的ip地址
    private String createTime;  //创建时间  19位
    private String createBy;    //创建人
    private String editTime;    //修改时间  19位
    private String editBy;      //修改人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginAct() {
        return loginAct;
    }

    public void setLoginAct(String loginAct) {
        this.loginAct = loginAct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getAllowIps() {
        return allowIps;
    }

    public void setAllowIps(String allowIps) {
        this.allowIps = allowIps;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }
}
