var contextPath="";

//jQuery(document).ready(function($) {
//
//	  if (window.history && window.history.pushState) {
//
//	    $(window).on('popstate', function() {
//	      var hashLocation = location.hash;
//	      var hashSplit = hashLocation.split("#!/");
//	      var hashName = hashSplit[1];
//
//	      if (hashName !== '') {
//	        var hash = window.location.hash;
//	        if (hash === '') {
//	        	location.href = "../login/loginOut";
//	        }
//	      }
//	    });
//
//	    window.history.pushState('forward', null, './#forward');
//	  }
//
//});

/**
 * 刷新当前center部分的页面
 * @param title
 */
function layout_center_refreshTab(title) {
	$('#layout_center_tabs').tabs('getTab', title).panel('refresh');
}

/**
 * 将左侧菜单点击的页面加载到center部分
 * @param opts
 */
function layout_center_addTabFun(opts) {
	var t = $('#layout_center_tabs');
	if (t.tabs('exists', opts.title)) {
		t.tabs('select', opts.title);
	} else {
		t.tabs('add', opts);
	}
}

function toLoginMethod(){
	//重新登录
	window.location.href = "../login/loginOut";
}

/**
 * 修改密码
 */
function changePwd() {
	var dialogId = $.acooly.framework.getDivId();

	var isSHA = $("#isSHA").val();
	$('<div id="'+dialogId+'"></div>').dialog({
		//href : '../html/changePassword.html?isSHA='+isSHA,
        href : '../html/changePassword.html?isSHA='+isSHA,
		width : 400,
		height : 230,
		modal : true,
		title : i18nData.changepwd.modaltitle,
		buttons : [ {
			text : i18nData.changepwd.resave,
			iconCls : 'icon-ok',
			handler : function() {
				var d = $("#"+dialogId);
				$('#user_changePassword_form').form('submit', {
					onSubmit:function(){
				    	var isValid = $(this).form('validate');
				    	if(!isValid){
				    		return false;
				    	}
				    	
				    	var newPwd = $('#system_newPassword').val();
				    	if(!validatePwdInput(newPwd)) {
				    		$.messager.alert(i18nData.changepwd.alertmsg, i18nData.changepwd.newpwdvalidate);
				    		return false;
				    	}
				    	
				    	//自定义检查合法性
				    	if(newPwd != $('#system_confirmNewPassword').val()){
							$.messager.alert(i18nData.changepwd.alertmsg,  i18nData.changepwd.twopwddiff);
							return false;
						}

						if(isSHA == "ON"){
                            $('#system_newPassword').val(sha256_digest(newPwd));
                            $('#system_confirmNewPassword').val(sha256_digest($('#system_confirmNewPassword').val()));
                            $('#system_oldPassword').val(sha256_digest($('#system_oldPassword').val()));
						}

						return true;						
					},
					success : function(data) {
						try {
							var result = $.parseJSON(data);
							if (result.success) {
								d.dialog('destroy');
								$.messager.alert(i18nData.changepwd.alertmsg, i18nData.changepwd.successmsg);
								setTimeout("toLoginMethod()", 3000);
							}else{
								if(result.message){
									$.messager.alert({title : i18nData.changepwd.alertmsg,msg : result.message});
								}
							}
						} catch (e) {
							$.messager.alert(i18nData.changepwd.alertmsg, i18nData.changepwd.changepwderror);
						}
					},
					error:function() {
						$.messager.alert(i18nData.changepwd.errormsg,i18nData.changepwd.servererror,"error");
					}
				});
			}
		} ],
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
			
		}
	});
}

/**
 * 验证密码 密码应满足至少8位，并包括数字、小写字母、大写字母和特殊符号4类中至少3类
 */
function validatePwdInput(pwdStr) {
	var pwdStatus = true;
    var regUpper = /[A-Z]/;
    var regLower = /[a-z]/;
    var regNumber = /[0-9]/;
    var regStr = /[^A-Za-z0-9]/;
    var complex = 0;
    
    if (regNumber.test(pwdStr)) {
        ++complex;
    }
    if (regLower.test(pwdStr)) {
        ++complex;
    }
    if (regUpper.test(pwdStr)) {
        ++complex;
    }
    if (regStr.test(pwdStr)) {
        ++complex;
    }
    if (complex < 3 || pwdStr.length < 8) {
    	pwdStatus = false;
    }
    return pwdStatus;
}

