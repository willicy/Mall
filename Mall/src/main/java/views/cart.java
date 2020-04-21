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
 * ���ﳵ
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
		
		
		//Gson��ResponseResultתΪjson����
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr = null;
		
	
		try {
			//����function�ж�Ҫ��δ���ҵ��
			if("list".equals(request.getParameter("function"))){
				//��ȡ�û����ﳵ����
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				
				//ɾ��ֱ�ӹ�������ݣ�ֱ�ӹ��������˹��ﳵ�����壬ÿ�η��ʹ��ﳵʱӦ����ɾ������
				cartDao.deleteByBuy();
				
				
				List<CartVO> list= cartDao.findByUid(uid);
			    // ����
				ResponseResult<List<CartVO>> rrs= new ResponseResult<List<CartVO>>(200,list);
				out.print(gson.toJson(rrs));
			}else if("add_count".equals(request.getParameter("function"))){
				//������Ʒ����
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				
				Integer id =Integer.valueOf(request.getParameter("id"));
				
				
				 // ����id��ѯ����
				Cart data = cartDao.findById(id);
			    // �ж������Ƿ�Ϊnull
				if(data == null){
			   
					rr=new ResponseResult<Void>(500,"�޸���Ʒ����ʧ�ܣ����Է��ʵĹ��ﳵ���ݲ�����");
				}else if(!data.getUid().equals(uid)){
			    
					rr=new ResponseResult<Void>(500,"�޸���Ʒ����ʧ�ܣ���������Ȩ����֤��ͨ��");
				}else{
			    // ��ȡԭ��������
				Integer count=data.getCount();
			    // ������+1
				count++;
			    // ���¹��ﳵ�����е�����:updateCount(id, count)
				cartDao.updateCount(id,count);
	
				    // ����
				rr=new ResponseResult<>(200);
				}
				out.print(gson.toJson(rr));
			}else if("reduce_count".equals(request.getParameter("function"))){
				//������Ʒ����
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				
				Integer id =Integer.valueOf(request.getParameter("id"));
				
				
				 // ����id��ѯ����
				Cart data = cartDao.findById(id);
			    // �ж������Ƿ�Ϊnull
				if(data == null){
			   
					rr=new ResponseResult<Void>(500,"�޸���Ʒ����ʧ�ܣ����Է��ʵĹ��ﳵ���ݲ�����");
				}else if(!data.getUid().equals(uid)){
			    
					rr=new ResponseResult<Void>(500,"�޸���Ʒ����ʧ�ܣ���������Ȩ����֤��ͨ��");
				}else{
			    // ��ȡԭ��������
				Integer count=data.getCount();
			    // ������-1
				count--;
			    // ���¹��ﳵ�����е�����:updateCount(id, count)
				cartDao.updateCount(id,count);
	
				    // ����
				rr=new ResponseResult<>(200);
				}
				out.print(gson.toJson(rr));
			
			}else if("get_by_ids".equals(request.getParameter("function"))){
				//͸������id��ȡ���ﳵ����
				
				String[] idsS = request.getParameterValues("cart_id");
				
			
				Integer []ids=new Integer[idsS.length];
				int i=0;
				for(String str:idsS){
					ids[i]=Integer.valueOf(str);
					i++;
				}
				
				List<CartVO> list= cartDao.findByIds(ids);

			    // ����
				ResponseResult<List<CartVO>> rrs=new ResponseResult<List<CartVO>>(200,list);
				out.print(gson.toJson(rrs));
			}else if("delete".equals(request.getParameter("function"))){
				//ɾ�����ﳵ����
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				
				Integer id = Integer.valueOf(request.getParameter("id"));
				Cart data = cartDao.findById(id);
				//�ж��Ƿ��ѯ������
				if(data == null){
					rr =new ResponseResult<Void>(500,"����ɾ���Ĺ��ﳵ���ݲ����� ");
		
				}else if(data.getUid()!=uid){
					rr =new ResponseResult<Void>(500,"ɾ�����ﳵʧ�ܣ�����Ȩ����֤��ͨ��");
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
			rr = new ResponseResult<Void>(601,"δ֪����");
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
		//�������ﳵ����
		try{
			 // ��session�л�ȡuid
			uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
			
			try {
				
				
				
				
				// ��uid��װ��cart��
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
				
				//�ж���ֱ�ӹ����Ǽӵ����ﳵ
				if(buy==1){
					cart.setBuy(1);
					dataId=cartDao.addnew(cart);
					
				}else{
				    // �жϲ�ѯ����Ƿ�Ϊnull
					if (data == null){
				    // �ǣ����û���δ�ڹ��ﳵ����Ӹ���Ʒ����ִ������
						dataId=cartDao.addnew(cart);
						
					} else {
						
				    // �񣺸��û��Ѿ��ڹ��ﳵ����Ӹ���Ʒ����ȡ����ǰ��ѯ���������е�id��count
						dataId = data.getId();
						Integer oldCount = data.getCount();
				    // -- ������һ��ȡ����count�����cart�е�count���˴��û��ύ��count������ӵõ��µ�count
						Integer newCount = oldCount + cart.getCount();
				    // -- ִ�и���
						cartDao.updateCount(dataId, newCount);
					}
				}
				
			    // ����
				ResponseResult<Integer>rrs=new ResponseResult<Integer>(200,dataId);
				
				out.println(gson.toJson(rrs));
				
				} catch (Exception e) {
					e.printStackTrace();
					rr = new ResponseResult<Void>(601,"δ֪����");
					out.println(gson.toJson(rr));
					
				}
		}catch(Exception e){
			rr = new ResponseResult<Void>(602,"���ȵ�¼");
			out.println(gson.toJson(rr));
		}
		}	
	
}
