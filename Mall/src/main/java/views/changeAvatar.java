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
 * 更新头像
 * @author Willicy
 *
 */
@MultipartConfig
@WebServlet("/changeAvatar")
public class changeAvatar extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
     
	/**
	 * 上传文件的最大大小
	 */
	private static final long FILE_MAX_SIZE = 5 * 1024 * 1024;
	/**
	 * 允许上传的文件类型
	 */
	private static final List<String> FILE_CONTENT_TYPES 
		= new ArrayList<>();
	
	/**
	 * 初始化允许上传的文件类型的集合
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
		
		//Gson将ResponseResult转为json类型
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr = null;
		
		//获取档案
		Part part = request.getPart("file");
		
		
		if (part.getSize()==0) {
			//如果part大小等于0,提示用户上传文件为空
			rr= new ResponseResult<Void>(500,"上传失败！没有选择上传的文件！");
			out.print(gson.toJson(rr));
		}else if (part.getSize() >FILE_MAX_SIZE) {
			//如果part大小大于最大限制,提示用户上传文件超出限制
		rr= new ResponseResult<Void>(500,"上传失败！文件大小超出限制!");
		out.print(gson.toJson(rr));
		}else if (!FILE_CONTENT_TYPES.contains(
				part.getContentType())) {
			//如果part类型不符合,提示用户上传文件类型不符合
			rr= new ResponseResult<Void>(500,"上传失败！文件类型限制png/jpeg!");
			out.print(gson.toJson(rr));
		}else{
			
			try {
				// 根据user.getId()查询用户数据,如果没有id则报错
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				//根据用户id查询用户数据
				User data = userDao.findById(uid);
				
				if (data == null) {
					// 判断数据是否为null
					rr= new ResponseResult<Void>(500,"修改头像失败！您尝试访问的用户数据不存在！");
					out.print(gson.toJson(rr));
				}else if (data.getIsDelete() == 1) {
					// 判断用户是否已被删除
					rr= new ResponseResult<Void>(500,"修改头像失败！您尝试访问的用户数据已经被删除！");
					out.print(gson.toJson(rr));
				}else{
					
					//获取文件名
					String header = part.getHeader("content-disposition");
					String path = header.substring(header.indexOf("filename=") + 10, header.length() - 1);
					
					//获取tomcat暂存的地址
					String apache = request.getSession().getServletContext().getRealPath("\\upload"); 
					
					//获取项目地址
					String relativelyPath=System.getProperty("user.dir"); 
					relativelyPath+="\\workspace\\Mall\\src\\main\\webapp\\upload";
					
					//在两个地址创建资料夹
					File file = new File(relativelyPath);
					File fileApache=new File(apache);
					if (!file.exists()) {
					    file.mkdirs();
					}
					if (!fileApache.exists()) {
						fileApache.mkdirs();
					}
					// 获取输入流
					InputStream inputStream = part.getInputStream();
					// 定义输出流
					FileOutputStream outputStream = new FileOutputStream(new File(file, path));
					FileOutputStream outputStreamApache = new FileOutputStream(new File(fileApache, path));
					// 从输入流中读入数据并写到输出字节流中
					int len = -1;
					byte[] bytes = new byte[1024];
					while ((len = inputStream.read(bytes)) != -1) {
					    outputStream.write(bytes, 0, len);
					    outputStreamApache.write(bytes, 0, len);
					}
					
					// 关闭资源
					outputStreamApache.close();
					outputStream.close();
					inputStream.close();
			
					// 删除临时文件
					part.delete();
					
					
					User user = new User();
					
					// 更新头像数据
					String avatar = "./upload/"+path;
					
					user.setId(uid);
					user.setAvatar(avatar);
					
					int rs = userDao.updateAvatar(user);
					if (rs != 1) {
						rr= new ResponseResult<Void>(500,"更新头像时出现未知错误！");
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
				rr = new ResponseResult<Void>(601,"未知错误");
				out.println(gson.toJson(rr));
				
			}
			
		}
		
	}
}
