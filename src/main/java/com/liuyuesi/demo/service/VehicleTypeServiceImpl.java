package com.liuyuesi.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liuyuesi.demo.entity.Vehicle;
import com.liuyuesi.demo.entity.VehicleType;
import com.liuyuesi.demo.mapper.VehicleTypeMapper;

@Service
public class VehicleTypeServiceImpl {

	@Autowired
	VehicleTypeMapper vehicleTypeMapper;
	
	//获取全部车型
	public List<VehicleType> findAll(){
		return vehicleTypeMapper.findAll();
	}
	
	public int insert(VehicleType vehicleType){
		vehicleTypeMapper.insert(vehicleType);
		return vehicleType.getVehicleTypeId();
	}
	
	public String update(VehicleType type) {
		int count = vehicleTypeMapper.update(type);
		if(count == 1) {
			return "success";
		}else {
			return "fail";
		}
	};
	
	public String delete(String types) {
		int count = vehicleTypeMapper.delete(types);
		if(count > 0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	public List<VehicleType> selectByBrandAndModel(String brand, String model){
		return vehicleTypeMapper.selectByBrandAndModel(brand,model);
	}
	
	//通过车型PK获取该车型全部信息
	public VehicleType findVehicleTypeById(byte typeId) {
		return vehicleTypeMapper.findVehicleTypeById(typeId);
	}
	
	//将租满的车型的is_full修改为true 代表该车型已租满
	public void updateFull(int typeId) {
		vehicleTypeMapper.updateFull(typeId);
	}
	
	public int updateTypeUrl(byte vehicleTypeId,String url) {
		return vehicleTypeMapper.updateTypeUrl(vehicleTypeId, url);
	}
	
	//删除某个车辆类型的图片
	public int deleteVehicleTypeImage(byte vehicleTypeId) {
		return vehicleTypeMapper.deleteVehicleTypeImage(vehicleTypeId);
	}
	
}
