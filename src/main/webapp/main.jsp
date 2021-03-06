<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>集成办公信息系统主界面</title>
<link href="http://blog.java1234.com/favicon.ico" rel="SHORTCUT ICON">
<%
	// 权限验证
	if(session.getAttribute("currentUser")==null){
		response.sendRedirect("index.jsp");
		return;
	}
%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function(){
		// 数据
		var treeData=[{
			text:"系统模块",
			children:[{
				text:"用户管理",
				children:[{
					text:"用户信息管理",
					attributes:{
						url:"userInfoManage.jsp"
					}
				},{
					text:"班级信息管理",
					attributes:{
						url:"gradeInfoManage.jsp"
					}
				},{
					text:"学生信息管理",
					attributes:{
						url:"studentInfoManage.jsp"
					}
				},{
					text:"图片列表",
					attributes:{
						url:"jsp/picture_list.jsp"
					}
				}]
			},{
				text:"人员管理",
				children:[{
					text:"ECharts图表",
					attributes:{
						url:"echarts.jsp"
					}
				},{
					text:"人员详情",
					attributes:{
						url:"jsp/people_detail.jsp"
					}
				},{
					text:"人员列表",
					attributes:{
						url:"jsp/people_list.jsp"
					}
				}]
			},{
				text:"报表管理",
				children:[
				    {
						text:"ECharts图表",
						attributes:{
							url:"echarts.jsp"
						},
					}
				]
			},{
				text:"接口管理",
				children:[{
					text:"ECharts图表",
					attributes:{
						url:"echarts.jsp"
					}
				}]
			},{
				text:"文件管理",
				children:[{
					text:"大文件上传",
					attributes:{
						url:"jsp/file/bigFileUpload.jsp"
					},
				},{
					text:"大文件上传(旧)",
					attributes:{
						url:"jsp/file/bigFileUploadOld.jsp"
					},
				},{
					text:"单文件上传",
					attributes:{
						url:"jsp/file/singleUpload.jsp"
					},
				}]
			}]
		}];
		
		// 实例化树菜单
		$("#tree").tree({
			data:treeData,
			lines:true,
			onClick:function(node){
				if(node.attributes){
					openTab(node.text,node.attributes.url);
				}
			}
		});
		
		// 新增Tab
		function openTab(text,url){
			if($("#layout_center_tabs").tabs('exists',text)){
				$("#layout_center_tabs").tabs('select',text);
			}else{
				var content="<iframe frameborder='0' scrolling='auto' style='width:100%;height:100%' src="+url+"></iframe>";
				$("#layout_center_tabs").tabs('add',{
					title:text,
					closable:true,
					content:content
				});
			}
		}
		
	});
</script>
<script type="text/javascript">
    $(function () {
        $("#refresh").text("刷新");
        $("#close").text("关闭");
        $("#closeother").text("关闭其他");
        $("#closeall").text("关闭所有");
    });
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/center.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js" charset="utf-8"></script>
</head>
<body class="easyui-layout">
	<div region="north" style="height: 80px;background-color: #E0EDFF">
		<div align="left" style="width: 80%;float: left"><img src="images/main.jpg"></div>
		<div style="padding-top: 50px;padding-right: 20px;">当前用户：&nbsp;<font color="red" >${currentUser.username }</font></div>
		
	</div>
	<div region="center">
		<div class="easyui-tabs" fit="true" border="false" id="layout_center_tabs">
			<div title="首页" >
				<div align="center" style="padding-top: 100px;"><font color="red" size="10">欢迎使用</font></div>
			</div>
		</div>
		<div id="layout_center_tabsMenu">
			<div type="refresh" id="refresh"></div>
			<div class="menu-sep"></div>
			<div type="close" id="close"></div>
			<div type="closeOther" id="closeother"></div>
			<div type="closeAll" id="closeall"></div>
		</div>
	</div>
	<div region="west" style="width: 150px;" title="导航菜单" split="true">
		<ul id="tree" checkbox="false"></ul>
	</div>
	<div region="south" style="height: 25px;" align="center">版权所有<a href="#">YCKJ091</a></div>
</body>
</html>