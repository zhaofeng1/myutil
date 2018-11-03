package com.zf.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {


	private static Pattern ANDROID_PKG_PATTERN = Pattern.compile("[a-zA-Z0-9_]*\\.[a-zA-Z0-9_\\.]*");
	private static final Pattern ITUNES_APP_ID_PATTERN = Pattern.compile("([0-9]{1,})");

	private static Pattern PLAY_GOOGLE_URL_PATTERN = Pattern.compile("(play\\.google\\.com.*)");
	private static Pattern ITUNES_URL_PATTERN = Pattern.compile(".*itunes\\.apple\\.com.*");
	private static Pattern IOS_STORE_URL_ID_PATTERN = Pattern.compile("/id[0-9]+");

	public static void main(String[] args) {
		String path = "E:/logs/tmp/handle/mobile-d-cpi only.xlsx";
		int sheetnum = 1;
		List<String[]> list = readExcel(path, sheetnum);
		if (sheetnum == 0) {
			getFirstSheet(list);
		} else if (sheetnum == 1) {
			getSecondSheet(list);
		} else if (sheetnum == 2) {
			getFirstSheet(list);
		}
		//		System.out.println(JSON.toJSONString(list));
	}

	public static void getSecondSheet(List<String[]> list) {
		for (String[] row : list) {
			if (row.length >= 3) {
				String name = row[0];
				String actions_ranges = row[1];
				String site_url = row[2];
				
				String platform = "";
				String geo = "";
				String appid = "";
				String payout = "";
				
				if (name.contains("[")) {
					String tempPlatformGeo = "";
					if (name.contains("]")) {
						tempPlatformGeo = name.substring(name.indexOf("[") + 1, name.indexOf("]"));
						geo = name.substring(name.indexOf("]") + 1);
					} else {
						tempPlatformGeo = name.substring(name.indexOf("[") + 1);
					}
					String[] temArray = tempPlatformGeo.split(",");
					if (temArray.length >= 2) {
						platform = temArray[1].trim();
						if ("iPhone".equalsIgnoreCase(platform) || "IOS".equalsIgnoreCase(platform)) {
							platform = "iOS";
						}
						if (!platform.contains("Android") && !platform.contains("iOS") && !platform.contains("IOS") && !platform.contains("iPhone")) {
							platform = temArray[0];
							if (StringUtils.isBlank(geo.trim())) {
								geo = temArray[temArray.length - 1];
							}
						}
					}
				}
				if (StringUtils.isBlank(platform)) {
					if (isGpWebsiteLink(site_url)) {
						platform = "Android";
					} else if (isItunesWebsiteLink(site_url)) {
						platform = "iOS";
					}
				}

				appid = getAppid(site_url);
				if (StringUtils.isBlank(appid)) {
					appid = "";
				}
				
				payout = actions_ranges;

				payout = replaceStr(payout, "Install on");
				payout = replaceStr(payout, "Registration on");
				payout = payout.trim();
				platform = platform.trim();
				geo = geo.trim();
				appid = appid.trim();
				name = name.trim();

				//				System.out.println(appid + "\t" + platform + "\t" + geo + "\t" + payout);
				System.out.println("insert into tmp_appid_geos_payout(appid,platform,geos,payout,campaignname) values('" + appid + "','" + platform + "','" + geo + "','" + payout + "','" + name + "');");
			}
		}
				
	}

	public static void getFirstSheet(List<String[]> list) {

		for (String[] row : list) {
			if (row.length > 5) {
				String campaignname = row[0];
				String platform = "";
				String geo = "";
				String appid = "";
				String payout = "";
				if (campaignname.contains("[")) {
					String tempPlatformGeo = "";
					if (campaignname.contains("]")) {
						tempPlatformGeo = campaignname.substring(campaignname.indexOf("[") + 1, campaignname.indexOf("]"));
					} else {
						tempPlatformGeo = campaignname.substring(campaignname.indexOf("[") + 1);
					}
					//					if (campaignname.contains("Rozet")) {
					//						System.out.println("1111");
					//					}

					String[] temArray = tempPlatformGeo.split(",");
					if (temArray.length == 2) {
						platform = temArray[0];
						if (platform.equals("CPI") || platform.equals("CPA")) {
							platform = temArray[1];
							geo = campaignname.substring(campaignname.indexOf("]") + 1);
						} else {
							geo = temArray[1];
						}
						geo = geo.trim();
						geo = replaceStr(geo, "+");
						geo = replaceStr(geo, "24");
						geo = replaceStr(geo, "\n");
						if (geo.equals("CPI") || geo.equals("CPA")) {
							geo = campaignname.substring(campaignname.indexOf("]") + 1);
						}

					} else if (temArray.length == 1) {
						platform = temArray[0];
						geo = campaignname.substring(campaignname.indexOf("]") + 1);
					} else if (temArray.length > 2) {
						platform = temArray[0];
						if ("android".equalsIgnoreCase(platform)) {
							geo = campaignname.substring(campaignname.indexOf(platform) + 8);
						} else if ("ios".equalsIgnoreCase(platform)) {
							geo = campaignname.substring(campaignname.indexOf(platform) + 4);
						}
					}
					geo = replaceStr(geo, "\n");
					geo = replaceStr(geo, "]");
					geo = geo.trim();

				}
				
				
				String loadingpage = row[1];

				if(StringUtils.isBlank(platform)){
					if (isGpWebsiteLink(loadingpage)) {
						platform = "Android";
					} else if (isItunesWebsiteLink(loadingpage)) {
						platform = "iOS";
					}
				}

				appid = getAppid(loadingpage);
				payout = row[4];
				payout= payout.replace("\n", " ");

				payout = payout.trim();
				platform = platform.trim();
				geo = geo.trim();
				appid = appid.trim();
				campaignname = campaignname.trim();
				//				System.out.println(appid + "\t" + platform + "\t" + geo + "\t" + payout);

				System.out.println("insert into tmp_appid_geos_payout(appid,platform,geos,payout,campaignname) values('" + appid + "','" + platform + "','" + geo + "','" + payout + "','" + campaignname + "');");
			}
		}
	}
	
	public static String replaceStr(String str,String repStr){
		if (str.contains(repStr)) {
			str = str.replace(repStr, "");
		}
		return str;
	}

	
	public static String getAppid(String priviewurl) {
		String appid = "";
		if (isGpWebsiteLink(priviewurl)) {
			int index = -1;
			if ((index = priviewurl.indexOf("id=")) != -1) {
				appid = priviewurl.substring(index + "id=".length()).split("&")[0];
			}
		} else if (isItunesWebsiteLink(priviewurl)) {
			Matcher m = IOS_STORE_URL_ID_PATTERN.matcher(priviewurl);
			if (m.find()) {
				appid = m.group().replace("/id", "");
			}
		}
		return appid;
	}

	public static boolean isGpWebsiteLink(String url) {
		if (StringUtils.isNotBlank(url)) {
			Matcher m = PLAY_GOOGLE_URL_PATTERN.matcher(url);
			if (m.find()) {
				return true;
			}
		}
		return false;
	}

	public static boolean isItunesWebsiteLink(String url) {
		if (StringUtils.isNotBlank(url)) {
			Matcher m = ITUNES_URL_PATTERN.matcher(url);
			if (m.find()) {
				return true;
			}
		}
		return false;
	}


	public static List<String[]> readExcel(String path, int sheetnum) {
		List<String[]> result = new ArrayList<String[]>();
		try {
			File file = new File(path);
			int rowSize = 0;
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			
			Workbook wb = null;
			String ext = path.substring(path.lastIndexOf("."));
			if(".xls".equals(ext)){
				wb = new HSSFWorkbook(in);
			}else if(".xlsx".equals(ext)){
				wb = new XSSFWorkbook(in);
			}else{
				wb=null;
			}

			// 打开HSSFWorkbook
//			POIFSFileSystem fs = new POIFSFileSystem(in);
//			HSSFWorkbook wb = new HSSFWorkbook(fs);
			Cell cell = null;


			Sheet st = wb.getSheetAt(sheetnum);
			// 第一行为标题，不取
			for (int rowIndex = 1; rowIndex <= st.getLastRowNum(); rowIndex++) {
				Row row = st.getRow(rowIndex);
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] temArray = new String[rowSize];

				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					cell = row.getCell(columnIndex);
					String value = getValueByCell(cell);
					temArray[columnIndex] = value;
				}
				result.add(temArray);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}
	
	public static String getValueByCell(Cell cell) {
		String value = "";
		if (cell != null) {
			// 注意：一定要设成这个，否则可能会出现乱码,后面版本默认设置
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:

				value = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					if (date != null) {
						value = new SimpleDateFormat("yyyy-MM-dd").format(date);
					} else {
						value = "";
					}
				} else {
					value = new DecimalFormat("#.#").format(cell.getNumericCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				// 导入时如果为公式生成的数据则无值
				if (!cell.getStringCellValue().equals("")) {
					value = cell.getStringCellValue();
				} else {
					value = cell.getNumericCellValue() + "";
				}
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				value = "";
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				value = (cell.getBooleanCellValue() == true ? "Y"

				: "N");
				break;
			default:
				value = "";
			}
		}
		return value;
	}
}
