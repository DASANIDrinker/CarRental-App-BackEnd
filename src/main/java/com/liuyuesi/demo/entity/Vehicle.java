package com.liuyuesi.demo.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle implements Serializable{
	
	private Short vehicleId;//汽车PK 自加
	private String plateNumber;//车牌号
//	private boolean isRadar;//是否有倒车雷达
//	private boolean isCamera;//是否有倒车影像	
	private Boolean isRecorder;//是否有行车记录仪
	private Boolean isETC;//是否有etc
	private Boolean isMount;//是否有手机支架
	private Boolean isUmbrella;//是否有雨伞
//	private boolean isSunroof;//是否有全景天窗
//	private boolean isLeather;//是否有真皮座椅
	private Boolean isOccupied;//是否被租用
	private VehicleType vehicleType;//汽车类型
}
