$(function() {
	initCenter();
});

/**
 * 初始化center部分
 */
function initCenter() {
	
	$('#layout_center_tabsMenu').menu({
		onClick : function(item) {
			var curTabTitle = $(this).data('tabTitle');
			var type = $(item.target).attr('type');

			if (type === 'refresh') {
				layout_center_refreshTab(curTabTitle);
				return;
			}

			if (type === 'close') {
				var t = $('#layout_center_tabs').tabs('getTab', curTabTitle);
				if (t.panel('options').closable) {
					$('#layout_center_tabs').tabs('close', curTabTitle);
				}
				return;
			}

			var allTabs = $('#layout_center_tabs').tabs('tabs');
			var closeTabsTitle = [];

			$.each(allTabs, function() {
				var opt = $(this).panel('options');
				if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
					closeTabsTitle.push(opt.title);
				} else if (opt.closable && type === 'closeAll') {
					closeTabsTitle.push(opt.title);
				}
			});

			for ( var i = 0; i < closeTabsTitle.length; i++) {
				$('#layout_center_tabs').tabs('close', closeTabsTitle[i]);
			}
		}
	});

	$('#layout_center_tabs').tabs({
		fit : true,
		border : false,
		onContextMenu : function(e, title) {
			e.preventDefault();
			$('#layout_center_tabsMenu').menu('show', {
				left : e.pageX,
				top : e.pageY
			}).data('tabTitle', title);
		},
		onSelect:function(title,index){
           if(title == '首页'){
        	   layout_center_refreshTab(title);
        	   return;
           }
		}
	});
}
