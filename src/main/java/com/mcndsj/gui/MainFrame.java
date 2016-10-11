package com.mcndsj.gui;

import com.mcndsj.Core;
import com.mcndsj.graph.Graph;
import com.mcndsj.graph.GraphManager;
import com.mcndsj.utils.FileUtils;
import com.mcndsj.utils.FrameUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Matthew on 21/06/2016.
 */
public class MainFrame extends JFrame{

    private GraphManager gm = null;
    private MainFrame_InfoPanel info;
    private TrayIcon tray;

    public MainFrame(GraphManager gm){
        this.gm = gm;
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("About");
        menuBar.add(menu);
        setMenuBar(menuBar);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        String imagePath = "/icon.png";
        InputStream imgStream = MainFrame.class.getResourceAsStream(imagePath );
        BufferedImage myImg = null;
        try {
            myImg = ImageIO.read(imgStream);
            setIconImage(myImg);

        } catch (IOException e) {
            e.printStackTrace();
        }



        setTitle("预警频道放大器 by 达尔文");
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                int answer = JOptionPane.showConfirmDialog(get(),
                        "确认退出? 确认将退出,否将最小化到托盘.", "预警频道放大器 by 达尔文",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if ( answer ==  JOptionPane.YES_OPTION){
                    System.exit(0);
                }else if(answer == JOptionPane.NO_OPTION){
                    setVisible(false);
                    tray.displayMessage("预警频道放大器","已缩小至托盘,双击托盘图标重新打开窗口.", TrayIcon.MessageType.INFO);
                }
            }
        });

        JPanel main = new JPanel(new BorderLayout());
        main.add(new MainFrame_InterPanel(this), BorderLayout.NORTH);
        info = new MainFrame_InfoPanel(this);
        main.add(info, BorderLayout.CENTER);
        add(main);


        pack();
        FrameUtils.centreWindow(this);
        setVisible(true);
        setResizable(false);

        Core.std("欢迎使用预警频道放大器-"+ Core.version +" Beta by 达尔文",true);
        Core.std("本程序仅供测试使用,作者本人不承担一切后果..",false);
        setUpTray(myImg,"刷怪助手" + Core.version);
    }

    private void setUpTray(BufferedImage myImg,String title){
        PopupMenu popup = new PopupMenu();
        MenuItem defaultItem = new MenuItem("退出");

        popup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("退出")){
                    if (JOptionPane.showConfirmDialog(get(),
                            "确认退出?", "预警频道放大器 by 达尔文",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                        System.exit(0);
                    }
                }
            }
        });

        popup.add(defaultItem);


        tray  = new TrayIcon(myImg,title ,popup);
        tray.setImageAutoSize(true);

        tray.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    if (isShowing()) {
                        setVisible(false);
                    } else {
                        setVisible(true);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(this.tray);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }



    public MainFrame get(){
        return this;
    }

    public String[] getBoxItems(){
        return gm.getAllGraphs();
    }

    public Graph getGraph(String name) throws Exception {
        return gm.getGraph(name);
    }

    public void stdout(String s,boolean pop,Color color){
        if(pop){
            tray.displayMessage("信息", s, TrayIcon.MessageType.INFO);
        }
        info.println(s,color);
    }

}
