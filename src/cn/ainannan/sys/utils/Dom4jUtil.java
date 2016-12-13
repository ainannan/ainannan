package cn.ainannan.sys.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jUtil {

	/**
	 * 保存XML文档
	 * @param document
	 * @param filePath
	 * @param encode
	 */
	public static void saveXML(Document document, String filePath, String encode) {

		OutputFormat format = OutputFormat.createPrettyPrint();

		if (null != encode) {
			format.setEncoding(encode.toUpperCase());
		}
		try {
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(filePath),
					format);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 获得document对象
	 * @param model
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static Document getDocument(boolean model,String path,String fileName) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			String fn = getFileName(path,fileName);
			// 当前路径是否可用
			if (isKeyong(fn)) {
				document = reader.read(fn);
				// 如果是空纪录的话就新建一个document，新建需要所以参数为true，列表不需要所以就为false
			} else if (model) {
				document = DocumentHelper.createDocument();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return document;
	}

	/**
	 * 获得文件名，并检查该文件是否存在 
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static String getFileName(String path,String fileName) {

		File folder = new File(path);
		if (!folder.exists()) {// 文件夹不存在则创建
			folder.mkdirs();
		}
		String fn = path + File.separator + fileName;
		File file = new File(fn);
		try {
			if (!file.exists()) // 文件不存在则创建
				file.createNewFile();
		} catch (Exception e) {
		}

		return fn;
	}

	/**
	 * 判断该路径是否可用
	 * @param filePath
	 * @return
	 */
	public static boolean isKeyong(String filePath) {
		boolean flag = false;
		File f = new File(filePath);
		try {
			FileInputStream fis = new FileInputStream(f);
			try {
				if (fis.available() > 0) {
					flag = true;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		return flag;
	}
}
