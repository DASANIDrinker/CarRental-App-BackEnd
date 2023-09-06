package com.liuyuesi.demo.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
	
//	private String name;
	private String openId;//唯一标识 PK
	private String sessionKey;//会话密钥
	private Integer accessToken;//返回前端的生成的标识
	private String phone;//用户姓名
//	private String code;
	
}
