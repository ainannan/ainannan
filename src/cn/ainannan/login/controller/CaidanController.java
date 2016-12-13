package cn.ainannan.login.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ainannan.login.bean.Caidan;
import cn.ainannan.login.service.CaidanService;
import cn.ainannan.sys.JSescape;
import cn.ainannan.sys.State;

@Controller
@RequestMapping(value="caidan.do")
public class CaidanController {
	
	@Autowired
	private CaidanService caidanService = null;
	
	@RequestMapping(params="method=add")
	public String add(Caidan caidan){
		
		if(caidan.getName() != null && !"".equals(caidan.getName())){
			
			caidan.setId(caidanService.getNewId());
			caidan.setName(JSescape.unescape(JSescape.unescape(caidan.getName())));
			caidan.setUrl(caidan.getUrl() + "&amp;caidanId="+caidan.getId());
			caidan.setClickRate(0);
			
			caidanService.save(caidan);
		}
		
		return "redirect:caidan.do?method=list";
	}
	
	@RequestMapping(params="method=list")
	public String list(HttpServletRequest req){
		
		req.setAttribute("caidanList", caidanService.getAllCaidanByClickRate(State.YES));
		return "caidan";
	}
}
