<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<%@ include file="header.jsp" %>
	
	<c:if test="${empty sessionScope}">
    <div class="ui container login-screen">
    	<div class="ui-login-field">
        <form class="ui form" name="login_form">
            <div class="field">
              <label>会員ID</label>
              <input type="text" name="member_name" placeholder="UserId" maxlength="6">
            </div>
            <div class="field">
              <label>Password</label>
              <input type="password" name="member_password" placeholder="Password" maxlength="20">
            </div>
            
            <button class="ui button" id="btn_login" type="button">Submit</button>
          </form>
          <span id="error_message" style="color: red;"></span>
          </div>
      </div>
      </c:if>
      
     <c:if test="${!empty sessionScope}">
      	<div class="ui card" id="member_id_card">
		  <div class="content">
		    <div class="header" id="access_member">${sessionScope.member_number} 様</div>
		  </div>
		  <div class="content">
		    <h4 class="ui sub header" id="point"><b>現在ポイント</b>　：　${sessionScope.point}</h4>
		    <div class="ui small feed">
		      <div class="event">
		        <div class="content">
		          <div class="summary">
		             <h4>ご案内</h4>
		          </div>
		        </div>
		      </div>
		      <div class="event">
		        <div class="content">
		          <div class="summary">
		             本ゲームは3桁の数字を当てるゲームです。
		          </div>
		        </div>
		      </div>
		      <div class="event">
		        <div class="content">
		          <div class="summary">
		             得たポイントは記録のみで楽しんでください。
		          </div>
		        </div>
		      </div>
		    </div>
		  </div>
		  <div class="extra content">
		    <button class="ui button" id="btn_logout" type="button">Logout</button><button class="ui button" id="btn_join" type="button">Join Game</button>
		  </div>
		</div>
      </c:if>
</body>
<script>
	let login_id = $("input[name='member_name']");
	let login_pw = $("input[name='member_password']");

	$(document).ready(function(){
		login_id.focus();

		// Login
		$("#btn_login").click(function(){
			if(login_id.val()==""){
				$('#error_message').text("会員情報が一致しません。");
				login_fail_msg("会員IDを入力してください");
				return;	
			}
			if(login_pw.val()==""){
				$('#error_message').text("パスワードを入力してください");
		    	login_pw.val("");
				login_pw.focus();
				return;	
			}
		
			$.ajax({
                type : "POST",
                url : "/login",
                data : {
                	"member_name" : login_id.val(),
                	"member_password" : login_pw.val()
                },
                success : function(res){
                	if(res=='success'){
                		location.reload();
                	}else if(res=='fail'){
                		
                		login_fail_msg('会員情報が一致しません。');
                	}else{
                		login_fail_msg('非正常的なアクセスです。');
                	}
                },
                error : function(e){
                	login_fail_msg("サーバーとの通信に失敗しました。");
                }
            });
		});
		
		// logout
		$('#btn_logout').click(function(){
			$.ajax({
				url : "/logout",
				type : "POST",
				success : function(res){
					BootstrapModalWrapperFactory.alert({
						title: "警報",
						message:res	
					});
					location.reload();
				},
				error : function(e){
					login_fail_msg("サーバーとの通信に失敗しました。");
				}
			});
		});
			
		$('#btn_join').click(function(){
			$.ajax({
				url : "/joingame",
				type : "POST",
				success : function(res){
					if(res=='1'){
						BootstrapModalWrapperFactory.alert({
							title: "警報",
							message:"本日の挑戦記録があります。\n後で挑戦してください。"	
						});
					}else{
						location.replace("maingame");
					}
				},
				error : function(e){
					login_fail_msg("サーバーとの通信に失敗しました。");
				}
			});
			
		});
		
		
		login_id.on('keydown', function(key) {
			if(key.keyCode == 13) {
				key.preventDefault();
				login_pw.focus();
			}
		});
		login_pw.on('keydown', function(key) {
			if(key.keyCode == 13) {
				key.preventDefault();
				$("#btn_login").click();
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
	
	function login_fail_msg(msg){
		$('#error_message').text(msg);
    	login_id.val("");
    	login_pw.val("");
    	login_id.focus();
	}
	
	
</script>
</html>


