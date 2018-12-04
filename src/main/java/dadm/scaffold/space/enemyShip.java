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
    GameEngine ge;
   int n,m;

    public enemyShip(GameEngine gameEngine){
        super(gameEngine, R.drawable.enemigo,-1);

        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;
        Random r = new Random();
        n = r.nextInt(20)+1 ;
        positionX =maxX/20 *n;//maxX - ((5 + maxX / 5)*n);
        positionY = maxY / 14;
        ge=gameEngine;
        m = r.nextInt(4)+1 ;
        speedFactor =m* pixelFactor * 100d / 1000000d; // We want to move at 100px per second on a 400px tall screen


    }




    @Override
    public void startGame() {




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

        positionY += speedFactor*10 ;
        if (positionY > maxY ) {
            ge.removeGameObject(this);}

    }
}
