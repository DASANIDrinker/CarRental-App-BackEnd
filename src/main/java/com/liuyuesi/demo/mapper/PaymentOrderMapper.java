package com.liuyuesi.demo.mapper;




import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

import com.liuyuesi.demo.entity.PaymentOrderInfo;
@Mapper
public interface PaymentOrderMapper {

	@Select("select * from paymentorder")
	@Results(id = "paymentorder",
			value = { 
					@Result(column = "sign", property = "sign", id = true), 
					@Result(column = "app_id", property = "appId"),
					@Result(column = "mch_id", property = "mchId"),
					@Result(column = "nonce_str", property = "nonceStr"),
					@Result(column = "sign_type", property = "signType"),
					@Result(column = "body", property = "body"),
					@Result(column = "out_trade_no", property = "outTradeNo"),
					@Result(column = "total_fee", property = "totalFee"),
					@Result(column = "spbill_create_ip", property = "spbillCreateIp"),
					@Result(column = "notify_url", property = "notifyUrl"),
					@Result(column = "trade_type", property = "tradeType"),
					@Result(column = "open_id", property = "openId"),
					}
			)
	Cursor<PaymentOrderInfo> findAll();
	
//	@Insert("insert into paymentorder(sign,app_id,mch_id,nonce_str,sign_type,body,out_trade_no,total_fee,spbill_create_ip,notify_url,trade_type,open_id) values(#{paymentorder.sign},#{paymentorder.app_id},#{paymentorder.mch_id},#{paymentorder.nonce_str},#{paymentorder.sign_type},#{paymentorder.body},#{paymentorder.out_trade_no},#{paymentorder.total_fee},#{paymentorder.spbill_create_ip},#{paymentorder.notify_url},#{paymentorder.trade_type},#{paymentorder.open_id})")
	@Insert("insert into paymentorder(sign,out_trade_no) values(#{sign},#{outTradeNo})")
//	@Options(useGeneratedKeys = true, keyColumn = "sign", keyProperty = "sign")
	void insert(String sign,String outTradeNo);
	
	@Select("select * from paymentorder where sign = #{sign}")
	PaymentOrderInfo findPaymentOrderBySign(String sign);
	
	@Select("select out_trade_no from paymentorder where sign = #{sign}")
	String findOutTradeNoBySign(String sign);
}
