package sample2.entities;

import lombok.Getter;
import me.hysong.sgl.SGLEntity;
import me.hysong.sgl.SGLSettings;
import me.hysong.sgl.panels.SGLPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.UUID;

public class KGKeyboardHitBlock extends SGLEntity {

    private static final String[] alphabetList = "AS';".split("");
    @Getter private String alphabet;

    public KGKeyboardHitBlock(SGLPanel involvedPanel, String alphabet) {
        super("KeyboardHitBlock_" + UUID.randomUUID(), KGKeyboardHitBlock.class.getName(), new JPanel(), involvedPanel);

        this.getPanel().setSize(50, 50);
        this.getPanel().setLocation(0, 0);
        this.getPanel().setLayout(null);
        this.getPanel().setOpaque(true);
        this.getPanel().setBackground(Color.BLACK);

        // Select random alphabet from assigned list
        this.alphabet = alphabet == null ? alphabetList[(int) (Math.random() * alphabetList.length)] : alphabet;

        // Create label
        JLabel label = new JLabel(this.alphabet);
        label.setSize(this.getWidth(), this.getHeight());
        label.setLocation(0, 0);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);

        this.getPanel().add(label);
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    public void move1BlockDown() {
        setY(getY() + 50);
    }
}
