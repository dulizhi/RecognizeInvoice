package com.yhw.common.baidu.util;

import com.yhw.common.utils.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wang.dingan on 2019/11/1.
 */
public class FormatUtil {

    public static String[] 保险_起止期_提取(String insurancePeriod) {
        insurancePeriod = insurancePeriod.replace("θ", "0");
        String[] insurancePeriods = insurancePeriod.split("至");
        String[] insurancePeriodDate = new String[2];
        try {
            int index = 0;
            if (insurancePeriods != null && insurancePeriods.length > 1) {
                for (String date : insurancePeriods) {
                    Pattern p = Pattern.compile("(\\d{2})年(\\d{1,2})月(\\d{1,2})");
                    Matcher m = p.matcher(date);

                    if (m.find()) {
                        int year = Integer.valueOf(m.group(1));
                        int month = Integer.valueOf(m.group(2));
                        int day = Integer.valueOf(m.group(3));
                        String month_s = "";
                        String day_s = "";

                        if (month > 12 || month < 1) {
                            month = month % 10;
                        }
                        if (month < 10) {
                            month_s = "0" + month;
                        } else {
                            month_s = "" + month;
                        }

                        if (day > 31 || day < 1) {
                            day = day % 10;
                        }
                        if (day < 10) {
                            day_s = "0" + day;
                        } else {
                            day_s = "" + day;
                        }
                        String insuranceDate = "20" + year + month_s + day_s;
                        insurancePeriodDate[index++] = insuranceDate;
                    }
                }
            }
        } catch (Exception e) {

        }
        return insurancePeriodDate;
    }

    public static String 合格证_标志号_提取(String plate) {
        plate = plate.replace("θ", "0");
        Pattern p = Pattern.compile("[0-9]{11}");
        Matcher m = p.matcher(plate);
        String v_plate = null;

        if (m.find()) {
            v_plate = m.group(0);
        }
        return v_plate;
    }

    public static String 合格证_有效期_提取(String certPeriod) {
        certPeriod = certPeriod.replace("θ", "0");
        Pattern p = Pattern.compile("(\\d{2})年(\\d{1,2})月");
        Matcher m = p.matcher(certPeriod);
        String insuranceDate = null;

        if (m.find()) {
            int year = Integer.valueOf(m.group(1));
            int month = Integer.valueOf(m.group(2));
            String month_s = "";

            if (month > 12 || month < 1) {
                month = month % 10;
            }
            if (month < 10) {
                month_s = "0" + month;
            } else {
                month_s = "" + month;
            }
            insuranceDate = "20" + year + month_s;
        }
        return insuranceDate;
    }

    public static String 登记证_登记日期_提取(String regDate) {
        regDate = regDate.replace("θ", "0");
        Pattern p = Pattern.compile("(\\d{2})-(\\d{1,2})-(\\d{1,2})");
        Matcher m = p.matcher(regDate);
        String date = null;

        if (m.find()) {
            int year = Integer.valueOf(m.group(1));
            int month = Integer.valueOf(m.group(2));
            int day = Integer.valueOf(m.group(3));
            String month_s = "";
            String day_s = "";

            if (month > 12 || month < 1) {
                month = month % 10;
            }
            if (month < 10) {
                month_s = "0" + month;
            } else {
                month_s = "" + month;
            }

            if (day > 31 || day < 1) {
                day = day % 10;
            }
            if (day < 10) {
                day_s = "0" + day;
            } else {
                day_s = "" + day;
            }
            date = "20" + year + month_s + day_s;
        }
        return date;
    }

    public static String 登记证_车辆型号_提取(String content) {
        Pattern p = Pattern.compile("BYD[\\S]+");
        Matcher m = p.matcher(content);
        String prop = null;

        if (m.find()) {
            prop = m.group(0);
        }
        return prop;
    }

    public static String 合格证_号码号牌_提取(String plate) {
        plate = plate.replace("θ", "0");
        plate = plate.replace("面", "闽");
        plate = plate.replace("画", "闽");
        plate = plate.replace("闫", "闽");
        plate = plate.replace("间", "闽");
        plate = plate.replace("侠", "陕");
        Pattern p = Pattern.compile("[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]{1}[A-Z]{1}[A-Z0-9]{5}[A-Z0-9挂学警港澳]{1}");
        Matcher m = p.matcher(plate);
        String v_plate = null;

        if (m.find()) {
            v_plate = m.group(0);
        }
        return v_plate;
    }

    public static String 行驶证副本_编号_提取(String num) {
        num = num.replace("θ", "0");
        Pattern p = Pattern.compile("[A-Z0-9]{13}");
        Matcher m = p.matcher(num);
        String v_plate = null;

        if (m.find()) {
            v_plate = m.group(0);
        }
        if(TextUtils.isEmpty(v_plate)){
            p = Pattern.compile("[A-Z0-9]{12,13}");
            m = p.matcher(num);

            if (m.find()) {
                v_plate = m.group(0);
            }
        }
        return v_plate;
    }

    public static String 行驶证副本_档案编号_提取(String num) {
        num = num.replace("θ", "0");
        Pattern p = Pattern.compile("[0-9]{9,12}");
        Matcher m = p.matcher(num);
        String v_plate = null;

        if (m.find()) {
            v_plate = m.group(0);
        }
        return v_plate;
    }
    public static String 行驶证副本_总质量_提取(String content) {
        Pattern p = Pattern.compile("[0-9]+kg");
        Matcher m = p.matcher(content);
        String weight = null;

        if (m.find()) {
            weight=m.group(0);
        }
        return weight;
    }
    public static String 行驶证副本_整备质量_提取(String content) {
        content=content.replace("量量","质量");
        Pattern p = Pattern.compile("整备[整备质量0-9]+kg");
        Matcher m = p.matcher(content);
        String weight = null;

        if (m.find()) {
            weight=m.group(0);
        }
        if(TextUtils.isEmpty(weight)) {
            p = Pattern.compile("[0-9]+kg整备");
            m = p.matcher(content);

            if (m.find()) {
                weight = m.group(0);
            }
        }
        if(!TextUtils.isEmpty(weight)){
            p = Pattern.compile("[0-9]+kg");
            m = p.matcher(weight);

            if (m.find()) {
                weight = m.group(0);
            }
        }
        return weight;
    }

    public static String 行驶证副本_核定载人数_提取(String content) {
        Pattern p = Pattern.compile("[0-9]{1}人");
        Matcher m = p.matcher(content);
        String count = null;

        if (m.find()) {
            count=m.group(0);
        }
        return count;
    }

    public static String 行驶证副本_外廓尺寸_提取(String content) {
        Pattern p = Pattern.compile("[0-9]{2,5}[×xX][0-9]{2,5}[×xX][0-9]{2,5}");
        Matcher m = p.matcher(content);
        String count = null;

        if (m.find()) {
            count=m.group(0)+"mm";
        }
        return count;
    }

    public static String 保险标识_保险单号_提取(String 保险单号) {
        保险单号 = 保险单号.replace("θ", "0");
        Pattern p = Pattern.compile("[A-Z0-9]{18,22}");
        Matcher m = p.matcher(保险单号);
        String v_保险单号 = null;

        if (m.find()) {
            v_保险单号 = m.group(0);
        }
        return v_保险单号;
    }

    public static String 保险标识_号码号牌_提取(String 号码号牌) {
        号码号牌 = 号码号牌.replace("θ", "0");
        Pattern p = Pattern.compile("[0-9]{6}");
        Matcher m = p.matcher(号码号牌);
        String v_号码号牌 = 合格证_号码号牌_提取(号码号牌);

        if (TextUtils.isEmpty(v_号码号牌)) {
            if (m.find()) {
                v_号码号牌 = m.group(0);
            }
        }
        if (TextUtils.isEmpty(v_号码号牌)) {
            String[] plates = 号码号牌.split(":");
            if (plates != null && plates.length > 1) {
                v_号码号牌 = plates[1];
            }
        }
        return v_号码号牌;
    }

    public static String 车架号_提取(String 车架号) {
        车架号 = 车架号.replace("θ", "0");
        车架号 = 车架号.replace("O", "0");
        车架号 = 车架号.replace("o", "0");
        Pattern p = Pattern.compile("L[A-Z0-9]{10,16}");
        Matcher m = p.matcher(车架号);
        String v_车架号 = null;

        if (m.find()) {
            v_车架号 = m.group(0);
        }
        return v_车架号;
    }

    public static String 网约证_许可号_提取(String content) {
        content = content.replace("θ", "0");
        Pattern p = Pattern.compile("[0-9]{12}");
        Matcher m = p.matcher(content);
        String no = null;

        if (m.find()) {
            no = m.group(0);
        }
        if (TextUtils.isEmpty(no)) {
            p = Pattern.compile("[0-9]{7,12}");
            m = p.matcher(content);

            if (m.find()) {
                no = m.group(0);
            }
        }
        return no;
    }

    public static String 网约证_车辆类型_提取(String type) {
        type = type.replace("θ", "0");
        Pattern p = Pattern.compile("比亚迪牌[A-Z0-9]{11}");
        Matcher m = p.matcher(type);
        String v_type = null;

        if (m.find()) {
            v_type = m.group(0);
        }

        if (TextUtils.isEmpty(v_type)) {
            p = Pattern.compile("比亚迪牌[A-Z0-9]{8,11}");
            m = p.matcher(type);

            if (m.find()) {
                v_type = m.group(0);
            }
        }
        return v_type;
    }

    public static String 网约证_属性_提取(String content, String type) {
        content = content.replace(":", "");
        Pattern p = Pattern.compile(type + "[\\S]+");
        Matcher m = p.matcher(content);
        String prop = null;

        if (m.find()) {
            prop = m.group(0);
        }
        if (!TextUtils.isEmpty(prop)) {
            String[] props = prop.split(type);
            if (props != null && props.length > 1) {
                prop = props[1];
            }
        }
        return prop;
    }

    public static void main(String[] args) {
        String period = "外尺寸4680×1765×1500mm准牵总质量";

        System.out.println(FormatUtil.行驶证副本_外廓尺寸_提取(period));
    }
}
