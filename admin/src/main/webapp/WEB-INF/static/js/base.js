/** common variable */
var JQGRID_MAX_CHAR = 15;

$(function(){
    $.unionTab = {pageNum: 0,
        addTab: function(a) {
            $(".currentPageContent").empty();
            if( globalCtx == null ||globalCtx == '' ){
                history.pushState('','促销规则&政策管理系统','/index');
            }else{
                history.pushState('','促销规则&政策管理系统',getContextPath()+'/index');
            }

            a.url = a.url.indexOf("?")>0? a.url+"&useLayout=false":a.url+"?useLayout=false";
            $.unionTab.pageNum++,
                pageNum = $.unionTab.pageNum,
                $("#pageTab").append($('<li id="tab' + pageNum + '"><a pageNum="' + pageNum + '" href="#page' + pageNum + '">' + a.title + '<button class="close" type="button" title="Remove this page">×</button>' + "</a></li>")),
                $("#pageTabContent").append($("<iframe id='page" + pageNum + '\' content width="100%" style="border:0px;width:100%;"></iframe>')),
                $("#tab" + pageNum + " a").click(function() {
                    return $.unionTab.showTab($(this).attr("pageNum")), !1
                }),
                $("#tab" + pageNum + " button").click(function() {
                    return $.unionTab.removeTab($(this).parents("li").children("a").attr("pageNum")), !1
                }),
                $("#page" + pageNum).tab("show"), $("#page" + pageNum).attr("src", a.url),
                $.unionTab.showTab(pageNum), navigator.userAgent.match(/firefox/i) ? void 0 : pageNum;
                $("#page" + pageNum).css("height", $(window).height()-104);
                var bodyclass = $("body").attr("class");
                $("#page" + pageNum).load(function(){
                    $(this).contents().find("body").attr("class",bodyclass);
                    $(this).contents().find("body").on('keydown', function(e) {
                            if (e.which == 116 || e.keyCode == 82 && e.ctrlKey) {
                                $("#page" + pageNum).get(0).contentWindow.location.reload();
                                //window.location.reload();
                                e.preventDefault();
                                return false;
                            }
                        }
                    );
                })


        },
        showTab: function(a) {
            $("#pageTabContent iframe").css("display", "none"),
                $("#pageTabContent #page" + a).css("display", "block"),
                $("#pageTab .active").removeClass("active"), $("#pageTab #tab" + a).addClass("active")
        },
        removeTab: function(a) {
            a || (a = window.top.$("#pageTabContent iframe:visible").attr("id"), a = a.replace("page", "")),
                window.top.$("#pageTab #tab" + a).hasClass("active") && window.top.$("#pageTab #tab" + a).prev().children("a").click(), window.top.$("#pageTab #tab" + a).remove(), window.top.$("#pageTabContent #page" + a).remove()
        },
        addRootTab: function(a) {
            return window.top.$.unionTab.addTab(a)
        }
    }

})

$(document).ready(function(){
    resetWindow();
   $(window).resize(resetWindow);
   $(".head h1 a").click(function(){
        $('.wrap').toggleClass('hide-slider');
    });
    $(".left-nav ul li a").click(function(){
        $.unionTab.addTab({
            title: $(this).text(),
            url: $(this).attr("path")
        });
        resetWindow();
    });



});

function resetWindow(){
    $("#pageTabContent iframe").css("height", $(window).height()-104);
}


/** jqrid fomatter ---start* */
function fomatterMoreChar(cellval, opts, rowObject, action){
	if(cellval && cellval.length>JQGRID_MAX_CHAR)
	{
		return '<a title="'+cellval+'">'+cellval.substr(0,JQGRID_MAX_CHAR)+"..."+'</a>';
	}
	return cellval;
}

function getCheckBoxValue(name)   
{   
    var str="";
    var chk=document.getElementsByName(name);
    for(var i=0;i<chk.length;i++)
    {
         if(chk[i].checked)
         {
              str+=chk[i].value+",";
         }
     } 
    return str.substring(0,str.length-1);
} 

function getDateStr(AddDayCount,split) {
	var dd = new Date();
	dd.setDate(dd.getDate() + AddDayCount);// 获取AddDayCount天后的日期
	var y = dd.getFullYear();
	var m = dd.getMonth() + 1;// 获取当前月份的日期
	var d = dd.getDate();
	if (m < 10)
		m = '0' + m;
	if(split==null)
	  return y + "-" + m + "-" + d;
	else
		return y + split + m + split + d;
}

/**
 * error alert
 */
var showError = function(message){
	BootstrapDialog.show({
	    type: BootstrapDialog.TYPE_DANGER,
	    title: 'ERROR',
	    message: message
	});
}

/**
 * info  alert
 */
var showInfo = function(msg)
{
	BootstrapDialog.show({
        title: 'Info',
        message: msg
	});
}


var ProgressBar = function () {
    var pleaseWaitDiv = $('<div class="modal hide" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false"><div class="modal-header"><h1>处理中...</h1></div><div class="modal-body"><div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div></div></div>');
    return {
        show: function() {
            pleaseWaitDiv.modal();
        },
        hide: function () {
            pleaseWaitDiv.modal('hide');
        }
    };
};
if(top &&  top.ProgressBar){
    ProgressBar = top.ProgressBar;
}

function fixBtns(){
    $(window).scroll(function(){
        var btncol = $('.btncol');
        var topvalue = $('.table-jqgrid').offset().top;

        var nowtop = $(window).scrollTop();

        if(nowtop>topvalue && !btncol.hasClass('fixedbtn')){
            btncol.addClass('fixedbtn');
        }
        if(nowtop<topvalue && btncol.hasClass('fixedbtn')){
            btncol.removeClass('fixedbtn');
        }
    });
}

function disableSaveBtn(saveBtnJquery){
    $(saveBtnJquery).attr("disabled",true);
}

function ableSaveBtn(saveBtnJquery){
    $(saveBtnJquery).attr("disabled",false);
}

function cellbreaker(cellval, opts, rowObject, action) {
    return "<div class='linebr' >"+ cellval + "</div>";
}

function commabreaker(cellval, opts, rowObject, action) {

    var elements = cellval.split(",");

    var result = "";
    $(elements).each(function(idx, element){
        result += "<div class='linebr' >"+ element + "</div>";
    });
    return result;
}

(function(jQuery){

    if(jQuery.browser) return;

    jQuery.browser = {};
    jQuery.browser.mozilla = false;
    jQuery.browser.webkit = false;
    jQuery.browser.opera = false;
    jQuery.browser.msie = false;

    var nAgt = navigator.userAgent;
    jQuery.browser.name = navigator.appName;
    jQuery.browser.fullVersion = ''+parseFloat(navigator.appVersion);
    jQuery.browser.majorVersion = parseInt(navigator.appVersion,10);
    var nameOffset,verOffset,ix;

// In Opera, the true version is after "Opera" or after "Version"
    if ((verOffset=nAgt.indexOf("Opera"))!=-1) {
        jQuery.browser.opera = true;
        jQuery.browser.name = "Opera";
        jQuery.browser.fullVersion = nAgt.substring(verOffset+6);
        if ((verOffset=nAgt.indexOf("Version"))!=-1)
            jQuery.browser.fullVersion = nAgt.substring(verOffset+8);
    }
// In MSIE, the true version is after "MSIE" in userAgent
    else if ((verOffset=nAgt.indexOf("MSIE"))!=-1) {
        jQuery.browser.msie = true;
        jQuery.browser.name = "Microsoft Internet Explorer";
        jQuery.browser.fullVersion = nAgt.substring(verOffset+5);
    }
// In Chrome, the true version is after "Chrome"
    else if ((verOffset=nAgt.indexOf("Chrome"))!=-1) {
        jQuery.browser.webkit = true;
        jQuery.browser.name = "Chrome";
        jQuery.browser.fullVersion = nAgt.substring(verOffset+7);
    }
// In Safari, the true version is after "Safari" or after "Version"
    else if ((verOffset=nAgt.indexOf("Safari"))!=-1) {
        jQuery.browser.webkit = true;
        jQuery.browser.name = "Safari";
        jQuery.browser.fullVersion = nAgt.substring(verOffset+7);
        if ((verOffset=nAgt.indexOf("Version"))!=-1)
            jQuery.browser.fullVersion = nAgt.substring(verOffset+8);
    }
// In Firefox, the true version is after "Firefox"
    else if ((verOffset=nAgt.indexOf("Firefox"))!=-1) {
        jQuery.browser.mozilla = true;
        jQuery.browser.name = "Firefox";
        jQuery.browser.fullVersion = nAgt.substring(verOffset+8);
    }
// In most other browsers, "name/version" is at the end of userAgent
    else if ( (nameOffset=nAgt.lastIndexOf(' ')+1) <
        (verOffset=nAgt.lastIndexOf('/')) )
    {
        jQuery.browser.name = nAgt.substring(nameOffset,verOffset);
        jQuery.browser.fullVersion = nAgt.substring(verOffset+1);
        if (jQuery.browser.name.toLowerCase()==jQuery.browser.name.toUpperCase()) {
            jQuery.browser.name = navigator.appName;
        }
    }
// trim the fullVersion string at semicolon/space if present
    if ((ix=jQuery.browser.fullVersion.indexOf(";"))!=-1)
        jQuery.browser.fullVersion=jQuery.browser.fullVersion.substring(0,ix);
    if ((ix=jQuery.browser.fullVersion.indexOf(" "))!=-1)
        jQuery.browser.fullVersion=jQuery.browser.fullVersion.substring(0,ix);

    jQuery.browser.majorVersion = parseInt(''+jQuery.browser.fullVersion,10);
    if (isNaN(jQuery.browser.majorVersion)) {
        jQuery.browser.fullVersion = ''+parseFloat(navigator.appVersion);
        jQuery.browser.majorVersion = parseInt(navigator.appVersion,10);
    }
    jQuery.browser.version = jQuery.browser.majorVersion;
})(jQuery);

$.fn.extend({
    focusPosition:function( value ){
        var elem = this[0];
        if (elem&&(elem.tagName=="TEXTAREA"||elem.type.toLowerCase()=="text")) {
            if($.browser.msie){
                var rng;
                if(elem.tagName == "TEXTAREA"){
                    rng = event.srcElement.createTextRange();
                    rng.moveToPoint(event.x,event.y);
                }else{
                    rng = document.selection.createRange();
                }
                if( value === undefined ){
                    rng.moveStart("character",-event.srcElement.value.length);
                    return  rng.text.length;
                }else if(typeof value === "number" ){
                    var index=this.position();
                    index>value?( rng.moveEnd("character",value-index)):(rng.moveStart("character",value-index))
                    rng.select();
                }
            }else{
                if( value === undefined ){
                    return elem.selectionStart;
                }else if(typeof value === "number" ){
                    elem.selectionEnd = value;
                    elem.selectionStart = value;
                }
            }
        }else{
            if( value === undefined )
                return undefined;
        }
    }
});

function bindFormUpAndDown(){
    $(".collapsebtn").click(function(){
        var _this = $(this);
        var tablewrap = _this.prev(".nav-content").find(".collapsetablewrap");
        if(_this.hasClass("down")){
            _this.removeClass("down").addClass("up");
            tablewrap.removeClass("down").addClass("up");
        }else if(_this.hasClass("up")){
            _this.removeClass("up").addClass("down");
            tablewrap.removeClass("up").addClass("down");
        }
    });
}

function getContextPath() {
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0,index+1);
	return result;
}