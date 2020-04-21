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
 * ��������
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
			// ����Date����
			Date now = new Date(System.currentTimeMillis());
			
		    // ����pay����
			Long pay=0L;
		    // List<CartVO> cartService.getByIds(ids)
			List<CartVO> carts = cartDao.findByIds(cartIds);

		    // ����List<OrderItem> orderItems
			List<OrderItem> orderItems=new ArrayList<OrderItem>();
		    // �������ϣ������У������ܼ�pay
			for (CartVO cartVO:carts){
				pay += cartVO.getNewPrice()*cartVO.getCount();
		    // -- ����OrderItem
				OrderItem item = new OrderItem();
		    // -- item���ԣ�goods_5��OK f
				item.setGoodsId(cartVO.getGid());
				item.setGoodsTitle(cartVO.getTitle());
				item.setGoodsImage(cartVO.getImage());
				item.setGoodsPrice(cartVO.getNewPrice());
				item.setGoodsCount(cartVO.getCount());
				orderItems.add(item);
			}
		    // ����Order����
			Order order = new Order();
		    // order���ԣ�uid��OK
			order.setUid(uid);
		    // order���ԣ�pay��OK
			order.setPay(pay);
		    // ͨ��addressService.getById()�õ��ջ���ַ����
			Address address = addressDao.findById(addressId);
			
			if(address==null){
				rr = new ResponseResult<Void>(500,"��������ʧ�ܣ��ջ���ַ����������ˢ��");
				out.println(gson.toJson(rr));
			}else{
			
		    // order���ԣ�recv_4��OK
			order.setRecvName(address.getName());
			order.setRecvPhone(address.getPhone());
			order.setRecvDistrict(address.getDistrict());
			order.setRecvAddress(address.getAddress());
		    // order���ԣ�order_time��OK
			
			order.setOrderTime(now);
		    // order���ԣ�status��OK��ֵΪ0
			order.setStatus(0);
		    // ���붩�����ݲ���ȡoid��insertOrder(order)
			Integer orderId =orderDao.insertOrder(order);
			
		    // ����orderItems
			for (OrderItem orderItem : orderItems) {
		        // item���ԣ�oid
				orderItem.setOid(orderId);
		        // ���붩����Ʒ����
				orderDao.insertOrderItem(orderItem);
			}
			
			for(Integer id : cartIds){
				Cart data = cartDao.findById(id);
				//�ж��Ƿ��ѯ������
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
			rr = new ResponseResult<Void>(601,"δ֪����");
			out.println(gson.toJson(rr));
		}
		}	
	
}
