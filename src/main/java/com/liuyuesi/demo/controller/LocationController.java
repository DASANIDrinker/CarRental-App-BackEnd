package com.liuyuesi.demo.controller;

import java.util.List;

import org.apache.ibatis.annotations.Options;
//import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liuyuesi.demo.entity.Location;
import com.liuyuesi.demo.service.LocationServiceImpl;
import org.apache.commons.lang3.StringUtils;
@RestController
@RequestMapping("/location")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LocationController {

	@Autowired
	private LocationServiceImpl locationService;
	
	@GetMapping("/getAll")
	public List<Location> findAll(){
		return locationService.findAll();
	}
	
	@GetMapping("/getLocationById")
	public Location getLocationById(@RequestParam("locationId") int locationId) {
		return locationService.getLocationById(locationId);
	}
	
	@PostMapping("/insert")
	public int insert(@RequestBody Location location) {
		return locationService.insert(location);
	}
	
	@DeleteMapping("/deleteById")
	public void deleteById(@RequestParam("locationId") int locationId) {
		locationService.deleteById(locationId);
	}
	
	@PutMapping("/update")
	public String update(@RequestBody Location location) {
		return locationService.update(location);
	}
	
	@DeleteMapping("/delete")
	public String delete(@RequestBody List<Integer> locationIds) {
		String ids = StringUtils.join(locationIds,",");
		return locationService.delete(ids);
	}
}
