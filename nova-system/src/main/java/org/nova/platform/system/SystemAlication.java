package org.nova.platform.system;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Description: qpay-business 业务渠道逻辑模块
 *
 * @author yyg
 * @date 2023/4/12 17:26
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
@ComponentScan("org.nova.platform")
@MapperScan("org.nova.platform.**.dao")
@Slf4j
public class SystemAlication {
    public static void main(String[] args) {
        SpringApplication.run(SystemAlication.class,args);
        log.info("******  system started ******");
    }
}
