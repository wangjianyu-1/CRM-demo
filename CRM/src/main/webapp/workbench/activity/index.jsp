<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

	<link href="jquery/jquery.datetimepicker.test/jquery.datetimepicker.min.css" type="text/css" rel="stylesheet" />
	<link href="jquery/bs_pagination/jquery.bs_pagination.min.css">


<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<!--日期拾取器-->
	<script type="text/javascript" src="jquery/jquery.datetimepicker.test/jquery.datetimepicker.min.js"></script>
	<script type="text/javascript" src="jquery/jquery.datetimepicker.test/jquery.datetimepicker.full.min.js"></script>
	<!--pagination-->

	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>


	<script type="text/javascript">

	$(function(){


		//为更新按钮绑事件
		$("#updateBtn").click(function () {


			//向后台发起修改Activity的请求
			var id ="${sessionScope.user.id}"
			$.ajax({
				url:"workbench/activity/update.do",
				data:{
					"id":$.trim($("#hidden-id").val()),
					"owner":$.trim($("#edit-owner").val()),
					"name":$.trim($("#edit-name").val()),
					"startDate":$.trim($("#edit-startDate").val()),
					"endDate":$.trim($("#edit-endDate").val()),
					"cost":$.trim($("#edit-cost").val()),
					"description":$.trim($("#edit-description").val())

				},
				type:"post",
				dataType:"json",
				success:function (data) {
					//data:"success"-boolean
					if(data.success){

						//添加成功后需要刷新市场活动列表


						alert("修改成功")
						pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
								,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));


						//利用reset清空表单中的值
						//注意jquery对象的reset没有，必须将jquery对象转换成dom对象之后才能试用reset()

						//关闭修改操作的模态窗口
						$("#editActivityModal").modal("hide");

					}else {
						alert("修改市场活动失败")
					}

				}
			})

		})

		//为修改按钮绑定事件，打开修改按钮模态窗口
		$("#editBtn").click(function () {

				var $xz = $("input[name=xz]:checked");

				if($xz.length == 0 ){
					alert("请选择需要修改的记录");
				}else if($xz.length > 1){
					alert("只能选择一条记录进行修改");
				}else {

					var id = $xz.val();
					//向后台发起请求
					$.ajax({
						url:"workbench/activity/getUserListAndActivity.do",
						data:{
							"id":id
						},
						type:"get",
						dataType:"json",
						success:function (data) {

							/*	data:
                                    {"uList":[{用户1},{2},{3}] , "a":{市场活动}}
                             */
							var html = "<option></option>";
							$.each(data.uList,function (i,n) {
								html+= "<option value='"+n.id+"'>"+n.name+"</option>"

							});

							$("#edit-owner").html(html);


							//处理单挑activity
							$("#hidden-id").val(data.a.id);
							$("#edit-name").val(data.a.name);
							$("#edit-startDate").val(data.a.startDate);
							$("#edit-endDate").val(data.a.endDate);
							$("#edit-cost").val(data.a.cost);
							$("#edit-description").val(data.a.description);

							$("#edit-owner").val(data.a.owner);



							//所有值都修改后。打开修改操作的模态窗口
							$("#editActivityModal").modal("show");
						}

					})


				}



		})

		//为删除按钮绑定事件，执行市场活动删除操作
		$("#deleteBtn").click(function () {

			//找到全部复选框中，选中的复选框jquery对象
			var $xz = $("input[name=xz]:checked");

			if( $xz.length == 0){
				alert("请选择需要删除的记录");
			}else{

				if(confirm("确定清除选定的记录吗？")){

					//当同一个key下多个value时，需要用传统的方式传递参数
					//url workbench/activity/delete.do?id=xxx&id=xxx&id=xxx

					//拼接参数
					var param=""
					for(i=0; i<$xz.length; i++){
						param+= "id="+$xz[i].value

						//如果不是最后一个元素，需要添加&
						if(i < $xz.length-1)
							param+="&"
					}

					//向后台发起删除操作
					$.ajax({
						url:"workbench/activity/delete.do",
						data:param,
						type:"post",
						dataType:"json",
						success:function (data) {
							//data:{success:boolean}

							if(data.success){

								pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

							}else{

								alert("删除市场活动失败")
							}
						}

					})

				}

			}


		})

		//为保存按钮绑定一个操作，执行添加操作
		$("#saveBtn").click(function () {
			//向后台发起添加Activity的请求
			$.ajax({
				url:"workbench/activity/save.do",
				data:{

					"owner":$.trim($("#create-owner").val()),
					"name":$.trim($("#create-name").val()),
					"startDate":$.trim($("#create-startDate").val()),
					"endDate":$.trim($("#create-endDate").val()),
					"cost":$.trim($("#create-cost").val()),
					"description":$.trim($("#create-description").val())

				},
				type:"post",
				dataType:"json",
				success:function (data) {
					//data:"success"-boolean
					if(data.success){

						//添加成功后需要刷新市场活动列表

						//关闭添加操作的模态窗口
						alert("添加成功")

						//利用reset清空表单中的值
						//注意jquery对象的reset没有，必须将jquery对象转换成dom对象之后才能试用reset()
						$("#ActivityAddForm")[0].reset();
						$("#createActivityModal").modal("hide");

					}else {
						alert("添加市场活动失败")
					}
					//pageList(1,2);

					pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

				}
			})

		})


		//为创建按钮绑定事件，打开模块窗口
		$("#addBtn").click(function () {

			//自己的时间控件
			$('.time').datetimepicker({
				timepicker:false,
				format:'Y-m-d'
			});
			$.datetimepicker.setLocale('zh');


			/*//添加时间控件
			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});*/



			/*操作模态窗口的方式：
			模态窗口jquery对象，调用modal，传递参数：  show打开窗口   hide关闭窗口
			*/

			//发起获取UserList的请求
			$.ajax({
				url:"workbench/activity/getUserList.do",
				data:{},
				type:"get",
				dataType:"json",
				success:function (data) {
					//data: list<User> uList
					//[{用户1}，{用户2}...]

					var  html = ""
					$.each(data,function (index,element) {
						html += "<option value='"+element.id+"'>"+element.name+"</option>"
					})
					$("#create-owner").html(html)
					//将当前用户设置为下拉框默认选项
					var id ="${sessionScope.user.id}"
					$("#create-owner").val(id);
				}

			})

			//所有者下拉处理结束后，展示模态窗口
			$("#createActivityModal").modal("show");

		})

		//为查询按钮绑定事件，触发pageList方法
		$("#searchBtn").click(function () {

			//每次在查询前，将查询的条件储存在隐藏域中
			$("#hidden-name").val($("#search-name").val())
			$("#hidden-owner").val($("#search-owner").val())
			$("#hidden-startDate").val($("#search-startDate").val())
			$("#hidden-endDate").val($("#search-endDate").val())

			pageList(1,2)
		})

		//页面加载完毕后触发一个方法pageList();
		pageList(1,2);

		//为全选复选框绑定事件，触发全选操作
		$("#qx").click(function () {

			$("input[name=xz]").prop("checked",this.checked)

		})

		/*
			动态生成的元素，我们不能以普通的绑定事件形式来进行操作
				只能用on方式的形式来触发事件

				$(需要绑定元素的有效的外层元素).on(绑定事件的方式，需要绑定的元素的jquery对象，回调函数)
		*/

		//全选复选框问题
		$("#activityBody").on("click",$("input[name=xz]"), function () {
			if($("input[name=xz]").length == $("input[name=xz]:checked").length){

				$("#qx").prop("checked",true)
			}else {
				$("#qx").prop("checked",false)
			}

		})



	});

	/* 哪些地方需要加pageList（）
		1）点击左侧的市场活动超链接，刷新市场活动列表
		2）添加、修改、删除后，需要刷新市场活动列表
		3）电视查询按钮时
		4）点击分页组件按钮时
	**/
	function pageList(pageNo,pageSize) {

		//将全选复选框取消选择
		$("#qx").prop("checked",false);

		//查询前将查询框储存的查询条件取出
		$("#search-name").val($("#hidden-name").val());
		$("#search-owner").val($("#hidden-owner").val());
		$("#search-startDate").val($("#hidden-startDate").val());
		$("#search-endDate").val($("#hidden-endDate").val());


		$.ajax({
			url:"workbench/activity/pageList.do",
			data:{
				"pageNo":pageNo,
				"pageSize":pageSize,
				"name":$.trim($("#search-name").val()),
				"owner":$.trim($("#search-owner").val()),
				"startDate":$.trim($("#search-startDate").val()),
				"endDate":$.trim($("#search-endDate").val())
			},
			type:"get",
			dataType:"json",
			success:function (data) {

				//data:   [{activity1},{activity2},{activity3}...]
				//分页插件需要的总记录数   {”total“:int，"dataList":[{activity1},{activity2},{activity3}...]}

				//查询结果拼接到activity列表中
				var html =""
				$.each(data.dataList,function (n,e) {
					html+='<tr class="active">'
					html+='<td><input type="checkbox" name="xz" value="'+e.id+'"/></td>'
					html+='<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id='+e.id+'\'">'+e.name+'</a></td>';
					html+='<td>'+e.owner+'</td>';
					html+='<td>'+e.startDate+'</td>';
					html+='<td>'+e.endDate+'</td>';
					html+='</tr>'
				})

				$("#activityBody").html(html)

				//计算总页数totalPages
				var totalPages = data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1

				//数据处理完之后，结合分页查询，对前端展示分页信息
				$("#activityPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				})

			}

		})
	}
	
</script>
</head>
<body>
	<!--隐藏域-->
	<input type="hidden" id="hidden-name">
	<input type="hidden" id="hidden-owner">
	<input type="hidden" id="hidden-startDate">
	<input type="hidden" id="hidden-endDate">


	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" id="ActivityAddForm" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner" >

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endDate">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">

						<input type="hidden" id="hidden-id">

					
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">

								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name" >
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startDate" >
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endDate" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="search-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="search-endDate">
				    </div>
				  </div>
				  
				  <button type="button" id="searchBtn" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
					<!--
						data-toggle="modal"  表示触发改按钮将会打开一个模块窗口
						data-target="#createActivityModal"    表示打开哪个模块窗口
						使用属性值打开模块窗口，无法对按钮功能扩充
					-->
				  <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">

				<div id="activityPage"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>