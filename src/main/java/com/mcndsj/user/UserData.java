package com.mcndsj.user;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Matthew on 15/08/2016.
 */
@Getter
@Setter
public class UserData {

    private String name;
    private List<String> intelChannels;
    private boolean lootSpec = false;

    public UserData(String name){
        this.name = name;
        intelChannels = new ArrayList<String>();
        lootSpec = true;
    }

    public UserData(String name,boolean lootSpec,String ...intelChannels){
        this.intelChannels = Arrays.asList(intelChannels);
        this.name = name;
        this.lootSpec = lootSpec;
    }

    public void load(){

    }




}
