package com.liuyuesi.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTypeImage {
	
	private byte typeId;
	private String imageName;
	private String imageUrl;
	
}
