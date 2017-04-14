package com.basic.bustation.model;

import com.vividsolutions.jts.geom.LineString;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Roadline entity. @author MyEclipse Persistence Tools
 */

public class Roadline implements java.io.Serializable {

	// Fields

	private Long id;
	private Roadstation roadstationByEndid;
	private Roadstation roadstationByStartid;
	private String name;
	private Time startTime;
	private Time endTime;
	private LineString line;
	private List linestations = new ArrayList<>();
	private List stationtolines = new ArrayList();

	// Constructors

	/** default constructor */
	public Roadline() {
	}

	/** minimal constructor */
	public Roadline(String name) {
		this.name = name;
	}

	/** full constructor */
	public Roadline(Roadstation roadstationByEndid,
			Roadstation roadstationByStartid, String name, Time startTime,
			Time endTime, List linestations, List stationtolines) {
		this.roadstationByEndid = roadstationByEndid;
		this.roadstationByStartid = roadstationByStartid;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.linestations = linestations;
		this.stationtolines = stationtolines;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Roadstation getRoadstationByEndid() {
		return this.roadstationByEndid;
	}

	public void setRoadstationByEndid(Roadstation roadstationByEndid) {
		this.roadstationByEndid = roadstationByEndid;
	}

	public Roadstation getRoadstationByStartid() {
		return this.roadstationByStartid;
	}

	public void setRoadstationByStartid(Roadstation roadstationByStartid) {
		this.roadstationByStartid = roadstationByStartid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Time getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime =startTime;
	}

	public Time getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime =endTime;
	}

	public List getLinestations() {
		return this.linestations;
	}

	public void setLinestations(List linestations) {
		this.linestations = linestations;
	}

	public List getStationtolines() {
		return this.stationtolines;
	}

	public void setStationtolines(List stationtolines) {
		this.stationtolines = stationtolines;
	}

	public LineString getLine() {
		return line;
	}

	public void setLine(LineString line) {
		this.line = line;
	}
	
}
