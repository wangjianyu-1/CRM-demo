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
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="ECharts/echarts.min.js"></script>
    <script type="text/javascript">

        $(function () {

            $.ajax({
                url:"workbench/transaction/getTranChart.do",
                type:"get",
                dataType:"json",
                success:function (data) {

                    // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('main'));

                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '交易信息图',
                            subtext: '纯属虚构',
                            left: 'center'
                        },
                        tooltip: {
                            trigger: 'item'
                        },
                        legend: {
                            orient: 'vertical',
                            left: 'left',
                        },
                        series: [
                            {
                                name: '访问来源',
                                type: 'pie',
                                radius: '50%',
                                data: data.dataList,
                            /*[
                                    {value: 1048, name: '搜索引擎'},
                                    {value: 735, name: '直接访问'},
                                    {value: 580, name: '邮件营销'},
                                    {value: 484, name: '联盟广告'},
                                    {value: 300, name: '视频广告'}
                                ],*/
                                emphasis: {
                                    itemStyle: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            })





        })

    </script>

</head>
<body>


<div id="main" style="width: 600px;height:400px;"></div>

</body>
</html>