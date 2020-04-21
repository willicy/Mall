<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!doctype html>
<html lang="en">
  <head>
  <title>商品</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="./jquery-getUrlParam.js"></script>
	 <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
	 <style type="text/css">
        * {
            margin: 0;
            padding: 0;
            list-style-type: none;
        }
        
        a,
        img {
            border: 0;
        }
        
        body {
            font: 12px/180% Arial, Helvetica, sans-serif, "宋体";
        }
        .father{
        	
        	margin:auto;
        	
        	display: flex;
        	
        	justify-content: space-between;
        	width:1500px;
        }
        
       
        
        .zoompic {
            border: solid 1px #dfdfdf;
            width: 560px;
            height: 437px;
             }
        
        .sliderbox {
            height: 94px;
            overflow: hidden;
            margin: 6px 0 0 0;
        }
        
       
        
        .sliderbox #btn-left {
            float: left;
            background-position: 0 0;
        }
        
        .sliderbox #btn-left.dasabled {
            background-position: 0 -76px;
        }
        
        .sliderbox #btn-right {
            float: right;
            background-position: -38px 0;
        }
        
        .sliderbox #btn-right.dasabled {
            background-position: -38px -76px;
        }
        
        .sliderbox .slider {
            float: left;
            height: 76px;
            width: 686px;
            position: relative;
            overflow: hidden;
            margin: 0 0 0 3px;
            display: inline;
        }
        
        .sliderbox .slider ul {
            position: absolute;
            left: 0;
            
        }
        
        .sliderbox .slider li {
            float: left;
            width: 94px;
            height: 94px;
            text-align: center;
            
            margin-left: 10px;
            margin-right: 8px;
        }
        
        .sliderbox .slider li img {
            border: solid 1px #dfdfdf;
        }
        
        .sliderbox .slider li.current img {
            border: solid 1px #3366cc;
        }
    </style>
	 
</head>

<body>
	
    <h1 style="display: flex;justify-content: flex-end; margin-top: 80px;"><a href="index.jsp" style="margin-right: 40px;">返回主页</a></h1>
    <!-- <div class="popup"> -->
    <div  class="father">
        <!-- 左边 -->
        <div class="zoombox">

            <div class="zoompic"><img id="img"  width="560" height="437" alt="墙纸"></div>

            <div class="sliderbox">
                
                <div  style="height: 94px;" class="slider" id="thumbnail">
                    <ul style="width: 686px;">
                        <li style="margin-left: 5px;" class="current">
                            <a id="img1-big"  target="_blank"><img id="img1"  width="94" height="94" alt="01"></a>
                        </li>
                        <li>
                            <a id="img2-big"  target="_blank"><img id="img2"  width="94" height="94" alt="02"></a>
                        </li>
                        <li>
                            <a id="img3-big"  target="_blank"><img id="img3"  width="94" height="94" alt="03"></a>
                        </li>
                        <li>
                            <a id="img4-big"  target="_blank"><img id="img4"  width="94" height="94" alt="04"></a>
                        </li>
                        <li style="margin-right: 0;">
                            <a id="img5-big"  target="_blank"><img id="img5"  width="94" height="94" alt="05"></a>
                        </li>
                    </ul>
                </div>
                
            </div>

        </div>
        
		<div class="right" style="margin-left: 20px;">
				<h1 style="margin-top: 30px;" id="goods-title" class="col-md-12 " >-XXX-</h1>
				<h4 class="col-md-12 text-row-1"><small id="goods-sell-point">-XXX-</small></h4>
				<div style="margin-top: 20px;" class="col-md-12 price-bar">
					<h2>学员售价： ¥<span id="goods-price">-XXX-</span>.00</h2>
					<div><small>*退货补运费 *7天无理由退货 *24小时快速退款 </small></div>
				</div>
				<form id="cart_form" role="form" method="GET" action="orderConfirm.jsp">
					<div style="margin-top: 30px;" class="col-md-12 form-space">
						<h2>数量：</h2>
						<input id="numDown" type="button" value="-" style="Float:left;"class="btn btn-primary" />
						<input id="num" type="text" size="2" readonly="readonly" class="form-control"style="Float:left;width: 60px; display:inline;margin:0px 5px 0px 5px;" value="1">
						<input  id="numUp"class="btn btn-primary"  type="button" value="+" />
					</div>
					<div  style="margin-top: 50px;"class="col-md-12 form-space">
						<h4><small><b>MALL</b>发货并提供售后服务,今日下单,明日送达</small></h4>

					</div>
					
					<div style="margin-top: 20px;" class="col-md-12 form-space">
							<input id="cid"  name="cart_id" type="text"  style="display: none;">
							<input  id ="buy" class="btn btn-primary btn-lg btn-block" type="button" value="立即购买">
					
					</div>

					<div style="margin-top: 20px;" class="col-md-12 form-space">

						<button  id="btn" type="button" class="btn btn-outline-secondary"><span class="fa fa-cart-plus"></span> 加入购物车</button>

						
					</div>
				</form>

			</div>
</div>
</body>
 <script type="text/javascript">
  
    var id;
    id = $.getUrlParam("id");
        $(document).ready(function() {
        	//显示商品详情
    		showGoodsDetails();
            
            
    		
    		

        });
        
      

    	
    	function showGoodsDetails(){
    		var url = "goods";
    		var data = "id="+id;
    		$.ajax({
    			"url":url,
    			"data":data,
    			"type":"POST",
    			"dataType":"json",
    			"success":function(json){
    				var goods =json.data;
    				if (goods == null){
    					location.href="index.jsp";
    					return; 
    				}
    				
    				$("#goods-title").html(goods.title);
    				$("#goods-sell-point").html(goods.sellPoint);
    				$("#goods-price").html(goods.price);
    				
    				$("#img").attr("src","."+ goods.image +"1_big.png");
    				$("#img1-big").attr("href","."+ goods.image +"1_big.png");
    				$("#img1").attr("src","."+goods.image+"1.jpg");
    				$("#img2-big").attr("href","."+goods.image+"2_big.png");
    				$("#img2").attr("src","."+goods.image+"2.jpg");
    				$("#img3-big").attr("href","."+goods.image+"3_big.png");
    				$("#img3").attr("src","."+goods.image+"3.jpg");
    				$("#img4-big").attr("href","."+goods.image+"4_big.png");
    				$("#img4").attr("src","."+goods.image+"4.jpg");
    				$("#img5-big").attr("href","."+goods.image+"5_big.png");
    				$("#img5").attr("src","."+goods.image+"5.jpg");
    				
    			},
    			"error":function(){
    				alert("失败");
    			}
    		});
    		
    		
    	}
    	
    	
    	
    	var textValue=1;
    	window.onload = function(){
    	   var html=document.body.innerHTML;
    	    document.body.innerHTML=html.replace(/value="?javascript:(\w+)"?/ig,
    	                   function(s,s1){return 'value="'+window[s1] +'"'});
	    	//点击小图切换大图
	        $("#thumbnail li a").click(function() {
	            $(".zoompic img").hide().attr({
	                "src": $(this).attr("href"),
	                "title": $("> img", this).attr("title")
	            });
	            
	            $("#thumbnail li.current").removeClass("current");
	            $(this).parents("li").addClass("current");
	            return false;
	        });
	        $(".zoompic>img").load(function() {
	            $(".zoompic>img:hidden").show();
	        });

	    	   //处理添加到购物车
	    	$("#btn").click(function(){
	    		var url="cart";
	    		 var gid = id;
	    		 var price = $("#goods-price").html();
	    		 var count = $("#num").val();
	    		 var data="gid=" + gid + "&price=" + price + "&count=" + count + "&buy=0";
	    		 console.log("[data]"+data);
	    		 $.ajax({
	    			 "url":url,
	    			 "data":data,
	    			 "type":"POST",
	    			 "dataType":"json",
	    			 "success":function(json){
	    				 if(json.state == 200){
	    					
	    					 alert("添加成功");
	    				 } else if(json.state == 602){
	    					 alert("添加失败"+json.message);
	    					 window.location.replace("login.jsp");
	    				 }else{
	    					 alert("添加失败"+json.message);
	    				 }
	    			 },
	    			 "error":function(){
	    				 alert("您的登录信息已经过期！请重新登录！");
	    			 }
    			 
    		 	});
    		});
    	   
	    	$("#buy").click(function(){
	    		var url="cart";
	    		 var gid = id;
	    		 var price = $("#goods-price").html();
	    		 var count = $("#num").val();
	    		 var data="gid=" + gid + "&price=" + price + "&count=" + count + "&buy=1";
	    		 console.log("[data]"+data);
	    		 $.ajax({
	    			 "url":url,
	    			 "data":data,
	    			 "type":"POST",
	    			 "dataType":"json",
	    			 "success":function(json){
	    				 if(json.state == 200){
	    					
	    					 $("#cid").val(json.data);
	    					 document.getElementById("cart_form").submit(); 
	    				 } else if(json.state == 602){
	    					 alert("购买失败"+json.message);
	    					 window.location.replace("login.jsp");
	    				 }else{
	    					 alert("购买失败"+json.message);
	    				 }
	    			 },
	    			 "error":function(){
	    				 alert("您的登录信息已经过期！请重新登录！");
	    			 }
				 
			 	});
			});
    	
    	    $("#numUp").click(function(){
    	     
    	     document.getElementById("num").value=++textValue;
    	     
    	     });
    	    $("#numDown").click(function(){
    	     if(document.getElementById("num").value>1)
    	     document.getElementById("num").value=--textValue;
    	     });   
    	}
    </script>

</body>
</html>