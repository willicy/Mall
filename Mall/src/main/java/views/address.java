package views;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.AddressDao;
import dao.impl.AddressDaoImpl;
import model.Address;
import model.ResponseResult;
/**
 * 地址管理
 * @author Willicy
 *
 */
@WebServlet("/address")
public class address extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public address() {
        super();
         
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		AddressDao addressDao = new AddressDaoImpl();
		
		//Gson将ResponseResult转为json类型
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr = null;
		
		try {
			// 根据session获取uid
			Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
			
			//根据function判断要如何处理业务
			String function=request.getParameter("function");
			if(function.equals("delete")){
				//删除数据
				Integer id = Integer.valueOf(request.getParameter("id"));
				Address data = addressDao.findById(id);
				//判断是否查询到数据
				if(data == null){
					rr =new ResponseResult<Void>(500,"尝试删除的收货地址数据不存在 ");
		
				}else if(data.getUid()!=uid){
					rr =new ResponseResult<Void>(500,"删除收货地址失败！访问权限验证不通过");
				}else{
					Integer rs = addressDao.deleteById(id);
					if(rs != 1){
						throw new Exception();
					}
				
					rr =new ResponseResult<Void>(200);
				}
				out.println(gson.toJson(rr));
			}else if(function.equals("default")){
				
				//将地址设为默认
				Integer id = Integer.valueOf(request.getParameter("id"));
				
				Address data = addressDao.findById(id);
			
				//判断是否查询到数据
				if(data == null){
					rr =new ResponseResult<Void>(500,"尝试访问的收货地址数据不存在 ");
						
				}else if(data.getUid()!=uid){
					
					rr =new ResponseResult<Void>(500,"设置默认收货地址失败！访问权限验证不通过");
				}else{
					Integer rs =addressDao.updateNonDefault(uid);
					
					if(rs==0){
						throw new Exception();
					}
					rs = addressDao.updateDefault(id);
					if(rs!=1){
						throw new Exception();
					}
					rr =new ResponseResult<Void>(200);
				}
				
				out.println(gson.toJson(rr));
				
			}else{
				throw new Exception();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rr = new ResponseResult<Void>(601,"未知错误");
			out.println(gson.toJson(rr));
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AddressDao addressDao = new AddressDaoImpl();
		
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		//Gson将ResponseResult转为json类型
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr;
		
		try {
				// 根据session获取uid
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				//获取用户的地址数据
				List<Address> addressList = addressDao.findByUid(uid);
				
				
				ResponseResult<List<Address>> rrs = new ResponseResult<List<Address>>(200,addressList);
				
				out.println(gson.toJson(rrs));
			} catch (Exception e) {
				e.printStackTrace();
				rr = new ResponseResult<Void>(601,"未知错误");
				out.println(gson.toJson(rr));
				
			}
			
		}	
	
}
