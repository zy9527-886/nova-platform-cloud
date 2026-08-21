package org.nova.platform.common.core.utils.upload;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.nova.platform.common.core.exception.BusinessException;
import org.nova.platform.common.core.utils.DateUtil;
import org.nova.platform.common.core.utils.IdGenerate;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Description :文件保存
 * Author : 张岳
 * Version : 1.0.0
 * Since : 1.0.0
 * Date : 2019/9/17
 */
@Slf4j
public class UploadUtil {

    /**
     * io分割大小
     */
    private static final int BUFFER_SIZE = 5120;

    /**
     * uuid与原文件名拼接符号
     */
    private static final String SEPARATOR = "#$—";

    /**
     * Description:保存文件
     *
     * @param file        文件
     * @param mainPathPre 主路径
     * @param subPathPre  分路径
     * @return String 保存后的路径
     * @auther 张岳
     * @date 2019/9/17
     */
    public static String upload(MultipartFile file, String mainPathPre, String subPathPre) throws Exception {
        Assert.notNull(file, "file must be not null");
        Date now = new Date();
        String path = subPathPre + DateUtil.DateToString(now, "yyyy/MM");
        String newpath = mainPathPre + path;
        File pathfile = new File(newpath);
        if (!pathfile.exists()) {
            boolean flag = pathfile.mkdirs();
            if (!flag) {
                throw new Exception("创建文件失败");
            }
        }
        BufferedOutputStream out = null;
        BufferedInputStream input = null;
        String filePath = null;
        try {
            //获取后缀
            String filename = file.getOriginalFilename();
            String prefix = filename.substring(filename.lastIndexOf(".") + 1);
            //修改文件名称 uuid
            String fileUUIDname = IdGenerate.getUUID() + SEPARATOR + filename.substring(0, filename.lastIndexOf("."));
            //修改后完整的文件名称
            String newFileName = fileUUIDname + "." + prefix;

            input = new BufferedInputStream(file.getInputStream());
            out = new BufferedOutputStream(new FileOutputStream(new File(newpath, newFileName)));
            byte[] buffer = new byte[BUFFER_SIZE];
            int n = 0;
            while (-1 != (n = input.read(buffer, 0, BUFFER_SIZE))) {
                out.write(buffer, 0, n);
            }
            filePath = path + "/" + newFileName;
        } catch (Exception e) {
            log.error("上传失败", e);
            throw new Exception("上传失败");
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(input);
        }
        return filePath;
    }

    /**
     * Description: 批量保存文件
     *
     * @param files       文件
     * @param mainPathPre 主路径
     * @param subPathPre  分路径
     * @return String 保存后的路径
     * @auther 张岳
     * @date 2019/9/17
     */
    public static String uploadBatch(MultipartFile[] files, String mainPathPre, String subPathPre) throws Exception {
        if (files.length == 0) {//非空校验
            return null;
        }
        String path = subPathPre + DateUtil.DateToString(new Date(), "yyyy/MM");
        String newpath = mainPathPre + path;
        File pathfile = new File(newpath);
        if (!pathfile.exists()) {
            pathfile.mkdirs();
        }
        String filePath = null;
        List<String> result = new LinkedList<String>();

        for (MultipartFile file : files) {
            //获取后缀
            String filename = file.getOriginalFilename();
            String prefix = filename.substring(filename.lastIndexOf(".") + 1);
            //修改文件名称 uuid
            String fileUUIDname = IdGenerate.getUUID() + SEPARATOR + filename.substring(0, filename.lastIndexOf("."));
            //修改后完整的文件名称
            String newFileName = fileUUIDname + "." + prefix;

            BufferedOutputStream out = null;
            BufferedInputStream input = null;
            try {
                input = new BufferedInputStream(file.getInputStream());
                out = new BufferedOutputStream(new FileOutputStream(new File(newpath, newFileName)));
                byte[] buffer = new byte[BUFFER_SIZE];
                int n = 0;
                while (-1 != (n = input.read(buffer, 0, BUFFER_SIZE))) {
                    out.write(buffer, 0, n);
                }
                filePath = path + "/" + newFileName;
                result.add(filePath);
            } catch (Exception e) {
                log.error("上传失败", e);
                throw new Exception("上传失败");
            } finally {
                IOUtils.closeQuietly(out);
                IOUtils.closeQuietly(input);
            }
        }
        return String.join(",", result);
    }

    /**
     * Description: 批量保存zip文件
     *
     * @param files       文件
     * @param mainPathPre 主路径
     * @param subPathPre  分路径
     * @return String 保存后的路径
     * @auther 张岳
     * @date 2019/9/17
     */
    public static String uploadZip(MultipartFile[] files, String mainPathPre, String subPathPre) {
        if (files.length == 0) {//非空校验
            return null;
        }
        String path = subPathPre + DateUtil.DateToString(new Date(), "yyyy/MM");
        String newpath = mainPathPre + path;
        File pathfile = new File(newpath);
        if (!pathfile.exists()) {
            pathfile.mkdirs();
        }
        String filePath = null;
        List<String> result = new LinkedList<String>();

        BufferedOutputStream out = null;
        ZipOutputStream zos = null;
        String uuid = IdGenerate.getUUID();
        try {
            out = new BufferedOutputStream(new FileOutputStream(new File(newpath, uuid + ".zip")));
            zos = new ZipOutputStream(out);
            for (MultipartFile file : files) {//循环上传问价放入zip
                BufferedInputStream input = null;
                try {
                    input = new BufferedInputStream(file.getInputStream());
                    String fileNm = file.getOriginalFilename();
                    int pos = fileNm.lastIndexOf(File.pathSeparator);
                    if (pos != -1) {
                        fileNm = fileNm.substring(pos + 1);
                    }
                    ZipEntry ze = new ZipEntry(IdGenerate.getUUID() + SEPARATOR + fileNm);
                    zos.putNextEntry(ze);
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int n = 0;
                    while (-1 != (n = input.read(buffer, 0, BUFFER_SIZE))) {
                        zos.write(buffer, 0, n);
                    }

                } catch (Exception e) {
                    log.error("上传失败", e);
                    throw new BusinessException("上传失败");
                } finally {
                    IOUtils.closeQuietly(input);
                }
            }

        } catch (Exception e) {
            log.error("上传失败", e);
            throw new BusinessException("上传失败");
        } finally {
            IOUtils.closeQuietly(zos);
            IOUtils.closeQuietly(out);
        }
        return newpath + "/" + uuid + ".zip";
    }

}