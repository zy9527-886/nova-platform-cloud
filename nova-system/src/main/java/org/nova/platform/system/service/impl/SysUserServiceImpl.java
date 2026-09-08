package org.nova.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SmUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.nova.platform.common.core.exception.BusinessException;
import org.nova.platform.common.database.utils.IdGenerate;
import org.nova.platform.common.database.page.PageQuery;
import org.nova.platform.system.dao.SysRolDao;
import org.nova.platform.system.dao.SysUserDao;
import org.nova.platform.system.dao.SysUserRolDao;
import org.nova.platform.system.entity.SysUser;
import org.nova.platform.system.entity.SysUserRol;
import org.nova.platform.system.entity.SysRol;
import org.nova.platform.system.entity.dto.SysUserDto;
import org.nova.platform.system.entity.vo.SysUserVo;
import org.nova.platform.system.entity.vo.SysUserRoleVo;
import org.nova.platform.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 银行用户表(SysUser)表服务实现类
 *
 * @author yyg
 * @since 2026-08-13 14:31:05
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    @Autowired
    private SysUserRolDao sysUserRolDao;
    @Autowired
    private SysRolDao sysRolDao;
    @Value("${base.init.password:Aa123456.}")
    private String initPassword;


    @Override
    public Page<SysUserVo> selectPage(PageQuery<SysUserVo, SysUserDto> pageQuery) {
        Page<SysUserVo> page = mapper.selectPage(pageQuery.getPage(), pageQuery.getQuery());
        attachRoles(page.getRecords());
        return page;
    }

    @Override
    public SysUserVo getDetail(Serializable userId) {
        SysUser entity = mapper.selectOneById(userId);
        if (entity == null) {
            return null;
        }
        SysUserVo result = BeanUtil.copyProperties(entity, SysUserVo.class);
        attachRoles(List.of(result));
        return result;
    }


    @Transactional
    @Override
    public int saveOrUpdate(SysUserDto user) {
        SysUser sysUser = BeanUtil.copyProperties(user, SysUser.class);
        String id = IdGenerate.getIdStr();
        int res=0;
        if (StringUtils.isBlank(user.getUserId())) {
            //插入参数处理
            sysUser.setUserId(id)
                   .setPwd(SmUtil.sm3(initPassword));
            res= mapper.insertSelective(sysUser);
            if (res <= 0) {
                throw new BusinessException("新增用户失败");
            }
            replaceUserRoles(id, user.getUserRolList(), false);
        } else {
            if (mapper.selectOneById(user.getUserId()) == null) {
                throw new BusinessException("用户不存在");
            }
            //修改参数处理
            sysUser.setPwd(null);
            res=  mapper.update(sysUser,true);
            if (res <= 0) {
                throw new BusinessException("修改用户失败");
            }
            replaceUserRoles(user.getUserId(), user.getUserRolList(), true);
        }
        return  res;
    }

    @Transactional
    @Override
    public boolean removeByIdWithRoles(Serializable userId) {
        sysUserRolDao.deleteByQuery(QueryWrapper.create().eq(SysUserRol::getUserId, userId));
        return mapper.deleteById(userId) > 0;
    }

    @Transactional
    @Override
    public boolean removeByIdsWithRoles(Collection<? extends Serializable> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return false;
        }
        sysUserRolDao.deleteByQuery(QueryWrapper.create().in(SysUserRol::getUserId, userIds));
        return mapper.deleteBatchByIds(userIds) > 0;
    }

    private void replaceUserRoles(String userId, List<SysUserRol> submittedRoles, boolean clearExisting) {
        Map<String, SysUserRol> uniqueRoles = new LinkedHashMap<>();
        if (!CollectionUtils.isEmpty(submittedRoles)) {
            submittedRoles.stream()
                    .filter(Objects::nonNull)
                    .filter(item -> StringUtils.isNotBlank(item.getRolId()))
                    .forEach(item -> {
                        item.setUserId(userId);
                        uniqueRoles.putIfAbsent(item.getRolId(), item);
                    });
        }
        if (!uniqueRoles.isEmpty()
                && sysRolDao.selectListByIds(uniqueRoles.keySet()).size() != uniqueRoles.size()) {
            throw new BusinessException("包含不存在的角色");
        }
        if (clearExisting) {
            sysUserRolDao.deleteByQuery(QueryWrapper.create().eq(SysUserRol::getUserId, userId));
        }
        if (!uniqueRoles.isEmpty()) {
            List<SysUserRol> rows = List.copyOf(uniqueRoles.values());
            sysUserRolDao.insertBatchSelective(rows);
        }
    }

    private void attachRoles(List<SysUserVo> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        List<String> userIds = users.stream()
                .map(SysUserVo::getUserId)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .toList();
        if (userIds.isEmpty()) {
            return;
        }
        Map<String, List<SysRol>> rolesByUser = mapper.selectRolesByUserIds(userIds).stream()
                .collect(Collectors.groupingBy(
                        SysUserRoleVo::getUserId,
                        Collectors.mapping(row -> BeanUtil.copyProperties(row, SysRol.class), Collectors.toList())
                ));
        users.forEach(item -> item.setRoles(rolesByUser.getOrDefault(item.getUserId(), Collections.emptyList())));
    }

}

