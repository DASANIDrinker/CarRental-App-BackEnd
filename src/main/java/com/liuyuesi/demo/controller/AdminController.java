package com.liuyuesi.demo.controller;
import org.apache.commons.lang3.StringUtils; 
import java.util.List;

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

import com.alibaba.fastjson.JSONObject;
import com.liuyuesi.demo.entity.Admin;
import com.liuyuesi.demo.service.AdminServiceImpl;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {
	
	@Autowired
	private AdminServiceImpl adminService;
	
	@RequestMapping("/createAdmin")
	public Admin createAdmin(@RequestBody JSONObject json) {
		
//		short adminId = json.getShort("adminId");
		String userName = json.getString("userName");
		String passWord = json.getString("passWord");
		
		Admin admin = new Admin();
//		admin.setAdminId(adminId);
		admin.setUserName(userName);
		admin.setPassWord(passWord);
		
		admin.setAdminId(adminService.insert(admin));
		
		return admin;
		
	}
	
	@GetMapping("/getAll")
	public List<Admin> getAll(){
		return adminService.getAll();
	}
	
	@GetMapping("/getAdminById")
	public Admin getAdminById(@RequestParam int adminId){
		return adminService.getAdminById(adminId);	
	}
	
//	public List<Admin> getAdminByName(@RequestParam String userName){
//		return adminService.getAdminByName(userName);
//	}
	
	@RequestMapping("/deleteAdminById")
	public String deleteAdminById(@RequestParam int adminId) {
		if(adminService.deleteAdminById(adminId) > 0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@RequestMapping("/updateAdminUNById")
	public String updateAdminUNById(@RequestParam(name = "adminId") int adminId,
			@RequestParam(name = "userName") String userName) {
		if(adminService.updateAdminUNById(adminId,userName) > 0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@RequestMapping("/updateAdminPWById")
	public String updateAdminPWById(@RequestParam(name = "adminId") int adminId,
			@RequestParam(name = "passWord") String passWord) {
		if(adminService.updateAdminPWById(adminId,passWord) > 0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@PutMapping("/update")
	public String update(@RequestBody Admin admin) {
		return adminService.update(admin);
	}
	
	@DeleteMapping("/delete")
	public String delete(@RequestBody List<Integer> adminIds) {
		String ids = StringUtils.join(adminIds,",");
		return adminService.delete(ids);
	}

	//检查Admin的用户名和密码是否存在
	@PostMapping("/check")
	public String check(@RequestBody Admin admin) {
		return adminService.check(admin);
	}
}
