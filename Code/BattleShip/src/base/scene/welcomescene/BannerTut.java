package base.scene.welcomescene;

import base.GameObject;
import base.Settings;
import base.event.KeyEventPress;
import base.renderer.SingleImageRenderer;
import base.scene.*;
import tklibs.SpriteUtils;

import java.awt.image.BufferedImage;

public class BannerTut extends GameObject {
    public BannerTut(){
        super();
        BufferedImage image = SpriteUtils.loadImage("assets/images/tut/tut.png");
        this.renderer = new SingleImageRenderer(image);
        this.position.set(Settings.SCREEN_WIDHT/2, Settings.SCREEN_HEIGHT/2);
    }
    @Override
    public void run() {
        if (KeyEventPress.isEnterPress){
            SceneManager.signNewScene(new Level1Scene());
        }
    }
}
