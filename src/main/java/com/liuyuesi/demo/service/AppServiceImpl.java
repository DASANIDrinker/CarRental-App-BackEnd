package com.liuyuesi.demo.service;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
public class AppServiceImpl {
	
	//本函数提供基于小程序的身份证 OCR 识别 通过身份证照片判断持证人的姓名 年龄 性别 身份证号等
			public String ocrIdCard(String tempFilePath, String accessToken,int count,int isFront) throws ClientProtocolException, IOException {
				
				//如果尝试了两次 还是接口繁忙 那么返回busy
				if(count == 3) {
					return "busy";
				}else {
					String url = "https://api.weixin.qq.com/cv/ocr/idcard";
			        url += "?type=photo";
			        url += "&img_url=" + tempFilePath; //要上传的图片的路径
			        url += "&access_token=" + accessToken;	//调用身份证识别接口的接口凭证

			        String res = null;
			        CloseableHttpClient httpClient = HttpClientBuilder.create().build();	 // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
			        // DefaultHttpClient();
			        HttpPost httpPost = new HttpPost(url);//不同于以往 这里要求Post请求
//			        HttpGet httpget = new HttpGet(url);   					//创建GET请求
			        CloseableHttpResponse response = null;					//响应模型
			        
			        // 配置信息
			        RequestConfig requestConfig = RequestConfig.custom()          // 设置连接超时时间(单位毫秒)
			                .setConnectTimeout(5000)                    // 设置请求超时时间(单位毫秒)
			                .setConnectionRequestTimeout(5000)             // socket读写超时时间(单位毫秒)
			                .setSocketTimeout(5000)                    // 设置是否允许重定向(默认为true)
			                .setRedirectsEnabled(false).build();           
			        httpPost.setConfig(requestConfig);                         // 将上面的配置信息 运用到这个Get请求里
			        response = httpClient.execute(httpPost);                   // 由客户端执行(发送)Get请求
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
			        System.out.println(jo);
			        String errCode = jo.getString("errcode");
			        String type = jo.getString("type");
			        int idCardFront = 0;
			        //判断上传的身份证的正反面
			        if(type == "Back") {
			        	idCardFront = 0;
			        }else if(type == "Front") {
			        	idCardFront = 1;
			        }
			        //如果上传的身份证正反面与该位置需要的身份证正反面匹配 并且上传成功
			        if(errCode == "0" && idCardFront == isFront) {
			        	return "success";
			        //如果上传成功但是 上传的身份证正反面和该位置需要的身份证正反面不匹配
			        }else if(errCode == "0" && idCardFront != isFront) {
			        	return "need"+type;
			        //如果接口繁忙
			        }else if(errCode == "-1") {
			        	return ocrIdCard(tempFilePath,accessToken,count+1,isFront);
			        //如果图片格式不对或者url不对
			        }else if(errCode == "101000"||errCode == "101001"|| errCode == "101002") {
			        	return "fail";
			        //其他情况
			        }else {
			        	return "fail";
			        }
				}
			}
}
