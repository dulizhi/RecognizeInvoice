package com.yhw.common.utils.gps;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.util.*;

public class SignUtil {
    public static String sign(Map<String, String> map) {
        List<String> values = new ArrayList<String>();
        for (String value : map.values()) {
            values.add(value);
        }
        return sign(values);
    }

    public static String sign(List<String> values) {
        if (values == null) {
            throw new NullPointerException("values is null");
        }
        values.remove(Collections.singleton(null));
        Collections.sort(values);
        StringBuilder sb = new StringBuilder();
        for (String s : values) {
            sb.append(s);
        }
        return Hashing.sha1().hashString(sb.toString(), Charsets.UTF_8).toString().toUpperCase();
    }

    public static String sign(String version, String appid, String secret) {
        List<String> values = Arrays.asList(version,appid,secret);
        if (values == null) {
            throw new NullPointerException("values is null");
        }
        values.remove(Collections.singleton(null));
        Collections.sort(values);
        StringBuilder sb = new StringBuilder();
        for (String s : values) {
            sb.append(s);
        }
        return Hashing.sha1().hashString(sb.toString(), Charsets.UTF_8).toString().toUpperCase();
    }

}
