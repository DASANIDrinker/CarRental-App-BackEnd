package com.liuyuesi.demo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.cursor.Cursor;

import com.liuyuesi.demo.entity.User;

@Mapper
public interface UserMapper {

	//获取所有用户
	@Select("select * from user")
	Cursor<User> findAll();
	
	//插入新用户
	//如果有相同的unique值插入 会ignore
	//如果insert时不加PK 那么会返回自增的PK的值 如果加了PK就按照添加的PK的值来添加
	@Insert("insert ignore into user(open_id,session_key,access_token) values(#{openId},#{sessionKey},#{accessToken})")
	@Options(useGeneratedKeys = true, keyColumn = "open_id", keyProperty = "openId")
	int insert(User user);
	
	//通过openId PK找到accessToken(自定义标识)
	@Select("select access_token from user where open_id= #{openId}")
	Integer findAccessTokenByOpenId(String openId);
	
	//通过accessToken找到openID
	@Select("select id from user where access_token = #{accessToken}")
	Integer findIdbyAccessToken(int accessToken);
	
	//通过openId找到用户
	User findUserByOpenId(String openId);

	//通过accessToken找到用户
	User findUserByAccessToken(int accessToken);
	
	//通过openId找到sessionKey
	@Select("select session_key from user where open_id = #{openId}")
	String findSessionKeyByOpenId(String openId);
	
	//检查accessToken是否为独特的
	@Select("select count(access_token) from user where access_token = #{accessToken}")
	int isATDuplicate(int accessToken);

	@Update("update user set phone = #{phone} where access_token = #{accessToken} ")
	int updatePhone(String phone, String accessToken);
}
