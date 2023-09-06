package com.liuyuesi.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liuyuesi.demo.service.SmsServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = { "短信发送" })
@RestController
@RequestMapping("/sms")
@CrossOrigin
public class SmsController {

	@Autowired
	private SmsServiceImpl smsService;

	@ApiOperation(value = "发送预订确认短信给员工")
	@GetMapping("/reservationNotify")
	public String NotifyStaffWithConfirmation(
			@ApiParam(name = "phone", value = "手机号码", required = true) @RequestParam(name = "phone") String phone, 
			@ApiParam(name = "name", value = "姓名", required = true) @RequestParam(name = "name") String name,
			@ApiParam(name = "id", value = "身份证号", required = true) @RequestParam(name = "id") String id,
			@ApiParam(name = "yearCurrent", value = "起始年份", required = true) @RequestParam(name = "yearCurrent") Integer yearCurrent,
			@ApiParam(name = "yearEnd", value = "停止年份", required = true) @RequestParam(name = "yearEnd") Integer yearEnd,
			@ApiParam(name = "monthCurrent", value = "起始月份", required = true) @RequestParam(name = "monthCurrent") Integer monthCurrent,
			@ApiParam(name = "monthEnd", value = "停止月份", required = true) @RequestParam(name = "monthEnd") Integer monthEnd,
			@ApiParam(name = "dateCurrent", value = "起始日期", required = true) @RequestParam(name = "dateCurrent") Integer dateCurrent,
			@ApiParam(name = "dateEnd", value = "终止日期", required = true) @RequestParam(name = "dateEnd") Integer dateEnd,
			@ApiParam(name = "brand", value = "品牌", required = true) @RequestParam(name = "brand") String brand,
			@ApiParam(name = "model", value = "型号", required = true) @RequestParam(name = "model") String model,
			@ApiParam(name = "minuteCurrent", value = "起始分钟", required = true) @RequestParam(name = "minuteCurrent") String minuteCurrent,
			@ApiParam(name = "minuteEnd", value = "终止分钟", required = true) @RequestParam(name = "minuteEnd") String minuteEnd,
			@ApiParam(name = "hourCurrent", value = "起始小时", required = true) @RequestParam(name = "hourCurrent") Integer hourCurrent,
			@ApiParam(name = "hourEnd", value = "终止小时", required = true) @RequestParam(name = "hourEnd") Integer hourEnd){
		// 调用service发送短信的方法
		
		//是否向员工发送预定确认短信
		boolean isSendStaff = smsService.sendReservationConfirmationToStaff("",name,id,yearCurrent,yearEnd,monthCurrent,monthEnd,dateCurrent,dateEnd,brand,model,minuteCurrent,minuteEnd,hourCurrent,hourEnd);
		//是否向用户发送确认短信
		boolean isSendUser = smsService.sendConfirmationToUser(phone,yearCurrent,yearEnd,monthCurrent,monthEnd,dateCurrent,dateEnd,brand,model,minuteCurrent,minuteEnd,hourCurrent,hourEnd);
//		发送信息成功
		if (isSendStaff&&isSendUser) {
			return "success";
		} else {
//			短信发送失败！
			return "fail";
		}
	}
	
	
	@ApiOperation(value = "发送订单确认短信给用户")
	@GetMapping("/orderNotify")
	public String NotifyUserWithConfirmation(@ApiParam(name = "phone", value = "手机号码", required = true) @RequestParam(name = "phone") String phone, 
			@ApiParam(name = "name", value = "姓名", required = true) @RequestParam(name = "name") String name,
			@ApiParam(name = "id", value = "身份证号", required = true) @RequestParam(name = "id") String id,
			@ApiParam(name = "yearCurrent", value = "起始年份", required = true) @RequestParam(name = "yearCurrent") Integer yearCurrent,
			@ApiParam(name = "yearEnd", value = "停止年份", required = true) @RequestParam(name = "yearEnd") Integer yearEnd,
			@ApiParam(name = "monthCurrent", value = "起始月份", required = true) @RequestParam(name = "monthCurrent") Integer monthCurrent,
			@ApiParam(name = "monthEnd", value = "停止月份", required = true) @RequestParam(name = "monthEnd") Integer monthEnd,
			@ApiParam(name = "dateCurrent", value = "起始日期", required = true) @RequestParam(name = "dateCurrent") Integer dateCurrent,
			@ApiParam(name = "dateEnd", value = "终止日期", required = true) @RequestParam(name = "dateEnd") Integer dateEnd,
			@ApiParam(name = "brand", value = "品牌", required = true) @RequestParam(name = "brand") String brand,
			@ApiParam(name = "model", value = "型号", required = true) @RequestParam(name = "model") String model,
			@ApiParam(name = "minuteCurrent", value = "起始分钟", required = true) @RequestParam(name = "minuteCurrent") String minuteCurrent,
			@ApiParam(name = "minuteEnd", value = "终止分钟", required = true) @RequestParam(name = "minuteEnd") String minuteEnd,
			@ApiParam(name = "hourCurrent", value = "起始小时", required = true) @RequestParam(name = "hourCurrent") Integer hourCurrent,
			@ApiParam(name = "hourEnd", value = "终止小时", required = true) @RequestParam(name = "hourEnd") Integer hourEnd) {
		
		
		//是否向员工发送订单确认短信
		boolean isSendStaff = smsService.sendOrderConfirmationToStaff("",name,id,yearCurrent,yearEnd,monthCurrent,monthEnd,dateCurrent,dateEnd,brand,model,minuteCurrent,minuteEnd,hourCurrent,hourEnd);
		//是否向用户发送订单和预订的确认短信
		boolean isSendUser = smsService.sendConfirmationToUser(phone,yearCurrent,yearEnd,monthCurrent,monthEnd,dateCurrent,dateEnd,brand,model,minuteCurrent,minuteEnd,hourCurrent,hourEnd);
		if (isSendStaff&&isSendUser) {
			return "success";
		} else {
			return "fail";
		}
		
		
	}

}
