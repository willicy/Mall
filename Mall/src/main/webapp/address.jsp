<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<title>地址</title>
<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
<style type="text/css">
	*{
	font-family: 'Microsoft YaHei';
}
	a{
		text-decoration: none;
		color: inherit;
		
	}

	#address-table{
	margin:0 auto;
	width: 1000;
	}
	table{
		margin:0 auto;
		margin-top:110;
		border-collapse: separate;
  		border-spacing: 0;
  		border-radius: 0.5em;
  		border: solid 0.2em #dfdfdf;
  		width: 100%;
	}
	th { 
		text-align:left;
		padding:5px;
	} 
	td { 
		font-size: 14px;
		border-top: 1.5px solid #ddd;
		padding:5px;
	} 
	header {
	margin: 0 auto;
	width:239;
	}
	body>a{
		margin-left: 450;	
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

<!--地址显示-->
				<div id="address-table">
					<table class="">
						<caption style="font-size: 30">收货地址列表</caption>
						<thead>
							<tr>
								<th>地址类型</th>
								<th>收货人姓名</th>
								<th style="width:300">详细地址</th>
								<th>联系电话</th>
								<th colspan="3">操作</th>
								
							</tr>
						</thead>
						<tbody id="list">
							
						</tbody>
						
					</table>
					<a href="addAddress.jsp" class="">新增收货地址</a>
					</div>
<script type="text/javascript">
//当页面完成加载时
$(document).ready(function() {
	showList();
});

function showList(){
	// 加载当前用户的收货地址列表
	var url = "address";
	$.ajax({
		"url": url,
		"type": "POST",
		"dataType": "json",
		"success": function(json) {
			var x=0;
			$("#list").empty();
			
			var list = json.data;
			
			for (var i = 0; i < list.length; i++) {
				
				var html = '<tr>'
					+ '<td>{tag}</td>'
					+ '<td>{name}</td>'
					+ '<td>{district} {address}</td>'
					+ '<td>{phone}</td>'
					+ '<td><a href="changeAddress.jsp?id={id}" ><span></span> 修改</a></td>'
					+ '<td><a href="javascript:deleteById({id});"><span></span> 删除</a></td>'
					+ '<td><input name="default_id" value="{id}" style="display:none;"><a {is_default} href="javascript:setDefault({id});">设为默认</a></td>'
					+ '</tr>';
					
				html = html.replace(/{id}/g, list[i].id);
				html = html.replace("{tag}", list[i].tag);
				html = html.replace("{name}", list[i].name);
				html = html.replace("{district}", list[i].district);
				html = html.replace("{address}", list[i].address);
				html = html.replace("{phone}", list[i].phone);
				
				if (list[i].isDefault == 1) {
					html = html.replace("{is_default}", 'style="display: none;"');
					x=1;
				} else {
					html = html.replace("{is_default}", '');
				}
				
				$("#list").append(html);
			}
			if(x==0){
				 setDefault($("input[name='default_id']").get(0).value);
			}
		}
	});
	
}
function deleteById(id){
	var url ="address";
	var data = "function=delete&id="+id;
	$.ajax({
		"url":url,
		"data":data,
		"type":"GET",
		"dataType":"json",
		"success":function(json){
			if(json.state == 200){
				showList();
				
			} else {
				alert(json.message);
			}
		},
		"error":function(){
			
			alert("您的登录信息已经过期！请重新登录！");
			/* location.href = "login.html"; */
		}
	});
	
}
function setDefault(id){
	
	var url ="address";
	var data = "function=default&id="+id;
	$.ajax({
		"url":url,
		"data":data,
		"type":"GET",
		"dataType":"json",
		"success":function(json){
			if(json.state == 200){
				showList();
				
			} else {
				
				alert(json.message);
			}
		},
		"error":function(){
			
			alert("您的登录信息已经过期！请重新登录！");
			/* location.href = "login.html"; */
		}
	});
}
</script>
</body>
</html>

