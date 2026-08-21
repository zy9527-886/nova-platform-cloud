package org.nova.platform.common.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: swagger3 config
 *
 * @author yyg
 * @date 2021/11/24 10:14
 */
@Configuration
public class Swagger3Config {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("nova 代码平台 API")
                .description("SpringDoc API 演示")
                .version("v1.0.0")
        );
    }

}

