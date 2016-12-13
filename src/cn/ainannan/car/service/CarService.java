package cn.ainannan.car.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import cn.ainannan.car.bean.Car;
import cn.ainannan.sys.FileNameState;
import cn.ainannan.sys.utils.Dom4jUtil;

@Service
public class CarService {

	/**
	 * 汽车消费保存
	 * 
	 * @param car
	 */
	public void save(Car car) {
		List<Car> carList = getAllCar();
		carList.add(0, car);
		
		saveXMLByList(carList);
	}

	/**
	 * 获得所有用户集合
	 * 
	 * @return
	 */
	public List<Car> getAllCar() {
		List<Car> cars = new ArrayList<Car>();

		SAXReader reader = new SAXReader();
		Document document = Dom4jUtil.getDocument(false,
				FileNameState.CAR_PATH, FileNameState.CAR_FILENAME);

		// 文档不为空时
		if (document != null) {
			Element root = document.getRootElement();
			List carList = root.elements("car");
			for (Iterator it = carList.iterator(); it.hasNext();) {
				Element carElement = (Element) it.next();
				Car car = new Car(
						Integer.parseInt(carElement.element("id").getText()),
						Integer.parseInt(carElement.element("niandu").getText()),
						Integer.parseInt(carElement.element("month").getText()),
						Integer.parseInt(carElement.element("money").getText()),
						Integer.parseInt(carElement.element("type").getText()),
						Integer.parseInt(carElement.element("state").getText()),
						carElement.element("inputDate").getText(), carElement
								.element("remark").getText());

				cars.add(car);
			}
		}

		return cars;
	}

	/**
	 * 保存用户List集合到XML文档中
	 * 
	 * @param carList
	 */
	public void saveXMLByList(List<Car> carList) {

		Document document = DocumentHelper.createDocument();

		Element cars = document.addElement("cars");

		for (Car car : carList) {
			Element carElement = cars.addElement("car");
			// Car(id,niandu,month,money,type:[油费,过路费,违章罚款,其它],state:[已付款,未付款],inputdate)
			Element id = carElement.addElement("id");
			id.setText(String.valueOf(car.getId()));

			Element niandu = carElement.addElement("niandu");
			niandu.setText(String.valueOf(car.getNiandu()));

			Element month = carElement.addElement("month");
			month.setText(String.valueOf(car.getMonth()));

			Element money = carElement.addElement("money");
			money.setText(String.valueOf(car.getMoney()));

			Element type = carElement.addElement("type");
			type.setText(String.valueOf(car.getType()));

			Element state = carElement.addElement("state");
			state.setText(String.valueOf(car.getState()));

			Element inputDate = carElement.addElement("inputDate");
			inputDate.setText(car.getInputDate());

			Element remark = carElement.addElement("remark");
			remark.setText(car.getRemark());

		}

		Dom4jUtil.saveXML(document, Dom4jUtil.getFileName(
				FileNameState.CAR_PATH, FileNameState.CAR_FILENAME), "utf-8"); // Ĭ��utf-8�ı����ʽ����XML�ĵ�
	}

	/**
	 * 获得最新的ID
	 */
	public int getNewId() {
		int oldId = 0;
		List<Car> carList = getAllCar();
		for (Car c : carList) {
			int carId = c.getId();
			oldId = carId > oldId ? carId : oldId;
		}
		return (oldId + 1);
	}
}
