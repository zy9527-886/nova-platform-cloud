package org.nova.platform.common.core.utils.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Dalvik
 *
 *         2016年3月17日
 */
public class RSAUtils {
	private static final Logger logger = LoggerFactory.getLogger(RSAUtils.class);
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;
	private static final String CHAR_SET = "UTF-8";
	/**
	 * RSA签名，目前服务端未支持
	 *
	 *            the source to be signed
	 * @return
	 */
	public static String sign(String sourcestr,String private_key) throws Exception {
		byte[] str= Base64.decode(private_key);
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(str);
		KeyFactory keyf = KeyFactory.getInstance("RSA");
		PrivateKey priKey = keyf.generatePrivate(priPKCS8);
		Signature signature = Signature
				.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(sourcestr.getBytes(CHAR_SET));
		byte[] signed = signature.sign();
		return Base64.encode(signed);
	}

	/**
	 * RSA验签
	 *
	 * @param source
	 *            签名内容
	 * @param sign
	 *            签名值
	 * @return
	 */
	public static boolean verify(String source, String sign,String public_key) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] encodedKey = Base64.decode(public_key);
		PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(
				encodedKey));

		Signature signature = Signature
				.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(source.getBytes(CHAR_SET));

		boolean bverify = signature.verify(Base64.decode(sign));
		return bverify;
	}

	/**
	 * 生成签名摘要
	 *
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static byte[] sha1(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest md = null;
		md = MessageDigest.getInstance("SHA-1"); // 选择SHA-1，也可以选择MD5
		byte[] digest = md.digest(data); // 返回的是byet[]，要转化为String存储比较方便
		return digest;
	}

	/**
	 * RSA加密
	 *
	 * @param paramstr
	 * @return
	 */
	public static String encrypt(String paramstr,String encode,String public_key) {
		try {
			byte[] cipherData = RSAUtils.encryptByPublicKey(paramstr.getBytes(encode), public_key);
			String result = Base64.encode(cipherData);
			return result;
		} catch (Exception e) {
			logger.error("加密出错",e);
		}
		return null;
	}

	/**
	 * RSA解密
	 *
	 * @param cipherData
	 *            the data to be decrypt
	 * @return decryptByPrivateKey
	 */
	public static String decrypt(String cipherData,String encode,String private_key) {
		try {
			byte[] cipher = RSAUtils.decryptByPrivateKey(Base64.decode(cipherData), private_key);
			String result = new String(cipher, encode);
			return result;
		} catch (Exception e) {
			logger.error("解密出错",e);
		}
		return null;
	}

	/**
	 * <P>
	 * 私钥解密
	 * </p>
	 *
	 * @param data
	 *            要解密的数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * <p>
	 * 公钥解密
	 * </p>
	 *
	 * @param data
	 *            要解密的数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String publicKey) throws Exception {
		byte[] keyBytes = Base64.decode(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * <p>
	 * 公钥加密
	 * </p>
	 *
	 * @param data
	 *            源数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		byte[] keyBytes = Base64.decode(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * <p>
	 * 私钥加密
	 * </p>
	 *
	 * @param data
	 *            源数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

}