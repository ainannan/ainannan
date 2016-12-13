package cn.ainannan.photo.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.ainannan.photo.bean.Photo;
import cn.ainannan.sys.State;

@Service
public class PhotoService {
	
	/**
	 * ������е���Ƭ����
	 * @param dir
	 * @return
	 */
	public List<Photo> getFileNameList(String dir) {
		List<Photo> fileList = new ArrayList<Photo>();
		File[] fs = new File(dir).listFiles(); // ��������·����List
				
		for (int i = 0; i < fs.length; i++) {
			fileList.add(new Photo(i, fs[i].getName(), dir,"暂无",new SimpleDateFormat(State.DATE_FORMAT).format(new Date())));
		}
		return fileList;
	}
	
	/**
	 * ������е���Ƭ���ϣ�����ѡ�񳤶�
	 * @param dir
	 * @param size
	 * @return
	 */
	public List<Photo> getFileNameList(String dir, int size) {
		List<Photo> fileList = new ArrayList<Photo>();
		File[] fs = new File(dir).listFiles(); // ��������·����List
		size = fs.length < size ? fs.length : size; // size���ܴ����б?�ȵ��ж�

		for (int i = 0; i < size; i++) {
			fileList.add(new Photo(i, fs[i].getName(), dir,"暂无",new SimpleDateFormat(State.DATE_FORMAT).format(new Date())));
		}
		return fileList;
	}
}
