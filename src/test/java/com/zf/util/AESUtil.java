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
		String enStr = encrypt("http://10.200.10.34:8010/v1/click?type=01&p1=30557&p2=10164&p3=10726&p8=1.04&p9=&p12=165628948&p13=500&p26=23&p52=3&p4=1004910200102131519955921867&p5=1662684189370000_1769833153869588&p6=US&p7=a2849f901c3210ca&p11=en&p14=185494&lid={loop_id}&p15=com.amazon.mShop.android.shopping&p33=000-111-222-333&p34=-1&p35=-1&p36=-1&p37=0&p38=0&p39=0&p40=10&p41=3943427&p48=0&p53=null&p62=1&p64=2&p65=3.4.4.4101&p66=2.1.6&p67=12345");
		System.out.println(enStr);
		String desStr = desEncrypt(enStr);
		System.out.println(desStr);
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

}