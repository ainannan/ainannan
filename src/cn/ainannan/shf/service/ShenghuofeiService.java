package cn.ainannan.shf.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.chainsaw.Main;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import cn.ainannan.shf.bean.Shenghuofei;
import cn.ainannan.shf.bean.ShenghuofeiShezhi;
import cn.ainannan.sys.FileNameState;
import cn.ainannan.sys.utils.Dom4jUtil;

@Service
public class ShenghuofeiService {

	/**
	 * 保存
	 * @param object
	 */
	public void save(Shenghuofei object) {
		List<Shenghuofei> list = getAll();
		list.add(0, object);
		
		saveXMLByList(list);
	}
	
	/**
	 * 根据ID保存
	 * @param object
	 */
	public void saveById(Shenghuofei object) {
		
		List<Shenghuofei> list = getAll();
		
		for(int i = 0; i < list.size(); i ++){
			
			if(list.get(i).getId() == object.getId()){
				list.set(i, object);
			}
		}
		
		saveXMLByList(list);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void delete(Integer id) {
		List<Shenghuofei> list = getAll();
		for (int i = 0; i < list.size(); i++) {
			
			if(list.get(i).getId() == id){
				list.remove(i);
				break;
			}
		}
		
		saveXMLByList(list);
	}
	
	public List<Shenghuofei> getAll() {
		List<Shenghuofei> list = new ArrayList<Shenghuofei>();

		SAXReader reader = new SAXReader();
		Document document = Dom4jUtil.getDocument(false,
				FileNameState.SHENGHUOFEI_PATH, FileNameState.SHENGHUOFEI_FILENAME);

		// 文档不为空时
		if (document != null) {
			Element root = document.getRootElement();
			List list2 = root.elements("shf");
			for (Iterator it = list2.iterator(); it.hasNext();) {
				Element element = (Element) it.next();
				Shenghuofei object = new Shenghuofei(
						Integer.parseInt(element.element("id").getText()),
						Double.parseDouble(element.element("money").getText()),
						Integer.parseInt(element.element("zhichuType").getText()),
						Integer.parseInt(element.element("zhichuren").getText()),
						element.element("inputDate").getText(), 
						element.element("remark").getText(),
						Integer.parseInt(element.element("state").getText()));

				list.add(object);
			}
		}
		
		//　按入账日期进行降序排序
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = 1; j < list.size() - i; j++) {
				// 如果第一个日期小于第二个日期时，两条调换位置
				if(list.get(j).getInputDate().compareTo(list.get(j - 1).getInputDate()) > 0 ){
					Shenghuofei shf = list.get(j);
					list.set(j, list.get(j - 1));
					list.set(j - 1, shf);
				}
			}
		}
		
		return list;
	}
	
	public void saveXMLByList(List<Shenghuofei> list) {

		Document document = DocumentHelper.createDocument();

		Element elements = document.addElement("shfs");

		for (Shenghuofei object : list) {
			Element element = elements.addElement("shf");
			// 
			Element id = element.addElement("id");
			id.setText(String.valueOf(object.getId()));

			Element money = element.addElement("money");
			money.setText(String.valueOf(object.getMoney()));

			Element zhichuType = element.addElement("zhichuType");
			zhichuType.setText(String.valueOf(object.getZhichuType()));

			Element zhichuren = element.addElement("zhichuren");
			zhichuren.setText(String.valueOf(object.getZhichuren()));

			Element inputDate = element.addElement("inputDate");
			inputDate.setText(object.getInputDate());

			Element remark = element.addElement("remark");
			remark.setText(object.getRemark());

			Element state = element.addElement("state");
			state.setText(String.valueOf(object.getState()));

		}

		Dom4jUtil.saveXML(document, Dom4jUtil.getFileName(
				FileNameState.SHENGHUOFEI_PATH, FileNameState.SHENGHUOFEI_FILENAME), "utf-8"); // 
	}

	/**
	 * 获得新ID
	 * @return
	 */
	public int getNewId() {
		int oldId = 0;
		List<Shenghuofei> list = getAll();
		for (Shenghuofei object : list) {
			int id = object.getId();
			oldId = id > oldId ? id : oldId;
		}
		return (oldId + 1);
	}

	/**
	 * 根据设置类查找到相关的生活费
	 * @param shezhi
	 * @return
	 */
	public List<Shenghuofei> getListByShezhi(ShenghuofeiShezhi shezhi) {
		
		String str = shezhi.getNiandu() + "-" + shezhi.getMonth();
		List<Shenghuofei> shenghuofeiList = new ArrayList<Shenghuofei>();
		
		List<Shenghuofei> shfList = getAll();
		
		for(Shenghuofei shf : shfList){
			if(shf.getInputDate().indexOf(str) > -1 && shf.getState() == 0){
				shenghuofeiList.add(shf);
			}
		}
		
		return shenghuofeiList;
	}

	/**
	 * 根据ID获得生活费对象
	 * @param id
	 * @return
	 */
	public Shenghuofei getById(int id) {

		List<Shenghuofei> list = getAll();
		for (Shenghuofei object : list) {
			
			if(object.getId() == id){
				
				return object;
			}
		}
		
		return null;
	}
	
	
}
