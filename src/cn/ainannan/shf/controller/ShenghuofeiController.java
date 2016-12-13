package cn.ainannan.shf.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ainannan.shf.bean.Shenghuofei;
import cn.ainannan.shf.bean.ShenghuofeiShezhi;
import cn.ainannan.shf.service.ShenghuofeiService;
import cn.ainannan.shf.service.ShenghuofeiShezhiService;
import cn.ainannan.sys.JSescape;
import cn.ainannan.sys.State;

@Controller
@RequestMapping(value = "shf.do")
public class ShenghuofeiController {
	
	@Autowired
	private ShenghuofeiService shfService;
	@Autowired
	private ShenghuofeiShezhiService shezhiService;
	
	/**
	 * 生活费列表
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "method=list")
	public String list(HttpServletRequest req) {
		
		req.setAttribute("shfList", shfService.getAll());
		req.setAttribute("shezhiList", shezhiService.getAll());
		
		return "tool/shenghuofei/list";
	}
	
	/**
	 * 跳转到添加页面
	 * @param shf
	 * @return
	 */
	@RequestMapping(params = "method=preAdd")
	public String preAdd(Shenghuofei shf, HttpServletRequest req) {
		if(null == shezhiService.getShezhiByNianduMonth(shezhiService.getShezhiJustNianduMonth())){
			req.getSession().setAttribute("msg", "当前月份的预算额度未设置，请先设置预算额度！");
			return "redirect:shf.do?method=list";
		}else{
			req.setAttribute("date", new Date());
			return "tool/shenghuofei/add";
		}
	}
	
	/**
	 * 添加生活费
	 * @param shf
	 * @return
	 */
	@RequestMapping(params = "method=add")
	public String add(Shenghuofei shf, HttpServletRequest req) {
		shf.setId(shfService.getNewId());
		shf.setRemark(JSescape.unescape(JSescape.unescape(shf.getRemark())));
		
		shfService.save(shf);
		// 如果生活费的状态为已入账，则在生活费设置里扣钱
		if(shf.getState() == State.YES){
			// 扣钱操作
			shezhiService.jizhang(shf.getInputDate(),shf.getMoney(),true);
		}
		
		req.getSession().setAttribute("msg", "记录消费成功！");
		return "redirect:shf.do?method=list";
	}
	
	/**
	 * 异步入账
	 * @param id
	 * @param req
	 * @param resp
	 */
	@RequestMapping(params = "method=ruzhang")
	public void ruzhang(Integer id, HttpServletRequest req, HttpServletResponse resp) {
		
		Shenghuofei shenghuofei = shfService.getById(id);
		
		shenghuofei.setState(State.YES);  	// 标记为已入账
		
		shfService.saveById(shenghuofei);  	 	// 保存
		
		shezhiService.jizhang(shenghuofei.getInputDate(), shenghuofei.getMoney(), true);	// 生活费预算扣钱
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		try {
			out = resp.getWriter(); // 需要抛出 异常

			out.write(String.valueOf(shenghuofei.getMoney()));	// 将添加金额传送到前端页面
			out.flush();
			out.close();
		} catch (IOException e) {
		}
	}
	
	/**
	 * 删除消费
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "method=delete")
	public String delete(Integer id, HttpServletRequest req) {
		
		// 判断该笔消费是否已经入账
		Shenghuofei shenghuofei = shfService.getById(id);
		// 已经入账了，要从预算里面刨出来
		if(shenghuofei.getState() == State.YES){
			String [] date = shenghuofei.getInputDate().split("-");
			// 根据年度月份获得预算表
			shezhiService.jizhang(shenghuofei.getInputDate(), shenghuofei.getMoney(), false); 	// 记账
		}
		
		shfService.delete(id);  	// 删除生活费
		
		req.getSession().setAttribute("msg", "删除成功！");
		
		return "redirect:shf.do?method=list";
	}
	
	// ======================================   预算      ======================================
	
	/**
	 * 跳转到预算添加页面
	 * @param shf
	 * @return
	 */
	@RequestMapping(params = "method=preAddShezhi")
	public String preAddShezhi(ShenghuofeiShezhi shezhi, HttpServletRequest req) {
		
		shezhi = shezhiService.getShezhiJustNianduMonth();
		shezhi.setMoney(400d); 		// 初始化金额
		
		// 获取上月的预算记录
		ShenghuofeiShezhi precidingShezhi = new ShenghuofeiShezhi(
				String.valueOf(Calendar.getInstance().get(Calendar.YEAR)), 
				String.valueOf(Calendar.getInstance().get(Calendar.MONTH)));
		
		precidingShezhi = shezhiService.getByParam(precidingShezhi);
		
		req.setAttribute("shezhi", shezhi);
		req.setAttribute("precidingShezhi", precidingShezhi);
		
		return "tool/shenghuofei/addShezhi";
	}
	
	/**
	 * 预算添加
	 * @param shezhi
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "method=addShezhi")
	public String addShezhi(ShenghuofeiShezhi shezhi, HttpServletRequest req) {
		
		shezhi.setId(shezhiService.getNewId());
		shezhi.setYiyongMoney(0d);
		
		shezhiService.save(shezhi);
		req.getSession().setAttribute("msg", "预算额度设置成功！");
		return "redirect:shf.do?method=list";
	}
	
	/**
	 * 生活费设置跳转到修改页面
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "method=preUpdateShezhi")
	public String preUpdateShezhi(ShenghuofeiShezhi shezhi, HttpServletRequest req) {
		
		if(shezhi.getId() != 0){
			ShenghuofeiShezhi sz = shezhiService.getById(shezhi.getId());
			req.setAttribute("shezhi", sz);
		}else{
			req.getSession().setAttribute("msg", "参数传递失败！无法修改！！");
			return "redirect:shf.do?method=list";
		}
		
		return "tool/shenghuofei/updateShezhi";
	}
	
	/**
	 * 预算修改
	 * @param shezhi
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "method=updateShezhi")
	public String updateShezhi(ShenghuofeiShezhi shezhi, HttpServletRequest req) {
		
		shezhiService.saveById(shezhi);
		req.getSession().setAttribute("msg", "预算额度修改成功！");
		return "redirect:shf.do?method=list";
	}
	
	/**
	 * 删除
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "method=deleteShezhi")
	public String deleteShezhi(Integer id, HttpServletRequest req) {
		
		shezhiService.delete(id);
		req.getSession().setAttribute("msg", "删除预算成功！");
		
		return "redirect:shf.do?method=list";
	}
	
	/**
	 * 跳转到结账页面
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "method=preJiezhang")
	public String preJiezhang(Integer id, HttpServletRequest req) {
		
		ShenghuofeiShezhi shezhi = shezhiService.getById(id);
		
		List<Shenghuofei> shfList = shfService.getListByShezhi(shezhi);
		
		req.getSession().setAttribute("shezhi", shezhi);
		req.getSession().setAttribute("shfList", shfList);
		
		return "tool/shenghuofei/jiezhang";
	}
	
	/**
	 * 结账操作
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "method=jiezhang")
	public String jiezhang(Integer id, HttpServletRequest req) {
		
		ShenghuofeiShezhi shezhi = shezhiService.getById(id);
		
		shezhi.setJiezhangState(State.YES);  	// 已结账
		shezhiService.saveById(shezhi);
		
		req.getSession().setAttribute("msg", "预算结账成功！");
		
		return "redirect:shf.do?method=list";
	}
}
