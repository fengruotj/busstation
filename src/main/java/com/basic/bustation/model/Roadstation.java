package com.basic.bustation.model;

import java.util.Set;

/**
 * Roadstation entity. @author MyEclipse Persistence Tools
 */

public class Roadstation implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String demo;
	private Double longitude;
	private Double latitude;
	private Double staytime;

	// Constructors

	/** default constructor */
	public Roadstation() {
	}

	/** minimal constructor */
	public Roadstation(String name, String demo, Double longitude,
			Double latitude,Double staytime) {
		this.name = name;
		this.demo = demo;
		this.longitude = longitude;
		this.latitude = latitude;
		this.staytime=staytime;
	}

	/** full constructor */
	public Roadstation(String name, String demo, Double longitude,
			Double latitude, Double staytime, Set roadsectionsForEndid,
			Set roadlinesForEndid, Set roadsectionsForStartid,
			Set stationtolines, Set roadlinesForStartid, Set linestations) {
		this.name = name;
		this.demo = demo;
		this.longitude = longitude;
		this.latitude = latitude;
		this.staytime = staytime;
	}

	// Property accessors


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDemo() {
		return this.demo;
	}

	public void setDemo(String demo) {
		this.demo = demo;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getStaytime() {
		return this.staytime;
	}

	public void setStaytime(Double staytime) {
		this.staytime = staytime;
	}


}
