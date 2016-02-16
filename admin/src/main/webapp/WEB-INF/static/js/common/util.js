$.fn.promotion_menu = function(menus, clickcallback) {
	var findChildren = function(menus, parent_id) {
		return $.grep(menus, function(ele) {
			return ele.parent == parent_id;
		});
	}
	var findMenu = function(menus, id) {
		return $.grep(menus, function(ele) {
			return ele.parent == id;
		})[0];
	}
	var constructSubMenu = function(children) {
		console.log(children);
		var container =  $('<ul id="collapse'+child.parent+'" class="collapse"><ul>');
		for (e in children) {
			var child = children[e];
			var menu_sub = '<li><a href="javascript:void(0);" path="'+child.path+'">'+child.name+'</a> </li>';
			container.append(menu_sub);
			if (clickcallback) {
				$("a", sub_menu).click(function(arg) {
					return function() {
						clickcallback(arg);
					}
				}(child));
			}
		}
		return container;
	}
	var constructMenu = function(menu, menus) {
		var header = $('<li > <a href="#collapse'+menu.id+'" data-toggle="collapse" data-parent="#menu_sidebar">'+menu.name+'（2)<span></span></a></li>');
		var children = findChildren(menus, menu.id);
		children.appendTo(header);
		var hasChildren = children && children.length > 0;
		if (hasChildren) {
			constructSubMenu(children).appendTo(header);
		} else {
			$("a", header).click(function() {
				clickcallback(menu);
			});
		}
//		if (menu.expand) {
//			$(header).addClass("active");
//		}
		return header;
	}

	var roots = $.grep(menus, function(ele) {
		return !ele.parent || ele.parent == -1;
	});

//	var menu_ul = $("<ul class=\"sidebar-menu\"/></ul>");

	$.each(roots, function(ele) {
		constructMenu(roots[ele], menus).appendTo("#menu_sidebar");
	});
	
	
//	$(menu_ul).appendTo($(this));
};

$.jgrid.extend({
	query : function(formId, precall, aftercall) {
		precall && precall();
		var data = $('#' + formId).serializeArray(); // 获取值
		var params = {};
		for (x in data) {
			// serializeArray bug? 无法处理multiple参数，特殊处理
			if ($('#' + formId + " :input[name='" + data[x]['name'] + "']")
					.attr("multiple")) {
				params[data[x]['name']] = decodeURIComponent($(
						'#' + formId + " :input[name='" + data[x]['name']
								+ "']").val());
			} else {
				params[data[x]['name']] = decodeURIComponent(data[x]['value'],
						true); // 防止中文乱码
			}
		}

		var originalData = $(this).getGridParam("postData");
		var newPostData = $.extend({}, originalData, params); // 替换jqgrid查询参数
		$(this).setGridParam({
			postData : newPostData
		}).setGridParam({
			page : 1
		}) // 更新条件后点击查询,永远跳到第一页
		.trigger('reloadGrid'); // 触发重新查询
		aftercall && aftercall();
	},
	deleteSelected : function(url, ajaxcallback, callback, from) {
		var _this = $(this);
		var postData = {};
		var selectedRows = $(this).jqGrid('getGridParam', 'selarrrow');
		if (!selectedRows || selectedRows.length < 1) {
			alert("请先选中需要删除的记录!");
			return;
		}
		var keys = $(this).jqGrid('getGridParam', 'deleteKeys');
		if (!keys || keys.length < 1) {
			alert("未配置表格主键!无法确认删除操作所依据的字段!");
			return;
		}

		for (ridIdx in selectedRows) {
			var row = $(this).getRowData(selectedRows[ridIdx]);
			for (keyIdx in keys) {
				var name = keys[keyIdx];
				if (!postData[name]) {
					postData[name] = row[name];
				} else {
					postData[name] += "," + row[name];
				}
			}
		}

		if (callback) {
			callback(postData);
		} else {
			$.ajax({
				type : "POST",
				url : url,
				data : postData,
				success : ajaxcallback || function() {
					_this.trigger('reloadGrid'); // 触发重新查询
					alert("删除成功");
				}
			});
		}
	},
	getSelectIdAsString : function() {
		var selectedRows = $(this).jqGrid('getGridParam', 'selarrrow');
		if (!selectedRows || selectedRows.length < 1) {
			return false;
		}
		return selectedRows.join(",");
	},
	clearGrid : function() {
		var rowIds = $(this).jqGrid('getDataIDs');
		// iterate through the rows and delete each of them
		for (var i = 0, len = rowIds.length; i < len; i++) {
			var currRow = rowIds[i];
			$(this).jqGrid('delRowData', currRow);
		}
	},
	getSelectIdAsStringSingle : function() {
		var selectedRow = $(this).jqGrid('getGridParam', 'selrow');
		if (!selectedRow) {
			return false;
		}
		return selectedRow;
	}
});

$.fn.fillForm = function(source) {
	if (!source) {
		return;
	}
	for (x in source) {
		$("input[name='" + x + "'],textarea[name='" + x + "']", this).val(
				source[x]);
	}
};

$.fn.formToJson = function() {
	var data = $(this).serializeArray(); // 获取值
	var params = {};
	for (x in data) {
		params[data[x]['name']] = decodeURIComponent(data[x]['value'], true); // 防止中文乱码
	}
	return params;
};

$.fn.rangdatetimepicker = function(options) {
	var defaultOption = {
		beforeShow : function() {
			$('#ui-datepicker-div').maxZIndex();
		},
		onClose : function(dateText, inst) {
			if ($(this).attr("before")) {
				var testStartDate = $(this).datetimepicker('getDate');
				var testEndDate = $('#' + $(this).attr("before"))
						.datetimepicker('getDate');

				if (testEndDate && testStartDate > testEndDate)
					$('#' + $(this).attr("before")).datetimepicker('setDate',
							testStartDate);
			}

			if ($(this).attr("after")) {
				var testStartDate = $('#' + $(this).attr("after"))
						.datetimepicker('getDate');
				var testEndDate = $(this).datetimepicker('getDate');
				if (testStartDate && testStartDate > testEndDate)
					$('#' + $(this).attr("after")).datetimepicker('setDate',
							testEndDate);
			}

		},
		onSelect : function(selectedDateTime) {
			if ($(this).attr("before")) {
				$("#" + $(this).attr("before")).datetimepicker(/* 'option' */{
					timeFormat : 'HH:mm:ss',
					dateFormat : 'yy-mm-dd'
				}, 'minDate', $(this).datetimepicker('getDate'));
			}

			if ($(this).attr("after")) {
				$("#" + $(this).attr("after")).datetimepicker(/* 'option' */{
					timeFormat : 'HH:mm:ss',
					dateFormat : 'yy-mm-dd'
				}, 'maxDate', $(this).datetimepicker('getDate'));
			}
		}
	};

	options = $.extend(defaultOption, options)

	//$(this).datetimepicker(options);
};

var unixtime_formatter = function(cellval, opts, rowObject, action) {
	if (!opts.colModel.formatoptions) {
		opts.colModel.formatoptions = {
			newformat : 'Y-m-d H:i:s'
		};
	}
	return $.fn.fmatter.call(this, "date", new Date(cellval * 1000), $.extend(
			{}, $.jgrid.formatter.date, opts), rowObject, action);
};

var defaultAjaxHandler = function(result) {
	if (result && !result.success) {
		BootstrapDialog.show({
			type : BootstrapDialog.TYPE_DANGER,
			title : 'Error',
			message : result.message
		});
	} else {
		BootstrapDialog.show({
			title : 'Info',
			message : result.message
		});
	}
};

$.fn.uploadDialog = function(url, config) {
	$(this).attr("src", url).dialog({
		title : '上传文件',
		width : 600,
		height : 600
	}).css('width', '600px');
	var ifr = $(this);
	config.closeDialog = function() {
		$(ifr).dialog("close");
	};

	$(this).load(function() {
		$(this)[0].contentWindow.setParams(config);
	});
};

$.fn.newDialog = function(url, config) {
	$(this).attr("src", url).dialog(config).css('width', config.width);
	var ifr = $(this);
	config.closeDialog = function() {
		$(ifr).dialog("close");
	};

	$(this).load(function() {
		$(this)[0].contentWindow.setParams(config);
	});
};

$.maxZIndex = $.fn.maxZIndex = function(opt) {
	var def = {
		inc : 10,
		group : "*"
	};
	$.extend(def, opt);
	var zmax = 0;
	$(def.group).each(function() {
		var cur = parseInt($(this).css('z-index'));
		zmax = cur > zmax ? cur : zmax;
	});
	if (!this.jquery)
		return zmax;

	return this.each(function() {
		zmax += def.inc;
		$(this).css("z-index", zmax);
	});
}

/**
 * Toastr Notification Bar Plugin
 */
toastr.options = {
	"closeButton" : true,
	"debug" : false,
	"positionClass" : "toast-bottom-full-width",
	"onclick" : function(e) {
		return false;
	},
	"showDuration" : "0",
	"hideDuration" : "2000",
	"timeOut" : "30000",
	"extendedTimeOut" : "1000",
	"showMethod" : "fadeIn",
	"hideMethod" : "fadeOut"
};

$.extend({
			showMessage : function(data, toaster) {
				// !! 兼容Ajax响应格式 : 项目前期未严格约定Ajax响应格式
				var isServerFault = data.data && !data.data.success;
				if (data.data) {
					data = $.extend({}, data, data.data);
				}
				// !!

				toaster = toaster || data.success ? top.toastr["info"]
						: top.toastr["error"];

				var detailBtnId = 'toastr_' + new Date().getTime();

				toaster(
						data.message
								+ (isServerFault ? "<a id='"
										+ detailBtnId
										+ "' class=' pull-right btn-sm toastr-detail-btn'>查看详细>></a>"
										: ''), data.title);

				if (isServerFault) {
					$('#' + detailBtnId, parent.document)
							.click(
									function() {
										top.BootstrapDialog
												.show({
													title : "详细信息",
													message : "<div style='height:500px'>"
															+ data.message
															+ "<br /><br /><pre style='height:400px'>"
															+ data.details
															+ "</pre></div>",
													type : BootstrapDialog.TYPE_DANGER
												});
									});
				}
			}
		});

/**
 * Backend Tabs 为定制化，未实现插件化
 */
$.promotionTab = {
	pageNum : 0,
	addTab : function(data) {
		$.promotionTab.pageNum++;
		pageNum = $.promotionTab.pageNum;
		$('#pageTab').append(
				$('<li id="tab' + pageNum + '"><a pageNum="' + pageNum
						+ '" href="#page' + pageNum + '">' + data.title
						+ '<button class="close" type="button" '
						+ (data.persisted ? 'style="display:none"' : '')
						+ 'title="Remove this page">×</button>' + '</a></li>'));

		$('#pageTabContent')
				.append(
						$("<iframe id='page"
								+ pageNum
								+ "' content width=\"100%\" style=\"border:0px;width:99%\"></iframe>"));

		$('#tab' + pageNum + ' a').click(function() {
			$.promotionTab.showTab($(this).attr('pageNum'));
			return false;
		});
		$('#tab' + pageNum + ' button').click(
				function() {
					$.promotionTab.removeTab($(this).parents('li')
							.children('a').attr('pageNum'));
					return false;
				});

		$('#page' + pageNum).tab('show');

		$('#page' + pageNum).attr('src', data.url);
		$.promotionTab.showTab(pageNum);
		if (!navigator.userAgent.match(/firefox/i))
			return pageNum;
	},
	showTab : function(tabId) {
		$('#pageTabContent iframe').css("display", "none");
		$('#pageTabContent #page' + tabId).css("display", "block");
		$('#pageTab .active').removeClass('active');
		$('#pageTab #tab' + tabId).addClass('active');
	},
	removeTab : function(tabId) {
		if (!tabId) { // if tabId is null , close current/displaying tab
			tabId = window.top.$('#pageTabContent iframe:visible').attr("id");
			tabId = tabId.replace('page', '');
		}
		// if the tabs to be closing is actived
		// activate previous tabs
		if (window.top.$('#pageTab #tab' + tabId).hasClass("active")) {
			window.top.$('#pageTab #tab' + tabId).prev().children('a').click();
		}
		// remove tab
		window.top.$('#pageTab #tab' + tabId).remove();
		window.top.$('#pageTabContent #page' + tabId).remove();
	},
	addRootTab : function(data) {
		return window.top.$.promotionTab.addTab(data);
	}
};

$(function() {
	$(".unixtime").each(
			function() {
				var pattern = $(this).attr("pattern")
				var time = parseInt($(this).text(), 10);

				var opts = {
					colModel : {
						formatoptions : {
							newformat : pattern
						}
					}
				};
				var time = $.fn.fmatter.call(null, "date",
						new Date(time * 1000), $.extend({},
								$.jgrid.formatter.date, opts), null, null);

				$(this).text(time);
			});
	// remove search btn of jqgrid globally
	jQuery.extend(jQuery.jgrid.nav, {
		search : false
	});

});

//
$.validator.addMethod("regex", function(value, element, regexp) {
	var re = new RegExp(regexp);
	return this.optional(element) || re.test(value);
});

$.validator.addMethod("require", function(value, element, regexp) {

	return value && value != '' && !/^\s+$/g.test(value);
});


$.validator.addMethod("isFloat", function(value, element) { 
    value=parseFloat(value);      
    return this.optional(element) || value>=0;       
}, "必须为正数");  


jQuery.validator.addMethod("isDate", function(value, element){
	var ereg = /^(\d{1,4})(-|\/)(\d{1,2})(-|\/)(\d{1,2})$/;
	var r = value.match(ereg);
	if (r == null) {
		return false;
	}
	var d = new Date(r[1], r[3] - 1, r[5]);
	var result = (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[5]);
	return this.optional(element) || (result);
}, "请输入正确的日期");

$.extend($.validator.messages, {
	regex : "输入格式不合法!",
	require : '必填'
});

jQuery.validator.setDefaults({
	showErrors: function(errorMap, errorList) {
	    $.each(this.successList, function(index, value) {
	      return $(value).popover("hide");
	    });
	    return $.each(errorList, function(index, value) {
	    	
	    	if ( $(value.element).data('popover') != null) {
	    		  $(value.element).popover('hide');
	    		  $(value.element).removeData('popover');
	    	}
	    	  
	      var _popover;
	      _popover = $(value.element).popover({
	        trigger: "manual",
	        placement: "bottom",
	        content: value.message,
	        template: "<div class=\"popover\"><div class=\"arrow\"></div><div class=\"popover-inner\"><div class=\"popover-content\" style=\"min-width:60px\"><p></p></div></div></div>"
	      });
//	      $(value.element).data("bs.popover").options.content = value.message;
	    /*  $(value.element).blur(function(){
	    	  alert(1);
	    	  if ( $(value.element).data('popover') != null) {
	    	  $(value.element).popover("show");
	    	  }
	      });*/
	      
	     /* $(value.element).focus(function(){
	    	  if ( $(value.element).data('popover') != null) {
	    	   $(value.element).popover('hide');
	    	  }
	      });*/
	      
	      $(value.element).attr('onclick',"hidePopover(this)");
	      return $(value.element).popover("show");
	    });
	}
});

 function hidePopover(el){
	 if ( $(el).data('popover') != null) {
	  $(el).popover('destroy');
	  }
 }

function isNotNull(val){
	if(val==null||val==''||typeof(val)=='undefined')
	{
		return false;
	}
	return true;
}

function valiateSelectOnGridForSingle(selectedIds){
	if(!selectedIds || selectedIds.length ==0){
		showError('当前没有选择！');
		return false;
	}
	if(selectedIds.length>1){
		showError('仅支持单选操作！');
		return false;
	}
	return true;
}
