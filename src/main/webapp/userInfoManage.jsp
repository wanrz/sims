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
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-plugin/jquery.cookie.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-plugin/jquery.form.js" charset="utf-8"></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-plugin/jquery_image_upload.js" charset="utf-8"></script> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/fileupload/jquery.fileupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/fileupload/jquery.fileupload-validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/fileupload/jquery.xdr-transport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js" charset="utf-8"></script>
<style>
	.image-list span {
		position: absolute;
		top: 43px;
		padding: 2px 5px;
		background: no-repeat,rgba(163, 133, 220, 1);
		/**IE8下不支持rgba  用IE的filter **/
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
	var url;
	
	function searchUser(){
		$('#dg').datagrid('load',{
			username:$('#s_username').val()
		});
	}
	
	function deleteUser(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert("系统提示","请选择要删除的数据！");
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].id);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确认要删掉这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("${pageContext.request.contextPath}/user/userDelete",{delIds:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","您已成功删除<font color=red>"+result.total+"</font>条数据！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert('系统提示','<font color=red>'+selectedRows[result.errorIndex].username+'</font>'+result.errorMsg);
					}
				},"json");
			}
		});
	}
	
	
	function openUserAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加用户信息");
		url="${pageContext.request.contextPath}/user/userSave";
	}
	
	function openUserModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑用户信息");
		$("#fm").form("load",row);
		url="${pageContext.request.contextPath}/user/userUpdate";
	}
	
	function closeUserDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	function resetValue(){
		debugger;
		$("#id").val("");
		$("#username").val("");
		$("#password").val("");
// 		$("#user_feature_file_edit").val("");
	}
	
	
	function saveUser(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
				if(result.message){
					$.messager.alert("系统提示",result.errorMsg);
					return;
				}else{
					$.messager.alert("系统提示","保存成功");
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				}
			}
		});
	}
	
	function showImg(value, row, index){
		if(typeof value == "undefined" || value == null || value == ""){
			return "<span>无刷脸登录账号</span>";
		}else{
			return "<img style='width:100px;height:100px;' border='1' src='${pageContext.request.contextPath}/user/getFile?fileUrl="+value+"'/>";
		}
	}
	
	/**
	 * 上传现场照片
	 */
	function imgFirstOnLoad() {
	    personAddVerifyImg("#user_feature_file_edit", "#previewImg_user_edit", "#userAdd_closeImg_first");
	}
	
	/**
	 * 校验图片
	 *
	 * @param uploadId
	 * @param showId
	 * @param closeId
	 */
	function personAddVerifyImg(uploadId, showId, closeId) {  
	    $(closeId).css({'display': 'block'});
	}
	
	/**
	 * 取消现场照片
	 */
	function imgFirstOnClock() {
	    personAddImgOnClock("#user_feature_file_edit", "#previewImg_user_edit", "#userAdd_closeImg_first");
	}

	/**
	 * 取消照片
	 *
	 * @param showId
	 * @param closeId
	 */
	function personAddImgOnClock(uploadId, showId, closeId) {
	    $(uploadId).val("");
	    // 还原上传原始图片
	    $(showId).attr("src", "${pageContext.request.contextPath}/images/upload.png");
	    // 隐藏取消按钮
	    $(closeId).css({'display': 'none'});
	}
	
	function uploadImage(){
		//初始化上传文件
		$('#user_feature_file_edit').fileupload({
	        dataType: 'json',  
	        add: function (e, data) {
	        	debugger;
	        	//验证图片类型
	        	if(!checkFileType(data.files[0].name)){
	        		return;
	        	}
	            data.submit();
	        },
	        done: function (e, result) {
//	        	{"code":"","data":{},"message":"图片大小不能超过1024KB","success":false}
	        	if (!result.result.success) {
	        		$("#previewImg_user_edit").attr("src","${pageContext.request.contextPath}/images/upload.png");
	        		$("#user_feature_file").val("");
	        		$.messager.alert("警告", result.result.message,"error");
	        		clearUploadStyle($("#user_eidt_upload_div"),$("#previewImg_user_edit"));
	        	} else {
	        		var imgData = result.result.data.image;
	        		var image = "data:image/png;base64,"+imgData;
	        		$("#previewImg_user_edit").attr("src",image);
	        		$("#user_feature_file").val(imgData);
	        	}
	        }
	    });
		$("#user_feature_file_edit").uploadPreviewVideo({ Img: "previewImg_user_edit", Width: 100, Height: 100 });

	}
	
	$(function() {
		$(document).ready(function(){
			var value=$("#picture").val();
			if(value!=""){
				$("#previewImg_user_edit").attr("src","${pageContext.request.contextPath}/user/getFile?fileUrl="+value+"");
			}
		});
		
		//初始化上传文件
		$('#user_feature_file_edit').fileupload({
	        dataType: 'json',  
	        add: function (e, data) {
	        	//验证图片类型
	        	if(!checkFileType(data.files[0].name)){
	        		return;
	        	}
	            data.submit();
	        },
	        done: function (e, result) {
//	        	{"code":"","data":{},"message":"图片大小不能超过1024KB","success":false}
	        	if (!result.result.success) {
	        		$("#previewImg_user_edit").attr("src","${pageContext.request.contextPath}/images/upload.png");
	        		$("#user_feature_file").val("");
	        		$.messager.alert("警告", result.result.message,"error");
	        		clearUploadStyle($("#user_eidt_upload_div"),$("#previewImg_user_edit"));
	        	} else {
	        		var imgData = result.result.data.image;
	        		var image = "data:image/png;base64,"+imgData;
	        		$("#previewImg_user_edit").attr("src",image);
	        		$("#user_feature_file").val(imgData);
	        	}
	        }
	    });
		$("#user_feature_file_edit").uploadPreviewVideo({ Img: "previewImg_user_edit", Width: 100, Height: 100 });

	});
</script>
</head>
<body style="margin: 5px;">
	<table id="dg" title="用户信息" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="${pageContext.request.contextPath}/user/userList" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="id" width="50">编号</th>
				<th field="username" width="100">用户名称</th>
				<th field="password" width="250">用户密码</th>
				<th data-options="field:'picture',width:110, formatter:showImg" >头像</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>
			<a href="javascript:openUserAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:openUserModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteUser()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>&nbsp;用户名称：&nbsp;<input type="text" name="s_username" id="s_username"/><a href="javascript:searchUser()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a></div>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width: 400px;height: 280px;padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table>
				<tr>
					<td><input type="hidden" name="id" id="id"/></td>
					<td><input type="hidden" id="user_feature_file" name="user_feature_file" value="" /></td>
				</tr>
				<tr id = "img_feature_field">
<!-- 					<th align="right" > -->
<!-- 					<label style="-webkit-writing-mode：vertical-rl;writing-mode:lr-tb ;" >图像</label> -->
<!-- 					</th> -->
					<td valign="top">图像：</td>
					<td align="left" colspan="3">
			             <div class="image-list">
						     <div style="width:100px;height:100px;" id="user_eidt_upload_div">
								<input type="file" id="user_feature_file_edit" class="fileClass" accept="image/bmp,image/jpeg,image/png,image/gif,video/mp4,video/rmvb,video/avi" 
								 data-url="${pageContext.request.contextPath}/user/uploadImage" title=" "/>
				             	<img src="${pageContext.request.contextPath}/images/upload.png" style="width:100px;height:100px;" id="previewImg_user_edit" onload="imgFirstOnLoad()"/>
								<i class="closeImg" id="userAdd_closeImg_first" >
		                            <img src="${pageContext.request.contextPath}/images/close.png" class="closeImg-img" onclick="imgFirstOnClock()"/>
		                        </i>
			             	</div>		
			             </div>		             
					</td>
				</tr>
				<tr>
					<td>用户名称：</td>
					<td><input type="text" name="username" id="username" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td valign="top">用户密码：</td>
					<td><input type="password" name="password" id=""password"" class="easyui-validatebox" required="true"/></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveUser()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeUserDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>