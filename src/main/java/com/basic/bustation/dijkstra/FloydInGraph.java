package com.basic.bustation.dijkstra;

/**
 * Created by dell-pc on 2016/4/26.
 */

import com.basic.bustation.model.Roadsection;
import com.basic.bustation.model.Roadstation;

import java.util.ArrayList;
import java.util.List;


public class FloydInGraph {

    /**
     * 图	邻接矩阵 		最短路径		弗洛伊德算法
     */
    private static double INF=Double.MAX_VALUE;   //边长度的最大值
    //dist[i][j]=INF<==>no edges between i and j
    public double[][] dist;                       //边之间的距离
    //the distance between i and j.At first,dist[i][j] is the weight of edge [i,j]
    private int[][] path;                      //路径
    public List<Integer> result=new ArrayList<Integer>();

    private double[][] matrix;                   //图的临界矩阵

    private List<Roadstation> roadstations=new ArrayList<>();

    /**
     * 初始化图结构
     * @param roadsectionList 站台信息链表
     * @param type    类型 0为 权值为时间  1位权值为距离
     */
    public void initMap(List<Roadsection> roadsectionList,int type){
        int size=roadsectionList.size()+1;
        this.path=new int[size][size];
        this.dist=new double[size][size];
        this.matrix=new double[size][size];

        //初始化临界矩阵将所有的权重初始化
        for(int num1=0;num1<size;num1++)
            for(int num2=0;num2<size;num2++){
                matrix[num1][num2]=Double.MAX_VALUE;
            }

        //初始化临界矩阵
        for(int i=0;i<roadsectionList.size();i++){
            Roadsection roadsection=roadsectionList.get(i);
            int start,end;///临界矩阵数组下标
            start=insertRoadsection(roadsection.getRoadstationByStartid());
            end=insertRoadsection(roadsection.getRoadstationByEndid());
            if(type==0){
                //权值为时间
                matrix[start][end]=roadsection.getElapsedtime();
                matrix[end][start]=roadsection.getElapsedtime();
            }else if(type==1){
                //权值为距离
                matrix[start][end]=roadsection.getDistance();
                matrix[end][start]=roadsection.getDistance();
            }
        }

    }

    /**
     * 插入一个新的站点到roadstations中
     * @param roadstation 站点
     * @return
     */
    public int insertRoadsection(Roadstation roadstation){
        int isSameId=isSameStation(roadstation);
        int mutex=-1;
        if(isSameId>=0){
            mutex=isSameId;
        }else {
            mutex=roadstations.size();
            roadstations.add(roadstation);
        }
        return mutex;
    }
    /**
     * 判断是否有相同的站台，若是则返回相应数组的下标。若没有则返回-1
     * @param temp 检测的站台
     * @return
     */
    private int isSameStation(Roadstation temp){
        int result=-1;
        for(int i=0;i<roadstations.size();i++){
            Roadstation roadstation=roadstations.get(i);
            if(temp.getId()==roadstation.getId())
            {
                result=i;
                break;
            }
        }
        return result;
    }

    /**
     * 找到最短路径
     * @param startstation
     * @param endstation
     */
    public double findMIXDistance(Roadstation startstation,Roadstation endstation){
        int start=isSameStation(startstation);
        int end=isSameStation(endstation);
        findCheapestPath(start,end,matrix);
        return this.dist[start][end];
    }

    /**
     * 返回最后的结果经过的站点
     * @return
     */
    public List<Roadstation> findelapsedStations(){
        List<Roadstation> list=new ArrayList<>();
        for(int num:result){
            list.add(roadstations.get(num));
       }
        return list;
    }

    public static void main(String[] args) {
//        FloydInGraph graph=new FloydInGraph(5);
//        Double[][] matrix={
//                {INF,30,INF,10,50},
//                {INF,INF,60,INF,INF},
//                {INF,INF,INF,INF,INF},
//                {INF,INF,INF,INF,30},
//                {50,INF,40,INF,INF},
//        };
//        int begin=0;
//        int end=4;
//        graph.findCheapestPath(begin,end,matrix);
//        List<Integer> list=graph.result;
//        System.out.println(begin+" to "+end+",the cheapest path is:");
//        System.out.println(list.toString());
//        System.out.println(graph.dist[begin][end]);
    }

    public  void findCheapestPath(int begin,int end,double[][] matrix){
        floyd(matrix);
        result.add(begin);
        findPath(begin,end);
        result.add(end);
    }

    public void findPath(int i,int j){
        int k=path[i][j];
        if(k==-1)return;
        findPath(i,k);
        result.add(k);
        findPath(k,j);
    }


    public  void floyd(double[][] matrix){
        int size=matrix.length;
        //initialize dist and path
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                path[i][j]=-1;
                dist[i][j]=matrix[i][j];
            }
        }
        for(int k=0;k<size;k++){
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++){
                    if(dist[i][k]!=INF&&
                            dist[k][j]!=INF&&
                            dist[i][k]+dist[k][j]<dist[i][j]){//dist[i][k]+dist[k][j]>dist[i][j]-->longestPath
                        dist[i][j]=dist[i][k]+dist[k][j];
                        path[i][j]=k;
                    }
                }
            }
        }

    }

    public FloydInGraph() {
    }

    public FloydInGraph(int size){
        this.path=new int[size][size];
        this.dist=new double[size][size];
        this.matrix=new double[size][size];
    }
}

