package com.billionbyte.pojo;

import java.sql.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel(value = "用户信息实体类")
public class UserDo {

    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String userName;
    /**
     * 密码
     */
    @ApiModelProperty("用户密码")
    private String password;
    /**
     * 邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String email;

    /**
     * 性别 1 男 0女
     */
    @ApiModelProperty("用户性别 1男 0女")
    private String sex;
    /**
     * 手机号码
     */
    @ApiModelProperty("用户电话号码")
    private String mobile;



}
