package sample.entities;

import me.hysong.sgl.SGLEntity;
import me.hysong.sgl.interfaces.SGLEntityInterface;
import me.hysong.sgl.panels.SGLCursor;
import me.hysong.sgl.panels.SGLPanel;
import sample.Main;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.UUID;

public class Slime extends SGLEntity implements SGLEntityInterface {

    public static final String entityCode = "entity.slime.";

    private Thread animate = null;
    private int health = 4000;
    private MobHealthBar healthBar;

    private int healthBarOffset = 15;

    public Slime(String uniqueCode, SGLPanel involvedPanel) {
        super("Slime_" + uniqueCode, entityCode + uniqueCode, new JPanel(), involvedPanel);
        this.getPanel().setSize(30, 30);
        this.getPanel().setOpaque(true);
        this.getPanel().setBackground(java.awt.Color.GREEN);
        this.getPanel().setForeground(java.awt.Color.GREEN);
        this.getPanel().setVisible(true);

        healthBar = new MobHealthBar(getEntityName(), health, health, 30, 10, Color.BLUE, involvedPanel);

        getInvolvedPanel().spawnRelatively(this, healthBar, 0, -healthBarOffset);

        this.addOverlappingEvent(SGLCursor.ENTITY_CODE, (entity, panel) -> {

            // Get base score by click accuracy
            int score = (int) (Main.player.getCurrentAttack() * Main.player.getLevel() * (double) (1 / entity.getRelativeDeviation(this)));

            // Based on critical chance, amplify score
            boolean crit = Main.player.getCriticalChance() > new Random().nextInt(0, 100);
            if (crit) {
                System.out.println("CRIT!");
                double newScore = score * (1 + (double) Main.player.getCriticalAmplifier() /100);
                System.out.println("Score updated from " + score + " to " + newScore);
                score = (int) newScore;
            }

            // Update health
            health -= score;
            healthBar.updateHealthLvl(health);

            // Spawn damage text
            if (!crit)
                getInvolvedPanel().spawnRelatively(this, new DamageText(String.valueOf(score), Color.RED, getInvolvedPanel()), 0, -20);
            else
                getInvolvedPanel().spawnRelatively(this, new DamageText("CRITICAL " + score, Color.MAGENTA, getInvolvedPanel()), 0, -20);

            // Kill if health is below 0
            if (health < 0) {
                animate.interrupt();
                System.out.println("Slime_" + uniqueCode + " died!");
                this.getPanel().setBackground(java.awt.Color.BLACK);

                // Kill both the slime and its health bar
                kill();
                healthBar.kill();
            }
        });

        animate = new Thread(() -> {
            while(isAlive()) {
                try {
                    Thread.sleep(new Random().nextInt(1000));
                    this.getPanel().setBackground(java.awt.Color.GREEN);
                    this.getPanel().setForeground(java.awt.Color.GREEN);
                    Thread.sleep(new Random().nextInt(1000));
                    this.getPanel().setBackground(java.awt.Color.RED);
                    this.getPanel().setForeground(java.awt.Color.RED);
                } catch (InterruptedException ignored) {
                }
            }
        });
        animate.start();
    }

    @Override
    public void setX(int x) {
        super.setX(x);
        healthBar.setX(x);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        healthBar.setY(y - healthBarOffset);
    }

    public Slime(SGLPanel involvedPanel) {
        this(UUID.randomUUID().toString(), involvedPanel);
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }
}
