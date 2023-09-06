package com.liuyuesi.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liuyuesi.demo.entity.PaymentOrderInfo;
import com.liuyuesi.demo.entity.User;
import com.liuyuesi.demo.service.OrderServiceImpl;
import com.liuyuesi.demo.service.PaymentOrderServiceImpl;
import com.liuyuesi.demo.service.UserServiceImpl;

@RestController
@RequestMapping("/paymentorders")
public class PaymentOrderController {

	@Autowired
	private PaymentOrderServiceImpl paymentOrderService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@GetMapping("/findAll")
	public List<PaymentOrderInfo> findAll(){
		return paymentOrderService.findAll();
	}
	
	@PostMapping("/insert")
	public void insert(@RequestParam("sign") String sign,@RequestParam("outTradeNo") String outTradeNo) {
		System.out.println(sign);
		paymentOrderService.insert(sign,outTradeNo);
	}
	
	@GetMapping("/findPaymentOrderBySign")
	public PaymentOrderInfo findPaymentOrderBySign(@RequestParam("sign") String sign) {
		return paymentOrderService.findPaymentOrderBySign(sign);
	}
	
//	@GetMapping("/findPaymentOrderByUser")
//	public List<PaymentOrderInfo> findPaymentOrderByUser(@RequestParam("accessToken") int accessToken){
//		User user = userService.findUserByAccessToken(accessToken);
//		String openId = user.getOpenId();
//		
//	}
}
