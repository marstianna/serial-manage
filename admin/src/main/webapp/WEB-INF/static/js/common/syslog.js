var logmodule = {
	linecount:0,
	commontop:0,
    isbottom:false,
	init: function(showContent){
		$('body').append("<div class='backmask' style='display:none;'></div>");
		var logmodal = "<div class='modal-upgradeLog stack10000' style=''>"
						+"<div class='btn-carousel'>"
						+"<div class='btn-up'><a href='javascript:;'></a></div>"
						+"</div>"
						+"<div class='logcontent'></div>"
						+"<div class='btn-carousel'>"
						+"<div class='line'></div>"
						+"<div class='btn-down'><a href='javascript:;'></a></div>"
						+"</div>"
						+"<div class='btn-cancel'><a href='javascript:;'></a></div>"
						+"</div>";


		$('body').append(logmodal);
//		var contentstr = "<div class='line'></div>"
//						 +"<div class='row'><h1>Android 5.0.0.0 版（更新时间：2015-04-03）</h1>"
//					     +"<p class='log'>更新功能<br>"
//						 +"精选各大新闻网站、社交平台、报刊杂志头条，最劲爆内容，一网打尽<br>"
//						 +"聚合相关文章、相关图片、网友观点，多角度透视新闻真相<br>"
//						 +"</p></div>"
//						 +"<div class='line'></div>"
//						 +"<div class='row'><h1>Android 4.7.0.0 版（更新时间：2015-02-12）</h1>"
//						 +"<p class='log'>更新功能"
//						 +"订阅话题有更新时支持推送"
//						 +"</p></div>"
//						 +"<div class='line'></div>"
//						 +"<div class='row'><h1>ABCDEFGH（更新时间：2015-02-12）</h1>"
//						 +"<p class='log'>YOOOOOOOOOOOOO"
//						 +"OOO"
//						 +"</p></div>"
//						 +"<div class='line'></div>"
//						 +"<div class='row'><h1>Android 5.0.0.0 版（更新时间：2015-04-03）</h1>"
//						 +"<p class='log'>更新功能<br>"
//						 +"精选各大新闻网站、社交平台、报刊杂志头条，最劲爆内容，一网打尽<br>"
//						 +"</p></div>"
//            +"<div class='line'></div>"
//            +"<div class='row'><h1>Android 5.0.0.0 版（更新时间：2015-04-03）</h1>"
//            +"<p class='log'>更新功能<br>"
//            +"精选各大新闻网站、社交平台、报刊杂志头条，最劲爆内容，一网打尽<br>"
//            +"</p></div>"
//            +"<div class='line'></div>"
//            +"<div class='row'><h1>Android 5.0.0.0 版（更新时间：2015-04-03）</h1>"
//            +"<p class='log'>更新功能<br>"
//            +"精选各大新闻网站、社交平台、报刊杂志头条，最劲爆内容，一网打尽<br>"
//            +"</p></div>"
//            +"<div class='line'></div>"
//            +"<div class='row'><h1>Android 5.0.0.0 版（更新时间：2015-04-03）</h1>"
//            +"<p class='log'>更新功能<br>"
//            +"精选各大新闻网站、社交平台、报刊杂志头条，最劲爆内容，一网打尽<br>"
//            +"</p></div>"
//						 +"<div class='line'></div>"
//						 +"<div class='row'><h1>Android 4.7.0.0 版（更新时间：2015-02-12）</h1>"
//						 +"<p class='log'>LASTLASTLAST<br>"
//						 +"精选各大新闻网站、社交平台、报刊杂志头条，最劲爆内容，一网打尽<br>"
//						 +"</p></div>"
						 
		var contentstr = "";
		var conList = showContent.split("%%");
		$.each(conList, function(i, item){
			if(item != ""){
				var conDetail = item.split("&&");
				contentstr += "<div class='line'></div>"
					 +"<div class='row'><h1>" + conDetail[0] + "</h1>"
				     +"<p class='log'>更新功能<br>"
				     +conDetail[1]
					 +"</p></div>";
			}
		});
		
		logmodule.fillcontent(contentstr);

		logmodule.bindevent();
		$(".backmask").fadeIn();
        $(".modal-upgradeLog").fadeIn();
	},
	fillcontent: function(contentstr){
		$('.logcontent').append(contentstr);
	},
	bindevent: function(){
		$('body').off('click','.modal-upgradeLog .btn-cancel').on('click','.modal-upgradeLog .btn-cancel',function(){
            var modal = $(this).parent();
            modal.fadeOut('fast',function(){
                modal.remove();
                $(".backmask").fadeOut("normal", function(){
                	$('.backmask').remove();
                });
                logmodule.linecount = 0;
                logmodule.commontop = 0;
                logmodule.isbottom = false;
            });
		});

		var modalHeight = $('.modal-upgradeLog').outerHeight();
		var modalTop = $(window).height()/2 - modalHeight/2;
		$(".modal-upgradeLog").css({'top':modalTop < 0 ? 0 : modalTop});
		
		var contentheight = $('.logcontent').outerHeight();
        var lineheight = $('.logcontent .line:first-child').outerHeight();
        var numofline = $('.logcontent .line').length;
        var rows =  $('.logcontent .row');
        var numofrow = rows.length;
        var arryLineTop = [0];
        for(var i=0;i<numofrow-1;i++){
            arryLineTop.push(arryLineTop[i]+lineheight+rows.eq(i).outerHeight());
        }
        var totalheight = lineheight*numofline;
        for(var i=0;i<numofrow;i++){
            totalheight += rows.eq(i).outerHeight();
        }

        $('body').off('click','.modal-upgradeLog .btn-up').on('click','.modal-upgradeLog .btn-up',function(){
            var index = logmodule.linecount;

            if(index>=0){
                if(index>=1){
                    logmodule.linecount -= 1;
                    var prevTop = arryLineTop[index-1];
                }else{
                    var prevTop = arryLineTop[0];
                }
                if(logmodule.isbottom){
                    logmodule.isbottom = false;
                }
                $('.logcontent').stop(true,false).animate({
                    scrollTop:prevTop
                },500,function(){});

            }

        });

		$('body').off('click','.modal-upgradeLog .btn-down').on('click','.modal-upgradeLog .btn-down',function(){
            var index = logmodule.linecount+1;
            var isb = logmodule.isbottom;
            var nextTop = arryLineTop[index];
            if(!nextTop || totalheight-nextTop<contentheight){
                $('.logcontent').stop(true,false).animate({
                    scrollTop:totalheight-contentheight
                },500,function(){});
                if(!isb){
                	if(nextTop){
                		logmodule.linecount += 1;
                	}
                    logmodule.isbottom = true;
                }
            }else{
                logmodule.linecount += 1;
                $('.logcontent').stop(true,false).animate({
                    scrollTop:nextTop
                },500,function(){});
            }

		});
	}
};
