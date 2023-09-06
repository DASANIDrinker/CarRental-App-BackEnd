package com.liuyuesi.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
	private String name;
	private String id;
	private String birth;
	private String address;
	private String gender;
	private String nationality;
	//准驾车辆 例如C1 C2
	private String carClass;
	private Integer accessToken;
	private String phone;
	private String idFrontUrl;
	private String idBackUrl;
	private String licenseUrl;
}
