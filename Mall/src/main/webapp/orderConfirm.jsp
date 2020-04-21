<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<title>确认订单</title>
<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
 <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
	
<style type="text/css">
	table{
		margin:0 auto;
		margin-top:20;
		margin-bottom:20;
		border-collapse: separate;
  		border-spacing: 0;
  		border-radius: 0.5em;
  		border: solid 0.2em #dfdfdf;
  		width: 1540;
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
	width:236;
	}
	body>a{
		margin-left: 160;	
	}
</style>
</head>
		<body>

		<div class="col-md-offset-1 col-md-10" style=" margin:0 auto;">


			<div class="col-md-12">
				<form id="form-create-order" role="form">
					<div class="form-group" style="margin-top:60px;">
						<label for="name">
							<h4>选择收货地址：</h4></label>
						<select name="address" id="address-list" class="form-control">
							
						</select>

					</div>
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">订单商品信息：</h3>
						</div>
						<div class="panel-body">
							<table class="cart-table" width="100%">
								<thead>
									<tr>

										<th width="110"></th>
										<th width="39%">商品</th>
										<th width="21%">单价</th>
										<th width="25%">数量</th>
										<th width="21%">金额</th>

									</tr>
								</thead>
								<tbody id="order-list" class="cart-body">
									

								</tbody>
							</table>
							<div class="total-bar">
								&nbsp;
								<span class="pull-right">已选商品
								<span id="selectCount"></span>件 总价¥
								<span id="selectTotal"></span>元

								</span>

							</div>
						</div>
					</div>
					<div class="pay-bar">
						<a href="cart.jsp">返回购物车</a>
						<span class="pull-right"><input id="btn-submit" type="button" value="在线支付" class="btn btn-primary btn-lg link-pay"/></span>
					</div>

				</form>

			</div>
		</div>

	
	
		<script type="text/javascript"> 
		var address_flag=0;
		$(document).ready(function(){
			
			showAddressList();
			showCartList();

			$("#btn-submit").click(function(){
				createOrder();
			});
		});
		function showCartList(){
			var url="cart";
			var data=location.search.substring(1)+"&function=get_by_ids";
			$.ajax({
				"url":url,
				"data":data,
				"type":"GET",
				"dataType":"json",
				"success":function(json){
					$("#order-list").empty();
					
					var count=0;
					var allPrice=0;
					var list =json.data;
					
					for(var i=0;i<list.length;i++){
						count+=list[i].count;
						allPrice+=list[i].count*list[i].newPrice;
						var html = '<tr>'
							+'<td><input type="hidden" name="cart_id" value="{id}" /><img src=".{image}collect.png" width="110" /></td>'
							+'<td>{title}</td>'
							+'<td>¥<span>{new_price}</span></td>'
							+'<td>{count}</td>'
							+'<td><span>{total_price}</span></td>'
							+'</tr>';
							

						html = html.replace("{id}",list[i].id);
						html = html.replace("{image}",list[i].image);
						html = html.replace("{title}",list[i].title);
						html = html.replace("{new_price}",list[i].newPrice);
						html = html.replace("{count}",list[i].count);
						html = html.replace("{total_price}",list[i].count*list[i].newPrice);
						
						$("#order-list").append(html);
					}
					$("#selectCount").html(count);
					$("#selectTotal").html(allPrice);
				}
			});
		}
		function showAddressList(){
			var url="address";
			$.ajax({
				"url":url,
				"type":"POST",
				"dataType":"json",
				"success":function(json){
					$("#address-list").empty();
					
					if(json.data!=""){
						address_flag=1;
						var list = json.data;
						
						for(var i =0;i<list.length;i++){
							var op='<option value="'+list[i].id+'">'
							+list[i].name+'/'
							+list[i].tag+'/'
							+list[i].phone+'/'
							+list[i].district+', '
							+list[i].address
							+'</option>';
							console.log(op);
							$("#address-list").append(op);
						}
					}else{
						alert("请先添加地址！即将跳转到添加地址页面");
						window.location.replace("address.jsp");
					}
				}
				
			});
		}
		function createOrder(){
			
		
			if(address_flag==1){
				
				var url = "order";
				var data=$("#form-create-order").serialize();
			
				
				$.ajax({
					"url":url,
					"data":data,
					"type":"POST",
					"dataType":"JSON",
					"success":function(json){
						if(json.state==200){
							alert("成功");
							window.location.replace("index.jsp");
						} else {
							alert(json.message);
						}
					},
					"error":function(){
						alert("您的登录信息已经过期！请重新登录！");
					}
				});
			}else{
				alert("请先添加用户地址");
			}
		}
		</script>
		
	</body>

</html>