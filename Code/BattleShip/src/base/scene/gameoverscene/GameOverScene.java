package base.scene.gameoverscene;

import base.GameObject;
import base.scene.Scene;
import javafx.scene.media.MediaPlayer;
import tklibs.AudioUtils;

public class GameOverScene extends Scene {
    @Override
    public void destroy() {
        GameObject.clearAll();
    }

    @Override
    public void init() {
        AudioUtils.initialize();
        MediaPlayer mediaPlayer = AudioUtils.playMedia("assets/sounds/gameover.wav");
        mediaPlayer.play();
        GameObject.recycle(BannerScene1.class);
    }

    @Override
    public void run() {
    }
}
