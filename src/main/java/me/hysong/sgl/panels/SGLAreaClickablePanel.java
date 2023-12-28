package me.hysong.sgl.panels;

import lombok.Getter;
import me.hysong.sgl.SGLEntity;
import me.hysong.sgl.SGLWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

@Getter
public class SGLAreaClickablePanel implements SGLPanel {

    private JPanel panel;
    private JPanel clickedArea;
    private JPanel cursor;
    private SGLEntity clickedAreaEntity;

    private boolean enableHitBoxDisplay;
    private int clickAreaWidth = 10;
    private int clickAreaHeight = 10;

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

    public void setCursor() {
        cursor = new JPanel();
        cursor.setSize(clickAreaWidth, clickAreaHeight);
        cursor.setOpaque(true);
        cursor.setLayout(null);
        cursor.setBackground(new java.awt.Color(0, 0, 0, 0));
        cursor.setForeground(new java.awt.Color(0, 0, 0, 0));
        cursor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cursor.setVisible(true);
        setCursor(cursor);
    }

    public void setCursor(JPanel cursorPanel) {
        cursor = cursorPanel;
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                cursor.setLocation(e.getX() - cursorPanel.getWidth()/2, e.getY() - cursorPanel.getHeight()/2);
                cursor.setVisible(true);
                window.getContentPane().repaint();
            }
        });
        panel.add(cursor);
    }

    public void setCursor(String filePath) {
        try {
            JPanel cursorPanel = new JPanel();
            cursorPanel.setSize(clickAreaWidth, clickAreaHeight);
            cursorPanel.setOpaque(false);

            // Load image from file to put on cursorPanel
            // The size of the image should be the same as the size of the cursorPanel
            BufferedImage cursorImage = ImageIO.read(new File(filePath));
            BufferedImage resizedImage = new BufferedImage(cursorPanel.getWidth(), cursorPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = resizedImage.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2D.drawImage(cursorImage, 0, 0, cursorPanel.getWidth(), cursorPanel.getHeight(), null);
            graphics2D.dispose();

            // Set the image as the background of the cursorPanel
            cursorPanel.setBackground(new Color(0, 0, 0, 0));
            cursorPanel.setForeground(new Color(0, 0, 0, 0));
            cursorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cursorPanel.setVisible(true);

            JLabel cursorLabel = new JLabel(new ImageIcon(resizedImage));
            cursorLabel.setSize(cursorPanel.getWidth(), cursorPanel.getHeight());
            cursorLabel.setOpaque(false);
            cursorLabel.setVisible(true);
            cursorPanel.add(cursorLabel);

            setCursor(cursorPanel);
        }catch (Exception e) {

        }
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
