package com.mcndsj.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 15/08/2016.
 */
public class UserManager {

    private static UserManager manager;

    static{
        manager = new UserManager();
    }

    private List<UserData> users;

    public UserManager(){
        users = new ArrayList<>();
    }


    public static UserManager get(){
        return manager;
    }

    public UserData getUser(String name){
        for(UserData data : users){
            if(data.getName().equals(name)){
                return data;
            }
        }
        users.add(new UserData(name));
        return getUser(name);
    }

}
