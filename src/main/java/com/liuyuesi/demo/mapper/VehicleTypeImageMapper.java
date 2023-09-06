package com.liuyuesi.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.liuyuesi.demo.entity.VehicleTypeImage;

@Mapper
public interface VehicleTypeImageMapper {

	@Select("select * from vehicletypeimage where typeid = ${typeId}")
	@Results({
		@Result(column = "vehicle_type_id", property = "typeId"),
		@Result(column = "image_name", property = "imageName"),
		@Result(column = "image_url", property = "imageUrl")
	})
	VehicleTypeImage findById(byte typeId);
	
	@Options(useGeneratedKeys = true,keyColumn = "vehicle_type_id",keyProperty = "typeId")
	@Insert("insert ignore into vehicletypeimage(vehicle_type_id,image_name,image_url) values(#{typeId},#{imageName},#{imageUrl})")
	int insert(VehicleTypeImage vehicleTypeImage);
	
	@Select("select * from vehicletypeimage")
	List<VehicleTypeImage> findAll();
	
}
