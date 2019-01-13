<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">  
<meta http-equiv="cache-control" content="no-cache">  
<meta http-equiv="expires" content="0">    
<title>人员信息管理</title>
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
        top: 34px;
    	right: 380px;
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
	
	function searchPeople(){
		$('#dg').datagrid('load',{
			name:$('#s_name').val()
		});
	}
	
	function refreshPeople(){
		$('#s_name').val('');
		$('#dg').datagrid('load',{
			name:$('#s_name').val()
		});
	}
	
	function deletePeople(){
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
				$.post("${pageContext.request.contextPath}/people/peopleDelete",{delIds:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","您已成功删除<font color=red>"+result.total+"</font>条数据！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert('系统提示','<font color=red>'+selectedRows[result.errorIndex].peoplename+'</font>'+result.errorMsg);
					}
				},"json");
			}
		});
	}
	
	function openPeopleAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加人员信息");
		url="${pageContext.request.contextPath}/people/peopleSave";
	}
	
	function openPeopleModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑人员信息");
		$("#fm").form("load",row);
		url="${pageContext.request.contextPath}/people/peopleUpdate";
		var value=$("#picture").val();
		if(value!=""){
			$("#previewImg_people_edit").attr("src","${pageContext.request.contextPath}/people/getFile?fileUrl="+value+"");
		}
	}
	
	function closePeopleDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	function resetValue(){
		$("#id").val("");
		$("#peoplename").val("");
		$("#password").val("");
		$("#previewImg_people_edit").attr("src","${pageContext.request.contextPath}/images/upload.png");
		$("#people_feature_file").val("");
	}
	
	
	function savePeople(){
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
	    personAddVerifyImg("#people_feature_file_edit", "#previewImg_people_edit", "#peopleAdd_closeImg_first");
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
	    personAddImgOnClock("#people_feature_file_edit", "#previewImg_people_edit", "#peopleAdd_closeImg_first");
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
		$('#people_feature_file_edit').fileupload({
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
	        		$("#previewImg_people_edit").attr("src","${pageContext.request.contextPath}/images/upload.png");
	        		$("#people_feature_file").val("");
	        		$.messager.alert("警告", result.result.message,"error");
	        		clearUploadStyle($("#people_eidt_upload_div"),$("#previewImg_people_edit"));
	        	} else {
	        		var imgData = result.result.data.image;
	        		var image = "data:image/png;base64,"+imgData;
	        		$("#previewImg_people_edit").attr("src",image);
	        		$("#people_feature_file").val(imgData);
	        	}
	        }
	    });
		$("#people_feature_file_edit").uploadPreviewVideo({ Img: "previewImg_people_edit", Width: 100, Height: 100 });

	}
	
	$(function() {
		$('#people_feature_file_edit').fileupload({
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
	        		$("#previewImg_people_edit").attr("src","${pageContext.request.contextPath}/images/upload.png");
	        		$("#people_feature_file").val("");
	        		$.messager.alert("警告", result.result.message,"error");
	        		clearUploadStyle($("#people_eidt_upload_div"),$("#previewImg_people_edit"));
	        	} else {
	        		var imgData = result.result.data.image;
	        		var image = "data:image/png;base64,"+imgData;
	        		$("#previewImg_people_edit").attr("src",image);
	        		$("#people_feature_file").val(imgData);
	        	}
	        }
	    });
		$("#people_feature_file_edit").uploadPreviewVideo({ Img: "previewImg_people_edit", Width: 100, Height: 100 });
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
        $('#showimg').attr("src", "${pageContext.request.contextPath}/people/getFile?fileUrl="+row.picture);
	}
	
	function closePicture(){
        $("#dd").dialog("close");
	}
	
	function showImg(value, row, index){
		if(typeof value == "undefined" || value == null || value == ""){
			return "<span>无刷脸登录账号</span>";
		}else{
			return "<a href='#' onclick='showPicture("+index+")'><img style='width:100px;height:100px;' border='1' src='${pageContext.request.contextPath}/people/getFile?fileUrl="+value+"'/></a>";
		}
	}
	
	 //转义
    function rowformater(value, row, index) {
    	var action = $("#operAction").html();
    	action = action.replace(/{index}/g,index);
    	return action;
    }
	 
    function showDetail(index){
    	$('#dg').datagrid('selectRow', index);
        var row = $('#dg').datagrid('getSelected');
        var url="${pageContext.request.contextPath}/people/detail?id="+row.id;
        openTopNewTabCloseTab(row.name+"-图片详情",url);
    }
    
    function singleUpload(index) {
        $('#dg').datagrid('selectRow', index);
        var row = $('#dg').datagrid('getSelected');
        $('#peopleId').val(row.id);	
        $("#dlgSingle").dialog("open").dialog("setTitle","单文件上传图片");
		url="${pageContext.request.contextPath}/upload/upload";
    }
    
    function batchUpload(index){
    	$('#dg').datagrid('selectRow', index);
        var row = $('#dg').datagrid('getSelected');
        debugger;
        var url="${pageContext.request.contextPath}/bigfile/bigFileUpload?id="+row.id;
        openTopNewTabCloseTab("批量上传",url);
    }
    
    function saveSingleUpload(){
    	debugger;
		$("#upload_form").form("submit",{
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
					$.messager.alert("系统提示","上传成功");
					resetUploadValue();
					$("#dlgSingle").dialog("close");
					$("#dg").datagrid("reload");
				}
			}
		});
	}
    
	function closeSingleUploadDialog(){
		$("#dlgSingle").dialog("close");
		resetUploadValue();
	}
	
	function resetUploadValue(){
		$("#uploadFile").val("");
	}

</script>
</head>
<body style="margin: 5px;">
	<table id="dg" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="${pageContext.request.contextPath}/people/peopleList" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="name">名称</th>
				<th field="title">标题</th>
				<th data-options="field:'picture',width:10, formatter:showImg">头像</th>
				<th field="state">状态</th>
				<th field="total">图片数量</th>
				<th field="remark">描述</th>
				<th field="createTime">创建时间</th>
				<th field="updateTime">更新时间</th>
				<th data-options="field:'Id',width:20,formatter:rowformater">操作</th>
			</tr>
		</thead>
	</table>
	<div id="operAction">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-download"  plain="true" onclick="downloadPicture('{index}');" resId="104109102">下载</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-system"  plain="true" onclick="singleUpload('{index}');" resId="104109103">单文件上传</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-system"  plain="true" onclick="batchUpload('{index}');" resId="104109103">批量上传</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-writeblog"  plain="true" onclick="showDetail('{index}');" resId="104109103">详情</a>
	</div>
	
	<div id="tb">
		<div>
			<a href="javascript:openPeopleAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:openPeopleModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deletePeople()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>&nbsp;名称：&nbsp;<input type="text" name="s_name" id="s_name"/>
		<a href="javascript:searchPeople()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		<a href="javascript:refreshPeople()" class="easyui-linkbutton" iconCls="icon-refresh" plain="true">重置</a>
		</div>
	</div>
	
	<div id="dd" class="easyui-dialog" style="width:1000px;height:500px;" closed="true" title="My Dialog" ondblclick="closePicture()" iconCls="icon-ok">
	    <img id="showimg" style="width:100%;" src="${pageContext.request.contextPath}/people/getFile?fileUrl="+url+"' />
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width: 560px;height: 410px;padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table>
				<tr>
					<td><input type="hidden" name="id" id="id"/></td>
					<td><input type="hidden" id="people_feature_file" name="people_feature_file" value="" /></td>
					<td><input type="hidden" id="picture" name="picture" value="" /></td>
				</tr>
				<tr id = "img_feature_field">
					<td valign="top">图像：</td>
					<td align="left" colspan="3">
			             <div class="image-list">
						     <div style="width:100px;height:100px;" id="people_eidt_upload_div">
								<input type="file" id="people_feature_file_edit" class="fileClass" accept="image/bmp,image/jpeg,image/png,image/gif,video/mp4,video/rmvb,video/avi" 
								 data-url="${pageContext.request.contextPath}/people/uploadImage" title=" "/>
				             	<img src="${pageContext.request.contextPath}/images/upload.png" style="width:100px;height:100px;" id="previewImg_people_edit" onload="imgFirstOnLoad()"/>
								<i class="closeImg" id="peopleAdd_closeImg_first" >
		                            <img src="${pageContext.request.contextPath}/images/close.png" class="closeImg-img" onclick="imgFirstOnClock()"/>
		                        </i>
			             	</div>		
			             </div>		             
					</td>
				</tr>
				<tr>
					<td>名称：</td>
					<td><input type="text" name="name" id="name" class="easyui-validatebox" required="true"/></td>
					<td>标题：</td>
					<td><input type="text" name="title" id="title" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><input type="text" name="state" id="state" data-options="validType:'length[1,1]'" class="easyui-validatebox" required="true"/></td>
					<td>总数：</td>
					<td><input type="text" name="total" id="total" data-options="validType:['integer','length[0,6]']" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td>描述：</td>
					<td><textarea rows="8" cols="30" name="remark" id="remark"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:savePeople()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closePeopleDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
	
	
	<div id="dlgSingle" class="easyui-dialog" style="width: 560px;height: 410px;padding: 10px 20px"
		closed="true" buttons="#dlgSingle-buttons">
		<form id="upload_form" method="POST" enctype="multipart/form-data">
		    <input type="hidden" id="peopleId" name="peopleId" /><br/><br/>
		    <input type="file" name="file" id="uploadFile" class="easyui-validatebox" required="true"/><br/><br/>
		</form>
	</div>
	
	<div id="dlgSingle-buttons">
		<a href="javascript:saveSingleUpload()" class="easyui-linkbutton" iconCls="icon-ok">上传</a>
		<a href="javascript:closeSingleUploadDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>