package org.nova.platform.system.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nova.platform.common.core.exception.BusinessException;
import org.nova.platform.common.database.page.PageQuery;
import org.nova.platform.system.dao.SysRolDao;
import org.nova.platform.system.dao.SysUserDao;
import org.nova.platform.system.dao.SysUserRolDao;
import org.nova.platform.system.entity.SysRol;
import org.nova.platform.system.entity.SysUser;
import org.nova.platform.system.entity.SysUserRol;
import org.nova.platform.system.entity.dto.SysUserDto;
import org.nova.platform.system.entity.vo.SysUserRoleVo;
import org.nova.platform.system.entity.vo.SysUserVo;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SysUserServiceImplTest {

    private SysUserDao mapper;
    private SysUserRolDao userRoleDao;
    private SysRolDao roleDao;
    private SysUserServiceImpl service;

    @BeforeEach
    void setUp() {
        mapper = mock(SysUserDao.class);
        userRoleDao = mock(SysUserRolDao.class);
        roleDao = mock(SysRolDao.class);
        service = new SysUserServiceImpl();
        ReflectionTestUtils.setField(service, "mapper", mapper);
        ReflectionTestUtils.setField(service, "sysUserRolDao", userRoleDao);
        ReflectionTestUtils.setField(service, "sysRolDao", roleDao);
        ReflectionTestUtils.setField(service, "initPassword", "Aa123456.");
    }

    @Test
    void updateRoleRowsUseTheExistingUserId() {
        SysUserDto user = new SysUserDto();
        user.setUserId("existing-user");
        user.setUserNm("alice");
        user.setUserRolList(new ArrayList<>(List.of(
                new SysUserRol().setRolId("role-1"),
                new SysUserRol().setRolId("role-2")
        )));
        when(mapper.update(any(SysUser.class), eq(true))).thenReturn(1);
        when(mapper.selectOneById("existing-user")).thenReturn(new SysUser().setUserId("existing-user"));
        when(roleDao.selectListByIds(any())).thenReturn(List.of(
                new SysRol().setRolId("role-1"),
                new SysRol().setRolId("role-2")
        ));

        assertEquals(1, service.saveOrUpdate(user));

        assertTrue(user.getUserRolList().stream()
                .allMatch(item -> "existing-user".equals(item.getUserId())));
        verify(userRoleDao).insertBatchSelective(user.getUserRolList());
    }

    @Test
    void pageRecordsAreEnrichedWithTheirRoles() {
        SysUserVo user = new SysUserVo();
        user.setUserId("user-1");
        Page<SysUserVo> page = new Page<>(1, 10, 1);
        page.setRecords(List.of(user));
        PageQuery<SysUserVo, SysUserDto> request = new PageQuery<>();
        request.setCurrent(1);
        request.setSize(10);
        request.setQuery(new SysUserDto());
        when(mapper.selectPage(any(Page.class), same(request.getQuery()))).thenReturn(page);

        SysUserRoleVo row = new SysUserRoleVo();
        row.setUserId("user-1");
        row.setRolId("role-1");
        row.setRolNm("审核员");
        when(mapper.selectRolesByUserIds(List.of("user-1"))).thenReturn(List.of(row));

        Page<SysUserVo> result = service.selectPage(request);

        assertEquals(List.of("role-1"), result.getRecords().get(0).getRoles().stream()
                .map(SysRol::getRolId).toList());
    }

    @Test
    void detailContainsAssignedRoles() {
        SysUser entity = new SysUser().setUserId("user-1").setUserNm("alice").setPwd("secret-hash");
        when(mapper.selectOneById("user-1")).thenReturn(entity);
        SysUserRoleVo row = new SysUserRoleVo();
        row.setUserId("user-1");
        row.setRolId("role-1");
        when(mapper.selectRolesByUserIds(List.of("user-1"))).thenReturn(List.of(row));

        SysUserVo result = service.getDetail("user-1");

        assertEquals("alice", result.getUserNm());
        assertEquals("role-1", result.getRoles().get(0).getRolId());
    }

    @Test
    void detailNeverSerializesThePasswordHash() throws Exception {
        SysUser entity = new SysUser().setUserId("user-1").setUserNm("alice").setPwd("secret-hash");
        when(mapper.selectOneById("user-1")).thenReturn(entity);
        when(mapper.selectRolesByUserIds(List.of("user-1"))).thenReturn(List.of());

        String json = new ObjectMapper().writeValueAsString(service.getDetail("user-1"));

        assertFalse(json.contains("\"pwd\""));
        assertFalse(json.contains("secret-hash"));
    }

    @Test
    void updatingANonexistentUserIsRejectedBeforeReplacingRoles() {
        SysUserDto user = new SysUserDto();
        user.setUserId("missing-user");
        user.setUserNm("alice");
        when(mapper.selectOneById("missing-user")).thenReturn(null);

        assertThrows(BusinessException.class, () -> service.saveOrUpdate(user));

        verify(mapper).selectOneById("missing-user");
    }

    @Test
    void bindingANonexistentRoleIsRejected() {
        SysUserDto user = new SysUserDto();
        user.setUserNm("alice");
        user.setUserRolList(List.of(new SysUserRol().setRolId("missing-role")));
        when(roleDao.selectListByIds(any())).thenReturn(List.of());

        assertThrows(BusinessException.class, () -> service.saveOrUpdate(user));
    }

    @Test
    void removingAUserDeletesRoleRowsBeforeTheUser() {
        when(mapper.deleteById("user-1")).thenReturn(1);

        assertTrue(service.removeByIdWithRoles("user-1"));

        var order = inOrder(userRoleDao, mapper);
        order.verify(userRoleDao).deleteByQuery(any());
        order.verify(mapper).deleteById("user-1");
    }
}
