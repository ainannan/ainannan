package cn.ainannan.jianshen.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import cn.ainannan.jianshen.bean.Yangwoqizuo;
import cn.ainannan.sys.FileNameState;
import cn.ainannan.sys.utils.Dom4jUtil;

@Service
public class YangwoqizuoService {
	
	/**
	 * 删除方法
	 * @param id
	 */
	public void delete(int id){
		List<Yangwoqizuo> list = getAll();
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
	public void save(Yangwoqizuo object){
		List<Yangwoqizuo> list = getAll();
		list.add(0, object);
		
		saveXMLByList(list);
	}
	
	/**
	 * 获得最新的ID
	 */
	public int getNewId() {
		int oldId = 0;
		List<Yangwoqizuo> list = getAll();
		for (Yangwoqizuo object : list) {
			int id = object.getId();
			oldId = id > oldId ? id : oldId;
		}
		return (oldId + 1);
	}
	
	/**
	 * 获得所有的体重信息
	 * @return
	 */
	public List<Yangwoqizuo> getAll() {
		List<Yangwoqizuo> list = new ArrayList<Yangwoqizuo>();

		Document document = Dom4jUtil.getDocument(false,
				FileNameState.YANGWOQIZUO_PATH, FileNameState.YANGWOQIZUO_FILENAME);
		// 文档不为空时
		if (document != null) {
			Element root = document.getRootElement();
			List list2 = root.elements("yangwoqizuo");
			for (Iterator it = list2.iterator(); it.hasNext();) {
				Element element = (Element) it.next();
				Yangwoqizuo object = new Yangwoqizuo(
						Integer.parseInt(element.element("id").getText()),
						Integer.parseInt(element.element("count").getText()),
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
	public static void saveXMLByList(List<Yangwoqizuo> list) {

		Document document = DocumentHelper.createDocument();

		Element elements = document.addElement("yangwoqizuos");

		for (Yangwoqizuo object : list) {
			Element element = elements.addElement("yangwoqizuo"); 

			Element id = element.addElement("id");
			id.setText(String.valueOf(object.getId()));
			
			Element count = element.addElement("count");
			count.setText(String.valueOf(object.getCount()));

			Element inputDate = element.addElement("inputDate");
			inputDate.setText(object.getInputDate());
		}

		Dom4jUtil.saveXML(document, Dom4jUtil.getFileName(
				FileNameState.YANGWOQIZUO_PATH, FileNameState.YANGWOQIZUO_FILENAME),
				"utf-8"); // 保存到XML中
	} 
}
