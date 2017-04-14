package com.basic.bustation.util;

import com.basic.bustation.controller.WebController;
import com.basic.bustation.dao.LinestationDAO;
import com.basic.bustation.dao.RoadlineDAO;
import com.basic.bustation.dao.RoadstationDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.TimerTask;

/**
 * Created by dell-pc on 2016/4/25.
 */
@Component
@Transactional(propagation= Propagation.REQUIRED)
public class InitTask extends TimerTask {

    protected static final Logger log = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private RoadlineDAO roadlineDAO;
    @Autowired
    private LinestationDAO linestationDAO;
    @Autowired
    private RoadstationDAO roadstationDAO;

    @Override
    public void run() {
        log.info("InitTask---------Run");
        roadlineDAO.findAll();
        linestationDAO.findAll();
        roadstationDAO.findAll();
    }
}
