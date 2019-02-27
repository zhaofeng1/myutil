package com.zf.util;



import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESUtil {

    private static String AES_KEY = "CWELWRREW4567i1o";
    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);
    
    public static void main(String[] args) {
		//		String enStr = encrypt("http://10.200.10.34:8010/v1/click?type=01&p1=30557&p2=10164&p3=10726&p8=1.04&p9=&p12=165628948&p13=500&p26=23&p52=3&p4=1004910200102131519955921867&p5=1662684189370000_1769833153869588&p6=US&p7=a2849f901c3210ca&p11=en&p14=185494&lid={loop_id}&p15=com.amazon.mShop.android.shopping&p33=000-111-222-333&p34=-1&p35=-1&p36=-1&p37=0&p38=0&p39=0&p40=10&p41=3943427&p48=0&p53=null&p62=1&p64=2&p65=3.4.4.4101&p66=2.1.6&p67=12345");
		//		String enStr = encrypt("type=01&p1=30742&p2=6657314&p3=10850&p9=6657314&p12=111464037&p5=1662684189370000_1769833153870389&p7=4e15fe03b27719d5&p33=7d30f0d6-07d1-469b-a8f9-cfcbd390d9ce");
		//		System.out.println(enStr);
		//		String enStr = "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";
		//		String desStr = desEncrypt(enStr);
		//		System.out.println(desStr);
    	
    	
		String enStrGet = encryptGet("type=01&p1=30742&p2=6657314&p3=10850&p9=6657314&p12=111464037&p5=1662684189370000_1769833153870389&p7=4e15fe03b27719d5&p33=7d30f0d6-07d1-469b-a8f9-cfcbd390d9ce&p15=com.mobile.legends&p6=PH", AES_KEY);
		System.out.println(enStrGet);
	}

    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            if (AES_KEY.equals("")) {
                AES_KEY = "CWELWRREW4567i1o";
            }

            SecretKeySpec keyspec = new SecretKeySpec(AES_KEY.getBytes("UTF-8"), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(AES_KEY.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return Base64.encodeBase64String(encrypted).trim();

        } catch (Exception e) {
        	logger.error( "encrypt error : " + e);
        }catch (OutOfMemoryError e) {
        	logger.error( "encrypt error : " + e);
        }
        return null;
    }

    public static String desEncrypt(String data) {
        try {
            byte[] encrypted1 = Base64.decodeBase64(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(AES_KEY.getBytes("UTF-8"), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(AES_KEY.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "UTF-8").trim();
        } catch (Exception e) {
//			SDKLogger.e(null, "desEncrypt error : " + e);
        }catch (OutOfMemoryError e) {
        	logger.error( "encrypt error : " + e);
        }
        return null;
    }

	/**
	 * get使用的aes加密，没有特殊符号
	 * @return
	 */
	public static String encryptGet(String data, String key) {
		try {
			return AES.encrypttoStr(data, key);
		} catch (Exception e) {
			logger.error("encrypt error:" + e);
		} catch (OutOfMemoryError e) {
			logger.error("encrypt error : " + e);
		}
		return null;
	}

	/**
	 * get使用的aes解密，没有特殊符号
	 * @return
	 */
	public static String desEncryptGet(String data, String key) {
		try {
			return AES.decryptByKey(data, key);
		} catch (Exception e) {
			logger.error("desEncrypt error:" + e);
		} catch (OutOfMemoryError e) {
			logger.error("desEncrypt error : " + e);
		}
		return null;
	}

}