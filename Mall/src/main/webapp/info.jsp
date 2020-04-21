<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<title>个人信息</title>
<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
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
	}
	.fieldset > *{
	width: 100%;
	margin-bottom:1rem;
	}
	
	header {
	margin: 0 auto;
	width:239;
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
				<h1>个人资料</h1>
			
				<label for="username">用户名</label>
					<input name="username" id="username" style="cursor: not-allowed;background-color: #eee;" type="text" class="text" value="孙悟空" readonly="readonly">
				<label >性别</label>
				<div id="gender">
					<input id="gender-male" type="radio" name="gender" value="1" checked="checked">男
					<input id="gender-female" type="radio" name="gender" value="0">女
				</div>
				<label>邮箱</label>
				<input id="email" name="email" required="required" class="text" type="text" placeholder="请输入邮箱" />
				<label >手机</label>
				<input id="phone" name="phone" class="text" type="text" placeholder="请输入手机" />
			<input id="change-btn" type="button" value="修改"/>
		
	</div>
</form>
<script type="text/javascript">

	//页面初始化时加载用户数据
	$(document).ready(function() {
		// 将请求提交到哪里
		var url = "info";
		// 发出ajax请求，并处理结果
		$.ajax({
			"url": url,
			"type": "POST",
			"dataType": "json",
			"success": function(json) {
				var user = json.data;
				$("#username").val(user.username);
				$("#phone").val(user.phone);
				$("#email").val(user.email);
				if (user.gender == 0) {
					$("#gender-female").attr("checked", "checked");
				}
				if (user.gender == 1) {
					$("#gender-male").attr("checked", "checked");
				}
			},"error":function(){
			}
		});
	});

	$("#change-btn").click(function(){
		if(
			document.getElementById("phone").value != ""&
			document.getElementById("email").value != ""
		){
			if(!/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,5})$/.test(document.getElementById("email").value)){
				alert("邮箱格式不正确");
				return;
			}
			if(!/^\d{10}$/.test(document.getElementById("phone").value)){
				alert("电话格式不正确");
				return;
			}
			var url = "changeInfo";
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
					}else{
						alert("修改密码失败！"+json.message);
					}
						
				}
			});
		}else{
			alert("请确认资料已输入完全");
		}
	});
</script>
</body>
</html>

