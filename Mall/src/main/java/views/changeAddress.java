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
import dao.DistrictDao;
import dao.impl.AddressDaoImpl;
import dao.impl.DistrictDaoImpl;
import model.Address;
import model.District;
import model.ResponseResult;
/**
 * ���µ�ַ
 * @author Willicy
 *
 */
@WebServlet("/changeAddress")
public class changeAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public changeAddress() {
        super();
         
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DistrictDao districtDao = new DistrictDaoImpl();
		AddressDao addressDao = new AddressDaoImpl();
		
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		//Gson��ResponseResultתΪjson����
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr;
		
		try {
			//����function�ж�Ҫ��ô����ҵ��
			String function = request.getParameter("function");
			
			if("list".equals(function)){
				//���ݵ�ַ����id����������Ϣ
				Integer id = Integer.valueOf(request.getParameter("id"));
				Address address = addressDao.findById(id);
				
				if(address==null){
					throw new Exception();
					
				}
				
				ResponseResult<Address> rrs = new ResponseResult<Address>(200,address);
				
				out.println(gson.toJson(rrs));
			}else if("update".equals(function)){
				//��װ��ַ���ݸ�ֵ
				Address address= new Address();
				address.setName(request.getParameter("name"));
				address.setProvince(request.getParameter("province"));
				address.setCity(request.getParameter("city"));
				address.setArea(request.getParameter("area"));
				address.setZip(request.getParameter("zip"));
				address.setAddress(request.getParameter("address"));
				address.setPhone(request.getParameter("phone"));
				address.setTag(request.getParameter("tag"));
				
				//��ȡdistrict
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
					address.setDistrict(district);
					Integer id = Integer.valueOf(request.getParameter("id"));
					
					//���µ�ַ����
					Integer rs=addressDao.updateInfo(address, id);
					if(rs==1){
						rr = new ResponseResult<Void>(200);
						out.println(gson.toJson(rr));
					}else{
						throw new Exception();
					}
				}else{
					throw new Exception();
				}
				
				
			}
			} catch (Exception e) {
				e.printStackTrace();
				rr = new ResponseResult<Void>(601,"δ֪����");
				out.println(gson.toJson(rr));
				
			}
			
		}	
	
}
