package com.liuyuesi.demo.controller;

import java.io.File;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.ServerResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liuyuesi.demo.entity.VehicleTypeImage;
import com.liuyuesi.demo.service.DriverServiceImpl;
import com.liuyuesi.demo.service.VehicleTypeImageServiceImpl;
import com.liuyuesi.demo.service.VehicleTypeServiceImpl;
import org.springframework.web.util.UriUtils;

@RestController
@RequestMapping("/image")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ImageController {

	@Autowired
	private VehicleTypeImageServiceImpl imageService;

	@Autowired
	private DriverServiceImpl driverService;
	
	@Autowired
	private VehicleTypeServiceImpl vehicleTypeService;
	
	// 身份信息文件保存在磁盘的地址
	@Value("${file.save.path.ids}")
	String fileSavePathId;

	@Value("${file.save.path.vehicles}")
	String fileSavePathVehicle;
	
	//上传驾驶员身份信息图片
	@RequestMapping("uploadIds")
	public String uploadImg(
			@RequestParam("filePath") String filePath,
			@RequestParam("id") String id,
			@RequestParam("label") Integer label) throws IOException {
	
		
		//如果是身份证正面照
		if(label == 1) {
			driverService.updateFrontIdUrl(id,filePath);
		}
		//如果是身份证反面照
		else if(label == 2) {
			driverService.updateBackIdUrl(id,filePath);
		}
		//如果是驾照照片
		else if(label == 3) {
			driverService.updateLicenseUrl(id,filePath);
		}
		
		return filePath;
	};
	
	//上传车辆类型信息图片
	@RequestMapping("uploadVehicles")
	public String uploadImgVehicle(
			HttpServletRequest request, 
			@RequestParam("file") MultipartFile myfile,
			@RequestParam("vehicleTypeId") byte vehicleTypeId) throws IOException {
		

		System.out.println(vehicleTypeId);
		//可以通过RequestParam获得formData里的数据 
		//也可以用request.getParameter函数来获得
		
		File fir = new File(fileSavePathVehicle);
		// 不存在则创建文件夹
		if (!fir.exists()) {
			fir.mkdirs();
		}
		
		// 文件的后缀名
		String suffix = myfile.getOriginalFilename().substring(myfile.getOriginalFilename().lastIndexOf("."));
		// 新文件名字 为了防止重复加上UUID
		String fileName = myfile.getOriginalFilename();
		System.out.println("filesavepath: " + fileSavePathVehicle + "newFileName: " + fileName);
		
		// 新的文件路径
		File newFile = new File(fileSavePathVehicle + fileName);
		
		// 把文件写入新的File文件
		myfile.transferTo(newFile);
		
		// url路径=http + :// + server名字 + port端口 + /imges/ + newFileName
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/images/"
				+ fileName;
		System.out.println(url);
		
		vehicleTypeService.updateTypeUrl(vehicleTypeId,fileName);
		
		return url;
	}

	@GetMapping("/downloadVehicleIcon")
	public List<String> downloadVehicleIcon() {
		List<VehicleTypeImage> vehicleImageList = imageService.findAll();
		List<String> result = new ArrayList<String>();
		for (VehicleTypeImage image : vehicleImageList) {
			String imageSource = image.getImageUrl().concat(image.getImageName());
			result.add(imageSource);
		}
		return result;
	}
	
	//从文件中删除汽车类型图片，并删除相关数据库信息
    @GetMapping("/deleteVehicleTypeImage")
    public String delFile(@RequestParam("vehicleTypeId") byte vehicleTypeId,@RequestParam("imageName") String imageName) {
    	
    	System.out.println(vehicleTypeId);
    	System.out.println(imageName);
    	
    	//通过imageName来获取图片的名字
        String img_path = fileSavePathVehicle + imageName;

        File file = new File(img_path);
        //数据库中删除数据
        int i = vehicleTypeService.deleteVehicleTypeImage(vehicleTypeId);
        if(i>0){
            if (file.exists()) {//文件是否存在
                if (file.delete()) {//存在就删了
                    return "删除成功";
                } else {
                    return "文件删除失败";
                }
            }else {
                return "文件不存在";
            }
        }else {
            return "数据删除失败";
        }
    }
    
    
    //从文件中删除身份证正面图片，并删除相关数据库信息
    @GetMapping("/deleteIdFrontImage")
    public String delIdFront(
    		@RequestParam("id") String id,
    		@RequestParam("idFrontUrl") String idFrontUrl) {
    	
    	System.out.println(id);
    	System.out.println(idFrontUrl);
    	
    	//通过imageName来获取图片的名字
        String img_path = fileSavePathId + idFrontUrl;

        File file = new File(img_path);
        //数据库中删除数据
        int i = driverService.deleteFrontIdUrl(id);
        if(i>0){
            if (file.exists()) {//文件是否存在
                if (file.delete()) {//存在就删了
                    return "删除成功";
                } else {
                    return "文件删除失败";
                }
            }else {
                return "文件不存在";
            }
        }else {
            return "数据删除失败";
        }
    }
    
    //从文件中删除身份证背面图片，并删除相关数据库信息
    @GetMapping("/deleteIdBackImage")
    public String delIdBack(
    		@RequestParam("id") String id,
    		@RequestParam("idBackUrl") String idBackUrl) {
    	
    	System.out.println(id);
    	System.out.println(idBackUrl);
    	
    	//通过imageName来获取图片的名字
        String img_path = fileSavePathId + idBackUrl;

        File file = new File(img_path);
        //数据库中删除数据
        int i = driverService.deleteBackIdUrl(id);
        if(i>0){
            if (file.exists()) {//文件是否存在
                if (file.delete()) {//存在就删了
                    return "删除成功";
                } else {
                    return "文件删除失败";
                }
            }else {
                return "文件不存在";
            }
        }else {
            return "数据删除失败";
        }
    }
    
  //从文件中删除驾驶证图片，并删除相关数据库信息
    @GetMapping("/deleteLicenseImage")
    public String delLicense(
    		@RequestParam("id") String id,
    		@RequestParam("licenseUrl") String licenseUrl) {
    	
    	System.out.println(id);
    	System.out.println(licenseUrl);
    	
    	//通过imageName来获取图片的名字
        String img_path = fileSavePathId + licenseUrl;

        File file = new File(img_path);
        //数据库中删除数据
        int i = driverService.deleteLicenseUrl(id);
        if(i>0){
            if (file.exists()) {//文件是否存在
                if (file.delete()) {//存在就删了
                    return "删除成功";
                } else {
                    return "文件删除失败";
                }
            }else {
                return "文件不存在";
            }
        }else {
            return "数据删除失败";
        }
    }
    
    
    
    @RequestMapping("/IdOCR")
	public JSONObject IdOCR(
			HttpServletRequest request, 
			@RequestParam("file") MultipartFile myfile) throws IOException {
    	
    	//先向微信后端 获取accessToken
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

		//可以通过RequestParam获得formData里的数据 
		//也可以用request.getParameter函数来获得
		
//		String tempId = request.getParameter("id");
//		System.out.println("A:"+id);
//		System.out.println("B:"+tempId);
//		System.out.println(myfile);
//		System.out.println(accessToken);
		File fir = new File(fileSavePathId);
		// 不存在则创建文件夹
		if (!fir.exists()) {
			fir.mkdirs();
		}
		// 文件的后缀名
		String suffix = myfile.getOriginalFilename().substring(myfile.getOriginalFilename().lastIndexOf("."));
		// 新文件名字 为了防止重复加上UUID
		String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
		System.out.println("filesavepath: " + fileSavePathId + "newFileName: " + newFileName);
//	     System.out.println(fileSavePath);
		// 新的文件路径
		File newFile = new File(fileSavePathId + newFileName);
		// 把文件写入新的File文件
		myfile.transferTo(newFile);
		
		String IdUrl = "https://xxx/api/img/ID/"
				+ newFileName;
//		IdUrl = UriUtils.encode(IdUrl, StandardCharsets.UTF_8);
		String OCRUrl = "https://api.weixin.qq.com/cv/ocr/idcard?type=photo";
		OCRUrl += "&img_url=" + IdUrl; // 要上传的图片的路径
		OCRUrl += "&access_token=" + accessToken; // 调用身份证识别接口的接口凭证
		String resOCR = null;
		CloseableHttpClient httpClientOCR = HttpClientBuilder.create().build(); // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		// DefaultHttpClient();
		HttpPost httpPostOCR = new HttpPost(OCRUrl);// 不同于以往 这里要求Post请求
//		        HttpGet httpget = new HttpGet(url);   					//创建GET请求
		CloseableHttpResponse responseOCR = null; // 响应模型

		// 配置信息
		RequestConfig requestConfigOCR = RequestConfig.custom() // 设置连接超时时间(单位毫秒)
				.setConnectTimeout(5000) // 设置请求超时时间(单位毫秒)
				.setConnectionRequestTimeout(5000) // socket读写超时时间(单位毫秒)
				.setSocketTimeout(5000) // 设置是否允许重定向(默认为true)
				.setRedirectsEnabled(false).build();
		httpPostOCR.setConfig(requestConfigOCR); // 将上面的配置信息 运用到这个Get请求里
		responseOCR = httpClientOCR.execute(httpPostOCR); // 由客户端执行(发送)Get请求
		HttpEntity responseEntityOCR = responseOCR.getEntity(); // 从响应模型中获取响应实体
		System.out.println("响应状态为:" + responseOCR.getStatusLine());
		if (responseEntityOCR != null) {
			resOCR = EntityUtils.toString(responseEntityOCR);
			System.out.println("响应内容长度为:" + responseEntityOCR.getContentLength());
			System.out.println("响应内容为:" + resOCR);
		}
		// 释放资源
		if (httpClientOCR != null) {
			httpClientOCR.close();
		}
		if (responseOCR != null) {
			responseOCR.close();
		}
		JSONObject joOCR = JSON.parseObject(resOCR);
		joOCR.put("IdUrl", newFileName);
		System.out.println(joOCR);
		System.out.println(IdUrl);
		
		return joOCR;
	};
	
	
	@RequestMapping("/licenseOCR")
	public JSONObject licenseOCR(
			HttpServletRequest request, 
			@RequestParam("file") MultipartFile myfile) throws IOException {
		

		//先向微信后端 获取accessToken
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
//		
//		
//		//可以通过RequestParam获得formData里的数据 
//		//也可以用request.getParameter函数来获得
//		
////		String tempId = request.getParameter("id");
////		System.out.println("A:"+id);
////		System.out.println("B:"+tempId);
////		System.out.println(myfile);
//		System.out.println(accessToken);
		
		
		File fir = new File(fileSavePathId);
		// 不存在则创建文件夹
		if (!fir.exists()) {
			fir.mkdirs();
		}
		// 文件的后缀名
		String suffix = myfile.getOriginalFilename().substring(myfile.getOriginalFilename().lastIndexOf("."));
		// 新文件名字 为了防止重复加上UUID
		String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
		System.out.println("filesavepath: " + fileSavePathId + "newFileName: " + newFileName);
//	     System.out.println(fileSavePath);
		// 新的文件路径
		File newFile = new File(fileSavePathId + newFileName);
		// 把文件写入新的File文件
		myfile.transferTo(newFile);
		
		String IdUrl = "https://xxx/api/img/ID/"
				+ newFileName;
//		IdUrl = UriUtils.encode(IdUrl, StandardCharsets.UTF_8);
		String OCRUrl = "https://api.weixin.qq.com/cv/ocr/drivinglicense?type=photo";
		OCRUrl += "&img_url=" + IdUrl; // 要上传的图片的路径
		OCRUrl += "&access_token=" + accessToken; // 调用身份证识别接口的接口凭证
		String resOCR = null;
		CloseableHttpClient httpClientOCR = HttpClientBuilder.create().build(); // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		// DefaultHttpClient();
		HttpPost httpPostOCR = new HttpPost(OCRUrl);// 不同于以往 这里要求Post请求
//		        HttpGet httpget = new HttpGet(url);   					//创建GET请求
		CloseableHttpResponse responseOCR = null; // 响应模型

		// 配置信息
		RequestConfig requestConfigOCR = RequestConfig.custom() // 设置连接超时时间(单位毫秒)
				.setConnectTimeout(5000) // 设置请求超时时间(单位毫秒)
				.setConnectionRequestTimeout(5000) // socket读写超时时间(单位毫秒)
				.setSocketTimeout(5000) // 设置是否允许重定向(默认为true)
				.setRedirectsEnabled(false).build();
		httpPostOCR.setConfig(requestConfigOCR); // 将上面的配置信息 运用到这个Get请求里
		responseOCR = httpClientOCR.execute(httpPostOCR); // 由客户端执行(发送)Get请求
		HttpEntity responseEntityOCR = responseOCR.getEntity(); // 从响应模型中获取响应实体
		System.out.println("响应状态为:" + responseOCR.getStatusLine());
		if (responseEntityOCR != null) {
			resOCR = EntityUtils.toString(responseEntityOCR);
			System.out.println("响应内容长度为:" + responseEntityOCR.getContentLength());
			System.out.println("响应内容为:" + resOCR);
		}
		// 释放资源
		if (httpClientOCR != null) {
			httpClientOCR.close();
		}
		if (responseOCR != null) {
			responseOCR.close();
		}
		JSONObject joOCR = JSON.parseObject(resOCR);
		joOCR.put("IdUrl", newFileName);
		System.out.println(joOCR);
		System.out.println(IdUrl);
		
		return joOCR;
	};
	
	
//	@GetMapping("/prefetch")
//	@ResponseBody
//	public List<ResponseEntity<Resource>> getAllImages() throws IOException {
//	    List<ResponseEntity<Resource>> images = new ArrayList<>();
//	    String[] directoryPaths = {"/image/aboutus", "/image/account", "/image/IdAndLicense", "/image/logo", "/image/swiper", "/image/vehicle"};
//	    for (String directoryPath : directoryPaths) {
//	        File directory = new File(directoryPath);
//	        if (directory.exists() && directory.isDirectory()) {
//	            File[] files = directory.listFiles();
//	            for (File file : files) {
//	                if (file.isFile() && (file.getName().endsWith(".jpg") || file.getName().endsWith(".png"))) {
//	                    Resource imageResource = new ByteArrayResource(Files.readAllBytes(file.toPath()));
//	                    MediaType contentType = file.getName().endsWith(".jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
//	                    images.add(ResponseEntity.ok()
//	                            .contentType(contentType)
//	                            .body(imageResource));
//	                }
//	            }
//	        }
//	    }
//	    return images;
//	}
	
	
	
//	@GetMapping("/prefetch")
//	@ResponseBody
//	public List<ByteArrayResource> getAllImages() throws IOException {
//	    List<ByteArrayResource> images = new ArrayList<>();
//	    String[] directoryPaths = {"/image/aboutus", "/image/account", "/image/IdAndLicense", "/image/logo", "/image/swiper", "/image/vehicle"};
//	    for (String directoryPath : directoryPaths) {
//	        File directory = new File(directoryPath);
//	        if (directory.exists() && directory.isDirectory()) {
//	            File[] files = directory.listFiles();
//	            for (File file : files) {
//	                if (file.isFile() && (file.getName().endsWith(".jpg") || file.getName().endsWith(".png"))) {
//	                    ByteArrayResource imageResource = new ByteArrayResource(Files.readAllBytes(file.toPath()));
//	                    images.add(imageResource);
//	                }
//	            }
//	        }
//	    }
//	    return images;
//	}

//	@GetMapping("/prefetch")
//	public List<String> getPrefetchImagesString(){
//		List<String> res = new ArrayList<String>();
//		res.add("https://wechat.dfssz")
//	}

}
