<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<title>更改密码</title>
<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
<link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet">
<style type="text/css">
*{
	font-family: 'Microsoft YaHei';
}
a{
	text-decoration: none;
	color: inherit;
	
}

form{
	display: flex;
	justify-content: center;
	align-items: center;
	padding-top: 2rem;
	width: 100vw;
}
label{
	margin-bottom: .5rem;
}
input{
border: 1px solid #999;
 background: transparent;
 padding: .5rem 1rem;
 margin-bottom: 1rem;
 outline: none;
}
input:active,input:focus{
	border: green 1px solid;
}
	.fieldset{ 
	width: 500px;
	border-radius: 8px;
	display: flex;
	flex-direction: column;
	box-shadow: 0px 2px 3px 0px rgba(0,0,0,.3);
	padding: 1rem;
	margin: 0 auto;
	}
	.fieldset > *{
	width: 100%;
	margin-bottom:1rem;
	}
	header {
	margin: 0 auto;
	width:239;
	}
	
	
	.bar{
	color:balck;
	background-color: C0C0C0;
	text-decoration: none;
	}
	#pwd-remind{
		font-size:12;
	}
	
	 
</style>
</head>
<body>
<h1><a href="index.jsp" style="float: right;margin-right: 200px">返回主页</a></h1>
<header class="nav">
	<a href="changePassword.jsp" >修改密码</a> 
    <a href="info.jsp" >个人资料</a> 
    <a href="avatar.jsp" >头像</a>
    <a href="address.jsp">收货地址</a> 
</header>
<form id="change-form" action="">
	<div class="fieldset">
				<h1>修改密码</h1>
				<div style="display: flex;   justify-content:space-between;"><label  for="oldPassword">密码</label><i  class="fa fa-eye" onclick="showhide()" id="eye"></i></div>
				<input id="pwd-old" name="oldPassword" class="text" type="password" placeholder="请输入密码" />
				<label for="password">新密码</label>
				<input id="pwd" name="password" class="text" type="password" onkeyup="validate()" placeholder="请输入密码" />
				<label for="pwd-check">确认密码</label><span id="pwd-remind"></span>
				<input id="pwd-check" class="text" type="password" placeholder="确认密码"  onkeyup="validate()" />
			<input id="change-btn" disabled="disabled"  type="button" value="修改"/>
		
	</div>
</form>
<script type="text/javascript">

	var eye = document.getElementById("eye");
	var pwd = document.getElementById("pwd");
	var pwd_check = document.getElementById("pwd-check");
	var pwd_old = document.getElementById("pwd-old");
	function showhide(){
	
	        if (pwd.type == "password") {
	            pwd.type = "text";
	            pwd_check.type = "text";
	            pwd_old.type = "text";
	                eye.className='fa fa-eye-slash'
	        }else {
	            pwd.type = "password";
	            pwd_check.type = "password";
	            pwd_old.type = "password";
	            eye.className='fa fa-eye'
	        }
	}
	function validate(){
		
		 if(pwd.value == pwd_check.value){
			document.getElementById("pwd-remind").innerHTML="<font color='green'>两次密码相同</font>";
			document.getElementById('change-btn').disabled=false;
		}else {
			document.getElementById('change-btn').disabled=true;
	    	document.getElementById("pwd-remind").innerHTML="<font color='red'>两次密码不相同</font>";
	
	     }
	}

	$("#change-btn").click(function(){
		if(
				
				pwd.value != ""&
				pwd_check.value != ""&
				pwd_old.value != ""
		){
			if(pwd.value == pwd_old.value){
				alert("新密码不能和旧密码相同");
				return;
			}
			if(!/^(?=.*[a-zA-Z])(?=.*\d)[A-Za-z\d]{8,16}$/.test(document.getElementById("pwd").value)){
				alert("密码至少8个字符，至少1个字母和1个数字,不能包含特殊字符，最大长度为16位。");
				return;
			}
			var url = "changePassword";
			// 请求参数
			var data = $("#change-form").serialize();
			// 发出ajax请求，并处理结果
			$.ajax({
				"url": url,
				"data": data,
				"type": "POST",
				"dataType": "json",
				"success": function(json) {
					if(json.state == 200){
						alert("修改成功！");
						window.location.replace("index.jsp");
					}else{
						alert("修改密码失败！"+json.message);
					}
						
				}
			});
		}else{
			alert("请输入 要修改内容");
		}
	});
</script>
</body>
</html>

