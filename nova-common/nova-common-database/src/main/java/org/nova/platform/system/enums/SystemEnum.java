package org.nova.platform.system.enums;

import lombok.Getter;

/**
 * System module enums.
 *
 * @author yyg
 */
public final class SystemEnum {

    private SystemEnum() {
    }

    @Getter
    public enum MenuType {
        BUTTON("0", "按钮"),
        MENU("1", "菜单");

        private final String code;
        private final String desc;

        MenuType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    @Getter
    public enum DisplayStatus {
        HIDE(0, "隐藏"),
        SHOW(1, "显示");

        private final Integer code;
        private final String desc;

        DisplayStatus(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    @Getter
    public enum EnableStatus {
        DISABLED("0", "停用"),
        ENABLED("1", "启用");

        private final String code;
        private final String desc;

        EnableStatus(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
