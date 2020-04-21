package views;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import interceptor.SessionMap;
import model.ResponseResult;
import model.User;
/**
 * 登录
 * @author Willicy
 *
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.getSession().removeAttribute("uid");
		request.getSession().removeAttribute("username");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取userDao进行数据库交互
		UserDao userDao = new UserDaoImpl();
		
		//设定response和request为utf-8
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		//获取向客户端返回json的api
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		//用username查询是否有此用户,并返回包含id的user对象将id设为session
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		
		User user = null;
		try {
			
			// 根據 MD5 演算法生成 MessageDigest 物件
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        md5.update(password.getBytes());
	        String md5Password = new BigInteger(1, md5.digest()).toString(16);
	        
	     
	        
			//判断用户名及密码
			user = userDao.findByUsername(username);
			if(user != null && md5Password.equals(user.getPassword())){
				
				HttpSession session = request.getSession();
				session.setAttribute("uid", user.getId());
				session.setAttribute("username", user.getUsername());
				
				//将账号id 和session id 存入map
				SessionMap.sessionMap.put(user.getId(),session.getId());
				
				ResponseResult<User> rr = new ResponseResult<User>(200,user);
				out.println(gson.toJson(rr));
				
			}else if(user == null){
				ResponseResult<Void> rr = new ResponseResult<Void>(500,"用户名错误");
				out.println(gson.toJson(rr));
			}else if(!md5Password.equals(user.getPassword())){
			ResponseResult<Void> rr = new ResponseResult<Void>(500,"密码不匹配");
				out.println(gson.toJson(rr));
			}
				
				
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			ResponseResult<Void> rr = new ResponseResult<Void>(601,"未知错误");
			out.println(gson.toJson(rr));
		}
		
		
		
	    
		
		
	}

}
