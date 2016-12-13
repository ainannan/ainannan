package cn.ainannan.jianshen.tizhong.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ainannan.jianshen.tizhong.bean.Tizhong;
import cn.ainannan.jianshen.tizhong.service.TizhongService;
import cn.ainannan.sys.utils.DateUtil;

@Controller
@RequestMapping(value="tizhong.do")
public class TizhongController {
	
	@Autowired
	private TizhongService tizhongService = null;
	
	@RequestMapping(params = "method=list")
	public String list(HttpServletRequest req) {
		req.setAttribute("tizhongList", tizhongService.getAll());
		return "tool/jianshen/tizhong/list";
	}
	

	@RequestMapping(params = "method=preAdd")
	public String preAdd(HttpServletRequest req) {
		req.setAttribute("date", DateUtil.getCurrDateString());
		return "tool/jianshen/tizhong/add";
	}
	
	@RequestMapping(params = "method=add")
	public String add(HttpServletRequest req, Tizhong tizhong) {
		
		tizhong.setId(tizhongService.getNewId());
		tizhongService.saveByMerge(tizhong);
		
		return "redirect:tizhong.do?method=list";
	}
	
	@RequestMapping(params = "method=delete")
	public String delete(HttpServletRequest req, int id) {
		
		tizhongService.delete(id);
		
		return "redirect:tizhong.do?method=list";
	}
	
}
