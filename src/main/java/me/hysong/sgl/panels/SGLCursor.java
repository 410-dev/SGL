package me.hysong.sgl.panels;

import me.hysong.sgl.SGLEntity;

import javax.swing.*;

public class SGLCursor extends SGLEntity {

    public static final String ENTITY_CODE = "entity.sgl.mouseTrigger";

    public static JPanel cursorPanel = new JPanel();

    public SGLCursor(SGLPanel involvedPanel) {
        super("SGL Cursor", ENTITY_CODE, cursorPanel, involvedPanel);
        cursorPanel.setSize(1, 1);
        cursorPanel.setOpaque(true);
        cursorPanel.setBackground(java.awt.Color.BLACK);
        cursorPanel.setVisible(true);
    }

    @Override
    public boolean isAlive() {
        return false;
    }
}
