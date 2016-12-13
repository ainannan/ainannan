package cn.ainannan.login.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ainannan.login.service.CaidanService;
import cn.ainannan.login.service.LoginService;
import cn.ainannan.sys.State;
import cn.ainannan.sys.bean.User;

@Controller
@RequestMapping(value="login.do")
public class LoginController {
	@Autowired
	private LoginService loginService = null;
	@Autowired
	private CaidanService caidanService = null;
	
	/**
	 * 跳转到登录
	 * @return
	 */
	@RequestMapping(params="method=preLogin")
	public String preLogin(boolean msg){
		
		return "login";
	}
	
	@RequestMapping(params="method=login")
	public String login(HttpServletRequest req, User user){
		
		String url = "caidan";
		
		// 如果用户对象为空
		if(null == user.getUserName()){
			req.getSession().setAttribute("msg", "登录状态失效，请重新登录！");
			return "login";
		}
		
		// 用户名密码不正确时
		if(!loginService.checkUser(user)){
			req.getSession().setAttribute("msg", "用户名或密码有误，请重新输入!");
			return "login";
		}else{
			req.getSession().setAttribute("userName", user.getUserName());
		}
		
		req.setAttribute("caidanList", caidanService.getAllCaidanByClickRate(State.YES));
		
		return url;
	}
}
