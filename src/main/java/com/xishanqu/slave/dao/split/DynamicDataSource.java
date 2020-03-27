package com.xishanqu.slave.dao.split;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc
 * @Author BaoNing
 * @Time 2020/3/27 16:58
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Resource(name = "masterDataSource")
    private DataSource masterDataSource;

    @Resource(name = "slaveOneDataSource")
    private DataSource slaveOneDataSource;

    @Resource(name = "slaveTwoDataSource")
    private DataSource slaveTwoDataSource;


    @Override
    public void afterPropertiesSet() {
        setDefaultTargetDataSource(masterDataSource);
        Map<Object, Object> dataSourceMap = new HashMap<>();
        //将三个数据源set入目标数据源
        dataSourceMap.put("master", masterDataSource);
        dataSourceMap.put("slaveOne", slaveOneDataSource);
        dataSourceMap.put("slaveTwo", slaveTwoDataSource);
        setTargetDataSources(dataSourceMap);
        super.afterPropertiesSet();
    }


    @Override
    protected Object determineCurrentLookupKey() {
        String dbType = DynamicDataSourceHolder.getDbType();
        return dbType;
    }

}
