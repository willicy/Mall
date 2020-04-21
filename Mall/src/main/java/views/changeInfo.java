package views;

import java.io.IOException;
import java.io.PrintWriter;

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
 * 更新用户信息
 * @author Willicy
 *
 */
@WebServlet("/changeInfo")
public class changeInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public changeInfo() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.getRequestDispatcher("info.jsp").forward(request, response);
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
		
		
		try {
				// 根据user.getId()查询用户数据,如果没有id则报错
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				//根据用户id查询用户数据
				User data = userDao.findById(uid);
			
				if(data == null){
					// 判断数据是否为null
					rr = new ResponseResult<Void>(500,"您尝试访问的用户数据不存在！");
				}
				if(data.getIsDelete() == 1) {
					// 判断用户是否已被删除
					rr = new ResponseResult<Void>(500,"您尝试访问的用户数据已经被删除！");
				}
				
				//封装用户数据并更新数据库
				User user =new User();
				user.setEmail(request.getParameter("email"));
				user.setPhone(request.getParameter("phone"));
				user.setGender(Integer.valueOf(request.getParameter("gender")));
				user.setId(uid);
				int rs = userDao.changeInfo(user);
				if(rs != 1){
					rr = new ResponseResult<Void>(500,"更新用户数据时出现未知错误！");
				}else{
					
					rr = new ResponseResult<Void>(200);
				}
				out.println(gson.toJson(rr));
			} catch (Exception e) {
				e.printStackTrace();
				rr = new ResponseResult<Void>(601,"未知错误");
				out.println(gson.toJson(rr));
				
			}
			
		}	
	
}
