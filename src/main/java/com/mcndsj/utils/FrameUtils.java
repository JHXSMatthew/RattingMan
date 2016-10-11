package com.mcndsj.utils;

import java.awt.*;

/**
 * Created by Matthew on 2016/5/19.
 */
public class FrameUtils {
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.repaint();
    }

}
