<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<title>头像</title>
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
	
	 background: transparent;
	 padding: .5rem 1rem;
	 margin-bottom: 1rem;
	 outline: none;
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
	
	margin-bottom:1rem;
	}
	header {
	margin: 0 auto;
	width:239;
	}
	
	

	#upload-btn{
	margin: 0 auto;
	display:block;
	width: 100;
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

<form id="change-form" enctype="multipart/form-data" >
	
	<div style="margin: 0 auto;"  class="fieldset">
		<h1>头像</h1>
			
		<label style="margin: 0 auto;">选择头像</label>
		<img style="margin: 0 auto;margin-bottom: 30;" class="text" id="img-avatar" src="./images/user.jpg" width="70" />
		<input id="file" style="margin-left:153px;margin-bottom: 30; " class="text" type="file" name="file">
		<input class="text" id="upload-btn" type="button" value="上传" />
						
	</div>
</form>
<script type="text/javascript">

	$(document).ready(function() {
		if ($.cookie("avatar") != null) {
			$("#img-avatar").attr("src", $.cookie("avatar"));
		}
	});
	
	$("#upload-btn").click(function() {
		
		var str=document.getElementById("file").value.split("\\")[document.getElementById("file").value.split("\\").length-1];
		if (str.match(/[\u0391-\uFFE5]+/g)!=null){
			
			alert('文件名不能包含汉字！');
			return;
		}
		
		
		var url = "changeAvatar";
		var data = new FormData($("#change-form")[0]);
		$.ajax({
			"url": url,
			"data": data,
			"type": "POST",
			"dataType": "json",
			"contentType": false,
			"processData": false,
			"success": function(json) {
				if (json.state == 200) {
					alert("修改头像成功！");
					$("#img-avatar").attr("src", json.data);
					$.cookie("avatar", json.data, {
						expires: 7
					});
				} else {
					alert(json.message);
				}
			},
			"error": function() {
				alert("修改头像出错！");
			}
		});
	});
</script>
</body>
</html>

