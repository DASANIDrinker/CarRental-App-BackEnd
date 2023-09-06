package com.liuyuesi.demo.service;

import com.liuyuesi.demo.entity.VehicleTypeImage;
import java.util.List;


public interface VehicleTypeImageService {

	 public VehicleTypeImage findById(byte typeId);
	 
	 public int insert(VehicleTypeImage vehicleTypeImage);
	 
	 public List<VehicleTypeImage> findAll();
}
