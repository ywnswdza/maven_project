/*package com.losy.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;


*//**
 * 操作excel
 * @author xiaoyin
 * @date 2012-8-29下午11:18:37
 *//*
@SuppressWarnings("all")
public final class ExcelOperate {

	
	*//**
	 * <h1>打印普通的，规则的excel文件 </h1> <br/>
	 * 默认列宽：4500，行高 25，如需更改请调构造方法
	 * @param titles	标题数组
	 * @param list		内容集，里面的String[]一定要和titles的length相等
	 * @param fileName	存放文件名称
	 * @param tagName	tag名称，为“”时默认为fileName
	 * @param fileUrl	存放文件夹url
	 * @return 文件路径
	 *//*
	public static String CreateExcel(String[] titles, List<String[]> list,
			String fileName, String tagName, String fileUrl) {
		return CreateExcel(titles, list, fileName, tagName, fileUrl, 4500, 25);
	}
	
	
	*//**
	 * 打印普通的，规则的excel文件
	 * @param titles	标题数组
	 * @param list		内容集，里面的String[]一定要和titles的length相等
	 * @param fileName		存放文件名称
	 * @param tagName	tag名称，为“”时默认为fileName
	 * @param fileUrl		存放文件夹url
	 * @param row_width 	列宽
	 * @param line_height	行高
	 * @return 文件路径
	 *//*
	public static String CreateExcel(String[] titles, List<String[]> list,
			String fileName, String tagName, String fileUrl, int row_width, int line_height) {
		if(null == tagName || "".equals(tagName)) tagName = fileName;
		final int ROWNUM = titles.length; //列数
		final int HEADLINE = 0;	//内容填写的开始行数(>=0)
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(tagName);
		
		//设置列宽
		for (int i=0,len=ROWNUM; i<len; i++) {
			sheet.setColumnWidth(i,row_width);
		}
		
		//创建表头
		HSSFRow row = sheet.createRow(HEADLINE);
		HSSFCell cell[] = new HSSFCell[ROWNUM];
		HSSFCellStyle style_a = getCellStyle_A(wb);
		for(int i=0; i<ROWNUM; i++){
			cell[i] = row.createCell(i);
			cell[i].setCellType(HSSFCell.ENCODING_UTF_16); //设置为字符串类型
			cell[i].setCellStyle(style_a); //设置样式
			cell[i].setCellValue(titles[i]); //创建标题
		}
		
		
		//填写内容
		HSSFCellStyle style_b = getCellStyle_B(wb);
		for(int i=HEADLINE,len=(list.size()+HEADLINE); i<len; i++){
			HSSFRow datarow = sheet.createRow(i+1);
			datarow.setHeightInPoints(line_height); //行高
			HSSFCell datacell[] = new HSSFCell[ROWNUM];
			for(int j=0; j<ROWNUM; j++){
				datacell[j] = datarow.createCell(j);
//				datacell[i].setEncoding(HSSFCell.ENCODING_UTF_16); //防止乱码
			}
			
			String[] strs = list.get(i-HEADLINE);
			for (int j = 0; j < strs.length; j++) {
				datacell[j].setCellStyle(style_b);
				try {
					datacell[j].setCellValue(Double.parseDouble(strs[j]));
				} catch (NumberFormatException e) {
					datacell[j].setCellValue(strs[j]);
				}
			}
		}
		
		// 创建文件保存
		FileOperate.createDir(fileUrl);
		String url = fileUrl + "/"+ fileName +".xls";
//		System.out.println("生成文件："+url);
		File file = new File(url);
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		
		return url;
	}
	
	
	*//**
	 * 导出渠道系统excel，打印首行带描述的 excel文件
	 * @param titles	标题数组
	 * @param list		内容集，里面的String[]一定要和titles的length相等
	 * @param fileName	存放文件名称
	 * @param tagName	tag名称，为“”时默认为fileName
	 * @param fileUrl	存放文件夹url
	 * @param descr		文档描述，不传入就没有	
	 * @return 文件路径
	 *//*
	public static String CreateChannelExcel(String[] titles, List<String[]> list,
			String fileName, String tagName, String fileUrl, String... descr) {
		if(null == tagName || "".equals(tagName)) tagName = fileName;
		final int ROWNUM = titles.length; //列数
		final int row_width = 4500;	//列宽
		final int line_height = 25; //行高
		int HEADLINE = 0;	//内容填写的开始行数(>=0)
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(tagName);
		
		HSSFCellStyle style_c = getCellStyle_C(wb);
		if(descr.length > 0){
			mergeCell(sheet, style_c, 0, 0, 0, (ROWNUM-1), descr[0]);
			HEADLINE = 1; //内容从第1行开始
		}
		
		
		//设置列宽
		for (int i=0,len=ROWNUM; i<len; i++) {
			sheet.setColumnWidth(i,row_width);
		}

		//创建表头
		HSSFRow row = sheet.createRow(HEADLINE);
		row.setHeightInPoints(line_height);
		HSSFCell cell[] = new HSSFCell[ROWNUM];
		HSSFCellStyle style_a = getCellStyle_A(wb);
		for(int i=0; i<ROWNUM; i++){
			cell[i] = row.createCell(i);
			cell[i].setCellType(HSSFCell.ENCODING_UTF_16); //设置为字符串类型
			cell[i].setCellStyle(style_a); //设置样式
			cell[i].setCellValue(titles[i]); //创建标题
		}
		
		
		//填写内容
		HSSFCellStyle style_b = getCellStyle_B(wb);
		for(int i=HEADLINE,len=(list.size()+HEADLINE); i<len; i++){
			HSSFRow datarow = sheet.createRow(i+1);
			datarow.setHeightInPoints(line_height); //行高
			HSSFCell datacell[] = new HSSFCell[ROWNUM];
			for(int j=0; j<ROWNUM; j++){
				datacell[j] = datarow.createCell(j);
//				datacell[i].setEncoding(HSSFCell.ENCODING_UTF_16); //防止乱码
			}
			
			String[] strs = list.get(i-HEADLINE);
			for (int j = 0; j < strs.length; j++) {
				datacell[j].setCellStyle(style_b);
				try {
					datacell[j].setCellValue(Integer.parseInt(strs[j]));
				} catch (NumberFormatException e) {
					datacell[j].setCellValue(strs[j]);
				}
			}
		}
		
		// 创建文件保存
		FileOperate.createDir(fileUrl);
		String url = fileUrl + "/"+ fileName +".xls";
//		System.out.println("生成文件："+url);
		File file = new File(url);
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		
		return url;
	}
	
	
	*//**
	 * <h1>合并单元格，添加数据到合并的单元格</h1> <br/>
	 * 合并从第rowFrom行columnFrom列，到rowTo行columnTo的区域 <br/>
	 * 注意：从0开始
	 * @param sheet	HSSFSheet
	 * @param style	样式对象HSSFCellStyle
	 * @param values	合并后显示的内容
	 *//*
	@SuppressWarnings("all")
	private static void mergeCell(HSSFSheet sheet, HSSFCellStyle style,
			int rowFrom, int columnFrom, int rowTo, int columnTo, String values) {
		rowFrom = (rowFrom < 0) ? 0 : rowFrom;
		columnFrom = (columnFrom < 0) ? 0 : columnFrom;
		rowTo = (rowTo < 0) ? 0 : rowTo;
		columnTo = (columnTo < 0) ? 0 : columnTo;
		Region region = new Region(rowFrom, (short)columnFrom, rowTo, (short)columnTo);
		sheet.addMergedRegion(region);
		
		HSSFRow row = sheet.createRow(rowFrom);
		row.setHeightInPoints(50);
		HSSFCell cell[] = new HSSFCell[1];
		cell[0] = row.createCell(0);
		cell[0].setCellType(HSSFCell.ENCODING_UTF_16); //设置为字符串类型
		cell[0].setCellStyle(style);  //设置样式
		cell[0].setCellValue( ("说明："+values) );
		
	}
	
	
	*//** 样式1(加粗) **//*
	private static HSSFCellStyle getCellStyle_A(HSSFWorkbook wb){
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 			// 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	// 指定单元格垂直居中对齐
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//背景色
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);		 	// 设置单无格的边框为粗体
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setWrapText(true);									// 指定单元格自动换行
		HSSFFont font = wb.createFont();
		font.setBoldweight((short)300);
		font.setFontName("宋体");
		font.setFontHeight((short)300);
		cellStyle.setFont(font);
		
		return cellStyle;
	}
	
	*//** 样式2(普通样式) **//*
	private static HSSFCellStyle getCellStyle_B(HSSFWorkbook wb){
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 			// 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	// 指定单元格垂直居中对齐
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);		 	// 设置单无格的下边框为粗体
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setWrapText(true);									// 指定单元格自动换行
//		cellStyle.setBorderTop((short)100);
		HSSFFont font = wb.createFont();
		font.setBoldweight((short)200);
		font.setFontName("宋体");
		font.setFontHeight((short)200);
		cellStyle.setFont(font);
		
		return cellStyle;
	}
	
	
	*//** 样式3(描述的样式) **//*
	private static HSSFCellStyle getCellStyle_C(HSSFWorkbook wb){
		HSSFCellStyle cellStyle = wb.createCellStyle();
//		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 			// 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	// 指定单元格垂直居中对齐
//		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//背景色
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);		 	// 设置单无格的边框为粗体
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setWrapText(true);									// 指定单元格自动换行
		HSSFFont font = wb.createFont();
		font.setBoldweight((short)200);
		font.setFontName("宋体");
		font.setFontHeight((short)200);
		font.setColor(HSSFColor.RED.index);
		cellStyle.setFont(font);
		
		return cellStyle;
	}
	
	
}
*/