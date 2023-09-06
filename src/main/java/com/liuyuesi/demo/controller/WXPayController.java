package com.liuyuesi.demo.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liuyuesi.demo.entity.PaymentOrderInfo;
import com.liuyuesi.demo.entity.PaymentOrderReturnInfo;
import com.liuyuesi.demo.entity.QueryReturnInfo;
import com.liuyuesi.demo.entity.SignInfo;
import com.liuyuesi.demo.entity.User;
import com.liuyuesi.demo.service.PaymentOrderServiceImpl;
import com.liuyuesi.demo.service.UserServiceImpl;
import com.liuyuesi.demo.util.HttpRequestUtil;
import com.liuyuesi.demo.util.PaymentOrderSetting;
import com.liuyuesi.demo.util.PaymentOrderUtil;
import com.liuyuesi.demo.util.RandomStringGenerator;
import com.liuyuesi.demo.util.SignatureUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

@RestController
@RequestMapping(value = "/wxpay")
public class WXPayController {

	@Autowired
	UserServiceImpl userService;

	@Autowired
	PaymentOrderServiceImpl paymentOrderService;

	@PostMapping(value = "/weixin/payment")
	public Map payment(@RequestParam(name = "total") int total, @RequestParam(name = "accessToken") int accessToken) {
		System.out.println(total);
		System.out.println(accessToken);
		int total_fee = total / 10;
//		int total_fee = total*100;
		int access_token = accessToken;
		Map<String, Object> map = new HashMap<>();
//	     String money = total;
		String title = "商品名字";
		User user = userService.findUserByAccessToken(access_token);
		String openId = user.getOpenId();
		try {
			PaymentOrderInfo order = new PaymentOrderInfo();
			order.setAppId(PaymentOrderSetting.getAppID());
			order.setMchId(PaymentOrderSetting.getMch_id());
			order.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
			order.setBody(title);
			order.setOutTradeNo(RandomStringGenerator.getRandomStringByLength(32));
//	            order.setTotalFee(Integer.parseInt(total)); // 该金钱其实10 是 0.1元
			order.setTotalFee(total_fee);
			order.setSpbillCreateIp("127.0.0.1");
			order.setNotifyUrl(PaymentOrderSetting.getNotify_url());
			order.setTradeType(PaymentOrderSetting.getTrade_type());
			// 这里直接使用当前用户的openid
			order.setOpenId(openId);
			order.setSignType("MD5");
			// 生成签名
			String sign = SignatureUtil.getSign(order);
			order.setSign(sign);

			String result = HttpRequestUtil.sendPost(PaymentOrderSetting.getUrl(), order);
			System.out.println(result);
			XStream xStream = new XStream();
			xStream.addPermission(AnyTypePermission.ANY);
			xStream.alias("xml", PaymentOrderReturnInfo.class);

			PaymentOrderReturnInfo returnInfo = (PaymentOrderReturnInfo) xStream.fromXML(result);
			System.out.println(returnInfo);
			// 二次签名
			if ("SUCCESS".equals(returnInfo.getReturn_code())
					&& returnInfo.getReturn_code().equals(returnInfo.getResult_code())) {
//	            	System.out.println("ABCDEFG");
				SignInfo signInfo = new SignInfo();
				signInfo.setAppId(PaymentOrderSetting.getAppID());
				long time = System.currentTimeMillis() / 1000;
				signInfo.setTimeStamp(String.valueOf(time));
				signInfo.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
				signInfo.setPrepay_id("prepay_id=" + returnInfo.getPrepay_id());
				signInfo.setSignType("MD5");
				// 生成签名
				String sign1 = SignatureUtil.getSign(signInfo);
				Map<String, String> payInfo = new HashMap<>();
				payInfo.put("timeStamp", signInfo.getTimeStamp());
				payInfo.put("nonceStr", signInfo.getNonceStr());
				payInfo.put("package", signInfo.getPrepay_id());
				payInfo.put("signType", signInfo.getSignType());
				payInfo.put("paySign", sign1);
				payInfo.put("outTradeNo", order.getOutTradeNo());
				map.put("status", 200);
				map.put("msg", "统一下单成功!");
				map.put("data", payInfo);

				// 此处可以写唤起支付前的业务逻辑

				// 业务逻辑结束 回传给小程序端唤起支付
				return map;
			}
			map.put("status", 500);
			map.put("msg", "统一下单失败!");
			map.put("data", null);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/weixin/callback")
	public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		// sb为微信返回的xml
		String notityXml = sb.toString();
		String resXml = "";
		System.out.println("接收到的报文：" + notityXml);

		Map map = PaymentOrderUtil.doXMLParse(notityXml);

		String returnCode = (String) map.get("return_code");
		if ("SUCCESS".equals(returnCode)) {
			// 验证签名是否正确
			Map<String, String> validParams = PaymentOrderUtil.paraFilter(map); // 回调验签时需要去除sign和空值参数
			String validStr = PaymentOrderUtil.createLinkString(validParams);// 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
			String sign = PaymentOrderUtil.sign(validStr, PaymentOrderSetting.getKey(), "utf-8").toUpperCase();// 拼装生成服务器端验证的签名
			// 因为微信回调会有八次之多,所以当第一次回调成功了,那么我们就不再执行逻辑了

			// 根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
			if (sign.equals(map.get("sign"))) {
				/** 此处添加自己的业务逻辑代码start **/
				// bla bla bla....
				/** 此处添加自己的业务逻辑代码end **/
				// 通知微信服务器已经支付成功
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			} else {
				System.out.println("微信支付回调失败!签名不一致");
			}
		} else {
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
					+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		}
		System.out.println(resXml);
		System.out.println("微信支付回调数据结束");

		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();
	}

	// 查询订单情况
	@RequestMapping(value = "/weixin/orderQuery", method = RequestMethod.POST)
	public Map orderQuery(@RequestParam("paySign") String paySign) {

		// 通过paySign获取outTradeNo
		String outTradeNo = paymentOrderService.findOutTradeNoBySign(paySign);

		Map<String, Object> map = new HashMap<>();
		try {
			PaymentOrderInfo order = new PaymentOrderInfo();
			order.setAppId(PaymentOrderSetting.getAppID());
			order.setMchId(PaymentOrderSetting.getMch_id());
			order.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
			order.setOutTradeNo(outTradeNo);
			order.setSignType("MD5");
			// 生成签名
			String sign = SignatureUtil.getSign(order);
			order.setSign(sign);
			// 因为在查询订单支付结果的时候 我们新创建的paymentOrderInfo实例里的total_fee初始值为0
			// 所以total_fee会被录入需要发送的请求里面 但是查询订单支付结果的api不接受total_fee属性
			// 所以我们需要将其设置为null 从而达到被xstream忽略的效果
			order.setTotalFee(null);
			System.out.println(order);
			String result = HttpRequestUtil.sendPost(PaymentOrderSetting.getQuery_url(), order);
			System.out.println("查询订单的响应为:" + result);
			XStream xStream = new XStream();
			xStream.addPermission(AnyTypePermission.ANY);
			xStream.alias("xml", QueryReturnInfo.class);

			QueryReturnInfo returnInfo = (QueryReturnInfo) xStream.fromXML(result);
			System.out.println("return Info is:" + returnInfo);
			if ("SUCCESS".equals(returnInfo.getReturn_code())) {
				map.put("data", returnInfo);
			} else {
				map.put("status", 500);
				map.put("msg", "查询订单失败!");
			}

			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 退款
	public String doRefund(String url, String data) throws Exception {
		// 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		File file = new File(this.getClass().getClassLoader().getResource("apiclient_cert.p12").getFile());

		// 指向你的证书的绝对路径，带着证书去访问
		FileInputStream instream = new FileInputStream(file);// P12文件目录
		try {
			// 下载证书时的密码、默认密码是你的MCHID mch_id
			keyStore.load(instream, PaymentOrderSetting.getMch_id().toCharArray());// 这里写密码
		} finally {
			instream.close();
		}
		// 下载证书时的密码、默认密码是你的MCHID mch_id
		SSLContext sslcontext = SSLContexts.custom()
				.loadKeyMaterial(keyStore, PaymentOrderSetting.getMch_id().toCharArray())// 这里也是写密码的
				.build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpPost httpost = new HttpPost(url); // 设置响应头信息
			httpost.addHeader("Connection", "keep-alive");
			httpost.addHeader("Accept", "*/*");
			httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpost.addHeader("Host", "api.mch.weixin.qq.com");
			httpost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpost.addHeader("Cache-Control", "max-age=0");
			httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
			httpost.setEntity(new StringEntity(data, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httpost);
			try {
				HttpEntity entity = response.getEntity();
				String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
				EntityUtils.consume(entity);
				return jsonStr;
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

	/**
	 * 申请退款
	 *
	 * @param orderId       商户订单号
	 * @param refundId      商户退款单号
	 * @param totalFee      订单金额
	 * @param refundFee     退款金额
	 * @param refundAccount 退款资金来源（默认传 "REFUND_SOURCE_UNSETTLED_FUNDS"） 注:
	 *                      退款金额不能大于订单金额
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "/weixin/refund", method = RequestMethod.POST)
	public Map<String, String> refund(String orderId, String refundId, String totalFee, String refundFee,
			String refundAccount) throws IllegalAccessException {

		Map<String, String> params = new HashMap<>();
		params.put("appid", PaymentOrderSetting.getAppID());
		params.put("mch_id", PaymentOrderSetting.getMch_id());
		params.put("nonce_str", RandomStringGenerator.getRandomStringByLength(32));
		params.put("out_trade_no", orderId); // 商户订单号和微信订单号二选一(我这里选的是商户订单号)
		params.put("out_refund_no", RandomStringGenerator.getRandomStringByLength(32));
		params.put("total_fee", totalFee);
		params.put("refund_fee", refundFee);
		params.put("refund_account", refundAccount);
		params.put("sign_type", "MD5");
		String order = PaymentOrderUtil.createLinkString(params);

		// 签名算法
		String sign = SignatureUtil.getSign(order);
		params.put("sign", sign);

		Map<String, String> map = new HashMap<>();
		try {
			String xml = PaymentOrderUtil.mapToXml(params);
			String response = doRefund("https://api.mch.weixin.qq.com/secapi/pay/refund", xml);
			map = PaymentOrderUtil.doXMLParse(response);
			Map<String,String> result = new HashMap<>();
			
			// 这里是将返回类型转换成了 map 如果要判断是否退款成功可以获取 map里面的 result_code 值，如果这个值等于
			// SUCCESS就代表退款成功，这里就可以写退款之后的逻辑，比如修改订单状态之类的，
			if ("SUCCESS".equals(map.get("result_code"))) {
				// 退款成功
				// 这里就写逻辑，当然，如果不需要写逻辑的话，可以直接吧if else 删掉也是可以直接跑起来的
				result.put("status", "200");
				result.put("msg", "退款成功!");
			} else {
				// 退款失败
				result.put("status", "500");
				result.put("msg", "退款失败!");
				result.put("data", null);
				return result;
			}

		} catch (Exception e) {
		}
		return map;
	}
}
