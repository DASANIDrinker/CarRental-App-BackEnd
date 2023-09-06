package com.liuyuesi.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.cursor.Cursor;

import com.liuyuesi.demo.entity.Location;
import com.liuyuesi.demo.entity.OrderStatus;
import com.liuyuesi.demo.entity.PaymentOrderInfo;
import com.liuyuesi.demo.entity.Reservation;
import com.liuyuesi.demo.entity.Vehicle;
import com.liuyuesi.demo.entity.VehicleType;

@Mapper
public interface ReservationMapper {

	@Select("select * from reservation")
	@Results(id = "reservation",
	value = {
	@Result(column = "reservation_id", property = "reservationId", id = true),
	@Result(column = "vehicle_id", property = "vehicle", javaType = Vehicle.class, one = @One(select = "com.liuyuesi.demo.mapper.VehicleMapper.findVehicleById")),
	@Result(column = "vehicle_type_id", property = "vehicleType", javaType = VehicleType.class, one = @One(select = "com.liuyuesi.demo.mapper.VehicleTypeMapper.findVehicleTypeById")),
	@Result(column = "access_token", property = "accessToken"),
	@Result(column = "start_date", property = "startDate"),
	@Result(column = "end_date", property = "endDate"),
	@Result(column = "pickup_location_id", property = "pickupLocation", javaType = Location.class, one = @One(select = "com.liuyuesi.demo.mapper.LocationMapper.getLocationById")),
	@Result(column = "dropoff_location_id", property = "dropoffLocation", javaType = Location.class, one = @One(select = "com.liuyuesi.demo.mapper.LocationMapper.getLocationById")),
	@Result(column = "days", property = "days"),
	@Result(column = "total", property = "total"),	
	@Result(column = "sign", property = "paymentOrderInfo", javaType = PaymentOrderInfo.class, one = @One(select = "com.liuyuesi.demo.mapper.PaymentOrderMapper.findPaymentOrderBySign")),
	@Result(column = "name", property = "name"),
	@Result(column = "id", property = "id"),
	@Result(column = "phone", property = "phone")
})
	Cursor<Reservation> findAll();
	
	@Options(useGeneratedKeys = true, keyColumn = "reservation_id", keyProperty = "reservationId")
	@Insert("insert into reservation(name,id,phone,access_token,vehicle_id,vehicle_type_id,start_date,end_date,pickup_location_id,dropoff_location_id,days,total,sign)"
			+ " values(#{reservation.name}, #{reservation.id}, #{reservation.phone}, #{reservation.accessToken},#{reservation.vehicle.vehicleId},#{reservation.vehicleType.vehicleTypeId},#{reservation.startDate},#{reservation.endDate},"
			+ "#{reservation.pickupLocation.locationId},#{reservation.dropoffLocation.locationId},#{reservation.days},#{reservation.total},#{reservation.paymentOrderInfo.sign})")
	int insertReservation(@Param("reservation") Reservation reservation);

	@Update("update reservation set name = #{reservation.name}, phone = #{reservation.phone}, id = #{reservation.id}, reservation_id = #{reservation.reservationId}"
			+ ", vehicle_id = #{reservation.vehicle.vehicleId}, vehicle_type_id = #{reservation.vehicleType.vehicleTypeId}, start_date = #{reservation.startDate}, end_date =#{reservation.endDate}"
			+ ", total = #{reservation.total}, days = #{reservation.days} where reservation_id = #{reservation.reservationId}")
	int update(@Param("reservation") Reservation reservation);
	
	@Delete("delete from reservation where reservation_id in (${reservationIds})")
	int delete(String reservationIds);
}
