package org.nova.platform.common.database.config;


import com.mybatisflex.core.audit.AuditManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Description: mybatis config
 *
 * @author yyg
 * @date 2023/4/25 16:40
 */
@Configuration
public class MyBatisFlexConfiguration  {
    @Value("${spring.datasource.db-type:mysql}")
    private String dbType;

    private static final Logger logger = LoggerFactory
            .getLogger("mybatis-flex-sql");


    public MyBatisFlexConfiguration() {
        //开启审计功能
        AuditManager.setAuditEnable(true);

        //设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage ->
                logger.info("{},{}ms", auditMessage.getFullSql()
                        , auditMessage.getElapsedTime())
        );
    }

//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor(){
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
//        //设置数据类型
//        paginationInnerInterceptor.setDbType(DbType.getDbType(dbType));
//        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
//        paginationInnerInterceptor.setOverflow(true);
//        //添加分页插件
//        interceptor.addInnerInterceptor(paginationInnerInterceptor);
//        return interceptor;
//    }

//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
//        paginationInnerInterceptor.setOptimizeJoin(true);
//        paginationInnerInterceptor.setDbType(DbType.ORACLE_12C);
//        paginationInnerInterceptor.setOverflow(true);
//        interceptor.addInnerInterceptor(paginationInnerInterceptor);
//        OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();
//        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);
//        return interceptor;
//    }
}
