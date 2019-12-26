package com.yhw.common.baidu.bean.invoice;

import com.yhw.common.utils.CurrencyUtil;

/**
 * 发票识别信息
 * Created by wang.dingan on 2019/10/31.
 */
public class InvoiceInfo {
    /**
     * 开票日期
     */
    private String 开票日期="";
    /**
     * 发票号码
     */
    private String 发票号码="";
    /**
     * 发票代码
     */
    private String 发票代码="";
    /**
     * 购买方名称
     */
    private String 购买方名称="";
    /**
     * 购买方号码
     */
    private String 购买方号码="";
    /**
     * 纳税人识别号
     */
    private String 纳税人识别号="";
    /**
     * 车辆类型
     */
    private String 车辆类型="";
    /**
     *厂牌型号
     */
    private String 厂牌型号="";
    /**
     *合格证号
     */
    private String 合格证号="";
    /**
     *发动机号码
     */
    private String 发动机号码="";
    /**
     * 车架号
     */
    private String 车架号="";
    /**
     * 销货单位名称
     */
    private String 销货单位名称="";
    /**
     * 机器编码
     */
    private String 机器编码="";
    /**
     * 价税合计大写
     */
    private String 价税合计="";
    /**
     * 价税合计小写
     */
    private String 价税合计小写="";
    /**
     * 税率
     */
    private String 税率 = "13%";
    /**
     * 税额
     */
    private String 税额="";
    /**
     * 不含税价格
     */
    private String 不含税价格="";

    public String get开票日期() {
        return 开票日期;
    }

    public void set开票日期(String 开票日期) {
        this.开票日期 = 开票日期;
    }

    public String get发票号码() {
        return 发票号码;
    }

    public void set发票号码(String 发票号码) {
        this.发票号码 = 发票号码;
    }

    public String get发票代码() {
        return 发票代码;
    }

    public void set发票代码(String 发票代码) {
        this.发票代码 = 发票代码;
    }

    public String get购买方名称() {
        return 购买方名称;
    }

    public void set购买方名称(String 购买方名称) {
        this.购买方名称 = 购买方名称;
    }

    public String get购买方号码() {
        return 购买方号码;
    }

    public void set购买方号码(String 购买方号码) {
        this.购买方号码 = 购买方号码;
    }

    public String get纳税人识别号() {
        return 纳税人识别号;
    }

    public void set纳税人识别号(String 纳税人识别号) {
        this.纳税人识别号 = 纳税人识别号;
    }

    public String get车辆类型() {
        return 车辆类型;
    }

    public void set车辆类型(String 车辆类型) {
        this.车辆类型 = 车辆类型;
    }

    public String get厂牌型号() {
        return 厂牌型号;
    }

    public void set厂牌型号(String 厂牌型号) {
        this.厂牌型号 = 厂牌型号;
    }

    public String get合格证号() {
        return 合格证号;
    }

    public void set合格证号(String 合格证号) {
        this.合格证号 = 合格证号;
    }

    public String get发动机号码() {
        return 发动机号码;
    }

    public void set发动机号码(String 发动机号码) {
        this.发动机号码 = 发动机号码;
    }

    public String get车架号() {
        return 车架号;
    }

    public void set车架号(String 车架号) {
        this.车架号 = 车架号;
    }

    public String get销货单位名称() {
        return 销货单位名称;
    }

    public void set销货单位名称(String 销货单位名称) {
        this.销货单位名称 = 销货单位名称;
    }

    public String get机器编码() {
        return 机器编码;
    }

    public void set机器编码(String 机器编码) {
        this.机器编码 = 机器编码;
    }

    public String get价税合计() {
        return CurrencyUtil.toChinaUpper(get价税合计小写());
    }

    public void set价税合计(String 价税合计) {
        this.价税合计 = 价税合计;
    }

    public String get价税合计小写() {
        价税合计小写 = CurrencyUtil.formatMoney(价税合计小写);
        return 价税合计小写;
    }

    public void set价税合计小写(String 价税合计小写) {
        价税合计小写 = CurrencyUtil.formatMoney(价税合计小写);
        this.价税合计小写 = 价税合计小写;
    }

    public String get税率() {
        return 税率;
    }

    public void set税率(String 税率) {
        this.税率 = 税率;
    }

    public String get税额() {
        税额 = CurrencyUtil.formatMoney(税额);
        return 税额;
    }

    public void set税额(String 税额) {
        税额 = CurrencyUtil.formatMoney(税额);
        this.税额 = 税额;
    }

    public String get不含税价格() {
        不含税价格 = CurrencyUtil.formatMoney(不含税价格);
        return 不含税价格;
    }

    public void set不含税价格(String 不含税价格) {
        不含税价格 = CurrencyUtil.formatMoney(不含税价格);
        this.不含税价格 = 不含税价格;
    }

    @Override
    public String toString() {
        return "InvoiceInfo{" +
                "开票日期='" + 开票日期 + '\'' +
                ", 发票号码='" + 发票号码 + '\'' +
                ", 发票代码='" + 发票代码 + '\'' +
                ", 购买方名称='" + 购买方名称 + '\'' +
                ", 购买方号码='" + 购买方号码 + '\'' +
                ", 纳税人识别号='" + 纳税人识别号 + '\'' +
                ", 车辆类型='" + 车辆类型 + '\'' +
                ", 厂牌型号='" + 厂牌型号 + '\'' +
                ", 合格证号='" + 合格证号 + '\'' +
                ", 发动机号码='" + 发动机号码 + '\'' +
                ", 车架号='" + 车架号 + '\'' +
                ", 销货单位名称='" + 销货单位名称 + '\'' +
                ", 机器编码='" + 机器编码 + '\'' +
                ", 价税合计='" + 价税合计 + '\'' +
                ", 价税合计小写='" + 价税合计小写 + '\'' +
                ", 税率='" + 税率 + '\'' +
                ", 税额='" + 税额 + '\'' +
                ", 不含税价格='" + 不含税价格 + '\'' +
                '}';
    }
}
