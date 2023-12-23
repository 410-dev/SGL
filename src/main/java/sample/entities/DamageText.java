package sample.entities;

import me.hysong.sgl.SGLEntity;
import me.hysong.sgl.panels.SGLPanel;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class DamageText extends SGLEntity {

    public static final String entityCode = "system.entity.damagetext";

    public DamageText(String amount, Color color, SGLPanel involvedPanel) {
        super("dmgtxt." + UUID.randomUUID(), entityCode, new JPanel(), involvedPanel);


        JLabel text = new JLabel(amount);
        text.setForeground(color);
        text.setFont(new Font("Arial", Font.BOLD, 20));
        text.setSize(amount.length() * 20, 50);
        this.getPanel().add(text);
        this.getPanel().setOpaque(false);
        this.getPanel().setBackground(Color.BLUE);
        this.getPanel().setForeground(Color.BLUE);
        this.getPanel().setSize(amount.length() * 20, 50);
        this.getPanel().setVisible(true);

        Thread animate = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                try {
                    Thread.sleep(10);
                    this.getPanel().setLocation(this.getPanel().getX(), this.getPanel().getY() - 1);
                } catch (InterruptedException ignored) {
                }
            }
            kill();
        });
        animate.start();
    }

    @Override
    public boolean isAlive() {
        return true;
    }

}
