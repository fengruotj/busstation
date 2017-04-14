package com.basic.bustation.util;

import com.basic.bustation.model.Point;
import com.basic.bustation.model.Roadline;
import com.vividsolutions.jts.geom.LineString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("roadUtil")
public class RoadUtil {
	public List<Point> getLineStringPoints(Roadline roadline){
		LineString linestring=roadline.getLine();
		List<Point> listpoints=new ArrayList<>(); 
		if(linestring!=null){
			for(int i=0;i<linestring.getNumPoints();i++){
				com.vividsolutions.jts.geom.Point temp=linestring.getPointN(i);
				listpoints.add(new Point(temp.getX(),temp.getY()));
			}
		}
		return listpoints;
	}
	
	public List<List<Point>> getLineStringPointsFromRoadList(List<Roadline> roadlinelist){
		List<List<Point>> points=new ArrayList<>();
		for(Roadline road:roadlinelist){
			points.add(getLineStringPoints(road));
		}
		return points;
	}
}
