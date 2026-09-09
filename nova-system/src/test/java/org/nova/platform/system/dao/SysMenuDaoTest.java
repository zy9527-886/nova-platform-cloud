package org.nova.platform.system.dao;

import org.apache.ibatis.annotations.Select;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SysMenuDaoTest {

    @Test
    void userResourceQueryIncludesAncestorsOfGrantedMenus() throws NoSuchMethodException {
        Select select = SysMenuDao.class
                .getMethod("selectUserResources", String.class)
                .getAnnotation(Select.class);

        assertTrue(select.value()[0].contains("WITH RECURSIVE"));
    }
}
