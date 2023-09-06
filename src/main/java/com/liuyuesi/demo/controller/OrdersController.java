package com.liuyuesi.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liuyuesi.demo.entity.Location;
import com.liuyuesi.demo.entity.Order;
import com.liuyuesi.demo.entity.OrderStatus;
import com.liuyuesi.demo.entity.PaymentOrderInfo;
import com.liuyuesi.demo.entity.Vehicle;
import com.liuyuesi.demo.entity.VehicleType;
import com.liuyuesi.demo.service.OrderServiceImpl;
import com.liuyuesi.demo.service.VehicleServiceImpl;
import com.liuyuesi.demo.service.VehicleTypeServiceImpl;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrdersController {

	@Autowired
	private OrderServiceImpl orderService;
	
	@Autowired
	VehicleServiceImpl vehicleService;

	@Autowired
	VehicleTypeServiceImpl vehicleTypeService;
	
	@GetMapping("/getAll")
	public List<Order> findAll(){
		return orderService.findAll();
	}
	
	@PostMapping("/getByUserId")
	public List<Order> getOrderByUserId(@RequestParam("userId") String accessToken) {
		System.out.println(accessToken);
		return orderService.getOrderByUserId(accessToken);
	}
	
	@GetMapping("/getById")
	public List<Order> getOrderById(@RequestParam("orderId") int orderId){
		return orderService.getOrderById(orderId);
	}
	
	@PostMapping("/insert")
	public int insert(
			@RequestParam("vehicleTypeId") byte vehicleTypeId,
			@RequestParam("id") String id,
			@RequestParam("phone") String phone,
			@RequestParam("sign") String sign,
			@RequestParam("accessToken") String accessToken,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate,
			@RequestParam("pickUpLocationId") int pickUpLocationId,
			@RequestParam("dropOffLocationId") int dropOffLocationId,
			@RequestParam("orderStatusId") byte orderStatusId,
			@RequestParam("days") int days,
			@RequestParam("total") int total,
			@RequestParam("name") String name) throws ParseException {
		
		Order order = new Order();
		//Vehicle vehicle = new Vehicle();
		VehicleType vehicleType = new VehicleType();
		PaymentOrderInfo paymentOrder = new PaymentOrderInfo();
		Location pickUpLocation = new Location();
		Location dropOffLocation = new Location();
		OrderStatus orderStatus = new OrderStatus();
		
		//基础变量设置 
		vehicleType.setVehicleTypeId(vehicleTypeId);
		paymentOrder.setSign(sign);
		pickUpLocation.setLocationId(pickUpLocationId);
		dropOffLocation.setLocationId(dropOffLocationId);
		orderStatus.setOrderStatusId(orderStatusId);
		
		//将前端的String格式时间转化为后端的Date格式时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date tempStartDate = sdf.parse(startDate);
		Date tempEndDate = sdf.parse(endDate);
		
		//根据vehicleTypeId获取vehicleId
		Vehicle tempVehicle = vehicleService.findAVehcileByTypeId(vehicleTypeId);
		vehicleService.updateOccupied(tempVehicle);
		if (vehicleService.checkTypeIsFull(vehicleTypeId)) {
			vehicleTypeService.updateFull(vehicleTypeId);
		}
		
		//设置传进service层和mapper层的实例参数
//		order.setOrderId(null);
		order.setName(name);
		order.setAccessToken(accessToken);
		order.setId(id);
		order.setPhone(phone);
		order.setVehicle(tempVehicle);
		order.setVehicleType(vehicleType);
		order.setPaymentOrderInfo(paymentOrder);
		order.setStartDate(tempStartDate);
		order.setEndDate(tempEndDate);
		order.setPickupLocation(pickUpLocation);
		order.setDropoffLocation(dropOffLocation);
		order.setOrderStatus(orderStatus);
		order.setDays(days);
		order.setTotal(total);
		
		System.out.println(order);
		
		return orderService.insert(order);
	}
	
	@PostMapping("/update")
	public int updateOrder(
			@RequestParam("orderId") Integer orderId,
			@RequestParam("id") String id,
			@RequestParam("phone") String phone,
			@RequestParam("vehicleTypeId") byte vehicleTypeId,
			@RequestParam("vehicleId") Short vehicleId,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate,
			@RequestParam("days") Integer days,
			@RequestParam("total") Integer total,
			@RequestParam("name") String name) throws ParseException {
		
		
		System.out.println(orderId);
		System.out.println(id);
		System.out.println(phone);
		System.out.println(vehicleTypeId);
		System.out.println(vehicleId);
		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println(days);
		System.out.println(total);
		System.out.println(name);
		
		
		
		Order order = new Order();
		VehicleType vehicleType = new VehicleType();
		Vehicle vehicle = new Vehicle();
		
		vehicleType.setVehicleTypeId(vehicleTypeId);
		vehicle.setVehicleId(vehicleId);
		
		Date sDate = new Date();
		Date eDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		sDate = simpleDateFormat.parse(startDate);
		eDate = simpleDateFormat.parse(endDate);
		
		order.setOrderId(orderId);
		order.setId(id);
		order.setPhone(phone);
		order.setVehicleType(vehicleType);
		order.setVehicle(vehicle);
		order.setStartDate(sDate);
		order.setEndDate(eDate);
		order.setDays(days);
		order.setTotal(total);
		order.setName(name);
		
		return orderService.updateOrder(order);
		
	}
}
