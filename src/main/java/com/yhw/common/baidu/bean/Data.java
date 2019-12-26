/**
  * Copyright 2019 bejson.com 
  */
package com.yhw.common.baidu.bean;
import java.util.List;

/**
 * Auto-generated: 2019-10-31 17:6:3
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data {

    private List<Ret> ret;
    private String templateSign;
    private String templateName;
    private int scores;
    private boolean isStructured;
    private String logId;
    private int clockwiseAngle;
    public void setRet(List<Ret> ret) {
         this.ret = ret;
     }
     public List<Ret> getRet() {
         return ret;
     }

    public void setTemplateSign(String templateSign) {
         this.templateSign = templateSign;
     }
     public String getTemplateSign() {
         return templateSign;
     }

    public void setTemplateName(String templateName) {
         this.templateName = templateName;
     }
     public String getTemplateName() {
         return templateName;
     }

    public void setScores(int scores) {
         this.scores = scores;
     }
     public int getScores() {
         return scores;
     }

    public void setIsStructured(boolean isStructured) {
         this.isStructured = isStructured;
     }
     public boolean getIsStructured() {
         return isStructured;
     }

    public void setLogId(String logId) {
         this.logId = logId;
     }
     public String getLogId() {
         return logId;
     }

    public void setClockwiseAngle(int clockwiseAngle) {
         this.clockwiseAngle = clockwiseAngle;
     }
     public int getClockwiseAngle() {
         return clockwiseAngle;
     }

}