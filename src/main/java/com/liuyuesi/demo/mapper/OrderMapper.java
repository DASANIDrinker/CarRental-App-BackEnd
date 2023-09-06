package com.liuyuesi.demo.mapper;

import java.util.List;

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

import com.liuyuesi.demo.entity.Location;
import com.liuyuesi.demo.entity.Order;
import com.liuyuesi.demo.entity.OrderStatus;
import com.liuyuesi.demo.entity.PaymentOrderInfo;
import com.liuyuesi.demo.entity.Vehicle;
import com.liuyuesi.demo.entity.VehicleType;

@Mapper
public interface OrderMapper {

	@Select("select * from orders")
	@Results(id = "orders",
		value = {
		@Result(column = "order_id", property = "orderId", id = true),
		@Result(column = "vehicle_id", property = "vehicle", javaType = Vehicle.class, one = @One(select = "com.liuyuesi.demo.mapper.VehicleMapper.findVehicleById")),
		@Result(column = "vehicle_type_id", property = "vehicleType", javaType = VehicleType.class, one = @One(select = "com.liuyuesi.demo.mapper.VehicleTypeMapper.findVehicleTypeById")),
		@Result(column = "access_token", property = "accessToken"),
		@Result(column = "start_date", property = "startDate"),
		@Result(column = "end_date", property = "endDate"),
		@Result(column = "pickup_location_id", property = "pickupLocation", javaType = Location.class, one = @One(select = "com.liuyuesi.demo.mapper.LocationMapper.getLocationById")),
		@Result(column = "dropoff_location_id", property = "dropoffLocation", javaType = Location.class, one = @One(select = "com.liuyuesi.demo.mapper.LocationMapper.getLocationById")),
		@Result(column = "order_status_id", property = "orderStatus", javaType = OrderStatus.class, one = @One(select = "com.liuyuesi.demo.mapper.OrderStatusMapper.findById")),
		@Result(column = "days", property = "days"),
		@Result(column = "total", property = "total"),	
		@Result(column = "sign", property = "paymentOrderInfo", javaType = PaymentOrderInfo.class, one = @One(select = "com.liuyuesi.demo.mapper.PaymentOrderMapper.findPaymentOrderBySign")),
		@Result(column = "name", property = "name")
	})
	Cursor<Order> findAll();
	
	@Select("select * from orders where access_token = #{accessToken}")
	@ResultMap("orders")
	Cursor<Order> getOrderByUserId(String accessToken);
	
	
	@Select("select * from orders where order_id = #{orderId}")
	@ResultMap("orders")
	List<Order> getOrderById(int orderId);
	
	//如果insert时不加PK 那么会返回自增的PK的值 如果加了PK就按照添加的PK的值来添加
	@Insert("insert into orders(order_id,id,phone,vehicle_id,vehicle_type_id,access_token,start_date,end_date,pickup_location_id,dropoff_location_id,order_status_id,days,total,sign,name) "
			+ " values(#{order.orderId},#{order.id},#{order.phone},#{order.vehicle.vehicleId},#{order.vehicleType.vehicleTypeId},#{order.accessToken},#{order.startDate},#{order.endDate},#{order.pickupLocation.locationId},#{order.dropoffLocation.locationId},#{order.orderStatus.orderStatusId},#{order.days},#{order.total},#{order.paymentOrderInfo.sign},#{order.name})")
	@Options(useGeneratedKeys = true, keyColumn = "order_id", keyProperty = "order.orderId")
//	@Insert("insert ignore into orders(order_id,id,phone,vehicle_id,vehicle_type_id,access_token,start_date,end_date,pickup_location_id,dropoff_location_id,order_status_id,days,total,sign) "
//			+ " values(#{orderId},#{id},#{phone},#{vehicle.vehicleId},#{vehicleType.vehicleTypeId},#{accessToken},#{startDate},#{endDate},#{pickupLocation.locationId},#{dropoffLocation.locationId},#{orderStatus.orderStatusId},#{days},#{total},#{paymentOrderInfo.sign})")
	int insert(@Param("order") Order order);
	
	
	@Update("update orders set id = #{order.id}, phone = #{order.phone}, vehicle_type_id = #{order.vehicleType.vehicleTypeId}, "
			+ "vehicle_id = #{order.vehicle.vehicleId}, start_date = #{order.startDate}, end_date = #{order.endDate}, "
			+ "days = #{order.days}, total = #{order.total}, name = #{order.name} where order_id = #{order.orderId}")
	int updateOrder(@Param("order") Order order);
	
}
