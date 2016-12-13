package cn.ainannan.sys.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ainannan.login.service.CaidanService;
import cn.ainannan.sys.State;

public class CheckLoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse resp = (HttpServletResponse) arg1;

		// 从session里取的用户名信息
		String userName = (String) req.getSession().getAttribute("userName");
		String uri = ((HttpServletRequest) req).getRequestURI();

		// 判断如果没有取到用户信息,就跳转到登陆页面
		if ((userName != null && !"".equals(userName)) || checkURI(uri)) {

			// 菜单的点击次数计数器
			String caidanId = req.getParameter("caidanId");
			if (null != caidanId && !"".equals(caidanId)) {
				CaidanService caidanService = new CaidanService();
				caidanService.countById(Integer.parseInt(caidanId));
			}
			arg2.doFilter(req, resp);
		} else {
			// 跳转到登陆页面
			req.getSession().setAttribute("msg", "登录状态失效，请重新登录！");
			req.getRequestDispatcher("login.do?method=preLogin").forward(req,
					resp);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	/**
	 * 验证URI是否包含以下后缀名的
	 * 
	 * @param uri
	 * @return
	 */
	private boolean checkURI(String uri) {
		
		for (int i = 0; i < State.FILTER_URLS.length; i++) {
			if(uri.endsWith(State.FILTER_URLS[i])){
				return false;
			}
		}

		return true;
	}
}
