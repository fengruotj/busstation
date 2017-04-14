package com.basic.bustation.util;

import com.basic.bustation.dao.RoadsectionDAO;
import com.basic.bustation.dao.RoadstationDAO;
import com.basic.bustation.dijkstra.FloydInGraph;
import com.basic.bustation.model.Roadstation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component("busSearchUtil")
public class BusSearchUtil {

    @Autowired
    RoadstationDAO roadstationDAO;

    @Autowired
    RoadsectionDAO roadsectionDAO;

    @Autowired
    JsonUtil jsonUtil;

    public Roadstation findRoadStation(String stationName){
        String hql="from Roadstation where name like ?";
        String param="%"+stationName+"%";
        return roadstationDAO.get(hql,param);
    }

    /**
     *  公交搜索算法基于Dijsraz最短路径算法
     * @param startStation  起始站点
     * @param endstartStation   结束站点
     * @param type     类型 0为 权值为时间  1位权值为距离
     * @return
     */
    public Map<String,Object> findBusSearch(String startStation,String endstartStation,int type){
        Map<String,Object> map=new HashMap<>();
        Roadstation startstation=findRoadStation(startStation);
        Roadstation endstation=findRoadStation(endstartStation);
        if(startstation!=null&endstation!=null){
            //起始站点有数据
            FloydInGraph graph=new FloydInGraph();
            graph.initMap(roadsectionDAO.findAll(),type);
            double weight=graph.findMIXDistance(startstation,endstation);
            if(weight!=Double.MAX_VALUE){
                map.put("roadstations",jsonUtil.roadStationList(graph.findelapsedStations()));
                map.put("type",type);
                map.put("weight",weight);
                map.put("status","success");
            }else{
                map.put("status","没有这样的最短路径");
            }
        }
        else{
            //没有这样的起始站点
            map.put("status","找不到这样的起点终点的站台,请输入正确的起点和终点的站台。");
        }

        return map;
    }
}
