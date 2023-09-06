package com.liuyuesi.demo.controller;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liuyuesi.demo.entity.User;
import com.liuyuesi.demo.service.UserServiceImpl;


//@RestController 等同于ResponseBody加上Controller
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

//	@Autowired
//	RedisTemplate<Integer,String> redisTemplate = new RedisTemplate<>();
	
	@Autowired
	UserServiceImpl userService;
	
//	@Autowired
//	UserRedisServiceImpl userRedisService;
	
	
	//收到code向微信官方接口发送请求获取open id和session key并返回accessToken
//	@ResponseBody
	@PostMapping("/getOpenId")
	public Integer getUserInfo(@RequestParam(name = "code") String code) throws Exception {
	
        System.out.println("code:  " + code);
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        url += "?appid=";//自己的appid
        url += "&secret=";//自己的appSecret
        url += "&js_code=" + code;
        url += "&grant_type=authorization_code";
        //url += "&connect_redirect=1";
        String res = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();	 // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        // DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);   					//创建GET请求
        CloseableHttpResponse response = null;					//响应模型
        
        // 配置信息
        RequestConfig requestConfig = RequestConfig.custom()          // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(5000)                    // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)             // socket读写超时时间(单位毫秒)
                .setSocketTimeout(5000)                    // 设置是否允许重定向(默认为true)
                .setRedirectsEnabled(false).build();           
        httpget.setConfig(requestConfig);                         // 将上面的配置信息 运用到这个Get请求里
        response = httpClient.execute(httpget);                   // 由客户端执行(发送)Get请求
        HttpEntity responseEntity = response.getEntity();		// 从响应模型中获取响应实体
        System.out.println("响应状态为:" + response.getStatusLine());
        if (responseEntity != null) {
            res = EntityUtils.toString(responseEntity);
            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
            System.out.println("响应内容为:" + res);
        }
        // 释放资源
        if (httpClient != null) {
            httpClient.close();
        }
        if (response != null) {
            response.close();
        }
        JSONObject jo = JSON.parseObject(res);
        System.out.println(jo.toString());
        String openid = jo.getString("openid");
        String sessionKey = jo.getString("session_key");
        
        Integer accessToken = userService.login(openid,sessionKey);
      
        System.out.println("session_key  " + sessionKey);
        System.out.println("openid  " + openid);
        System.out.println("AccessToken  " + accessToken);
        
        return accessToken;
    }
	
//	@PostMapping("/getPhoneNumber")
//	public Integer getUserPhoneNumber(@RequestParam(name = "code") ) {
//		
//		
//	}
	
	@PostMapping("/updatePhone")
	public String updatePhone(@RequestParam(value = "phone") String phone,
			@RequestParam(value = "accessToken") String accessToken) {
		System.out.println(phone);
		System.out.println(accessToken);
		return userService.updatePhone(phone, accessToken);
		
	}
	
	@RequestMapping(path = "/getAll")
	public List<User> getAll(){
//		System.out.println("111");
		return userService.findAll();
	}
	
	@RequestMapping("/accessToken")
	public User findAccessToken(@RequestParam(name = "accessToken") int accessToken) {
		return userService.findUserByAccessToken(accessToken);
	}

	
	
	
}
