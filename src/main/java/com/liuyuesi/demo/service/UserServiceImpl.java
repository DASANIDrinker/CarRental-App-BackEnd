package com.liuyuesi.demo.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liuyuesi.demo.entity.User;
import com.liuyuesi.demo.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	RedisTemplate<String,Integer> redisTemplate = new RedisTemplate<>();
	
	TimeUnit day = TimeUnit.DAYS;
	
	//获取所有用户
	@Transactional
	public List<User> findAll(){
		List<User> users = new ArrayList<User>();
		Cursor<User> all = userMapper.findAll();
		for(User user:all) {
			users.add(user);
		}
		return users;
	}
	
	//插入新用户
	public void insert(User user){
		userMapper.insert(user);
	}
	
	//通过openId PK找到accessToken(自定义标识)
	public Integer findAccessTokenByOpenId(String openId) {
		return userMapper.findAccessTokenByOpenId(openId);
	}
	
	//通过openId找到sessionKey
	public String findSessionKeyByOpenId(String openId) {
		return userMapper.findSessionKeyByOpenId(openId);
	}
	
	//通过openId找到用户
	public User findUserByOpenId(String openId){
		return userMapper.findUserByOpenId(openId);
	}
	
	//通过accessToken找到用户
	public User findUserByAccessToken(int accessToken){
		return userMapper.findUserByAccessToken(accessToken);
	}
	
//	@Cacheable(value = "AccessToken", key = "#user.accessToken")
//	public String createThirdSessionCache(User user) {
//		String thirdSession = user.getOpenId();
//		return thirdSession;
//	}
	
	//通过前端的openId和sessionKey将其存储并构建以及返回AccessToken
	public Integer login(String openId,String sessionKey){
		Integer value = redisTemplate.opsForValue().get(openId);
		
		//如果没有获取到value也就是accessToken 证明没有
		//redis数据库没有
		if(value == null){
			System.out.println("当redis没有值时  "+value);
			//通过aT获取user实例
			User user = userMapper.findUserByOpenId(openId);
			//redis数据库没有并且MYSQL数据库里也没有该user
			if(user == null) {
				System.out.println("当MYSQL和redis都没有值时!");
				User newUser = new User();
				newUser.setOpenId(openId);
				newUser.setSessionKey(sessionKey);
				SecureRandom random = null;
				try {
					random = SecureRandom.getInstance("SHA1PRNG");
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int randomNum;
				do {
					randomNum = random.nextInt(2000000000);
				}
				while(!isATDuplicate(randomNum));
				
				newUser.setAccessToken(randomNum);
				//向数据库插入该user
				userMapper.insert(newUser);
				//System.out.println("New user's openId is: " + userMapper.insert(newUser));
				//创建accessToken的redis cache
				redisTemplate.opsForValue().set(openId, randomNum,2,day);
				return newUser.getAccessToken();
			//redis数据库没有但是MYSQL数据库有该用户
			}else {
				System.out.println("当MYSQL有值 redis没值时!");
				//创建accessToken的redis cache
//				this.createThirdSessionCache(user);
				redisTemplate.opsForValue().set(openId, user.getAccessToken(),2,day);
				return user.getAccessToken();
			}
		//如果获取到缓存的accessToken 就直接返回
		//redis数据库有 MYSQL肯定也有
		}else {
			System.out.println("当redis有值时!");
			return value;
		}
	}
	
	//检查AccessToken是否独特
	public boolean isATDuplicate(int accessToken) {
		if(userMapper.isATDuplicate(accessToken) == 0) {
			return true;
		}else {
			return false;
		}
	}
	
	//插入电话号码
	public String updatePhone(String phone, String accessToken) {
		if(userMapper.updatePhone(phone,accessToken) == 1) {
			return "success";
		}else {
			return "fail";
		}
	}
}
