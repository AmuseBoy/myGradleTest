package com.liu.test.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.jxls.area.XlsArea;
import org.jxls.command.GridCommand;
import org.jxls.common.Context;
import org.jxls.template.SimpleExporter;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;

import com.liu.person.user.User;
import com.liu.person.user.UserService;
import com.liu.util.ExcelUtil;
import com.liu.util.FileUtil;
import com.liu.util.PoiUtil;
import com.liu.util.ResultUtil;

import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@RestController
public class FileController {

	@Autowired
	private UserService userService;
	
	
	
//	@RequestMapping(value="/api/file/upload", method=RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> upload(@RequestBody MultipartFile file,@RequestParam("name") String name,HttpServletRequest req) throws IOException{
//		if(!file.isEmpty()){
//			byte[] bytes = file.getBytes();
//			System.out.println(file.getOriginalFilename());
//			System.out.println("req:"+req.getRequestURL());
//			System.out.println("name:"+name);
//			return ResultUtil.success();
//		}else{
//			return ResultUtil.fail();
//		}
//	}
	
	@RequestMapping(value="/api/file/upload", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upload(HttpServletRequest req,HttpServletResponse resp, @RequestParam("name") String name) throws IOException{
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)req;
		//得到上传的文件
		MultipartFile file = multipartRequest.getFile("file");
		//得到上传服务器的路径
		System.out.println("其他参数："+name);
		System.out.println("活得路径："+req.getSession().getServletContext().getContextPath());
		if(file != null){
			String filename = file.getOriginalFilename();
			String path = "F:/"+filename;
			File uploadFile = new File(path);
			file.transferTo(uploadFile);
		}
		return ResultUtil.success();
	}
	
	/**
	 * excel上传示例(使用jxls上传)
	 * @param file
	 * @param param
	 * @return
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws InvalidFormatException 
	 */
	@RequestMapping(value="/api/excel/upload/{param}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadExcel(@RequestBody MultipartFile file,
			@PathVariable String param,@RequestParam("name") String name) throws IOException, InvalidFormatException, SAXException{
		System.out.println("参数："+param+",第二个参数："+name);
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> list = new ArrayList<User>();
		map.put("head", new HashMap<String, Object>());
		map.put("userList", list);
		InputStream ins = new ByteArrayInputStream(file.getBytes());
		ExcelUtil.importFile("user.xml",ins,map);
		System.out.println("取得大小:"+list.size()+";head:"+map.get("head"));
		for(User u :list){
			System.out.println("电子邮箱："+u.getEmail());
			//其他处理逻辑
		}
		return ResultUtil.success();
	}
	
	/**
	 * excel下载示例
	 * 文件下载示例，直接下载模板
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value="/api/excel/download/{param}",method=RequestMethod.GET)
	@ResponseBody
	public void downloadExcel(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		String fileName = "user.xlsx";
		String fileChName = "用户.xlsx";
		Resource resource = new ClassPathResource("template/"+fileName);
		if(resource.isReadable()){
			InputStream is = resource.getInputStream();
			FileUtil.downLoadFile(req, resp,is,fileChName);
		}
	}
	
	
	/**
	 * jxls 导出excel数据示例
	 * 直接下载没有弹出框，代码拼接模板
	 * @throws IOException 
	 */
	@RequestMapping(value="/api/excel/download1", method=RequestMethod.GET)
	@ResponseBody
	public void downLoadExcel1() throws IOException{
		String fileChName = "userTemp.xlsx";
		OutputStream os = new FileOutputStream("F:/"+fileChName);
		List<User> userList = userService.findAll();
		List<String> headers = Arrays.asList("id","name","sex","birth","email");
		SimpleExporter exporter = new SimpleExporter();
		exporter.gridExport(headers, userList, "id,name,sex,birth,email", os);
	}
	
	/**
	 * jxls 导出excel数据示例
	 * 有弹出框，代码拼接模板
	 * @throws IOException 
	 */
	@RequestMapping(value="/api/excel/download2", method=RequestMethod.GET)
	@ResponseBody
	public void downLoadExcel2(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		String fileChName = "userTemp.xlsx";
		FileUtil.downLoadFile2(req, resp,fileChName);//设置Excel属性
		OutputStream os = resp.getOutputStream();
		List<User> userList = userService.findAll();
		List<String> headers = Arrays.asList("编号","姓名","性别","生日","邮箱");
		SimpleExporter exporter = new SimpleExporter();
		exporter.gridExport(headers, userList, "id,name,sex,birth,email", os);
	}
	
	
	
	/**
	 * jxls根据模板放入数据
	 * 没有弹出框
	 * @throws IOException
	 */
	@RequestMapping(value="/api/excel/download3", method=RequestMethod.GET)
	@ResponseBody
	public void downLoadExcel3() throws IOException{
		String fileChName = "userTemp.xlsx";
		Resource resource = new ClassPathResource("template/userTemplate.xlsx");
		if(resource.isReadable()){
			InputStream is = resource.getInputStream();//输入流
			OutputStream os = new FileOutputStream("F:/"+fileChName);//Excel输出流
			List<User> userList = userService.findAll();
			Context context = new Context();
			context.putVar("users", userList);
			JxlsHelper.getInstance().processTemplate(is, os, context);
		}
	}
	
	
	/**
	 * jxls根据模板放入数据
	 * 有弹出框
	 * @throws IOException
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws BiffException 
	 */
	@RequestMapping(value="/api/excel/download4", method=RequestMethod.GET)
	@ResponseBody
	public void downLoadExcel4(HttpServletRequest req,HttpServletResponse resp) throws IOException, RowsExceededException, WriteException, BiffException{
		String fileChName = "userTemp.xlsx";
		Resource resource = new ClassPathResource("template/userTemplate.xlsx");
		
		if(resource.isReadable()){
			InputStream is = resource.getInputStream();//输入流
			OutputStream os = resp.getOutputStream();//Excel输出流
			FileUtil.downLoadFile2(req, resp,fileChName);//设置Excel属性
			List<User> userList = userService.findAll();
			Context context = new Context();
			context.putVar("users", userList);
			JxlsHelper.getInstance().processTemplate(is, os, context);
		}
	}
	
	
	/**
	 * 待验证的方法
	 * @throws BiffException
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	@RequestMapping(value="/api/excel/updateExcel", method=RequestMethod.GET)
	@ResponseBody
	public void updateExcel() throws BiffException, IOException, RowsExceededException, WriteException{
//		byte[] buffer = new byte[512];
//		int numberRead = 0;
		InputStream ins = null;
		OutputStream ous = null;
		try {
			ins = new FileInputStream(new File("F:\\userTemp.xlsx"));
			ous = new FileOutputStream(new File("F:\\userTemplate.xlsx"));
			
//			while((numberRead=ins.read(buffer))!=-1){
//				ous.write(buffer,0,numberRead);//numberRead的目的在于防止最后一次读取的字节小于buffer长度， 
//			}
			
//			Workbook wb = Workbook.getWorkbook(ins);
//			WritableWorkbook wrb = Workbook.createWorkbook(new File("F:\\userTemp.xlsx"), wb);
//			WritableSheet sheet = wrb.getSheet(0);
//			Label label = new Label(3,2,"性别哈哈");
//			WritableCellFeatures wcf = new WritableCellFeatures();
//			List<String> selectList = new ArrayList<String>();
//			selectList.add("男");
//			selectList.add("女");
//			wcf.setDataValidationList(selectList);
//			label.setCellFeatures(wcf);
//			sheet.addCell(label);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			ins.close();
			ous.close();
		}
	}
	
	
	
	/**
	 * 使用Poi导出Excel
	 * @throws IOException
	 */
	@RequestMapping(value="/api/excel/downLoadExcelByPoi", method=RequestMethod.GET)
	@ResponseBody
	public void downLoadExcelByPoi() throws IOException{
		FileOutputStream output = new FileOutputStream("F:\\poiBook.xlsx");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("mySheet");
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("这是汉字");
		wb.write(output);
		wb.close();
		output.close();
	}
	
	
	/**
	 * 使用Poi导出Excel
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value="/api/excel/downLoadExcelByPoi2", method=RequestMethod.GET)
	@ResponseBody
	public void downLoadExcelByPoi2(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("用户表");
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell = row1.createCell(0);
		cell.setCellValue("用户一览表");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));//合并单元格，起始行，截至行，起始列，截至列；
		HSSFRow row2 = sheet.createRow(1);
		row2.createCell(0).setCellValue("编号");
		row2.createCell(1).setCellValue("姓名");
		row2.createCell(2).setCellValue("性别");
		row2.createCell(3).setCellValue("生日");
		row2.createCell(4).setCellValue("邮箱");
		
		//绑定下拉框
		CellRangeAddressList regions = new CellRangeAddressList(1,1,3,3);  
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(new String[]{"男","女"});//生成下拉框内容  
		HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);//绑定下拉框和作用区域  
		sheet.addValidationData(data_validation);  //对sheet页生效  
		
		OutputStream output = resp.getOutputStream();
		FileUtil.downLoadFile2(req,resp,"userTemp.xlsx");
		wb.write(output);
		output.close();
		wb.close();
	}
	
	/**
	 * 使用Poi导出Excel
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws SecurityException
	 * @throws ReflectiveOperationException
	 */
	@RequestMapping(value="/api/excel/downLoadExcelByPoi3", method=RequestMethod.GET)
	@ResponseBody
	public void downLoadExcelByPoi3(HttpServletRequest req,HttpServletResponse resp) throws IOException, SecurityException, ReflectiveOperationException{
		String[] headList = new String[]{"编号","姓名","性别","生日","邮箱"};
		List<User> userList = userService.findAll();
		OutputStream output = resp.getOutputStream();
		FileUtil.downLoadFile2(req,resp,"userTemp.xlsx");
		PoiUtil.exportExcel("用户", headList, userList, output);
	}
	
	
	
	
//	public static void main(String[] args) {
////		char a = 'C';
////		System.out.println((a));
//		
//		String s = null ;
//		System.out.println(s.toString());
//	}
	
	
}
