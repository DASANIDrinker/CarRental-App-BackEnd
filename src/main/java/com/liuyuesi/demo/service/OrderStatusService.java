package com.liuyuesi.demo.service;

import java.util.List;

import com.liuyuesi.demo.controller.OrderStatusController;

public interface OrderStatusService {

	List<OrderStatusController> findAll();
	
	OrderStatusController findById(byte orderStatusId);
	
	int insert(OrderStatusController orderstatus);
	
	
}
