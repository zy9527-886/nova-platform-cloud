package org.nova.platform.system.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import org.nova.platform.common.database.page.PageQuery;
import org.nova.platform.system.entity.SysUser;
import org.nova.platform.system.entity.dto.SysUserDto;
import org.nova.platform.system.entity.vo.SysUserVo;

/**
 * 银行用户表(SysUser)表服务接口
 *
 * @author yyg
 * @since 2026-08-13 14:31:04
 */
public interface SysUserService extends IService<SysUser> {

    Page<SysUserVo> selectPage(PageQuery<SysUserVo, SysUserDto> pageQuery);


    /**
     * 增加或修改
     * @param user
     * @return
     */
    int saveOrUpdate(SysUserDto user);

}

