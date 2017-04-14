package com.basic.bustation.model;

/**
 * Stationtoline entity. @author MyEclipse Persistence Tools
 */

public class Stationtoline implements java.io.Serializable {

	// Fields

	private Long id;
	private Roadstation roadstation;
	private Roadline roadline;
	private String name;

	// Constructors

	/** default constructor */
	public Stationtoline() {
	}

	/** full constructor */
	public Stationtoline(Roadstation roadstation, Roadline roadline, String name) {
		this.roadstation = roadstation;
		this.roadline = roadline;
		this.name = name;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Roadstation getRoadstation() {
		return this.roadstation;
	}

	public void setRoadstation(Roadstation roadstation) {
		this.roadstation = roadstation;
	}

	public Roadline getRoadline() {
		return this.roadline;
	}

	public void setRoadline(Roadline roadline) {
		this.roadline = roadline;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
