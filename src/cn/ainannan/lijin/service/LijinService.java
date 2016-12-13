package cn.ainannan.lijin.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import cn.ainannan.lijin.bean.Lijin;
import cn.ainannan.sys.FileNameState;
import cn.ainannan.sys.State;
import cn.ainannan.sys.utils.Dom4jUtil;

@Service
public class LijinService {
	
	/**
	 * 入账操作
	 * @param id
	 */
	public void biaojiYihuan(int id){
		List<Lijin> list = getAll();
		for(int i = 0; i < list.size(); i++){
			Lijin lijin = list.get(i);
			if(lijin.getId() == id){
				lijin.setState(State.YES);
				list.set(i, lijin);
				break;
			}
		}
		
		saveXMLByList(list);
	}
	
	/**
	 * 删除方法
	 * @param id
	 */
	public void delete(int id){
		List<Lijin> list = getAll();
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getId() == id){
				list.remove(i);
				break;
			}
		}
		
		saveXMLByList(list);
	}
	
	/**
	 * 保存体重到数据库中
	 * @param object
	 */
	public void save(Lijin object){
		List<Lijin> list = getAll();
		list.add(0, object);
		
		saveXMLByList(list);
	}
	
	/**
	 * 获得最新的ID
	 */
	public int getNewId() {
		int oldId = 0;
		List<Lijin> list = getAll();
		for (Lijin object : list) {
			int id = object.getId();
			oldId = id > oldId ? id : oldId;
		}
		return (oldId + 1);
	}
	
	/**
	 * 获得所有的礼金
	 * @return
	 */
	public List<Lijin> getAll() {
		List<Lijin> list = new ArrayList<Lijin>();

		Document document = Dom4jUtil.getDocument(false,
				FileNameState.LIJIN_PATH, FileNameState.LIJIN_FILENAME);
		// 文档不为空时
		if (document != null) {
			Element root = document.getRootElement();
			List list2 = root.elements("lijin");
			for (Iterator it = list2.iterator(); it.hasNext();) {
				Element element = (Element) it.next();
				Lijin object = new Lijin(
						Integer.parseInt(element.element("id").getText()),
						element.element("name").getText(),
						Integer.parseInt(element.element("money").getText()),
						Integer.parseInt(element.element("guishuren").getText()),
						Integer.parseInt(element.element("state").getText()),
						element.element("inputDate").getText()
						);

				list.add(object);
			}
		}
		
		return list;
	}

	/**
	 * 添加菜单List到文档中
	 * 
	 * @param caidanList
	 */
	public static void saveXMLByList(List<Lijin> list) {

		Document document = DocumentHelper.createDocument();

		Element elements = document.addElement("lijins");

		for (Lijin object : list) {
			Element element = elements.addElement("lijin"); 

			Element id = element.addElement("id");
			id.setText(String.valueOf(object.getId()));

			Element name = element.addElement("name");
			name.setText(object.getName());

			Element money = element.addElement("money");
			money.setText(String.valueOf(object.getMoney()));

			Element guishuren = element.addElement("guishuren");
			guishuren.setText(String.valueOf(object.getGuishuren()));

			Element state = element.addElement("state");
			state.setText(String.valueOf(object.getState()));

			Element inputDate = element.addElement("inputDate");
			inputDate.setText(object.getInputDate());
		}

		Dom4jUtil.saveXML(document, Dom4jUtil.getFileName(
				FileNameState.LIJIN_PATH, FileNameState.LIJIN_FILENAME),
				"utf-8"); // 保存到XML中
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public List<String> getNameList(String name){
		List<String> strList = new ArrayList<String>();
		
		List<Lijin> lijinList = getAll();
		
		for(Lijin lijin : lijinList){
			String lijinName = lijin.getName();
			if(lijinName.indexOf(name) >= 0){
				strList.add(lijinName);
			}
		}
		
		return strList;
	}
	
}
