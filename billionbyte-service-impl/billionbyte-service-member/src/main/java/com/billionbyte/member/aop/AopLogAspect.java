package com.billionbyte.member.aop;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

@Aspect
@Component
@Slf4j
public class AopLogAspect {

    // 申明一个切点
    @Pointcut("execution(public * com.billionbyte.member.contronller.*.*(..))")
    private void serviceAspect() {
    }
    //Threadlocal计算响应时间
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    // 请求method前打印内容
    @Before(value = "serviceAspect()")
    public void methodBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        TIME_THREADLOCAL.set(System.currentTimeMillis());
        // 打印请求内容
         log.warn("===============请求内容===============");
         log.info("请求时间"+df.format(new Date()));
         log.info("请求地址:" + request.getRequestURL().toString());
         log.info("请求方式:" + request.getMethod());
         log.info("请求类方法:" + joinPoint.getSignature());
         log.info("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
         log.info("===============请求内容===============");

    }

    // 在方法执行完结后打印返回内容
    @AfterReturning(returning = "o", pointcut = "serviceAspect()")
    public void methodAfterReturing(Object o) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        long timecost=System.currentTimeMillis()- TIME_THREADLOCAL.get();
        TIME_THREADLOCAL.remove();
         log.info("--------------返回内容----------------");
         log.info("返回时间:"+df.format(new Date()));
         log.info("响应时间:"+timecost+"ms");
         log.info("Response内容:" +JSONObject.toJSONString(o));
         log.info("--------------返回内容----------------");
    }

}
