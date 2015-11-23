package usee.com.utils;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class LogUtils {
public static String logRequest(HttpServletRequest request){
	StringBuffer str=new StringBuffer();
	
	Enumeration enu=request.getParameterNames();  
	while(enu.hasMoreElements()){  
	String paraName=(String)enu.nextElement();  
	str.append(paraName+": "+request.getParameter(paraName)+" ");  
	}  
	return str.toString();
}
}
