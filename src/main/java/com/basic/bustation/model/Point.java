package com.basic.bustation.model;

public class Point implements java.io.Serializable {
	
	private Double longitude;
	private Double latitude;
	
	public Point(double x, double y) {
		// TODO 自动生成的构造函数存根
		longitude=x;
		latitude=y;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	
}
