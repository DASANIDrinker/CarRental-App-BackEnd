package com.liuyuesi.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liuyuesi.demo.entity.OrderStatus;
import com.liuyuesi.demo.service.OrderStatusServiceImpl;

@RestController
@RequestMapping("/orderstatus")
public class OrderStatusController {

	@Autowired
	private OrderStatusServiceImpl orderStatusService;
	
	@GetMapping("/getAll")
	public List<OrderStatus> findAll(){
		return orderStatusService.findAll();
	}
	
	@GetMapping("/get")
	public OrderStatus findById(@RequestParam("orderStatusId") byte orderStatusId) {
		return orderStatusService.findById(orderStatusId);
	}
	
	@PostMapping("/insert")
	public int insert(@RequestBody OrderStatus orderstatus) {
		return orderStatusService.insert(orderstatus);
	}
}
