package com.liuyuesi.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liuyuesi.demo.entity.Vehicle;
import com.liuyuesi.demo.mapper.VehicleMapper;

@Service
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	VehicleMapper vehicleMapper;

//	@Autowired
//	SqlSessionTemplate sqlSessionTemplate;

//	private List<Vehicle> vehicles = new ArrayList<Vehicle>();

	// 获取全部车辆
	@Transactional
	public List<Vehicle> findAll() {
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		try (Cursor<Vehicle> cursor = vehicleMapper.findAll()) {
			for (Vehicle v : cursor) {
				vehicles.add(v);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vehicles;
	};

	public Vehicle findVehicleById(int id) {
		return vehicleMapper.findVehicleById(id);
	};

	public Vehicle findVehicleByPlate(String plateNumber) {
		return vehicleMapper.findVehicleByPlate(plateNumber);
	};

	// 通过车型ID获取某种车型的全部可租用车辆
	@Transactional
	public List<Vehicle> findVehicleByTypeId(byte typeId) {
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		try (Cursor<Vehicle> cursor = vehicleMapper.findVehicleByTypeId(typeId)) {
			for (Vehicle v : cursor) {
				vehicles.add(v);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vehicles;
	}

	// 并将车实例的租用状态更新为租用中
	public void updateOccupied(Vehicle vehicle) {
		vehicleMapper.updateOccupied(vehicle);
	}

	// 通过车型PK获取一辆该车实例
	public Vehicle findAVehcileByTypeId(int typeId) {
		return vehicleMapper.findAVehicleByTypeId(typeId);
	}

	// 检查某一个车型是否租满
	public boolean checkTypeIsFull(int typeId) {
		if (vehicleMapper.checkTypeIsFull(typeId) > 0) {
			return false;// 未租满
		} else {
			return true;// 租满
		}
	}

	//插入一个车
	public int insert(Vehicle vehicle, Integer vehicleTypeId) {
		int count = vehicleMapper.insert(vehicle, vehicleTypeId);
		return count;
	}
	
	//更新一台汽车
	public String update(Short vehicleId, Boolean etc, Boolean recorder, Boolean mount, Boolean umbrella,
			Boolean occupied) {
		int count = vehicleMapper.update(vehicleId, etc, recorder, mount, umbrella, occupied);
		if(count == 1) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	//删除一台车
	public String delete(String vehicleId) {
		int count = vehicleMapper.delete(vehicleId);
		if(count != 0) {
			return "success";
		}else {
			return "fail";
		}
	}
}
