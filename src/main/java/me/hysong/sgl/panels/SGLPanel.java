package me.hysong.sgl.panels;

import me.hysong.sgl.SGLEntity;
import me.hysong.sgl.SGLWindow;

import javax.swing.*;

public interface SGLPanel {
    JPanel getPanel();
    SGLWindow getWindow();

    default void spawnRelatively(SGLEntity self, SGLEntity toSpawn, float xPercent, float yPercent) {
        spawnRelatively(self, toSpawn, (int) (getPanel().getWidth() * xPercent), (int) (getPanel().getHeight() * yPercent));
    }

    default void spawnRelatively(SGLEntity self, SGLEntity toSpawn, int x, int y) {
        spawnAbsolutely(self, toSpawn, self.getX() + x, self.getY() + y);

    }

    default void spawnAbsolutely(SGLEntity self, SGLEntity toSpawn, int x, int y) {
        System.out.println("Self (x,y)=" + self.getX() + "," + self.getY() + " toSpawn (x,y)=" + x + "," + y);
        toSpawn.setX(x);
        toSpawn.setY(y);
        System.out.println("Entity <" + self.getEntityName() + "> spawned " + toSpawn.getEntityName() + " to " + x + ", " + y);
        getWindow().addEntity(toSpawn.getEntityName(), toSpawn);
    }
}
