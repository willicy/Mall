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

import com.google.gson.Gson;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.ResponseResult;
import model.User;
/**
 * 注册
 */
@WebServlet("/register")
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public register() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserDao userDao = new UserDaoImpl();
		
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		

		//Gson将ResponseResult转为json类型
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr;
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
				
				User user = userDao.findByUsername(username);
				//判断是否已创建
				if(user == null){
					//创建用户
					user=new User();
					user.setAvatar("./images/user.jpg");
					user.setEmail(request.getParameter("email"));
					user.setGender(Integer.valueOf(request.getParameter("gender")));
					user.setPhone(request.getParameter("phone"));
					user.setIsDelete(0);
					user.setUsername(username);
					
					// 根據 MD5 演算法生成 MessageDigest 物件
			        MessageDigest md5 = MessageDigest.getInstance("MD5");
			        md5.update(password.getBytes());
			        String md5Password = new BigInteger(1, md5.digest()).toString(16);
			        // 使用 srcBytes 更新摘要
			        
			        
					user.setPassword(md5Password);
					
					
					int rs=userDao.addnew(user);
					if(rs!=1){
						rr = new ResponseResult<Void>(601,"未知错误");
					}else{
						rr = new ResponseResult<Void>(200);
					}
					out.println(gson.toJson(rr));
					
				}else{
					//已被注册
					rr = new ResponseResult<Void>(500,"用户名已被注册");
					out.println(gson.toJson(rr));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				rr = new ResponseResult<Void>(601,"未知错误");
				out.println(gson.toJson(rr));
				
			}
			
		}	
	
}
