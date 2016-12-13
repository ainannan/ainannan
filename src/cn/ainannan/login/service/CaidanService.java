package cn.ainannan.login.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import cn.ainannan.login.bean.Caidan;
import cn.ainannan.sys.FileNameState;
import cn.ainannan.sys.State;
import cn.ainannan.sys.utils.Dom4jUtil;

@Service
public class CaidanService {

	public static void main(String args[]) {

		new CaidanService().getAllCaidanByClickRate(
				new CaidanService().getAllCaidan(), State.YES);

		/*
		 * List<Caidan> caidans = new ArrayList<Caidan>(); caidans.add(new
		 * Caidan(1, "汽车费用管理", "car" + ".do?method=list", 11)); caidans.add(new
		 * Caidan(2, "健身管理", "jianshen" + ".do?method=list", 6));
		 * caidans.add(new Caidan(3, "生活费管理", "shf" + ".do?method=list", 33));
		 * caidans.add(new Caidan(4, "礼金管理", "lijin" + ".do?method=list", 22));
		 * 
		 * saveXMLByList(caidans);
		 */
	}

	/**
	 * 菜单点击次数计数器
	 * @param id
	 */
	public void countById(int id) {
		List<Caidan> caidanList = getAllCaidan();
		for (int i = 0; i < caidanList.size(); i++) {
			Caidan cd = caidanList.get(i);
			if (cd.getId() == id) {
				cd.setClickRate(cd.getClickRate() + 1);
				caidanList.set(i, cd);
				break;
			}
		}
		
		saveXMLByList(caidanList);
	}

	/**
	 * 通过ID获得 菜单对象
	 * 
	 * @param id
	 * @return
	 */
	public Caidan getCaidanById(int id) {
		Caidan caidan = null;
		for (Caidan cd : getAllCaidan()) {
			if (cd.getId() == id) {
				caidan = cd;
				break;
			}
		}

		return caidan;
	}

	/**
	 * 菜单的创建保存方法
	 * 
	 * @param caidan
	 */
	public void save(Caidan caidan) {
		List<Caidan> caidanList = getAllCaidan();
		caidanList.add(caidan);

		saveXMLByList(caidanList);
	}

	/**
	 * 获得最新的ID
	 */
	public int getNewId() {
		int oldId = 0;
		List<Caidan> list = getAllCaidan();
		for (Caidan c : list) {
			int id = c.getId();
			oldId = id > oldId ? id : oldId;
		}
		return (oldId + 1);
	}

	/**
	 * 返回排序后的菜单集合
	 * 
	 * @param flag
	 * @return
	 */
	public List<Caidan> getAllCaidanByClickRate(int flag) {
		List<Caidan> caidans = this.getAllCaidan();
		this.getAllCaidanByClickRate(caidans, flag);
		return caidans;
	}

	/**
	 * 排序List,1为降序，0为升序
	 * 
	 * @return
	 */
	public void getAllCaidanByClickRate(List<Caidan> caidans, int flag) {

		if (State.YES == flag) {
			for (int i = 0; i < caidans.size() - 1; i++) {
				for (int j = 1; j < caidans.size() - i; j++) {
					if (caidans.get(j - 1).getClickRate() < caidans.get(j)
							.getClickRate()) {
						Caidan cd = caidans.get(j - 1);
						caidans.set((j - 1), caidans.get(j));
						caidans.set((j), cd);
					}
				}
			}
		} else if (State.NO == flag) {
			for (int i = 0; i < caidans.size() - 1; i++) {
				for (int j = 1; j < caidans.size() - i; j++) {
					if (caidans.get(j - 1).getClickRate() > caidans.get(j)
							.getClickRate()) {
						Caidan cd = caidans.get(j - 1);
						caidans.set((j - 1), caidans.get(j));
						caidans.set((j), cd);
					}
				}
			}
		}

	}

	/**
	 * 获得所有的菜单信息
	 * 
	 * @return
	 */
	public List<Caidan> getAllCaidan() {
		List<Caidan> caidans = new ArrayList<Caidan>();

		SAXReader reader = new SAXReader();
		Document document = Dom4jUtil.getDocument(false,
				FileNameState.CAIDAN_PATH, FileNameState.CAIDAN_FILENAME);
		// 文档不为空时
		if (document != null) {
			Element root = document.getRootElement();
			List caidanList = root.elements("caidan");
			for (Iterator it = caidanList.iterator(); it.hasNext();) {
				Element element = (Element) it.next();
				Caidan caidan = new Caidan(
						Integer.parseInt(element.element("id").getText()),
						element.element("name").getText(),
						element.element("url").getText(),
						Integer.parseInt(element.element("clickRate").getText()));

				caidans.add(caidan);
			}
		}

		return caidans;
	}

	/**
	 * 添加菜单List到文档中
	 * 
	 * @param caidanList
	 */
	public static void saveXMLByList(List<Caidan> caidanList) {

		Document document = DocumentHelper.createDocument();

		Element elements = document.addElement("caidans");

		for (Caidan caidan : caidanList) {
			Element element = elements.addElement("caidan");

			Element id = element.addElement("id");
			id.setText(String.valueOf(caidan.getId()));

			Element name = element.addElement("name");
			name.setText(caidan.getName());

			Element url = element.addElement("url");
			url.setText(caidan.getUrl());

			Element clickRate = element.addElement("clickRate");
			clickRate.setText(String.valueOf(caidan.getClickRate()));

		}

		Dom4jUtil.saveXML(document, Dom4jUtil.getFileName(
				FileNameState.CAIDAN_PATH, FileNameState.CAIDAN_FILENAME),
				"utf-8"); // 保存到XML中
	}

}
