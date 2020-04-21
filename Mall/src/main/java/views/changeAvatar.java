package views;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.ResponseResult;
import model.User;

/**
 * ����ͷ��
 * @author Willicy
 *
 */
@MultipartConfig
@WebServlet("/changeAvatar")
public class changeAvatar extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
     
	/**
	 * �ϴ��ļ�������С
	 */
	private static final long FILE_MAX_SIZE = 5 * 1024 * 1024;
	/**
	 * �����ϴ����ļ�����
	 */
	private static final List<String> FILE_CONTENT_TYPES 
		= new ArrayList<>();
	
	/**
	 * ��ʼ�������ϴ����ļ����͵ļ���
	 */
	static {
		FILE_CONTENT_TYPES.add("image/jpeg");
		FILE_CONTENT_TYPES.add("image/png");
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public changeAvatar() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.getRequestDispatcher("avatar.jsp").forward(request, response);
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
		 
		ResponseResult<Void> rr = null;
		
		//��ȡ����
		Part part = request.getPart("file");
		
		
		if (part.getSize()==0) {
			//���part��С����0,��ʾ�û��ϴ��ļ�Ϊ��
			rr= new ResponseResult<Void>(500,"�ϴ�ʧ�ܣ�û��ѡ���ϴ����ļ���");
			out.print(gson.toJson(rr));
		}else if (part.getSize() >FILE_MAX_SIZE) {
			//���part��С�����������,��ʾ�û��ϴ��ļ���������
		rr= new ResponseResult<Void>(500,"�ϴ�ʧ�ܣ��ļ���С��������!");
		out.print(gson.toJson(rr));
		}else if (!FILE_CONTENT_TYPES.contains(
				part.getContentType())) {
			//���part���Ͳ�����,��ʾ�û��ϴ��ļ����Ͳ�����
			rr= new ResponseResult<Void>(500,"�ϴ�ʧ�ܣ��ļ���������png/jpeg!");
			out.print(gson.toJson(rr));
		}else{
			
			try {
				// ����user.getId()��ѯ�û�����,���û��id�򱨴�
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				//�����û�id��ѯ�û�����
				User data = userDao.findById(uid);
				
				if (data == null) {
					// �ж������Ƿ�Ϊnull
					rr= new ResponseResult<Void>(500,"�޸�ͷ��ʧ�ܣ������Է��ʵ��û����ݲ����ڣ�");
					out.print(gson.toJson(rr));
				}else if (data.getIsDelete() == 1) {
					// �ж��û��Ƿ��ѱ�ɾ��
					rr= new ResponseResult<Void>(500,"�޸�ͷ��ʧ�ܣ������Է��ʵ��û������Ѿ���ɾ����");
					out.print(gson.toJson(rr));
				}else{
					
					//��ȡ�ļ���
					String header = part.getHeader("content-disposition");
					String path = header.substring(header.indexOf("filename=") + 10, header.length() - 1);
					
					//��ȡtomcat�ݴ�ĵ�ַ
					String apache = request.getSession().getServletContext().getRealPath("\\upload"); 
					
					//��ȡ��Ŀ��ַ
					String relativelyPath=System.getProperty("user.dir"); 
					relativelyPath+="\\workspace\\Mall\\src\\main\\webapp\\upload";
					
					//��������ַ�������ϼ�
					File file = new File(relativelyPath);
					File fileApache=new File(apache);
					if (!file.exists()) {
					    file.mkdirs();
					}
					if (!fileApache.exists()) {
						fileApache.mkdirs();
					}
					// ��ȡ������
					InputStream inputStream = part.getInputStream();
					// ���������
					FileOutputStream outputStream = new FileOutputStream(new File(file, path));
					FileOutputStream outputStreamApache = new FileOutputStream(new File(fileApache, path));
					// ���������ж������ݲ�д������ֽ�����
					int len = -1;
					byte[] bytes = new byte[1024];
					while ((len = inputStream.read(bytes)) != -1) {
					    outputStream.write(bytes, 0, len);
					    outputStreamApache.write(bytes, 0, len);
					}
					
					// �ر���Դ
					outputStreamApache.close();
					outputStream.close();
					inputStream.close();
			
					// ɾ����ʱ�ļ�
					part.delete();
					
					
					User user = new User();
					
					// ����ͷ������
					String avatar = "./upload/"+path;
					
					user.setId(uid);
					user.setAvatar(avatar);
					
					int rs = userDao.updateAvatar(user);
					if (rs != 1) {
						rr= new ResponseResult<Void>(500,"����ͷ��ʱ����δ֪����");
						out.print(gson.toJson(rr));
					}else{
						
						ResponseResult<String> rrs= new ResponseResult<String>();
						rrs.setState(200);
						rrs.setData(avatar);
						out.print(gson.toJson(rrs));
						
					}
					
					
					
				
				}
			} catch (Exception e) {
				e.printStackTrace();
				rr = new ResponseResult<Void>(601,"δ֪����");
				out.println(gson.toJson(rr));
				
			}
			
		}
		
	}
}
