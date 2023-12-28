package me.hysong.sgl.interfaces;

import me.hysong.sgl.SGLEntity;
import me.hysong.sgl.panels.SGLPanel;

@FunctionalInterface
public interface SGLEntityRunnable {
    void run(SGLEntity entity, SGLPanel panel);
    default void runPrior(SGLEntity entity, SGLPanel panel) {}
    default void runPost(SGLEntity entity, SGLPanel panel) {}
    default void runPriorAsync(SGLEntity entity, SGLPanel panel) {}
    default void runPostAsync(SGLEntity entity, SGLPanel panel) {}
}
