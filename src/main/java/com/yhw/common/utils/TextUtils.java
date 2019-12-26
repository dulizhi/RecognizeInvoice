package com.yhw.common.utils;

/**
 * @author zhang.jiali4
 * @title TextUtils
 * @description
 * @date 2019/10/18
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */
public class TextUtils {
    public static boolean isEmpty(String text) {
        return (text == null) || (text.isEmpty());
    }

    public static byte getByte(String text) {
        byte value = 0;
        if (!isEmpty(text)) {
            value = Byte.parseByte(text);
        }
        return value;
    }

    public static short getShort(String text) {
        short value = 0;
        if (!isEmpty(text)) {
            value = Short.parseShort(text);
        }
        return value;
    }

    public static int getInt(String text) {
        int value = 0;
        if (!isEmpty(text)) {
            value = Integer.parseInt(text);
        }
        return value;
    }

    public static float getFloat(String text) {
        float value = 0;
        if (!isEmpty(text)) {
            value = Float.parseFloat(text);
        }
        return value;
    }

    public static long getLong(String text) {
        long value = 0;
        if (!isEmpty(text)) {
            value = Long.parseLong(text);
        }
        return value;
    }

    public static double getDouble(String text) {
        double value = 0;
        if (!isEmpty(text)) {
            value = Double.parseDouble(text);
        }
        return value;
    }

    public static String formatOracleMobileNum(String mobileNum,int total_len,String ch) {
        String formatMobileNum=mobileNum;
        if(!TextUtils.isEmpty(mobileNum)){
            for (int i=0;i<total_len-mobileNum.length();i++){
                formatMobileNum+=(ch);
            }
        }
        return formatMobileNum;
    }
}

