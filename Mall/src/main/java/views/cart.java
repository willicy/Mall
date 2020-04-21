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

import dao.CartDao;
import dao.impl.CartDaoImpl;
import model.Cart;
import model.ResponseResult;
import model.vo.CartVO;
/**
 * 购物车
 * @author Willicy
 *
 */
@WebServlet("/cart")
public class cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public cart() {
        super();
         
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CartDao cartDao = new CartDaoImpl();
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		
		//Gson将ResponseResult转为json类型
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr = null;
		
	
		try {
			//根据function判断要如何处理业务
			if("list".equals(request.getParameter("function"))){
				//获取用户购物车数据
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				
				//删除直接购买的数据，直接购买引用了购物车当载体，每次访问购物车时应当先删除数据
				cartDao.deleteByBuy();
				
				
				List<CartVO> list= cartDao.findByUid(uid);
			    // 返回
				ResponseResult<List<CartVO>> rrs= new ResponseResult<List<CartVO>>(200,list);
				out.print(gson.toJson(rrs));
			}else if("add_count".equals(request.getParameter("function"))){
				//增加商品数量
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				
				Integer id =Integer.valueOf(request.getParameter("id"));
				
				
				 // 根据id查询数据
				Cart data = cartDao.findById(id);
			    // 判断数据是否为null
				if(data == null){
			   
					rr=new ResponseResult<Void>(500,"修改商品数量失败！尝试访问的购物车数据不存在");
				}else if(!data.getUid().equals(uid)){
			    
					rr=new ResponseResult<Void>(500,"修改商品数量失败！访问数据权限验证不通过");
				}else{
			    // 获取原来的数量
				Integer count=data.getCount();
			    // 将数量+1
				count++;
			    // 更新购物车数据中的数量:updateCount(id, count)
				cartDao.updateCount(id,count);
	
				    // 返回
				rr=new ResponseResult<>(200);
				}
				out.print(gson.toJson(rr));
			}else if("reduce_count".equals(request.getParameter("function"))){
				//减少商品数量
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				
				Integer id =Integer.valueOf(request.getParameter("id"));
				
				
				 // 根据id查询数据
				Cart data = cartDao.findById(id);
			    // 判断数据是否为null
				if(data == null){
			   
					rr=new ResponseResult<Void>(500,"修改商品数量失败！尝试访问的购物车数据不存在");
				}else if(!data.getUid().equals(uid)){
			    
					rr=new ResponseResult<Void>(500,"修改商品数量失败！访问数据权限验证不通过");
				}else{
			    // 获取原来的数量
				Integer count=data.getCount();
			    // 将数量-1
				count--;
			    // 更新购物车数据中的数量:updateCount(id, count)
				cartDao.updateCount(id,count);
	
				    // 返回
				rr=new ResponseResult<>(200);
				}
				out.print(gson.toJson(rr));
			
			}else if("get_by_ids".equals(request.getParameter("function"))){
				//透过数据id获取购物车数据
				
				String[] idsS = request.getParameterValues("cart_id");
				
			
				Integer []ids=new Integer[idsS.length];
				int i=0;
				for(String str:idsS){
					ids[i]=Integer.valueOf(str);
					i++;
				}
				
				List<CartVO> list= cartDao.findByIds(ids);

			    // 返回
				ResponseResult<List<CartVO>> rrs=new ResponseResult<List<CartVO>>(200,list);
				out.print(gson.toJson(rrs));
			}else if("delete".equals(request.getParameter("function"))){
				//删除购物车数据
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				
				Integer id = Integer.valueOf(request.getParameter("id"));
				Cart data = cartDao.findById(id);
				//判断是否查询到数据
				if(data == null){
					rr =new ResponseResult<Void>(500,"尝试删除的购物车数据不存在 ");
		
				}else if(data.getUid()!=uid){
					rr =new ResponseResult<Void>(500,"删除购物车失败！访问权限验证不通过");
				}else{
					Integer rs = cartDao.deleteById(id);
					if(rs != 1){
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
		
		CartDao cartDao = new CartDaoImpl();
		
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr;
		Integer uid=null;
		Cart cart = new Cart();
		//新增购物车数据
		try{
			 // 从session中获取uid
			uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
			
			try {
				
				
				
				
				// 将uid封装到cart中
				Long goodsId = Long.valueOf(request.getParameter("gid"));
				
				Long price = Long.valueOf(request.getParameter("price"));
				
				Integer count = Integer.valueOf(request.getParameter("count"));
				
				Integer buy = Integer.valueOf(request.getParameter("buy"));
				cart.setGid(goodsId);
				cart.setUid(uid);
				cart.setPrice(price);
				cart.setCount(count);
				Cart data = cartDao.findByUidAndGid(uid, goodsId);
				Integer dataId;
				
				//判断是直接购买还是加到购物车
				if(buy==1){
					cart.setBuy(1);
					dataId=cartDao.addnew(cart);
					
				}else{
				    // 判断查询结果是否为null
					if (data == null){
				    // 是：该用户尚未在购物车中添加该商品，则执行新增
						dataId=cartDao.addnew(cart);
						
					} else {
						
				    // 否：该用户已经在购物车中添加该商品，则取出此前查询到的数据中的id和count
						dataId = data.getId();
						Integer oldCount = data.getCount();
				    // -- 根据上一步取出的count与参数cart中的count（此次用户提交的count），相加得到新的count
						Integer newCount = oldCount + cart.getCount();
				    // -- 执行更新
						cartDao.updateCount(dataId, newCount);
					}
				}
				
			    // 返回
				ResponseResult<Integer>rrs=new ResponseResult<Integer>(200,dataId);
				
				out.println(gson.toJson(rrs));
				
				} catch (Exception e) {
					e.printStackTrace();
					rr = new ResponseResult<Void>(601,"未知错误");
					out.println(gson.toJson(rr));
					
				}
		}catch(Exception e){
			rr = new ResponseResult<Void>(602,"请先登录");
			out.println(gson.toJson(rr));
		}
		}	
	
}
