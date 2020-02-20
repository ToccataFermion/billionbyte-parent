package com.billionbyte.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "统一返回类")
public class BaseResponse<T> {

    @ApiModelProperty("状态码")
    private Integer code;
    @ApiModelProperty("返回描述信息")
    private String msg;
    @ApiModelProperty("返回数据对象")
    private T data;

    public BaseResponse() {

    }

    public BaseResponse(Integer code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
