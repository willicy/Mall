<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<title>订单</title>
<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
 <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
	
<style type="text/css">
	table{
  		border-spacing: 0;
  		
  		border-top: solid 0.2em #dfdfdf;
  		border-bottom: solid 0.2em #dfdfdf;
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
	.orders-block{
		margin-bottom: 40px;
		border-collapse: separate;
  		border-spacing: 0;
  		border-radius: 0.5em;
  		border: solid 0.2em #dfdfdf;
	}
	#orders{
	width: 1540px;
	margin: 0 auto;
	}
</style>
</head>
		<body>
		
<div id="orders" class="col-md-10">
			<h1><a href="index.jsp" style="margin-left: 918px">主页</a></h1>
			

</div>
		
	
	
		<script type="text/javascript"> 
		$(document).ready(function(){
			showOrderList();
		});
		
		function showOrderList(){
			var url="orderList";
			$.ajax({
				"url":url,
				"type":"POST",
				"dataType":"json",
				"success":function(json){
					$("#orders").empty();
					if(json.state==500){
						alert(json.message);
					}
					var list =json.data;
					var html ='<h1 style="text-align:right;"><a href="index.jsp" >主页</a></h1>'
					$("#orders").append(html);
					for(var i=0;i<list.length;i++){
						var html ='<div class="orders-block" >'
							+'<div class="">'
							+'	<h4 class="">'
							+'		订单号：{id}，下单日期：{time}，收货人：{recv_name}'
							+'	</h4>'
							+'</div>'
							+'<div class="panel-body">'
							+'	<table class="orders-table" width="100%">'
							+'		<thead>'
							+'		<tr>'
							+'				<th width="110"></th>'
							+'				<th width="35%">商品</th>'
							+'				<th width="10%">单价</th>'
							+'				<th width="8%">数量</th>'
							+'				<th width="10%">小计</th>'
							+'				<th width="10%">状态</th>'
							+'				<th width="10%">操作</th>'
							+'			</tr>'
							+'		</thead>'
							+'		<tbody id="order_item'+i+'" class="orders-body">'
							+'		</tbody>'
							+'	</table>'
							+'	<div>'
							+'		<span class="pull-right">订单总金额：¥{pay}</span>'
							+'	</div>'
							+'</div>'
							+'</div>'
						
						html = html.replace("{id}",list[i].id);
						html = html.replace("{time}",list[i].orderDate);
						html = html.replace("{recv_name}",list[i].recvName);
						html = html.replace("{pay}",list[i].pay);
						
						
						
						$("#orders").append(html);
						var items =list[i].items;
						for(var j=0;j<items.length;j++){
							var html_Item ='<tr>'
							+'<td><img src=".{goodsImage}/collect.png" width="110" /></td>'
							+'<td>{goodsTitle}</td>'
							+'<td>¥<span>{goodsPrice}</span></td>'
							+'<td>{goodsCount}件</td>'
							+'<td>¥<span>{price}</span></td>'
							+'<td>'
							+'	<div>已发货</div>'
							+'</td>'
							+'<td><a href="#" class="btn btn-default btn-xs">确认收货</a></td>'
							+'</tr>'
							
							html_Item = html_Item.replace("{goodsImage}",items[j].goodsImage);
							html_Item = html_Item.replace("{goodsTitle}",items[j].goodsTitle);
							html_Item = html_Item.replace("{goodsPrice}",items[j].goodsPrice);
							html_Item = html_Item.replace("{goodsCount}",items[j].goodsCount);
							html_Item = html_Item.replace("{price}",items[j].goodsPrice*items[j].goodsCount);
							$("#order_item"+i).append(html_Item);
							
						}
					}
					
				}
			});
		}
		function order_items(json){
			
		}
	
		</script>
		
	</body>

</html>