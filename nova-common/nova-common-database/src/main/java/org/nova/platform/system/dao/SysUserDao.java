package org.nova.platform.system.dao;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import org.apache.ibatis.annotations.Param;
import org.nova.platform.system.entity.SysUser;
import org.nova.platform.system.entity.dto.SysUserDto;
import org.nova.platform.system.entity.vo.SysUserVo;

import java.util.List;

/**
 * 银行用户表(SysUser)表数据库访问层
 *
 * @author yyg
 * @since 2026-08-13 14:24:23
 */
public interface SysUserDao extends BaseMapper<SysUser> {

    List<SysUserVo> selectPage(@Param("page") Page<SysUserVo> page,
                               @Param("query") SysUserDto query);

}
