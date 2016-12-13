package cn.ainannan.shf.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import cn.ainannan.shf.bean.ShenghuofeiShezhi;
import cn.ainannan.sys.FileNameState;
import cn.ainannan.sys.State;
import cn.ainannan.sys.utils.Dom4jUtil;
import cn.ainannan.sys.utils.NumberUtil;

@Service
public class ShenghuofeiShezhiService {

	public void save(ShenghuofeiShezhi object) {
		
		List<ShenghuofeiShezhi> list = getAll();
		list.add(0, object);
		
		saveXMLByList(list);
	}
	
	/**
	 * 根据ID进行保存
	 * @param object
	 */
	public void saveById(ShenghuofeiShezhi object) {
		
		List<ShenghuofeiShezhi> list = getAll();
		
		for(int i = 0; i < list.size(); i ++){
			
			if(list.get(i).getId() == object.getId()){
				list.set(i, object);
			}
		}
		
		saveXMLByList(list);
	}

	public List<ShenghuofeiShezhi> getAll() {
		List<ShenghuofeiShezhi> list = new ArrayList<ShenghuofeiShezhi>();

		SAXReader reader = new SAXReader();
		Document document = Dom4jUtil.getDocument(false,
				FileNameState.SHENGHUOFEISHEZHI_PATH, FileNameState.SHENGHUOFEISHEZHI_FILENAME);

		// 文档不为空时
		if (document != null) {
			Element root = document.getRootElement();
			List list2 = root.elements("shezhi");
			for (Iterator it = list2.iterator(); it.hasNext();) {
				Element element = (Element) it.next();
				
				double money = Double.parseDouble(element.element("money").getText());				// 总金额
				double yiyongMoney = Double.parseDouble(element.element("yiyongMoney").getText());	// 已用
				double weiyongMoney =  NumberUtil.changeDouble(money - yiyongMoney);
				int isChaozhi = yiyongMoney > money ? 1 : 0;
				
				ShenghuofeiShezhi object = new ShenghuofeiShezhi(
						Integer.parseInt(element.element("id").getText()),
						element.element("niandu").getText(),
						element.element("month").getText(),
						Double.parseDouble(element.element("money").getText()),
						Double.parseDouble(element.element("yiyongMoney").getText()),
						weiyongMoney,
						getRijunByMoney(weiyongMoney, Integer.parseInt(element.element("month").getText())),
						Integer.parseInt(element.element("jiezhangState").getText()),
						isChaozhi,
						isChaozhi == 1 ? yiyongMoney - money : 0
					);

				list.add(object);
			}
		}

		return list;
	}

	
	public void saveXMLByList(List<ShenghuofeiShezhi> list) {

		Document document = DocumentHelper.createDocument();

		Element elements = document.addElement("shezhis");

		for (ShenghuofeiShezhi object : list) {
			Element element = elements.addElement("shezhi");
			
			Element id = element.addElement("id");
			id.setText(String.valueOf(object.getId()));

			Element niandu = element.addElement("niandu");
			niandu.setText(object.getNiandu());

			Element month = element.addElement("month");
			month.setText(object.getMonth());

			Element money = element.addElement("money");
			money.setText(String.valueOf(object.getMoney()));

			Element yiyongMoney = element.addElement("yiyongMoney");
			yiyongMoney.setText(String.valueOf(object.getYiyongMoney()));
			
			Element jiezhangState = element.addElement("jiezhangState");
			jiezhangState.setText(String.valueOf(object.getJiezhangState()));
			
		}

		Dom4jUtil.saveXML(document, Dom4jUtil.getFileName(
				FileNameState.SHENGHUOFEISHEZHI_PATH, FileNameState.SHENGHUOFEISHEZHI_FILENAME), "utf-8"); // 
	}

	/**
	 * 获得设置类的新ID
	 * @return
	 */
	public int getNewId() {
		int oldId = 0;
		List<ShenghuofeiShezhi> list = getAll();
		for (ShenghuofeiShezhi object : list) {
			int id = object.getId();
			oldId = id > oldId ? id : oldId;
		}
		return (oldId + 1);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void delete(int id) {

		List<ShenghuofeiShezhi> list = getAll();
		
		for(int i = 0; i < list.size(); i ++){
			// 相同ID
			if(list.get(i).getId() == id){
				list.remove(i);
				break;
			}
		}
		saveXMLByList(list);
	}
	
	/**
	 * 通过ID查询对象
	 * @param id
	 * @return
	 */
	public ShenghuofeiShezhi getById(int id) {
		
		List<ShenghuofeiShezhi> list = getAll();
		for (ShenghuofeiShezhi object : list) {
			
			if(object.getId() == id){
				
				return object;
			}
		}
		
		return null;
	}
	
	/**
	 * 通过参数获得日均标准
	 * @param money
	 * @param yiyongMoney
	 * @param month
	 * @return
	 */
	public static double getRijunByMoney(double weiyongMoney,int month){
		double rijun = 0;
		int [] months = {31,28,31,30,31,30,31,31,30,31,30,31};
		
		int days = months[month - 1];	// 本月天数
		int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);		// 当前日期
		
		int yuDay = days - nowDay;
		rijun = NumberUtil.changeDouble(weiyongMoney / yuDay);	// 转换成小数点保留2位
		rijun = rijun < 0 ? 0 : rijun;	// 最小不能小于零的操作
		
        return rijun;
	}
	
	/**
	 * 得到设置类的实例，仅包含当前年月的字段
	 * @return
	 */
	public ShenghuofeiShezhi getShezhiJustNianduMonth(){
		return new ShenghuofeiShezhi(
				String.valueOf(Calendar.getInstance().get(Calendar.YEAR)), 
				String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
	}
	
	/**
	 * 根据年度、月份获得设置表
	 * @param niandu
	 * @param month
	 * @return
	 */
	public  ShenghuofeiShezhi getShezhiByNianduMonth(ShenghuofeiShezhi sz){
		List<ShenghuofeiShezhi> shezhiList = getAll();
		ShenghuofeiShezhi shezhi = null;
		for(ShenghuofeiShezhi shezhi2 : shezhiList){
			if(isPipei(shezhi2, sz)){
				shezhi = shezhi2;
				break;
			}
		}
		
		return shezhi;
	}
	
	/**
	 * 判断是否有重复数据
	 * @param shezhi
	 * @return
	 */
	public boolean isChongfuByList(ShenghuofeiShezhi shezhi){
		boolean flag = false;
		for(ShenghuofeiShezhi sz : getAll()){
			if(isPipei(sz, shezhi)){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 判断两个额度是否为同年同月
	 * @param sz1
	 * @param sz2
	 * @return
	 */
	public static boolean isPipei(ShenghuofeiShezhi sz1,ShenghuofeiShezhi sz2){
		boolean flag = false;
		
		if(sz1.getNiandu().equals(sz2.getNiandu()) && sz1.getMonth().equals(sz2.getMonth())){
			flag = true;
		}
		
		return flag;
	}

	/**
	 * 记账操作
	 * @param inputDate	要入账的年度月份
	 * @param money 入账金额
	 * @param flag 
	 */
	public void jizhang(String inputDate, double money, boolean flag) {
		String [] dates = inputDate.split("-");
		ShenghuofeiShezhi sz = new ShenghuofeiShezhi(dates[0], dates[1]);
		ShenghuofeiShezhi shezhi = this.getShezhiByNianduMonth(sz);
		
		// 判断状态：true为增加，false为扣除
		if(flag){
			shezhi.setYiyongMoney(NumberUtil.changeDouble(shezhi.getYiyongMoney() + money));
		}else{
			shezhi.setYiyongMoney(NumberUtil.changeDouble(shezhi.getYiyongMoney() - money));
		}
		
		List<ShenghuofeiShezhi> list = getAll();
		
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getId() == shezhi.getId()){
				list.set(i, shezhi);
				break;
			}
		}
		
		saveXMLByList(list);
	}

	public ShenghuofeiShezhi getByParam(ShenghuofeiShezhi precidingShezhi) {
		List<ShenghuofeiShezhi> shezhiList = getAll();
		ShenghuofeiShezhi shezhi = null;
		for(ShenghuofeiShezhi shezhi2 : shezhiList){
			// 年度月份相同 并且已结账状态的
			if(isPipei(shezhi2, precidingShezhi) && shezhi2.getJiezhangState() == State.YES){
				shezhi = shezhi2;
				break;
			}
		}
		
		return shezhi;
	}


}
