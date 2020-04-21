package views;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.GoodsDao;
import dao.impl.GoodsDaoImpl;
import model.Goods;
import model.ResponseResult;
/**
 * 商品管理
 * @author Willicy
 *
 */
@WebServlet("/goods")
public class goods extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public goods() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.getRequestDispatcher("product.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		GoodsDao goodsDao = new GoodsDaoImpl();
		
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr = null;
	
		
		try {
			//获取商品信息
			Long id = Long.valueOf(request.getParameter("id"));
			
			Goods goods = goodsDao.findById(id);
			
			ResponseResult<Goods> rrs =new ResponseResult<Goods>(200,goods);
			
			out.print(
					gson.toJson(rrs));
				
			} catch (Exception e) {
				e.printStackTrace();
				rr = new ResponseResult<Void>(601,"未知错误");
				out.println(gson.toJson(rr));
				
			}
			
		}	
	
}
