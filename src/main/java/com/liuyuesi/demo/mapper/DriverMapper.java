package com.liuyuesi.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.cursor.Cursor;

import com.liuyuesi.demo.entity.Driver;

@Mapper
public interface DriverMapper {

//	@Insert("insert ignore into driver(id,name,birth,address,gender,nationality,car_class,access_token,phone) values(#{driver.id},#{driver.name},#{driver.birth},#{driver.address},#{driver.gender},#{driver.nationality},#{driver.carClass},#{driver.accessToken},#{driver.phone})")
//	int insert(@Param("driver") Driver driver);
	
	@Insert("insert ignore into driver(id,name,birth,address,gender,nationality,car_class,access_token,phone) values(#{id},#{name},#{birth},#{address},#{gender},#{nationality},#{carClass},#{accessToken},#{phone})")
	int insert(Driver driver);
	
	@Select("select * from driver")
	@Results(id = "driver", value = { @Result(column = "id", property = "id", id = true),
			@Result(column = "name", property = "name"), @Result(column = "birth", property = "birth"),
			@Result(column = "address", property = "address"), @Result(column = "gender", property = "gender"),
			@Result(column = "nationality", property = "nationality"),
			@Result(column = "car_class", property = "carClass"),
			@Result(column = "access_token", property = "accessToken"),
			@Result(column = "phone", property = "phone"),
			@Result(column = "id_front_url", property = "idFrontUrl"),
			@Result(column = "id_back_url", property = "idBackUrl"),
			@Result(column = "license_url", property = "licenseUrl"),})
	Cursor<Driver> getAll();

	@Select("select * from driver where id = #{id}")
	@ResultMap("driver")
	Driver getDriverById(String id);

	@Update("update driver set name = #{name}, phone = #{phone}, birth = #{birth}, address = #{address},"
			+ "gender = #{gender}, nationality = #{nationality}, car_class = #{carClass} where id = #{id}")
	int update(Driver driver);
	
	@Delete("delete from driver where id in (${ids})")
	int delete(String ids);
	
	
	@Select("select * from driver where access_token = #{accessToken}")
	@ResultMap("driver")
	List<Driver> getDriverByAT(Integer accessToken);

	@Delete("delete from driver where id = #{id}")
	int deleteDriver(String id);
	
	@Update("update driver set id_front_url = #{url} where id = #{id}")
	int updateFrontIdUrl(String id, String url); 
	
	@Update("update driver set id_back_url = #{url} where id = #{id}")
	int updateBackIdUrl(String id, String url);
	
	@Update("update driver set license_url = #{url} where id = #{id}")
	int updateLicenseUrl(String id, String url);
	
	@Update("update driver set id_front_url = null where id = #{id}")
	int deleteFrontIdUrl(String id);
	
	@Update("update driver set id_back_url = null where id = #{id}")
	int deleteBackIdUrl(String id);
	
	@Update("update driver set license_url = null where id = #{id}")
	int deleteLicenseUrl(String id);
}
