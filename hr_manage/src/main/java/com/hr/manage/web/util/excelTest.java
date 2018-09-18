package com.hr.manage.web.util;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.checkwork.model.CheckWorkDetail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
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

/**
 * 
 * @author wcyong
 *
 * @date 2013-6-21
 */
public class excelTest {

	  public static void main(String [] args){
		  readExcelToObj("E:\\project_personal\\hr\\文档\\表格\\测试百度.xlsx");
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
		while (rows.hasNext()) {  
             Row row = rows.next();  //获得行数据  
             if(row.getRowNum()<5||isRowEmpty(row))
             	continue;
             Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
//             CheckWorkDetail detail= new CheckWorkDetail();
             while (cells.hasNext()) {  
            	 Cell cell = cells.next();  
            	 CellStyle cellStyle = cell.getCellStyle();
            	 XSSFColor color = (XSSFColor) cellStyle.getFillForegroundColorColor();
					byte[] bColor =color.getRGBWithTint();
					String bgColor = bytesToHexFun(bColor);
					XSSFFont eFont = (XSSFFont) wb.getFontAt(cellStyle.getFontIndex());
//					CTFont fc=eFont.getCTFont();
					 CTColor[] d= eFont.getCTFont().getColorArray();
					 byte[] b=null;
					 if(d.length>0){
						  b= d[0].getRgb();
					 }
					 String fColor="#";
					 if(b!=null){
						 fColor =bytesToHexFun(b);
					 }
	               
//					System.out.print(" "+bgColor+" "+fColor+ " ");
			
//            	 Font eFont = wb.getFontAt(cellStyle.getFontIndex());

            	 boolean isMerge = isMergedRegion(sheet, row.getRowNum(), cell.getColumnIndex());
            	// 判断是否具有合并单元格
 				if (isMerge) {
 					String rs = getMergedRegionValue(sheet, row.getRowNum(),
 							cell.getColumnIndex());
 					System.out.print(rs + " "+fColor+ " "+bgColor+" ");
 				} else {
 					if(StringUtils.isBlank(getCellValue(cell))){
 						System.out.print("|" + " "+fColor+ " "+bgColor+" ");
 					}
 					else{
 						System.out.print(getCellValue(cell) + " "+fColor+ " "+bgColor+" ");
 					}
 					
 				}
             }
 			System.out.println();
 		}
//                 String cellValue = "";
//                 switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
//                 case HSSFCell.CELL_TYPE_NUMERIC:  
//                 	if (DateUtil.isCellDateFormatted(cell)) {
//							Date d = cell.getDateCellValue(); // 对日期处理
//							DateFormat formater = new SimpleDateFormat(
//									"yyyy-MM-dd HH:mm:ss");
//							cellValue = formater.format(d);
//						} else {// 其余按照数字处理
//								// 防止科学计数法
//							DecimalFormat df = new DecimalFormat("0.000");
//							double acno = cell.getNumericCellValue();
//							String acnoStr = df.format(acno);
//							if (acnoStr.indexOf(".") > 0) {
//								acnoStr = acnoStr.replaceAll("0+?$", "");// 去掉多余的0
//								cellValue = acnoStr.replaceAll("[.]$", "");// 如最后一位是.则去掉
//							}
//						}  
//                     break;  
//                 case HSSFCell.CELL_TYPE_STRING:  
//                 	cellValue = cell.getRichStringCellValue().getString(); 
//                     break;  
//                 case HSSFCell.CELL_TYPE_BOOLEAN:  
//                 	cellValue = String.valueOf(cell.getBooleanCellValue());  
//                     break;  
//                 case HSSFCell.CELL_TYPE_BLANK:  
//                 	cellValue = "";
//                     break;  
//                 case HSSFCell.CELL_TYPE_ERROR:  
//                 	cellValue = "";
//                     break;  
//                 case HSSFCell.CELL_TYPE_FORMULA:  
//                 	cellValue = cell.getCellFormula() + "";
//                     break;  
//                 default:  
//                 	cellValue = "";
//                     break;  
//                 } 
//                 if(cell.getColumnIndex()==0&&cellValue.equals("")){
//                 	break;
//                 }
//                 /**
//					 * 处理文件中定义的属性
//					 */
//					String transforValue="";
//					switch (cell.getColumnIndex()) {
//					
//					}
//             }
//		}
//		Row row = null;
//		Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
//		for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
//			row = sheet.getRow(i);
//			if(row.getRowNum()<5||row==null) continue;
//			
//			for (Cell c : row) {
//				boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
//				// 判断是否具有合并单元格
//				if (isMerge) {
//					String rs = getMergedRegionValue(sheet, row.getRowNum(),
//							c.getColumnIndex());
//					System.out.print(rs + " ");
//				} else {
//					if(StringUtils.isBlank(getCellValue(c))){
//						System.out.print("#" + " ");
//					}
//					else{
//						System.out.print(getCellValue(c) + " ");
//					}
//					
//				}
//			}
//			System.out.println();
//		}
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

	
    public static class ColorInfo{  
        /** 
         * 颜色的alpha值，此值控制了颜色的透明度 
         */  
        public int A;  
        /** 
         * 颜色的红分量值，Red 
         */  
        public int R;  
        /** 
         * 颜色的绿分量值，Green 
         */  
        public int G;  
        /** 
         * 颜色的蓝分量值，Blue 
         */  
        public int B;  
      
        public int toRGB() {  
            return this.R << 16 | this.G << 8 | this.B;  
        }  
      
        public java.awt.Color toAWTColor(){  
            return new java.awt.Color(this.R,this.G,this.B,this.A);  
        }  
      
        public static ColorInfo fromARGB(int red, int green, int blue) {  
            return new ColorInfo((int) 0xff, (int) red, (int) green, (int) blue);  
        }  
        public static ColorInfo fromARGB(int alpha, int red, int green, int blue) {  
            return new ColorInfo(alpha, red, green, blue);  
        }  
        public ColorInfo(int a,int r, int g , int b ) {  
            this.A = a;  
            this.B = b;  
            this.R = r;  
            this.G = g;  
        }  
    }  
}