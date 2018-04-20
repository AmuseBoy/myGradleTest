package com.liu.commons;

/**
 * 自定义异常：主要用于处"业务逻辑"错误
 * 既返回错误信息，又需要回滚数据；
 * @author 刘培振 liupeizhen@bestvike.com
 *
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private String retFlag;
    private String retMsg;
    
    public BusinessException(String retFlag, String retMsg) {
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
