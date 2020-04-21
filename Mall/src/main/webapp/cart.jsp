<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<title>购物车</title>
<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
 <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
	
<style type="text/css">
*{
		font-family: 'Microsoft YaHei';
	}
	a{
		text-decoration: none;
		color: inherit;
		
	}
	table{
		margin:0 auto;
		margin-top:110;
		border-collapse: separate;
  		border-spacing: 0;
  		border-radius: 0.5em;
  		border: solid 0.2em #dfdfdf;
  		width: 1000;
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
	
		<div class="col-md-offset-1 col-md-10" style="margin: 0 auto; display: block; width: 1030px;">

			<div class="panel panel-primary">
				<div   class="panel-heading">
					<h3 style="margin: 0 auto; display: block; width: 84px;" class="panel-title"><span class="fa fa-shopping-cart"></span> 购物车</h3>
					<h1><a href="index.jsp" style="float: right;">返回主页</a></h1>
				</div>
				<div class="panel-body">
					<form id="cart_form" role="form" method="GET" action="orderConfirm.jsp">
						<!--购物车表格开始-->
						<table id="myTable" class="cart-table" width="100%">
							<thead>
								<tr>
									<th width="8%">
										<input id="CheckAll" type="checkbox" class="ckall"    />全选</th>
									<th width="110"></th>
									<th width="29%">商品</th>
									<th width="11%">单价</th>
									<th width="15%">数量</th>
									<th width="11%">金额</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="cart-list" class="cart-body">
								
							</tbody>
						</table>
						
					</form>
					<div>
							<span class="pull-right">
								<input id="sub" type="submit" value="  结  算  " class="btn btn-primary btn-lg link-account" />
							</span>
						</div>
				</div>
			</div>

		</div>
		<script type="text/javascript">
			$(function() {
				//返回链接
				$(".link-account").click(function() {
					location.href = "orderConfirm.jsp";
				})
			})
		</script>
		<script type="text/javascript">
		$(document).ready(function(){
			showCartList();
			
			$("#CheckAll").click(function(){
				if($("#CheckAll").prop("checked")){//如果全選按鈕有被選擇的話（被選擇是true）
				 $("input[name='cart_id']").each(function(){
				   $(this).prop("checked",true);//把所有的核取方框的property都變成勾選
				 })
				}else{
				 $("input[name='cart_id']").each(function(){
				     $(this).prop("checked",false);//把所有的核方框的property都取消勾選
				 })
				}
			})
			$("#sub").click(function(){
				var x=$("input[name='cart_id']").is(":checked");
				if(x){
					 document.getElementById("cart_form").submit();
				}
				if(!x){
					alert("请勾选");
					window.location.replace("cart.jsp");
				}
				
			})	  
			
		});
		
		function showCartList(){
			var data ="function=list";
			$.ajax({
				"url":"cart",
				"data":data,
				"type":"GET",
				"dataType":"json",
				"success": function(json){
					var list = json.data;
					
					$("#cart-list").empty();
					
					for (var i = 0; i < list.length; i++) {
						
						var html='<tr>'
						+'<td>'
						+	'<input type="checkbox" name="cart_id" value="{id}" class="ckitem" />'
						+'</td>'
						+'<td><img src=".{image}collect.png" width="110" /></td>'
						+'<td>{title}</td>'
						+'<td>¥<span id="goodsPrice{id}">{newPrice}</span></td>'
						+'<td>'
						+	'<input type="button" value="-" class="num-btn" onclick="reduceCount({id})" />'
						+	'<input id="goodsCount{id}" type="text" size="2" readonly="readonly" class="num-text" value="{count}">'
						+	'<input class="num-btn" type="button" value="+" onclick="addCount({id})" />'
						+'</td>'
						+'<td><span id="goodsTotalPrice{id}">{totalPrice}</span></td>'
						+'<td><input type="button" onclick="deleteRow(this,{id})" class="cart-del btn btn-default btn-xs" value="删除"/></td>'
						+		'</tr>';
						
						
						
						
						html = html.replace(/{id}/g,list[i].id);
						html = html.replace("{image}",list[i].image);
						html = html.replace("{title}",list[i].title);
						html = html.replace("{newPrice}",list[i].newPrice);
						html = html.replace("{count}",list[i].count);
						html = html.replace("{totalPrice}",list[i].newPrice * list[i].count);
						
						$("#cart-list").append(html);
					}
				}
				
			});
			
		}
		function addCount(id){
			var url = "cart";
			var data = "function=add_count&id="+id;
			$.ajax({
				"url":url,
				"data":data,
				"type":"GET",
				"dataType":"json",
				"success": function(json){
					
				var c = parseInt($("#goodsCount"+id).val());
				c++
				$("#goodsCount"+id).val(c);
				
				var p = parseInt($("#goodsPrice"+id).html());
				var tp = p * c;
				
				$("#goodsTotalPrice"+id).html(tp);
				},
				"error":function(){
					 alert("您的登录信息已经过期！请重新登录！");
				}
				
				
			});
		}
		function reduceCount(id){
			if(1<parseInt($("#goodsCount"+id).val())){
				var url = "cart";
				var data = "function=reduce_count&id="+id;
				$.ajax({
					"url":url,
					"data":data,
					"type":"GET",
					"dataType":"json",
					"success": function(json){
						
					var c = parseInt($("#goodsCount"+id).val());
					c--
					$("#goodsCount"+id).val(c);
					
					var p = parseInt($("#goodsPrice"+id).html());
					var tp = p * c;
					
					$("#goodsTotalPrice"+id).html(tp);
					},
					"error":function(){
						 alert("您的登录信息已经过期！请重新登录！");
					}
					
					
				});
			}
		}
		function deleteRow(r,id){
			var url = "cart";
			var data = "function=delete&id="+id;
			$.ajax({
				"url":url,
				"data":data,
				"type":"GET",
				"dataType":"json",
				"success": function(json){
					
				var i=r.parentNode.parentNode.rowIndex;
				document.getElementById('myTable').deleteRow(i);
				},
				"error":function(){
					 alert("错误"); 
				}
			});
		
		}
		</script>
	</body>

</html>