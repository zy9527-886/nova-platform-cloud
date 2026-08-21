package org.nova.platform.common.core.utils;

import cn.hutool.crypto.symmetric.SM4;

import java.util.Base64;

/**
 * @author: zy
 * @date: 2025/2/28 9:33
 * @description:
 */
public class Sm4Util {

    // 默认偏移向量（IV）
    private static final String DEFAULT_IV = "0123456789abcdef";

    /**
     * 加密数据
     *
     * @param data 待加密的数据
     * @param key  SM4 密钥（16 字节或 32 字节的字符串）
     * @return 加密后的数据（Base64 编码）
     */
    public static String encrypt(String data, String key) {
        SM4 sm4 =new SM4( key.getBytes());
        sm4.setIv(DEFAULT_IV.getBytes()); // 设置偏移向量
        return sm4.encryptBase64(data.getBytes());
    }

    /**
     * 解密数据
     *
     * @param encryptedData 加密后的数据（Base64 编码）
     * @param key           SM4 密钥（16 字节或 32 字节的字符串）
     * @return 解密后的数据
     */
    public static String decrypt(String encryptedData, String key) {
        SM4 sm4 = new SM4(key.getBytes());
        sm4.setIv(DEFAULT_IV.getBytes()); // 设置偏移向量
        return new String(sm4.decrypt(Base64.getDecoder().decode(encryptedData)));
    }

    public static void main(String[] args) {
        // 定义密钥（16 字节或 32 字节）
        String key = "0123456789abcdef"; // 示例密钥

        // 待加密数据
        String originalData = "Hello, SM4!";
        System.out.println("Original Data: " + originalData);

        // 加密
        String encryptedData = encrypt(originalData, key);
        System.out.println("Encrypted Data: " + encryptedData);

        // 解密
        String decryptedData = decrypt(encryptedData, key);
        System.out.println("Decrypted Data: " + decryptedData);
    }
}
