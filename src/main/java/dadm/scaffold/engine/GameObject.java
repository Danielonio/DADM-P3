package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.List;

public abstract class GameObject {
    //public Rect rect;

    public abstract void startGame();

    public abstract void onUpdate(long elapsedMillis, GameEngine gameEngine, List<GameObject> gameObjects, int i);

    //public abstract Rect getRect();

    public abstract void onDraw(Canvas canvas);

    public final Runnable onAddedRunnable = new Runnable() {
        @Override
        public void run() {
            onAddedToGameUiThread();
        }
    };

    public void onAddedToGameUiThread(){
    }

    public final Runnable onRemovedRunnable = new Runnable() {
        @Override
        public void run() {
            onRemovedFromGameUiThread();
        }
    };

    public void onRemovedFromGameUiThread(){
    }
}
