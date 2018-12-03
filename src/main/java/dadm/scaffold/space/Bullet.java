package dadm.scaffold.space;



import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;
import dadm.scaffold.engine.Sprite;

public class Bullet extends Sprite {

    private double speedFactor;

    private SpaceShipPlayer parent;

    public Bullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.bullet,-1);

        speedFactor = gameEngine.pixelFactor * -300d / 1000d;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine, List<GameObject> gameObjects, int i) {
        positionY += speedFactor * elapsedMillis;
        if (positionY < -imageHeight) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this);
        }
    }


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
        parent = parentPlayer;
    }
}
