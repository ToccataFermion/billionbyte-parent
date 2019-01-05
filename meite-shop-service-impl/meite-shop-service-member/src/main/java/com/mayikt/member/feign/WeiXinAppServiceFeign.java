package com.mayikt.member.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.mayikt.weixin.service.WeiXinAppService;

@FeignClient(name = "app-mayikt-weixin")
public interface WeiXinAppServiceFeign extends WeiXinAppService {

	// /**
	// * 功能说明： 应用服务接口
	// */
	// @GetMapping("/getApp")
	// public AppEntity getApp();
}
