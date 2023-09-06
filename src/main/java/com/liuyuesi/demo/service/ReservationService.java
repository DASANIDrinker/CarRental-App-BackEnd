package com.liuyuesi.demo.service;

import java.util.List;

import com.liuyuesi.demo.entity.Reservation;

public interface ReservationService {

	int insertReservation(Reservation reservation);
	
	String convertResToDriver(String name,String id,String carClass,Integer accessToken,String birth,String gender,String nationality,String address);

	List<Reservation> findAll();
}
