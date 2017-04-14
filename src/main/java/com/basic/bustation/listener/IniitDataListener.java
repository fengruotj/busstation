package com.basic.bustation.listener;

import com.basic.bustation.util.InitTask;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by dell-pc on 2016/4/24.
 */

/**
 * 初始化监听事件
 */
@WebListener
public class IniitDataListener implements ServletContextListener{

    private ApplicationContext applicationContext=null;

    private InitTask initTask;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("init");
        //初始化加载数据
        applicationContext=applicationContext= WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        initTask= (InitTask) applicationContext.getBean("initTask");
        initTask.run();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("destory");
    }
}
