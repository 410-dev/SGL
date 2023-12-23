package me.hysong.sgl.interfaces;

import me.hysong.sgl.SGLEntity;
import me.hysong.sgl.panels.SGLPanel;

@FunctionalInterface
public interface SGLEntityRunnable {
    void run(SGLEntity entity, SGLPanel panel);
}
