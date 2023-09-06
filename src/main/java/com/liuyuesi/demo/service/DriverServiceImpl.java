package com.liuyuesi.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liuyuesi.demo.entity.Driver;
import com.liuyuesi.demo.entity.Order;
import com.liuyuesi.demo.mapper.DriverMapper;

@Service
public class DriverServiceImpl {

	@Autowired
	DriverMapper driverMapper;

	public String insert(Driver driver) {
//		System.out.println(driverMapper.insert(driver));
		driverMapper.insert(driver);
		return driver.getId();
	}

	public String update(Driver driver) {
		if(driverMapper.update(driver) == 1) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	public String delete(String ids) {
		if(driverMapper.delete(ids) >= 1) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@Transactional
	public List<Driver> getAll() {
		Cursor<Driver> drivers = driverMapper.getAll();
		List<Driver> driverList = new ArrayList<Driver>();
		for (Driver driver : drivers) {
			driverList.add(driver);
		}
		return driverList;
	}

	public Driver getDriverById(String id) {
		return driverMapper.getDriverById(id);
	}

	public List<Driver> getDriverByAT(Integer accessToken) {
		return driverMapper.getDriverByAT(accessToken);
	}

	public String deleteDriver(String id) {
		if (driverMapper.deleteDriver(id) == 1) {
			return "success";
		} else {
			return "false";
		}
	}
	
	public int updateFrontIdUrl(String id, String url) {
		return driverMapper.updateFrontIdUrl(id, url);
	}
	
	public int updateBackIdUrl(String id, String url) {
		return driverMapper.updateBackIdUrl(id, url);
	}
	
	public int updateLicenseUrl(String id, String url) {
		return driverMapper.updateLicenseUrl(id, url);
	}
	
	public int deleteFrontIdUrl(String id) {
		return driverMapper.deleteFrontIdUrl(id);
	}
	
	public int deleteBackIdUrl(String id) {
		return driverMapper.deleteBackIdUrl(id);
	}
	
	public int deleteLicenseUrl(String id) {
		return driverMapper.deleteLicenseUrl(id);
	}

}
