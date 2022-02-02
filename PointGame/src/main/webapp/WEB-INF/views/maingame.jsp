<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<div align="center">***${sessionScope.random_number}</div>

<div class="ui_maingame_layout">
	<form name="game_form">
	<input type="hidden" name="game_code" value="${status.game_code}">
	<input type="hidden" name="game_count" value="${status.game_count}">
		<img src="../resources/img/basewing.png">
		<div class="ui left icon input">
			<input type="text" value="" name="input_number" id="input_number" maxlength="3" onlyNum>
			<i class="gamepad icon"></i>
		</div>
		<button class="ui icon button" name="btn_confirm" type="button">
		  <i class="check icon"></i>
		</button>
		<div class="ui label">会員番号 ${status.member_number}   
		    <div class="detail">Point</div><div class="detail" id="user_point">${sessionScope.point}</div>
		</div>
	</form>
	
	<div class="ui relaxed divided list" id="play_list">
		<div class="item">
			<i class="bars icon"></i>
			<div class="content">
				<p class="header">回数</p>
				<div class="game_descript">入力数字</div>
				<div class="game_descript">結果</div>
			</div>
		</div>
		
		<c:if test="${list!=null}">
		<c:forEach items="${list}" var="savegame">
		<c:set var="count" value="${count + 1}"/>
			<div class="item">
				<i class="baseball ball icon"></i>
				<div class="content">
					<p class="header">${count}回目</p>
					<div class="game_descript">${savegame.save_i_num}</div>
					<div class="game_descript">${savegame.save_result}</div>
				</div>
			</div>
		</c:forEach>
		</c:if>
		
	</div>
</div>
</body>


<script>
	let user_num = $('input[name="input_number"]');
	
	$(document).ready(function(){
		user_num.focus();
		
		// 확인 이벤트
		$('button[name="btn_confirm"]').click(function(){
			let chk = effective_chk();
			var value_count = $("input[name='game_count']");
			if(chk){
				value_count.attr('value', Number(value_count.attr('value'))+1);
				var param = $("form[name='game_form']").serialize();
				
				$.ajax({
	                type : "POST",
	                url : "/maingame",
	                data : param,
	                error : function(){
	                	msg_focus("サーバーとの通信に失敗しました。");
	                }
	            }).done(function(msg){
	            	var obj = msg.split("|");
	            	div_increment(obj[0], obj[1], obj[2]);
	            	
	            	user_num.val("");
	        		user_num.focus();
	            	
	            	if(obj[3]=="1"){
	            		comp = true;
	            		$('button[name="btn_confirm"]').attr('disabled', true);
	    				$('input[name="input_number"]').attr('disabled', true);
	    				$('#user_point').text(obj[4]);
	    				
	    				
	    				var point = Number(obj[4]);
	    				var get_point = point - Number(${sessionScope.point});

	    				modal_on("成功", "おめでとうございます‼　" + get_point+"を獲得しました‼<br>現在ポイントは"+point+"です。");
	            	}
	            	if(value_count.attr('value')>=10 && obj[3]=="0"){
						$('button[name="btn_confirm"]').attr('disabled', true);
						$('input[name="input_number"]').attr('disabled', true);
						modal_on("失敗", "残念です。<br>明日また挑戦しましょう‼");
					}
	            })
			}
		});
		
		// 엔터 이벤트
		user_num.on('keydown', function(key) {
			if(key.keyCode == 13) {
				key.preventDefault();
				$('button[name="btn_confirm"]').click();
			}
		});
	});
	
	$(document).keydown(function(e){   
        if(e.target.nodeName != "INPUT" && e.target.nodeName != "TEXTAREA"){       
            if(e.keyCode === 8){   
            return false;
            }
        }
    });
	
	// 유효성 검사
	let regex = /[^0-9]/g;
	$('input[onlyNum]').on('keydown', function () {
	    $(this).val($(this).val().replace(regex, ""));
	});
	$('input[onlyNum]').on('keyup', function () {
	    $(this).val($(this).val().replace(regex, ""));
	});
	
	function effective_chk(){
		if(regex.test(user_num.val()) || user_num.val()==""){
			msg_focus("数字を入力してください。");
			return false;
		}

		if(user_num.val().length!=3){
			msg_focus("３桁数字を入力してください。");
			return false;
		}
		
		let num_chk = user_num.val().split("");
		if(num_chk[0]==num_chk[1] || num_chk[0]==num_chk[2] || num_chk[1]==num_chk[2]){
			msg_focus("数字は重複せずに記入してください。");
			return false;
		}
		return true;
	}
	
	function msg_focus(msg){
		BootstrapModalWrapperFactory.alert({
			title: "警報",
			message:msg	
		});
		user_num.val("");
		user_num.focus();
	}
	
	function div_increment(game_count, user_num, result){
		var insert_div = "";
		
		insert_div += '<div class="item">'
		insert_div += '<i class="baseball ball icon"></i>'
		insert_div += '<div class="content">'
		insert_div += '<p class="header">'+game_count+ '回目</p>'
		insert_div += '<div class="game_descript">'+ user_num + '</div>'
		insert_div += '<div class="game_descript">'+ result +'</div>'
		insert_div += '</div>'
		insert_div += '</div>'

		$("#play_list").append(insert_div);

	}
	
	function modal_on(subject, msg){
		
		BootstrapModalWrapperFactory.createModal({
	        title: subject,
	        message: msg,
	        closable: false,
	        closeByBackdrop: false,
	        buttons: [{
                label: "結果を見る",
                cssClass: "btn btn-primary",
                action: function () {
                	return this.hide();
                }
            },
            {
                label: "メインページへ",
                cssClass: "btn btn-primary",
                action: function () {
                	location.replace("");
                }
            }]
	    }).show();
	}
</script>
</html>