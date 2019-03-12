package com.zf.util;



import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESUtilNew {

	private static String AES_KEY = "TCbdWjFVqkNS9Let";
    private static final Logger logger = LoggerFactory.getLogger(AESUtilNew.class);
    
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
		//uv
		//		System.out
		//				.println(encrypt("{\"conf_version\":\"7\",\"app_pkg\":\"com.altamob.android.sdkdemo.adserver\",\"app_version\":\"1.0\",\"sdk_version\":\"5.2.0.6.9\",\"aid\":\"21c45fae3dfc2f7b\",\"gaid_encrypted\":1,\"gaid\":\"5lko7jG+ybpuf4J8kGHu1jmxYHFsluxMMPZCgDFnpUc3+F1/6l1LJB/ZrwhI67rm\",\"os_version\":\"8.0.0\",\"country\":\"HK\",\"kernel_version\":\"\",\"language\":\"zh\",\"network_type\":\"1\",\"user_agent\":\"Mozilla/5.0 (Linux; Android 8.0.0; SM-G9500 Build/R16NW; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/68.0.3440.91 Mobile Safari/537.36\",\"extras\":{\"GoogleAdvertiserID\":\"5lko7jG+ybpuf4J8kGHu1jmxYHFsluxMMPZCgDFnpUc3+F1/6l1LJB/ZrwhI67rm\",\"android_id\":\"RItmNx9wHy9UUlLHnziyEA==\",\"brand\":\"samsung\",\"device\":\"dreamqltechn\",\"product\":\"dreamqltezh\",\"sdk_init\":26,\"deviceType\":\"user\",\"packagename\":\"com.altamob.android.sdkdemo.adserver\",\"model\":\"SM-G9500\",\"manufacturer\":\"samsung\",\"cpu_abi\":\"arm64-v8a\",\"buildName\":\"R16NW\",\"display_id\":\"R16NW.G9500ZHU3CRG5\",\"abi\":\"arm64-v8a\",\"abi2\":\"\",\"arch\":\"\",\"latitude\":0,\"longitude\":0,\"timestamp\":0,\"lang_code\":\"zh\",\"lang\":\"English\",\"app_version_code\":\"1\",\"screen_size\":\"normal\",\"screen_format\":\"long\",\"screenDensity\":\"high\",\"displayWidth\":1080,\"displayHeight\":2220,\"macaddress\":\"\",\"mcc\":-1,\"mnc\":-1,\"art\":\"arm64\",\"sensors\":\"[{sT=1, sVS=[-0.40678406, 0.6699829, 9.765106], sN=LSM6DSL Accelerometer, sV=STMicroelectronics, sVE=[-0.35891724, 0.62931824, 9.8033905]}, {sT=2, sVS=[86.476135, 8.421326, -120.285034], sN=AK09916C Magnetometer, sV=AKM, sVE=[86.439514, 7.383728, -119.0979]}, {sT=4, sVS=[4.5776367E-4, 0.0014038086, -0.0013275146], sN=LSM6DSL Gyroscope, sV=STMicroelectronics, sVE=[-7.6293945E-4, 1.8310547E-4, 0.0011138916]}]\",\"ip\":\"fe80::986e:b4ff:fee7:6350%dummy0\",\"screenLayout\":268435810,\"screenDensity_1\":480,\"apiLevel\":26,\"screenWidth_1\":1080,\"screenHeight_1\":2220,\"limit_ad_tracking\":\"false\",\"connectionType\":\"WIFI\",\"networkType\":\"WIFI\",\"wiredHeadset\":true,\"networkOperator\":\"46001\",\"newNetworkOperatorName\":\"\",\"deviceFreeSpace\":51509192}}"));
		//		System.err.println(desEncrypt("FWKAv0619CCczOBM8V3FP5Asm5jHcFwAw/h7PM+v5TuW6E1xz5KEOXVKPtaovYZF"));
		//		System.out
		//				.println(encrypt("type=01&p1=30557&p2=10164&p3=10402&p8=1.04&p9=&p12=150504164&p13=500&p26=23&p52=3&p4=1004910200102131519955921867&p5=1662684189370000_1769833153869588&p6=JP&p7=a2849f901c3210ca&p11=en&p14=185494&lid={loop_id}&p15=com.enfeel.MMR&p33=000-111-222-333&p34=-1&p35=-1&p36=-1&p37=0&p38=0&p39=0&p40=10&p41=3943427&p48=0&p53=null&p62=1&p64=2&p65=3.4.4.4101&p66=2.1.6&p67=12345"));
		//sdk install
		//		System.out
		//				.println(encrypt("{\"update\":0,\"app_pkg\":\"com.altamob.android.sdkdemo.adserver\",\"model\":\"Android SDK built for x86\",\"transactionId\":\"\",\"network_type\":\"4\",\"net_statu\":4,\"kernel_version\":\"Linux version 3.10.0+ (lfy@lfy0.mtv.corp.google.com) (gcc version 4.9 20140827 (prerelease) (GCC) ) #1 SMP PREEMPT Wed Feb 22 08:53:42 PST 2017\",\"app_version\":\"1.0\",\"aid\":\"24a4752414ec8102\",\"gaid_encrypted\":1,\"ctit\":0,\"version\":1,\"timestamp\":1543410490280,\"ins_type\":0,\"pkg_name\":\"com.speed.booster.kim\",\"event\":0,\"offerId\":\"\",\"gaid\":\"2OcA+JEM1xsn7hbCi5vbpOMWSSafZYFuOvrTavZ3ImXqqlkmxfe/vboKgFr1GxKb\",\"sdk_version\":\"5.2.0.9.Y\",\"label\":\"Booster\",\"versionName\":\"1.0\",\"country\":\"US\",\"os_version\":\"6.0\",\"extras\":{\"usmMode\":\"adb\",\"networkType\":\"MOBILE\",\"versionIncremental\":\"4729342\",\"app_version_code\":\"1\",\"arch\":\"\",\"mcc\":-1,\"abi\":\"x86\",\"gyroscopeSensor\":\"[]\",\"isSimulator\":1,\"buildProduct\":\"sdk_google_phone_x86\",\"versionBaseband\":\"\",\"android_id\":\"eb841k0KECIaRtfeq6KtPA==\",\"gravitySensor\":\"[]\",\"limit_ad_tracking\":\"false\",\"longitude\":0,\"simOperatorName\":\"Android\",\"rssi\":-127,\"batteryPropertyCapacity\":0,\"screen_format\":\"normal\",\"screenLayout\":268435794,\"networkOperator\":\"310260\",\"art\":\"x86\",\"newNetworkOperatorName\":\"Android\",\"screen_size\":\"normal\",\"connectionType\":\"MOBILE\",\"wiredHeadset\":true,\"deviceFreeSpace\":432048,\"displayHeight\":1794,\"wifiInterface\":\"\",\"hasGps\":true,\"buildTags\":\"test-keys\",\"apiLevel\":23,\"manufacturer\":\"unknown\",\"bootSerialno\":\"\",\"latitude\":0,\"availMem\":\"1148312\",\"lang_code\":\"en\",\"vmName\":\"Dalvik\",\"screenHeight_1\":1794,\"model\":\"Android SDK built for x86\",\"pixel\":\"Android SDK built for x86\",\"sdk_init\":23,\"densityDpi\":420,\"displayWidth\":1080,\"lang\":\"English\",\"screenDensity\":\"high\",\"timestamp\":0,\"buildName\":\"MASTER\",\"simOperator\":\"310260\",\"deviceType\":\"userdebug\",\"GoogleAdvertiserID\":\"2OcA+JEM1xsn7hbCi5vbpOMWSSafZYFuOvrTavZ3ImXqqlkmxfe/vboKgFr1GxKb\",\"screenWidth_1\":1080,\"mnc\":-1,\"abi2\":\"\",\"screenDensity_1\":420,\"simNetworkType\":13,\"packagename\":\"com.altamob.android.sdkdemo.adserver\",\"simState\":\"READY\",\"isMountedExternalStorage\":false,\"sensors\":\"[{sT=1, sV=The Android Open Source Project, sVE=[0.0, 9.81, 0.0], sVS=[0.0, 9.81, 0.0], sN=Goldfish 3-axis Accelerometer}, {sT=2, sV=The Android Open Source Project, sVE=[22.0, 5.9, 43.1], sVS=[22.0, 5.9, 43.1], sN=Goldfish 3-axis Magnetic field sensor}]\",\"product\":\"sdk_google_phone_x86\",\"cpu_abi\":\"x86\",\"hasFingerprint\":false,\"externalStorageSize\":\"812531712\",\"device\":\"generic_x86\",\"brand\":\"Android\",\"display_id\":\"sdk_google_phone_x86-userdebug 6.0 MASTER 4729342 test-keys\"},\"user_agent\":\"Mozilla/5.0 (Linux; Android 6.0; Android SDK built for x86 Build/MASTER; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36\",\"language\":\"en\",\"add_time\":\"1543410490280\",\"natural\":0}"));
		//vps 111
		System.out
.println(encrypt("type=01&p1=30053&p2=6657314&p3=10850&p9=6657314&p12=203671820&p5=1662684189370000_1769833153870389&p7=4e15fe03b27719d5&p33=7d30f0d6-07d1-469b-a8f9-cfcbd390d9ce"));
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
				AES_KEY = "TCbdWjFVqkNS9Let";
            }

            SecretKeySpec keyspec = new SecretKeySpec(AES_KEY.getBytes("UTF-8"), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(AES_KEY.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return Base64.encodeBase64String(encrypted).trim();

        } catch (Exception e) {
			logger.error("encrypt error :", e);
        }catch (OutOfMemoryError e) {
			logger.error("encrypt error : ", e);
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
			logger.error("desEncrypt error:" + e);
        }catch (OutOfMemoryError e) {
        	logger.error( "encrypt error : " + e);
        }
        return null;
    }

}