package com.liuyuesi.demo.entity;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	private int orderId;
	private String phone;
	private Vehicle vehicle;
	private VehicleType vehicleType;
	private String id;
	private String accessToken;
//	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date startDate;
//	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date endDate;
	private Location pickupLocation;
	private Location dropoffLocation;
	private OrderStatus orderStatus;
	private int days;
	private int total;
	private PaymentOrderInfo paymentOrderInfo;
	private String name;
}
