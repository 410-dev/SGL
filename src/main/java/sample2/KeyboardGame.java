package sample2;

import me.hysong.libhyextended.environment.Subsystem;
import me.hysong.libhyextended.environment.SubsystemEnvironment;
import me.hysong.sgl.SGLSettings;
import sample2.scenes.KGTitle;

import javax.swing.*;
import java.io.IOException;

public class KeyboardGame {

    public static int bestScore = 0;

    public static void main(String[] args) {

        SubsystemEnvironment env = new SubsystemEnvironment("keyboardgame", null);

        try {
            if (env.isFile("bestScore")) {
                bestScore = Integer.parseInt(env.readString("bestScore").strip());
            } else {
                env.writeString("bestScore", "0");
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Save file corrupted or failed to load. Please delete the save file and restart the game. The save file is: " + env.realpath("bestScore"), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        SGLSettings.verboseLevel = SGLSettings.LogLevel.ERROR;

        KeyboardGameWindow window = new KeyboardGameWindow();

        window.load(KGTitle.class);
        window.start();
    }
}