package com.mayikt.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayikt.member.MemberService;
import com.mayikt.member.feign.WeiXinAppServiceFeign;
import com.mayikt.weixin.entity.AppEntity;
import com.mayikt.weixin.service.WeiXinAppService;
import com.netflix.discovery.converters.Auto;

/**
 * 
 * 
 * @description:会员服务接口实现
 * @author: 97后互联网架构师-余胜军
 * @contact: QQ644064779、微信yushengjun644 www.mayikt.com
 * @date: 2019年1月3日 下午3:03:17
 * @version V1.0
 * @Copyright 该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@RestController
public class MemberServiceImpl implements MemberService {
	@Autowired
	private WeiXinAppServiceFeign weiXinAppServiceFeign;

	@GetMapping("/memberInvokeWeixin")
	public AppEntity memberInvokeWeixin() {
		return weiXinAppServiceFeign.getApp();
	}

}
