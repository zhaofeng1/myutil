package com.zf.model;

import java.util.List;

/**
 * 产品信息
 * @author ZhaoFeng
 * @date 2016年12月27日
 */
public class ProdInfo {

	private String prodTitle;
	private List<String> imgUrls;
	private List<String> details;
	private String rp;
	private String sebelum;
	private String diskon;
	private String descTitle;
	private String descHtml;
	
	
	public String getProdTitle() {
		return prodTitle;
	}
	public void setProdTitle(String prodTitle) {
		this.prodTitle = prodTitle;
	}
	public List<String> getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(List<String> imgUrls) {
		this.imgUrls = imgUrls;
	}
	public String getRp() {
		return rp;
	}
	public void setRp(String rp) {
		this.rp = rp;
	}
	public String getSebelum() {
		return sebelum;
	}
	public void setSebelum(String sebelum) {
		this.sebelum = sebelum;
	}
	public String getDiskon() {
		return diskon;
	}
	public void setDiskon(String diskon) {
		this.diskon = diskon;
	}
	public List<String> getDetails() {
		return details;
	}
	public void setDetails(List<String> details) {
		this.details = details;
	}
	public String getDescTitle() {
		return descTitle;
	}
	public void setDescTitle(String descTitle) {
		this.descTitle = descTitle;
	}
	public String getDescHtml() {
		return descHtml;
	}
	public void setDescHtml(String descHtml) {
		this.descHtml = descHtml;
	}
	
	
}
