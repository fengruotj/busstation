package com.basic.bustation.dijkstra;

import com.basic.bustation.Application;
import com.basic.bustation.dao.RoadsectionDAO;
import com.basic.bustation.dao.RoadstationDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dell-pc on 2016/4/26.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest("server.port:0")
@TransactionConfiguration(defaultRollback=false)
@Transactional(propagation= Propagation.REQUIRED)
public class FloydInGraphTest {

    @Autowired
    private RoadsectionDAO roadsectionDAO;
    @Autowired
    private RoadstationDAO roadstationDAO;

    @Test
    public void testFindMIXDistance() throws Exception {
        FloydInGraph graph=new FloydInGraph();
        graph.initMap(roadsectionDAO.findAll(),1);
        graph.findMIXDistance(roadstationDAO.getById(Long.valueOf(33)),roadstationDAO.getById(Long.valueOf(52)));
    }
}
