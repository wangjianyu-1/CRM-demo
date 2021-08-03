<%--
  Created by IntelliJ IDEA.
  User: 爱吃羊的大灰狼（嗷呜---）
  Date: 2021/7/23
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
</head>
<body>

$(".time").datetimepicker({
minView: "month",
language:  'zh-CN',
format: 'yyyy-mm-dd',
autoclose: true,
todayBtn: true,
pickerPosition: "bottom-left"
});


        $.ajax({
            url:"",
            data:{},
            type:"",
            dataType:"json",
            success:function (data) {

            }
        })

        //走后台，获取数据库中可选的所有者
        $.ajax({
        url:"/workbench/activity/getUserList.do",
        data:{},
        type:"get",
        dataType:"json",
        success:function (data) {
        //data: list<User> uList
            //[{用户1}，{用户2}...]

            var  html = "<option></option>"
            $.each(data,function (index,element) {
            html += "<option value='"+element.id+"'>"+element.name+"</option>"
            })
            $("#create-Owner").html(html)

            }

            })

            创建人创建时间
            String createTime = DateTimeUtil.getSysTime();
            String createBy = ((User)request.getSession().getAttribute("user")).getName();
</body>
</html>
