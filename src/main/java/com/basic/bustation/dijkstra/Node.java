package com.basic.bustation.dijkstra;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell-pc on 2016/4/26.
 */
public class Node {
    private String name;
    private Map<Node,Integer> child=new HashMap<Node,Integer>();  //存放节点的相邻节点信息包括权值
    public Node(String name){
        this.name=name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Map<Node, Integer> getChild() {
        return child;
    }
    public void setChild(Map<Node, Integer> child) {
        this.child = child;
    }
}
