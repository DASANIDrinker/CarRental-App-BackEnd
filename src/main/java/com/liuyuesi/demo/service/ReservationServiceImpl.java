package com.liuyuesi.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liuyuesi.demo.entity.Driver;
import com.liuyuesi.demo.entity.Reservation;
import com.liuyuesi.demo.mapper.DriverMapper;
import com.liuyuesi.demo.mapper.ReservationMapper;

@Service
public class ReservationServiceImpl {

	@Autowired
	private ReservationMapper reservationMapper;
	
	@Autowired
	private DriverMapper driverMapper;
	
	public int insertReservation(Reservation reservation) {
		reservationMapper.insertReservation(reservation);
		return reservation.getReservationId();
	}
	
	public String convertResToDriver(String name,String id,String carClass,Integer accessToken,String birth,String gender,String nationality,String address) {
		Driver driver = new Driver();
		driver.setAccessToken(accessToken);
		driver.setAddress(address);
		driver.setBirth(birth);
		driver.setCarClass(carClass);
		driver.setGender(gender);
		driver.setId(id);
		driver.setName(name);
		driver.setNationality(nationality);
		driverMapper.insert(driver);
		return driver.getId();
	}

	@Transactional
	public List<Reservation> findAll(){
	
		Cursor<Reservation> reservations = reservationMapper.findAll();
		List<Reservation> reservationList = new ArrayList<Reservation>();
		for(Reservation reservation:reservations) {
			reservationList.add(reservation);
		}
		return reservationList;
	}
	
	public String update(Reservation reservation) {
		if(reservationMapper.update(reservation) == 1) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	public String delete(String reservationIds) {
		if(reservationMapper.delete(reservationIds) >= 1) {
			return "success";
		}else {
			return "fail";
		}
	}
}
