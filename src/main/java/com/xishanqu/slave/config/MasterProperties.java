package com.xishanqu.slave.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Desc
 * @Author BaoNing
 * @Time 2020/3/27 16:37
 */
@ConfigurationProperties(prefix = "spring.datasource.master")
@Component
@Data
public class MasterProperties {

    private String url;

    private String username;

    private String password;

    private String driverClassName;


}
