package com.basic.bustation.controller;

import org.ol4jsf.proxy.config.Environment;
import org.ol4jsf.proxy.config.OL4JSFProxyConfig;
import org.ol4jsf.proxy.config.OL4JSFProxyException;
import org.ol4jsf.proxy.config.Resource;
import org.ol4jsf.util.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dell-pc on 2016/4/21.
 */
@Controller
public class WebController extends  BaseController{

    private static final long serialVersionUID = 1L;
    private static OL4JSFProxyConfig ol4jsfProxyConfig;
    private static final Logger log = Logger.getLogger("OL4JSF-PROXY");

    @RequestMapping(value = "/",produces = "text/html;charset=UTF-8")
    public String index(){
        return "index";
    }

    @RequestMapping("send_{var1}_{var2}.action")
    public String sendFunc(@PathVariable("var1") String var1,@PathVariable("var2") String var2){
        return var1+"/"+var2;
    }

    @RequestMapping(value = "/OL4JSFProxy/*")
    public void OL4JSFProxy(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(ol4jsfProxyConfig == null) {
            log.warning("OL4JSFProxy is not configured.");
            response.getWriter().println("OL4JSFProxy is not configured.");
        } else {
            String uri = request.getRequestURI();
            String resourceName = uri.substring(uri.lastIndexOf("/") + 1, uri.length());
            Environment env = ol4jsfProxyConfig.getEnvironment();
            if(env != null && env.getResource(resourceName) != null) {
                Resource resource = env.getResource(resourceName);
                String destURL = resource.getUrl();
                log.fine("URL: " + destURL);
                boolean doGet = request.getMethod().equals("GET");
                if(doGet) {
                    destURL = destURL + "?" + request.getQueryString();
                }

                PrintWriter xmlOut = null;

                try {
                    URL u = new URL(destURL);
                    HttpURLConnection acon = (HttpURLConnection)u.openConnection();
                    acon.setAllowUserInteraction(false);
                    acon.setRequestMethod(request.getMethod());
                    if(!doGet) {
                        acon.setRequestProperty("Content-Type", request.getContentType());
                    }

                    acon.setDoOutput(true);
                    acon.setDoInput(true);
                    acon.setUseCaches(false);
                    String in;
                    String count;
                    if(resource.getAuthentication() != null) {
                        String output = resource.getAuthentication().getPassword();
                        String buffer = resource.getAuthentication().getUsername() + ":" + output;
                        in = Base64.encode(buffer).replaceAll("(\r)?\n", "");
                        count = "Basic " + in;
                        acon.setRequestProperty("Authorization", count);
                    }

                    if(!doGet) {
                        BufferedReader output1 = request.getReader();
                        StringBuilder buffer1 = new StringBuilder();
                        in = null;

                        while((in = output1.readLine()) != null) {
                            buffer1.append(in);
                        }

                        output1.close();
                        count = buffer1.toString();
                        String charsetName = request.getCharacterEncoding() != null?request.getCharacterEncoding():"UTF-8";
                        xmlOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(acon.getOutputStream(), charsetName)));
                        xmlOut.write(count);
                        xmlOut.flush();
                    }

                    if(acon.getResponseCode() >= 400) {
                        response.setContentType("application/xml");
                        PrintWriter output2 = response.getWriter();
                        output2.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                        output2.println("<servlet-exception>");
                        output2.println("HTTP response: " + acon.getResponseCode() + "\n" + URLDecoder.decode(acon.getResponseMessage(), "UTF-8"));
                        output2.println("</servlet-exception>");
                        output2.close();
                    } else {
                        response.setContentType(acon.getContentType());
                        response.setHeader("Content-disposition", acon.getHeaderField("Content-disposition"));
                        ServletOutputStream output3 = response.getOutputStream();
                        byte[] buffer2 = new byte[1024];
                        InputStream in1 = acon.getInputStream();

                        for(int count1 = in1.read(buffer2); count1 != -1; count1 = in1.read(buffer2)) {
                            output3.write(buffer2, 0, count1);
                        }

                        in1.close();
                        output3.close();
                    }
                } finally {
                    if(xmlOut != null) {
                        xmlOut.close();
                    }

                }
            } else {
                log.warning("ol4jsf-proxy.xml is not configured properly.");
            }

        }
    }

    static {
        try {
            ol4jsfProxyConfig = OL4JSFProxyConfig.getOL4JSFProxyConfig("/ol4jsf-proxy.xml");
        } catch (OL4JSFProxyException var1) {
            log.log(Level.WARNING, "Fail on loading ol4jsf-proxy.xml.", var1);
        }

    }
}
