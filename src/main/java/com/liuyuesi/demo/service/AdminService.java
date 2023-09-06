package com.liuyuesi.demo.service;

import java.util.List;

import com.liuyuesi.demo.entity.Admin;

public interface AdminService {

	int insert(Admin admin);
	
	List<Admin> getAll();
	
	Admin getAdminById(int adminId);
	
//	List<Admin> getAdminByName(String userName);
	
	int deleteAdminById(int adminId);
	
//	int deleteAdminByName(String userName);
	
	int updateAdminUNById(int adminId,String userName);
	
	int updateAdminPWById(int adminId,String passWord);
	
//	int updateAdminUNByPS(int passWord);
	
//	int updateAdminPSByUN(int userName);
}
