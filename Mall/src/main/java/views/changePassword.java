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
 * ��������
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
		
		//Gson��ResponseResultתΪjson����
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr;
		
		//��ȡ�µĺ;ɵ�����
		String oldPassword = request.getParameter("oldPassword");
		String password = request.getParameter("password");
		
		
		try {
				// ����user.getId()��ѯ�û�����,���û��id�򱨴�
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				//�����û�id��ѯ�û�����
				User user = userDao.findById(uid);
				
				if(user == null){
					// �ж������Ƿ�Ϊnull
					rr = new ResponseResult<Void>(500,"�����Է��ʵ��û����ݲ����ڣ�");
				}
				if(user.getIsDelete() == 1) {
					// �ж��û��Ƿ��ѱ�ɾ��
					rr = new ResponseResult<Void>(500,"�����Է��ʵ��û������Ѿ���ɾ����");
				}
				// ���� MD5 ���㷨���� MessageDigest ���
		        MessageDigest md5 = MessageDigest.getInstance("MD5");
		        md5.update(oldPassword.getBytes());
		        String md5OldPassword = new BigInteger(1, md5.digest()).toString(16);
		        
				if(user.getPassword().equals(md5OldPassword)){
					//������Ͽ������������ľ�������ͬ����������������ݿ�
					md5.update(password.getBytes());
					String md5NewPassword = new BigInteger(1, md5.digest()).toString(16);
					Integer rs= userDao.updatePassword(uid,md5NewPassword);
					
					if(rs != 1){
						rr = new ResponseResult<Void>(500,"��������ʱ����δ֪����");
					}else{
						
						rr = new ResponseResult<Void>(200);
					}
					
					
				}else{
					rr = new ResponseResult<Void>(500,"���벻ƥ�䣡");
				}
				out.println(gson.toJson(rr));
			} catch (Exception e) {
				e.printStackTrace();
				rr = new ResponseResult<Void>(601,"δ֪����");
				out.println(gson.toJson(rr));
				
			}
			
		}	
	
}
