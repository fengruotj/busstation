package com.basic.bustation.util;

import com.basic.bustation.model.Roadsection;
import com.basic.bustation.model.Roadstation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component("jsonUtil")
public class JsonUtil {
	
	public List<Map<String,Object>> roadsectionList(List<Roadsection> roadsectionList){
		List<Map<String,Object>> mapList=new ArrayList<>();
		for(Roadsection roadsection:roadsectionList){
			Map<String,Object> map=new HashMap<>();
			map.put("id",roadsection.getId());
			map.put("name", roadsection.getName());
			map.put("statrtstation", roadsection.getRoadstationByStartid().getName());
			map.put("endstation", roadsection.getRoadstationByEndid().getName());
			map.put("elapsedtime", roadsection.getElapsedtime());
			map.put("distance",roadsection.getDistance());
			mapList.add(map);
		}
		return mapList;
	}

    /**
     * 将链表站点信息转换为json对象
     * @param roadstationList
     * @return
     */
	public List<Map<String,Object>> roadStationList(List<Roadstation> roadstationList){
		List<Map<String,Object>> mapList=new ArrayList<>();
		for(Roadstation roadstation:roadstationList){
			Map<String,Object> map=new HashMap<>();
            map=roadstationToJson(roadstation);
			mapList.add(map);
		}
		return mapList;
	}

    //将站点信息转换为json对象
	public Map<String,Object> roadstationToJson(Roadstation roadstation){
		Map map=new HashMap();
		map.put("id",roadstation.getId());
		map.put("name",roadstation.getName());
		map.put("demo",roadstation.getDemo());
		map.put("longitude",roadstation.getLongitude());
		map.put("latitude",roadstation.getLatitude());
		map.put("staytime",roadstation.getStaytime());
		return map;
	}

}
