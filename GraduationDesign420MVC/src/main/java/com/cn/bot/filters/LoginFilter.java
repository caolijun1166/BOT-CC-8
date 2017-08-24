package com.cn.bot.filters;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(filterName="LoginFilter", urlPatterns={"/humitemp.html","/history.html"})
//应将humitemp.html,history.html（要过滤的页面根据实际需要）设置为默认浏览器不缓存，否则浏览器在第一次请求被过滤页面时，能通过过滤器，之后的请求就不会通过浏览器，因为浏览器直接载入之前获取的缓存页面
public class LoginFilter implements Filter {
	Logger log = Logger.getLogger(LoginFilter.class);

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		HttpSession isExist = req.getSession(false);
		if(isExist == null){
			log.debug("=====================================================HttpSession is null !=====================================================");
			resp.sendRedirect("hintLogin.html");
		}else{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			log.debug("=====================================================HttpSession is exist, the creation time is: " + formatter.format(isExist.getCreationTime()) + " and the SessionID is: " + isExist.getId()+ "=====================================================");
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		log.debug("=====================================================LoginFilter init()!=====================================================");
	}

}
