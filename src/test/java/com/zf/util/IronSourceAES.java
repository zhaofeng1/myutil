package com.zf.util;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Arrays;

/**
 * ironSource 加密
 */
public class IronSourceAES {
    private static boolean mDidSendEncryptionFailEventInSession = false;

    public static final String KEY = "C38FB23A402222A0C17D34A92F971D1F";

    public static synchronized String encode(String keyString, String stringToEncode) {
        String str;
        synchronized (IronSourceAES.class) {
            if (StringUtils.isEmpty(keyString)) {
                str = "";
            } else if (StringUtils.isEmpty(stringToEncode)) {
                str = "";
            } else {
                try {
                    Security.addProvider(new BouncyCastleProvider());
                    SecretKeySpec skeySpec = getKey(keyString);
                    byte[] clearText = stringToEncode.getBytes("UTF8");
                    byte[] iv = new byte[16];
                    Arrays.fill(iv, (byte) 0);
                    IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
                    cipher.init(1, skeySpec, ivParameterSpec);
                    byte[] decoded = cipher.doFinal(clearText);
                    str = SDKBase64.encodeToString(cipher.doFinal(clearText), 0).replaceAll(System.getProperty("line.separator"), "");
                } catch (Exception e) {
                    e.printStackTrace();
                    str = "";
                }
            }
        }
        return str;
    }

    public static synchronized String decode(String keyString, String stringToDecode) {
        String str;
        synchronized (IronSourceAES.class) {
            if (StringUtils.isEmpty(keyString)) {
                str = "";
            } else if (StringUtils.isEmpty(stringToDecode)) {
                str = "";
            } else {
                try {
                    SecretKey key = getKey(keyString);
                    byte[] iv = new byte[16];
                    Arrays.fill(iv, (byte) 0);
                    IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                    byte[] encrypedPwdBytes = SDKBase64.decode(stringToDecode, 0);
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                    cipher.init(2, key, ivParameterSpec);
                    str = new String(cipher.doFinal(encrypedPwdBytes));
                } catch (Exception e) {
                    e.printStackTrace();
                    if (!mDidSendEncryptionFailEventInSession) {
                        mDidSendEncryptionFailEventInSession = true;
                        JSONObject data = new JSONObject();
                        try {
                            data.put("status", "false");
                            data.put("reason", 1);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
//                        InterstitialEventsManager.getInstance().sendEventToUrl(new EventData(IronSourceConstants.REVIVED_EVENT, data), "https://track.atom-data.io");
                    }
                    str = "";
                }
            }
        }
        return str;
    }

    private static SecretKeySpec getKey(String key) throws UnsupportedEncodingException {
        byte[] keyBytes = new byte[32];
        Arrays.fill(keyBytes, (byte) 0);
        byte[] passwordBytes = key.getBytes("UTF-8");
        System.arraycopy(passwordBytes, 0, keyBytes, 0, passwordBytes.length < keyBytes.length ? passwordBytes.length : keyBytes.length);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public static void main(String[] args){
        String enParamsStr = IronSourceAES.encode(IronSourceAES.KEY, "platform=android&applicationKey=2.6.2&applicationUserId=35b640b5-6847-4206-a7a0-d7e4e4038c85&sdkVersion=6.8.2&advId=35b640b5-6847-4206-a7a0-d7e4e4038c85&appVer=1.0&osVer=26&devMake=samsung&devModel=SM-G950U1&connType=4g");//特定加密方式
        System.out.println(enParamsStr);
    }

}
