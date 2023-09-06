package com.liuyuesi.demo.service;

import java.util.List;

import com.liuyuesi.demo.entity.Vehicle;
import com.liuyuesi.demo.entity.VehicleType;

public interface VehicleTypeService {
	//获取全部车型
	List<VehicleType> findAll();
	
	
	int insert(VehicleType vehicleType);
	
	
	List<VehicleType> selectByBrandAndModel(String brand, String model);
	
	//通过车型PK获取该车型全部信息
	VehicleType findVehicleTypeById(byte typeId);
	
	//将租满的车型的is_full修改为true 代表该车型已租满
	void updateFull(int typeId);
	
	int updateTypeUrl(byte vehicleTypeId, String url);
	
	int deleteVehicleTypeImage(byte vehicleTypeId);
	
}
