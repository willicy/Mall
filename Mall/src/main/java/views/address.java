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
 * ��ַ����
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
		
		//Gson��ResponseResultתΪjson����
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr = null;
		
		try {
			// ����session��ȡuid
			Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
			
			//����function�ж�Ҫ��δ���ҵ��
			String function=request.getParameter("function");
			if(function.equals("delete")){
				//ɾ������
				Integer id = Integer.valueOf(request.getParameter("id"));
				Address data = addressDao.findById(id);
				//�ж��Ƿ��ѯ������
				if(data == null){
					rr =new ResponseResult<Void>(500,"����ɾ�����ջ���ַ���ݲ����� ");
		
				}else if(data.getUid()!=uid){
					rr =new ResponseResult<Void>(500,"ɾ���ջ���ַʧ�ܣ�����Ȩ����֤��ͨ��");
				}else{
					Integer rs = addressDao.deleteById(id);
					if(rs != 1){
						throw new Exception();
					}
				
					rr =new ResponseResult<Void>(200);
				}
				out.println(gson.toJson(rr));
			}else if(function.equals("default")){
				
				//����ַ��ΪĬ��
				Integer id = Integer.valueOf(request.getParameter("id"));
				
				Address data = addressDao.findById(id);
			
				//�ж��Ƿ��ѯ������
				if(data == null){
					rr =new ResponseResult<Void>(500,"���Է��ʵ��ջ���ַ���ݲ����� ");
						
				}else if(data.getUid()!=uid){
					
					rr =new ResponseResult<Void>(500,"����Ĭ���ջ���ַʧ�ܣ�����Ȩ����֤��ͨ��");
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
			rr = new ResponseResult<Void>(601,"δ֪����");
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
		
		//Gson��ResponseResultתΪjson����
		Gson gson=new Gson();
		PrintWriter out = response.getWriter();
		 
		ResponseResult<Void> rr;
		
		try {
				// ����session��ȡuid
				Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
				//��ȡ�û��ĵ�ַ����
				List<Address> addressList = addressDao.findByUid(uid);
				
				
				ResponseResult<List<Address>> rrs = new ResponseResult<List<Address>>(200,addressList);
				
				out.println(gson.toJson(rrs));
			} catch (Exception e) {
				e.printStackTrace();
				rr = new ResponseResult<Void>(601,"δ֪����");
				out.println(gson.toJson(rr));
				
			}
			
		}	
	
}
