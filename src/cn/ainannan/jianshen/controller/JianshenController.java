package cn.ainannan.jianshen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ainannan.jianshen.bean.Yangwoqizuo;
import cn.ainannan.jianshen.service.YangwoqizuoService;
import cn.ainannan.sys.utils.DateUtil;

@Controller
@RequestMapping(value="jianshen.do")
public class JianshenController {
	
	@Autowired
	private YangwoqizuoService ywqzService = null;
	
	/**
	 * 仰卧起坐跳转到添加页面
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "method=preAddYwqz")
	public String preAddYwqz(HttpServletRequest req) {
		req.setAttribute("date", DateUtil.getCurrDateString());
		return "tool/jianshen/addYwqz";
	}
	
	/**
	 * 仰卧起坐添加
	 * @param req
	 * @param ywqz
	 * @return
	 */
	@RequestMapping(params = "method=addYwqz")
	public String addYwqz(HttpServletRequest req,Yangwoqizuo ywqz) {
		ywqz.setId(ywqzService.getNewId());
		
		ywqzService.save(ywqz);
		return "redirect:jianshen.do?method=list";
	}
	
	@RequestMapping(params = "method=list")
	public String list(HttpServletRequest req) {
		req.setAttribute("ywqzList", ywqzService.getAll());
		return "tool/jianshen/list";
	}
	
	
}
