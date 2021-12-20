package ru.rsreu.gorkin.codeanalyzer.desktop.screenshots;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ScreenshotMaker {
    public void makeScreenshot(Component comp, File file) {
        if (comp.isVisible()) {
            try {
                System.out.println(comp);
                Robot robot = new Robot();
                Rectangle bounds = new Rectangle(comp.getLocationOnScreen(), comp.getSize());
                bounds.x -= 1;
                bounds.y -= 1;
                bounds.width += 2;
                bounds.height += 2;
                BufferedImage snapShot = robot.createScreenCapture(bounds);
                ImageIO.write(snapShot, "png", file);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
