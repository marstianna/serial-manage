var advicemodule = {
	linecount:0,
	commontop:0,
    isbottom:false,
	init: function(skin){
		$('body').append("<div class='backmask' style='display:none;'></div>");
		var logmodal = "<div class='modal-upgradeLog' style='height:300px;z-index:1005'>"
						+"<div class='logcontent' style='width:80%;height:80%'>"
						+"<textarea id='adviceCont' style='width:95%;height:95%' placeholder='请写下您在使用系统中遇到的问题和让促销系统更好的为您服务的建议'></textarea>"
						+"</div>"
						+"<div style='text-align: right;padding-right: 70px;padding-top: 5px;'><button class='btn btn-sm advice-submit'>提交</button></div>"
						+"<div class='btn-cancel'><a href='javascript:;'></a></div>"
						+"</div>";


		$('body').append(logmodal);

		advicemodule.bindevent();
		$(".backmask").fadeIn();
        $(".modal-upgradeLog").fadeIn();
	},
	bindevent: function(){
		$('body').off('click','.modal-upgradeLog .btn-cancel').on('click','.modal-upgradeLog .btn-cancel',function(){
            var modal = $(this).parent();
            modal.fadeOut('fast',function(){
                modal.remove();
                $(".backmask").fadeOut("normal", function(){
                	$('.backmask').remove();
                });
            });
		});

		$('.advice-submit').on('click',function(){
			var adviceCont = $("#adviceCont").val();
			
			if(adviceCont == "" || /^[ ]+$/g.test(adviceCont)){
				showError("请填入您的意见或问题!");
				return;
			}
			if(adviceCont.length > 500){
				showError("字数过多, 请稍微调整一下再提交, 最多支持500字!");
				return;
			}
			$.ajax({
				url: getContextPath() + "/extra/advise",
				type: 'POST',
				data:{msg : adviceCont},
				dataType: 'text',
				success: function(result){
					if(result == "success"){
						BootstrapDialog.alert("我们会尽快处理您的意见或问题, 谢谢!");
						$("#adviceCont").val("");
						$(".modal-upgradeLog .btn-cancel").click();
					}else{
						showError("发生错误, 请尝试刷新页面重新提交!");
					}
				}
			});
		});
		
		var modalHeight = $('.modal-upgradeLog').outerHeight();
		var modalTop = $(window).height()/2 - modalHeight/2;
		$(".modal-upgradeLog").css({'top':modalTop < 0 ? 0 : modalTop});
	}
};
