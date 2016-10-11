package com.mcndsj;

import com.mcndsj.graph.GraphManager;
import com.mcndsj.observers.IntelController;
import com.mcndsj.utils.FileUtils;
import com.mcndsj.utils.SoundUtils;
import com.mcndsj.gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Matthew on 21/06/2016.
 */
public class Core {

    private static Core instance;
    private MainFrame frame;
    private static List<String > cache;
    private static long heartBeats = 0;
    private Timer timer;

    public static String version = "beta V0.5";

    public Core(){
        instance = this;
        init();
    }

    public static void heartBeats(){
        heartBeats = System.currentTimeMillis();
        if(getInstance().timer == null){
            getInstance().timer = new Timer();
            getInstance().timer.schedule(new TimerTask() {
                int count  = 0;
                @Override
                public void run() {
                    if(count > 10){
                        System.exit(-1);
                    }
                    if(System.currentTimeMillis() - heartBeats > 1000 * 10){
                        count ++;
                        std("检测到内核线程崩溃,可能有啥BUG发生了,尝试重启内核中……",true);
                        IntelController.get().forceKillTimer();
                        IntelController.get().setUpTimer();
                    }else{
                        if(count > 0){
                            std("内核重启完毕……",true);
                        }
                        count = 0;
                    }
                }
            },1000,1000);
        }
    }

    public void init(){
        //check resources
        File f = new File("regions");
        if(!f.exists()){
            try {
                FileUtils.ExportResource("/regions");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        f = new File("sound/sound.wav");
        if(!f.exists()){
            try {
                FileUtils.ExportResource("/sound");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //backend init
        GraphManager gm = new GraphManager();



        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame  = new MainFrame(gm);
    }

    public static void main(String[] args){
        // do io
        Core c = new Core();
    }

    public static void std(String s,boolean pop){
        std(s,pop,Color.BLACK);
    }

    public static void std(String s, boolean pop, Color color){
        try {
            for(String ss : cache){
                getInstance().frame.stdout(ss,false,color);
            }
            cache.clear();
            getInstance().frame.stdout(s,pop,color);
        }catch(Exception e){
            if(cache == null){
                cache = new ArrayList<String>();
            }
            cache.add(s);
        }
    }


    public static void warn(String s){
        std(s,true,Color.RED);
        SoundUtils.play();

    }


    public static Core getInstance(){
        return instance;
    }






}
