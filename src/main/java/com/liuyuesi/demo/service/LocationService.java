package com.liuyuesi.demo.service;

import java.util.List;

import com.liuyuesi.demo.entity.Location;

public interface LocationService {

	List<Location> findAll();
	
	Location getLocationById(int locationId);
	
	int insert(Location location);
	
	void deleteById(int locationId);
}
