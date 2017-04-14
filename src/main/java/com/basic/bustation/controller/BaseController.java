package com.basic.bustation.controller;

import com.basic.bustation.dao.*;
import com.basic.bustation.util.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dell-pc on 2016/4/21.
 */
public class BaseController {

    @Autowired
    public ApplicationContext applicationContext;

    @ModelAttribute("BasePath")
    public String getBasePath(HttpServletRequest httpServletRequest){
        return httpServletRequest.getContextPath();
    }

    protected Gson gson = new Gson();

    protected static final Logger log = LoggerFactory.getLogger(WebController.class);

    @Autowired
    protected LinestationDAO linestationDAO;
    @Autowired
    protected RoadlineDAO roadlineDAO;
    @Autowired
    protected RoadsectionDAO roadsectionDAO;
    @Autowired
    protected RoadstationDAO roadstationDAO;
    @Autowired
    protected StationtolineDAO stationtolineDAO;
    @Autowired
    protected BusSearchUtil busSearchUtil;
    @Autowired
    protected GeomUtil geomUtil;
    @Autowired
    protected RoadUtil roadUtil;
    @Autowired
    protected JsonUtil jsonUtil;
    @Autowired
    protected FreeMarkerUtil freeMarkerUtil;

}
