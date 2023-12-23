package sample;

import sample.scenes.InGameScene;
import me.hysong.sgl.SGLWindow;
import sample.scenes.TitleScene;

public class Main {
    public static void main(String[] args) {
        SGLWindow window = new SGLWindow();

        window.load(TitleScene.class);

        window.start();
    }
}
