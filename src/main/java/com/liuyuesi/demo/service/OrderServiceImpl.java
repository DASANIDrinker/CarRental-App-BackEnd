package com.liuyuesi.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liuyuesi.demo.entity.Order;
import com.liuyuesi.demo.mapper.OrderMapper;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderMapper orderMapper;
	
	
	@Transactional
	public List<Order> findAll(){
		Cursor<Order> orders = orderMapper.findAll();
		List<Order> orderList = new ArrayList<Order>();
		for(Order order:orders) {
			orderList.add(order);
		}
		return orderList;
	}
	
	@Transactional
	public List<Order> getOrderByUserId(String accessToken){
		Cursor<Order> orders = orderMapper.getOrderByUserId(accessToken);
		System.out.println(orders);
		List<Order> orderList = new ArrayList<Order>();
		
		for(Order order:orders) {
//			//Date 格式
//			Date start = order.getStartDate();
//			Date end = order.getEndDate();
//			//String 格式
//			String startDate = start.toString();
//			String endDate = end.toString();
//			startDate = dataToString(startDate);
//			endDate = dataToString(endDate);
//			
//			start = (Date)startDate;
			
			orderList.add(order);
		}
		System.out.println(orderList);
		return orderList;
		
	}
	
	public List<Order> getOrderById(int orderId){
		return orderMapper.getOrderById(orderId);
	}
	
	public int insert(Order order) {
//		orderMapper.insert(order);
//		return order.getOrderId();
		return orderMapper.insert(order);
	}
	
	public int updateOrder(Order order) {
		return orderMapper.updateOrder(order);
	}
	
	//美国标准时间转化为标准时间
	//美国东部标准时间为EST 中国标准时间为GMT
	public static String dataToString(String dateString) {


        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'EST'Z", Locale.ENGLISH);
        Date dd = null; //将字符串改为date的格式
        try {
            dd = sdf.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String resDate= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(dd);
        System.out.println(resDate);
        return resDate;

    }
	

}
