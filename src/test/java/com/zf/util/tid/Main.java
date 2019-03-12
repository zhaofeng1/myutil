package com.zf.util.tid;


public class Main {

	private static String SPECIAL_STR = "_";
	private static String SPECIAL_STR_IMP = "-";

	public static final String TYPE_IMP = "imp";

	private static void long2bytes(long value, byte[] bytes, int offset) {
		for (int i = 7; i > -1; i--) {
			bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);
		}
	}

	public static String compressedUUID() {
		return "1234567890" + Thread.currentThread().getId() + "" + System.currentTimeMillis();
	}


	public static TransactionIdBean getDecodeBase62UniqueKey(String tid) {
		if ((tid.startsWith(SPECIAL_STR) && tid.endsWith(SPECIAL_STR)) || (tid.startsWith(SPECIAL_STR_IMP) && tid.endsWith(SPECIAL_STR_IMP))) {
			boolean isclick = true;
			if (tid.startsWith(SPECIAL_STR_IMP) && tid.endsWith(SPECIAL_STR_IMP)) {
				isclick = false;
			}
			String resource = Base62Scale.decode(tid.substring(1, tid.length() - 1));
			String[] ss = resource.split("_");
			if (ss.length == 7 || ss.length == 8) {
				String uniq = ss[0];
				TransactionIdBean bean = new TransactionIdBean();
				bean.setSubsite(ss[1]);
				bean.setSource(ss[2]);
				bean.setOfferid(Integer.valueOf(ss[3]));
				bean.setGeo(String2AssicUtil.decodeCountryCode(ss[4]));
				bean.setMaxPayout(Double.valueOf(ss[5]));
				if (ss[6].contains(".")) {
					bean.setPlacementid(ss[6].replace(".", "_"));
				} else {
					bean.setPlacementid(ss[6]);
				}
				if (ss.length == 8) {
					bean.setDavm(ss[7]);
				}
				bean.setTime(Long.valueOf(uniq.substring(uniq.length() - 13, uniq.length() - 3)));
				bean.setTid(tid);
				bean.setUniq(uniq);
				bean.setIsclick(isclick);
				return bean;
			} else {
				//				log.error("decode failure --> tid:" + tid);
				return null;
			}

		}
		return null;
	}

	/**
	 * 加密
	 * @param bean
	 * @param type
	 * @param pidCode
	 * @return
	 */
	public static String getBase62UniqueKey(TransactionIdBean bean, String type, String pidCode) {
		String uniq = "1234";
		String subsite = bean.getSubsite();
		String source = bean.getSource();
		String offerid = bean.getOfferid() + "";
		String geo = String2AssicUtil.encodeCountryCode(bean.getGeo());
		String davm = bean.getDavm();

		String price = bean.getMaxPayout() + "";
		if (price.length() > 5) {
			price = price.substring(0, 5);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(uniq).append("_").append(subsite).append("_").append(source).append("_").append(offerid).append("_").append(geo).append("_").append(price).append("_").append(pidCode).append("_").append(davm);
		try {
			String base62 = Base62Scale.encode(sb.toString());
			if (base62 == null) {
				//				log.error("bean:" + JSON.toJSONString(bean));
			}
			if (type.equals(TYPE_IMP)) {
				return SPECIAL_STR_IMP + base62 + SPECIAL_STR_IMP;
			}
			return SPECIAL_STR + base62 + SPECIAL_STR;
		} catch (Exception e) {
			e.printStackTrace();
			return uniq;
		}

	}

	public static void main(String[] args) {
		String pidCode = "15";
		//        TransactionIdBean bean = new TransactionIdBean();
		////        bean.setSubsite("22222");
		////        bean.setSource("11111");
		////        bean.setOfferid(123456);
		////        bean.setMaxPayout(123.3);
		////        bean.setGeo("US");
		////        String rs = getBase62UniqueKey(bean, "click", pidCode);
		//        System.out.println("tid:" + rs);

		int[] offerIds = new int[] { 185895797, 12512222 };
		String[] geos = new String[] { "IN", "IN" };

		for (int i = 0; i < offerIds.length; i++) {
			TransactionIdBean tmp = new TransactionIdBean();
			tmp.setSubsite("30777");
			tmp.setSource("1111");
			tmp.setOfferid(offerIds[i]);
			tmp.setGeo(geos[i]);
			tmp.setDavm("0");
			;
			String result = getBase62UniqueKey(tmp, "click", pidCode);
			System.out.println("offerId:" + offerIds[i] + " tid:" + result);
		}


		//        bean = getDecodeBase62UniqueKey("_mWXy34pZaO8vSnOXsjPJRa02YepZHwE2Ix_");
		//        System.out.println(bean);
	}
}
