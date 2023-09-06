package com.liuyuesi.demo.util;

import org.springframework.beans.factory.annotation.Value;

public class SmsUtil{

//	@Value("${tencent.sms.keyId}")
    private static String secretID = "";
//    @Value("${tencent.sms.keysecret}")
    private static String secretKey = "";
//    @Value("${tencent.sms.smsSdkAppId}")
    private static String smsSdkAppID = "";
//    @Value("${tencent.sms.signName}")
    private static String signName = "";
//    @Value("${tencent.sms.reservationStaffTemplateId}")
    private static String reservationStaffTemplateId = "";
//    @Value("${tencent.sms.orderStaffTemplateId}")
    private static String orderStaffTemplateId = "";
//    @Value("${tencent.sms.userTemplateId}")
    private static String userTemplateId = "";
    

    public static String getSecretID() {
    	return secretID;
    }
    
    public static String getSecretKey() {
    	return secretKey;
    }
    
    public static String getSmsSdkAppID() {
    	return smsSdkAppID;
    }
    
    public static String getSignName() {
    	return signName;
    }
    
    public static String getReservationStaffTemplateId() {
    	return reservationStaffTemplateId;
    }
    
    public static String getOrderStaffTemplateId() {
    	return orderStaffTemplateId;
    }
    
    public static String getUserTemplateId() {
    	return userTemplateId;
    }
    
    
}
