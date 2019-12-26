/**
 * Copyright 2019 bejson.com
 */
package com.yhw.common.baidu.bean.certificate;

/**
 * Auto-generated: 2019-11-01 17:37:32
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class CertificateInfo {

    private String 合格标志号="";
    private String 号牌号码="";
    private String 有效期="";

    public void set合格标志号(String 合格标志号) {
        this.合格标志号 = 合格标志号;
    }

    public String get合格标志号() {
        return 合格标志号;
    }

    public void set号牌号码(String 号牌号码) {
        this.号牌号码 = 号牌号码;
    }

    public String get号牌号码() {
        return 号牌号码;
    }

    public String get有效期() {
        return 有效期;
    }

    public void set有效期(String 有效期) {
        this.有效期 = 有效期;
    }

    @Override
    public String toString() {
        return "CertificateInfo{" +
                "合格标志号='" + 合格标志号 + '\'' +
                ", 号牌号码='" + 号牌号码 + '\'' +
                ", 有效期='" + 有效期 + '\'' +
                '}';
    }
}