package org.nova.platform.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.nova.platform.system.dao.SysFileDao;
import org.nova.platform.system.entity.SysFile;
import org.nova.platform.system.service.SysFileService;
import org.springframework.stereotype.Service;

/**
 * 文件信息(SysFile)表服务实现类
 *
 * @author yyg
 * @since 2026-08-13 16:56:38
 */
@Service("sysFileService")
public class SysFileServiceImpl extends ServiceImpl<SysFileDao, SysFile> implements SysFileService {

}

