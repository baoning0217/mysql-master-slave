package com.xishanqu.slave.dao.split;

import lombok.extern.log4j.Log4j2;

/**
 * @Desc
 * @Author BaoNing
 * @Time 2020/3/27 17:02
 */
@Log4j2
public class DynamicDataSourceHolder {

    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static final String DB_MASTER = "master";
    public static final String DB_SLAVE_ONE="slaveOne";
    public static final String DB_SLAVE_TWO="slaveTwo";

    /**
     * @Description: 获取线程的DbType
     */
    public static String getDbType() {
        String db = contextHolder.get();
        if(db==null) {
            db = "master";
        }
        return db;
    }
    /**
     * @Description: 设置线程的DbType
     */
    public static void setDbType(String str) {
        log.info("所使用的数据源为:"+str);
        contextHolder.set(str);
    }

    /**
     * @Description: 清理连接类型
     */
    public static void clearDbType() {
        contextHolder.remove();
    }

}
