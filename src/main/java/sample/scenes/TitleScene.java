package sample.scenes;

import me.hysong.sgl.SGLWindow;
import me.hysong.sgl.panels.SGLAreaClickablePanel;
import me.hysong.sgl.panels.SGLPlainPanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TitleScene extends SGLPlainPanel {
    public TitleScene(SGLWindow window) {
        super(window);

        JLabel title = new JLabel("Sample Game");
        JLabel subtitle = new JLabel("Example for SGL (Swing Game Library)");
        JButton startButton = new JButton("Start");
        JButton exitButton = new JButton("Exit");

        title.setBounds(0, 0, 100, 100);
        title.setLocation(100, 100);
        title.setVisible(true);

        subtitle.setBounds(0, 0, 100, 100);
        subtitle.setLocation(100, 150);
        subtitle.setVisible(true);

        startButton.setBounds(0, 0, 100, 100);
        startButton.setLocation(100, 200);
        startButton.setVisible(true);

        exitButton.setBounds(0, 0, 100, 100);
        exitButton.setLocation(100, 250);
        exitButton.setVisible(true);

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                window.load(InGameScene.class);
            }
        });

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                window.getFrame().dispose();
            }
        });

        getPanel().add(title);
        getPanel().add(subtitle);
        getPanel().add(startButton);
        getPanel().add(exitButton);
    }
}
