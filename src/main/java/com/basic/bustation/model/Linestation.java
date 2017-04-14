package com.basic.bustation.model;

/**
 * Linestation entity. @author MyEclipse Persistence Tools
 */

public class Linestation implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5837365191847441112L;
	private Long id;
	private Roadstation roadstation;
	transient private Roadline roadline;
	private Long sn;

	// Constructors

	/** default constructor */
	public Linestation() {
	}

	/** full constructor */
	public Linestation(Roadstation roadstation, Roadline roadline, Long sn) {
		this.roadstation = roadstation;
		this.roadline = roadline;
		this.sn = sn;
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

	public Long getSn() {
		return this.sn;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

}
