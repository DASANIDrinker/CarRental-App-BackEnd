package com.liuyuesi.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.liuyuesi.demo.entity.Location;

@Mapper
public interface LocationMapper {
	
	@Select("select * from location")
	List<Location> findAll();
	
	@Select("select * from location where location_id = #{locationId}")
	Location getLocationById(int locationId);
	
	//如果insert时不加PK 那么会返回自增的PK的值 如果加了PK就按照添加的PK的值来添加
	@Options(useGeneratedKeys = true, keyColumn = "location_id", keyProperty = "locationId")
	@Insert("insert ignore into location(location_id,location_brief,province,city,district,street,community,building,room)"
			+ "values(#{locationId},#{locationBrief},#{province},#{city},#{district},#{street},#{community},#{building},#{room})")
	int insert(Location location);
	
	@Delete("delete from location where location_id = #{locationId}")
	void deleteById(int locationId);
	
	@Update("update location set location_brief = #{locationBrief}, province = #{province}, city = #{city}, "
			+ "district = #{district}, street = #{street}, community = #{community}, building = #{building}, room = #{room}"
			+ "where location_id = #{locationId}")
	int update(Location location);
	
	@Delete("delete from location where location_id in (${locationIds})")
	int delete(String locationIds);
	
}
