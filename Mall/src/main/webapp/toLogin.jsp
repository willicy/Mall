<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<title>跳转</title>
<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="jquery.cookie.js"></script>

	
</head>
<body>

<span id="boolean">访问此页面需先登录!</span>
<span id="count">3</span>秒后跳转到登录页面~

<script type="text/javascript"> 
$(document).ready(function() {
	var b=document.getElementById("boolean");
	if(<%=session.getAttribute("other_Login")%>){
		<%request.getSession().removeAttribute("other_Login");%>
		b.innerHTML = "此账号已从别处登录!"
	}
	
});
var y=document.getElementById("count");
setTimeout(function(){ y.innerHTML = "2" }, 1000);
setTimeout(function(){ y.innerHTML = "1" }, 2000);
setTimeout(function(){window.location.replace("login.jsp"); }, 3000);
</script> 

</body>
</html>

