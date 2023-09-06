package com.liuyuesi.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import com.liuyuesi.demo.entity.OrderStatus;

@Mapper
public interface OrderStatusMapper {
	
	@Select("select * from orderstatus")
	public List<OrderStatus> findAll();
	
	@Select("select * from orderstatus where order_status_id = #{orderStatusId}")
	public OrderStatus findById(byte orderStatusId);
	
	//如果insert时不加PK 那么会返回自增的PK的值 如果加了PK就按照添加的PK的值来添加
	@Options(useGeneratedKeys = true, keyColumn = "order_status_id", keyProperty = "orderStatusId")
	@Insert("insert ignore into orderstatus(order_status_id,order_description) values(#{orderStatusId},#{orderDescription}})")
	public int insert(OrderStatus orderStatus);
	
}
