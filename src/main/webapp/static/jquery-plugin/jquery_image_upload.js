/*
*名称:图片上传本地预览插件 v1.1
*作者:周祥
*时间:2013年11月26日
*介绍:基于JQUERY扩展,图片上传预览插件 目前兼容浏览器(IE 谷歌 火狐) 不支持safari
*插件网站:http://keleyi.com/keleyi/phtml/image/16.htm
*参数说明: Img:图片ID;Width:预览宽度;Height:预览高度;ImgType:支持文件类型;Callback:选择文件显示图片后回调方法;
*使用方法: 
<div>
<img id="ImgPr" width="120" height="120" /></div>
<input type="file" id="up" />
把需要进行预览的IMG标签外 套一个DIV 然后给上传控件ID给予uploadPreview事件
$("#up").uploadPreview({ Img: "ImgPr", Width: 120, Height: 120, ImgType: ["gif", "jpeg", "jpg", "bmp", "png"], Callback: function () { }});
*/
jQuery.fn.extend({
    uploadPreview: function (opts) {
        var _self = this,
            _this = $(this);
        opts = jQuery.extend({
            Img: opts.Img,
            Width: opts.Width,
            Height: opts.Height,
            ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
            Callback: function () {}
        }, opts || {});
        _self.getObjectURL = function (file) {
            var url = null;
            if (window.createObjectURL != undefined) {
                url = window.createObjectURL(file)
            } else if (window.URL != undefined) {
                url = window.URL.createObjectURL(file)
            } else if (window.webkitURL != undefined) {
                url = window.webkitURL.createObjectURL(file)
            }
            return url
        };
        _this.change(function () {
            if (this.value) {
                if (!RegExp("\.(" + opts.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
                    //alert(i18nData.jqueryImageUpload.selectimg + opts.ImgType.join("，") + i18nData.jqueryImageUpload.imgStyle);
                    this.value = "";
                    return false
                }
                try {
                    $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
                } catch (e) {
                    var $file = $(this);
                    var fileObj = $file[0];
                    var windowURL = window.URL || window.webkitURL;
                    var dataURL;
                    var $img = $("#"+opts.Img);
                    if (fileObj && fileObj.files && fileObj.files[0]) {
                        dataURL = windowURL.createObjectURL(fileObj.files[0]);
                        $img.attr('src', dataURL);
                        //$img.css('display', '');
                    } else {
                        dataURL = $file.val();

                        var  $div = $img.parent()[0];
                        $img.parent().css({
                            "filter": "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)",
                            "display": "inline-block",
                            "cursor": "pointer",
                            'width': opts.Width + 'px',
                            'height': opts.Height + 'px'
                        });
                        $div.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dataURL;
                        $img.css('display', 'none');
                    }
                    opts.Callback()
                }
            }
        })
    }
});

jQuery.fn.extend({
	uploadPreviewAll: function (opts) {
        var _self = this,
            _this = $(this);
        opts = jQuery.extend({
            Img: opts.Img,
            Width: opts.Width,
            Height: opts.Height,
            ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
            Callback: function () {}
        }, opts || {});
        _self.getObjectURL = function (file) {
            var url = null;
            if (window.createObjectURL != undefined) {
                url = window.createObjectURL(file)
            } else if (window.URL != undefined) {
                url = window.URL.createObjectURL(file)
            } else if (window.webkitURL != undefined) {
                url = window.webkitURL.createObjectURL(file)
            }
            return url
        };
        _this.change(function () {
            if (this.value) {
            	$("#"+opts.Img+"_file").textbox("setValue",this.value);
                if (!RegExp("\.(" + opts.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
                    //alert("选择文件错误,图片类型必须是" + opts.ImgType.join("，") + "中的一种");
                    this.value = "";
                    $("#" + opts.Img).attr('src', "../images/upload.png");
                    return false
                }
                if ($.support.msie) {
                    try {
                        $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
                    } catch (e) {
                        var src = "";
                        var obj = $("#" + opts.Img);
                        var div = obj.parent("div")[0];
                        _self.select();
                        if (top != self) {
                            window.parent.document.body.focus()
                        } else {
                            _self.blur()
                        }
                        src = document.selection.createRange().text;
                        document.selection.empty();
                        obj.hide();
                        obj.parent("div").css({
                            'filter': 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)',
                            'width': opts.Width + 'px',
                            'height': opts.Height + 'px'
                        });
                        div.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = src
                    }
                } else {
                    $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
                }
                opts.Callback()
            }
        })
    }
});

jQuery.fn.extend({
    uploadPreviewVideo: function (opts) {
        var _self = this,
            _this = $(this);
        opts = jQuery.extend({
            Img: opts.Img,
            Width: opts.Width,
            Height: opts.Height,
            ALLType: ["gif", "jpeg", "jpg", "bmp", "png", "mp4", "rmvb", "avi"],
            VideoType: ["mp4", "rmvb", "avi"],
            Callback: function () {}
        }, opts || {});
        _self.getObjectURL = function (file) {
            var url = null;
            if (window.createObjectURL != undefined) {
                url = window.createObjectURL(file)
            } else if (window.URL != undefined) {
                url = window.URL.createObjectURL(file)
            } else if (window.webkitURL != undefined) {
                url = window.webkitURL.createObjectURL(file)
            }
            return url
        };
        _this.change(function () {
            if (this.value) {
                if (!RegExp("\.(" + opts.ALLType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
                    //alert(i18nData.jqueryImageUpload.selectimg + opts.ALLType.join("，") + i18nData.jqueryImageUpload.imgStyle);
                    this.value = "";
                    return false
                }
                if (RegExp("\.(" + opts.VideoType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
                    //如果是视频则不预览
                    return false
                }
                try {
                    $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
                } catch (e) {
                    var $file = $(this);
                    var fileObj = $file[0];
                    var windowURL = window.URL || window.webkitURL;
                    var dataURL;
                    var $img = $("#"+opts.Img);
                    if (fileObj && fileObj.files && fileObj.files[0]) {
                        dataURL = windowURL.createObjectURL(fileObj.files[0]);
                        $img.attr('src', dataURL);
                        //$img.css('display', '');
                    } else {
                        dataURL = $file.val();

                        var  $div = $img.parent()[0];
                        $img.parent().css({
                            "filter": "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)",
                            "display": "inline-block",
                            "cursor": "pointer",
                            'width': opts.Width + 'px',
                            'height': opts.Height + 'px'
                        });
                        $div.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dataURL;
                        $img.css('display', 'none');
                    }
                    opts.Callback()
                }
            }
        })
    }
});

function clearUploadStyle(div,img){
	div.css('filter','');
	div.css('display','');
	div.css('cursor','');
	img.css('display','block');
	div.css('width','150px');
	div.css('height','150px');
}