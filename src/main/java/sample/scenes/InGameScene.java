package sample.scenes;

import me.hysong.libhyextended.swingx.FastImage;
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

    public InGameScene(SGLWindow window) {
        super(window, 20, 20, true, new MouseAdapter[]{});
//        try {
////            JPanel c = new FastImage("cursor.png", 20, 20).getPanel();
////            c.setOpaque(false);
////            c.setSize(20, 20);
////            setCursor(c);
//            setCursor("cursor.png");
//        }catch (Exception e) {
//            e.printStackTrace();
//            setCursor();
//        }

        for(int i = 0; i < 5; i++) {
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
    }
}
