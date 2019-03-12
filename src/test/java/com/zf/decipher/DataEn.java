/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package com.zf.decipher;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author yzw
 */
public class DataEn {

	private static SecretKey secretKey;
	private static final String AES = "AES";

	static {
		try {
			secretKey = new SecretKeySpec(Base64.decode("8iLUnz1tcbfGwApQcpr2AQ==", 4), AES);
		} catch (Exception ex) {
			Logger.getLogger(DataEn.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * 加密
	 *
	 * @param data
	 *            待加密数据
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data) throws Exception {
		byte[] endata = encrypt(data.getBytes());
		return Base64.encodeToString(endata, Base64.CRLF);
	}

	/**
	 * 解密
	 *
	 * @param data
	 *            待解密数据
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String data) throws Exception {
		if (StringUtils.isBlank(data))
			return "";
		byte be[] = Base64.decode(data, Base64.CRLF);
		return new String(decrypt(be));
	}

	private static byte[] encrypt(byte[] data) throws Exception {
		if (null == secretKey) {
			throw new SecurityException("secretKey is null");
		}
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}

	private static byte[] decrypt(byte[] data) throws Exception {
		if (null == secretKey) {
			throw new SecurityException("secretKey is null");
		}
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}

	private static byte[] getSecretKey(byte[] publicKey, byte[] privateKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("DH");
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory.getAlgorithm());
		keyAgree.init(priKey);
		keyAgree.doPhase(pubKey, true);
		return keyAgree.generateSecret(AES).getEncoded();
	}

	public static void main(String[] args) {
		try {
			System.out.println(
					DataEn.encrypt("{\"robotId\":\"aabbcc\",\"clickTime\":\"1480919718696\",\"clickId\":\"ncbtest1480919718696\",\"clickUrl\":\"http://niuc.com.test\",\"referer\":\"http://test.referer\",\"trackingUrl\":\"http://test.tackingurl\",\"clickResult\":\"1\"}"));
			String s = DataEn.encrypt("{\"robotId\":\"aabbcc\",\"clickTime\":\"1480919718696\",\"clickId\":\"ncbtest1480919718696\",\"clickUrl\":\"http://niuc.com.test\",\"referer\":\"http://test.referer\",\"trackingUrl\":\"http://test.tackingurl\",\"clickResult\":\"1\"}");
			String ss = s.replaceAll("\n", "").replaceAll("\r", "");
			System.out.println(s);
			System.out.println(ss);
			System.out.println(DataEn.decrypt(
					"NPiRPQU7MvYejbxHFcLvyINidHKwQHbAGzhhgAmmeywNOjfF7Za7meXLxTgVKq2YMxWz0Q8SMuiLyQ59t0Zet0qKQqrzf7R9Saff6aDvCWm2LjccLDeQtaVO9ltPFxt1LDW5RZEFNZuyx5zP5fVFXAfMNrdmKjQ+i0yM6DRZ8UcuO0jAWarCM7VLpII+nBTA1ojkyynMWueZjagqEP0rZbgSHENtKJfFZ4JB6cvYe9hChjfT0FPkyvhXP6moKjC0YFNF7oaOJ8RfQJy56/OY5g=="));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
