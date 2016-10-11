package com.mcndsj.graph;

import com.mcndsj.utils.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Matthew on 21/06/2016.
 */
public class Graph {

    private String name = null;
    private HashMap<String,String[]> connections;

    public Graph(String name,File f) throws IOException, ParseException {
        this.name = name;
        connections = new HashMap<String,String[]>();

        String s = FileUtils.readFile(f.getPath());
        JSONParser paser = new JSONParser();
        JSONArray array = (JSONArray) paser.parse(s);
        for(Object obj : array){
            JSONObject jobj = (JSONObject) obj;
            String system = (String) jobj.get("name");
            JSONArray connections = (JSONArray) jobj.get("connections");
            this.connections.put(system, (String[]) connections.toArray(new String[connections.size()]));
            //System.err.println(system + " Connections:" + Arrays.toString(this.connections.get(system)));
        }
    }

    public String getName(){
        return this.name;
    }

    public boolean isConnected(){
        return false;
    }

    public int getJump(String from, String to, int jump){
        System.err.println("Searching from " + from +" to " + to);
        PriorityQueue<SearchNode> queue = new PriorityQueue<>();
        queue.add(new SearchNode(null,from));
        while(!queue.isEmpty()){
            SearchNode current = queue.poll();
            if(current.getName().equals(to)){
                System.err.println(" - " + current.getCost() + "jumps!");
                return current.getCost();
            }
            if(current.getCost() >= jump){
                System.err.println(" - out of jumps ");
                return Integer.MAX_VALUE;
            }
            String[] str = connections.get(current.getName());
            if(str == null)
                continue;

            for(String s : str){
                if(!connections.containsKey(s)){
                    continue;
                }
                if(current.hasBeen(s)){
                    continue;
                }
                queue.add(new SearchNode(current,s));
            }
        }
        System.err.println(" - no connections ");
        return Integer.MAX_VALUE;
    }


    public boolean isInGraph(String str){
        for(String s : connections.keySet()){
            if(str.equals(s)){
                return true;
            }
        }
        return false;
    }

    public Set<String> getAllSystems(){
        return connections.keySet();
    }



}
