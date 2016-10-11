package com.mcndsj.observers;

import com.mcndsj.Core;
import com.mcndsj.graph.Graph;
import com.mcndsj.utils.ConfigUtils;
import com.mcndsj.utils.FileUtils;
import com.sun.deploy.config.Config;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.*;
import java.util.Timer;

/**
 * Created by Matthew on 21/06/2016.
 */
public class IntelController extends Timer {

    public static IntelController observer;


    private Graph g;
    private String location;
    private int range;
    private TimerTask currentTask;

    private String systemPath;

    private IntelController(String systemPath){
        System.err.println(systemPath);
        this.systemPath = systemPath;

    }


    public void watchStart(Graph g, final String current, final int range){
        if(g == null){
            Core.std("监控开始,聊天频道/数据文件:"+ g.getName() +" 当前星系:" + current  + " 监控范围:" + range,true);
        }else{
            Core.std("监控开始,聊天频道/数据文件:"+ g.getName() +" 当前星系:" + current  + " 监控范围:" + range,true);
        }
        this.g = g;
        this.location = current;
        this.range = range;

        setUpTimer();

    }

    public boolean rescue(){
        setUpTimer();
        return true;
    }

    public String getSystemPath(){
        return systemPath;
    }


    public void forceKillTimer(){
        currentTask.cancel();
        currentTask = null;
        this.purge();
    }

    public void setUpTimer(){
        try{
            currentTask.cancel();
        }catch(Exception e){

        }
        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();

        File chatFileFolder = new File(fw.getDefaultDirectory().getPath() + File.separatorChar +"EVE" + File.separatorChar + "logs" + File.separatorChar +"Chatlogs");
        if(chatFileFolder == null || !chatFileFolder.exists()) {
            Core.std("无法定位文档,尝试枚举...",false);
            chatFileFolder = new File(getSystemPath() + File.separatorChar + "My Documents" + File.separatorChar + "EVE" + File.separatorChar + "logs" + File.separatorChar + "Chatlogs");
            if (chatFileFolder == null || !chatFileFolder.exists()) {
                chatFileFolder = new File(getSystemPath() + File.separatorChar + "Documents" + File.separatorChar + "EVE" + File.separatorChar + "logs" + File.separatorChar + "Chatlogs");
                if (chatFileFolder == null || !chatFileFolder.exists()) {
                    chatFileFolder = new File(getSystemPath() + File.separatorChar + "我的文档" + File.separatorChar + "EVE" + File.separatorChar + "logs" + File.separatorChar + "Chatlogs");
                    if (chatFileFolder == null || !chatFileFolder.exists()) {
                        Core.std("枚举失败,无法找到游戏资料目录,请反馈给 Eclipse Tekitsu !",false);
                        Core.std("错误信息: " + fw.getDefaultDirectory().getPath(),false);
                        return;
                    }
                }
            }
            Core.std("枚举成功!",false);
        }

        final File overf = chatFileFolder;
        final Graph relativeg = g;
        currentTask = new TimerTask() {
            File lastInteFile = null;
            int lastInteLine = 0;
            boolean firstRun = true;

            String lastOwner = null;
            String lastDate;


            @Override
            public void run() {
                Core.heartBeats();
                File toChekck = getLastWatchFile();
                if(lastInteFile == null || !lastInteFile.equals(toChekck)){
                    lastInteFile = toChekck;
                    lastInteLine = 0;
                    lastOwner = FileUtils.getFileOwner(lastInteFile);
                    Core.std("当前预警主监控更换至角色:" + lastOwner,false);
                }

                try {
                    Scanner s = new Scanner(lastInteFile,"UTF-16LE");
                    ArrayList<String> list = new ArrayList<String>();

                    while (s.hasNextLine()){
                        String str = s.nextLine();
                        list.add(str);
                    }
                    s.close();

                    if(firstRun){
                        lastInteLine = list.size();
                        firstRun = false;
                        return;
                    }

                    if(lastInteLine >= list.size()){
                        return;
                    }
                    for(int i = lastInteLine ; i < list.size() ; i ++){
                        String currentString =list.get(i);
                        System.out.println(currentTask);
                        if(!currentString.contains("clr") && !currentString.contains("status") ){
                            if(lastDate != null && !FileUtils.isAfter(FileUtils.getLineDate(currentString),lastDate))
                                continue;

                            for (String system : relativeg.getAllSystems()) {
                                if (currentString.contains(system)) {
                                    int jumps = relativeg.getJump(system, location,range);
                                    if (jumps <= range) {
                                        Core.warn("预警:发现敌人在 " + system + " 距离当前星系 " + jumps + " 跳.");
                                        Core.std(currentString,false);
                                        lastDate = FileUtils.getLineDate(currentString);
                                    }
                                }
                            }
                        }

                        lastInteLine = i + 1;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

            public File getLastWatchFile(){
                File[] files_array = overf.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.contains(ConfigUtils.getLastSection());
                    }
                });

                File lastFile = files_array[files_array.length - 1];
                for(File f : files_array){
                    if(f.lastModified() > lastFile.lastModified()){
                        System.out.println(f.lastModified());
                        lastFile = f;
                    }
                }
                return lastFile;
            }
        };

        this.schedule(currentTask,1000,100);
    }


    public static IntelController get(){
        if(observer == null){
            observer = new IntelController(System.getProperty("user.home"));
        }
        return observer;
    }


}
