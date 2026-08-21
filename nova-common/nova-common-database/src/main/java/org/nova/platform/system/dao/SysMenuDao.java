package org.nova.platform.system.dao;

import com.mybatisflex.core.BaseMapper;
import org.nova.platform.system.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 银行用户菜单权限(SysMenu)表数据库访问层
 *
 * @author yyg
 * @since 2026-08-13 16:49:17
 */
public interface SysMenuDao extends BaseMapper<SysMenu> {

    @Select("""
            SELECT DISTINCT
                m."menu_id",
                m."menu_nm",
                m."perm_cd",
                m."path",
                m."prent_id",
                m."sort",
                m."icon",
                m."typ",
                m."is_dsp",
                m."menu_source"
            FROM "sys_menu" m
            INNER JOIN "sys_rol_menu" rm ON rm."menu_id" = m."menu_id"
            INNER JOIN "sys_user_rol" ur ON ur."rol_id" = rm."rol_id"
            WHERE ur."user_id" = #{userId}
            ORDER BY m."sort" ASC NULLS LAST
            """)
    List<SysMenu> selectUserResources(@Param("userId") String userId);

    @Select("""
            SELECT DISTINCT
                rm."rol_id" AS "roleId",
                m."path" AS "path"
            FROM "sys_rol_menu" rm
            INNER JOIN "sys_menu" m ON m."menu_id" = rm."menu_id"
            WHERE m."typ" = #{buttonType}
              AND m."path" IS NOT NULL
              AND m."path" <> ''
            ORDER BY rm."rol_id", m."path"
            """)
    List<Map<String, Object>> selectRoleButtonPaths(@Param("buttonType") String buttonType);
}

