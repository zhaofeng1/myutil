package com.zf.mybitset.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author zhaofeng
 *
 */
public class AppInfo {

	private Integer id;
	private String appid;
	private String status;
	private String platform;
	private Set<String> geos;

	public AppInfo(int id, String appid, String status, String platform, String geos) {
		this.id = id;
		this.appid = appid;
		this.status = status;
		this.platform = platform;
		this.geos = new HashSet<String>();
		if (StringUtils.isNotBlank(geos)) {
			for (String geo : geos.split(",")) {
				this.geos.add(geo);
			}
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Set<String> getGeos() {
		return geos;
	}

	public void setGeos(Set<String> geos) {
		this.geos = geos;
	}

}
