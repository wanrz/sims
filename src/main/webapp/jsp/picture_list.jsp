<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户信息管理</title>
<link href="http://blog.java1234.com/favicon.ico" rel="SHORTCUT ICON">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-plugin/jquery_image_upload.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-plugin/jquery.cookie.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-plugin/jquery.form.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/fileupload/jquery.fileupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/fileupload/jquery.fileupload-validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/fileupload/jquery.xdr-transport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/print.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js" charset="utf-8"></script>

<style>
	.image-list span {
		position: absolute;
		top: 43px;
		padding: 2px 5px;
		background: no-repeat,rgba(163, 133, 220, 1);
		/**IE8下不支持rgba   用IE的filter **/
		filter: progid:DXImageTransform.Microsoft.gradient(startcolorstr=#A385DC,endcolorstr=#A385DC);
		color: white;
		cursor: pointer;
	}
	.image-list > div {
		display:inline-block;
		background-size:cover;
		width:100px;
		height:100px;
		cursor: pointer;
	}
	.image-list > img {
		cursor: pointer;
	}
	.fileClass{position:absolute; margin:0px; opacity:0;filter:alpha(opacity:0); z-index:999;
		width:100px;
		height:100px;
	}
	
	.closeImg{
        position: absolute;
        top: 30px;
    	right: 194px;
        width: 20px;
        height: 20px;
        cursor: pointer;
        display: none;
    }
    .closeImg-img{
        width: 20px;
        height: 20px;
    }
</style>
<script type="text/javascript">
	function searchUser(){
		$('#dg').datagrid('load',{
		});
	}
	
	function showImg(value, row, index){
		if(typeof value == "undefined" || value == null || value == ""){
			return "<span>无刷脸登录账号</span>";
		}else{
			return "<img style='width:100%;height:100%;' border='1' src='${pageContext.request.contextPath}/user/getFile?fileUrl="+value+"'/>";
		}
	}
	
	$(function() {
		searchUser();
	});
	
</script>
</head>
<body style="margin: 5px;">
	<table id="dg" title="用户信息" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="false" url="${pageContext.request.contextPath}/user/userList" fit="true" >
		<thead>
			<tr>
				<th data-options="field:'picture',width:10, formatter:showImg"></th>
			</tr>
		</thead>
	</table>
</body>
</html>