package org.ys.common.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class RequsetUtils {
	private RequsetUtils() {}
	
	/**
	 * 获取request中的参数并组装成map返回
	 * @param request
	 * @return
	 */
	public static Map<String,Object> getParamsMap(HttpServletRequest request){
		Enumeration<String> enu=request.getParameterNames();  
		Map<String,Object> paramMap = new HashMap<String,Object>();
		while(enu.hasMoreElements()){  
			String paraName=(String)enu.nextElement();  
			//System.out.println(paraName+": "+request.getParameter(paraName)); 
			if(StringUtils.isNotEmpty(paraName)){
				paramMap.put(paraName, request.getParameter(paraName));
			}
		}
		return paramMap;
	}
}
