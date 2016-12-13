package cn.ainannan.jianshen.tizhong.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import cn.ainannan.car.bean.Car;
import cn.ainannan.jianshen.tizhong.bean.Tizhong;
import cn.ainannan.sys.FileNameState;
import cn.ainannan.sys.utils.Dom4jUtil;
import cn.ainannan.sys.utils.NumberUtil;

@Service
public class TizhongService {
	
	/**
	 * 删除方法
	 * @param id
	 */
	public void delete(int id){
		List<Tizhong> list = getAll();
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
	public void save(Tizhong object){
		List<Tizhong> list = getAll();
		list.add(0, object);
		
		saveXMLByList(list);
	}
	
	/**
	 * 保存体重到数据库中，如果同一天有多条数据，则合并为一条，体重值取平均值
	 * @param object
	 */
	public void saveByMerge(Tizhong object){
		boolean flag = true;
		List<Tizhong> list = getAll();
		
		for (int i = 0; i < list.size(); i++) {
			Tizhong tz = list.get(i);
			if(tz.getInputDate().equals(object.getInputDate())){
				double average = (tz.getWeight() + object.getWeight()) / 2d;
				tz.setWeight(NumberUtil.getDoubleByTwo(average));
				
				list.set(i, tz);
				flag = false;
				break;
			}
		}
		if(flag){
			list.add(0, object);
		}
		
		saveXMLByList(list);
	}
	
	/**
	 * 获得所有的体重信息
	 * @return
	 */
	public List<Tizhong> getAll() {
		List<Tizhong> tizhongs = new ArrayList<Tizhong>();

		Document document = Dom4jUtil.getDocument(false,
				FileNameState.TIZHONG_PATH, FileNameState.TIZHONG_FILENAME);
		// 文档不为空时
		if (document != null) {
			Element root = document.getRootElement();
			List caidanList = root.elements("tizhong");
			for (Iterator it = caidanList.iterator(); it.hasNext();) {
				Element element = (Element) it.next();
				Tizhong object = new Tizhong(
						Integer.parseInt(element.element("id").getText()),
						Double.parseDouble(element.element("weight").getText()),
						element.element("inputDate").getText()
						);

				tizhongs.add(object);
			}
		}

		return tizhongs;
	}

	/**
	 * 添加菜单List到文档中
	 * 
	 * @param caidanList
	 */
	public static void saveXMLByList(List<Tizhong> list) {

		Document document = DocumentHelper.createDocument();

		Element elements = document.addElement("tizhongs");

		for (Tizhong object : list) {
			Element element = elements.addElement("tizhong");

			Element id = element.addElement("id");
			id.setText(String.valueOf(object.getId()));
			
			Element weight = element.addElement("weight");
			weight.setText(String.valueOf(object.getWeight()));

			Element inputDate = element.addElement("inputDate");
			inputDate.setText(object.getInputDate());
		}

		Dom4jUtil.saveXML(document, Dom4jUtil.getFileName(
				FileNameState.TIZHONG_PATH, FileNameState.TIZHONG_FILENAME),
				"utf-8"); // 保存到XML中
	}
	
	/**
	 * 获得最新的ID
	 */
	public int getNewId() {
		int oldId = 0;
		List<Tizhong> list = getAll();
		for (Tizhong object : list) {
			int id = object.getId();
			oldId = id > oldId ? id : oldId;
		}
		return (oldId + 1);
	}
}
