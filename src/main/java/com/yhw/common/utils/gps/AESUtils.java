package com.yhw.common.utils.gps;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESUtils {

    private static final String algorithm = "AES/CBC/PKCS5Padding";
    private static final int keySize = 128;

    private AESUtils() {
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param b
     * @return
     */
    private static String byteToHex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int n = 0; n < b.length; n++) {
            String hex = Integer.toHexString(b[n] & 0XFF);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 16进制转二进制
     *
     * @param s
     * @return
     */
    private static byte[] hexToByte(String s) {
        if (s.length() < 1) {
            return null;
        }
        if ((s.length() % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < s.length() / 2; i++) {
            int high = Integer.parseInt(s.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(s.substring(i * 2 + 1, i * 2 + 2), 16);
            b[i] = (byte) (high * 16 + low);
        }
        return b;
    }


    /**
     * 对字符串进行加密
     *
     * @param data
     * @param key  不对key进行MD5
     * @return
     * @throws Exception
     */
    public static String encrypt2(String data, String key) throws Exception {
        byte[] enc = doEncrypt2(data.getBytes("UTF-8"), key);
        return byteToHex(enc);
    }


    private static byte[] doEncrypt2(byte[] data, String key)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException {
        try {
            byte[] keyArr = hexToByte(key);
            SecretKey secKey = new SecretKeySpec(keyArr, "AES");
            IvParameterSpec iv = new IvParameterSpec(new byte[16]);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secKey, iv);
            return cipher.doFinal(data);
        } catch (InvalidKeyException e) {
            throw new InvalidKeyException("不正确的密钥");
        } catch (IllegalBlockSizeException e) {
            throw new IllegalBlockSizeException("不正确的数据块长度");
        } catch (BadPaddingException e) {
            throw new BadPaddingException("加密失败");
        }
    }
}

