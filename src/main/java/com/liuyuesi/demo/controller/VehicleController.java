package com.liuyuesi.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.liuyuesi.demo.entity.Vehicle;
import com.liuyuesi.demo.entity.VehicleType;
import com.liuyuesi.demo.service.VehicleServiceImpl;
import com.liuyuesi.demo.service.VehicleTypeServiceImpl;

@RestController
@RequestMapping("/vehicle")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VehicleController {

	@Autowired
	VehicleServiceImpl vehicleService;

	@Autowired
	VehicleTypeServiceImpl vehicleTypeService;

	//插入车辆品种
	@RequestMapping(path = "/insertType")
	public int insertType(@RequestBody VehicleType type) {
		System.out.println(type);
		return vehicleTypeService.insert(type);
	}
	
	
//	插入车辆
	@CrossOrigin(origins = "*", maxAge = 3600)
	@PostMapping(path = "/insert", produces = "application/json;charset=utf-8", consumes = "application/json;charset=utf-8")
	public int insert(@RequestBody JSONObject json) {
		String plateNumber = "";
		Boolean etc = false;
		Boolean recorder = false;
		Boolean mount = false;
		Boolean umbrella = false;
		Boolean occupied = false;
		Integer vehicleType = 1;
		try {
			System.out.println(json);
			plateNumber = json.getString("plateNumber").replaceAll("\"", "");

			// 删掉""是因为数据自带""从而导致 数据无法被parseBoolean识别为true或者false
			String isRecorder = json.getString("recorder").replaceAll("\"", "");
			recorder = Boolean.parseBoolean(isRecorder);

			String isETC = json.getString("etc").replaceAll("\"", "");
			System.out.println(isETC);
			etc = Boolean.parseBoolean(isETC);
			System.out.println(etc);

			String isMount = json.getString("mount").replaceAll("\"", "");
			mount = Boolean.parseBoolean(isMount);

			String isUmbrella = json.getString("umbrella").replaceAll("\"", "");
			umbrella = Boolean.parseBoolean(isUmbrella);

			String isOccupied = json.getString("occupied").replaceAll("\"", "");
			occupied = Boolean.parseBoolean(isOccupied);

			vehicleType = json.getInteger("vehicleTypeId");

		} catch (Exception e) {

		}
		Vehicle vehicle = new Vehicle();
		vehicle.setIsETC(etc);
		vehicle.setIsMount(mount);
		vehicle.setIsOccupied(occupied);
		vehicle.setPlateNumber(plateNumber);
		vehicle.setIsRecorder(recorder);
		vehicle.setIsUmbrella(umbrella);
		System.out.println(vehicle);
		//回传的是新创建数据的PK
		int insert = vehicleService.insert(vehicle, vehicleType);
		return insert;

	}

	// 获取全部车辆
	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping(path = "/getAll", produces = "application/json;charset=utf-8")
	public List<Vehicle> getAvaliableVehicles() {
		return vehicleService.findAll();
	}

	// 通过车型ID获取某种车型的全部可租用车辆
	@GetMapping(path = "/getAllByTypeId", produces = "application/json;charset=utf-8")
	public List<Vehicle> getAvaliableVehiclesByTypeId(@RequestParam(value = "typeId") byte vehicleTypeId) {
		return vehicleService.findVehicleByTypeId(vehicleTypeId);
	}

	// 获取全部车型
	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping(path = "/getAllType", produces = "application/json;charset=utf-8")
	public List<VehicleType> getVehicleTypes() {
		return vehicleTypeService.findAll();
	}

	// 通过车型PK获取该车型全部信息
	@GetMapping(path = "/getType", produces = "application/json;charset = utf-8")
	public VehicleType getVehicleTypeById(@RequestParam(value = "typeId") byte vehicleTypeId) {
		return vehicleTypeService.findVehicleTypeById(vehicleTypeId);
	}

	// 通过车型PK获取一辆该车实例
	// 并将车实例的租用状态更新为租用中
	@PostMapping(path = "/updateOccupied", produces = "application/json;charset = utf-8")
	public String updateOccupied(@RequestParam(value = "typeId") int typeId) {

		Vehicle vehicle = vehicleService.findAVehcileByTypeId(typeId);
		vehicleService.updateOccupied(vehicle);
		if (vehicleService.checkTypeIsFull(typeId)) {
			vehicleTypeService.updateFull(typeId);
		}
		if (vehicle == null) {
			return "Error! No Avaliable Vehicle for that type";
		}
		return "Your Vehicle Id is: " + vehicle.getVehicleId();
	}

	// 检查某一个车型是否租满(用在更新某辆车的租用状态后)
	@PostMapping(path = "/checkIsFull", produces = "application/json;charset = utf-8")
	public boolean checkTypeIsFull(@RequestParam(value = "typeId") int typeId) {
		return vehicleService.checkTypeIsFull(typeId);
	}

//	public List<VehicleType> findAvaliableVehicleTypes(){
//		
//	}
	@PostMapping(path = "/findAVhicleByTypeId")
	public Vehicle findAVehicleByTypeId(@RequestParam(value = "typeId") int typeId) {
		return vehicleService.findAVehcileByTypeId(typeId);
	}
	
	//更新一辆车
//	@CrossOrigin(origins = "*", maxAge = 3600)
	@PostMapping(path = "/update")
	public String update(
			@RequestParam(value = "vehicleId") Short vehicleId,
			@RequestParam(value = "etc", required = false) String etc,
			@RequestParam(value = "recorder", required = false) String recorder,
			@RequestParam(value = "mount", required = false) String mount,
			@RequestParam(value = "umbrella", required = false) String umbrella,
			@RequestParam(value = "occupied", required = false) String occupied
//			Short vehicleId,
//			String etc,
//			String recorder,
//			String mount,
//			String umbrella,
//			String occupied
//			Vehicle vehicle
			){
		
//		System.out.println(vehicle);
//		Short vehicleId = vehicle.getVehicleId();
//		Boolean isETC = vehicle.getIsETC();
//		Boolean isRecorder = vehicle.getIsRecorder();
//		Boolean isMount = vehicle.getIsMount();
//		Boolean isUmbrella = vehicle.getIsUmbrella();
//		Boolean isOccupied = vehicle.getIsOccupied();
		
//		System.out.println(isETC);
//		System.out.println(isRecorder);
//		System.out.println(isMount);
//		System.out.println(isUmbrella);
//		System.out.println(isOccupied);
		
		System.out.println(vehicleId);
		System.out.println(etc);
		System.out.println(recorder);
		System.out.println(mount);
		System.out.println(umbrella);
		System.out.println(occupied);
		
		Boolean isETC = Boolean.parseBoolean(etc);
		Boolean isRecorder = Boolean.parseBoolean(recorder);
		Boolean isMount = Boolean.parseBoolean(mount);
		Boolean isUmbrella = Boolean.parseBoolean(umbrella);
		Boolean isOccupied = Boolean.parseBoolean(occupied);
		return vehicleService.update(vehicleId, isETC, isRecorder, isMount, isUmbrella, isOccupied);
		
	}
	
	//更新一类车
//	@CrossOrigin(origins = "*", maxAge = 3600)
	@PutMapping(path = "/updateType")
//	public String updateType(@RequestBody VehicleType type) {
	public String updateType(@RequestBody VehicleType type) {
		System.out.println(type);
		return vehicleTypeService.update(type);
	}
	//删除车辆
	@DeleteMapping(path = "/delete")
	public String delete(@RequestBody List<Short> vehicleIds) {
		//将List转化为String是因为 List的[]号无法被sql识别
		String vehicles = StringUtils.join(vehicleIds,",");
//		System.out.println(vehicle);
		System.out.println(vehicleIds);
		return vehicleService.delete(vehicles);
	}
	
	//删除车辆类型
	@DeleteMapping(path = "/deleteType")
	public String deleteType(@RequestBody List<Byte> vehicleTypes) {
		System.out.println(vehicleTypes);
		String types = StringUtils.join(vehicleTypes,",");
		System.out.print(types);
		return vehicleTypeService.delete(types);
	}
}
