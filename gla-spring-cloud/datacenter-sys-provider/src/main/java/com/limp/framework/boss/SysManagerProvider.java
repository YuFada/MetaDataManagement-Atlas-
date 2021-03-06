package com.limp.framework.boss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
@EnableDiscoveryClient//服务发现
//@EnableCaching  //2 开启缓存
@ComponentScan(basePackages = "com.limp.framework.boss")
@MapperScan("com.limp.framework.boss.mapper.oracle")
//开启定时任务
@EnableScheduling
public class SysManagerProvider
{
	public static void main(String[] args)
	{
		SpringApplication.run(SysManagerProvider.class, args);
		System.out.println("/****************SysManagerProvider_8011提供者启动成功*************/");
	}
}
