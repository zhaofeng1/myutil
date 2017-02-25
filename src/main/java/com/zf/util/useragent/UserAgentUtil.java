package com.zf.util.useragent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 *
 * Create on 2016年4月19日 下午5:54:54
 *
 * @author qinzheng.
 *
 */
public class UserAgentUtil {

	private static Logger logger = LoggerFactory.getLogger(UserAgentUtil.class);

	/**
	 * 根据User-Agent获取客户端版本号：android/ios
	 * 
	 * @param agent
	 * @param ua
	 * @return
	 */
	public static String getOsVersion(UserAgent agent) {
		return parseOsVersion(agent);
	}

	/**
	 * 根据User-Agent获取客户端版本号：android/ios
	 * 
	 * @param ua
	 * @return
	 */
	public static String getOsVersion(String ua) {
		UserAgent agent = new UserAgent(ua);
		String osversion = parseOsVersion(agent);
		return osversion;
	}

	private static String parseOsVersion(UserAgent agent) {
		String osversion = "";
		String line = null;
		try {
			line = agent.getUserAgentString().toLowerCase();
			if (agent.getOperatingSystem().getGroup().getName().equals("Android")) {
				switch (agent.getOperatingSystem().getManufacturer()) {
				case ULTRAFONE:
				case A8:
				case ZEN:
					osversion = line.split("version/")[1].split(" ")[0];
					break;
				default:
					String[] normal = { "android ", "android", "android/", "adr " };
					for (String split : normal) {
						if (line.contains(split)) {
							osversion = line.split(split)[1].trim().replace(";", " ").replace("_", " ").replace("-", " ").replace("\\", " ").split(" ")[0];
							break;
						}
					}
					break;
				}
			} else if ("iOS".equals(agent.getOperatingSystem().getGroup().getName())) {
				osversion = line.split("os ")[1].trim().split(" ")[0].trim();
			}
			osversion = osversion.replace(";", "").replace(")", "").replace("/", "").trim();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("bad ua-parse " + line);
		}
		return osversion;
	}
}
