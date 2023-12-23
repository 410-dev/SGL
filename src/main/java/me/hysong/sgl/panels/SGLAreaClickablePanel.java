package me.hysong.sgl.panels;

import lombok.Getter;
import me.hysong.sgl.SGLEntity;
import me.hysong.sgl.SGLWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

@Getter
public class SGLAreaClickablePanel implements SGLPanel {

    private JPanel panel;
    private JPanel clickedArea;
    private SGLEntity clickedAreaEntity;

    private boolean enableHitBoxDisplay;
    private int clickAreaWidth;
    private int clickAreaHeight;

    private SGLWindow window;


    public SGLAreaClickablePanel(SGLWindow window, int clickAreaWidth, int clickAreaHeight, boolean enableHitBoxDisplay, MouseAdapter[] mouseAdapters) {
        super();

        this.window = window;

        this.enableHitBoxDisplay = enableHitBoxDisplay;
        this.clickAreaWidth = clickAreaWidth;
        this.clickAreaHeight = clickAreaHeight;

        this.panel = new JPanel();
        this.panel.setSize(window.getFrame().getSize());
        this.panel.setLayout(null);
        window.setPanel(this);

        clickedArea = new JPanel();
        setClickAreaSize(enableHitBoxDisplay, clickAreaWidth, clickAreaHeight);

        setMouseListeners(mouseAdapters);

        this.panel.add(clickedArea);
    }

    public void setClickAreaSize(boolean enableHitBoxDisplay, int clickAreaWidth, int clickAreaHeight) {
        clickedArea.setOpaque(true);
        this.enableHitBoxDisplay = enableHitBoxDisplay;
        if (enableHitBoxDisplay) {
            clickedArea.setBackground(Color.BLUE);
            clickedArea.setForeground(Color.BLUE);
        } else {
            clickedArea.setBackground(new Color(0, 0, 0, 0));
            clickedArea.setForeground(new Color(0, 0, 0, 0));
        }
        clickedArea.setSize(clickAreaWidth, clickAreaHeight);
        clickedArea.setVisible(false);
        SGLCursor.cursorPanel = clickedArea;
        clickedAreaEntity = new SGLCursor(this);

        this.clickAreaWidth = clickAreaWidth;
        this.clickAreaHeight = clickAreaHeight;
    }

    public void setMouseListeners(MouseAdapter[] mouseAdapters) {
        resetMouseListeners();

        for (MouseAdapter mouseAdapter : mouseAdapters) {
            this.panel.addMouseListener(mouseAdapter);
        }
    }

    public void addMouseListener(MouseAdapter mouseAdapter) {
        this.panel.addMouseListener(mouseAdapter);
    }

    public void addMouseListeners(MouseAdapter[] mouseAdapters) {
        for (MouseAdapter mouseAdapter : mouseAdapters) {
            this.panel.addMouseListener(mouseAdapter);
        }
    }

    public void setEnableHitBoxDisplay() {
        this.panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int x = e.getX() - clickAreaWidth / 2;
                int y = e.getY() - clickAreaHeight / 2;

                clickedAreaEntity.getPanel().setLocation(x, y);

                if (enableHitBoxDisplay) {clickedAreaEntity.getPanel().setVisible(true);}

                ArrayList<SGLEntity> overlappingEntities = window.getOverlappingEntities(clickedAreaEntity);

                Collections.reverse(overlappingEntities);

                for (SGLEntity entity : overlappingEntities) {
                    if (enableHitBoxDisplay) System.out.println("Entity <" + entity.getEntityCode() + ">: " + entity.getOverlapPercentage(clickedAreaEntity.getPanel()) + ", Relative Deviation: " + entity.getRelativeDeviation(clickedAreaEntity.getPanel()));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                clickedArea.setVisible(false);
            }
        });
    }

    public void resetMouseListeners() {
        // Remove all mouse adapters
        for (MouseListener mouseAdapter : this.panel.getMouseListeners()) {
            this.panel.removeMouseListener(mouseAdapter);
        }
        setEnableHitBoxDisplay();
    }
}
