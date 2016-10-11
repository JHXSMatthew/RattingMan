package com.mcndsj.utils;

import com.mcndsj.Core;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Matthew on 21/06/2016.
 */
public class FileUtils {
    static public String ExportResource(String resourceName) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        try {
            stream = FileUtils.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(FileUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(jarFolder + resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }

        return jarFolder + resourceName;
    }

    public static String readFile(String path)
            throws IOException {

        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }

    public static void writeFile(String path, String write)
            throws IOException {

        Files.write(Paths.get(path),write.getBytes());
    }


    public static String getSystemPath(){
        return System.getProperty("user.home");
    }

    public static File getEVELogDirectory(){
        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        File eveFileDir = new File(fw.getDefaultDirectory().getPath() + File.separatorChar +"EVE" + File.separatorChar + "logs");
        if(eveFileDir == null || !eveFileDir.exists()) {
            Core.std("无法定位文档,尝试枚举...",false);
            eveFileDir = new File(getSystemPath() + File.separatorChar + "My Documents" + File.separatorChar + "EVE" + File.separatorChar + "logs" );
            if (eveFileDir == null || !eveFileDir.exists()) {
                eveFileDir = new File(getSystemPath() + File.separatorChar + "Documents" + File.separatorChar + "EVE" + File.separatorChar + "logs" );
                if (eveFileDir == null || !eveFileDir.exists()) {
                    eveFileDir = new File(getSystemPath() + File.separatorChar + "我的文档" + File.separatorChar + "EVE" + File.separatorChar + "logs");
                    if (eveFileDir == null || !eveFileDir.exists()) {
                        Core.std("枚举失败,无法找到游戏资料目录,请反馈给 Eclipse Tekitsu !",false);
                        Core.std("错误信息: " + fw.getDefaultDirectory().getPath(),false);
                        return null;
                    }
                }
            }
            Core.std("枚举成功!",false);
        }
        return  eveFileDir;
    }

    public static File getEVEChatDirectory(){
        return new File(getEVELogDirectory() ,"Chatlogs");
    }

    public static File getEVEGameLog(){
        return new File(getEVELogDirectory() ,"Gamelogs");
    }



    public static HashSet<String> getAllChannelName(){
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
                        return null;
                    }
                }
            }
            Core.std("枚举成功!",false);
        }
        HashSet<String> set = new HashSet<>();
        for(File f :chatFileFolder.listFiles()){
            String channelName = f.getName().split("_",2)[0];
            set.add(channelName);
        }
        return set;
    }



    public static HashSet<String> getAllCharName(){
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
                        return null;
                    }
                }
            }
            Core.std("枚举成功!",false);
        }
        HashSet<String> set = new HashSet<>();
        for(File f :chatFileFolder.listFiles()){
            String channelName = f.getName().split("_",2)[0];
            try {
                Scanner s = new Scanner(f,"UTF-16LE");
                while(s.hasNextLine()){
                    String line = s.nextLine();
                    if(line.startsWith("  Listener:")){
                        set.add(line.replaceAll("  Listener:        ",""));
                        System.out.println(set);
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        return set;
    }

    public static String getFileOwner(File f) {
        try {
            Scanner s = new Scanner(f, "UTF-16LE");
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.startsWith("  Listener:")) {
                    return line.replaceAll("  Listener:        ", "");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static String getLineDate(String line){
        Pattern pattern = Pattern.compile("\\[ \\d{4}.\\d{2}.\\d{2} \\d{2}:\\d{2}:\\d{2} \\]");
        Matcher matcher = pattern.matcher(line);

        if(matcher.find()){
            return matcher.group();
        }
        return line;
    }

    /**
     *
     * @param line
     * @param line2
     * @return true line before line2
     */
    public static boolean isBefore(String line,String line2){
        DateFormat format = new SimpleDateFormat("[ yyyy.MM.dd hh:mm:ss");
        try {
            Date date = format.parse(line);
            Date b = format.parse(line2);
            return date.before(b);

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param line
     * @param line2
     * @return true line after line2
     */
    public static boolean isAfter(String line,String line2){
        DateFormat format = new SimpleDateFormat("[ yyyy.MM.dd hh:mm:ss");
        try {
            Date date = format.parse(line);
            Date b = format.parse(line2);
            return date.after(b);

        }catch(Exception e){

        }
        return false;
    }

}
