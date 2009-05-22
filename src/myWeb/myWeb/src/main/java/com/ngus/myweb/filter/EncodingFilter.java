
package com.ngus.myweb.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class EncodingFilter implements Filter{
	public void init(FilterConfig config) throws ServletException{
		encoding = config.getInitParameter("encoding");
	}
	
	public void destroy(){
		encoding = null;
	}
	
	public void doFilter(ServletRequest srequest , ServletResponse sresponse , FilterChain chain) throws IOException, ServletException{
		HttpServletRequest request = (HttpServletRequest) srequest;
		request.setCharacterEncoding(encoding);
		chain.doFilter(srequest,sresponse);
	}
	
	private String encoding;
}
