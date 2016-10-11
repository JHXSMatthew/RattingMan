package com.mcndsj.utils;

import javax.sound.sampled.*;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

/**
 * Created by Matthew on 21/06/2016.
 */
public class SoundUtils {
    private static Clip buffer;

    private static String voiceName = "kevin16";

    public  static void play(){
        try{
            if(buffer == null){
                buffer = SoundUtils.load(new File("sound/sound.wav"));
            }
            buffer.start();
            buffer = null;
        }catch(Exception e){
            System.out.println("Sound not supported.");
            e.printStackTrace();
        }
    }


    private static Clip load(File f){
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
