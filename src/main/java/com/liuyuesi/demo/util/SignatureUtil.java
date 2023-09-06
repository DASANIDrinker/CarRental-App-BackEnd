package com.liuyuesi.demo.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class SignatureUtil {
	
	public static String getSign(Object o) throws IllegalAccessException{
		ArrayList<String> list = new ArrayList<String>();
		Class cls = o.getClass();
		Field[] fields = cls.getDeclaredFields();
		for(Field f:fields) {
			f.setAccessible(true);
			if(f.get(o) != null && f.get(o) != "") {
				String name = f.getName();
				XStreamAlias anno = f.getAnnotation(XStreamAlias.class);
				if(anno != null) {
					name = anno.value();
				}
//				if(name == "appId") {
//					name = "appid";
//				}else if(name == "mchId") {
//					name = "mch_id";
//				}else if(name == "nonceStr") {
//					name = "nonce_str";
//				}else if(name == "outTradeNo") {
//					name = "out_trade_no";
//				}else if(name == "totalFee") {
//					name = "total_fee";
//				}else if(name == "spbillCreateIp") {
//					name = "spbill_create_ip";
//				}else if(name == "notifyUrl") {
//					name = "notify_url";
//				}else if(name == "tradeType") {
//					name = "trade_type";
//				}else if(name == "signType") {
//					name = "sign_type";
//				}
				list.add(name + "=" + f.get(o) + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + PaymentOrderSetting.getKey();
        System.out.println("签名数据：" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        return result;
	}
	
	
	
	public static String getSign(Map<String, Object> map) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + PaymentOrderSetting.getKey();
        //Util.log("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        //Util.log("Sign Result:" + result);
        return result;
    }
}
