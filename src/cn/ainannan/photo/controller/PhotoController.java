package cn.ainannan.photo.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;

import cn.ainannan.photo.service.PhotoService;
import cn.ainannan.sys.FileNameState;

@Controller
@RequestMapping(value="photo.do")
public class PhotoController {
	@Autowired
	PhotoService photoService = null;
	
	@RequestMapping(params="method=list")
	public String list(HttpServletRequest req,HttpServletResponse resp){
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();  
		String filePath = servletContext.getRealPath("/") + FileNameState.PHOTO_PATH;
		
		req.setAttribute("photoPath", FileNameState.PHOTO_PATH);
		req.setAttribute("photoList", photoService.getFileNameList(filePath));
		
		return "photo/list";
	}
}
