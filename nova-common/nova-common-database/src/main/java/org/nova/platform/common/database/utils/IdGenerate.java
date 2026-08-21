package org.nova.platform.common.database.utils;

import com.mybatisflex.core.keygen.impl.SnowFlakeIDKeyGenerator;

/**
 * @author: yyg
 * @since: 2026/8/18 15:49
 */
public class IdGenerate {

        private static final SnowFlakeIDKeyGenerator GENERATOR =
                new SnowFlakeIDKeyGenerator();

        public static Long getId() {
            return (Long) GENERATOR.generate(null, null);
        }

        public static String getIdStr() {
            return String.valueOf( GENERATOR.generate(null, null));
        }
}
