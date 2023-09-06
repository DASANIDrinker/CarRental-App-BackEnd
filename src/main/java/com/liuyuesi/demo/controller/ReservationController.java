package com.liuyuesi.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liuyuesi.demo.entity.Location;
import com.liuyuesi.demo.entity.PaymentOrderInfo;
import com.liuyuesi.demo.entity.Reservation;
import com.liuyuesi.demo.entity.Vehicle;
import com.liuyuesi.demo.entity.VehicleType;
import com.liuyuesi.demo.service.ReservationServiceImpl;
import com.liuyuesi.demo.service.VehicleServiceImpl;
import com.liuyuesi.demo.service.VehicleTypeServiceImpl;
import com.liuyuesi.demo.util.String2DateConverter;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReservationController {
	
	@Autowired
	private ReservationServiceImpl reservationService;
	
	@Autowired
	VehicleServiceImpl vehicleService;

	@Autowired
	VehicleTypeServiceImpl vehicleTypeService;
	
//	@Autowired
//	String2DateConverter converter;
	
	@RequestMapping("/insert")
	public int insertReservation(@RequestParam(value = "name") String name,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "phone") String phone,
			@RequestParam(value = "accessToken") String accessToken,
			@RequestParam(value = "vehicleTypeId") byte vehicleTypeId,
			@RequestParam(value = "sign") String sign,
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate,
			@RequestParam(value = "pickUpLocationId") int pickUpLocationId,
			@RequestParam(value = "dropOffLocationId") int dropOffLocationId,
			@RequestParam(value = "days") int days,
			@RequestParam(value = "total") int total) throws ParseException {
			
			Reservation reservation = new Reservation();
			VehicleType vehicleType = new VehicleType();
			PaymentOrderInfo paymentOrder = new PaymentOrderInfo();
			Location pickUpLocation = new Location();
			Location dropOffLocation = new Location();
			
			vehicleType.setVehicleTypeId(vehicleTypeId);
			paymentOrder.setSign(sign);
			pickUpLocation.setLocationId(pickUpLocationId);
			dropOffLocation.setLocationId(dropOffLocationId);
			
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
			
			reservation.setAccessToken(accessToken);
			reservation.setId(id);
			reservation.setName(name);
			reservation.setPhone(phone);
			reservation.setName(name);
			reservation.setVehicle(tempVehicle);
			reservation.setVehicleType(vehicleType);
			reservation.setPaymentOrderInfo(paymentOrder);
			reservation.setStartDate(tempStartDate);
			reservation.setEndDate(tempEndDate);
			reservation.setPickupLocation(pickUpLocation);
			reservation.setDropoffLocation(dropOffLocation);
			reservation.setDays(days);
			reservation.setTotal(total);

			System.out.println(name);
			System.out.println(id);
			System.out.println(phone);
			System.out.println(accessToken);
			System.out.println(reservation);
			
		return reservationService.insertReservation(reservation);
	}

	@RequestMapping("/convert")
	public String convertResToDriver(@RequestParam(value = "name") String name,
			@RequestParam(value = "id") String id, 
			@RequestParam(value = "carClass") String carClass,
			@RequestParam(value = "accessToken") Integer accessToken,
			@RequestParam(value = "birth", required = false) String birth,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "nationality", required = false) String nationality,
			@RequestParam(value = "address", required = false) String address) {
		
		return reservationService.convertResToDriver(name,id,carClass,accessToken,birth,gender,nationality,address);
		
	}

	@RequestMapping("/findAll")
	public List<Reservation> findAll(){
		return reservationService.findAll();
	}
	
	@PostMapping("/update")
//	public String update(@RequestBody Reservation reservation) {
	public String update(
			@RequestParam("reservationId") Integer reservationId, 
			@RequestParam("name") String name,
			@RequestParam("phone") String phone,
			@RequestParam("vehicleId") Short vehicleId,
			@RequestParam("vehicleTypeId") byte vehicleTypeId,
			@RequestParam("id") String id,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate,
			@RequestParam("pickupLocation") Integer pickupLocationId,
			@RequestParam("dropoffLocation") Integer dropoffLocationId,
			@RequestParam("total") Integer total,
			@RequestParam("days") Integer days) throws ParseException {
		
		Reservation reservation = new Reservation();
		VehicleType vehicleType = new VehicleType();
		Vehicle vehicle = new Vehicle();
		
		vehicleType.setVehicleTypeId(vehicleTypeId);
		vehicle.setVehicleId(vehicleId);
		
		Date sDate = new Date();
		Date eDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		sDate = simpleDateFormat.parse(startDate);
		eDate = simpleDateFormat.parse(endDate);
		
//		sDate = String2DateConverter.convert(startDate);
//		eDate = String2DateConverter.convert(endDate);
		
		reservation.setReservationId(reservationId);
		reservation.setName(name);
		reservation.setPhone(phone);
		reservation.setVehicle(vehicle);
		reservation.setVehicleType(vehicleType);
		reservation.setId(id);
		reservation.setStartDate(sDate);
		reservation.setEndDate(eDate);
		reservation.setTotal(total);
		reservation.setDays(days);
		return reservationService.update(reservation);
	}

	@DeleteMapping("/delete")
	public String delete(@RequestBody List<Integer> reservationIds) {
		String ids = StringUtils.join(reservationIds,",");
		return reservationService.delete(ids);
	}
}
