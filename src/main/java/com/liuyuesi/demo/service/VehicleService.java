package com.liuyuesi.demo.service;

import java.util.List;

import org.apache.ibatis.cursor.Cursor;

import com.liuyuesi.demo.entity.Vehicle;

public interface VehicleService {

	//获取全部车辆
	List<Vehicle> findAll();
	
	
	Vehicle findVehicleById(int id);
	
	
	Vehicle findVehicleByPlate(String plateNumber);
	
	//通过车型ID获取某种车型的全部可租用车辆
	List<Vehicle> findVehicleByTypeId(byte vehicleTypeId);
	
	//并将车实例的租用状态更新为租用中
	void updateOccupied(Vehicle vehicle);
	
	//通过车型PK获取一辆该车实例
	Vehicle findAVehcileByTypeId(int typeId);
	
	//检查某一个车型是否租满
	boolean checkTypeIsFull(int typeId);
	

	
}
