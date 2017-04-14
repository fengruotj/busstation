package com.basic.bustation.controller;

import com.basic.bustation.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by dell-pc on 2016/4/21.
 */
@Controller
@Transactional(propagation= Propagation.REQUIRED)
public class RoadstationController extends BaseController{

    @RequestMapping(value = "/roadstation_save.action",
            produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String save(Roadstation model,@RequestParam long idroad){

        //首先保存该站点信息
        if(roadstationDAO.getCount(model.getName())==0){
            roadstationDAO.save(new Roadstation(model.getName(), model.getDemo(), model.getLongitude(), model.getLatitude(),model.getStaytime()));
        }

        Roadline roadline=roadlineDAO.findById(idroad);
        List<Linestation> linestation=roadline.getLinestations();
        Roadstation roadstation=(Roadstation)roadstationDAO.findByName(model.getName()).get(0);

        //如果该用户线路是第一次添加站点，那么就要更新他的起始站点
        if(linestation.size()==0){
            System.out.println("First station");
            roadline.setRoadstationByStartid(roadstation);
            linestationDAO.save(new Linestation(roadstation,roadlineDAO.findById(idroad),Long.valueOf(0) ));
        }else{
            int size=linestation.size();
            linestationDAO.save(new Linestation(roadstation,roadlineDAO.findById(idroad),Long.valueOf(size--) ));
            roadline.setRoadstationByEndid(roadstation);
        }

        //更新线路端节点
        List<Roadsection> roadsectionlist=roadline.getLinestations();
        if(roadsectionlist.size()!=0){
            Roadstation roadstationEndid=roadstation;
            Roadstation roadstationByStartid=linestation.get(linestation.size()-1).getRoadstation();
            String name=roadstationByStartid.getName()+"-"+roadstationEndid.getName();
            roadsectionDAO.save(new Roadsection(roadstationEndid, roadstationByStartid, name, 5.0,200.0));
        }

        //更新StationtoLine
        stationtolineDAO.save(new Stationtoline(roadstation, roadline, roadstation.getName()));
        return "success";
    }

    @RequestMapping(value = "/roadstation_findByName.action",
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findStationByStationName(@RequestParam String stationName){
        Roadstation roadstation=stationtolineDAO.get("from Roadstation s where s.name=?",stationName);
        return gson.toJson(roadstation);
    }
}
