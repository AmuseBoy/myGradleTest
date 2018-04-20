package com.liu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public class FileUtil {

	public static Log logger = LogFactory.getLog(FileUtil.class);
	
	/**
	 * 获取contentType
	 * @param fileName
	 * @return
	 */
	public static String getContentType(String fileName){
        if (StringUtils.isEmpty(fileName) || !fileName.contains(".")) {
            return "";
        }
		String extName = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isEmpty(extName)) {
            return "";
        }
        logger.info("extName:"+extName);
        String contentType = "";
        if (extName.equals("png")) {
            contentType = "image/png";
        } else if (extName.equals("jpeg") || extName.equals("jpg")) {
            contentType = "text/jpeg";
        } else if (extName.equals("gif")) {
            contentType = "text/gif";
        } else if (extName.equals("bmp")) {
            contentType = "text/bmp";
        } else if (extName.equals("zip")) {
            contentType = "application/zip";
        } else if (extName.equals("rar")) {
            contentType = "application/x-rar-compressed";
        } else if (extName.equals("exe")) {
            contentType = "application/x-msdownload";
        } else if (extName.equals("pdf")) {
            contentType = "application/pdf";
        } else if (extName.equals("doc")) {
            contentType = "application/msword";
        } else if (extName.equals("docx")) {
            contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else if (extName.equals("xls")) {
            contentType = "application/vnd.ms-excel";
        } else if (extName.equals("xlsx")) {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else if (extName.equals("ppt")) {
            contentType = "application/vnd.ms-powerpoint";
        } else if (extName.equals("pptx")) {
            contentType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        } else if (extName.equals("apk")) {
            contentType = "application/vnd.android.package-archive";
        } else {
            contentType = "application/x-msdownload";
        }
        return contentType;
	}
	
	
	
	/**
	 * 文件下载
	 * @param req
	 * @param resp
	 * @param is
	 * @param fileChName
	 * @throws IOException
	 */
	public static void downLoadFile(HttpServletRequest req,HttpServletResponse resp,
			InputStream is,String fileChName) throws IOException{
		resp.setContentType(getContentType(fileChName));
		resp.addHeader("Content-Disposition", "attachment;filename=\""+URLEncoder.encode(fileChName, "UTF-8")+"\"");
		ServletOutputStream os = resp.getOutputStream();
		
		int readLength;
		byte[] buf = new byte[4096];
		while((readLength = is.read(buf))!= -1){
			os.write(buf, 0, readLength);
		}
		is.close();
		os.close();
	}
	
	
	public static void downLoadFile2(HttpServletRequest req,HttpServletResponse resp,
			String fileChName) throws IOException{
		resp.setContentType(getContentType(fileChName));
		resp.addHeader("Content-Disposition", "attachment;filename=\""+URLEncoder.encode(fileChName, "UTF-8")+"\"");
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 测试
	 */
//	public static void main(String[] args) throws IOException {
//		//inputStreamTest();
//		//fileOutputStreamTest();
//		System.out.println(URLEncoder.encode("刘培振".getBytes("UTF-8").toString(), "UTF-8"));
//		System.out.println(new String("刘培振".getBytes("UTF-8")));
//		
//	}
	
	
	
	
	
	/**
	 * 字节输入流
	 */
	public static void inputStreamTest(){
		InputStream ins = null;
		int count = 0;
		try {
			ins = new FileInputStream(new File("E:\\test1\\后台报错.txt"));
			//new File("E:/test1/后台报错.txt");也可以
			while(ins.read()!=-1){
				count++;
			}
			System.out.println("一共"+count+"字节");
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				ins.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 字节输入输出流
	 */
	public static void fileOutputStreamTest(){
		byte[] buffer = new byte[512];
		int numberRead = 0;
		FileInputStream ins = null;
		FileOutputStream ous = null;
		try {
			ins = new FileInputStream(new File("E:\\test1\\后台报错.txt"));
			ous = new FileOutputStream(new File("E:\\test1\\后台报错copy.txt"));
			
			while((numberRead=ins.read(buffer))!=-1){
				ous.write(buffer,0,numberRead);//numberRead的目的在于防止最后一次读取的字节小于buffer长度， 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				ins.close();
				ous.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 文件
	 * @throws IOException
	 */
	public static void createFile() throws IOException{
		File file = new File("E:\\test1\\test.txt");
		if(!file.exists()){
			//file.mkdir();
			file.createNewFile();
		}else{
			//file.delete();
		}
		System.out.println(file.isDirectory()+":"+file.isFile());
		System.out.println(file);
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getName());
		System.out.println(file.getParent());
		System.out.println(file.getParentFile().getParent());
	}
	
}
