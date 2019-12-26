package com.yhw.domain;

/**
 * 增值税发票商品名、单价等
 * author yang.hongwei
 * date 2019/12/19
 * */
public class InvoiceData {
    private String word;//列数据
    private String row;//列号

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }
}
