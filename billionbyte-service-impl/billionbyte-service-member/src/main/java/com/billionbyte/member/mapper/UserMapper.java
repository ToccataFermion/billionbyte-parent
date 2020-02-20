package com.billionbyte.member.mapper;


import com.billionbyte.pojo.UserDo;
import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.query.Param;




@Mapper
public interface UserMapper {

	@Insert("INSERT INTO `billionbyte_user` VALUES (null,#{userName},#{password}, #{mobile},#{email}, #{sex} );")
	int egister(UserDo userDo);

	@Select("SELECT * FROM billionbyte_user WHERE userId=#{userId};")
	UserDo findByUserId(@Param("userId") Long userId);

	@Update("UPDATE billionbyte_user SET password =#{0} WHERE userId=#{1}")
	int updateUserPassword(@Param("password") String password, @Param("userId") Long userId);

	@Delete("DELETE FROM billionbyte_user WHERE userId=#{1}")
	int deleteUserByUserId(@Param("userId")Long userId);
}
