package com.liuyuesi.demo.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrderInfo {
	//@XStreamAlias注释 是为了将class里的每个变量转化为xml里的对应的标签
	@XStreamAlias("appid")
	private String appId; //小程序ID
	@XStreamAlias("mch_id")
	private String mchId; //商户号
	@XStreamAlias("nonce_str")
	private String nonceStr; //随机字符串
	@XStreamAlias("sign_type")
	private String signType; //签名类型
	@XStreamAlias("sign")
	private String sign; //签名
	@XStreamAlias("body")
	private String body; //商品描述
	@XStreamAlias("out_trade_no")
	private String outTradeNo; //商户订单号
	@XStreamAlias("total_fee")
	private Integer totalFee; //标价金额 单位为分
	@XStreamAlias("spbill_create_ip")
	private String spbillCreateIp; //终端IP
	@XStreamAlias("notify_url")
	private String notifyUrl; //通知地址
	@XStreamAlias("trade_type")
	private String tradeType; //交易类型
	@XStreamAlias("openid")
	private String openId; //用户标识
	
}
