package cn.ainannan.car.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ainannan.car.bean.Car;
import cn.ainannan.car.service.CarService;
import cn.ainannan.sys.JSescape;
import cn.ainannan.sys.utils.StringUtils;

@Controller
@RequestMapping(value = "car.do")
public class CarController {
	@Autowired
	private CarService carService = null;

	@RequestMapping(params = "method=list")
	public String list(HttpServletRequest req) {

		req.setAttribute("carList", carService.getAllCar());
		return "tool/car/list";
	}

	@RequestMapping(params = "method=preAdd")
	public String preAdd() {

		return "tool/car/add";
	}

	@RequestMapping(params = "method=add")
	public String add(Car car) {
		
		car.setId(carService.getNewId());
		String inputDate = car.getInputDate();
		car.setNiandu(StringUtils.getNianduOrMonth(inputDate, 1));
		car.setMonth(StringUtils.getNianduOrMonth(inputDate, 2));
		car.setRemark(JSescape.unescape(JSescape.unescape(car.getRemark())));
		
		carService.save(car);
		
		return "redirect:car.do?method=list";
	}
}
