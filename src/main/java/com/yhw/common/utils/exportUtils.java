package com.yhw.common.utils;

import com.yhw.common.utils.poi.CustomExcelUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class exportUtils {
    /**
     * 自定义导出证照列表
     *
     * @param data
     * @param title
     * @param sheetName
     * @return 流
     * 传入data作为数据列内容，title作为表头内容
     */
    public static InputStream customExport(List<?> data, LinkedHashMap<String, String> title, String sheetName){
        if (data==null||data.isEmpty()){
            return null;
        }
        SXSSFWorkbook workbook = null;
        try {
            Iterator<?> dataIterator = data.iterator();
            workbook = new SXSSFWorkbook(1000);
            workbook.setCompressTempFiles(true);
            SXSSFSheet sheet = workbook.createSheet(sheetName);
            Map<String, CellStyle> styles = CustomExcelUtil.createInsCountStyles(workbook);
            CellStyle titleStyle = styles.get("header");
            CellStyle dataStyle = styles.get("data");
            int rowIndex = 0;
            Row titleRow = sheet.createRow(rowIndex++);
            Row dataRow = null;
            int column = 0;
            Iterator<String> titleIterator = title.keySet().iterator();
            while (titleIterator.hasNext()){
                String sheetTitle = titleIterator.next();
                CustomExcelUtil.createStringCell(titleRow,column++, title.get(sheetTitle), titleStyle);
            }
            while (dataIterator.hasNext()){
                column = 0;
                dataRow = sheet.createRow(rowIndex++);
                Object obj = dataIterator.next();
                Class<?> objClass = obj.getClass();
                Iterator<String> iterator = title.keySet().iterator();
                while (iterator.hasNext()){
                    String field = iterator.next();//获取title的key
                    Field declaredField = objClass.getDeclaredField(field);//根据key获取data对应的属性
                    declaredField.setAccessible(true);

                    String typeName = declaredField.getType().getSimpleName();//判断data值的类型
                    if (declaredField.get(obj) != null && !declaredField.get(obj).toString().isEmpty()){
                        switch (typeName){
                            case "String" :
                                String strTemp = (String)declaredField.get(obj);//根据属性名获取值
                                CustomExcelUtil.createStringCell(dataRow, column++, strTemp, dataStyle);
                                break;
                            case "Date" :
                                Date dateTemp = (Date)declaredField.get(obj);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String str = sdf.format(dateTemp);
                                if (!"saveTime".equals(declaredField.getName()) && !"invTime".equals(declaredField.getName()) && !"taxTime".equals(declaredField.getName())){
                                    str = str.substring(0,str.length() - 8);
                                }
                                CustomExcelUtil.createStringCell(dataRow, column++, str, dataStyle);
                                break;
                        }
                    }else {
                        CustomExcelUtil.createStringCell(dataRow, column++, "", dataStyle);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] barray = bos.toByteArray();
        try {
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = new ByteArrayInputStream(barray);
        return is;
    }
}
