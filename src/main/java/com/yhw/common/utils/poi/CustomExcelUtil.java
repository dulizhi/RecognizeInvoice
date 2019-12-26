package com.yhw.common.utils.poi;

import com.yhw.common.utils.DateUtils;
import org.apache.poi.ss.usermodel.*;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhang.jiali4
 * @title CustomExcelUtil
 * @description 自定义excel工具
 * @date 2019/11/19
 */
public class CustomExcelUtil {
    private static DecimalFormat df=new DecimalFormat("#.00");
    /**
     * 自定义保险统计报表样式
     * @param wb 文件
     * @return
     */
    public static Map<String, CellStyle> createInsCountStyles(Workbook wb)
    {
        // 写入各条记录,每条记录对应excel表中的一行
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        styles.put("data", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        style.setFont(headerFont);
        styles.put("header", style);
        return styles;
    }

    /**
     * 自定义创建单元格
     */
    public static void createStringCell(Row row, int column, String value, CellStyle style)
    {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    public static void createIntegerCell(Row row, int column, Integer value, CellStyle style)
    {
        Cell cell = row.createCell(column);
        if(value == null){
            cell.setCellValue(0);
        }else{
            cell.setCellValue(value);
        }
        cell.setCellStyle(style);
    }
    public static void createDoubleCell(Row row, int column, Double value, CellStyle style)
    {
        Cell cell = row.createCell(column);
        if(value == null){
            cell.setCellValue(0);
        }else{
            cell.setCellValue(Float.valueOf(df.format(value)));
        }
        cell.setCellStyle(style);
    }

    public static void createDateCell(Row row, int column, Date value, CellStyle style)
    {
        Cell cell = row.createCell(column);
        if(value != null){
            cell.setCellValue(DateUtils.dateTime(value));
        }else{
            cell.setCellValue("");
        }
        cell.setCellStyle(style);
    }
}
