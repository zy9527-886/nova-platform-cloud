package org.nova.platform.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.digest.SM3;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.nova.platform.common.database.utils.IdGenerate;
import org.nova.platform.common.database.page.PageQuery;
import org.nova.platform.system.dao.SysUserDao;
import org.nova.platform.system.dao.SysUserRolDao;
import org.nova.platform.system.entity.SysUser;
import org.nova.platform.system.entity.SysUserRol;
import org.nova.platform.system.entity.dto.SysUserDto;
import org.nova.platform.system.entity.vo.SysUserVo;
import org.nova.platform.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 银行用户表(SysUser)表服务实现类
 *
 * @author yyg
 * @since 2026-08-13 14:31:05
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    @Autowired
    private SysUserRolDao SysUserRoldao;
    @Value("${base.init.password:Aa123456.}")
    private String initPassword;


    public static final QueryColumn[] SAFE_COLUMNS = {
    };

    @Override
    public Page<SysUserVo> selectPage(PageQuery<SysUserVo, SysUserDto> pageQuery) {
        Page<SysUserVo> page = pageQuery.getPage();
        page.setRecords(mapper.selectPage(page, pageQuery.getQuery()));
        return page;
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
            if(CollectionUtils.isNotEmpty(user.getUserRolList())){
                for (SysUserRol userRol : user.getUserRolList()) {
                    userRol.setUserId(id);
                }
                SysUserRoldao.insertBatchSelective(user.getUserRolList());
            }
        } else {
            //修改参数处理
            sysUser.setPwd(null);
            res=  mapper.update(sysUser,true);
            SysUserRoldao.deleteByQuery(new QueryWrapper().eq(SysUserRol::getUserId,user.getUserId()) );
            if(CollectionUtils.isNotEmpty(user.getUserRolList())){
                for (SysUserRol userRol : user.getUserRolList()) {
                    userRol.setUserId(id);
                }
                SysUserRoldao.insertBatchSelective(user.getUserRolList());
            }
        }
        return  res;
    }

}

