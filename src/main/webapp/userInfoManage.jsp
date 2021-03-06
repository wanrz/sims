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
	var url;
	
	function searchUser(){
		$('#dg').datagrid('load',{
			username:$('#s_username').val()
		});
	}
	
	function refreshUser(){
		$('#s_username').val('');
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
		var value=$("#picture").val();
		if(value!=""){
			$("#previewImg_user_edit").attr("src","${pageContext.request.contextPath}/user/getFile?fileUrl="+value+"");
		}
	}
	
	function closeUserDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	function resetValue(){
		$("#id").val("");
		$("#username").val("");
		$("#password").val("");
		$("#previewImg_user_edit").attr("src","${pageContext.request.contextPath}/images/upload.png");
		$("#user_feature_file").val("");
	}
	
	
	function saveUser(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
				debugger;
				if(result.errorMsg){
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
	
	// 定义到jQuery全局变量下-文件下载   ajax一般是用来请求服务端的数据，下载文件需要先从服务器获取文件请求路径，然后使用form表单提交的方法来实现文件的下载。
	jQuery.download = function (url, method, fileUrl) {
	  jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' +  // action请求路径及推送方法
	              '<input type="text" name="fileUrl" value="' + fileUrl + '"/>' + // 文件路径
	          '</form>')
	  .appendTo('body').submit().remove();
	};
	
	function downloadPicture(index){
		$('#dg').datagrid('selectRow', index);
        var row = $('#dg').datagrid('getSelected');
        if (row.picture != null && row.picture !== undefined)
        $.download('${pageContext.request.contextPath}/file/download', 'post', row.picture); // 下载文件
	}
	
	function showPicture(index){
		$('#dg').datagrid('selectRow', index);
        var row = $('#dg').datagrid('getSelected');
        if (row.picture != null && row.picture !== undefined)
        $("#dd").dialog("open").dialog("setTitle","头像信息");
        $('#showimg').attr("src", "${pageContext.request.contextPath}/user/getFile?fileUrl="+row.picture);
	}
	
	function closePicture(){
        $("#dd").dialog("close");
	}
	
	function showImg(value, row, index){
		if(typeof value == "undefined" || value == null || value == ""){
			return "<span>无刷脸登录账号</span>";
		}else{
			return "<a href='#' onclick='showPicture("+index+")'><img style='width:100px;height:100px;' border='1' src='${pageContext.request.contextPath}/user/getFile?fileUrl="+value+"'/></a>";
		}
	}
	
	 //转义
    function rowformater(value, row, index) {
    	var action = $("#operAction").html();
    	action = action.replace(/{index}/g,index);
    	return action;
		 
//         return "<div><a href='#' class='easyui-linkbutton' iconCls='icon-download' onclick='downloadPicture(" + index + ")'>下载</a>&nbsp;"
//         +"<a href='#' onclick='showHsCode(" + index + ")'>显示路径</a></div>";          
    }
	 
    function showHsCode(index) {
        $('#dg').datagrid('selectRow', index);
        var row = $('#dg').datagrid('getSelected');
        if (row.picture != null && row.picture !== undefined)
        	$.messager.alert("提示",row.picture);
    }
    
    function print(){
    	$("#user-box").jqprint();
    	alert(1)
    }

</script>
</head>
<body style="margin: 5px;">
	<table id="dg" title="用户信息" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="${pageContext.request.contextPath}/user/userList" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="id" width="10">编号</th>
				<th data-options="field:'picture',width:10, formatter:showImg">头像</th>
				<th field="username" width="30">用户名称</th>
				<th field="password" width="30">用户密码</th>
				<th data-options="field:'Id',width:20,formatter:rowformater,onClickRow:showHsCode">操作</th>
			</tr>
		</thead>
	</table>
	<div id="operAction">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-download"  plain="true" onclick="downloadPicture('{index}');" resId="104109102">下载</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-system"  plain="true" onclick="showHsCode('{index}');" resId="104109103">显示路径</a>
	</div>
	
	<div id="tb">
		<div>
			<a href="javascript:openUserAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:openUserModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteUser()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript:downloadPicture(1)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载测试</a>
			<input type ='button' value='普通打印' onclick='javascript:window.print()' />	
			<a href="javascript:CreateFormPage('打印测试', $('#dg'))" class="easyui-linkbutton" iconCls="icon-print" plain="true">打印</a>
			<a href="javascript:print()" class="easyui-linkbutton" iconCls="icon-print" plain="true">新打印</a>

<!-- 			<object id="wb" classid="ClSID:8856F961-340A-11D0-A96B-00C04Fd705A2" width="0" height="0"></object>　　 -->
<!-- 	        <input type ='button' value='打印' onclick='javascript:wb.ExecWB(6,1)'/> -->
<!-- 	        <input type ='button' value='打印预览' onclick='javascript:wb.ExecWB(7,1)'/> -->
<!-- 	        <input type ='button' value='页面设置' onclick='javascript:wb.ExecWB(8,1)'/> -->
		</div>
		<div>&nbsp;用户名称：&nbsp;<input type="text" name="s_username" id="s_username"/>
		<a href="javascript:searchUser()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		<a href="javascript:refreshUser()" class="easyui-linkbutton" iconCls="icon-refresh" plain="true">重置</a>
		</div>
	</div>
	
	<div id="dd" class="easyui-dialog" style="width:1000px;height:500px;" closed="true" title="My Dialog" ondblclick="closePicture()" iconCls="icon-ok">
	    <img id="showimg" style="width:100%;" src="${pageContext.request.contextPath}/user/getFile?fileUrl="+url+"' />
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width: 400px;height: 280px;padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table>
				<tr>
					<td><input type="hidden" name="id" id="id"/></td>
					<td><input type="hidden" id="user_feature_file" name="user_feature_file" value="" /></td>
					<td><input type="hidden" id="picture" name="picture" value="" /></td>
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