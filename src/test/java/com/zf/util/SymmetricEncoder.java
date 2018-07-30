package com.zf.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*
 * AES对称加密和解密
 */
public class SymmetricEncoder {

	private static final String KEY = "test";
	/*
	 * 加密 1.构造密钥生成器 2.根据ecnodeRules规则初始化密钥生成器 3.产生密钥 4.创建和初始化密码器 5.内容加密 6.返回字符串
	 */
	public static String AESEncode(String content) {
		try {
			//1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			//2.根据ecnodeRules规则初始化密钥生成器
			//生成一个128位的随机源,根据传入的字节数组
			keygen.init(128, new SecureRandom(KEY.getBytes()));
			//3.产生原始对称密钥
			SecretKey original_key = keygen.generateKey();
			//4.获得原始对称密钥的字节数组
			byte[] raw = original_key.getEncoded();
			//5.根据字节数组生成AES密钥
			SecretKey key = new SecretKeySpec(raw, "AES");
			//6.根据指定算法AES自成密码器
			Cipher cipher = Cipher.getInstance("AES");
			//7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
			byte[] byte_encode = content.getBytes("utf-8");
			//9.根据密码器的初始化方式--加密：将数据加密
			byte[] byte_AES = cipher.doFinal(byte_encode);
			//10.将加密后的数据转换为字符串
			//这里用Base64Encoder中会找不到包
			//解决办法：
			//在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
			String AES_encode = new String(new BASE64Encoder().encode(byte_AES));
			//11.将字符串返回
			return AES_encode;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		//如果有错就返加nulll
		return null;
	}

	/*
	 * 解密 解密过程： 1.同加密1-4步 2.将加密后的字符串反纺成byte[]数组 3.将加密内容解密
	 */
	public static String AESDncode(String content) {
		try {
			//1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			//2.根据ecnodeRules规则初始化密钥生成器
			//生成一个128位的随机源,根据传入的字节数组
			keygen.init(128, new SecureRandom(KEY.getBytes()));
			//3.产生原始对称密钥
			SecretKey original_key = keygen.generateKey();
			//4.获得原始对称密钥的字节数组
			byte[] raw = original_key.getEncoded();
			//5.根据字节数组生成AES密钥
			SecretKey key = new SecretKeySpec(raw, "AES");
			//6.根据指定算法AES自成密码器
			Cipher cipher = Cipher.getInstance("AES");
			//7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.DECRYPT_MODE, key);
			//8.将加密并编码后的内容解码成字节数组
			byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
			/*
			 * 解密
			 */
			byte[] byte_decode = cipher.doFinal(byte_content);
			String AES_decode = new String(byte_decode, "utf-8");
			return AES_decode;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		//如果有错就返加nulll
		return null;
	}

	public static void main(String[] args) {
		SymmetricEncoder se = new SymmetricEncoder();
		Scanner scanner = new Scanner(System.in);
		/*
		 * 加密
		 */
		String str = "http://10.200.10.34:8010/v1/click?type=01&p1=30557&p2=10164&p3=10726&p8=1.04&p9=&p12=165628948&p13=500&p26=23&p52=3&p4=1004910200102131519955921867&p5=1662684189370000_1769833153869588&p6=US&p7=a2849f901c3210ca&p11=en&p14=185494&lid={loop_id}&p15=com.amazon.mShop.android.shopping&p33=000-111-222-333&p34=-1&p35=-1&p36=-1&p37=0&p38=0&p39=0&p40=10&p41=3943427&p48=0&p53=null&p62=1&p64=2&p65=3.4.4.4101&p66=2.1.6&p67=12345";
		String enStr = se.AESEncode(str);
		System.out.println("加密后的密文是:" + enStr);

		/*
		 * 解密
		 */
		String deStr = se.AESDncode(enStr);
		System.out.println("解密：" + deStr);
	}

}