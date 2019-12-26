package com.yhw.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhang.jiali4
 * @title ByteUtil
 * @description
 * @date 2019/10/18
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */
public class ByteUtil {

    /**
     * short convert byte
     *
     * @param b
     * @param s
     * @param index
     */
    public static void shortTobyteArray(byte b[], short s, int index) {
        b[index + 0] = (byte) (s >> 8);
        b[index + 1] = (byte) (s >> 0);
    }

    /**
     * byte[] conver short
     *
     * @param b
     * @param index
     * @return
     */
    public static short getShort(byte[] b, int index) {
        return (short) (((b[index + 0] << 8) | b[index + 1] & 0xff));
    }

    public static void int3ToByteArray(byte[] bb, int x, int index) {
        bb[index + 0] = (byte) (x >> 16);
        bb[index + 1] = (byte) (x >> 8);
        bb[index + 2] = (byte) (x >> 0);
    }

    public static int getInt3(byte[] bb, int index) {
        return (int) ((((bb[index + 0] & 0xff) << 16)
                | ((bb[index + 1] & 0xff) << 8)
                | ((bb[index + 2] & 0xff) << 0)));
    }

    /**
     * int convert byte
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void intToByteArray(byte[] bb, int x, int index) {
        bb[index + 0] = (byte) (x >> 24);
        bb[index + 1] = (byte) (x >> 16);
        bb[index + 2] = (byte) (x >> 8);
        bb[index + 3] = (byte) (x >> 0);
    }

    /**
     * byte[] conver int
     *
     * @param bb
     * @param index
     * @return
     */
    public static int getInt(byte[] bb, int index) {
        return (int) ((((bb[index + 0] & 0xff) << 24)
                | ((bb[index + 1] & 0xff) << 16)
                | ((bb[index + 2] & 0xff) << 8) | ((bb[index + 3] & 0xff) << 0)));
    }

    /**
     * long convert byte
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void longToByteArray(byte[] bb, long x, int index) {
        bb[index + 0] = (byte) (x >> 56);
        bb[index + 1] = (byte) (x >> 48);
        bb[index + 2] = (byte) (x >> 40);
        bb[index + 3] = (byte) (x >> 32);
        bb[index + 4] = (byte) (x >> 24);
        bb[index + 5] = (byte) (x >> 16);
        bb[index + 6] = (byte) (x >> 8);
        bb[index + 7] = (byte) (x >> 0);
    }

    /**
     * byte[] to long
     *
     * @param bb
     * @param index
     * @return
     */
    public static long getLong(byte[] bb, int index) {
        return ((((long) bb[index + 0] & 0xff) << 56)
                | (((long) bb[index + 1] & 0xff) << 48)
                | (((long) bb[index + 2] & 0xff) << 40)
                | (((long) bb[index + 3] & 0xff) << 32)
                | (((long) bb[index + 4] & 0xff) << 24)
                | (((long) bb[index + 5] & 0xff) << 16)
                | (((long) bb[index + 6] & 0xff) << 8) | (((long) bb[index + 7] & 0xff) << 0));
    }

    /**
     * char convert Byte[]
     *
     * @param bb
     * @param ch
     * @param index
     */
    public static void charToByteArray(byte[] bb, char ch, int index) {
        int temp = (int) ch;
        // byte[] b = new byte[2];
        bb[index] = new Integer(temp & 0xff).byteValue();

    }

    /**
     * byte[] to char
     *
     * @param b
     * @param index
     * @return
     */
    public static char getChar(byte[] b, int index) {
        char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        return c;
    }

    /**
     * float convert byte[]
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void floatToByteArray(byte[] bb, float x, int index) {
        // byte[] b = new byte[4];
        int l = Float.floatToIntBits(x);
        for (int i = 0; i < 4; i++) {
            bb[index + i] = new Integer((l >> (24 - 8 * i))).byteValue();
        }
    }

    /**
     * byte[] to float
     *
     * @param b
     * @param index
     * @return
     */
    public static float getFloat(byte[] b, int index) {
        int l;
        l = b[index + 3];
        l &= 0xff;
        l |= ((long) b[index + 2] << 8);
        l &= 0xffff;
        l |= ((long) b[index + 1] << 16);
        l &= 0xffffff;
        l |= ((long) b[index + 0] << 24);
        return Float.intBitsToFloat(l);
    }

    /**
     * double convert to
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void doubleToByteArray(byte[] bb, double x, int index) {
        // byte[] b = new byte[8];
        long l = Double.doubleToLongBits(x);
        for (int i = 0; i < 8; i++) {
            bb[index + i] = new Long((l >> (56 - 8 * i))).byteValue();
        }
    }

    /**
     * byte[] to double
     *
     * @param b
     * @param index
     * @return
     */
    public static double getDouble(byte[] b, int index) {
        long l;
        l = b[7];
        l &= 0xff;
        l |= ((long) b[6] << 8);
        l &= 0xffff;
        l |= ((long) b[5] << 16);
        l &= 0xffffff;
        l |= ((long) b[4] << 24);
        l &= 0xffffffffl;
        l |= ((long) b[3] << 32);
        l &= 0xffffffffffl;
        l |= ((long) b[2] << 40);
        l &= 0xffffffffffffl;
        l |= ((long) b[1] << 48);
        l &= 0xffffffffffffffl;
        l |= ((long) b[0] << 56);
        return Double.longBitsToDouble(l);
    }

    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++) bs[i - begin] = src[i];
        return bs;
    }

    public static long getUnsignedInt(byte[] src, int index) {
        int result = getInt(src, index);
        return getUnsignedLong(result);
    }

    public static int getUnsignedByte(byte data) {
        return data & 0xFF;
    }

    public static int getUnsignedByte(short data) {
        return data & 0x0FFFF;
    }

    public static long getUnsignedLong(int data) {
        return data & 0x0FFFFFFFFl;
    }

    public static int OxStringtoInt(String ox) {
        ox = ox.toLowerCase();
        if (ox.startsWith("0x")) {
            ox = ox.substring(2, ox.length());
        }
        int ri = 0;
        int oxlen = ox.length();
        if (oxlen > 8) {
            return 0XFF;
        }

        for (int i = 0; i < oxlen; i++) {
            char c = ox.charAt(i);
            int h = 0;
            if (('0' <= c && c <= '9')) {
                h = c - 48;
            } else if (('a' <= c && c <= 'f')) {
                h = c - 87;

            } else if ('A' <= c && c <= 'F') {
                h = c - 55;
            } else {
                return 0XFF;
            }
            byte left = (byte) ((oxlen - i - 1) * 4);
            ri |= (h << left);
        }
        return ri;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    static private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static boolean isByteEmpty(byte[] data) {
        boolean result = false;
        int len = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0) {
                len++;
            }
        }
        if (len == data.length) {
            result = true;
        }
        return result;
    }

    public static String convertByteToString(String title, byte[] data) {
        String output = "";
        if (!StringUtils.isEmpty(title)) {
            output = title + ":";
        }
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(data[i] & 0xFF);
            if (hex.length() == 1)
                hex = '0' + hex;
            output += "0x" + hex.toUpperCase() + " ";
        }
        return output;
    }

    public static byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int ch;
        while ((ch = is.read(buffer)) != -1) {
            bytestream.write(buffer, 0, ch);
        }
        byte data[] = bytestream.toByteArray();
        bytestream.close();
        return data;
    }
}
