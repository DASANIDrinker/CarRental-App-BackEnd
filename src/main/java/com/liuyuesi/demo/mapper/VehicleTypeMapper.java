package com.liuyuesi.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.liuyuesi.demo.entity.VehicleType;

@Mapper
public interface VehicleTypeMapper {

	//获取全部车型
//	@Select("select * from vehicletype where is_full = ${false}")
	@Select("select * from vehicletype")
	@Results(id = "vehicleType",
		value = {
		@Result(column = "vehicle_type_id", property = "vehicleTypeId", id= true),
		@Result(column = "brand",property = "brand"),
		@Result(column = "model",property = "model"),
		@Result(column = "displacement",property = "displacement"),
		@Result(column = "boxes",property = "boxes"),
		@Result(column = "power",property = "power"),
		@Result(column = "is_auto",property = "isAuto"),
		@Result(column = "seats",property = "seats"),
		@Result(column = "style",property = "style"),
		@Result(column = "is_full",property = "isFull"),
		@Result(column = "fee_per_day",property = "feePerDay"),
		@Result(column = "nation", property = "nation"),
		@Result(column = "is_camera", property = "isCamera"),
		@Result(column = "is_sunroof", property = "isSunroof"),
		@Result(column = "is_leather", property = "isLeather"),
		@Result(column = "image_url", property = "imageUrl"),
		@Result(column = "image_name", property = "imageName")
	})
	List<VehicleType> findAll();
	
	//如果insert时不加PK 那么会返回自增的PK的值 如果加了PK就按照添加的PK的值来添加
	@Options(useGeneratedKeys = true,keyColumn = "vehicle_type_id",keyProperty = "vehicleTypeId")
	@Insert("insert ignore into vehicletype(is_full,brand,model,displacement,boxes,power,is_auto,seats,style,fee_per_day,nation,is_camera,is_sunroof,is_leather,image_url,image_name) values(#{isFull},#{brand},#{model},#{displacement},#{boxes},#{power},#{isAuto},#{seats},#{style},#{feePerDay},#{nation},#{isCamera},#{isSunroof},#{isLeather},#{imageUrl},#{imageName})")
	int insert(VehicleType vehicleType);
	
	@Update("update vehicletype set brand = #{brand}, model = #{model}, displacement = #{displacement}, is_auto = #{isAuto},"
			+ "fee_per_day = #{feePerDay}, boxes = #{boxes}, power = #{power}, seats = #{seats}, style = #{style}, is_full = #{isFull},"
			+ "nation = #{nation}, is_camera = #{isCamera}, is_sunroof = #{isSunroof}, is_leather = #{isLeather} "
			+ "where vehicle_type_id = #{vehicleTypeId}")
	int update(VehicleType type);
	
	@Delete("delete from vehicletype where vehicle_type_id in (${vehicleTypes})")
	int delete(@Param("vehicleTypes") String types);
	
	@Select("select * from vehicleType where brand = #{brand}, model = #{model}, is_full = #{isFull}")
	List<VehicleType> selectByBrandAndModel(String brand, String model);
	
	//通过车型PK获取该车型全部信息
	@Select("select * from vehicletype where vehicle_type_id = #{vehicleTypeId}")
	@ResultMap("vehicleType")
	VehicleType findVehicleTypeById(byte vehicleTypeId);
	
	//将租满的车型的is_full修改为true 代表该车型已租满
	@Update("update vehicletype set is_full = ${true} where vehicle_type_id = #{typeId}")
	void updateFull(int typeId);
	
	//将汽车类型的url更新
	@Update("update vehicletype set image_url = #{url}, image_name = #{url} where vehicle_type_id = #{vehicleTypeId}")
	int updateTypeUrl(byte vehicleTypeId, String url);
	
	//通过将汽车类型的imageUrl和imageName删除 达到数据库里删除汽车类型照片的效果
	@Update("update vehicletype set image_url = null, image_name = null where vehicle_type_id = #{vehicleTypeId}")
	int deleteVehicleTypeImage(byte vehicleTypeId);
}
