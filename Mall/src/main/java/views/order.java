package views;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.AddressDao;
import dao.CartDao;
import dao.OrderDao;
import dao.impl.AddressDaoImpl;
import dao.impl.CartDaoImpl;
import dao.impl.OrderDaoImpl;
import model.Address;
import model.Cart;
import model.Order;
import model.OrderItem;
import model.ResponseResult;
import model.vo.CartVO;
import model.vo.OrderVO;
/**
 * 订单
 */
@WebServlet("/orderList")
public class order extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public order() {
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
	
		
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		
		OrderDao orderDao = new OrderDaoImpl();
		ResponseResult<Void> rr;
		List<OrderVO> orderVOList =new ArrayList<OrderVO>();
		
		try{
			Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
			List<Integer> orderList =orderDao.findByUid(uid);
			//判断是否有已创立订单
			if(orderList.size()==0){
				rr = new ResponseResult<Void>(500,"没有已创立订单");
				out.println(gson.toJson(rr));
			}else{ 
				OrderVO orderVO = null;
				//循环查出订单表
				for(int i=0;i<orderList.size();i++){
					orderVO = orderDao.findById(orderList.get(i));
					orderVOList.add(orderVO);
				}
				
				
				
				ResponseResult<List<OrderVO>> rrs = new ResponseResult<List<OrderVO>>(200,orderVOList);
				out.println(gson.toJson(rrs));
				
			}
		}catch(Exception e){
			e.printStackTrace();
			rr = new ResponseResult<Void>(601,"未知错误");
			out.println(gson.toJson(rr));
		}
	}
	
}
