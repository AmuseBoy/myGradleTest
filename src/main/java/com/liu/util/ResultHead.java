package com.liu.util;

import java.io.Serializable;

/**
 * @Desc 返回体
 * @Author 刘培振
 * @Date 2017/9/14-11:20
 */
public class ResultHead implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String retFlag;
    private String retMsg;
    
    public ResultHead() {
    }
    
    public ResultHead(String retFlag, String retMsg) {
        this.retFlag = retFlag;
        this.retMsg = retMsg;
    }
    
    public String getRetFlag() {
        return retFlag;
    }
    public void setRetFlag(String retFlag) {
        this.retFlag = retFlag;
    }
    public String getRetMsg() {
        return retMsg;
    }
    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
