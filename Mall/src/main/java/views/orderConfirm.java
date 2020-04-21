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
/**
 * 创建订单
 */
@WebServlet("/order")
public class orderConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public orderConfirm() {
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
		
		CartDao cartDao = new CartDaoImpl();
		AddressDao addressDao=new AddressDaoImpl();
		OrderDao orderDao = new OrderDaoImpl();
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr;
		
		try{
			String[] idsS = request.getParameterValues("cart_id");
			Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
			Integer addressId = Integer.valueOf(request.getParameter("address"));
			
			Integer []cartIds=new Integer[idsS.length];
			int i=0;
			for(String str:idsS){
				cartIds[i]=Integer.valueOf(str);
				i++;
			}
			// 创建Date对象
			Date now = new Date(System.currentTimeMillis());
			
		    // 声明pay变量
			Long pay=0L;
		    // List<CartVO> cartService.getByIds(ids)
			List<CartVO> carts = cartDao.findByIds(cartIds);

		    // 创建List<OrderItem> orderItems
			List<OrderItem> orderItems=new ArrayList<OrderItem>();
		    // 遍历集合，过程中，计算总价pay
			for (CartVO cartVO:carts){
				pay += cartVO.getNewPrice()*cartVO.getCount();
		    // -- 创建OrderItem
				OrderItem item = new OrderItem();
		    // -- item属性：goods_5，OK f
				item.setGoodsId(cartVO.getGid());
				item.setGoodsTitle(cartVO.getTitle());
				item.setGoodsImage(cartVO.getImage());
				item.setGoodsPrice(cartVO.getNewPrice());
				item.setGoodsCount(cartVO.getCount());
				orderItems.add(item);
			}
		    // 创建Order对象
			Order order = new Order();
		    // order属性：uid，OK
			order.setUid(uid);
		    // order属性：pay，OK
			order.setPay(pay);
		    // 通过addressService.getById()得到收货地址数据
			Address address = addressDao.findById(addressId);
			
			if(address==null){
				rr = new ResponseResult<Void>(500,"创建订单失败！收货地址数据有误，请刷新");
				out.println(gson.toJson(rr));
			}else{
			
		    // order属性：recv_4，OK
			order.setRecvName(address.getName());
			order.setRecvPhone(address.getPhone());
			order.setRecvDistrict(address.getDistrict());
			order.setRecvAddress(address.getAddress());
		    // order属性：order_time，OK
			
			order.setOrderTime(now);
		    // order属性：status，OK，值为0
			order.setStatus(0);
		    // 插入订单数据并获取oid：insertOrder(order)
			Integer orderId =orderDao.insertOrder(order);
			
		    // 遍历orderItems
			for (OrderItem orderItem : orderItems) {
		        // item属性：oid
				orderItem.setOid(orderId);
		        // 插入订单商品数据
				orderDao.insertOrderItem(orderItem);
			}
			
			for(Integer id : cartIds){
				Cart data = cartDao.findById(id);
				//判断是否查询到数据
				if(data == null || data.getUid()!=uid){
					throw new Exception();
				}else{
					Integer rs = cartDao.deleteById(id);
					if(rs != 1){
						throw new Exception();
					}
				}
			}
			
		
			rr = new ResponseResult<Void>(200);
			out.println(gson.toJson(rr));
			}
		}catch(Exception e){
			e.printStackTrace();
			rr = new ResponseResult<Void>(601,"未知错误");
			out.println(gson.toJson(rr));
		}
		}	
	
}
