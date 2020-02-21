package com.billionbyte.member.contronller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.billionbyte.base.BaseApiService;
import com.billionbyte.base.BaseResponse;
import com.billionbyte.constants.Constants;
import com.billionbyte.member.service.MemberService;
import com.billionbyte.pojo.UserDo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.search.aggregations.Aggregation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "用户接口服务")
public class MemberContonller extends BaseApiService {

    @Autowired
    MemberService memberService;
    @ApiOperation(value = "测试接口 ",notes = "详细说明",httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code=203,message="用户信息不存在"),
            @ApiResponse(code=400,message="参数错误"),
    })
    @PostMapping("/member/test")
    public String member() {
        return "ok";
    }

    @ApiOperation(value="插入新用户数据",notes = "输入UserDo插入用户数据,userId不可指定")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="userId",dataType="Integer",required=false,value="用户id",defaultValue="0"),
            @ApiImplicitParam(paramType="query",name="userName",dataType="String",required=true,value="用户名",defaultValue="无名"),
            @ApiImplicitParam(paramType="query",name="password",dataType="String",required=true,value="用户的密码",defaultValue="321"),
            @ApiImplicitParam(paramType="query",name="email",dataType="String",required=false,value="用户的邮箱",defaultValue="321@qq.com"),
            @ApiImplicitParam(paramType="query",name="sex",dataType="String",required=false,value="用户的性别(男为1，女为0)",defaultValue="1"),
            @ApiImplicitParam(paramType="query",name="mobile",dataType="String",required=false,value="用户的电话",defaultValue="124556")
    })
    @ApiResponses({
            @ApiResponse(code=203,message="用户信息不存在"),
            @ApiResponse(code=400,message="参数错误"),
    })
    @PostMapping("/member/post")
    public BaseResponse<JSONObject> insertMember(UserDo userDo) {
        //1验证各种参数等
        String userName=userDo.getUserName();
        if(userName.isEmpty()){
            return setResultError(Constants.HTTP_RES_CODE_PARAM_ERROR_400,"参数（用户名）不能为空");
        }
        //2do-dto转换(略)
        return memberService.insertMember(userDo)>0? setResultSuccess("注册成功") :setResultError("注册失败");

    }


    @ApiOperation(value = "更新用户密码 ",notes = "输入UserDo更新用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="userId",dataType="Long",required=true,value="用户的ID",defaultValue="1"),
            @ApiImplicitParam(paramType="query",name="userPassword",dataType="String",required=true,value="用户的密码",defaultValue="321")
    })
    @ApiResponses({
            @ApiResponse(code=203,message="用户信息不存在"),
            @ApiResponse(code=400,message="参数错误"),
    })
    @PutMapping("/member/put")
    public BaseResponse<JSONObject> updateMember(String userPassword,Long userId) {
        //1参数验证//2do-dto转换//3查询数据库4返回JSON对象
        if(userPassword.isEmpty()){
            return setResultError(Constants.HTTP_RES_CODE_PARAM_ERROR_400,"密码不能为空");
        }
        if(userId==null){
            return setResultError(Constants.HTTP_RES_CODE_PARAM_ERROR_400,"id不能为空");
        }
        return  memberService.updateMember(userPassword,userId)> 0 ? setResultSuccess("修改密码成功") :setResultError(Constants.HTTP_RES_CODE_NOTEXIST_203,"修改密码失败");
    }


    @ApiOperation(value = "查询用户信息 ",notes = "输入用户id查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="userId",dataType="Long",required=true,value="用户的ID",defaultValue="1")
    })
    @ApiResponses({
            @ApiResponse(code=203,message="用户信息不存在"),
            @ApiResponse(code=400,message="参数错误"),
    })
    @GetMapping("/member/get")
    public BaseResponse<UserDo> getMember(Long userId) {
        //1参数验证 2do-dto转换//3查询数据库 4返回JSON对象
        if(userId==null){
            return setResultError(Constants.HTTP_RES_CODE_PARAM_ERROR_400,"参数不能为空");
        }
        UserDo userEntity=memberService.getMember(userId);
        if(userEntity==null){
            return  setResultError(Constants.HTTP_RES_CODE_NOTEXIST_203 ,"用户信息不存在");
        }
        JSONObject data=new JSONObject();
        data.put("data",userEntity);
        return setResultSuccess(data);
    }

    @ApiOperation("删除用户信息(慎)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="userId",dataType="Long",required=true,value="用户的ID",defaultValue="1")
    })
    @ApiResponses({
            @ApiResponse(code=203,message="用户信息不存在"),
            @ApiResponse(code=400,message="参数错误"),
    })
    @DeleteMapping("/member/delete")
    public BaseResponse<JSONObject> deleteMember(Long userId) {
        //1参数验证 2do-dto转换//3查询数据库 4返回状态码
        if(userId==null){
            return setResultError(Constants.HTTP_RES_CODE_PARAM_ERROR_400,"参数不能为空");
        }
        int result=memberService.deleteMember(userId);
        if(result==0){
            return  setResultError(Constants.HTTP_RES_CODE_NOTEXIST_203 ,"用户信息不存在");
        }
        return setResultSuccess("删除成功");
    }
    @ApiOperation("根据用户名搜索用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="userName",dataType="String",required=true,value="用户名",defaultValue="name")
    })
    @ApiResponses({
            @ApiResponse(code=203,message="用户信息不存在"),
            @ApiResponse(code=400,message="参数错误"),
    })
    @GetMapping("/member/search")
    public  BaseResponse<List<UserDo>> searchByNameMember(String userName) {
        List<UserDo> UserDolist=null;
        try {

            UserDolist = memberService.searchByNameMember(userName);
        } catch (IOException e){
            e.printStackTrace();
            return setResultError(Constants.HTTP_RES_CODE_500,"内部错误");
        }
        JSONObject data=new JSONObject();
        data.put("data",UserDolist);
        return  setResultSuccess(data);
    }
    @ApiOperation("桶聚合嵌套桶聚合再嵌套指标聚合查询：先按sex分组再按userId区间分组，统计每组文档数sum")
    @ApiResponses({
            @ApiResponse(code=400,message="参数错误"),
    })
    @GetMapping("/member/countGroupByuserIdandSex")
    public  BaseResponse<JSONObject> countGroupByuserIdandSex() {
        String result=null;
        try {

            result = memberService.countGroupByuserIdandSex();
        } catch (IOException e){
            e.printStackTrace();
            return setResultError(Constants.HTTP_RES_CODE_500,"内部错误");
        }
        JSONObject data=new JSONObject();
        Map mapType = JSON.parseObject(result,Map.class);
        data.put("data",mapType);
        return  setResultSuccess(data);
    }
    @ApiOperation("桶聚合嵌套指标聚合查询：按sex分组统计每个sex组的avg_userId")
    @ApiResponses({
            @ApiResponse(code=400,message="参数错误"),
            @ApiResponse(code=500,message="内部错误"),
    })
    @GetMapping("/member/avgUserIdGroupBySex")
    public  BaseResponse<JSONObject> avgUserIdGroupBySex(){
        String result=new String();
        try {

            result = memberService.avgUserIdGroupBySex();
        } catch (IOException e){
            e.printStackTrace();
            return setResultError(Constants.HTTP_RES_CODE_500,"内部错误");
        }
        JSONObject data=new JSONObject();
        Map map=JSON.parseObject(result,Map.class);
        data.put("data",map);
        return  setResultSuccess(data);
    }


    @ApiOperation(value="插入新用户数据到ES",notes = "输入UserDo插入用户数据,")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query" ,name="userId",dataType="int",required=true,value="用户id",defaultValue="1"),
            @ApiImplicitParam(paramType="query",name="userName",dataType="String",required=true,value="用户名",defaultValue="name1"),
            @ApiImplicitParam(paramType="query",name="password",dataType="String",required=true,value="用户的密码",defaultValue="password1"),
            @ApiImplicitParam(paramType="query",name="email",dataType="String",required=true,value="用户的邮箱",defaultValue="1@qq.com"),
            @ApiImplicitParam(paramType="query",name="sex",dataType="String",required=true,value="用户的性别(男为1，女为0)",defaultValue="1"),
            @ApiImplicitParam(paramType="query",name="mobile",dataType="String",required=true,value="用户的电话",defaultValue="1")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="参数错误"),
            @ApiResponse(code=500,message="内部错误"),
    })
    @PostMapping("/member/insertMemeberToES")
    public BaseResponse<JSONObject> insertMemeberToES(UserDo userDo) {
        //1验证各种参数等
        JSONObject data=new JSONObject();
        try {

            String response=memberService.insertMemeberToES(userDo);
            data.put("data",response);
        }catch (Exception e){
            e.printStackTrace();
            return setResultError(Constants.HTTP_RES_CODE_500,"内部错误");
        }

        return  setResultSuccess(data);
    }
    @ApiOperation(value="更新新用户数据到ES",notes = "输入UserDo更新用户数据,")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="userId",dataType="int",required=true,value="用户id",defaultValue="1"),
            @ApiImplicitParam(paramType="query",name="userName",dataType="String",required=true,value="用户名",defaultValue="name1"),
            @ApiImplicitParam(paramType="query",name="password",dataType="String",required=true,value="用户的密码",defaultValue="password1"),
            @ApiImplicitParam(paramType="query",name="email",dataType="String",required=true,value="用户的邮箱",defaultValue="111@qq.com"),
            @ApiImplicitParam(paramType="query",name="sex",dataType="String",required=true,value="用户的性别(男为1，女为0)",defaultValue="1"),
            @ApiImplicitParam(paramType="query",name="mobile",dataType="String",required=true,value="用户的电话",defaultValue="1")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="参数错误"),
            @ApiResponse(code=500,message="内部错误"),
    })
    @PostMapping("/member/updateMemeberToES")
    public BaseResponse<JSONObject> updateMemeberToES(UserDo userDo) {
        //1验证各种参数等
        JSONObject data=new JSONObject();
        try {

            String response=memberService.updateMemberToES(userDo);
            data.put("data",response);
        }catch (Exception e){
            e.printStackTrace();
            return setResultError(Constants.HTTP_RES_CODE_500,"内部错误");
        }

        return  setResultSuccess(data);
    }
    @ApiOperation(value="任意字段从ES查询用户数据",notes = "输入UserDo查询用户数据,")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="userId",dataType="long",required=false,value="用户id",defaultValue="1"),
            @ApiImplicitParam(paramType="query",name="userName",dataType="String",required=false,value="用户名",defaultValue="name1"),
            @ApiImplicitParam(paramType="query",name="password",dataType="String",required=false,value="用户的密码",defaultValue="password1"),
            @ApiImplicitParam(paramType="query",name="email",dataType="String",required=false,value="用户的邮箱",defaultValue="111@qq.com"),
            @ApiImplicitParam(paramType="query",name="sex",dataType="String",required=false,value="用户的性别(男为1，女为0)",defaultValue="1"),
            @ApiImplicitParam(paramType="query",name="mobile",dataType="String",required=false,value="用户的电话",defaultValue="1")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="参数错误"),
            @ApiResponse(code=500,message="内部错误"),
    })
    @PostMapping("/member/boolSearchByAnyField")
    public BaseResponse<List<UserDo>> boolSearchByAnyField(UserDo userDo) {
        //1验证各种参数等
        JSONObject data=new JSONObject();
        try {

            List<UserDo> response;   response=memberService.boolSearchByAnyField(userDo);
            data.put("data",response);
        }catch (Exception e){
            e.printStackTrace();
            return setResultError(Constants.HTTP_RES_CODE_500,"内部错误");
        }

        return setResultSuccess(data);
    }
}
