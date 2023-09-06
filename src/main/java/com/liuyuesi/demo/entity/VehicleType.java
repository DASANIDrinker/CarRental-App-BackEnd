package com.liuyuesi.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class VehicleType {

	private byte vehicleTypeId;//汽车种类id PK
	private String brand;//汽车品牌
	private String model;//汽车型号 eg.速腾
	private String displacement;//排量
	private Boolean isAuto;//是否是自动挡
	private short feePerDay;//每天价格
	private String boxes;//几厢车 (两厢-2 三厢-3 SUV-4)
	private String power;//动力类型(油-1 电-2 混合-3)
	private byte seats;//座位数
	private String style;//风格(1.经济型 2.纯电动 3.舒适型 4.SUV 5.商务型 6.豪华型)
	private Boolean isFull;//该类型车是否组满
	private String nation;//出产国
//	private boolean isRadar;//倒车雷达
	private Boolean isCamera;//倒车影像
	private Boolean isSunroof;//全景天窗
	private Boolean isLeather;//真皮座椅
	private String imageUrl;//图片url链接
	private String imageName;//图片名字及后缀
}
