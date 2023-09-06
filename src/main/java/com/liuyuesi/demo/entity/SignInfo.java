package com.liuyuesi.demo.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class SignInfo {
	
	private String appId;//小程序ID

    private String timeStamp;//时间戳

    private String nonceStr;//随机串

    private String outTradeNo;//商户订单号
    
    @XStreamAlias("package")
    private String prepay_id;

    private String signType;//签名方式

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String repay_id) {
        this.prepay_id = repay_id;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
    
    public void setOutTradeNo(String outTradeNo) {
    	this.outTradeNo = outTradeNo;
    }
    
    public String getOutTradeNo() {
    	return outTradeNo;
    }
}

