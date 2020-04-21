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
 * �����û���Ϣ
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
		
		//Gson��ResponseResultתΪjson����
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr;
		
		
		try {
				// ����user.getId()��ѯ�û�����,���û��id�򱨴�
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				//�����û�id��ѯ�û�����
				User data = userDao.findById(uid);
			
				if(data == null){
					// �ж������Ƿ�Ϊnull
					rr = new ResponseResult<Void>(500,"�����Է��ʵ��û����ݲ����ڣ�");
				}
				if(data.getIsDelete() == 1) {
					// �ж��û��Ƿ��ѱ�ɾ��
					rr = new ResponseResult<Void>(500,"�����Է��ʵ��û������Ѿ���ɾ����");
				}
				
				//��װ�û����ݲ��������ݿ�
				User user =new User();
				user.setEmail(request.getParameter("email"));
				user.setPhone(request.getParameter("phone"));
				user.setGender(Integer.valueOf(request.getParameter("gender")));
				user.setId(uid);
				int rs = userDao.changeInfo(user);
				if(rs != 1){
					rr = new ResponseResult<Void>(500,"�����û�����ʱ����δ֪����");
				}else{
					
					rr = new ResponseResult<Void>(200);
				}
				out.println(gson.toJson(rr));
			} catch (Exception e) {
				e.printStackTrace();
				rr = new ResponseResult<Void>(601,"δ֪����");
				out.println(gson.toJson(rr));
				
			}
			
		}	
	
}
