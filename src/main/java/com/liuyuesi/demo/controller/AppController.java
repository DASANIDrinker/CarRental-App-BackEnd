package com.liuyuesi.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liuyuesi.demo.service.AppServiceImpl;

@RestController
@RequestMapping("/app")
public class AppController {

	@Autowired
	private AppServiceImpl appService;

	// 此access token是接口调用凭证 不是用户验证的access token
	// 此函数最后会调用ocr接口函数 来识别身份证上面的信息
	@GetMapping("/getAccessToken")
//		public List<String> getAccessToken(@RequestParam(name = "encodedTempFilePath") String tempFilePath,
//				@RequestParam(name = "isFront") int isFront) throws ParseException, IOException {
	public String getAccessToken() throws ParseException, IOException {
//			System.out.println(tempFilePath);
		String url = "https://api.weixin.qq.com/cgi-bin/token";
		url += "?grant_type=client_credential";
		url += "&appid=";// 自己的appid
		url += "&secret=";// appSecret

		String res = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build(); // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		// DefaultHttpClient();
		HttpGet httpget = new HttpGet(url); // 创建GET请求
		CloseableHttpResponse response = null; // 响应模型

		// 配置信息
		RequestConfig requestConfig = RequestConfig.custom() // 设置连接超时时间(单位毫秒)
				.setConnectTimeout(5000) // 设置请求超时时间(单位毫秒)
				.setConnectionRequestTimeout(5000) // socket读写超时时间(单位毫秒)
				.setSocketTimeout(5000) // 设置是否允许重定向(默认为true)
				.setRedirectsEnabled(false).build();
		httpget.setConfig(requestConfig); // 将上面的配置信息 运用到这个Get请求里
		response = httpClient.execute(httpget); // 由客户端执行(发送)Get请求
		HttpEntity responseEntity = response.getEntity(); // 从响应模型中获取响应实体
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
		System.out.println(jo);
		String accessToken = jo.getString("access_token");
//	        String sessionKey = jo.getString("session_key");
		System.out.println(accessToken);
//	        Integer accessToken = userService.login(openid,sessionKey);

		// 1代表尝试的次数 用来递归ocrIdCard函数 实现多次调用函数
		// 并且当该函数调用次数达到一定次数时 停止并返回
//	        String msg = appService.ocrIdCard(tempFilePath, accessToken,1,isFront);
//			List<String> result = new ArrayList<String>();
//			result.add(accessToken);
//			result.add(msg);

		return accessToken;
	}

	@RequestMapping("/getPhoneNumber")
	public List<String> getPhoneNumber(@RequestParam(name = "code") String code,
			@RequestParam(name = "accessToken") String accessToken) throws IOException {

		String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber";
		url += "?access_token=" + accessToken;
		url += "&code=" + code;

		String res = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build(); // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		// DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url); // 创建POST请求
//	        String json = String.format("{\"code\":\"$s\"}",code);
		String json = "{\"code\":\"" + code + "\"}";// 将code作为post Body的一部分
		System.out.println(json);
		StringEntity entity = new StringEntity(json);// 将string转化为entity
		httpPost.setEntity(entity);// 设置Body
//	        httpPost.setHeader("Accept","application/json");
		CloseableHttpResponse response = null; // 响应模型

		// 配置信息
		RequestConfig requestConfig = RequestConfig.custom() // 设置连接超时时间(单位毫秒)
				.setConnectTimeout(5000) // 设置请求超时时间(单位毫秒)
				.setConnectionRequestTimeout(5000) // socket读写超时时间(单位毫秒)
				.setSocketTimeout(5000) // 设置是否允许重定向(默认为true)
				.setRedirectsEnabled(false).build();
		httpPost.setConfig(requestConfig); // 将上面的配置信息 运用到这个Get请求里
		response = httpClient.execute(httpPost); // 由客户端执行(发送)Get请求
		HttpEntity responseEntity = response.getEntity(); // 从响应模型中获取响应实体
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
		// 将返回的object转为JSONObject
		JSONObject jo = JSON.parseObject(res);
		System.out.println(jo);
		// 提取错误提示
		int errCode = jo.getIntValue("errcode");
		List<String> result = new ArrayList<String>();
		if (errCode == 0) {
			// 提取JSONObject里面的sub Object
			JSONObject phoneInfo = jo.getJSONObject("phone_info");
			// 再提取sub Object里面的电话号码和地区编号
			String phone = phoneInfo.getString("purePhoneNumber");
			String countryCode = phoneInfo.getString("countryCode");
			System.out.println(phone);

			// 将返回的数据存在List里面

			result.add(phone);
			result.add(countryCode);
		} else {
			result.add("fail");
		}

		return result;
	}

	// 本函数提供基于小程序的身份证 OCR 识别 通过身份证照片判断持证人的姓名 年龄 性别 身份证号等
	@RequestMapping("/licenseOCR")
	public String ocrIdCard(@RequestParam(name = "TempFilePath") String tempFilePath,
			@RequestParam(name = "accessToken") String accessToken) throws ClientProtocolException, IOException {

		// 如果尝试了两次 还是接口繁忙 那么返回busy
//			if(count == 3) {
//				return "busy";
//			}else {

		// https://api.weixin.qq.com/cv/ocr/driving?type=MODE&img_url=ENCODE_URL&access_token=ACCESS_TOCKEN

		System.out.println(tempFilePath);
		String url = "https://api.weixin.qq.com/cv/ocr/idcard";
		url += "&img_url=" + tempFilePath; // 要上传的图片的路径
		url += "&access_token=" + accessToken; // 调用身份证识别接口的接口凭证

		String res = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build(); // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		// DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);// 不同于以往 这里要求Post请求
//		        HttpGet httpget = new HttpGet(url);   					//创建GET请求
		CloseableHttpResponse response = null; // 响应模型

		// 配置信息
		RequestConfig requestConfig = RequestConfig.custom() // 设置连接超时时间(单位毫秒)
				.setConnectTimeout(5000) // 设置请求超时时间(单位毫秒)
				.setConnectionRequestTimeout(5000) // socket读写超时时间(单位毫秒)
				.setSocketTimeout(5000) // 设置是否允许重定向(默认为true)
				.setRedirectsEnabled(false).build();
		httpPost.setConfig(requestConfig); // 将上面的配置信息 运用到这个Get请求里
		response = httpClient.execute(httpPost); // 由客户端执行(发送)Get请求
		HttpEntity responseEntity = response.getEntity(); // 从响应模型中获取响应实体
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
		System.out.println(jo);
		String errCode = jo.getString("errcode");
//		        String type = jo.getString("type");
//		        int idCardFront = 0;
//		        //判断上传的身份证的正反面
//		        if(type == "Back") {
//		        	idCardFront = 0;
//		        }else if(type == "Front") {
//		        	idCardFront = 1;
//		        }
//		        //如果上传的身份证正反面与该位置需要的身份证正反面匹配 并且上传成功
//		        if(errCode == "0" && idCardFront == isFront) {
//		        	return "success";
//		        //如果上传成功但是 上传的身份证正反面和该位置需要的身份证正反面不匹配
//		        }else if(errCode == "0" && idCardFront != isFront) {
//		        	return "need"+type;
//		        //如果接口繁忙
//		        }else if(errCode == "-1") {
//		        	return ocrIdCard(tempFilePath,accessToken,count+1,isFront);
//		        //如果图片格式不对或者url不对
//		        }else if(errCode == "101000"||errCode == "101001"|| errCode == "101002") {
//		        	return "fail";
//		        //其他情况
//		        }else {
//		        	return "fail";
//		        }
		return "lol!";
//			}
	}
	
	
	

}
