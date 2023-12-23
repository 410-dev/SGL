package me.hysong.sgl.panels;

import lombok.Getter;
import me.hysong.sgl.SGLWindow;

import javax.swing.*;

@Getter
public class SGLPlainPanel implements SGLPanel {
    private SGLWindow window;
    private JPanel panel;
    public static final String ENTITY_CODE = "entity.sgl.mouseTrigger";
    public SGLPlainPanel(SGLWindow window) {
        super();
        this.panel = new JPanel();
        this.panel.setSize(window.getFrame().getSize());
        this.panel.setLayout(null);
        window.setPanel(this);
        this.window = window;
    }
}
