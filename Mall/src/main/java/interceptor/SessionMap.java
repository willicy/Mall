package interceptor;

import java.util.HashMap;
import java.util.Map;
/**
 * key 为user id value 为session id的map
 * 用于查看可以访问用户的session是什么
 * @author willicy
 *
 */
public class SessionMap {
	public static Map<Integer,String> sessionMap =new HashMap<>();
}
