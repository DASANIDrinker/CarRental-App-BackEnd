package com.liuyuesi.demo.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liuyuesi.demo.util.SmsRandomUtil;
import com.liuyuesi.demo.util.SmsUtil;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.*;

@Service
public class SmsServiceImpl implements SmsService {
	
	
	// 发送预定确认短信给员工
	@Override
	public boolean sendReservationConfirmationToStaff(String phone, String name, String id, Integer yearCurrent,
			Integer yearEnd, Integer monthCurrent, Integer monthEnd, Integer dateCurrent, Integer dateEnd, String brand,
			String model,String minuteCurrent,String minuteEnd,Integer hourCurrent,Integer hourEnd) {
		// 判断手机号是否为空
		if (StringUtils.isEmpty(phone)) {
			return false;
		}
		try {
			// 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
			// 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
			Credential cred = new Credential(SmsUtil.getSecretID(), SmsUtil.getSecretKey());
			// 实例化一个http选项，可选的，没有特殊需求可以跳过
			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("sms.tencentcloudapi.com");
			// 实例化一个client选项，可选的，没有特殊需求可以跳过
			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);
			// 实例化要请求产品的client对象,clientProfile是可选的 第二个参数是地域信息
			SmsClient client = new SmsClient(cred, "ap-beijing", clientProfile);
			// 实例化一个请求对象,每个接口都会对应一个request对象
			SendSmsRequest req = new SendSmsRequest();
			// 设置固定的参数
			req.setSmsSdkAppId(SmsUtil.getSmsSdkAppID());// 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId
			req.setSignName(SmsUtil.getSignName());// 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名
			req.setTemplateId(SmsUtil.getReservationStaffTemplateId());// 模板 ID: 必须填写已审核通过的模板 ID
			/* 用户的 session 内容: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
//            String sessionContext = "xxx";
//            req.setSessionContext(sessionContext);

			// 设置发送相关的参数
			//分别向员工手机号和下单手机号发送信息
			String[] phoneNumberSet1 = { "+86" + phone };
			req.setPhoneNumberSet(phoneNumberSet1);// 发送的手机号
			// 设置要传递的参数
			String[] templateParamSet1 = { name, id, yearCurrent.toString(), monthCurrent.toString(),
					dateCurrent.toString(), hourCurrent.toString(), minuteCurrent, yearEnd.toString(), monthEnd.toString(), 
					dateEnd.toString(),hourEnd.toString(),minuteEnd, brand, model };
			req.setTemplateParamSet(templateParamSet1);

			// 发送短信
			// 返回的resp是一个SendSmsResponse的实例，与请求对象对应
			SendSmsResponse resp = client.SendSms(req);
			System.out.println("resp" + resp);
			// 输出json格式的字符串回包
			System.out.println(SendSmsResponse.toJsonString(resp));
			return true;
		} catch (TencentCloudSDKException e) {
			e.printStackTrace();
			return false;
		}
	}

	//发送订单确认短信给员工
	public boolean sendOrderConfirmationToStaff(String phone, String name, String id, Integer yearCurrent,
			Integer yearEnd, Integer monthCurrent, Integer monthEnd, Integer dateCurrent, Integer dateEnd, String brand,
			String model,String minuteCurrent,String minuteEnd,Integer hourCurrent,Integer hourEnd) {
		// 判断手机号是否为空
		if (StringUtils.isEmpty(phone)) {
			return false;
		}
		try {
			// 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
			// 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
			Credential cred = new Credential(SmsUtil.getSecretID(), SmsUtil.getSecretKey());
			System.out.println(SmsUtil.getSecretID());
			System.out.println(SmsUtil.getSecretKey());
			System.out.println(SmsUtil.getSmsSdkAppID());
	        System.out.println(SmsUtil.getSignName());
	        System.out.println(SmsUtil.getReservationStaffTemplateId());
	        System.out.println(SmsUtil.getOrderStaffTemplateId());
	        System.out.println(SmsUtil.getUserTemplateId());
			// 实例化一个http选项，可选的，没有特殊需求可以跳过
			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("sms.tencentcloudapi.com");
			// 实例化一个client选项，可选的，没有特殊需求可以跳过
			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);
			// 实例化要请求产品的client对象,clientProfile是可选的 第二个参数是地域信息
			SmsClient client = new SmsClient(cred, "ap-beijing", clientProfile);
			// 实例化一个请求对象,每个接口都会对应一个request对象
			SendSmsRequest req = new SendSmsRequest();
			// 设置固定的参数
			req.setSmsSdkAppId(SmsUtil.getSmsSdkAppID());// 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId
			req.setSignName(SmsUtil.getSignName());// 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名
			req.setTemplateId(SmsUtil.getOrderStaffTemplateId());// 模板 ID: 必须填写已审核通过的模板 ID
			/* 用户的 session 内容: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
//		            String sessionContext = "xxx";
//		            req.setSessionContext(sessionContext);

			// 设置发送相关的参数
			String[] phoneNumberSet1 = { "+86" + phone };
			req.setPhoneNumberSet(phoneNumberSet1);// 发送的手机号
			// 设置要传递的参数
			String[] templateParamSet1 = { name, id, yearCurrent.toString(), monthCurrent.toString(),
					dateCurrent.toString(), hourCurrent.toString(), minuteCurrent, yearEnd.toString(), monthEnd.toString(), 
					dateEnd.toString(),hourEnd.toString(),minuteEnd, brand, model };
			req.setTemplateParamSet(templateParamSet1);

			// 发送短信
			// 返回的resp是一个SendSmsResponse的实例，与请求对象对应
			SendSmsResponse resp = client.SendSms(req);
			System.out.println("resp" + resp);
			// 输出json格式的字符串回包
			System.out.println(SendSmsResponse.toJsonString(resp));
			return true;
		} catch (TencentCloudSDKException e) {
			e.printStackTrace();
			return false;
		}
	}

	//发送订单/预定确认短信给用户
	public boolean sendConfirmationToUser(String phone, Integer yearCurrent, Integer yearEnd, Integer monthCurrent,
			Integer monthEnd, Integer dateCurrent, Integer dateEnd, String brand, String model,
			String minuteCurrent,String minuteEnd,Integer hourCurrent,Integer hourEnd) {

		// 判断手机号是否为空
		if (StringUtils.isEmpty(phone)) {
			return false;
		}
		try {
			// 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
			// 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
			Credential cred = new Credential(SmsUtil.getSecretID(), SmsUtil.getSecretKey());
			// 实例化一个http选项，可选的，没有特殊需求可以跳过
			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("sms.tencentcloudapi.com");
			// 实例化一个client选项，可选的，没有特殊需求可以跳过
			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);
			// 实例化要请求产品的client对象,clientProfile是可选的 第二个参数是地域信息
			SmsClient client = new SmsClient(cred, "ap-beijing", clientProfile);
			// 实例化一个请求对象,每个接口都会对应一个request对象
			SendSmsRequest req = new SendSmsRequest();
			// 设置固定的参数
			req.setSmsSdkAppId(SmsUtil.getSmsSdkAppID());// 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId
			req.setSignName(SmsUtil.getSignName());// 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名
			req.setTemplateId(SmsUtil.getUserTemplateId());// 模板 ID: 必须填写已审核通过的模板 ID
			/* 用户的 session 内容: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
//				            String sessionContext = "xxx";
//				            req.setSessionContext(sessionContext);

			// 设置发送相关的参数
			String[] phoneNumberSet1 = { "+86" + phone };
			req.setPhoneNumberSet(phoneNumberSet1);// 发送的手机号
			// 设置要传递的参数
			String[] templateParamSet1 = { brand, model, yearCurrent.toString(), monthCurrent.toString(),
					dateCurrent.toString(), hourCurrent.toString(), minuteCurrent ,yearEnd.toString(), monthEnd.toString(), dateEnd.toString(), hourEnd.toString(),minuteEnd };
			req.setTemplateParamSet(templateParamSet1);

			// 发送短信
			// 返回的resp是一个SendSmsResponse的实例，与请求对象对应
			SendSmsResponse resp = client.SendSms(req);
			System.out.println("resp" + resp);
			// 输出json格式的字符串回包
			System.out.println(SendSmsResponse.toJsonString(resp));
			return true;
		} catch (TencentCloudSDKException e) {
			e.printStackTrace();
			return false;
		}
	}

}
