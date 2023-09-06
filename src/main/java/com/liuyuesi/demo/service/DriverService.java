package com.liuyuesi.demo.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.liuyuesi.demo.entity.Driver;

public interface DriverService {

	int insert(Driver driver);
	
	List<Driver> getAll();
	
	Driver getDriverById(String id);
	
	List<Driver> getDriverByAT(Integer accessToken);
	
	String deleteDriver(String id);
	
	int updateFrontIdUrl(String id, String url);
	
	int updateBackIdUrl(String id);
	
	int updateLicenseUrl(String id);
	
	int deleteFrontIdUrl(String id);
	
	int deleteBackIdUrl(String id);
	
	int deleteLicenseUrl(String id);
}
