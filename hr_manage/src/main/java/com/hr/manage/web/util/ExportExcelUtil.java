package com.hr.manage.web.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
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

public class ExportExcelUtil<T> {
	
	private static String author;
	
	private static String notes;
	
	public void exportExcel(Collection<T> dataset, OutputStream out) {
		exportExcel(null, null, dataset, out, "yyyy-MM-dd HH:mm:ss");
	}

	public void exportExcel(String[] headers, Collection<T> dataset,
			OutputStream out) {
		exportExcel(null, headers, dataset, out, "yyyy-MM-dd HH:mm:ss");
	}

	public void exportExcel(String[] headers, Collection<T> dataset,
			OutputStream out, String pattern) {
		exportExcel(null, headers, dataset, out, pattern);
	}

	@SuppressWarnings({"rawtypes"})
	public void exportExcel(String title, String[] headers,
			Collection<T> dataset, OutputStream out, String pattern) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));
		if(StringUtils.isNotEmpty(author)){
			// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
			comment.setAuthor(author);
		}
		if(StringUtils.isNotEmpty(notes)){
			// 设置注释内容
			comment.setString(new HSSFRichTextString(notes));
		}

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {})==null?"":getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					HSSFRichTextString textStringValue = null;
					if (value instanceof Integer) {
						int intValue = (Integer) value;
						cell.setCellValue(intValue);
					} else if (value instanceof Float) {
						float fValue = (Float) value;
						textStringValue = new HSSFRichTextString(String.valueOf(fValue));
						cell.setCellValue(textStringValue);
					} else if (value instanceof Double) {
						double dValue = (Double) value;
						textStringValue = new HSSFRichTextString(String.valueOf(dValue));
						cell.setCellValue(textStringValue);
					} else if (value instanceof Long) {
						long longValue = (Long) value;
						cell.setCellValue(longValue);
					}
					if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
						cell.setCellValue(textValue);
					} /*
					 * else if (value instanceof byte[]) { // 有图片时，设置行高为60px;
					 * row.setHeightInPoints(60); // 设置图片所在列宽度为80px,注意这里单位的一个换算
					 * sheet.setColumnWidth(i, (short) (35.7 * 80)); //
					 * sheet.autoSizeColumn(i); byte[] bsValue = (byte[]) value;
					 * HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
					 * 1023, 255, (short) 6, index, (short) 6, index);
					 * anchor.setAnchorType(2); patriarch.createPicture(anchor,
					 * workbook.addPicture( bsValue,
					 * HSSFWorkbook.PICTURE_TYPE_JPEG)); }
					 */else if (value instanceof Double) {
						double dValue = (Double) value;
						HSSFRichTextString textRich = new HSSFRichTextString(
								String.valueOf(dValue));
						cell.setCellValue(textRich);
					} else {
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
						cell.setCellValue(textValue);
					}
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}

		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public static void setAuthor(String author) {
		ExportExcelUtil.author = author;
	}

	public void setNotes(String notes) {
		ExportExcelUtil.notes = notes;
	}

	public ExportExcelUtil(String author, String notes) {
		super();
		ExportExcelUtil.author = author;
		ExportExcelUtil.notes = notes;
	}
	
	
}
