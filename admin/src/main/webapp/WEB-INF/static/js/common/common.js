var common ={
    globalVariable:{
        pageSize:10,
        userType:""
    },
    fn:{
        /**
         * @param url   请求地址
         * @param data  请求参数
         * @param successFun  请求成功回调函数
         */
        ajax: function(url,data,successFun,currentPage,errorFun){
            $.ajax({
                type: "POST",
                url: url,
                data: data,
                cache:false,
                dataType: "json",
                success: function (data) {
                    if(currentPage){
                        successFun(data,currentPage);
                    }else{
                        successFun(data);
                    }
                },
                error: function () {
                    if(errorFun){
                        errorFun();
                    }else{
                        alert("'通讯失败,请稍后刷新页面!'");
                    }
                }
            });
        },
        //ajax post  json请求       url：请求地址 ；  data:请求参数  ；successFun:请求成功回调函数
        ajaxSync: function(url,data,successFun,errorFun){
            $.ajax({
                type: "POST",
                url: url,
                data: data,
                async:false,
                cache:false,
                dataType: "json",
                success: function (data) {
                    successFun(data);
                },
                error: function () {
                    if(errorFun){
                        errorFun();
                    }else{
                        alert("'通讯失败,请稍后刷新页面!'");
                    }
                }
            });
        },
        ajaxGet: function(url,data,successFun,errorFun){
            $.ajax({
                type: "GET",
                url: url,
                cache:false,
                data: data,
                dataType: "json",
                success: function (data) {
                    successFun(data);
                },
                error: function () {
                    if(errorFun){
                        errorFun();
                    }else{
                        alert("'通讯失败,请稍后刷新页面!'");
                    }
                }
            });
        },
        showDialog:function (ejsUrl,data,size,readyFun){
            var randomDialogId = parseInt(Math.random()*1000000);
            data.randomDialogId = randomDialogId;
            var dataHtml = new EJS({url:ejsUrl}).render({data:data});
            var dialog = $(dataHtml);
            dialog.attr('id',randomDialogId);
            if(size){
                if(size.width){
                    dialog.css("width",size.width);
                }
                if(size.height){
                    dialog.css("height",size.height);
                }
            }
            $("body").append(dialog);

            //对话框关闭后，清理资源
            $("#"+randomDialogId).on('hidden', function () {
                $("#"+randomDialogId).addClass("hide");
                $("#"+randomDialogId).remove();
            });
            $("#"+randomDialogId).modal({
                keyboard: false,
                show:true,
                backdrop:'static'
            }).css({
                'margin-left': function () {
                    return -($(this).width() / 2);
                }
            });
            $(".close").css("display",'none');
            if(readyFun){
                readyFun();
            }
        },
        /**
         *  显示确认对话框
         * @param showCancel 是否显示取消按钮
         * @param title 标题
         * @param message
         * @param callback   确认按钮回调
         * @param cancelCallback 取消按钮回调
         */
        showConfirmMessage: function (title, message, callback, cancelCallback, showCancel) {
            var html = "<div style='display:none'></div>"
            var randomDialogId = 'confirm_'+parseInt(Math.random() * 1000000);
            var dialog = $(html);
            dialog.attr('id', randomDialogId);
            $("body").append(dialog);
            $('#' + randomDialogId).confirmModal({
                heading: title,
                body: message,
                callback: callback,
                cancelCallback: cancelCallback,
                showCancel: showCancel
            });
            $(".close").css("display", 'none');
        },
        //组装form 数据为json 格式数据 用于新增和更新数据
        getFromJsonData : function(formId){
            var jsonData = {};
            var fields = $("#"+formId).serializeArray();
            jQuery.each(fields, function(i, field){
                if(field.value != ''){
                    jsonData[field.name]= $.trim(field.value);
                }
            });
            return jsonData;
        },
        /**
         * 分页工具栏
         * @param callbackFunctionName 分页回调函数的全名，该函数完成数据分页显示的数据列表,
         *                              为兼容异步请求模式。该函数的型参格式为 function(pageIndex,callback)，该callback函数在异步请求响应时调用
         * @targetContainer 分页的工具栏将要放置的目标容器ID
         * @maxPageCount 最大的页数，可选。主要用于判断用户输入的页数不能超过次数
         */
        pagination:function(pageIndex,callbackFunctionName,targetContainer,maxPageCount){
            var pageIndex = ~~pageIndex;
            if(pageIndex<1){
                pageIndex = 1;
            }
            if(pageIndex>maxPageCount){
                pageIndex = maxPageCount;
            }

            //匿名回调函数，基于数据记录总数构建分页工具栏，并显示在页面指定的容器中
            function callback(recordCount){
                var pageHTML='';
                var recordIndexBegin = 0;
                var recordIndexEnd = 0;
                recordIndexBegin = (pageIndex - 1) * common.globalVariable.pageSize + 1;
                recordIndexEnd = recordIndexBegin + common.globalVariable.pageSize - 1;

                if (recordIndexEnd > recordCount) {
                    recordIndexEnd = recordCount;
                }
                if(!recordCount){
                    recordCount=0;
                }
                var pageCount = parseInt(recordCount/common.globalVariable.pageSize);
                if(recordCount%common.globalVariable.pageSize>0){
                    pageCount++;
                }

                var data = {};
                data.recordCount = recordCount;
                data.pageSize = common.globalVariable.pageSize;
                data.pageCount = pageCount;
                data.currentPage = pageIndex;
                data.recordIndexBegin = recordIndexBegin;
                data.recordIndexEnd = recordIndexEnd;
                data.pagination = callbackFunctionName;
                data.targetContainer = targetContainer;
                var randomPagerId = "page_"+parseInt(Math.random()*1000000);
                data.id = randomPagerId;

                pageHTML = new EJS({url:'/js/common/pagination.ejs'}).render({data:data});
                $("#"+targetContainer).html('');
                $("#"+targetContainer).html(pageHTML);
            }
            eval(callbackFunctionName+'('+pageIndex+',callback)');
        },
        showErrorMessages:function(errorCode,errorMessage,description){
            var data = {};
            data.errorCode = errorCode;
            data.errorMessage = errorMessage;
            data.description = description;
            var errorDialogHTML = new EJS({url:'/js/common/errorDialog.ejs'}).render({data:data});
            var errorDialog = $(errorDialogHTML);
            var randomDialogId = parseInt(Math.random()*1000000);
            errorDialog.attr('id',randomDialogId);
            $("body").append(errorDialog);

            //对话框关闭后，清理资源
            $("#"+randomDialogId).on('hidden', function () {
                $("#"+randomDialogId).remove();
            })
            $("#"+randomDialogId).modal('show');
            $(".close").css("display",'none');
        },
        showInfoMessages:function(title,message){
            var data = {};
            data.title = title;
            data.message = message;
            var infoDialogHTML = new EJS({url:'/js/common/infoDialog.ejs'}).render({data:data});
            var infoDialog = $(infoDialogHTML);
            var randomDialogId = parseInt(Math.random()*1000000);
            infoDialog.attr('id',randomDialogId);
            $("body").append(infoDialog);

            //对话框关闭后，清理资源
            $("#"+randomDialogId).on('hidden', function () {
                $("#"+randomDialogId).remove();
            })
            $("#"+randomDialogId).modal('show');
            $(".close").css("display",'none');
        },
        //给跳页输入框增加回车事件     跳页input框id ,总页面隐藏字段Id，验证通过请求后台的方法
        jumpToPageAddEnterKeyEvent:function(pageNumInput,callbackFunctionName,targetContainer,recordTotalCount,maxPageCount){
            $(pageNumInput).keydown(function(e){
                if(!e)e=window.event;
                if((e.keyCode||e.which)==13){
                    e.preventDefault();
                    var pageIndex =$(pageNumInput).val();
                    if(pageIndex>maxPageCount){
                        pageIndex = maxPageCount;
                    }
                    common.fn.pagination(pageIndex,callbackFunctionName,targetContainer,recordTotalCount);
                }
            })
        },
        digitInput:function(element,event){
            //8：退格键、46：delete、37-40： 方向键
            //48-57：小键盘区的数字、96-105：主键盘区的数字
            //189、109：小键盘区和主键盘区的负号
            var e = event || window.event; //IE、FF下获取事件对象
            var cod = event.charCode||event.keyCode; //IE、FF下获取键盘码
            //小数点处理

            if(cod!=8 && cod != 46 && (cod<37 || cod>40) && (cod<48 || cod>57) && (cod<96 || cod>105)) notValue(event);
            function notValue(e){
                e.preventDefault ? e.preventDefault() : e.returnValue=false;
            }
        },
        formatDateTime:function (obj) {
            var myDate = new Date(obj.time);
            var year = myDate.getFullYear();
            var month = ("0" + (myDate.getMonth() + 1)).slice(-2);
            var day = ("0" + myDate.getDate()).slice(-2);
//            var h = ("0" + myDate.getHours()).slice(-2);
//            var m = ("0" + myDate.getMinutes()).slice(-2);
//            var s = ("0" + myDate.getSeconds()).slice(-2);
//            var mi = ("00" + myDate.getMilliseconds()).slice(-3);
            return year + "-" + month + "-" + day ;
        }
    }
}
$(document).ready(function(){
    common.globalVariable.userType = $.cookie("type");
})