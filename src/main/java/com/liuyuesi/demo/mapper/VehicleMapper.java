package com.liuyuesi.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.cursor.Cursor;

import com.liuyuesi.demo.entity.Vehicle;
import com.liuyuesi.demo.entity.VehicleType;

@Mapper
public interface VehicleMapper {

	// 获取全部车辆
	@Select("select * from vehicle")
	@Results(id = "vehicle", value = { @Result(column = "vehicle_id", property = "vehicleId", id = true),
			@Result(column = "plate_number", property = "plateNumber"),
//		@Result(column = "is_radar",property = "isRadar"),
//		@Result(column = "is_camera",property = "isCamera"),
			@Result(column = "is_recorder", property = "isRecorder"), @Result(column = "is_etc", property = "isETC"),
			@Result(column = "is_mount", property = "isMount"),
			@Result(column = "is_umbrella", property = "isUmbrella"),
//		@Result(column = "is_sunroof",property = "isSunroof"),
//		@Result(column = "is_leather",property = "isLeather"),
			@Result(column = "is_occupied", property = "isOccupied"),
			@Result(column = "vehicle_type_id", property = "vehicleType", javaType = VehicleType.class, one = @One(select = "com.liuyuesi.demo.mapper.VehicleTypeMapper.findVehicleTypeById")) })
	Cursor<Vehicle> findAll();

	// 通过车型ID获取某种车型的全部可租用车辆
	@Select("select * from vehicle where is_occupied = ${false} and vehicle_type_id = #{vehicleType}")
//	@Results({
//		@Result(column = "vehicle_id", property = "vehicleId", id= true),
//		@Result(column = "plate_number",property = "plateNumber"),
////		@Result(column = "is_radar",property = "isRadar"),
////		@Result(column = "is_camera",property = "isCamera"),
//		@Result(column = "is_recorder",property = "isRecorder"),
//		@Result(column = "is_etc",property = "isETC"),
//		@Result(column = "is_mount",property = "isMount"),
//		@Result(column = "is_umbrella",property = "isUmbrella"),
////		@Result(column = "is_sunroof",property = "isSunroof"),
////		@Result(column = "is_leather",property = "isLeather"),
//		@Result(column = "is_occupied", property = "isOccupied"),
//		@Result(column = "vehicle_type_id",property = "vehicleType",javaType = VehicleType.class ,one = @One(select = "com.liuyuesi.demo.mapper.VehicleTypeMapper.findVehicleTypeById"))
//	})
	@ResultMap("vehicle")
	Cursor<Vehicle> findVehicleByTypeId(byte vehicleType);

	@Select("select * from vehicle where vehicle_id = #{id}")
	Vehicle findVehicleById(int id);

	@Select("select * from vehicle where plate_number = #{plateNumber}")
	Vehicle findVehicleByPlate(String plateNumber);

//	@Insert("insert into vehicle where plate_number = #{plateNumber} is_radar = #{isRadar} is_camera = #{isCamera} is_recorder = #{isRecorder} is_etc = #{isETC} is_mount = {#isMount} is_umbrella = #{isUmbrealla} is_sunroof = #{isSunRoof}  is_leather = #{isLeather} is_occupied = #{isOccupied} vehicle_type_id = #{vehicleTypeId} ")
//	@Options(useGeneratedKeys = true, keyColumn = "vehicle_id", keyProperty = "vehicleId")
//	int insert(Vehicle vehicle);
//	
	// 通过Param来确保ibatis知道哪个property对应哪个column
	// 如果insert时不加PK 那么会返回自增的PK的值 如果加了PK就按照添加的PK的值来添加
	@Insert("insert into vehicle(plate_number,is_recorder,is_etc,is_mount,is_umbrella,is_occupied,vehicle_type_id)  values(#{vehicle.plateNumber}, #{vehicle.isRecorder}, #{vehicle.isETC}, #{vehicle.isMount}, #{vehicle.isUmbrella}, #{vehicle.isOccupied}, #{vehicleTypeId})")
//	@Options(useGeneratedKeys = true, keyColumn = "vehicle_id", keyProperty = "vehicleId")
	@Options(useGeneratedKeys = true, keyColumn = "vehicle_id", keyProperty = "vehicle.vehicleId")
	int insert(@Param("vehicle") Vehicle vehicle, @Param("vehicleTypeId") Integer vehicleTypeId);

	// 并将车实例的租用状态更新为租用中
	@Update("update vehicle set is_occupied = ${true} where vehicle_id = #{vehicleId}")
	void updateOccupied(Vehicle vehicle);

	// 通过车型PK获取一辆该车实例
	@Select("select * from vehicle where vehicle_type_id = #{typeId} and is_occupied = ${false} limit 1")
	Vehicle findAVehicleByTypeId(int typeId);

	// 检查某一个车型是否租满(XML)
	int checkTypeIsFull(int typeId);

	// 更新一台车的属性
	@Update("update vehicle set is_etc = #{etc}, is_recorder = #{recorder}, is_mount = #{mount}, is_umbrella = #{umbrella},"
			+ "is_occupied = #{occupied} where vehicle_id = #{vehicleId}")
	int update(Short vehicleId, Boolean etc, Boolean recorder, Boolean mount, Boolean umbrella, Boolean occupied);

	// 删除一台车
	@Delete("delete from vehicle where vehicle_id in (${vehicleIds})")
	int delete(@Param("vehicleIds") String vehicleId);
}
