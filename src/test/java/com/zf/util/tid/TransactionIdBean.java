package com.zf.util.tid;

import java.util.Date;

public class TransactionIdBean {
    private String subsite;
    private String source;
    private int offerid;
    private String geo;
    private String placementid;
    private double maxPayout;
    private long time = System.currentTimeMillis() / 1000;//10位时间戳
    private Double convPayout;//postback中payout参数
    private Date convTime;
    private String tid;//原始tid
    private String subsiteType;
    private String uniq;//拼接的最原始的唯一ID标识其中包含服务器IP
    private Integer ctit;
    private String appid;
    private String placementType;
	private boolean isclick;//true 为click  false为imp
	private String davm;//1:davm 0:非davm

    public Integer getCtit() {
        return ctit;
    }

    public void setCtit(Integer ctit) {
        this.ctit = ctit;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPlacementType() {
        return placementType;
    }

    public void setPlacementType(String placementType) {
        this.placementType = placementType;
    }

    public String getSubsiteType() {
		return subsiteType;
	}

	public void setSubsiteType(String subsiteType) {
		this.subsiteType = subsiteType;
	}

	public String getSubsite() {
        return subsite;
    }

    public void setSubsite(String subsite) {
        this.subsite = subsite;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getOfferid() {
        return offerid;
    }

    public void setOfferid(int offerid) {
        this.offerid = offerid;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getPlacementid() {
        return placementid;
    }

    public void setPlacementid(String placementid) {
        this.placementid = placementid;
    }

    public double getMaxPayout() {
        return maxPayout;
    }

    public void setMaxPayout(double maxPayout) {
        this.maxPayout = maxPayout;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Double getConvPayout() {
        return convPayout;
    }

    public void setConvPayout(Double convPayout) {
        this.convPayout = convPayout;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Date getConvTime() {
        return convTime;
    }

    public void setConvTime(Date convTime) {
        this.convTime = convTime;
    }

    public String getUniq() {
		return uniq;
	}

	public void setUniq(String uniq) {
		this.uniq = uniq;
	}

	public String getDavm() {
		return davm;
	}

	public void setDavm(String davm) {
		this.davm = davm;
	}

	@Override
	public String toString() {
		return "TransactionIdBean [subsite=" + subsite + ", source=" + source + ", offerid=" + offerid + ", geo=" + geo
				+ ", placementid=" + placementid + ", maxPayout=" + maxPayout + ", time=" + time + ", convPayout="
				+ convPayout + ", convTime=" + convTime + ", tid=" + tid + ", subsiteType=" + subsiteType + ", uniq="
				+ uniq
				+ ", davm="
				+ davm
				+ "]";
	}

	public String log() {
        return "subsite=" + subsite + "&source=" + source
                + "&offerid=" + offerid + "&geo=" + geo + "&placementid="
 + placementid + "&maxPayout=" + maxPayout + "&time=" + time + "&convPayout=" + convPayout + "&uniq=" + uniq + "&davm=" + davm;
    }

	public boolean isIsclick() {
		return isclick;
	}

	public void setIsclick(boolean isclick) {
		this.isclick = isclick;
	}

}
