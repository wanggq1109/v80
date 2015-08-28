// JavaScript Document
jQuery(document).ready(function($){
	$(".ProductList li").hover(function(){
		$(this).find(".hoverline").fadeIn().end().find(".images").addClass("mousehover").stop().animate({
			"height":"160px"															  
		},500).find("img").stop().animate({
			"margin-top":"-30px"	
		},500);	
		$(this).find(".view").stop().animate({
			"bottom":"0"									 
		},500);
	},function(){
		$(this).find(".hoverline").fadeOut().end().find(".images").removeClass("mousehover").stop().animate({
			"height":"222px"															  
		},500).find("img").stop().animate({
			"margin-top":"0"	
		},500);	
		$(this).find(".view").stop().animate({
			"bottom":"-60px"									 
		},500);
	});
});

