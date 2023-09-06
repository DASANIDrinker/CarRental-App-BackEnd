package com.liuyuesi.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liuyuesi.demo.entity.Location;
import com.liuyuesi.demo.mapper.LocationMapper;

@Service
public class LocationServiceImpl {

	@Autowired
	private LocationMapper locationMapper;
	
//	@Autowired
//	private Location location;
	
	public List<Location> findAll(){
		return locationMapper.findAll();
	}
	
	public Location getLocationById(int locationId) {
		return locationMapper.getLocationById(locationId);
	}
	
	public int insert(Location location) {
		locationMapper.insert(location);
		//因为用了options的useGeneratedKeys所以可以通过getLocationId返回新增的PK
		return location.getLocationId();
	}
	
	public void deleteById(int locationId) {
		
	}
	
	public String delete(String locationIds) {
		if(locationMapper.delete(locationIds) >= 1) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	public String update(Location location) {
		if(locationMapper.update(location) == 1) {
			return "success";
		}else {
			return "false";
		}
	}
}
