/**
 * Copyright 2019 bejson.com
 */
package com.yhw.common.baidu.bean;

import java.util.List;

/**
 * Auto-generated: 2019-11-18 17:20:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class 通用文字识别高精度Result {

    private long log_id;
    private int direction;
    private int words_result_num;
    private List<WordsResult> words_result;

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result(List<WordsResult> words_result) {
        this.words_result = words_result;
    }

    public List<WordsResult> getWords_result() {
        return words_result;
    }

}