package com.liuyuesi.demo.service;

import java.util.List;

import com.liuyuesi.demo.entity.User;

public interface UserService {

	//获取所有用户
	List<User> findAll();
	
	//插入新用户
	void insert(User user);
	
	//通过openId PK找到accessToken(自定义标识)
	Integer findAccessTokenByOpenId(String openId);
	
	//通过openId找到sessionKey
	String findSessionKeyByOpenId(String openId);
	
//	Integer findIdbyAccessToken(int accessToken);
	
	//通过openId找到用户
	User findUserByOpenId(String openId);
	
	//通过accessToken找到用户
	User findUserByAccessToken(int accessToken);
	
//	String createThirdSessionCache(User user);
	
	//通过前端的openId和sessionKey将其存储并构建以及返回AccessToken
	Integer login(String openId,String sessionKey);
	
	//检查AT是否为独特的
	boolean isATDuplicate(int accessToken);
	
	//插入电话号码
	String updatePhone(String phone, String accessToken);
	
}
