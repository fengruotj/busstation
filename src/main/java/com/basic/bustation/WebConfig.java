package com.basic.bustation;

import com.basic.bustation.listener.IniitDataListener;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by dell-pc on 2016/4/19.
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
    }

    // 用于处理编码问题
    @Bean
    public FilterRegistrationBean characterEncodingFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        registration.setFilter(characterEncodingFilter);
        registration.addUrlPatterns("/*");
        registration.addInitParameter("encoding","utf-8");
        registration.setName("encodingFilter");
        return registration;
    }

    ///解决Hibernate OpenSessionInView
    @Bean
    public FilterRegistrationBean OpenSessionInViewFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        OpenSessionInViewFilter openSessionInViewFilter=new OpenSessionInViewFilter();
        registration.setFilter(openSessionInViewFilter);
        registration.addUrlPatterns("/*");
        registration.addInitParameter("singleSession", "true");
        registration.setName("OpenSessionInViewFilter");
        return registration;
    }

    @Bean
    public ServletListenerRegistrationBean InitDataListenre(){
        ServletListenerRegistrationBean registrationBean=new ServletListenerRegistrationBean();
        IniitDataListener iniitDataListener=new IniitDataListener();
        registrationBean.setListener(iniitDataListener);
        registrationBean.setName("initDataListener");
        registrationBean.setEnabled(true);
        return registrationBean;
    }

//    @Bean
//    public ServletRegistrationBean dispatcherServletRegistration() {
//        OL4JSFProxy ol4JSFProxyServlet=new OL4JSFProxy();
//        ServletRegistrationBean registration = new ServletRegistrationBean(ol4JSFProxyServlet,"/OL4JSFProxy/*");
//        registration.setLoadOnStartup(2);
//        return registration;
//    }

}
