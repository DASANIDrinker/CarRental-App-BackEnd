package com.liuyuesi.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.liuyuesi.demo.entity.Admin;

@Mapper
public interface AdminMapper {

	@Options(useGeneratedKeys = true, keyColumn = "admin_id", keyProperty = "adminId")
	@Insert("insert ignore into admin(admin_id,user_name,pass_word) values(#{adminId},#{userName},#{passWord})")
	int insert(Admin admin);

	@Select("select * from admin")
	@Results(id = "admin", value = { @Result(column = "admin_id", property = "adminId", id = true),
			@Result(column = "user_name", property = "userName"),
			@Result(column = "pass_word", property = "passWord") })
	List<Admin> getAll();

	@Select("select * from admin where admin_id = #{adminId}")
	@ResultMap("admin")
	Admin getAdminById(int adminId);

	@Delete("delete from admin where admin_id = #{adminId}")
	int deleteAdminById(int adminId);

	@Update("update admin set user_name = #{userName} where admin_id = #{adminId}")
	int updateAdminUNById(int adminId, String userName);

	@Update("update admin set pass_word = #{passWord} where admin_id = #{adminId}")
	int updateAdminPWById(int adminId, String passWord);

	@Update("update admin set user_name = #{userName}, pass_word = #{passWord} where admin_id = #{adminId}")
	int update(Admin admin);

	@Delete("delete from admin where admin_id in (${adminIds})")
	int delete(String adminIds);
	
	@Select("select count(admin_id) from admin where user_name = #{userName} AND pass_word = #{passWord}")
	int check(Admin admin);
}
