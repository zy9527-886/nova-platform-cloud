package org.nova.platform.system.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.nova.platform.system.dao.SysMenuDao;
import org.nova.platform.system.dao.SysRolDao;
import org.nova.platform.system.dao.SysRolMenuDao;
import org.nova.platform.system.dao.SysUserRolDao;
import org.nova.platform.system.entity.SysMenu;
import org.nova.platform.system.entity.SysRol;
import org.nova.platform.system.entity.SysRolMenu;
import org.nova.platform.system.entity.dto.SysRolMenuBindDto;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SysRolServiceImplTest {

    private SysRolDao mapper;
    private SysRolMenuDao roleMenuDao;
    private SysUserRolDao userRoleDao;
    private SysMenuDao menuDao;
    private SysRolServiceImpl service;

    @BeforeEach
    void setUp() {
        mapper = mock(SysRolDao.class);
        roleMenuDao = mock(SysRolMenuDao.class);
        userRoleDao = mock(SysUserRolDao.class);
        menuDao = mock(SysMenuDao.class);
        service = new SysRolServiceImpl();
        ReflectionTestUtils.setField(service, "mapper", mapper);
        ReflectionTestUtils.setField(service, "sysRolMenuDao", roleMenuDao);
        ReflectionTestUtils.setField(service, "sysUserRolDao", userRoleDao);
        ReflectionTestUtils.setField(service, "sysMenuDao", menuDao);
    }

    @Test
    void bindMenusReplacesRowsAndRemovesDuplicateOrBlankIds() {
        SysRolMenuBindDto dto = new SysRolMenuBindDto();
        dto.setRolId("role-1");
        dto.setMenuIds(new ArrayList<>(List.of("menu-1", "menu-1", " ", "menu-2")));
        when(mapper.selectOneById("role-1")).thenReturn(new SysRol().setRolId("role-1"));
        when(menuDao.selectListByIds(any())).thenReturn(List.of(
                new SysMenu().setMenuId("menu-1"),
                new SysMenu().setMenuId("menu-2")
        ));
        when(roleMenuDao.insertBatchSelective(any())).thenReturn(2);

        assertTrue(service.bindMenus(dto));

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<SysRolMenu>> rows = ArgumentCaptor.forClass(List.class);
        verify(roleMenuDao).insertBatchSelective(rows.capture());
        assertEquals(List.of("menu-1", "menu-2"), rows.getValue().stream()
                .map(SysRolMenu::getMenuId).toList());
        assertTrue(rows.getValue().stream().allMatch(item -> "role-1".equals(item.getRolId())));
    }

    @Test
    void bindingAnEmptyListClearsAllMenusWithoutInserting() {
        SysRolMenuBindDto dto = new SysRolMenuBindDto();
        dto.setRolId("role-1");
        dto.setMenuIds(List.of());
        when(mapper.selectOneById("role-1")).thenReturn(new SysRol().setRolId("role-1"));

        assertTrue(service.bindMenus(dto));

        verify(roleMenuDao).deleteByQuery(any());
    }

    @Test
    void getMenuIdsReturnsOnlyTheAssignedIds() {
        when(roleMenuDao.selectListByQuery(any())).thenReturn(List.of(
                new SysRolMenu().setRolId("role-1").setMenuId("menu-1"),
                new SysRolMenu().setRolId("role-1").setMenuId("menu-2")
        ));

        assertEquals(List.of("menu-1", "menu-2"), service.getMenuIds("role-1"));
    }

    @Test
    void removingARoleDeletesBothAssociationKindsFirst() {
        when(mapper.deleteById("role-1")).thenReturn(1);

        assertTrue(service.removeByIdWithRelations("role-1"));

        var order = inOrder(userRoleDao, roleMenuDao, mapper);
        order.verify(userRoleDao).deleteByQuery(any());
        order.verify(roleMenuDao).deleteByQuery(any());
        order.verify(mapper).deleteById("role-1");
    }
}
