package com.liuyuesi.demo.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation implements Serializable{

	private Integer reservationId;
	private String name;
	private String id;
	private String phone;
	private String accessToken;
	private Vehicle vehicle;
	private VehicleType vehicleType;
	private Date startDate;
	private Date endDate;
	private Location pickupLocation;
	private Location dropoffLocation;
	private int days;
	private int total;
	private PaymentOrderInfo paymentOrderInfo;
	
	
	
}
