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
 * �û���Ϣ
 * @author Willicy
 *
 */
@WebServlet("/info")
public class info extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public info() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.getRequestDispatcher("changeInfo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserDao userDao = new UserDaoImpl();
		
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr = null;
	
		
		try {
			//�г��û���Ϣ
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				User user = userDao.findById(uid);
				
				if(user == null){
					rr = new ResponseResult<Void>(500,"�����Է��ʵ��û����ݲ����ڣ�");

					out.println(gson.toJson(rr));
				}
				if(user.getIsDelete() == 1) {
					rr = new ResponseResult<Void>(500,"�����Է��ʵ��û������Ѿ���ɾ����");

					out.println(gson.toJson(rr));
				}else{
					user.setPassword(null);
					user.setIsDelete(null);
					user.setId(uid);
					ResponseResult<User> rrs = new ResponseResult<User>(200,user);
					out.println(gson.toJson(rrs));
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				rr = new ResponseResult<Void>(601,"δ֪����");
				out.println(gson.toJson(rr));
				
			}
			
		}	
	
}
