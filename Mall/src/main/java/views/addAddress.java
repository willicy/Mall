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

import dao.AddressDao;
import dao.DistrictDao;
import dao.UserDao;
import dao.impl.AddressDaoImpl;
import dao.impl.DistrictDaoImpl;
import dao.impl.UserDaoImpl;
import model.Address;
import model.District;
import model.ResponseResult;
import model.User;
/**
 * 新增地址
 * @author Willicy
 *
 */
@WebServlet("/addAddress")
public class addAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addAddress() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.getRequestDispatcher("addAddress.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		//Gson将ResponseResult转为json类型
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr = null;
		
		
		DistrictDao districtDao = new DistrictDaoImpl();
		
		AddressDao addressDao = new AddressDaoImpl();
		Address address= new Address();
		
		
		
		try {
			// 根据session获取uid
			Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
			
			// 将uid封装到address中
			address.setUid(uid);
			
			//查看用户是否已有地址数据，如没有则设置为默认
			Integer count = addressDao.getCountByUid(
					address.getUid());
			address.setIsDefault(count == 0 ? 1 : 0);
				
			
			//District 赋值
			address.setProvince(request.getParameter("province"));
			address.setCity(request.getParameter("city"));
			address.setArea(request.getParameter("area"));
			
			String provinceName = null;
			String cityName = null;
			String areaName = null;
			String district =null;
			District p = districtDao.findByCode(address.getProvince());
			District c = districtDao.findByCode(address.getCity());
			District a = districtDao.findByCode(address.getArea());
			if (p != null&c != null&a != null) {
				provinceName = p.getName();
				cityName = c.getName();
				areaName = a.getName();
				district=provinceName + ", " + cityName + ", " + areaName;
			}
			
			if(district==null){
				//如果没获取到地名 发生错误
				throw new Exception();
			}else{
				address.setDistrict(district);
				address.setName(request.getParameter("name"));
				address.setZip(request.getParameter("zip"));
				address.setAddress(request.getParameter("address"));
				address.setPhone(request.getParameter("phone"));
				address.setTag(request.getParameter("tag"));
				
				//新增地址数据
				Integer rs=addressDao.addnew(address);
				if(rs!=1){
					throw new Exception();
				}
				rr = new ResponseResult<Void>(200);
				out.print(gson.toJson(rr));
			}
			
				
			} catch (Exception e) {
				e.printStackTrace();
				rr = new ResponseResult<Void>(601,"增加收货信息时发生未知错误");
				out.println(gson.toJson(rr));
				
			}
			
		
		
	}
	
	
}
