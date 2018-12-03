package dadm.scaffold.space;

import android.graphics.Rect;

import java.util.List;
import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;
import dadm.scaffold.engine.Sprite;


public class enemyShip extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private static final long TIME_BETWEEN_BULLETS = 250;

    private int maxX;
    private int maxY;
    private double speedFactor;
   int n;

    public enemyShip(GameEngine gameEngine){
        super(gameEngine, R.drawable.robot,-1);
        speedFactor = pixelFactor * 100d / 1000000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;
        Random r = new Random();
        n = r.nextInt(5)+1 ;
    }




    @Override
    public void startGame() {

        int n=1;


        positionX = maxX/n;//maxX - ((5 + maxX / 5)*n);
        positionY = maxY / 4;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine, List<GameObject> resto, int e) {
        // Get the info from the inputController
        updatePosition(elapsedMillis);

        int numGameObjects = resto.size();
        for (int i = 0; i < numGameObjects; i++) {
            if(resto.get(i) instanceof Bullet ||resto.get(i) instanceof MegaBullet ){
                Sprite s = (Sprite)resto.get(i);
                if(rect.setIntersect(rect,s.getRect()) && i!=e){
                    gameEngine.removeGameObject(this);
                    gameEngine.removeGameObject(resto.get(i));
                   gameEngine.nBajas++;
                }
            }
        }
    }


    private void updatePosition(long elapsedMillis) {
        positionX = maxX/n;
        positionY += speedFactor*10 ;

    }
}
