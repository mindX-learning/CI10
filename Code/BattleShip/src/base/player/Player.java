package base.player;

import base.*;
import base.counter.FrameCounter;
import base.event.KeyEventPress;
import base.event.MouseManager;
import base.physics.BoxCollider;
import base.physics.Physics;
import base.stone.StoneType1;
import javafx.scene.media.MediaPlayer;
import tklibs.AudioUtils;

public class Player extends GameObject implements Physics {
    FrameCounter fireCounter;
    int hp;
    Vector2D velocity;
    Vector2D bulletVelocity;

    public Player() {
        super();
        this.bulletVelocity = new Vector2D();
        this.renderer = new PlayerAnimator();
        this.position = new Vector2D(Settings.START_PLAYER_POSITION_X
                , Settings.START_PLAYER_POSITION_Y);
        this.fireCounter = new FrameCounter(10);
        this.collider = new BoxCollider(30, 69);
        this.hp = Settings.PLAYER_HP;
        this.velocity = new Vector2D(0, 0);
    }
    @Override
    public void run() {
        int VX = 0;
        int VY = 0;
        if(KeyEventPress.isUpPress) {
            VY -= 3;
        }
        if(KeyEventPress.isDownPress) {
            VY += 3;
        }
        if(KeyEventPress.isRightPress) {
            VX += 3;
        }
        if(KeyEventPress.isLeftPress) {
            VX -= 3;
        }
        this.move(VX, VY);
        //fire
        boolean fireCounterRun = this.fireCounter.run();
        if(MouseManager.mouseManager.isPressed && fireCounterRun) {
            bulletVelocity.set(MouseManager.mouseManager.position.x - this.position.x, MouseManager.mouseManager.position.y - this.position.y );
            this.fire(bulletVelocity.normalize().scaleThis(6));
        }
        if (KeyEventPress.isSpacePress && fireCounterRun){
            this.fireSpace();
        }

        if (this.position.x <= 90){
            this.position.x = 90;
        }
        if (this.position.x >= 405){
            this.position.x = 405;
        }
        if (this.position.y >= 520){
            this.position.y = 520;
        }
        if (this.position.y <= 0){
            this.position.y = -50;
        }

        StoneType1 type1 = GameObject.intersect(StoneType1.class,this);
        if (type1!=null){
            this.destroy();
        }
        this.position.addThis(this.velocity);
    }
    public void fireSpace(){
        AudioUtils.initialize();
        MediaPlayer mediaPlayer = AudioUtils.playMedia("assets/sounds/player-shot.mp3");
        mediaPlayer.play();
        PlayerBulletType1 bulletType1 = GameObject.recycle(PlayerBulletType1.class);
        bulletType1.position.set(this.position.x,this.position.y);
        bulletType1.velocity.set(0,-6);
        this.fireCounter.reset();
    }
    public void fire(Vector2D velocity) {
        AudioUtils.initialize();
        MediaPlayer mediaPlayer = AudioUtils.playMedia("assets/sounds/player-shot.mp3");
        mediaPlayer.play();
        PlayerBulletType1 bullet = GameObject.recycle(PlayerBulletType1.class);
        bullet.velocity.set(velocity);
        bullet.position.set(this.position.x, this.position.y);
        this.fireCounter.reset();
    }

    public void move(int velocityX, int velocityY) {
        if(velocityX == 0 && velocityY == 0) {
            this.velocity.set(0, 0);
        } else {
            this.velocity.addThis(velocityX, velocityY);
            this.velocity.set(clamp(velocity.x, -3, 3),
                    clamp(velocity.y, -3, 3));
        }
    }

    public float clamp(float number, float min, float max) {
        return number < min ? min : number > max ? max : number;
    }

    @Override
    public void destroy() {
        super.destroy();

        Explosion explosion = GameObject.recycle(Explosion.class);
        explosion.position.set(this.position);

        AudioUtils.initialize();
        MediaPlayer mediaPlayer = AudioUtils.playMedia("assets/sounds/enemy-player-explosion-big.wav");
        mediaPlayer.play();

        DeathPlayer deathPlayer = GameObject.recycle(DeathPlayer.class);
        deathPlayer.position.set(this.position);
    }

    @Override
    public BoxCollider getBoxCollider() {
        return this.collider;
    }

    public void takeDamage(int damage) {
        AudioUtils.initialize();
        MediaPlayer mediaPlayer = AudioUtils.playMedia("assets/sounds/takedamage.mp3");
        mediaPlayer.play();

        this.hp -= damage;
        if(this.hp <= 0) {
            this.destroy();
        }
    }

    public void takeDamageByTank(int damage) {
        AudioUtils.initialize();
        MediaPlayer mediaPlayer = AudioUtils.playMedia("assets/sounds/takedamage.mp3");
        mediaPlayer.play();

        this.hp -= damage;
        if(this.hp <= 0) {
            this.destroy();
        }

    }

    public void takeDamageByBoss(int damage) {
        AudioUtils.initialize();
        MediaPlayer mediaPlayer = AudioUtils.playMedia("assets/sounds/takedamage.mp3");
        mediaPlayer.play();

        this.hp -= damage;
        if(this.hp <= 0) {
            this.destroy();
        }
    }
}
