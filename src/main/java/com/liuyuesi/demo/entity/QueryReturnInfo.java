package com.liuyuesi.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryReturnInfo {

    private String return_code;

    private String return_msg;

    private String result_code;

    private String err_code;

    private String err_code_des;

    private String appid;

    private String mch_id;

    private String nonce_str;

    private String sign;

    private String prepay_id;

    private String trade_type;

    private String device_info;

    private String openid;

    private String is_subscribe;

    private String trade_state;

    private String bank_type;

    private int total_fee;

    private int settlement_total_fee;

    private String fee_type;

    private int cash_fee;

    private String cash_fee_type;

    private int coupon_fee;

    private int coupon_count;

    private String coupon_type_$n;

    private String coupon_id_$n;

    private String transaction_id;

    private String out_trade_no;

    private String time_end;

    private String trade_state_desc;
    
    private String attach;

}
