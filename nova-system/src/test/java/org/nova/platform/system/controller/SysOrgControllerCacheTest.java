package org.nova.platform.system.controller;

import org.junit.jupiter.api.Test;
import org.nova.platform.system.entity.SysOrg;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SysOrgControllerCacheTest {

    @Test
    void controllerUsesSpringCacheAnnotationsForOrganizationTree() throws Exception {
        Method tree = SysOrgController.class.getMethod("tree");
        Cacheable cacheable = tree.getAnnotation(Cacheable.class);
        assertNotNull(cacheable);
        assertArrayEquals(new String[]{"system:org:tree"}, cacheable.cacheNames());
        assertEquals("'tree'", cacheable.key());

        assertEvictsTreeCache(SysOrgController.class.getMethod("saveOrUpdate", SysOrg.class));
        assertEvictsTreeCache(SysOrgController.class.getMethod("removeById", Serializable.class));
        assertEvictsTreeCache(SysOrgController.class.getMethod("removeByIds", List.class));
    }

    private void assertEvictsTreeCache(Method method) {
        CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
        assertNotNull(cacheEvict);
        assertArrayEquals(new String[]{"system:org:tree"}, cacheEvict.cacheNames());
        assertTrue(cacheEvict.allEntries());
    }
}
