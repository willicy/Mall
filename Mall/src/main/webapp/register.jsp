<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<title>注册</title>
<link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet">
<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
<style type="text/css">
	a{
		text-decoration: none;
		color: inherit;
		
	}
	fieldset{ 
	width:500;
	height:300;
	border-radius: 8px;
	float: none;
	margin: 0 auto;
	margin-top: 110;
	
	}

	
	.text{
		display: table;
	}

	#gender{
	margin-top:1;
	margin-bottom: 21;
	margin-left: 43;
	}
	#reg-btn{
	float: left;
	margin-left:210;
	display:block;
	width: 80;
	}
	#pwd-remind{
		font-size:12;
	}
	#reg-form-left{
		float: left;
		margin-left: 60;
		
	}
	#reg-form-right{
		float: right;
		margin-right: 60;
	}
</style>
</head>
<body>

<form id="reg-form" action="">
	<fieldset>
		<legend align="center"  style="font-size:30px;margin-bottom: 30">注册</legend>
			<div id="reg-form-left">
				<span >用户名</span><br>
				<input id="username" name="username" class="text " type="text" placeholder="请输入用户名"/><br>
				<div style="display: flex;   justify-content:space-between;"><span >密码</span><i  class="fa fa-eye" onclick="showhide()" id="eye"></i></div>
				<input id="pwd" name="password" class="text" type="password"  onkeyup="validate()" placeholder="请输入密码" /><br>
				<span >确认密码</span><span id="pwd-remind"></span><br>
				<input id="pwd-check" class="text"type="password" placeholder="确认密码"  onkeyup="validate()" /><br>
			</div>
			<div id="reg-form-right">
				<span >性别</span><br>
				<div id="gender">
					<input id="gender-male" type="radio" name="gender" value="1" checked="checked">男
					<input id="gender-female" type="radio" name="gender" value="0">女<br>
				</div>
				<span >邮箱</span><br>
				<input id="email" name="email" required="required" class="text" type="text" placeholder="请输入邮箱" /><br>
				<span >手机</span><br>
				<input id="phone" name="phone" class="text" type="text" placeholder="请输入手机" /><br>
			</div>
			<input id="reg-btn" type="button" value="注册"/><a style="float: left;margin-left: 117px;" href="login.jsp">登录</a>
		
	</fieldset>
</form>
<script type="text/javascript">
	
	function validate(){
		var pwd = document.getElementById("pwd").value;
		var pwd1 = document.getElementById("pwd-check").value;
		 if(pwd == pwd1){
			document.getElementById("pwd-remind").innerHTML="<font color='green'>两次密码相同</font>";
			document.getElementById('reg-btn').disabled=false;
		}else {
	    	document.getElementById("pwd-remind").innerHTML="<font color='red'>两次密码不相同</font>";
	    	document.getElementById('reg-btn').disabled=true;
	
	     }
	}

	$("#reg-btn").click(function(){
		if(
				document.getElementById("username").value != ""&
				document.getElementById("pwd").value != ""&
				document.getElementById("pwd-check").value != ""&
				document.getElementById("phone").value != ""&
				document.getElementById("email").value != ""
		){
			

			if(!/^\w{1,12}$/.test(document.getElementById("username").value)){
				alert("用户名格式为 字母、数字、下划线、空格的任意组合，最大长度为12位。");
				return;
			}
			if(!/^(?=.*[a-zA-Z])(?=.*\d)[A-Za-z\d]{8,16}$/.test(document.getElementById("pwd").value)){
				alert("密码至少8个字符，至少1个字母和1个数字,不能包含特殊字符，最大长度为16位。");
				return;
			}
			if(!/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,5})$/.test(document.getElementById("email").value)){
				alert("邮箱格式不正确");
				return;
			}
			if(!/^\d{10}$/.test(document.getElementById("phone").value)){
				alert("电话格式不正确");
				return;
			}
			var url = "register";
			// 请求参数
			var data = $("#reg-form").serialize();
			// 发出ajax请求，并处理结果
			$.ajax({
				"url": url,
				"data": data,
				"type": "POST",
				"dataType": "json",
				"success": function(json) {
					if(json.state == 200){
						alert("注册成功！");
						window.location.replace("login.jsp");
					}else{
						alert("注册失败！"+json.message);
					}
						
				}
			});
		}else{
			alert("请确认资料已输入完全");
		}
	});
	var eye = document.getElementById("eye");
	var pwd = document.getElementById("pwd");
	var pwd_check = document.getElementById("pwd-check");
	function showhide(){
	
	        if (pwd.type == "password") {
	            pwd.type = "text";
	            pwd_check.type = "text";
	                eye.className='fa fa-eye-slash'
	        }else {
	            pwd.type = "password";
	            pwd_check.type = "password";
	            eye.className='fa fa-eye'
	        }
	}
</script>
</body>
</html>

