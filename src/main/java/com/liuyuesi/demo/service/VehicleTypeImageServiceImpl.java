package com.liuyuesi.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liuyuesi.demo.entity.VehicleTypeImage;
import com.liuyuesi.demo.mapper.VehicleTypeImageMapper;

@Service
public class VehicleTypeImageServiceImpl implements VehicleTypeImageService{

	@Autowired
	private VehicleTypeImageMapper imageMapper;
	
	public VehicleTypeImage findById(byte typeId) {
		return imageMapper.findById(typeId);
	}
	
	public int insert(VehicleTypeImage vehicleTypeImage) {
		return imageMapper.insert(vehicleTypeImage);
	}
	
	public List<VehicleTypeImage> findAll(){
		return imageMapper.findAll();
	}
	
}
