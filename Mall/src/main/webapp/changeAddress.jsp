<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<title>更改地址</title>
<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
<style type="text/css">
	*{
		font-family: 'Microsoft YaHei';
	}
	a{
		text-decoration: none;
		color: inherit;
		
	}
	fieldset{ 
	width:440;
	height:420;
	border-radius: 8px;
	float: none;
	margin: 0 auto;
	margin-top: 110;
	
	}
	label{
		margin-left: 40;
	}
	header {
	margin: 0 auto;
	width:168;
	}
	a{
	margin-left: 380;
	}
	.right{
	 	float: left;
	}
	.left{
		margin-left:40;
		width:200;
		float: left;
	}
	.text{
		margin: 0 auto;
		display:block;
		width: 360;
	}

	.form-group{
		margin: 0 auto;
		
		display:block;
		clear:both;
		
	}
	.form-control{
		width: 133;
	}
	#buttons{
		width: 360;
		margin: 0 auto;
		margin-top:88;
		display:block;
		clear:both;
	}
</style>
</head>
<body>


<form id="address-form" action="">
	

	<fieldset>
		<legend   style="font-size:30px;margin-bottom: 10">修改地址</legend>
		<a href="address.jsp">返回</a>
				<div class="form-group">
					<label>收货人</label><br>
					<input name="name"id="username" style="margin-bottom: 17;" type="text" class="text" placeholder="请输入收货人姓名" >
				</div>
				<div class="form-group">
					<div class="left">
						<span >省/直辖市</span><br>
						<select name="province" class="form-control" data-province="---- 选择省 ----" id="provinces">
							<option value="0">--- 请选择 ---</option>
						</select>
					</div>
					<div class="right">
						<span >城市</span><br>
						<select name="city" class="form-control" data-city="---- 选择市 ----" id="cities">
							<option id="city_option" value="0">--- 请选择 ---</option>
						</select>
					</div>
				</div>
				<div class="form-group">
							
					<div class="left">
						<span >区县</span><br>
						<select name="area" class="form-control" data-district="---- 选择区 ----" id="areas">
							<option id="area_option" value="0">--- 请选择 ---</option>
						</select>
					</div>
					<div class="right">
						<span >邮政编码</span><br>
						<input id="zip"  name="zip" type="text" maxlength="6" class="form-control" placeholder="请输入邮政编码">
					</div>
				</div>
				<div class="form-group" style="margin-top: 55;margin-bottom: 20">
					<label>详细地址</label><br>
					<div class="col-md-8">
						<textarea id="address"  class="text" name="address" class="form-control" rows="3" placeholder="输入详细的收货地址，小区名称、门牌号等"></textarea>
					</div>
				</div>
				<div class="form-group">
					<div class="left">
						<span >手机</span><br>
						<input id="phone"  name="phone" type="text" class="form-control" placeholder="请输入手机号码">
					</div>
					<div class="right">
						<span >地址类型</span><br>
						<input id="tag"  name="tag" type="text" class="form-control" placeholder="请输入地址类型，如：家、公司或者学校">
					</div>
				</div>
				<div class="form-group">
					<div id="buttons">
						<input style="margin-right: 269" id="btn-submit" type="button" class="col-md-1 btn btn-primary" value="保存" />
						<input type="reset" class="col-md-offset-1 col-md-1 btn btn-primary" value="重置" />
					</div>
				</div>

	</fieldset>
</form>
<script type="text/javascript">
var provincesList = [];
	//加载省的列表
	function getProvinceList() {
		// 清空当前列表
		$("#provinces").empty();
		// 添加默认选项
		var op = '<option value="0" id="province_option">--- 请选择 ---</option>';
		$("#provinces").append(op);
		
		// 将请求提交到哪里
		var url = "district";
		var data="parent=86";
		// 发出ajax请求，并处理结果
		$.ajax({
			"url": url,
			"data": data,
			"type": "POST",
			"dataType": "json",
			"success": function(json) {
				// 遍历加载新列表项
				var list = json.data;
				
				for (var i = 0; i < list.length; i++) {
					
					op = '<option value="' 
						+ list[i].code + '">' 
						+ list[i].name + '</option>';
					$("#provinces").append(op);
					// <select>
					// 		<option value="1">第1项</option>
					// 		<option value="2">第2项</option>
					// 		<option value="3">第3项</option>
					// </select>
				}
			}
		});
	}
	
	// 加载市的列表
	function  getCityList(address) {
		
		// 清空当前列表
		$("#cities").empty();
		// 添加默认选项
		var op = '<option value="0"  id="city_option">--- 请选择 ---</option>';
		$("#cities").append(op);
		
		// 获取所选择的省
		var code = $("#provinces").val();
		//console.log(code);
		// 判断省的代号的值
		if (code == 0) {return;}
		
		
		// 将请求提交到哪里
		var url = "district";
		var data="parent="+code;
		// 发出ajax请求，并处理结果
		$.ajax({
			"url": url,
			"data": data,
			"type": "POST",
			"dataType": "json",
			"success": function(json) {
				// 遍历加载新列表项
				
				var list = json.data;
				for (var i = 0; i < list.length; i++) {
					
					var op = '<option value="' 
						+ list[i].code + '">' 
						+ list[i].name + '</option>';
					$("#cities").append(op);
				}
				if(address){
					var cities = document.querySelectorAll("#cities option");
					cities.forEach(function(city){
						
						if(city.value == address.city){
							city.selected = true;
						} 
					});
					getAreaList(address);
				}
			}
		});
	}
	
	// 加载区的列表
	function getAreaList(address) {
		// 清空当前列表
		$("#areas").empty();
		// 添加默认选项
		var op = '<option id="area_option" value="0">--- 请选择 ---</option>';
		$("#areas").append(op);
		
		// 获取所选择的市
		var code = $("#cities").val();
		
		// 判断市的代号的值
		if (code == 0) {
			return;
		}
		
		// 将请求提交到哪里
		var url = "district";
		var data = "parent="+code;
		// 发出ajax请求，并处理结果
		
		$.ajax({
			"url": url,
			"data": data,
			"type": "POST",
			"dataType": "json",
			"success": function(json) {
				// 遍历加载新列表项
				var list = json.data;
				for (var i = 0; i < list.length; i++) {
					
					var op = '<option value="' 
						+ list[i].code + '">' 
						+ list[i].name + '</option>';
					$("#areas").append(op);
				}
				if(address){
				var areas = document.querySelectorAll("#areas option");
				areas.forEach(function(area){
					if(area.value == address.area){
						area.selected = true;
					} 
				});
				}
			}
		});
	}
	
	// 当页面加载完成时
	$(document).ready(function() {
		
		/* // 加载省的列表
		 */
			
		getProvinceList();
		showList();
		
		
		// 为省的列表绑定change事件
		$("#provinces").change(function(){
			getCityList();
			
			// 清空区列表
			$("#areas").empty();
			// 添加默认选项
			var op = '<option value="0">--- 请选择 ---</option>';
			$("#areas").append(op);
		});
		
		// 为市的列表绑定change事件
		$("#cities").change(function() {
			// 加载区的列表
			getAreaList();
		});
	});
	
	function  showList(){
		// 将请求提交到哪里
		
		var url = "changeAddress";
		var data =location.search.substring(1)+"&function=list";
		// 发出ajax请求，并处理结果
		$.ajax({
			"url": url,
			"data":data,
			"type": "POST",
			"dataType": "json",
			"success": function(json) {
				var address = json.data;
				$("#username").val(address.name);
				$("#phone").val(address.phone);
				$("#zip").val(address.zip);
				$("#address").val(address.address);
				$("#tag").val(address.tag);
				
				var provinces = document.querySelectorAll("#provinces option");
				provinces.forEach(function(province){
					if(province.value == address.province){
						province.selected = true;
					} 
				});
				getCityList(address);
				
			},"error":function(){
				
			}
		});
	}
	
	
	
	
	
	// 为提交按钮绑定单击事件
	$("#btn-submit").click(function() {
		// 检查提交的数据
		if (
				$("#provinces").val() == 0 |
				$("#cites").val() == 0 |
				$("#areas").val() == 0 |
				$("#username").val() == "" |
				$("#zip").val() == "" |
				$("#address").val() == "" |
				$("#phone").val() == "" |
				$("#tag").val() == "" 
			) {
			
			alert("请重新确认已输入完全");
			return;
		}
		
		if(!/^\d{10}$/.test($("#phone").val())){
			alert("电话格式不正确");
			return;
		}
		
		
		// 将请求提交到哪里
		var url = "changeAddress";
		// 请求参数
		var data = $("#address-form").serialize()+"&"+location.search.substring(1)+"&function=update";
		console.log("收货地址参数：" + data);
		// 发出ajax请求，并处理结果
		$.ajax({
			"url": url,
			"data": data,
			"type": "POST",
			"dataType": "json",
			"success": function(json) {
				if (json.state == 200) {
					alert("保存成功！");
					window.location.replace("address.jsp");
				} else {
					alert(json.message);
				}
			},
			"error": function(xhr, text, errorThrown) {
				console.log("xhr.status=" + xhr.status);
				// 3xx 4xx 5xx
				alert("您的登录信息已经过期！请重新登录！");
			}
		});
	});
</script>
</body>
</html>

