package interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 访问拦截器,判断是否已登录  否:将不在白名单的访问路径转到登录页面。
 * @author Willicy
 *
 */
public class LoginInterceptor implements Filter {

	Logger log = Logger.getLogger(LoginInterceptor.class);  
	  
	private static final String NO_CHECK = "noCheck";  
	private static final String REDIRECT_PATH = "redirectPath";  
	private List<String> noCheckList = new ArrayList<String>();  
	private String redirectPath = "/login.jsp";  
	  
	public void init(FilterConfig init) throws ServletException {  
	log.info("初始化filter....");  
	String noChecks = init.getInitParameter(NO_CHECK);  
		if(StringUtils.isNotBlank(noChecks)){  
			if(StringUtils.indexOf(noChecks,",")!=-1){  
				for(String no : noChecks.split(",")){  
					noCheckList.add(StringUtils.trimToEmpty(no));  
				}  
			}else{  
				noCheckList.add(noChecks);  
			}  
		}  
		String path = init.getInitParameter(REDIRECT_PATH);  
		if(StringUtils.isNotBlank(path)){  
			redirectPath = path;  
		}  
	}  
	  
	private boolean check(String path) {  
		if (noCheckList == null || noCheckList.size() <= 0)  
		return false;  
		for (String s : noCheckList) {  
			if (path.indexOf(s) > -1) {  
			return true;  
			}  
		}  
		return false;  
	}  
	private boolean sessionCheck(HttpServletRequest request) {  
	try {
		
		Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
		
		if(SessionMap.sessionMap.get(uid)!=request.getSession().getId()) {
			return true;
		};
		
	}catch(Exception e) {
		return false; 
	}
	return false;
	
	 
	}  
	public void doFilter(ServletRequest arg0, ServletResponse arg1,  
	FilterChain arg2) throws IOException, ServletException {  
	HttpServletRequest request = (HttpServletRequest) arg0;  
	HttpServletResponse response = (HttpServletResponse) arg1;  
	
	log.info("被filter过滤.......");  
	String contextpath = request.getContextPath();  
	if("/".equals(contextpath)){contextpath="";}  
	if(sessionCheck(request)) {
		request.getSession().removeAttribute("uid");
		request.getSession().removeAttribute("username");
		request.getSession().setAttribute("other_Login","true");
		response.sendRedirect(response.encodeURL(contextpath+redirectPath));  
		
		
    }else {
			if(check(request.getRequestURI())){  
				
				log.info("白名单");  
				arg2.doFilter(request, response);  
			}else{
				try{
					
						Integer uid = Integer.valueOf(request.getSession().getAttribute("uid").toString());
						if(uid !=null){  
							
							arg2.doFilter(request, response);
						}
					}catch(Exception e){
						
						
							log.info("黑名单");   
							response.sendRedirect(response.encodeURL(contextpath+redirectPath));  
					}  
			}	
		}  
	}  
	public void destroy() {  
	log.info("销毁filter....");  
	}  
	  
	

}