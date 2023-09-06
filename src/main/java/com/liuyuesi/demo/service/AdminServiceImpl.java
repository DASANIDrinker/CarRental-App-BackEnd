package com.liuyuesi.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liuyuesi.demo.entity.Admin;
import com.liuyuesi.demo.mapper.AdminMapper;

@Service
public class AdminServiceImpl {
	
	@Autowired
	private AdminMapper adminMapper;
	
	public int insert(Admin admin) {
		adminMapper.insert(admin);
		return admin.getAdminId();
	}
	
	public List<Admin> getAll(){
		return adminMapper.getAll();
	}
	
	public Admin getAdminById(int adminId){
		return adminMapper.getAdminById(adminId);
	}
	
//	List<Admin> getAdminByName(String userName);
	
	public int deleteAdminById(int adminId) {
		return adminMapper.deleteAdminById(adminId);
	}
	
//	int deleteAdminByName(String userName);
	
	public int updateAdminUNById(int adminId,String userName) {
		return adminMapper.updateAdminUNById(adminId,userName);
	}
	
	public int updateAdminPWById(int adminId,String passWord) {
		return adminMapper.updateAdminPWById(adminId,passWord);
	}
	
	public String update(Admin admin) {
		if(adminMapper.update(admin) == 1) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	public String delete(String adminIds) {
		if(adminMapper.delete(adminIds) >= 1) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	public String check(Admin admin) {
//		int count = adminMapper.check(admin);
//		System.out.println(count);
		if(adminMapper.check(admin) == 1) {
			return "success";
		}else {
			return "fail";
		}
	}
}
