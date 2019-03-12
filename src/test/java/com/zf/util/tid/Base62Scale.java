package com.zf.util.tid;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 将传进来的字符串进行加密
 * 需要保证以 _ 进行拼接，拼接的串必须是数字
 */
public class Base62Scale {

    private static final BigInteger BASE = BigInteger.valueOf(62);
    /** 自定义12进制字典 */
    private static final char[] DICT = {'0','1','2','3','4','5','6','7','8','9','_','.'};
    private static final BigInteger DICT_LENGTH = BigInteger.valueOf(12);
    private static final Map<Character, Integer> dictMap = new HashMap<Character, Integer>();
    static {
        for (int i = 0; i < DICT.length; i++) {
            dictMap.put(Character.valueOf(DICT[i]), i);
        }
    }

    private static String encode62(final BigInteger num) {
        if (0 == BigInteger.ZERO.compareTo(num)) {
            return "0";
        }
        BigInteger value = num.add(BigInteger.ZERO);
        StringBuilder sb = new StringBuilder();
        while(BigInteger.ZERO.compareTo(value) < 0) {
            BigInteger[] quotient = value.divideAndRemainder(BASE);
            int remainder = quotient[1].intValue();
            if (remainder < 10) {
                sb.insert(0, (char) (remainder + '0'));
            } else if (remainder < 10 + 26) {
                sb.insert(0, (char) (remainder + 'a' - 10));
            } else {
                sb.insert(0, (char) (remainder + 'A' - 10 - 26));
            }
            value = quotient[0];
        }
        return sb.toString();
    }

    private static String decode10(BigInteger num) {
        if (0 == BigInteger.ZERO.compareTo(num)) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        BigInteger value = num.add(BigInteger.ZERO);
        while(BigInteger.ZERO.compareTo(value) < 0) {
            BigInteger[] quotient = value.divideAndRemainder(DICT_LENGTH);
            sb.insert(0, DICT[quotient[1].intValue()]);
            value = quotient[0];
        }
        return sb.toString();
    }

    private static BigInteger decode62(String base62Str) {
        return decode(base62Str.getBytes(StandardCharsets.UTF_8));
    }

    private static BigInteger decode(byte[] base62Bytes) {
        BigInteger res = BigInteger.ZERO;
        BigInteger multer = BigInteger.ONE;
        for (int i = base62Bytes.length - 1; i >= 0; i--) {
            res = res.add(multer.multiply(BigInteger.valueOf(alphabetValueOf(base62Bytes[i]))));
            multer = multer.multiply(BASE);
        }
        return res;
    }

//    private static BigInteger encode102(String base12Str) {
//        byte[] bytes = base12Str.getBytes(StandardCharsets.UTF_8);
//        BigInteger res = BigInteger.ZERO;
//        BigInteger multer = BigInteger.ONE;
//        for (int i = bytes.length - 1; i >= 0; i--) {
//            res = res.add(multer.multiply(BigInteger.valueOf(dictMap.get(bytes[i]))));
//            multer = multer.multiply(BASE);
//        }
//        return res;
//    }

    private static int alphabetValueOf(byte bytee) {
        if (Character.isLowerCase(bytee)) {
            return bytee - ('a' - 10);
        } else if (Character.isUpperCase(bytee)) {
            return bytee - ('A' - 10 - 26);
        }
        return bytee - '0';
    }

    private static BigInteger encode10(String base10Str) {
        char[] base10Chars = base10Str.toCharArray();
        BigInteger res = BigInteger.ZERO;
        BigInteger multer = BigInteger.ONE;
        for (int i = base10Chars.length - 1; i >=0; i--) {
            res = res.add(multer.multiply(BigInteger.valueOf(dictMap.get(base10Chars[i]))));
            multer = multer.multiply(DICT_LENGTH);
        }
        return res;
    }
    
    public static String encode(String str){
    	try {
			return Base62Scale.encode62((Base62Scale.encode10(str)));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public static String decode(String str){
    	try {
			return Base62Scale.decode10(Base62Scale.decode62(str));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    

    public static String getUniqKey(String subsite,String source,String offerid,String countryCode,String bid,String uniqID){
    	String key = new StringBuffer()
		.append(subsite)
		.append("_"+source)
		.append("_"+offerid)
		.append("_"+countryCode)
		.append("_"+bid)
//		.append("_"+CalendarUtils.formatYearMonthDayHourNoSeparated(Calendar.getInstance().getTime()))
		.append("_"+"2018021211")
		.append("_"+uniqID)
		.toString();
//    	System.out.println(key);
    	return Base62Scale.encode62((Base62Scale.encode10(key)));
    }
    
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<90;i++){
        	sb.append("9");
        }
        System.out.println(sb.toString().length());
        String res = Base62Scale.encode62((Base62Scale.encode10(sb.toString())));
        System.out.println(res);
        System.out.println(res.length());
        System.out.println(Base62Scale.decode10(Base62Scale.decode62(res)));
        
    }
}
