package org.nova.platform.common.core.utils;


import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Description : sm2工具
 *
 * @author : zy
 * Version : 1.0.0
 * Since : 1.0.0
 * Date : 2019/8/14
 */
@Slf4j
@Component
public class Sm2Util {
    /**
     * 公钥
     */
    public static String publicKey;
    @Value("${security.public-key:042300c7b28f5c97a9956dde69bc90d5cf4d9a7b24533ad3df95660b7d56b39827e029b6e2331ed918cc16640c854e64dd98fda1052910342c7d5f1501d675ab6a}")
    public void setPublicKey(String publicKey) {
        Sm2Util.publicKey = publicKey;
    }

    /**
     * 私钥
     */
    public static String privateKey;
    @Value("${security.private-key:00908627ca4faca78d41f6b6e3c2feab49680f3e3702c65fe91c75ba0356927601}")
    public void setPrivateKey(String privateKey) {
        Sm2Util.privateKey = privateKey;
    }

    /**
     * 加密
     *
     * @param publicKey 公钥
     * @param data      明文
     * @return 密文
     */
    public static String encrypt(String publicKey, String data) {
        return SmUtil.sm2(null, publicKey)
                .encryptHex(data.getBytes(), KeyType.PublicKey)
                // 加密后，密文前面会有04，需要去掉
                .substring(2);
    }
    public static String encrypt(String data) {
        return SmUtil.sm2(null, publicKey)
                .encryptHex(data.getBytes(), KeyType.PublicKey)
                // 加密后，密文前面会有04，需要去掉
                .substring(2);
    }

    /**
     * 解密
     *
     * @param privateKey 私钥
     * @param data       密文
     * @return 明文
     */
    public static String decrypt(String privateKey, String data) {
        // 前端加密是没有04的，所以解析的时候要加04
        data = "04" + data;
        return SmUtil.sm2(privateKey, null)
                .decryptStr(data, KeyType.PrivateKey);
    }
    public static String decrypt(String data) {
        // 前端加密是没有04的，所以解析的时候要加04
        data = "04" + data;
        return SmUtil.sm2(privateKey, null)
                .decryptStr(data, KeyType.PrivateKey);
    }

    public static void main(String[] args) {
        String.valueOf(null);
       String text = "aaaa";
        SM2 sm2 = SmUtil.sm2();
        String privateKey = HexUtil.encodeHexStr(BCUtil.encodeECPrivateKey(sm2.getPrivateKey()));
        String publicKey = HexUtil.encodeHexStr(((BCECPublicKey) sm2.getPublicKey()).getQ().getEncoded(false));
        // 公钥加密
       String encryptStr1 = Sm2Util.encrypt("042300c7b28f5c97a9956dde69bc90d5cf4d9a7b24533ad3df95660b7d56b39827e029b6e2331ed918cc16640c854e64dd98fda1052910342c7d5f1501d675ab6a", text);

        //私钥解密
        String decryptStr1 = Sm2Util.decrypt("00908627ca4faca78d41f6b6e3c2feab49680f3e3702c65fe91c75ba0356927601", encryptStr1);
        //sm3
        System.out.println(encryptStr1);
        System.out.println(decryptStr1);

        System.out.println("111");
    }

}

