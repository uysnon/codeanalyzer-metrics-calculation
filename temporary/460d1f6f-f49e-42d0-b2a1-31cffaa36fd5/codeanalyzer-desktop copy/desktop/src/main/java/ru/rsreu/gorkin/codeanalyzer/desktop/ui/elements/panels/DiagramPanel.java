package ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.panels;

import ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.graphics.Arrow;
import ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.utils.PathFinder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class DiagramPanel extends JPanel {
    Graphics2D graphics;
    List<JButton> classElements;
    Component startComponent;
    Color arrowColor = new Color(41, 50, 65);

    public DiagramPanel(java.util.List<JButton> classElements, GridLayout gridLayout, Component startComponent) {
        super(gridLayout);
        setBorder(new EmptyBorder(30, 30, 30, 30));
        this.startComponent = startComponent;
        this.classElements = classElements;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphics = (Graphics2D) g;
        List<Component> barriers = new ArrayList<>();
        barriers.addAll(classElements);
//        barriers.add(startComponent);

        for (JButton button : classElements) {
//            drawArrow(
//                    0,
//                    0,
//                    button.getX(),
//                    button.getY() + (double) button.getHeight() / 2);

            List<Point2D> points = new PathFinder().findPath(
                    new Point2D.Double(startComponent.getX(), startComponent.getY() + startComponent.getHeight() / 2.0),
//                    classes.stream().filter(jButton -> jButton != button).toArray(JComponent[]::new),
                    barriers.toArray(new JComponent[0]),
                    button
            );

            Color oldColor = g.getColor();
            g.setColor(arrowColor);
            for (int i = 0; i < points.size() - 1; i++) {
                g.drawLine((int) points.get(i).getX(),
                        (int) points.get(i).getY(),
                        (int) points.get(i + 1).getX(),
                        (int) points.get(i + 1).getY());
                if (i == points.size() - 2) {
                    drawArrow(
                            (int) points.get(i).getX(),
                            (int) points.get(i).getY(),
                            (int) points.get(i + 1).getX(),
                            (int) points.get(i + 1).getY());
                }
            }
            g.setColor(oldColor);
        }


//        drawArrow(50, 50, 100, 100);
//        drawArrow(50, 50, 50, 100);

    }

    private void drawArrow(double x1, double y1, double x2, double y2) {
        if (graphics != null) {
            Arrow.draw(graphics,
                    new Point2D.Double(x1, y1),
                    new Point2D.Double(x2, y2),
                    new BasicStroke(1),
                    new BasicStroke(1),
                    10,
                    arrowColor);
        }
    }
}
