package com.traffic_hand.util;

import java.security.Key;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 
 * <p>
 * Title: CryptUtil
 * </p>
 * <p>
 * Description: 加解密工具类
 * </p>
 * 
 * @author LiuSP
 * @date 2017年5月25日
 */
public class CryptUtil {
	
	protected static ObjectMapper mapper = new ObjectMapper();

	/*
	 * 密钥算法
	 */
	public static final String KEY_ALGORITHM = "AES";

	/*
	 * 加密/解密算法/工作模式/填充方式 Bouncy castle组件支持PKCS7Padding填充方式,适配IOS
	 */
//	public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";
	
	//中车信息解密   PKCS5Padding
	public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	/**
	 * 生成密钥，bouncy castle支持64位密钥
	 * 
	 * @return byte[] 二进制密钥
	 * @throws Exception
	 */
	public static String initkey() throws Exception {

		// 实例化密钥生成器
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM, "BC");
		// 初始化密钥生成器，AES要求密钥长度为256位
		kg.init(256);
		// 生成密钥
		SecretKey secretKey = kg.generateKey();
		// 获取二进制密钥编码形式
		return Base64.encode(secretKey.getEncoded());
	}

	/**
	 * 转换密钥
	 * 
	 * @param key
	 *            二进制密钥
	 * @return 密钥
	 * @throws Exception
	 */
	public static Key toKey(byte[] key) throws Exception {
		// 实例化DES密钥
		// 生成密钥
		SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
		return secretKey;
	}

	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return 加密后的数据
	 * @throws Exception
	 */
	public static String encrypt(String data) throws Exception {
		String key = "dAA%D#V*2a9r4I!V";
		byte[] keys = key.getBytes();
		// 还原密钥
		Key k = toKey(keys);
		/**
		 * 实例化 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncy castle组件实现
		 * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
		 */
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
		// 初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		// 执行操作
		return Base64.encode(cipher.doFinal(data.getBytes()));
	}

	/**
	 * 解密数据
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密后的数据
	 * @throws Exception
	 */
	public static String decrypt(String data, String key) throws Exception {
		byte[] datas = Base64.decode(data);
		byte[] keys = key.getBytes();
		// 转换密钥
		Key k = toKey(keys);
		/**
		 * 实例化 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
		 * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
		 */
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);
		// 执行操作
		return new String(cipher.doFinal(datas),"UTF-8");
	}
	

	/**
	 * 解密数据
	 * 
	 * @param bytes
	 *            待解密数据
	 * @return String 解密后的数据
	 * @throws Exception
	 */
	public static String decrypt(byte[] bytes) throws Exception {
		String xbus_key = "e7f6787a8e5a0ec6";
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(xbus_key.getBytes("utf-8"), "AES"));
		bytes = cipher.doFinal(bytes);

		return new String(bytes, "utf-8");
	}

	

}
