package com.mcndsj.graph;

import com.mcndsj.Core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 21/06/2016.
 */
public class GraphManager {


    private List<Graph> list;

    public GraphManager(){
        this.list = new ArrayList<Graph>();
        load();
    }

    public void load(){
        File f = new File("regions");
        Core.std("加载地图数据库中.",false);
        if(f!= null && f.isDirectory()){
            File[] all = f.listFiles();
            for(File temp : all){
                if(temp.getName().contains("json")){
                    try {
                        list.add(new Graph(temp.getName().replace(".json", ""), temp));
                    }catch(Exception e){
                        Core.std("文件无法解析" + temp.getName() + "请确认文件是否损坏!",false);
                    }
                }
            }
            Core.std("加载成功!",false);

        }else{
            Core.std("地图数据库文件夹(regions)不存在,请完全解压本程序!",false);
        }
    }

    public String[] getAllGraphs(){
        String[] s = new String[list.size()];
        for(int i = 0 ; i < list.size() ; i ++){
            s[i] = list.get(i).getName();
        }
        return s;
    }

    public Graph getGraph(String s) throws Exception {
        for(Graph g : list){
            if(g.getName().equals(s)){
                return g;
            }
        }
        throw new Exception("Null com.mcndsj.graph.Graph Exception!");
    }

}
