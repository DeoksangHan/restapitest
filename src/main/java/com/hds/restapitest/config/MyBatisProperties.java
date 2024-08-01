package com.hds.restapitest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "spring.mybatis") //*.properties, *.yml 에 있는 property 가져온다.
public class MyBatisProperties {
	private String mapperLocations;
	private String typeAliasesPackage;

}
