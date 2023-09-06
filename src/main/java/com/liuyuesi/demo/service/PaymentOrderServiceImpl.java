package com.liuyuesi.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liuyuesi.demo.entity.PaymentOrderInfo;
import com.liuyuesi.demo.mapper.PaymentOrderMapper;

@Service
public class PaymentOrderServiceImpl implements PaymentOrderService {

	@Autowired
	PaymentOrderMapper paymentOrderMapper;
	
	@Transactional
	public List<PaymentOrderInfo> findAll(){
		List<PaymentOrderInfo> paymentOrders = new ArrayList<PaymentOrderInfo>();
		Cursor<PaymentOrderInfo> all = paymentOrderMapper.findAll();
		for(PaymentOrderInfo order:all) {
			paymentOrders.add(order);
		}
		return paymentOrders;
	}
	
	public void insert(String sign,String outTradeNo) {
		paymentOrderMapper.insert(sign,outTradeNo);
//		if(sign!=null||sign!="") {
//		}
//		return "";
	}
	
	public PaymentOrderInfo findPaymentOrderBySign(String sign) {
		PaymentOrderInfo paymentOrderInfo = paymentOrderMapper.findPaymentOrderBySign(sign);
		if(paymentOrderInfo!=null) {
			return paymentOrderInfo;
		}
		return new PaymentOrderInfo();
	}
	
	public String findOutTradeNoBySign(String sign) {
		return paymentOrderMapper.findOutTradeNoBySign(sign);
	}
}
