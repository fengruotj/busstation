package com.basic.bustation.dao;

import com.basic.bustation.Application;
import com.basic.bustation.model.Roadline;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.io.WKTReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest("server.port:0")
@TransactionConfiguration(defaultRollback=false)
@Transactional(propagation= Propagation.REQUIRED)
public class HibernateSpatialTest {
	@Resource
	private RoadlineDAO roadlineDAO;
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//fail("尚未实现");
		try {
			WKTReader formText = new WKTReader();
			Geometry geom = null;
			Roadline roadline=roadlineDAO.findById(Long.valueOf(2));
			geom = formText.read("LINESTRING(0 0, 10 10, 20 25, 50 60)");
			roadline.setLine((LineString)geom);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	@Test
	public void sys01(){
		System.out.println("fdsfsd");
	}
}
