package com.hr.manage.web.util;

/**
 * Created by 符柱成 on 2017/8/23.
 */

import hr.manage.component.admin.model.Admin;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            T这里代表一个不确定是实体类，即参数实体
 */
public class ExportBeanExcel<T> {

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出
     *
     * title         表格标题名
     * headersName  表格属性列名数组
     * headersId    表格属性列名对应的字段---你需要导出的字段名
     *  dtoList     需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象
     *  out         与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     */
    public  void exportExcel(String title, List<String> headersName,List<String> headersId,
                            List<T> dtoList,OutputStream out) {
        //表头--标题栏
        Map<Integer, String> headersNameMap = new HashMap<>();
        int key=0;
        for (int i = 0; i < headersName.size(); i++) {
            if (!headersName.get(i).equals(null)) {
                headersNameMap.put(key, headersName.get(i));
                key++;
            }
        }
        //字段
        Map<Integer, String> titleFieldMap = new HashMap<>();
        int value = 0;
        for (int i = 0; i < headersId.size(); i++) {
            if (!headersId.get(i).equals(null)) {
                titleFieldMap.put(value, headersId.get(i));
                value++;
            }
        }
        // 声明一个工作薄
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(title);
        sheet.setDefaultRowHeight((short) (2 * 256)); //设置默认行高，表示2个字符的高度，必须先设置列宽然后设置行高，不然列宽没有效果
        sheet.setDefaultColumnWidth(17);    //设置默认列宽
        // 生成一个样式
        XSSFCellStyle style = wb.createCellStyle();
        XSSFRow row = sheet.createRow(0);
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        XSSFCell cell;
        Collection c = headersNameMap.values();//拿到表格所有标题的value的集合
        Iterator<String> it = c.iterator();//表格标题的迭代器
        //根据选择的字段生成表头
        short size = 0;
        while (it.hasNext()) {
            cell = row.createCell(size);
            cell.setCellValue(it.next().toString());
            cell.setCellStyle(style);
            size++;
        }
        //表格标题一行的字段的集合
        Collection zdC = titleFieldMap.values();
        Iterator<T> labIt = dtoList.iterator();//总记录的迭代器
        int zdRow =0;//列序号
        while (labIt.hasNext()) {//记录的迭代器，遍历总记录
            int zdCell = 0;
            zdRow++;
            row = sheet.createRow(zdRow);
            T l = (T) labIt.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = l.getClass().getDeclaredFields();//获得JavaBean全部属性
            for (short i = 0; i < fields.length; i++) {//遍历属性，比对
                Field field = fields[i];
                String fieldName = field.getName();//属性名
                Iterator<String> zdIt = zdC.iterator();//一条字段的集合的迭代器
                while (zdIt.hasNext()) {//遍历要导出的字段集合
                    if (zdIt.next().equals(fieldName)) {//比对JavaBean的属性名，一致就写入，不一致就丢弃
                        String getMethodName = "get"
                                + fieldName.substring(0, 1).toUpperCase()
                                + fieldName.substring(1);//拿到属性的get方法
                        Class tCls = l.getClass();//拿到JavaBean对象
                        try {
                            Method getMethod = tCls.getMethod(getMethodName,
                                    new Class[] {});//通过JavaBean对象拿到该属性的get方法，从而进行操控
                            Object val = getMethod.invoke(l, new Object[] {});//操控该对象属性的get方法，从而拿到属性值
                            String textVal = null;
                            if (val!= null) {
                                textVal = String.valueOf(val);//转化成String
                            }else{
                                textVal = null;
                            }
                            row.createCell((short) zdCell).setCellValue(textVal);//写进excel对象
                            zdCell++;
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        try {
//            FileOutputStream exportXls = new FileOutputStream(fileName);
            wb.write(out);
//            exportXls.close();
            System.out.println("导出成功!");
        } catch (FileNotFoundException e) {
            System.out.println("导出失败!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("导出失败!");
            e.printStackTrace();
        }
    }
    public static void main(String [] args){
//        List<String> headerId = new ArrayList<>();
//        Field[] fields = Admin.class.getDeclaredFields();
//        int i = 0;
//        while(i<fields.length) {
//            Field field = fields[i];
//            String fieldName = field.getName();//属性名
//            System.out.println(fieldName.toString());
//            headerId.add(fieldName);
//            i++;
//        }
//        System.out.println(headerId.toString());

        
		List<String> listName = new ArrayList<>();
//		listName.add("用户id");
		listName.add("用户名");
		listName.add("名字");
		listName.add("电话");
		listName.add("tba2");
		List<String> listId = new ArrayList<>();
//		listId.add("userid");
		listId.add("username");
		listId.add("realname");
		listId.add("mobilePhone");
		listId.add("tblAa2");
		List<Admin> list = new ArrayList<>();
//		list.add(new Admin(1, "admin","管理员", "11111111111",new TblA(1, "2")));
//		list.add(new Admin(2, "sufei","苏菲", "2222222222",new TblA(1, "2")));
//		list.add(new Admin(3, "common","卡门", "33333333",new TblA(1, "2")));
//		list.add(new Admin(4, "jifen","积分", "444444444444",new TblA(1, "2")));
//		list.add(new Admin(5, "point","积分2", "555555555",new TblA(1, "2")));
//		list.add(new Admin(6, "sdfdsf","乱码", "6666666",new TblA(1, "2")));
//		ExportBeanExcel<Admin> exportBeanExcelUtil = new ExportBeanExcel();
//		exportBeanExcelUtil.exportExcel("测试POI导出EXCEL文档",listName,listId,list);

//        List<String> listName = new ArrayList<>();
//        listName.add("id");
//        listName.add("名字");
//        listName.add("性别");
//        List<String> listId = new ArrayList<>();
//        listId.add("id");
//        listId.add("name");
//        listId.add("sex");
//        List<Student> list = new ArrayList<>();
//        list.add(new Student(111,"张三asdf","男"));
//        list.add(new Student(111,"李四asd","男"));
//        list.add(new Student(111,"王五","女"));
//
//
//        ExportBeanExcel<Student> exportBeanExcelUtil = new ExportBeanExcel();
//        exportBeanExcelUtil.exportExcel("测试POI导出EXCEL文档",listName,listId,list);

    }
}

