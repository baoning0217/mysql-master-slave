package com.xishanqu.slave.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @Desc
 * @Author BaoNing
 * @Time 2020/3/27 16:51
 */
@Configuration
@Log4j2
public class DataSourceConfig {

    @Autowired
    private MasterProperties masterProperties;

    @Autowired
    private SlaveOneProperties slaveOneProperties;

    @Autowired
    private SlaveTwoProperties slaveTwoProperties;


    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterProperties(){
        log.info("masterDataSource初始化");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(masterProperties.getUrl());
        dataSource.setUsername(masterProperties.getUsername());
        dataSource.setPassword(masterProperties.getPassword());
        dataSource.setDriverClassName(masterProperties.getDriverClassName());
        return dataSource;
    }


    @Bean(name = "slaveOneDataSource")
    public DataSource dataBaseSlaveOneDataSource(){
        log.info("slaveOneDataSource初始化");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(slaveOneProperties.getUrl());
        dataSource.setUsername(slaveOneProperties.getUsername());
        dataSource.setPassword(slaveOneProperties.getPassword());
        dataSource.setDriverClassName(slaveOneProperties.getDriverClassName());
        return dataSource;
    }


    @Bean(name = "slaveTwoDataSource")
    public DataSource dataBaseSlaveTwoDataSource(){
        log.info("slaveTwoDataSource初始化");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(slaveTwoProperties.getUrl());
        dataSource.setUsername(slaveTwoProperties.getUsername());
        dataSource.setPassword(slaveTwoProperties.getPassword());
        dataSource.setDriverClassName(slaveTwoProperties.getDriverClassName());
        return dataSource;
    }

}
