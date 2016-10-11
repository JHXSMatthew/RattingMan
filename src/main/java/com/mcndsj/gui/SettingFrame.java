package com.mcndsj.gui;

import com.mcndsj.utils.FrameUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Matthew on 15/08/2016.
 */
public class SettingFrame extends JFrame {

    private MainFrame main;
    private SettingFrame_GeneralPanel generalPanel;
    private SettingFrame_DetailedPanel detailedPanel;

    public SettingFrame(MainFrame frame){
        this.main = frame;
        main.setEnabled(false);
        setLayout(new BorderLayout());
        generalPanel = new SettingFrame_GeneralPanel();
        detailedPanel = new SettingFrame_DetailedPanel();
        add(generalPanel,BorderLayout.WEST);
        add(detailedPanel,BorderLayout.EAST);
        pack();

        FrameUtils.centreWindow(this);
    }

    public void onDispose(){
        main.setEnabled(true);
    }




}
