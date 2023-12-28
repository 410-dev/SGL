package sample2.scenes;

import me.hysong.libhyextended.environment.SubsystemEnvironment;
import me.hysong.sgl.SGLEntity;
import me.hysong.sgl.SGLSettings;
import me.hysong.sgl.SGLWindow;
import me.hysong.sgl.interfaces.SGLEntityRunnable;
import me.hysong.sgl.panels.SGLPanel;
import me.hysong.sgl.panels.SGLPlainPanel;
import sample2.KeyboardGame;
import sample2.entities.KGKeyboardHitBlock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import static me.hysong.libhyextended.utils.Run.async;

public class KGPlayable1 extends SGLPlainPanel {

    private final ArrayList<KGKeyboardHitBlock> blocks = new ArrayList<>();

    private int currentSession = 0;
    private final int maxSessions = 10;

    private int score = 0;
    private long lastKill = 0;

    private final String scoreLabelStr = "Score: %score%";
    private final JLabel scoreLabel = new JLabel();

    private boolean sessionOver = false;

    private void summons(SGLWindow window) {
        int y = 100;
        int availableXes = 7;
        int beginRegionX = window.getFrame().getWidth() / 2 - (availableXes * 30) / 2;
        for (int j = 0; j < new Random().nextInt(4, 10); j++) {
            try {Thread.sleep(300);} catch (InterruptedException e) {throw new RuntimeException(e);}
            if (sessionOver) return;
            KGKeyboardHitBlock block = new KGKeyboardHitBlock(this, null);
            block.addKeyboardEvent(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    System.out.println(e.getKeyChar());
                    if (e.getKeyChar() == block.getAlphabet().toLowerCase().charAt(0) || e.getKeyChar() == block.getAlphabet().toUpperCase().charAt(0)) {
                        SGLSettings.log("Key pressed: " + e.getKeyChar(), SGLSettings.LogLevel.INFO);
                        block.kill();
                        int additionalScore = (int) (1000 / (System.currentTimeMillis() - lastKill));
                        score += 10 + additionalScore;
                        lastKill = System.currentTimeMillis();
                    }else{
                        score -= 15;
                    }
                }
            });
            block.onKill(new SGLEntityRunnable() {
                @Override
                public void run(SGLEntity entity, SGLPanel panel) {
                    SGLSettings.log("Current session: " + currentSession, SGLSettings.LogLevel.INFO);
                    SGLSettings.log("Current blocks remaining: " + blocks.size(), SGLSettings.LogLevel.INFO);
                    blocks.remove(0);
                    if (currentSession < maxSessions && blocks.isEmpty()) {
                        currentSession++;
                        SGLSettings.log("Current session updated to: " + currentSession, SGLSettings.LogLevel.INFO);
                        async(() -> summons(window));
                        SGLSettings.log("Summoned new blocks", SGLSettings.LogLevel.INFO);
                        return;
                    } else if (currentSession == maxSessions && blocks.isEmpty()) {
                        sessionOver = true;

                        if (score > KeyboardGame.bestScore) {
                            KeyboardGame.bestScore = score;
                            JOptionPane.showMessageDialog(null, "You win! New best score: " + score);
                            SubsystemEnvironment env = new SubsystemEnvironment("keyboardgame", null);
                            try{
                                env.writeString("bestScore", String.valueOf(score));
                            }catch (Exception e){
                                JOptionPane.showMessageDialog(null, "Failed to save best score: " + e.getMessage());
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "You win!");
                        }
                        window.load(KGTitle.class);
                        return;
                    }
                    blocks.get(0).getPanel().setFocusable(true);
                    blocks.get(0).getPanel().requestFocus();
                    blocks.get(0).getPanel().setBackground(Color.BLUE);

                    scoreLabel.setText(scoreLabelStr.replace("%score%", String.valueOf(score)));
                }

                @Override
                public void runPostAsync(SGLEntity entity, SGLPanel panel) {
                    run(entity, panel);
                }
            });
            blocks.add(block);
            int thisx = new Random().nextInt(availableXes) * 30 + beginRegionX;
            spawn(block, thisx, y);
            blocks.get(0).getPanel().setFocusable(true);
            blocks.get(0).getPanel().requestFocus();
            blocks.get(0).getPanel().setBackground(Color.BLUE);
            y += 60;
            window.getContentPane().repaint();
        }
        SGLSettings.log("Summoned " + blocks.size() + " blocks", SGLSettings.LogLevel.INFO);
        lastKill = System.currentTimeMillis();
    }

    public KGPlayable1(SGLWindow window) {
        super(window);

        // Set jlabel
        scoreLabel.setText(scoreLabelStr.replace("%score%", String.valueOf(score)));
        scoreLabel.setSize(200, 50);
        scoreLabel.setLocation(0, 0);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setVerticalAlignment(JLabel.CENTER);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBackground(Color.BLACK);
        scoreLabel.setOpaque(true);
        scoreLabel.setVisible(true);
        this.getPanel().add(scoreLabel);

        async(() -> {
            summons(window);

            // Get the first block and highlight it
            blocks.get(0).getPanel().setFocusable(true);
            blocks.get(0).getPanel().requestFocus();
            blocks.get(0).getPanel().setBackground(Color.BLUE);
        });
    }
}
