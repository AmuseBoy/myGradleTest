package com.liu.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

public class ExcelUtil {

	
	/**
	 * 使用jxls导入excel
	 * @param xmlFile
	 * @param inputStream
	 * @param map
	 * @throws IOException
	 * @throws SAXException
	 * @throws InvalidFormatException
	 */
	public static void importFile(String xmlFile,InputStream inputStream,Map<String, Object> map) throws IOException, SAXException, InvalidFormatException{
		Resource resouce = new ClassPathResource("template/"+xmlFile);
		if(resouce.isReadable()){
			InputStream isXml = resouce.getInputStream();
			XLSReader reader = ReaderBuilder.buildFromXML(isXml);
			XLSReadStatus status = reader.read(inputStream, map);
			System.out.println("status:"+status);
			isXml.close();
			inputStream.close();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
