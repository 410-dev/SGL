package sample.entities;

import me.hysong.sgl.SGLEntity;
import me.hysong.sgl.interfaces.SGLEntityInterface;
import me.hysong.sgl.panels.SGLPanel;

import javax.swing.*;
import java.awt.*;

public class MobHealthBar  extends SGLEntity implements SGLEntityInterface {

    public static final String entityCode = "system.entity.mobHealthBar";

    private int maxHealth;
    private int currentHealth;
    private JPanel healthBar = new JPanel();

    public MobHealthBar(String entityName, int maxHealth, int currentHealth, int width, int height, Color remainingColor, SGLPanel involvedPanel) {
        super(entityName + ".healthbar", entityCode, new JPanel(), involvedPanel);

        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;

        getPanel().setBackground(Color.GRAY);
        getPanel().setForeground(Color.GRAY);
        getPanel().setSize(width, height);
        getPanel().setVisible(true);
        getPanel().setOpaque(true);
        getPanel().setLayout(null);

        healthBar.setSize(width, height);
        healthBar.setBackground(remainingColor);
        healthBar.setForeground(remainingColor);
        healthBar.setVisible(true);
        healthBar.setOpaque(true);
        healthBar.setLocation(0, 0);
        healthBar.setLayout(null);
        this.getPanel().add(healthBar);
    }

    public void updateHealthLvl(int currentHealth) {
        this.currentHealth = currentHealth;
        healthBar.setSize((int) ((double) currentHealth / (double) maxHealth * getPanel().getWidth()), getPanel().getHeight());
    }

    @Override
    public boolean isAlive() {
        return true;
    }
}
