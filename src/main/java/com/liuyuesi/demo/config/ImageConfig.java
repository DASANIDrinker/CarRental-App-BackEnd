package com.liuyuesi.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableWebMvc
@Configuration
@CrossOrigin(origins = "*", maxAge = 3600)
class ImageConfig implements WebMvcConfigurer {
	
	
	//上传的图片保存在磁盘的地址
 	@Value("${file.save.path.ids}")
    private String fileSavePath;
 	
 	//前端图片保存在磁盘的地址(包括汽车类型图片 和前端的logo轮播图等)
 	@Value("${file.get.path.vehicles}")
 	private String fileGetPathVehicles;
 	
 	@Value("${file.get.path.ids}")
 	private String fileGetPathIds;
 	
 	@Value("${file.get.path.frontend}")
 	private String fileGetPathFrontEnd;
 	
 	//照片返回前端设置
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    	//图片资源处理器 将/img/....请求路径 转化为/file +{{fileSavePath}}路径 来请求图片
    	//线下版本的图片资源处理器
    	registry.addResourceHandler("/img/aboutUs/**").addResourceLocations("file:"+fileGetPathVehicles+"/aboutUs/");
    	registry.addResourceHandler("/img/account-background/**").addResourceLocations("file:"+fileGetPathVehicles+"/account-background/");
    	registry.addResourceHandler("/img/carBlock/**").addResourceLocations("file:"+fileGetPathVehicles+"/carBlock/");
    	registry.addResourceHandler("/img/IdAndLicense/**").addResourceLocations("file:"+fileGetPathVehicles+"/IdAndLicense/");
    	registry.addResourceHandler("/img/Logo/**").addResourceLocations("file:"+fileGetPathVehicles+"/Logo/");
    	registry.addResourceHandler("/img/swiper/**").addResourceLocations("file:"+fileGetPathVehicles + "/swiper/");
    	registry.addResourceHandler("/img/ID/**").addResourceLocations("file:"+fileGetPathIds);
    	
    	//线上版本的图片资源处理器 测试版本1.0 失败
//    	registry.addResourceHandler("/img/aboutUs/**").addResourceLocations("file:"+fileGetPathVehicles+"/aboutus/");
//    	registry.addResourceHandler("/img/account-background/**").addResourceLocations("file:"+fileGetPathVehicles+"/account/");
//    	registry.addResourceHandler("/img/carBlock/**").addResourceLocations("file:"+fileGetPathVehicles+"/vehicle/");
//    	registry.addResourceHandler("/img/IdAndLicense/**").addResourceLocations("file:"+fileGetPathVehicles+"/id/");
//    	registry.addResourceHandler("/img/Logo/**").addResourceLocations("file:"+fileGetPathVehicles+"/logo/");
//    	registry.addResourceHandler("/img/swiper/**").addResourceLocations("file:"+fileGetPathVehicles + "/swiper/");
//    	registry.addResourceHandler("/img/ID/**").addResourceLocations("file:"+fileGetPathIds);
    	
    	
    	//线上版本的图片资源处理器 测试版本 1.1成功 线上用以下的
//    	registry.addResourceHandler("/img/aboutUs/**").addResourceLocations("file:"+fileGetPathFrontEnd+"aboutus/");
//    	registry.addResourceHandler("/img/account-background/**").addResourceLocations("file:"+fileGetPathFrontEnd+"account/");
//    	registry.addResourceHandler("/img/carBlock/**").addResourceLocations("file:"+fileGetPathVehicles);
//    	registry.addResourceHandler("/img/IdAndLicense/**").addResourceLocations("file:"+fileGetPathFrontEnd+"IdAndLicense/");
//    	registry.addResourceHandler("/img/Logo/**").addResourceLocations("file:"+fileGetPathFrontEnd+"logo/");
//    	registry.addResourceHandler("/img/swiper/**").addResourceLocations("file:"+fileGetPathFrontEnd + "swiper/");
//    	registry.addResourceHandler("/img/ID/**").addResourceLocations("file:"+fileGetPathIds);
    	
    
    }
}
