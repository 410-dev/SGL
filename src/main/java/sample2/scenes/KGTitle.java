package sample2.scenes;

import me.hysong.sgl.SGLWindow;
import me.hysong.sgl.panels.SGLPlainPanel;
import sample2.KeyboardGame;

import javax.swing.*;
import java.awt.*;

public class KGTitle extends SGLPlainPanel {

    public KGTitle(SGLWindow window) {
        super(window);
        JLabel title = new JLabel("Keyboard Game");
        JLabel bestScore = new JLabel("Best Score: " + KeyboardGame.bestScore);
        JButton start = new JButton("Start");
        JButton exit = new JButton("Exit");

        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setBounds(0, 0, 450, 70);
        title.setLocation((window.getFrame().getWidth() / 2) - (title.getWidth() / 2), title.getHeight()*2);
        title.setHorizontalAlignment(JLabel.CENTER);

        bestScore.setFont(new Font("Arial", Font.BOLD, 20));
        bestScore.setBounds(0, 0, 450, 70);
        bestScore.setLocation((window.getFrame().getWidth() / 2) - (bestScore.getWidth() / 2), title.getHeight()*3);
        bestScore.setHorizontalAlignment(JLabel.CENTER);

        start.setBounds(0, 0, 100, 50);
        start.setLocation((window.getFrame().getWidth() / 2) - (start.getWidth() / 2), (window.getFrame().getHeight() / 2) - (start.getHeight() / 2) + 100);
        start.addActionListener(e -> {
            window.load(KGPlayable1.class);
        });

        exit.setBounds(0, 0, 100, 50);
        exit.setLocation((window.getFrame().getWidth() / 2) - (exit.getWidth() / 2), (window.getFrame().getHeight() / 2) - (exit.getHeight() / 2) + 170);
        exit.addActionListener(e -> {
            System.exit(0);
        });

        getPanel().add(title);
        getPanel().add(bestScore);
        getPanel().add(start);
        getPanel().add(exit);
    }
}
