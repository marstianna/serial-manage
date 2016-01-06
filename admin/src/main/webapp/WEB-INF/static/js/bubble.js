/**
 * Created by msunderwater on 15-7-14.
 */

/* every bubble only show one time
 * param parentdiv    --   jquery selector    .classname / #id
 * param direction    --   bubble-top / bubble-bottom / bubble-left / bubble-right
 * param text         --   string
 * param left         --   int
 * param right        --   int
 * param top          --   int
 * param bottom       --   int
 */
var Bubble = window.Bubble || {};
var trigger = true;

Bubble.closeBinded = false;

Bubble.init = function(options){
	
	var bubbleHead =  $.cookie(options.parentdiv);
	if(bubbleHead && trigger){
		return;
	}
    var newbubble = document.createElement('div');
    var parent =  $(options.parentdiv);
    newbubble.className = "bubble "+options.direction;
    newbubble.innerHTML = "<div class='bubble-close'></div>" + "<div class='bubble-text'>"+options.text+"</div>";


    //position control
    if(options.left){
        newbubble.style.left = options.left +"px";
    }
    if(options.right){
        newbubble.style.right = options.right +"px";
    }
    if(options.top){
        newbubble.style.top = options.top +"px";
    }
    if(options.bottom){
        newbubble.style.bottom = options.bottom +"px";
    }
    parent.append(newbubble);
    //bind event
//    if(!Bubble.closeBinded){
        parent.on("click",".bubble-close",function(){
            $(this).closest(".bubble").remove();
            $.cookie(options.parentdiv, options.parentdiv);
        });
//        Bubble.closeBinded = true;
//    }



};