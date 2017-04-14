package com.basic.bustation.controller;

import com.basic.bustation.model.Roadsection;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell-pc on 2016/4/21.
 */
@Controller
@Transactional(propagation= Propagation.REQUIRED)
public class RoadSectionController extends BaseController{

    @RequestMapping(value = "/roadsection_queryRoadSection.action",
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryRoadSection(Roadsection model, @RequestParam Integer page,@RequestParam Integer rows){
        Map pageMap=new HashMap<String, Object>();
        pageMap.put("total", roadsectionDAO.getCount(model.getName()));
        System.out.println(roadsectionDAO.getCount(model.getName()));
        List<Roadsection> roadSectionList=roadsectionDAO.queryWithPageAndSize(model.getName(), page, rows);
        pageMap.put("rows", jsonUtil.roadsectionList(roadSectionList));
        return gson.toJson(pageMap);
    }

    @RequestMapping(value = "/roadsection_update.action",
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String update(Roadsection model){
        Map map=new HashMap<>();
        try {
            Roadsection roadsection=roadsectionDAO.findById(model.getId());
            roadsection.setName(model.getName());
            roadsection.setDistance(model.getDistance());
            roadsection.setElapsedtime(model.getElapsedtime());
            map.put("success",true);
        }catch (Exception e){
            map.put("errorMsg",e.getMessage());
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    @RequestMapping(value = "/roadsection_savedistance.action",
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String saveDistance(Roadsection model){
        Map map=new HashMap<>();
        try {
            Roadsection roadsection=roadsectionDAO.findById(model.getId());
            roadsection.setDistance(model.getDistance());
            map.put("success",true);
        }catch (Exception e){
            map.put("errorMsg",e.getMessage());
            e.printStackTrace();
        }
        return gson.toJson(map);
    }
}
