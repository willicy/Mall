<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<title>登录</title>
<link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet">
<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="jquery.cookie.js"></script>
<style type="text/css">
	*{
		font-family: 'Microsoft YaHei';
	}
	a{
		text-decoration: none;
		color: inherit;
		
	}
	fieldset{ 
	width:300;
	height:250;
	border-radius: 8px;
	float: none;
	margin: 0 auto;
	margin-top: 110;
	
	}
	 span {
	 
	 margin-left:   78px;
	}
	fieldset > a{
		margin-left: 240;
	}
	.text{
	
	margin: 0 auto;
	display:block;
	 }
	 #login-btn{
	margin: 0 auto;
	display:block;
	width: 100;
	 }
	
	
</style>
</head>
<body>
<div style="margin:0 auto; width: 66px;" ><h1><a href="index.jsp" >主页</a></h1></div>

<form id="login-form" action="">
	<fieldset>
		<legend   style="font-size:30px;margin-bottom: 30">登录</legend>
			<span >用户名</span><br>
			<input id="username" name="username" class="text" id="text" type="text" placeholder="请输入用户名"/><br>
			<span >密码</span><i style="margin-left: 96px;" class="fa fa-eye" onclick="showhide()" id="eye"></i>
			<input id="pwd" name="password" class="text" id="text" type="password" placeholder="请输入密码" /><br>
			
			<input id="login-btn" type="button" value="登录"/>
			<a href="register.jsp">注册</a>
		
	</fieldset>
</form>
<script type="text/javascript">
var pwd = document.getElementById("pwd");
var pwd_check = document.getElementById("pwd-check");
var pwd_old = document.getElementById("pwd-old");
	function showhide(){
	
	        if (pwd.type == "password") {
	            pwd.type = "text";
	            eye.className='fa fa-eye-slash'
	        }else {
	            pwd.type = "password";
	            eye.className='fa fa-eye'
	        }
	}
	$("#login-btn").click(function(){
		if(
				document.getElementById("username").value != ""&
				document.getElementById("pwd").value != ""
		){
			var url = "login";
			// 请求参数
			var data = $("#login-form").serialize();
			// 发出ajax请求，并处理结果
			$.ajax({
				"url": url,
				"data": data,
				"type": "POST",
				"dataType": "json",
				"success": function(json) {
					if(json.state == 200){
						alert("登录成功！");
						$.cookie("avatar", json.data.avatar, {
							expires: 7
						});
					
						$.cookie("username", json.data.username, {
							expires: 7
						});
						window.location.replace("index.jsp");
					}else{
						alert("登录失败！"+json.message);
					}
						
				}
			});
		}else{
			alert("用户名/密码不得为空");
		}
	});
</script>
</body>
</html>

