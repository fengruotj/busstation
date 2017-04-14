package com.basic.bustation.dijkstra;

/**
 * Created by dell-pc on 2016/4/26.
 */

public class Main {
    public static void main(String[] args) {
        DijkstraAlgorithm test=new DijkstraAlgorithm();
        Node start=test.init();
        test.computePath(start);
        test.printPathInfo();
    }
}
