package base.tank;

import base.Explosion;
import base.GameObject;
import base.Vector2D;
import base.action.Action;
import base.action.ActionRepeat;
import base.action.ActionSequence;
import base.action.ActionWait;
import base.physics.BoxCollider;
import base.physics.Physics;
import javafx.scene.media.MediaPlayer;
import tklibs.AudioUtils;

public class Tank extends GameObject implements Physics {
    BoxCollider collider;
    Action action;
    public Tank(){
        super();
        this.position=new Vector2D();
        this.defineAction();
    }

    private void defineAction() {
        ActionWait actionWait = new ActionWait(50);
        Action actionFire = new Action() {
            @Override
            public void run(GameObject master) {
                fire();
                this.isDone = true;
            }

            @Override
            public void reset() {
                this.isDone = false;
            }
        };
        ActionSequence actionSequence = new ActionSequence(actionWait, actionFire);
        ActionRepeat actionRepeat = new ActionRepeat(actionSequence);

        this.action = actionRepeat;
    }

    @Override
    public void run() {
        this.position.y+=3;
        this.action.run(this);
    }

    public void fire() {

    }

    public void takeDamage(int damage) {
    }

    @Override
    public void destroy() {
        super.destroy();
        Explosion explosion = GameObject.recycle(Explosion.class);
        explosion.position.set(this.position);

        AudioUtils.initialize();
        MediaPlayer mediaPlayer = AudioUtils.playMedia("assets/sounds/explosion.wav");
        mediaPlayer.play();
    }
    @Override
    public BoxCollider getBoxCollider() {
        return this.collider;
    }
}
