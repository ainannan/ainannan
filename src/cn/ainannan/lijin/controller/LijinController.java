package cn.ainannan.lijin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ainannan.lijin.bean.Lijin;
import cn.ainannan.lijin.service.LijinService;
import cn.ainannan.sys.JSescape;
import cn.ainannan.sys.utils.DateUtil;

@Controller
@RequestMapping(value = "lijin.do")
public class LijinController {

	@Autowired
	private LijinService lijinService = null;

	@RequestMapping(params = "method=preAdd")
	public String preAdd(HttpServletRequest req) {
		req.setAttribute("date", DateUtil.getCurrDateString());
		req.setAttribute("lijinList", lijinService.getAll());
		return "tool/lijin/add";
	}
	

	@RequestMapping(params = "method=add")
	public String add(HttpServletRequest req, Lijin lijin) {
		
		lijin.setId(lijinService.getNewId());
		lijin.setName(JSescape.unescape(JSescape.unescape(lijin.getName())));
		lijinService.save(lijin);
		return "redirect:lijin.do?method=list";
	}
	

	@RequestMapping(params = "method=delete")
	public String delete(int id) {
		lijinService.delete(id);
		return "redirect:lijin.do?method=list";
	}
	
	@RequestMapping(params = "method=biaojiYihuan")
	public String biaojiYihuan(int id) {
		lijinService.biaojiYihuan(id);
		return "redirect:lijin.do?method=list";
	}

	@RequestMapping(params = "method=list")
	public String list(HttpServletRequest req) {
		req.setAttribute("lijinList", lijinService.getAll());
		return "tool/lijin/list";
	}
	
	@RequestMapping(params = "method=searchName")
	public void searchName(String str, HttpServletResponse resp) {
		
		
		
		JSONObject json = new JSONObject();
		json.put("nameList", getJsonStr(lijinService.getNameList(str)));
		
		resp.setContentType("application/x-json;charset=utf-8");
		try {
			json.write(resp.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*public String getJsonStr(Object obj) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONObject json =JSONObject.fromObject(obj, jsonConfig);
		
		String str = json.toString();// 将json对象转换为字符串
		
		System.out.println("JSON:" + str);	// 测试
		return str;
	}*/
	
	public String getJsonStr(Object obj) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONArray json =JSONArray.fromObject(obj, jsonConfig);
		
		String str = json.toString();// 将json对象转换为字符串
		
		System.out.println("JSON:" + str);	// 测试
		return str;
	}
}
