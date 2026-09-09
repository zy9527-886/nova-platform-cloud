package org.nova.platform.system.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nova.platform.common.core.exception.BusinessException;
import org.nova.platform.system.dao.SysOrgDao;
import org.nova.platform.system.dao.SysUserDao;
import org.nova.platform.system.entity.SysOrg;
import org.nova.platform.system.entity.vo.SysOrgTreeVo;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SysOrgServiceImplTest {

    private SysOrgDao mapper;
    private SysUserDao userDao;
    private SysOrgServiceImpl service;

    @BeforeEach
    void setUp() {
        mapper = mock(SysOrgDao.class);
        userDao = mock(SysUserDao.class);
        service = new SysOrgServiceImpl();
        ReflectionTestUtils.setField(service, "mapper", mapper);
        ReflectionTestUtils.setField(service, "sysUserDao", userDao);
    }

    @Test
    void treeBuildsParentChildRelations() {
        when(mapper.selectAll()).thenReturn(List.of(
                org("child", "00010001", "0001"),
                org("root", "0001", "0")
        ));

        List<SysOrgTreeVo> tree = service.getTree();

        assertEquals(List.of("root"), tree.stream().map(SysOrgTreeVo::getOrgId).toList());
        assertEquals(List.of("child"), tree.get(0).getChildren().stream().map(SysOrgTreeVo::getOrgId).toList());
    }

    @Test
    void deletingAnOrganizationWithChildrenIsRejected() {
        when(mapper.selectOneById("root")).thenReturn(org("root", "0001", "0"));
        when(mapper.selectCountByQuery(any())).thenReturn(1L);

        assertThrows(BusinessException.class, () -> service.removeOrgById("root"));

        verify(mapper, never()).deleteById(any());
    }

    private SysOrg org(String id, String code, String parentCode) {
        return new SysOrg().setOrgId(id).setOrgCd(code).setOrgNm(id).setOrgPrentId(parentCode)
                .setOrgLvCd(String.valueOf(code.length() / 4)).setOrgStus("1");
    }
}
