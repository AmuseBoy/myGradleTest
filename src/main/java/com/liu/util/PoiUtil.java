package com.liu.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class PoiUtil {
	
	/**
	 * 使用POI导出excel公共方法
	 * @param title
	 * @param headers
	 * @param dataset
	 * @param out
	 * @throws IOException
	 * @throws ReflectiveOperationException
	 * @throws SecurityException
	 */
    public static <T> void exportExcel(String title, String[] headers,List<T> dataset, OutputStream out) throws IOException, ReflectiveOperationException, SecurityException{
		HSSFWorkbook workbook = new HSSFWorkbook();// 声明一个工作薄 
		HSSFSheet sheet = workbook.createSheet(title);// 生成一个表格 
		sheet.setDefaultColumnWidth(15);// 设置表格默认列宽度为15个字节 
		HSSFCellStyle style = workbook.createCellStyle();// 生成一个样式 
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);// 设置这些样式       
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();// 生成一个字体 
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);// 把字体应用到当前的样式         
        HSSFCellStyle style2 = workbook.createCellStyle();// 生成并设置另一个样式 
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font2 = workbook.createFont();// 生成另一个字体 
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style2.setFont(font2);// 把字体应用到当前的样式         
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();// 声明一个画图的顶级管理器 
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));// 设置注释内容 
        comment.setAuthor("leno");// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容. 
        HSSFRow row = sheet.createRow(0);// 产生表格标题行 
        for (int i = 0; i < headers.length; i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        
        Iterator<T> it = dataset.iterator();// 遍历集合数据，产生数据行 
        int index = 0;
        while (it.hasNext()){
            index++;
            row = sheet.createRow(index);
            Object o = it.next();
            
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值 
            Field[] fields = o.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++){
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                Field field = fields[i];
                String fieldName = field.getName();
                
                String type = field.getGenericType().toString();
                if (type.equals("class java.lang.String")) {  
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
                    Method getMethod = o.getClass().getMethod(getMethodName);
                    Object value = getMethod.invoke(o);
                    if(value != null){
                        HSSFRichTextString richString = new HSSFRichTextString(value.toString());
                        HSSFFont font3 = workbook.createFont();
                        font3.setColor(HSSFColor.BLUE.index);
                        richString.applyFont(font3);
                        cell.setCellValue(richString);
                    }
                }

            }
        }
        workbook.write(out);
        workbook.close();
        out.close();
    }
	
	
	
}

