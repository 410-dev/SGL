package sample;

import sample.scenes.InGameScene;
import me.hysong.sgl.SGLWindow;
import sample.scenes.TitleScene;

public class Main {

    public static Player player = new Player();

    public static void main(String[] args) {

        player.setCriticalAmplifier(85);
        player.setCriticalChance(50);

        SGLWindow window = new SGLWindow();

        window.load(TitleScene.class);

        window.start();


    }
}
