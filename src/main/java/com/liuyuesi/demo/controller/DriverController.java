package com.liuyuesi.demo.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liuyuesi.demo.entity.Driver;
import com.liuyuesi.demo.service.DriverServiceImpl;

@RestController
@RequestMapping("/driver")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DriverController {

	@Autowired
	private DriverServiceImpl driverService;

	@RequestMapping("/uploadDriver")
	public String uploadDriver(@RequestBody String json) {
		// fastjson转成json对象
		JSONObject jsonObject = JSON.parseObject(json);
		// 在转成不同的实体类
//	    Driver driver = jsonObject.getObject("driver",Driver.class);
		String phone = jsonObject.getString("phone");
		String name = jsonObject.getString("name");
		String id = jsonObject.getString("id");
		String birth = jsonObject.getString("birth");
		String address = jsonObject.getString("address");
		String gender = jsonObject.getString("gender");
		String nationality = jsonObject.getString("nationality");
		// 准驾车辆 例如C1 C2
		String carClass = jsonObject.getString("carClass");
		Integer accessToken = jsonObject.getInteger("accessToken");
//		System.out.println(name);
//		System.out.println(id);
//		System.out.println(birth);
//		System.out.println(address);
//		System.out.println(gender);
//		System.out.println(nationality);
//		System.out.println(carClass);
//		System.out.println(accessToken);
//		System.out.println();

		Driver driver = new Driver();
		driver.setName(name);
		driver.setId(id);
		driver.setBirth(birth);
		driver.setAddress(address);
		driver.setGender(gender);
		driver.setNationality(nationality);
		driver.setCarClass(carClass);
		driver.setAccessToken(accessToken);
		driver.setPhone(phone);
		System.out.println(driver.getId());
		System.out.println(driver.getName());
		System.out.println(driver.getPhone());
		System.out.println(driver.getAddress());
		System.out.println(driver.getBirth());
		System.out.println(driver.getCarClass());
		System.out.println(driver.getNationality());
		System.out.println(driver.getAccessToken());
		System.out.println(driver.getGender());
		
		return driverService.insert(driver);
		// User user = jsonObject.getObject("user", User.class);
//	    UserAccount userAccount = jsonObject.getObject("userAccount", UserAccount.class);

	}

	@RequestMapping("/getAll")
	public List<Driver> getAll() {
		return driverService.getAll();
	}

	@RequestMapping("/getDriverById")
	public Driver getDriverById(@RequestParam(value = "id") String id) {
		return driverService.getDriverById(id);
	}

	@RequestMapping("/getDriversByAT")
	public List<Driver> getDriverByAT(@RequestParam(value = "accessToken") Integer accessToken) {
		return driverService.getDriverByAT(accessToken);
	}

	@RequestMapping("/deleteDriver")
	public String deleteDriver(@RequestParam(value = "id") String id) {
		return driverService.deleteDriver(id);
	}
	
	@PutMapping(path = "/update")
	public String update(@RequestBody Driver driver) {
		return driverService.update(driver);
	}
	
	@DeleteMapping(path = "/delete")
	public String delete(@RequestBody List<String> ids) {
		System.out.print(ids);
		String driverIds = StringUtils.join(ids,",");
		return driverService.delete(driverIds);

	}

}
