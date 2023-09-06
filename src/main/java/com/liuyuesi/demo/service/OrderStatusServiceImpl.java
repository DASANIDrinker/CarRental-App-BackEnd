package com.liuyuesi.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liuyuesi.demo.controller.OrderStatusController;
import com.liuyuesi.demo.entity.OrderStatus;
import com.liuyuesi.demo.mapper.OrderStatusMapper;

@Service
public class OrderStatusServiceImpl {

	@Autowired
	private OrderStatusMapper orderStatusMapper;
	
	public List<OrderStatus> findAll(){
		return orderStatusMapper.findAll();
	}
	
	public OrderStatus findById(byte orderStatusId) {
		return orderStatusMapper.findById(orderStatusId);
	}
	
	public int insert(OrderStatus orderStatus) {
		orderStatusMapper.insert(orderStatus);
		return orderStatus.getOrderStatusId();
	}
}
