package com.zf.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zf.model.ProdInfo;

/**
 * 写excel文件
 * 
 * @author ZhaoFeng
 * @date 2016年12月28日
 */
public class ExcelUtil {

	private static final Logger log = Logger.getLogger(ExcelUtil.class);

	public static void writeExcel(List<ProdInfo> list, String path) {
		OutputStream out = null;
		try {
			Workbook workBook = new XSSFWorkbook();
			// sheet 对应一个工作页
			Sheet sheet = workBook.createSheet();
			/**
			 * 往Excel中写新数据
			 */
			Row row = null;
			Cell cell = null;
			List<String> titleList = new ArrayList<String>();
			titleList.add("标题");
			titleList.add("图片");
			titleList.add("细节");
			titleList.add("RP");
			titleList.add("SEBELUM");
			titleList.add("DISKON");
			titleList.add("描述标题");
			titleList.add("描述html");
			// 写表头
			row = sheet.createRow(0);
			for (int i = 0; i < titleList.size(); i++) {
				cell = row.createCell(i);
				cell.setCellValue(titleList.get(i));
			}
			List<String> tempList = null;
			for (int j = 0; j < list.size(); j++) {
				// 创建一行：从第二行开始，跳过属性列
				row = sheet.createRow(j + 1);
				tempList = convertData(list.get(j));
				for (int i = 0; i < tempList.size(); i++) {
					cell = row.createCell(i);
					cell.setCellValue(tempList.get(i));
				}
			}
			// 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
			out = new FileOutputStream(path);
			workBook.write(out);
		} catch (Exception e) {
			log.error(e);
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				log.error(e);
			}
		}
		System.out.println("数据导出成功");
	}

	private static List<String> convertData(ProdInfo inf) {
		List<String> list = new ArrayList<String>();
		list.add(inf.getProdTitle());
		list.add(getListString(inf.getImgUrls()));
		list.add(getListString(inf.getDetails()));
		list.add(inf.getRp());
		list.add(inf.getSebelum());
		list.add(inf.getDiskon());
		list.add(inf.getDescTitle());
		list.add(inf.getDescHtml());

		return list;
	}

	public static String getListString(List<String> list) {
		String str = "";
		for (String s : list) {
			str += s + ",";
		}
		if (str.length() > 0) {
			str = str.substring(0, str.length() - 1);
			System.out.println("a");
		}
		return str;
	}
}
