package com.zf.util.list;

public class People {

	private Long id;
	private Double conRate;

	public People(Long id, Double conRate) {
		super();
		this.id = id;
		this.conRate = conRate;
	}

	public People() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getConRate() {
		return conRate;
	}

	public void setConRate(Double conRate) {
		this.conRate = conRate;
	}

}
