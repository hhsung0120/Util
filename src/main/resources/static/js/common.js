
$(window).load(function() {    
     $('#loading').hide();  
    });

$(document).ready(function () {
    datepicker_box();
    side_menu();
    tab();
	var select = $("select");

	select.change(function () {
		var select_name = $(this).children("option:selected").text();
		$(this).siblings("label").text(select_name);
	});
});

$(document).ready(function () {
$("#accodion_area > button").click(function(e){
		e.preventDefault();
		if($(this).next().is(":animated") == false){
			if($(this).hasClass("on") == false){
				$(this).addClass("on").next().stop().slideDown(300,"easeOutExpo");
			}else{
				$(this).removeClass("on").next().stop().slideUp(300,"easeOutExpo");
			}
		}
	});

$(".btn_tg").click(function(e){
		e.preventDefault();
		if($(this).parent().parent().next().is(":animated") == false){
			if($(this).hasClass("on") == false){
				$(this).addClass("on").parent().parent().next().stop().slideDown(300,"easeOutExpo");
			}else{
				$(this).removeClass("on").parent().parent().next().stop().slideUp(300,"easeOutExpo");
			}
		}
	});    
    
});

$(document).ready(function () {
//추가 버튼
    $(document).on("click","button[name=addStaff]",function(){
        
        var addStaffText =  '<tr name="trStaff">'
                            +'<td>1</td>'+'<td><input type="text"></td>'
                            +'<td><input type="text"></td>'
                            +'<td><div class="select-box-w">'+'<label for="">선택</label>'+'<select>'+'<option selected="">선택</option>'+'<option>ooo</option>'+'<option>ooo</option>'+'</select>'+'</div></td>'
                            +'<td><input type="text"></td>'
                            +'<td><div class="select-box-w">'+'<label for="">선택</label>'+'<select>'+'<option selected="">선택</option>'+'<option>ooo</option>'+'<option>ooo</option>'+'</select>'+'</div></td>'
                            +'<td><input type="text"></td>'
                            +'<td><button class="btn-sm btn-gray" name="delStaff">삭제</button></td>'
                            '</tr>';
            
        var trHtml = $( "tr[name=trStaff]:last" ); //last를 사용하여 trStaff라는 명을 가진 마지막 태그 호출
        
        trHtml.after(addStaffText); //마지막 trStaff명 뒤에 붙인다.
        
    });
    
    //삭제 버튼
    $(document).on("click","button[name=delStaff]",function(){
        
        var trHtml = $(this).parent().parent();
        
        trHtml.remove(); //tr 테그 삭제
        
    });
    
    $(document).on("click","button[name=addStaff2]",function(){
        
        var addStaffText =  '<tr name="trStaff2">'
                            +'<td>1</td>'+'<td><input type="text"></td>'
                            +'<td><input type="text"></td>'
                            +'<td><div class="select-box-w">'+'<label for="">선택</label>'+'<select>'+'<option selected="">선택</option>'+'<option>ooo</option>'+'<option>ooo</option>'+'</select>'+'</div></td>'
                            +'<td><input type="text"></td>'
                            +'<td><div class="select-box-w">'+'<label for="">선택</label>'+'<select>'+'<option selected="">선택</option>'+'<option>ooo</option>'+'<option>ooo</option>'+'</select>'+'</div></td>'
                            +'<td><input type="text"></td>'
                            +'<td><button class="btn-sm btn-gray" name="delStaff2">삭제</button></td>'
                            '</tr>';
            
        var trHtml = $( "tr[name=trStaff2]:last" ); //last를 사용하여 trStaff라는 명을 가진 마지막 태그 호출
        
        trHtml.after(addStaffText); //마지막 trStaff명 뒤에 붙인다.
        
    });
    
    //삭제 버튼
    $(document).on("click","button[name=delStaff2]",function(){
        
        var trHtml = $(this).parent().parent();
        
        trHtml.remove(); //tr 테그 삭제
        
    });
    
});



function datepicker_box(){
    //셀렉트박스
    $( ".datepicker" ).datepicker({
        showOn: "both", 
        buttonImage: "../images/icon_cal2.png", 
        buttonImageOnly: true,
        changeMonth: true,
        changeYear: true,
        dayNames: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], 
        monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
        monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        dateFormat: "yy-mm-dd",
        currentText: '오늘', 
        showMonthAfterYear: true,
        minDate:-0,
  });
}

$(function(){
	$(".accordian > a").click(function(){
		$(this).next().slideUp();
		$(".accordian > a").removeClass('on');
		if(!$(this).next().is(":visible"))
		{
			$(this).next().slideDown();
			$(".accordian > a").addClass('on');
		}
	})
})

$(document).on('click', '.toggleBG', function () {
    var toggleBG = $(this);
    var toggleFG = $(this).find('.toggleFG');
    var left = toggleFG.css('left');
    if(left == '40px') {
        toggleBG.removeClass('on');
        toggleActionStart(toggleFG, 'TO_LEFT');
    }else if(left == '0px') {
        toggleBG.addClass('on');
        toggleActionStart(toggleFG, 'TO_RIGHT');
    }
});
 
// 토글 버튼 이동 모션 함수
function toggleActionStart(toggleBtn, LR) {
    // 0.01초 단위로 실행
    var intervalID = setInterval(
        function() {
            // 버튼 이동
            var left = parseInt(toggleBtn.css('left'));
            left += (LR == 'TO_RIGHT') ? 5 : -5;
            if(left >= 0 && left <= 40) {
                left += 'px';
                toggleBtn.css('left', left);
            }
        }, 10);
    setTimeout(function(){
        clearInterval(intervalID);
    }, 201);
}



function side_menu(){

    $("#lnb > .left_btn").click(function(e){
        if($("#lnb").hasClass("open")){
            $("#lnb").removeClass("open");
            $(".content").removeClass("open");
        }
        else{
            $("#lnb").addClass("open");
            $(".content").addClass("open");
        }
    });

    $(".side_menu ul.depth_02 > li > a").click(function(e){
         if($(this).parents("li").hasClass("open")){
            $(this).parents("li").removeClass("open")
         }else{
            $(this).parents("li").addClass("open");
         }
         $(this).parents("li").find("ul").eq(0).slideToggle();
    });
}

function tab(){
    $(document).on("click", ".target_list > li", function(e){
            e.stopImmediatePropagation();
            var idx = $(this).index();
            var pos = 0;
            $(".targer_wrap > li").each(function(index, item){
                if(idx > index){
                    pos = pos + $(item).height()
                }
            });
            $(".targer_wrap").animate({
                scrollTop:pos
            },1000);
    });
    $(document).on("click", ".tab_list > li", function(e){
        e.stopImmediatePropagation();
        var idx = $(this).index();
        var tab_wrap = $(this).parents(".tab_wrap");
        $(tab_wrap).find(".tab_list > li").removeClass("on");
        $(tab_wrap).find(".tab_list > li").eq(idx).addClass("on");
        $(tab_wrap).find(".tab_content > li").removeClass("on");
        $(tab_wrap).find(".tab_content > li").eq(idx).addClass("on");

    });

}



function popup(){
    
    var BPOPUP='';
    $(".pop_btn").bind('click', function(e) {
      e.preventDefault();
        var pop_id = $(this).data("pop");
      BPOPUP =  $('#'+pop_id).bPopup({
           modalClose : true,
           positionStyle:'fixed',
          onOpen:function(){ 
                $("body").addClass("pop_open");
           },
           onClose:function(){
               $("body").removeClass("pop_open");
           },
       });
    });   
}


var BPOPUP='';
	(function($) {
		$(function() {
			  $('#btn_add').bind('click', function(e) {
				  e.preventDefault();
				  BPOPUP =  $('#pop_p_list').bPopup({
					   modalClose : true,
					   positionStyle:'fixed',
				   });
				});    
		 });
	})(jQuery);

var BPOPUP='';
	(function($) {
		$(function() {
			  $('#btn_appro').bind('click', function(e) {
				  e.preventDefault();
				  BPOPUP =  $('#pop_appro').bPopup({
					   modalClose : true,
					   positionStyle:'fixed',
				   });
				});    
		 });
	})(jQuery);

var BPOPUP='';
	(function($) {
		$(function() {
			  $('#btn_Shproduct').bind('click', function(e) {
				  e.preventDefault();
				  BPOPUP =  $('#pop_ShProduct').bPopup({
					   modalClose : true,
					   positionStyle:'fixed',
				   });
				});    
		 });
	})(jQuery);

var BPOPUP='';
	(function($) {
		$(function() {
			  $('#btn_Cate').bind('click', function(e) {
				  e.preventDefault();
				  BPOPUP =  $('#pop_Cate').bPopup({
					   modalClose : true,
					   positionStyle:'fixed',
				   });
				});    
		 });
	})(jQuery);

var BPOPUP='';
	(function($) {
		$(function() {
			  $('#btn_cancle').bind('click', function(e) {
				  e.preventDefault();
				  BPOPUP =  $('#pop_cancle').bPopup({
					   modalClose : true,
					   positionStyle:'fixed',
				   });
				});    
		 });
	})(jQuery);

var BPOPUP='';
	(function($) {
		$(function() {
			  $('#btn_cancle2').bind('click', function(e) {
				  e.preventDefault();
				  BPOPUP =  $('#pop_cancle2').bPopup({
					   modalClose : true,
					   positionStyle:'fixed',
				   });
				});    
		 });
	})(jQuery);

var BPOPUP='';
	(function($) {
		$(function() {
			  $('#btn_resend').bind('click', function(e) {
				  e.preventDefault();
				  BPOPUP =  $('#pop_resend').bPopup({
					   modalClose : true,
					   positionStyle:'fixed',
				   });
				});    
		 });
	})(jQuery);

var BPOPUP='';
	(function($) {
		$(function() {
			  $('#btn_changeDate').bind('click', function(e) {
				  e.preventDefault();
				  BPOPUP =  $('#pop_changeDate').bPopup({
					   modalClose : true,
					   positionStyle:'fixed',
				   });
				});    
		 });
	})(jQuery);

var BPOPUP='';
	(function($) {
		$(function() {
			  $('.memo_a').bind('click', function(e) {
				  e.preventDefault();
				  BPOPUP =  $('#pop_memo').bPopup({
					   modalClose : true,
					   positionStyle:'fixed',
				   });
				});    
		 });
	})(jQuery);

var BPOPUP='';
	(function($) {
		$(function() {
			  $('#btn_memo').bind('click', function(e) {
				  e.preventDefault();
				  BPOPUP =  $('#pop_memo').bPopup({
					   modalClose : true,
					   positionStyle:'fixed',
				   });
				});    
		 });
	})(jQuery);

var BPOPUP='';
	(function($) {
		$(function() {
			  $('#btn_service').bind('click', function(e) {
				  e.preventDefault();
				  BPOPUP =  $('#pop_service').bPopup({
					   modalClose : true,
					   positionStyle:'fixed',
				   });
				});    
		 });
	})(jQuery);

var BPOPUP='';
	(function($) {
		$(function() {
			  $('#btn_history').bind('click', function(e) {
				  e.preventDefault();
				  BPOPUP =  $('#pop_history').bPopup({
					   modalClose : true,
					   positionStyle:'fixed',
				   });
				});    
		 });
	})(jQuery);

var BPOPUP='';
	(function($) {
		$(function() {
			  $('#btn_sms').bind('click', function(e) {
				  e.preventDefault();
				  BPOPUP =  $('#pop_sms').bPopup({
					   modalClose : true,
					   positionStyle:'fixed',
				   });
				});    
		 });
	})(jQuery);


var BPOPUP='';
	(function($) {
		$(function() {
			  $('#add_new').bind('click', function(e) {
				  e.preventDefault();
				  BPOPUP =  $('#pop_addnew').bPopup({
					   modalClose : true,
					   positionStyle:'fixed',
				   });
				});    
		 });
	})(jQuery);

function move_item(){

   
    $(".move_btn_wrap .move_btn").click(function(e){
        var chk = 0;
        $(".move_item_list input[type=checkbox]").each(function(index, item){
            if($(item).is(":checked")){
                $(".move_select_item tr").each(function(s_index, s_item){
                    if($(item).parents("tr").data("idx") == $(s_item).data("idx")){
                        chk++;
                    } 
                });
                if(chk == 0 ){  
                    var len = $(".move_select_item tr").length+1;                
                    var tr = $(item).parents("tr").clone()
                    tr = tr.append("<td>2020-02-14 17:01</td>")
                    tr.find("input[type=checkbox]").prop("checked",false);
                    tr.find("td").eq(1).text(len);
                    $(".move_select_item tbody").append(tr)
                }
                chk = 0;
            }
        });
    });

}
	


	$(function(){
		$('.upload_text').val('');
		$('.input_file').change(function(){
			var i = $(this).val();
			$('.upload_text').val(i);
		});
	});


$(function () {
    $(".tab_content2").hide();
    $(".tab_content2:first").show();

    $("ul.tabs2 li").click(function () {
        $("ul.tabs2 li").removeClass("active").css("color", "");
        //$(this).addClass("active").css({"color": "darkred","font-weight": "bolder"});
        $(this).addClass("active");
        $(".tab_content2").hide()
        var activeTab = $(this).attr("rel");
        $("#" + activeTab).fadeIn()
    });
});


$(function() {

    $("#sortable").sortable();

    $("#sortable").disableSelection();

});











