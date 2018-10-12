package com.hr.manage.web.util;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.checkwork.model.CheckWorkBaidu;
import hr.manage.component.checkwork.model.CheckWorkBaiduDetail;
import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.personal.model.PersonalInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.junit.Test;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTColor;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFont;

import com.hr.manage.web.constant.CodeMsg;
import com.hr.manage.web.constant.JSONResult;

/**
 * 
 * @author wcyong
 *
 * @date 2013-6-21
 */
public class excelTest {

	  public static void main(String [] args){
		  readExcelToObj("D:\\java_other\\人资\\测试表格\\百度考勤.xlsx");
//		  readExcelToObj("E:\\project_personal\\hr\\文档\\表格\\测试百度.xlsx");
    }
	  
	@Test
	public void testReadExcel() {
		readExcelToObj("f:\\test\\out3.xls");
	}

	/**
	 * 读取excel数据
	 * 
	 * @param path
	 */
	private static void readExcelToObj(String path) {

		Workbook  wb = null;
		try {
			InputStream is = new FileInputStream(path);
			try {
				wb = WorkbookFactory.create(is);
			} catch (EncryptedDocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			Sheet  xssfSheet = wb.getSheetAt(0);
//			Row  xssfRow = xssfSheet.getRow(0);
//			for (int i = 0; i < 8; i++) {
//				Cell cell = xssfRow.getCell(i);
//				CellStyle cellStyle = cell.getCellStyle();
//				
//					XSSFColor color = (XSSFColor) cellStyle.getFillForegroundColorColor();
//					byte[] a =color.getARGB();
//					byte[] c =color.getRGBWithTint();
//					String sa = bytesToHexFun(a);
//					String sc = bytesToHexFun(c);
////					short scolor = color.hashCode();  // 前两位是透明度
//					XSSFFont eFont = (XSSFFont) wb.getFontAt(cellStyle.getFontIndex());
//					CTFont fc=eFont.getCTFont();
//					 CTColor[] d=    fc.getColorArray();
//					 byte[] b=null;
//					 if(d.length>0){
//						  b= d[0].getRgb();
//					 }
//					 String sb="#";
//					 if(b!=null){
//						 sb =bytesToHexFun(b);
//					 }
//	               
//					System.out.print(" "+sa+" "+sc+ " "+sb);
//			
//				
//			}
			
			readExcel(wb, 0, 0, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	 public static String bytesToHexFun(byte[] bytes) {
	        StringBuilder buf = new StringBuilder(bytes.length * 2);
//	        buf.append("0xFF");
	        for(byte b : bytes) { // 使用String的format方法进行转换
	            buf.append(String.format("%02x", new Integer(b & 0xff)).toUpperCase());
	        }

	        return buf.toString();
	    }
	 
	/**
	 * 读取excel文件
	 * 
	 * @param wb
	 * @param sheetIndex
	 *            sheet页下标：从0开始
	 * @param startReadLine
	 *            开始读取的行:从0开始
	 * @param tailLine
	 *            去除最后读取的行
	 */
	private static void readExcel(Workbook wb, int sheetIndex, int startReadLine,
			int tailLine) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		 Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器
		 List<CheckWorkBaidu> baiduList = new ArrayList<CheckWorkBaidu>();
		 CheckWorkBaidu baidu = null;
		 List<CheckWorkBaiduDetail> baiduDetails = new ArrayList<CheckWorkBaiduDetail>();
		 CheckWorkBaiduDetail detail = null;
		 while (rows.hasNext()) {  
             Row row = rows.next();  //获得行数据  
             if(row.getRowNum()<5||isRowEmpty(row))
             	continue;
             Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
             
             detail = new CheckWorkBaiduDetail();
             if(row.getRowNum()%2==1){
            	 baidu.setBaiduDetails(baiduDetails);
            	 baiduList.add(baidu);
            	 baiduDetails.clear();
            	 baidu = new CheckWorkBaidu();
             }
                      
             while (cells.hasNext()) {  
            	 Cell cell = cells.next();  
            	 String cellValue = "";
            	 
	                
					 switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
	                    case HSSFCell.CELL_TYPE_NUMERIC:  
	                    	if (DateUtil.isCellDateFormatted(cell)) {
								Date d = cell.getDateCellValue(); // 对日期处理
								DateFormat formater = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								cellValue = formater.format(d);
							} else {// 其余按照数字处理
									// 防止科学计数法
								DecimalFormat df = new DecimalFormat("0.000");
								double acno = cell.getNumericCellValue();
								String acnoStr = df.format(acno);
								if (acnoStr.indexOf(".") > 0) {
									acnoStr = acnoStr.replaceAll("0+?$", "");// 去掉多余的0
									cellValue = acnoStr.replaceAll("[.]$", "");// 如最后一位是.则去掉
								}
							}  
	                        break;  
	                    case HSSFCell.CELL_TYPE_STRING:  
	                    	cellValue = cell.getRichStringCellValue().getString(); 
	                        break;  
	                    case HSSFCell.CELL_TYPE_BOOLEAN:  
	                    	cellValue = String.valueOf(cell.getBooleanCellValue());  
	                        break;  
	                    case HSSFCell.CELL_TYPE_BLANK:  
	                    	cellValue = "";
	                        break;  
	                    case HSSFCell.CELL_TYPE_ERROR:  
	                    	cellValue = "";
	                        break;  
	                    case HSSFCell.CELL_TYPE_FORMULA:  
	                    	cellValue = cell.getCellFormula() + "";
	                        break;  
	                    default:  
	                    	cellValue = "";
	                        break;  
	                    } 
	                    if(cell.getColumnIndex()==0&&cellValue.equals("")){
	                    	continue;
	                    }
	                    /**
						 * 处理文件中定义的属性
						 */
						String transforValue="";
						String [] colorStrings=new String [2];
						switch (cell.getColumnIndex()) {
							case 0:// 序号
								break;
							case 1:// 姓名
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								baidu.setName(transforValue);
								break;
							case 2:// 日期 白班or夜班；偶数白班；奇数夜班
								transforValue = String.valueOf(cellValue).trim();
								if(transforValue.equals("白班")){
									detail.setType(0);
								}
								else{
									detail.setType(1);
								}
								break;
							case 3:// 1号
								// FABF8F背景红色;BFBFBF 背景黑色 FFFFFF背景白色
								// FFFF0000字体红色
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								//0为背景色  1:为字体色
								colorStrings = getColors(wb,cell);
								if(colorStrings!=null){
									System.out.print( "  1 "+colorStrings[0]+ " "+colorStrings[1]+" "+transforValue);
								}
								detail.setCurrentDay(1);
								detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 4:// 2号
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								//0为背景色  1:为字体色
								colorStrings = getColors(wb,cell);
								if(colorStrings!=null){
									System.out.print(  "  2 "+colorStrings[0]+ " "+colorStrings[1]+" "+transforValue);
								}
								detail.setCurrentDay(1);
								detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 5:// 3号
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								//0为背景色  1:为字体色
								colorStrings = getColors(wb,cell);
								if(colorStrings!=null){
									System.out.print(  "  3 "+colorStrings[0]+ " "+colorStrings[1]+" "+transforValue);
								}
								detail.setCurrentDay(1);
								detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 6:// 4号
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								//0为背景色  1:为字体色
								colorStrings = getColors(wb,cell);
								if(colorStrings!=null){
									System.out.print(  "  4 "+colorStrings[0]+ " "+colorStrings[1]+" "+transforValue);
								}
								detail.setCurrentDay(1);
								detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 7:// 5号
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								//0为背景色  1:为字体色
								colorStrings = getColors(wb,cell);
								if(colorStrings!=null){
									System.out.print(  "  5 "+colorStrings[0]+ " "+colorStrings[1]+" "+transforValue);
								}
								detail.setCurrentDay(1);
								detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 8:// 6号
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								//0为背景色  1:为字体色
								colorStrings = getColors(wb,cell);
								if(colorStrings!=null){
									System.out.print(  "  6 "+colorStrings[0]+ " "+colorStrings[1]+" "+transforValue);
								}
								detail.setCurrentDay(1);
								//detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 9:// 7号
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								//0为背景色  1:为字体色
								colorStrings = getColors(wb,cell);
								if(colorStrings!=null){
									System.out.print(  "  7 "+colorStrings[0]+ " "+colorStrings[1]+" "+transforValue);
								}
								detail.setCurrentDay(1);
								detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 10:// 8号
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								//0为背景色  1:为字体色
								colorStrings = getColors(wb,cell);
								if(colorStrings!=null){
									System.out.print(  "  8 "+colorStrings[0]+ " "+colorStrings[1]+" "+transforValue);
								}
								detail.setCurrentDay(1);
								detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 11:// 9号
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								//0为背景色  1:为字体色
								colorStrings = getColors(wb,cell);
								if(colorStrings!=null){
									System.out.print(  "  9 "+colorStrings[0]+ " "+colorStrings[1]+" "+transforValue);
								}
								detail.setCurrentDay(1);
								detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 12:// 10号
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								//0为背景色  1:为字体色
								colorStrings = getColors(wb,cell);
								if(colorStrings!=null){
									System.out.print(  "  10 "+colorStrings[0]+ " "+colorStrings[1]+" "+transforValue);
								}
								detail.setCurrentDay(1);
								detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 13:// 11号
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								//0为背景色  1:为字体色
								colorStrings = getColors(wb,cell);
								if(colorStrings!=null){
									System.out.print( "  11 "+colorStrings[0]+ " "+colorStrings[1]+" "+transforValue);
								}
								detail.setCurrentDay(1);
								detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 14:// 12号
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								//0为背景色  1:为字体色
								colorStrings = getColors(wb,cell);
								if(colorStrings!=null){
									System.out.print(  "  12 "+colorStrings[0]+ " "+colorStrings[1]+" "+transforValue);
								}
								detail.setCurrentDay(1);
								detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 15:// 13号
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								//0为背景色  1:为字体色
								colorStrings = getColors(wb,cell);
								if(colorStrings!=null){
									System.out.print(  "  13 "+colorStrings[0]+ " "+colorStrings[1]+" "+transforValue);
								}
								detail.setCurrentDay(1);
								detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 16:// 14号
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								//0为背景色  1:为字体色
								colorStrings = getColors(wb,cell);
								if(colorStrings!=null){
									System.out.print(  "  14 "+colorStrings[0]+ " "+colorStrings[1]+" "+transforValue);
								}
								detail.setCurrentDay(1);
								detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
							case 17:// 15号
								transforValue = String.valueOf(cellValue).trim();
								if(StringUtils.isBlank(transforValue)){
									continue;
								}
								//0为背景色  1:为字体色
								colorStrings = getColors(wb,cell);
								if(colorStrings!=null){
									System.out.print(  "  15 "+colorStrings[0]+ " "+colorStrings[1]+" "+transforValue);
								}
								detail.setCurrentDay(1);
								detail.setWorkHours(BigDecimal.valueOf(Double.parseDouble(transforValue)));
								break;
						}
						
                   }  
              baiduDetails.add(detail); 
         	  System.out.println();      
			}

//            	 boolean isMerge = isMergedRegion(sheet, row.getRowNum(), cell.getColumnIndex());
//            	// 判断是否具有合并单元格
// 				if (isMerge) {
// 					String rs = getMergedRegionValue(sheet, row.getRowNum(),
// 							cell.getColumnIndex());
// 					System.out.print(rs + " "+fColor+ " "+bgColor+" ");
// 				} else {
// 					if(StringUtils.isBlank(getCellValue(cell))){
// 						System.out.print("|" + " "+fColor+ " "+bgColor+" ");
// 					}
// 					else{
// 						System.out.print(getCellValue(cell) + " "+fColor+ " "+bgColor+" ");
// 					}
// 					
// 				}

	}

	private static String [] getColors(Workbook wb,Cell cell) {
		   String [] colorStrings =new String[2];
		   CellStyle cellStyle = cell.getCellStyle();
   	       XSSFColor color = (XSSFColor) cellStyle.getFillForegroundColorColor();
			byte[] bColor =color.getRGBWithTint();
			String bgColor = bytesToHexFun(bColor);
			colorStrings[0]= bgColor;
			XSSFFont eFont = (XSSFFont) wb.getFontAt(cellStyle.getFontIndex());
			 CTColor[] ctColors= eFont.getCTFont().getColorArray();
			 byte[] bColors=null;
			 if(ctColors.length>0){
				 bColors= ctColors[0].getRgb();
			 }
			 String fColor="#";
			 if(bColors!=null){
				 fColor =bytesToHexFun(bColors);
			 }
			 colorStrings[1]= fColor;
			 return colorStrings;
	}

	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {

				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getCellValue(fCell);
				}
			}
		}

		return null;
	}

	/**
	 * 判断合并了行
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean isMergedRow(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row == firstRow && row == lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return
	 */
	private static boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断sheet页中是否含有合并单元格
	 * 
	 * @param sheet
	 * @return
	 */
	private boolean hasMerged(Sheet sheet) {
		return sheet.getNumMergedRegions() > 0 ? true : false;
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param firstRow
	 *            开始行
	 * @param lastRow
	 *            结束行
	 * @param firstCol
	 *            开始列
	 * @param lastCol
	 *            结束列
	 */
	private void mergeRegion(Sheet sheet, int firstRow, int lastRow,
			int firstCol, int lastCol) {
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol,
				lastCol));
	}

	public static boolean isRowEmpty(Row row) {
	    for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);
	        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK){
	            return false;
	        }
	    }
	    return true;
	}
	
	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {

		if (cell == null)
			return "";

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

			return cell.getStringCellValue();

		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

			return String.valueOf(cell.getBooleanCellValue());

		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

			return cell.getCellFormula();

		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

			return String.valueOf(cell.getNumericCellValue());

		}
		return "";
	}

}