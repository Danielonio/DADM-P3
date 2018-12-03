package dadm.scaffold.space;

import android.graphics.Rect;

import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;
import dadm.scaffold.engine.Sprite;

public class MegaBullet extends Sprite {

    private double speedFactor;

    private SpaceShipPlayer parent;

    int dir;
    public MegaBullet(GameEngine gameEngine,int dir){
        super(gameEngine, R.drawable.robot,-1);
        this.dir=dir;
        speedFactor = gameEngine.pixelFactor * -300d / 1000d;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine, List<GameObject> resto, int e) {
        switch (dir)
        {
            case 0:
                positionY += speedFactor * elapsedMillis;
                positionX += 0.4*speedFactor * elapsedMillis;

                break;
            case 1:
                positionY += speedFactor * elapsedMillis;

                break;
            case 2:
                positionY += speedFactor * elapsedMillis;
                positionX -=0.4* speedFactor * elapsedMillis;

                break;
        }
        if (positionY < -imageHeight) {
            gameEngine.removeGameObject(this);

            parent.releaseMegaBullet(this);
        }


        int numGameObjects = resto.size();
        for (int i = 0; i < numGameObjects; i++) {
            if(resto.get(i) instanceof enemyShip){
                Sprite s = (Sprite)resto.get(i);
                if(rect.setIntersect(rect,s.getRect()) && i!=e){
                    gameEngine.removeGameObject(resto.get(i));
                }
            }
        }
    }

    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY, int dir) {
        this.dir=dir;
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
        parent = parentPlayer;
    }
}
