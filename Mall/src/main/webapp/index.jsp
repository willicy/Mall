<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
	<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="jquery.cookie.js"></script>
	<style type="text/css">
	header{
		height: 90px; 
	}
	main{
		width: 1000px;
		margin: 0 auto;
		margin-top:50px;
	}
	
	.login-show{
		display:none;
	}
	.login-ani{
		width:66px;
		position: relative;
		overflow: hidden;
	}
	
	#login{
		display: inline-block;
		height: 2rem;
		transition: all .5s ease;
	}
	.login-wrap .switch-login{
		display: none;
	}
	.login-wrap.login-ani .switch-login{
	display: inline-block;
		position:absolute;
		    right: -65px;
    top: 50%;
    transform: translateY(-50%);
    		transition: all .5s ease;
	}
	
	.login-ani:hover .switch-login{
		right:12px;
	}
	
	.login-ani:hover #login{
		margin-left:-65px;
	}
	
	</style>
    <title>主页</title>
  </head>
  <body >
  <header>
	  <nav class="navbar navbar-expand-lg navbar-light fixed-top" style=" background-color: #e3f2fd;" >
	  	<div class="container" style="width:1000px;padding: 0;">
	  		
		  	<a class="navbar-brand d-flex flex-row align-items-center justify-content-start" href="index.jsp" style="height: 40px;padding: 0;">
		  	
		  	<img alt="" src="./images/logo_1.png">
		  	<div class="" style="padding-left: 15px; display: inline; font-size: 24px;font-weight: 700;color: #4a4a4a">Mall</div>
		  	</a>
		  	<div class="collapse navbar-collapse justify-content-end" id="navbarResponse">
		    <ul class="navbar-nav">
		      <li class="nav-item active login-show">
		        <a class="nav-link" href="order.jsp">订单<span class="sr-only">(current)</span></a>
		      </li>
		      <li class="nav-item login-show">
		        <a class="nav-link" href="cart.jsp">购物车</a>
		      </li>
		      
		      <li class="nav-item dropdown login-show" >
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		                         管理
		        </a>
		    	<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
			        <a class="dropdown-item" href="changePassword.jsp">修改密码</a>
			        <a class="dropdown-item" href="info.jsp">个人资料</a>
			        <a class="dropdown-item" href="avatar.jsp">修改头像</a>
			        <a class="dropdown-item" href="address.jsp">收货地址</a>
		    	</div>
		      </li >
		      <li >
		       <img class="circle" style="margin-top:3px; width:30px; border-radius:30px"  id="img-avatar" src="./images/user.jpg"  />
		      </li>
		      <li class="nav-item login-wrap">
		     
		        <a href="login.jsp" id="login" class="nav-link">登录</a>
		        <a id="logout" class="switch-login" href="login.jsp">登出</a>
		        
		      
		      </li>
		     
		     </ul>
		     
		  	</div>
		</div>
	</nav>
  </header>
  <main>
	<div id="carouselExampleIndicators" style="  height:400px;" class="carousel slide" data-ride="carousel">
	  <ol class="carousel-indicators">
	    <li data-target="#carouselExampleIndicators" data-slide-to="0"></li>
	    <li data-target="#carouselExampleIndicators" data-slide-to="1" class="active"></li>
	    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
	    <li data-target="#carouselExampleIndicators" data-slide-to="3"></li>
	    <li data-target="#carouselExampleIndicators" data-slide-to="4"></li>
	  </ol>
	  <div class="carousel-inner">
	    <div class="carousel-item ">
	      <a href="product.jsp?id=10000027">
	       <img src=".\images\index\index_banner5.png " class="d-block " alt="...">
	      </a>
	    </div>
	    <div class="carousel-item active">
	      <a href="product.jsp?id=10000019">
	      <img src=".\images\index\index_banner4.png" class="d-block " alt="...">
	      </a>
	    </div>
	    <div class="carousel-item">
	    <a href="product.jsp?id=10000005">
	      <img src=".\images\index\index_banner3.png"class="d-block " alt="...">
	     </a>
	    </div>
	    <div class="carousel-item">
	    <a href="product.jsp?id=10000001">
	      <img src=".\images\index\index_banner2.png"class="d-block " alt="...">
	     </a>
	    </div>
	    <div class="carousel-item ">
	      <a href="product.jsp?id=10000029">
	      <img src=".\images\index\index_banner1.png"class="d-block " alt="...">
	      </a>
	    </div>
	  </div>
	  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
	    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
	    <span class="sr-only">Previous</span>
	  </a>
	  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
	    <span class="carousel-control-next-icon" aria-hidden="true"></span>
	    <span class="sr-only">Next</span>
	  </a>
	</div>
	
	<table id="list-hot" class="table border" style="width: 1000px; margin:0 auto;">
		<div class="border rounded-top" style="width: 1000px;  margin:0 auto; margin-top:50px;">
					<h3 class="panel-title">热销排行</h3>
		</div>
	   
		<tbody>
		</tbody>
	  
	</table>
</main>
  </body>
  <script type="text/javascript">
  
		$(document).ready(function(){
			showHotList();
			showAU();
		});
		function showAU(){
			var url ="index";
			$.ajax({
				"url":url,
				"type":"GET",
				"dataType":"json",
				"success":function(json){
					
					if (json.message ==$.cookie("username") ) {
						$("#img-avatar").attr("src", $.cookie("avatar"));
						var username=$.cookie("username");
						if(username.length>5){
							document.getElementById("login").innerHTML=username.substring(0,5)+"...";
						}else{
							document.getElementById("login").innerHTML=username;
						}
						
						document.querySelector(".login-wrap").classList.add('login-ani');
						$('.login-show').removeClass('login-show');
						
						
					}
				
					
					
					
				},"error":function(json){
					alert("错误");
					
				}
			});
		}
		function showHotList(){
			var url ="index";
			$.ajax({
				"url":url,
				"type":"POST",
				"dataType":"json",
				"success":function(json){
					$("#list-hot").empty();
					var list =json.data;
					for(var i=0;i<list.length;i++){
						var x =i+1;
					var html = '<tr>'
					      +'<th scope="row">'+x+'</th>'
					      +'<td><a href="product.jsp?id={id}">{title}</a></td>'
					      +'<td>¥{price}</td>'
					      +'<td align="right"><img src=".{image}collect.png" width="50%""></td>'
					      +'</tr>';
						
						html = html.replace(/{id}/g,list[i].id);
						html = html.replace(/{title}/g,list[i].title);
						html = html.replace(/{price}/g,list[i].price);
						html = html.replace(/{image}/g,list[i].image);
						
						$("#list-hot").append(html);
					}
				}
			});
		}
		$("#logout").click(function(){
			var url ="login";
			$.ajax({
				"url":url,
				"type":"GET"
			});
		});
		</script>
</html>

