package com.basic.bustation.controller;

import com.basic.bustation.model.Linestation;
import com.basic.bustation.model.Roadline;
import com.basic.bustation.model.Roadstation;
import com.basic.bustation.model.Stationtoline;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.vividsolutions.jts.geom.LineString;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell-pc on 2016/4/21.
 */
@Controller
@Transactional(propagation= Propagation.REQUIRED)
public class RoadlineController extends BaseController{

    //定义两个Gson过滤器用来过滤Gson中的字段或者类
    private static ExclusionStrategy exclusionStrategy=new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            String name=f.getName();
            return  name.contains("roadstationByEndid")|name.contains("roadstationByStartid")
                    |name.contains("stationtolines");
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            String name=aClass.getName();
            return name.contains("LineString");
        }
    };

   private static ExclusionStrategy datagridexclusionStrategy=new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            String name=f.getName();
            return  name.contains("linestations")|name.contains("stationtolines");
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            String name=aClass.getName();
            return name.contains("LineString");
        }
    };

    /**
     * 找到所有的路线
     * @return
     */
    @RequestMapping(value = "/roadline_findAllRoadlineByJson.action",
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findAllRoadlineByJson(){
        List<Roadline> roadlist=roadlineDAO.findAll();
        gson=new GsonBuilder().setExclusionStrategies(exclusionStrategy).create();
        Map pageMap=new HashMap<>();
        pageMap.put("rows",roadlist);
        pageMap.put("linestring",roadUtil.getLineStringPointsFromRoadList(roadlist));
        return gson.toJson(pageMap);
    }

    /**
     * 找到所有的路线通过分页显示
     * @param roadline
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/roadline_queryJoinStartStationAndEndStation.action",
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryJoinStartStationAndEndStation(Roadline roadline, @RequestParam Integer page,
                                                     @RequestParam Integer rows){
        System.out.println(roadline.getName());
        gson=new GsonBuilder().setExclusionStrategies(datagridexclusionStrategy).create();
        Map pageMap=new HashMap<>();
        pageMap.put("total", roadlineDAO.getCount(roadline.getName()));
        pageMap.put("rows", roadlineDAO.queryJoinStartStationAndEndStation(roadline.getName(), page, rows));
        return  gson.toJson(pageMap);
    }

    @RequestMapping(value = "/roadline_findRoadLineById.action",
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findRoadLineById(Roadline roadline){
        gson=new GsonBuilder().setExclusionStrategies(exclusionStrategy).create();
        Map pageMap= new HashMap<>();
        Roadline road=roadlineDAO.findById(roadline.getId());
        pageMap.put("rows",road);
        pageMap.put("linestring",roadUtil.getLineStringPoints(road));
        return gson.toJson(pageMap);
    }

    @RequestMapping(value = "/roadline_save.action",
            produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String save(Roadline model, @RequestParam String startTime1, @RequestParam
                            String endTime2) throws ParseException, java.text.ParseException {
        model.setRoadstationByEndid(roadstationDAO.findById(Long.valueOf(9)));
        model.setRoadstationByStartid(roadstationDAO.findById(Long.valueOf(9)));

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        model.setStartTime(new Time(sdf.parse(startTime1).getTime()));
        model.setEndTime(new Time(sdf.parse(endTime2).getTime()));
        roadlineDAO.save(model);
        return "success";
    }

    @RequestMapping(value = "/roadline_update.action",
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String update(@RequestParam String name,@RequestParam String startTime ,
                         @RequestParam String endTime, @RequestParam Long id) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Map map=new HashMap<>();
        try {
            Roadline model=roadlineDAO.findById(id);
            model.setId(id);
            model.setName(name);
            model.setStartTime(new Time(sdf.parse(startTime).getTime()));
            model.setEndTime(new Time(sdf.parse(endTime).getTime()));
            roadlineDAO.merge(model);
            map.put("success",true);
        }catch (Exception e){
            map.put("errorMsg",e.getMessage());
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/roadline_deleteRoadlineString.action",
            produces = "text/html;charset=UTF-8")
    @ResponseBody
    //清空roadlineString信息
    public String deleteRoadlineString(Roadline model){
        Roadline roadline=roadlineDAO.findById(model.getId());
        if(roadline.getLine()!=null){
            roadline.setLine(null);
        }
        return "success";
    }

    @RequestMapping(value = "/roadline_addRoadlineString.action",
            produces = "text/html;charset=UTF-8")
    @ResponseBody
    //添加线路空间数据Action
    public String addRoadlineString(Roadline model,@RequestParam String longitude,
                                  @RequestParam String latitude ){
        Roadline roadline=roadlineDAO.findById(model.getId());
        if(roadline.getLine()!=null){
            //修改之前的数据，对字符串进行处理
            String lines=roadline.getLine().toText();
            lines=lines.substring(0, lines.lastIndexOf(")"));
            lines+=" , "+longitude+" "+latitude+" )";
            System.out.println(lines);
            roadline.setLine((LineString)geomUtil.wktToGeometry(lines));
        }else{
            //第一次添加数据
            Roadstation startstation=roadline.getRoadstationByStartid();
            String lines="LINESTRING ("+startstation.getLongitude()+" "+startstation.getLatitude()+","+longitude+" "+latitude+")";
            roadline.setLine((LineString)geomUtil.wktToGeometry(lines));
        }
        return "success";
    }

    @RequestMapping(value = "/roadline_updatelotRoadlineString.action",
            produces = "text/html;charset=UTF-8")
    @ResponseBody
    //批量修改线路空间数据Action
    public String updatelotRoadlineString(Roadline model,@RequestParam  String temp){
        Roadline roadline=roadlineDAO.findById(model.getId());
        roadline.setLine((LineString)geomUtil.wktToGeometry(temp));
        return "success";
    }

    //这是公交线路搜索算法
    @RequestMapping(value = "/roadline_bussearch.action",
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String bussearch(@RequestParam String startaddress,@RequestParam String endaddress){
        //Roadstation startaddress=(Roadstation)roadstationDAO.findByName(startaddress)

        gson=new GsonBuilder().setExclusionStrategies(exclusionStrategy).create();
        Map pageMap=new HashMap<>();

        Roadstation startstation=busSearchUtil.findRoadStation(startaddress);
        Roadstation endstation=busSearchUtil.findRoadStation(endaddress);

        if(startstation==null||endstation==null){
            pageMap.put("result", "找不到这样的起点终点的站台,请输入正确的起点和终点的站台。");
            return gson.toJson(pageMap);
        }

        List<Stationtoline> startLineList=stationtolineDAO.findBystation(startstation);
        List<Stationtoline> endLineList=stationtolineDAO.findBystation(endstation);


        //一次换乘和直达的数据准备
        List<Roadline> X=new ArrayList<>();
        List<Roadline> Y=new ArrayList<>();

        List<List<Linestation>> O=new ArrayList<>();
        List<List<Linestation>> P=new ArrayList<>();

        for(Stationtoline stationtoline:startLineList){
            X.add(stationtoline.getRoadline());
            O.add((List<Linestation>)stationtoline.getRoadline().getLinestations());
        }
        for(Stationtoline stationtoline:endLineList){
            Y.add(stationtoline.getRoadline());
            P.add((List<Linestation>)stationtoline.getRoadline().getLinestations());
        }

//		算法搜索直达的公交路线 我这里将对象存储在了数组中。
//		步骤1：输入乘车的起始站点A和目的站点B。 
//		步骤2：搜索公交数据库，经过起始站点A的公交线路存为X(i)（i=1, 2, 3, □, m, m为正整数) , 经过目的站点B的公交线路存为Y(j) ( j=1, 2, 3, □, n, n为正整数) 。 
//		步骤3：判断是否有X(i) =Y(j) , 将满足条件的存入Z。 
//		若Z≥1，则公交线路X(i) 即Y(j) 为从站点A到站点B的直达最优线路，输出结果并结束运算。
        List<Roadline> roadline=new ArrayList<>();
        for(Roadline x: X)
            for(Roadline y: Y){
                if(x.getId()==y.getId()){
                    roadline.add(x);
                }
            }

        if(roadline.size()!=0){
            pageMap.put("result", "这里有"+roadline.size()+"条可选路径");
            pageMap.put("rows", roadline);
            pageMap.put("linestring",roadUtil.getLineStringPointsFromRoadList(roadline));
            String label="从"+startaddress+"乘坐";
            for(int i=0;i<roadline.size();i++){
                label+=roadline.get(i).getName();
                if(i!=roadline.size()-1)
                    label+="或";
            }
            label+="直达"+endaddress;
            pageMap.put("label", label);
            pageMap.put("type", 1);
            return gson.toJson(pageMap);
        }

//		//搜索一次换乘节点
//		步骤4：搜索公交数据库，将公交线路X(i)所包含的公交站点存为公交换乘矩阵O(i, u) ( u=1, 2, 3, □, g, g为正整数)，
//		公交线路Y(j)所包含的站点存为公交换乘矩阵P(j, v) ( v=1, 2, 3, □, h, h为正整数)。 
//		步骤5：判断是否有O(i, u) =P(j, v)，将满足条件的存入W，若W≥1，
//		则站点O(i, u) 即P(j, v) 为从站点A到站点B的一次换乘站点，公交线路X(i)、Y(j) 为换乘一次的最优路线, 输出结果并结束运算。 
        List<Map<String,Linestation>> linestationresult=new ArrayList<>();
        for(List<Linestation> o:O)
            for(List<Linestation> p :P)
                for(Linestation u: o)
                    for(Linestation j: p){
                        if(u.getRoadstation().getId()==j.getRoadstation().getId()){
                            boolean m_bool=true;
                            /**
                             * 这里通过循环遍历之前检测到的linestationresult结果，判断是否有两次乘车的首辆车的id是否相同。若
                             * 相同则必须进行选择出最佳的换乘站点。这里通过比较最先存储的换乘点到起点序列sn的距离，将距离最短的换乘linestation存储到
                             * linestationresult结果中
                             */
                            for(int i=0;i<linestationresult.size();i++){
                                Map<String,Linestation> temp=linestationresult.get(i);
                                Linestation satrtlinestation=linestationDAO.findByRoadlineAndStation(temp.get("start").getRoadline(), startstation);
                                if(u.getRoadline().getId()==temp.get("start").getRoadline().getId()){
                                    long sn=temp.get("start").getSn();
                                    if(Math.abs(satrtlinestation.getSn()-sn)>Math.abs(u.getSn()-sn)){
                                        temp.put("start", u);
                                    }
                                }
                                m_bool=false;
                            }
                            if(m_bool){
                                Map<String,Linestation> linestation=new HashMap<>();
                                linestation.put("start", u);
                                linestation.put("end", j);
                                linestationresult.add(linestation);
                            }
                        }
                    }

        if(linestationresult.size()!=0){
            //收集所有的交通线路
            List<Roadline> roadlineList=new ArrayList<>();
            //收集所有的换乘点信息
            List<Roadstation> stationList=new ArrayList<>();
            String label="";

            for(int j=0;j<linestationresult.size();j++){
                //添加查询结果的所有交通信息
                Linestation tempstart=linestationresult.get(j).get("start");
                Linestation tempend=linestationresult.get(j).get("end");
                roadlineList.add(tempstart.getRoadline());
                roadlineList.add(tempend.getRoadline());
                //添加查询结果的换乘点信息
                stationList.add(tempstart.getRoadstation());
                label+=(j+1)+ "、从"+startaddress+"乘坐";
                label+=tempstart.getRoadline().getName()+"到"+tempstart.getRoadstation().getName()+"换乘"+tempend.getRoadline().getName();
                label+="直达"+endaddress+"\n";
                if(j!=linestationresult.size()-1)
                    label+="或";
            }

            //添加道路信息
            pageMap.put("rows", roadlineList);
            //添加换乘站的信息
            pageMap.put("changestation", stationList);
            //添加路线标签信息
            pageMap.put("label", label);
            //添加result信息
            pageMap.put("result", "这里有"+linestationresult.size()+"条可选路径");
            pageMap.put("type", 2);
            pageMap.put("linestring",roadUtil.getLineStringPointsFromRoadList(roadlineList));
            return gson.toJson(pageMap);
        }
        pageMap.put("result", "很抱歉没有直达的乘车区间");

        System.out.println(startaddress+" "+endaddress);
        return gson.toJson(pageMap);
    }
}
