package com.xishanqu.slave.dao.split;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * @Desc
 * @Author BaoNing
 * @Time 2020/3/27 17:06
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class })
})
@Component
@Log4j2
public class DynamicDataSourceInterceptor implements Interceptor {

    // 验证是否为写SQL的正则表达式
    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    /**
     * 主要的拦截方法
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 判断当前是否被事务管理
        boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
        String lookupKey = DynamicDataSourceHolder.DB_MASTER;
        if (!synchronizationActive) {
            //如果是非事务的，则再判断是读或者写。
            // 获取SQL中的参数
            Object[] objects = invocation.getArgs();
            // object[0]会携带增删改查的信息，可以判断是读或者是写
            MappedStatement ms = (MappedStatement) objects[0];
            // 如果为读，且为自增id查询主键，则使用主库
            // 这种判断主要用于插入时返回ID的操作，由于日志同步到从库有延时
            // 所以如果插入时需要返回id，则不适用于到从库查询数据，有可能查询不到
            if (ms.getSqlCommandType().equals(SqlCommandType.SELECT) && ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                lookupKey = DynamicDataSourceHolder.DB_MASTER;
            } else {
                BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
                // 正则验证
                if (sql.matches(REGEX)) {
                    // 如果是写语句
                    lookupKey = DynamicDataSourceHolder.DB_MASTER;
                } else {
                    List<String> listStr = new ArrayList<>();
                    listStr.add(DynamicDataSourceHolder.DB_SLAVE_ONE);
                    listStr.add(DynamicDataSourceHolder.DB_SLAVE_TWO);
                    lookupKey = RandomUtil.randomEle(listStr);
                }
            }
        } else {
            // 如果是通过事务管理的，一般都是写语句,直接通过主库
            lookupKey = DynamicDataSourceHolder.DB_MASTER;
        }
        log.info("在" + lookupKey + "中进行操作");
        DynamicDataSourceHolder.setDbType(lookupKey);
        // 最后直接执行SQL
        return invocation.proceed();
    }

    /**
     * 返回封装好的对象，或代理对象
     */
    @Override
    public Object plugin(Object target) {
        // 如果存在增删改查，则直接拦截下来，否则直接返回
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    /**
     * 类初始化的时候做一些相关的设置
     */
    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub

    }

}
