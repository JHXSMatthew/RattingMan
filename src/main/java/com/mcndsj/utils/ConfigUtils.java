package com.mcndsj.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

/**
 * Created by Matthew on 21/06/2016.
 */
public class ConfigUtils {

    private static JSONObject json;

    private static String defaultFile = "Phoenix.Intel";
    private static String defaultRange = "5";
    private static String defaultSystem = "RU-PT9";


    public static void load(){
        File config = new File("config");
        if(config.isDirectory() || !config.exists()){
            try {
                config.createNewFile();
                JSONObject obj = new JSONObject();
                obj.put("system",defaultSystem);
                obj.put("range",defaultRange);
                obj.put("section",defaultFile);
                FileUtils.writeFile(config.getPath(),obj.toJSONString());
                json = obj;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                String j = FileUtils.readFile(config.getPath());
                JSONParser parser = new JSONParser();
                json = (JSONObject) parser.parse(j);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getSystem(){
        if(json == null){
            load();
        }
        String returnValue = defaultSystem;
        try{
            returnValue = (String) json.get("system");
        }catch(Exception e){

        }
        return returnValue;
    }

    public static String getRange(){
        if(json == null){
            load();
        }
        String returnValue = defaultRange;
        try{
            returnValue = (String) json.get("range");
        }catch(Exception e){

        }
        return returnValue;
    }

    public static String getLastSection(){
        if(json == null){
            load();
        }
        String returnValue = defaultFile;
        try{
            returnValue = (String) json.get("section");
        }catch(Exception e){

        }
        return returnValue;
    }

    public static void saveConfig(String section,String system,String range){
        File config = new File("config");
        JSONObject obj = new JSONObject();
        obj.put("system",system);
        obj.put("range",range);
        obj.put("section",section);
        try {
            FileUtils.writeFile(config.getPath(),obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        json = obj;
    }

}
