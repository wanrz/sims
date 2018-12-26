/**
 * 检查文件类型
 * @param fileName
 * @param fileType 1 图片类型 2视频类型
 * @returns {Boolean}
 */
function checkFileType(fileName,fileType){
	if(typeof(fileName) == "undefined" || fileName == null || fileName == ""){        
        $.messager.alert(i18nData.common.alertInfo,i18nData.common.imgInfo);
        return false;
    } 
	
	if(!fileType) {
		fileType = 1;
	}
	
    if(fileType == 1 && !/\.(jpg|jpeg|png|JPG|PNG)$/.test(fileName))
    {        
        $.messager.alert(i18nData.common.alertInfo,i18nData.common.imgType);
        return false;
    } else if(fileType == 3 && !/\.(avi|rmvb|mp4)$/.test(fileName)) {
    	$.messager.alert(i18nData.common.alertInfo,i18nData.common.videoType);
        return false;
    }
    return true;
}

/**
 * 数字时间转字符串
 * @param nS
 * @returns
 */
function getLocalTime(nS) { 
	if(nS == null || nS == undefined){
		return '';
	}
	var date = new Date(nS);
	Y = date.getFullYear() + '-';
	M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
	D = date.getDate() + ' ';
	h = date.getHours() + ':';
	m = (date.getMinutes() < 10 ? ('0'+date.getMinutes()) : date.getMinutes()) + ':';
	s = date.getSeconds() < 10 ? ('0'+date.getSeconds()) : date.getSeconds(); 
	return Y+M+D+h+m+s;     
}