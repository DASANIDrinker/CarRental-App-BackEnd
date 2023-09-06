package com.liuyuesi.demo.util;

public class PaymentOrderSetting {
	//小程序ID
    private static String appID = "";

    // 小程序的secret
    private static String secret = "";

    //商户号
    private static String mchId = "";

    // 商户支付秘钥
    private static String key = "";

    // 回调通知地址
    private static String notifyUrl = "http://localhost:8081/wxpay/weixin/callback";

    //交易类型
    private static  String tradeType = "JSAPI";

    //统一下单API接口链接
    private static String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    //查询订单API接口链接
    private static String queryUrl = "https://api.mch.weixin.qq.com/pay/orderquery";

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
    	PaymentOrderSetting.key = key;
    }

    public static String getAppID() {
        return appID;
    }

    public static void setAppID(String appID) {
    	PaymentOrderSetting.appID = appID;
    }

    public static String getMch_id() {
        return mchId.trim();
    }

    public static void setMch_id(String mch_id) {
    	PaymentOrderSetting.mchId = mch_id;
    }

    public static String getSecret() {
        return secret;
    }

    public static void setSecret(String secret) {
    	PaymentOrderSetting.secret = secret;
    }

    public static String getNotify_url() {
        return notifyUrl;
    }

    public static void setNotify_url(String notify_url) {
    	PaymentOrderSetting.notifyUrl = notify_url;
    }

    public static String getTrade_type() {
        return tradeType;
    }

    public static void setTrade_type(String trade_type) {
    	PaymentOrderSetting.tradeType = trade_type;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
    	PaymentOrderSetting.url = url;
    }

    public static String getQuery_url() {
        return queryUrl;
    }

    public static void setQuery_url(String query_url) {
    	PaymentOrderSetting.queryUrl = query_url;
    }
}
