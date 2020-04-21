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
 * 更新密码
 * @author Willicy
 *
 */
@WebServlet("/changePassword")
public class changePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public changePassword() {
        super();
        }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.getRequestDispatcher("changePassword.jsp").forward(request, response);
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
		
		//获取新的和旧的密码
		String oldPassword = request.getParameter("oldPassword");
		String password = request.getParameter("password");
		
		
		try {
				// 根据user.getId()查询用户数据,如果没有id则报错
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				//根据用户id查询用户数据
				User user = userDao.findById(uid);
				
				if(user == null){
					// 判断数据是否为null
					rr = new ResponseResult<Void>(500,"您尝试访问的用户数据不存在！");
				}
				if(user.getIsDelete() == 1) {
					// 判断用户是否已被删除
					rr = new ResponseResult<Void>(500,"您尝试访问的用户数据已经被删除！");
				}
				// 根據 MD5 演算法生成 MessageDigest 物件
		        MessageDigest md5 = MessageDigest.getInstance("MD5");
		        md5.update(oldPassword.getBytes());
		        String md5OldPassword = new BigInteger(1, md5.digest()).toString(16);
		        
				if(user.getPassword().equals(md5OldPassword)){
					//如果资料库旧密码与输入的旧密码相同，将新密码存入数据库
					md5.update(password.getBytes());
					String md5NewPassword = new BigInteger(1, md5.digest()).toString(16);
					Integer rs= userDao.updatePassword(uid,md5NewPassword);
					
					if(rs != 1){
						rr = new ResponseResult<Void>(500,"更新密码时出现未知错误！");
					}else{
						
						rr = new ResponseResult<Void>(200);
					}
					
					
				}else{
					rr = new ResponseResult<Void>(500,"密码不匹配！");
				}
				out.println(gson.toJson(rr));
			} catch (Exception e) {
				e.printStackTrace();
				rr = new ResponseResult<Void>(601,"未知错误");
				out.println(gson.toJson(rr));
				
			}
			
		}	
	
}
