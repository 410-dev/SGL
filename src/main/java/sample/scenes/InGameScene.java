package sample.scenes;

import sample.entities.Slime;
import lombok.Getter;
import me.hysong.sgl.SGLEntity;
import me.hysong.sgl.SGLWindow;
import me.hysong.sgl.panels.SGLAreaClickablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

@Getter
public class InGameScene extends SGLAreaClickablePanel {

    private JPanel cursor;

    public InGameScene(SGLWindow window) {
        super(window, 10, 10, false, new MouseAdapter[]{});

        for(int i = 0; i < 1; i++) {
            Slime s = new Slime(this);
            s.setX(new Random().nextInt(500));
            s.setY(new Random().nextInt(500));
            window.addEntity(s.getEntityName(), s);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ArrayList<SGLEntity> overlappingEntities = window.getOverlappingEntities(getClickedAreaEntity());

                Collections.reverse(overlappingEntities);

                for (SGLEntity entity : overlappingEntities) {
//                    System.out.println("Entity <" + entity.getEntityCode() + ">: " + entity.getOverlapPercentage(getClickedAreaEntity()) + ", RD: " + entity.getRelativeDeviation(getClickedAreaEntity()));
                    entity.runOverlappingEvent(getClickedAreaEntity());
                    break;
                }

                // From here, run asynchronously:
                new Thread(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    boolean found = false;
                    for (SGLEntity entity : window.getEntities().values()) {
                        if (entity instanceof Slime) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(null, "You win!");
                        System.exit(0);
                    }
                }).start();
            }
        });

        // Hovering Cursor, black outlined box with transparent fill
        cursor = new JPanel();
        cursor.setSize(10, 10);
        cursor.setOpaque(true);
        cursor.setLayout(null);
        cursor.setBackground(new java.awt.Color(0, 0, 0, 255));
        cursor.setForeground(new java.awt.Color(0, 0, 0, 255));
        cursor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cursor.setVisible(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                cursor.setLocation(e.getX() - 5, e.getY() - 5);
                cursor.setVisible(true);
                window.getContentPane().repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                cursor.setLocation(e.getX() - 5, e.getY() - 5);
                window.getContentPane().repaint();
            }
        });
    }
}
