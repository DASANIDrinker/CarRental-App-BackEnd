package com.liuyuesi.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

	private Integer locationId;
	private String locationBrief;
	private String province;
	private String city;
	private String district;
	private String street;
	private String community;
	private String building;
	private String room;
}
