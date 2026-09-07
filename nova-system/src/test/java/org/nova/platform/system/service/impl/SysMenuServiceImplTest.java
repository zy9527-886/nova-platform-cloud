package org.nova.platform.system.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nova.platform.common.core.exception.BusinessException;
import org.nova.platform.system.dao.SysMenuDao;
import org.nova.platform.system.dao.SysRolMenuDao;
import org.nova.platform.system.entity.SysMenu;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SysMenuServiceImplTest {

    private SysMenuDao mapper;
    private SysRolMenuDao roleMenuDao;
    private SysMenuServiceImpl service;

    @BeforeEach
    void setUp() {
        mapper = mock(SysMenuDao.class);
        roleMenuDao = mock(SysRolMenuDao.class);
        service = new SysMenuServiceImpl();
        ReflectionTestUtils.setField(service, "mapper", mapper);
        ReflectionTestUtils.setField(service, "sysRolMenuDao", roleMenuDao);
    }

    @Test
    void listOrderedSortsBySortThenMenuId() {
        when(mapper.selectAll()).thenReturn(List.of(
                menu("3", 20), menu("2", 10), menu("1", 10)
        ));

        assertEquals(List.of("1", "2", "3"), service.listOrdered().stream()
                .map(SysMenu::getMenuId).toList());
    }

    @Test
    void deletingAParentMenuIsRejected() {
        when(mapper.selectCountByQuery(any())).thenReturn(1L);

        assertThrows(BusinessException.class, () -> service.removeLeafById("menu-1"));

        verify(mapper, never()).deleteById(any());
    }

    @Test
    void deletingALeafClearsRoleRowsBeforeTheMenu() {
        when(mapper.selectCountByQuery(any())).thenReturn(0L);
        when(mapper.deleteById("menu-1")).thenReturn(1);

        assertTrue(service.removeLeafById("menu-1"));

        var order = inOrder(roleMenuDao, mapper);
        order.verify(roleMenuDao).deleteByQuery(any());
        order.verify(mapper).deleteById("menu-1");
    }

    private SysMenu menu(String id, int sort) {
        return new SysMenu().setMenuId(id).setMenuNm(id).setPrentId("0").setSort(sort);
    }
}
