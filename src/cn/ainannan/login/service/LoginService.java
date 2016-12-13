package cn.ainannan.login.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import cn.ainannan.sys.FileNameState;
import cn.ainannan.sys.State;
import cn.ainannan.sys.bean.User;
import cn.ainannan.sys.utils.Dom4jUtil;

@Service
public class LoginService {
	
	
	/**
	 * 验证用户
	 * @param user
	 * @return
	 */
	public boolean checkUser(User user){
		boolean flag = false;
		
		List<User> userList = getAllUser();
		
		for(User u : userList){
			if(u.getUserName().equals(user.getUserName()) 
					&& u.getPassword().equals(user.getPassword())
					&& u.getState() == State.YES){
				flag = true;
			}
		}
		
		return flag;
	}
	
	/**
	 * 获得所有用户集合
	 * @return
	 */
	public List<User> getAllUser() {
		List<User> Users = new ArrayList<User>();

		SAXReader reader = new SAXReader();
		Document document = Dom4jUtil.getDocument(false,FileNameState.USER_PATH,FileNameState.USER_FILENAME);

		// 文档不为空时
		if (document != null) {
			Element root = document.getRootElement();
			List UserList = root.elements("user");
			for (Iterator it = UserList.iterator(); it.hasNext();) {
				Element userElement = (Element) it.next();
				User user = new User(
						Integer.parseInt(userElement.element("id").getText()),
						userElement.element("userName").getText(),
						userElement.element("password").getText(),
						Integer.parseInt(userElement.element("state").getText())
						); 
				
				Users.add(user); 
			}
		}
		
		return Users;
	}
	
	/**
	 * 保存用户List集合到XML文档中
	 * @param userList
	 */
	public static void saveXMLByList(List<User> userList) {
		
		Document document = DocumentHelper.createDocument();

		Element labels = document.addElement("users");

		for (User user : userList) {
			Element labelElement = labels.addElement("user");
			
			Element id = labelElement.addElement("id");
			id.setText(String.valueOf(user.getId()));
			
			Element userName = labelElement.addElement("userName");
			userName.setText(user.getUserName());
			
			Element password = labelElement.addElement("password");
			password.setText(user.getPassword());
			
			Element state = labelElement.addElement("state");
			state.setText(String.valueOf(user.getState()));
			
		}

		Dom4jUtil.saveXML(document, Dom4jUtil.getFileName(FileNameState.USER_PATH,FileNameState.USER_FILENAME), "utf-8"); // Ĭ��utf-8�ı����ʽ����XML�ĵ�
	}

	/**
	 * 获得最新的ID
	 */
	/*public static int getNewId() {
		int oldId = 0;
		List<DoutuLabel> DoutuLabelList = getAllDoutuLabel();
		for (DoutuLabel DoutuLabel : DoutuLabelList) {
			int DoutuLabelId = DoutuLabel.getId();
			oldId = DoutuLabelId > oldId ? DoutuLabelId : oldId; // ȡ�����ID
		}
		return (oldId + 1);
	}*/
	
}
