package org.nova.platform.auth.config;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token JWT config.
 *
 * @author yyg
 */
@Configuration
public class SaTokenJwtConfig {

    @Bean
    public StpLogic stpLogic() {
        return new StpLogicJwtForSimple();
    }
}
