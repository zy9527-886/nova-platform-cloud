package org.nova.platform.system.dao;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryTable;
import com.mybatisflex.core.query.QueryWrapper;
import org.nova.platform.system.entity.SysUser;
import org.nova.platform.system.entity.SysUserRol;
import org.nova.platform.system.entity.dto.SysUserDto;
import org.nova.platform.system.entity.vo.SysUserVo;
import org.nova.platform.system.entity.vo.SysUserRoleVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 银行用户表(SysUser)表数据库访问层
 *
 * @author yyg
 * @since 2026-08-13 14:24:23
 */
public interface SysUserDao extends BaseMapper<SysUser> {

    @Select("""
            <script>
            SELECT ur.user_id AS "userId",
                   r.rol_id, r.rol_nm, r.rol_cd, r.rol_lv, r.rol_desc,
                   r.org_id, r.is_pub, r.cre_per, r.cre_tm, r.updt_per, r.updt_tm
            FROM sys_user_rol ur
            INNER JOIN sys_rol r ON r.rol_id = ur.rol_id
            WHERE ur.user_id IN
            <foreach collection="userIds" item="userId" open="(" separator="," close=")">
                #{userId}
            </foreach>
            ORDER BY r.rol_lv, r.rol_id
            </script>
            """)
    List<SysUserRoleVo> selectRolesByUserIds(@Param("userIds") List<String> userIds);

    QueryTable USER = new QueryTable("sys_user").as("u");
    QueryTable USER_ROLE = new QueryTable("sys_user_rol").as("ur");

    QueryColumn USER_ID = new QueryColumn(USER, "user_id");
    QueryColumn USER_NM = new QueryColumn(USER, "user_nm");
    QueryColumn RMK = new QueryColumn(USER, "rmk");
    QueryColumn ID_TYP = new QueryColumn(USER, "id_typ");
    QueryColumn ID_NO = new QueryColumn(USER, "id_no");
    QueryColumn REAL_NM = new QueryColumn(USER, "real_nm");
    QueryColumn TEL = new QueryColumn(USER, "tel");
    QueryColumn ORG_CD = new QueryColumn(USER, "org_cd");
    QueryColumn STUS = new QueryColumn(USER, "stus");
    QueryColumn PWD_ERR_TMS = new QueryColumn(USER, "pwd_err_tms");
    QueryColumn PWD_CHG_TM = new QueryColumn(USER, "pwd_chg_tm");
    QueryColumn LST_LGN_TM = new QueryColumn(USER, "lst_lgn_tm");
    QueryColumn LOCK_TM = new QueryColumn(USER, "lock_tm");
    QueryColumn ICON = new QueryColumn(USER, "icon");
    QueryColumn CRE_PER = new QueryColumn(USER, "cre_per");
    QueryColumn CRE_TM = new QueryColumn(USER, "cre_tm");
    QueryColumn UPDT_PER = new QueryColumn(USER, "updt_per");
    QueryColumn UPDT_TM = new QueryColumn(USER, "updt_tm");
    QueryColumn USER_ROLE_USER_ID = new QueryColumn(USER_ROLE, "user_id");
    QueryColumn USER_ROLE_ROLE_ID = new QueryColumn(USER_ROLE, "rol_id");

    /**
     * 使用 MyBatis-Flex 内置分页，由数据库方言生成分页和总数 SQL。
     */
    default Page<SysUserVo> selectPage(Page<SysUserVo> page, SysUserDto query) {
        QueryWrapper wrapper = QueryWrapper.create()
                .select(USER_ID, USER_NM, RMK, ID_TYP, ID_NO, REAL_NM, TEL, ORG_CD, STUS,
                        PWD_ERR_TMS, PWD_CHG_TM, LST_LGN_TM, LOCK_TM, ICON,
                        CRE_PER, CRE_TM, UPDT_PER, UPDT_TM)
                .from(USER)
                .orderBy(CRE_TM.desc(), USER_ID.asc());

        if (query != null) {
            wrapper.where(USER_NM.likeRight(query.getUserNm(), SysUserDao::hasText))
                    .and(ID_NO.likeRight(query.getIdNo(), SysUserDao::hasText))
                    .and(REAL_NM.likeRight(query.getRealNm(), SysUserDao::hasText))
                    .and(TEL.likeRight(query.getTel(), SysUserDao::hasText))
                    .and(STUS.eq(query.getStus(), SysUserDao::hasText));

            if (query.getUserRolList() != null && !query.getUserRolList().isEmpty()) {
                List<String> roleIds = query.getUserRolList().stream()
                        .map(SysUserRol::getRolId)
                        .filter(SysUserDao::hasText)
                        .toList();
                if (!roleIds.isEmpty()) {
                    QueryWrapper roleQuery = QueryWrapper.create()
                            .select(USER_ROLE_USER_ID)
                            .from(USER_ROLE)
                            .where(USER_ROLE_ROLE_ID.in(roleIds));
                    wrapper.and(USER_ID.in(roleQuery));
                }
            }
        }

        return paginateAs(page, wrapper, SysUserVo.class);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

}
