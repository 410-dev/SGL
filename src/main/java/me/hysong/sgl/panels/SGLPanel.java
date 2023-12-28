package me.hysong.sgl.panels;

import me.hysong.sgl.SGLEntity;
import me.hysong.sgl.SGLWindow;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

public interface SGLPanel {
    JPanel getPanel();
    SGLWindow getWindow();

    default void addKeyboardEvent(KeyAdapter keyAdapter) {
        getPanel().addKeyListener(keyAdapter);
    }

    default void addMouseEvent(MouseAdapter mouseAdapter) {
        getPanel().addMouseListener(mouseAdapter);
    }

    default void spawnRelatively(SGLEntity self, SGLEntity toSpawn, float xPercent, float yPercent) {
        spawnRelatively(self, toSpawn, (int) (getPanel().getWidth() * xPercent), (int) (getPanel().getHeight() * yPercent));
    }

    default void spawnRelatively(SGLEntity self, SGLEntity toSpawn, int x, int y) {
        // Anchor point is top left, but we want to spawn relative to the center

        // Get self's center
        int selfCenterX = self.getX() + self.getWidth() / 2;
        int selfCenterY = self.getY() + self.getHeight() / 2;

        // Get new position based on self center
        int newX = selfCenterX + x - toSpawn.getWidth() / 2;
        int newY = selfCenterY + y - toSpawn.getHeight() / 2;

        System.out.println("Self (x,y)=" + self.getX() + "," + self.getY() + " toSpawn (x,y)->(nx,ny)=" + x + "," + y + "->" + newX + "," + newY + " where width=" + self.getWidth() + " height=" + self.getHeight());

        spawnAbsolutely(self, toSpawn, newX, newY);

//        spawnAbsolutely(self, toSpawn, self.getX() + x, self.getY() + y);

    }

    default void spawnAbsolutely(SGLEntity self, SGLEntity toSpawn, int x, int y) {
        System.out.println("Self (x,y)=" + self.getX() + "," + self.getY() + " toSpawn (x,y)=" + x + "," + y);
        toSpawn.setX(x);
        toSpawn.setY(y);
        System.out.println("Entity <" + self.getEntityName() + "> spawned " + toSpawn.getEntityName() + " to " + x + ", " + y);
        getWindow().addEntity(toSpawn.getEntityName(), toSpawn);
    }

    default void spawn(SGLEntity toSpawn, int x, int y) {
        toSpawn.setX(x);
        toSpawn.setY(y);
        System.out.println("Entity <" + toSpawn.getEntityName() + "> spawned to " + x + ", " + y);
        getWindow().addEntity(toSpawn.getEntityName(), toSpawn);
    }
}
