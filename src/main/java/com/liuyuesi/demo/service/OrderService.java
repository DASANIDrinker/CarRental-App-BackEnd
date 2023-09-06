package com.liuyuesi.demo.service;

import java.util.List;

import com.liuyuesi.demo.entity.Order;

public interface OrderService {

	List<Order> findAll();
	
	List<Order> getOrderByUserId(String accessToken);
	
	List<Order> getOrderById(int orderId);
	
	int insert(Order order);
	
	int updateOrder(Order order);
}
