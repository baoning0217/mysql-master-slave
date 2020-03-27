package com.xishanqu.slave.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Desc
 * @Author BaoNing
 * @Time 2020/3/27 16:40
 */
@ConfigurationProperties(prefix = "spring.datasource.slave-two")
@Component
@Data
public class SlaveTwoProperties {

    private String url;

    private String username;

    private String password;

    private String driverClassName;


}
