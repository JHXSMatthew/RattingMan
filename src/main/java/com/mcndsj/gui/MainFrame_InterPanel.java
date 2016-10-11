package com.mcndsj.gui;

import com.mcndsj.observers.IntelController;
import com.mcndsj.Core;
import com.mcndsj.graph.Graph;
import com.mcndsj.utils.ConfigUtils;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Matthew on 21/06/2016.
 */
public class MainFrame_InterPanel extends JPanel{


    private JComboBox box;
    private JTextField location;
    private JTextField range;

    public MainFrame_InterPanel(final MainFrame frame){
        setLayout(new GridLayout());

        add(new JLabel("频道文件:",JLabel.CENTER));


        box = new JComboBox();
        for(String s : frame.getBoxItems())
            box.addItem(s);
        try {
            box.setSelectedItem(ConfigUtils.getLastSection());
        }catch(Exception e){

        }
        add(box);

        add(new JLabel("当前星系:",JLabel.CENTER));
        location = new JTextField();
        location.setText(ConfigUtils.getSystem());
        add(location);


        add(new JLabel("预警范围:",JLabel.CENTER));
        range = new JTextField();
        range.setText(ConfigUtils.getRange());
        ((AbstractDocument)range.getDocument()).setDocumentFilter(
                new Filter());
        add(range);

        Button b = new Button("Confirm");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String section = (String) box.getSelectedItem();
                String currentLoc = location.getText();
                if(currentLoc == null){
                    Core.std("请输入当前所在星系,区分大小写符号全角半角.",false);
                    return;
                }
                try {
                    int r = Integer.parseInt(range.getText());
                    try {
                        Graph ga = frame.getGraph(section);
                        try{
                            if(!ga.isInGraph(currentLoc)){
                                Core.std("当前所在星系不在配置文件内,请检查配置文件!.",false);
                                return;
                            };
                            ConfigUtils.saveConfig(ga.getName(),currentLoc,String.valueOf(r));
                            IntelController.get().watchStart(ga,currentLoc,r);
                        }catch(Exception eaae){

                        }
                    }catch(Exception eeee){
                        Core.std("所选配置文件不存在,错误代码201",false);
                        return;
                    }
                }catch(Exception ee){
                    Core.std("距离" + range.getText() + "不是一个有效数字,请输入数字!",false);
                    return;
                }
            }
        });
        add(b);
    }



}
