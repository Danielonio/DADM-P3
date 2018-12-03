package dadm.scaffold.space;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;

public class SpaceShipPlayer extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 40;
    private static final long TIME_BETWEEN_BULLETS = 500;
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<MegaBullet> mbullets= new ArrayList<MegaBullet>();
    private long timeSinceLastFire;

    private int maxX;
    private int maxY;
    private double speedFactor;


    public SpaceShipPlayer(GameEngine gameEngine,int color_nave){
        super(gameEngine, R.drawable.ship,color_nave);
        speedFactor = 2* pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;

        initBulletPool(gameEngine);
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            mbullets.add(new MegaBullet(gameEngine,1));
        }
    }

    private Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }
    private MegaBullet getMegaBullet() {
        if (mbullets.isEmpty()) {
            return null;
        }
        return mbullets.remove(0);
    }
    void releaseBullet(Bullet bullet) {
        bullets.add(bullet);
    }
    void releaseMegaBullet(MegaBullet mbullet) {
        mbullets.add(mbullet);
    }

    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY-maxY / 4;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine, List<GameObject> resto, int e) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.theInputController);
        checkFiring(elapsedMillis, gameEngine);
        int numGameObjects = resto.size();
        for (int i = 0; i < numGameObjects; i++) {
            if(resto.get(i) instanceof enemyShip){
                Sprite s = (Sprite)resto.get(i);
                if(rect.setIntersect(rect,s.getRect()) && i!=e){
                    gameEngine.nVidas--;
                    gameEngine.removeGameObject(resto.get(i));
                }
            }
        }
    }

    private void updatePosition(long elapsedMillis, InputController inputController) {
        positionX += speedFactor * inputController.horizontalFactor * elapsedMillis;
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += speedFactor * inputController.verticalFactor * elapsedMillis;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if(gameEngine.theInputController.isFiring){
            if ( timeSinceLastFire > TIME_BETWEEN_BULLETS) {//gameEngine.theInputController.isFiring &&
                MegaBullet mbullet = getMegaBullet(); MegaBullet mbullet1 = getMegaBullet(); MegaBullet mbullet2 = getMegaBullet();
                if (mbullet == null ||mbullet1 == null ||mbullet2 == null ) {
                    return;
                }
                mbullet.init(this, positionX + imageWidth/2, positionY,0);
                mbullet1.init(this, positionX + imageWidth/2, positionY,1);
                mbullet2.init(this, positionX + imageWidth/2, positionY,2);
                gameEngine.addGameObject(mbullet);gameEngine.addGameObject(mbullet1);gameEngine.addGameObject(mbullet2);
                timeSinceLastFire = 0;
            }
            else {
                timeSinceLastFire += elapsedMillis;
            }


        }else  {

        if ( timeSinceLastFire > TIME_BETWEEN_BULLETS) {//gameEngine.theInputController.isFiring &&
            Bullet bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + imageWidth/2, positionY);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
        }
        else {
            timeSinceLastFire += elapsedMillis;
        }
        }
    }

}
