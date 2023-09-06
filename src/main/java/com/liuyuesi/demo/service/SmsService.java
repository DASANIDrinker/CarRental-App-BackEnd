package com.liuyuesi.demo.service;

public interface SmsService {

	
	public boolean sendReservationConfirmationToStaff(String phone,String name, String id, Integer yearCurrent, Integer yearEnd, Integer monthCurrent, Integer monthEnd, Integer dateCurrent, Integer dateEnd, String brand, String model,String minuteCurrent,String minuteEnd,Integer hourCurrent,Integer hourEnd);
	public boolean sendOrderConfirmationToStaff(String phone,String name, String id, Integer yearCurrent, Integer yearEnd, Integer monthCurrent, Integer monthEnd, Integer dateCurrent, Integer dateEnd, String brand, String model,String minuteCurrent,String minuteEnd,Integer hourCurrent,Integer hourEnd);
	public boolean sendConfirmationToUser(String phone,Integer yearCurrent, Integer yearEnd, Integer monthCurrent, Integer monthEnd, Integer dateCurrent, Integer dateEnd, String brand, String model,String minuteCurrent,String minuteEnd,Integer hourCurrent,Integer hourEnd);
}
