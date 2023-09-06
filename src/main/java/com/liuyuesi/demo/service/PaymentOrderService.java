package com.liuyuesi.demo.service;

import java.util.List;

import com.liuyuesi.demo.entity.PaymentOrderInfo;

public interface PaymentOrderService {
	
	List<PaymentOrderInfo> findAll();
	
	void insert(String sign,String outTradeNo);
	
	PaymentOrderInfo findPaymentOrderBySign(String sign);
	
	String findOutTradeNoBySign(String sign);
//	void deletePaymentOrderBySign(String sign);

}
