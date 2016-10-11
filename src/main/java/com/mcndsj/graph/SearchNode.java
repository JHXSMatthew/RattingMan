package com.mcndsj.graph;

import java.util.Comparator;

/**
 * Created by Matthew on 21/06/2016.
 */
public class SearchNode implements Comparable {

    private SearchNode last;
    private String current;
    private int cost = 0;

    public SearchNode(SearchNode node, String current){
        last = node;
        this.current = current;
        try {
            this.cost = node.getCost() + 1;
        }catch(Exception e){

        }
    }

    public boolean hasGone(String name){
            if(current.equals(name)){
                return true;
            }else if(last == null){
                return false;
            }else{
                return last.hasGone(name);
            }

    }

    public SearchNode getNode(){
        return last;
    }

    public String getName(){
        return current;
    }

    public int getCost(){
        return cost;
    }

    @Override
    public int compareTo(Object o) {
        return getCost() - ((SearchNode)o).getCost();
    }
}
