package dadm.scaffold.engine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.input.InputController;
import dadm.scaffold.space.resultado;

public class GameEngine {


    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private List<GameObject> objectsToAdd = new ArrayList<GameObject>();
    private List<GameObject> objectsToRemove = new ArrayList<GameObject>();

    private UpdateThread theUpdateThread;
    private DrawThread theDrawThread;
    public InputController theInputController;
    private final GameView theGameView;

    public int width;
    public int height;
    public double pixelFactor;

    private Activity mainActivity;
    TextView bajas;
    public int nBajas=0;
    public int nVidas=3;
    boolean b = true;
    public boolean fin = true;
    public GameEngine(Activity activity, GameView gameView, TextView bajas) {
        mainActivity = activity;

        theGameView = gameView;
        theGameView.setGameObjects(this.gameObjects);
        this.width = theGameView.getWidth()
                - theGameView.getPaddingRight() - theGameView.getPaddingLeft();
        this.height = theGameView.getHeight()
                - theGameView.getPaddingTop() - theGameView.getPaddingTop();

        this.pixelFactor = this.height / 600d;

        this.bajas=bajas;

    }

    public void setTheInputController(InputController inputController) {
        theInputController = inputController;
    }

    public void startGame() {

        // Stop a game if it is running
        stopGame();

        // Setup the game objects
        int numGameObjects = gameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            gameObjects.get(i).startGame();
        }

        // Start the update thread
        theUpdateThread = new UpdateThread(this);
        theUpdateThread.start();

        // Start the drawing thread
        theDrawThread = new DrawThread(this);
        theDrawThread.start();
    }

    public void stopGame() {
        if (theUpdateThread != null) {
            theUpdateThread.stopGame();
        }
        if (theDrawThread != null) {
            theDrawThread.stopGame();
        }
    }

    public void pauseGame() {
        if (theUpdateThread != null) {
            theUpdateThread.pauseGame();
        }
        if (theDrawThread != null) {
            theDrawThread.pauseGame();
        }
    }

    public void resumeGame() {
        if (theUpdateThread != null) {
            theUpdateThread.resumeGame();
        }
        if (theDrawThread != null) {
            theDrawThread.resumeGame();
        }
    }

    public void addGameObject(GameObject gameObject) {
        if (isRunning()) {
            objectsToAdd.add(gameObject);
        } else {
            gameObjects.add(gameObject);
        }
        mainActivity.runOnUiThread(gameObject.onAddedRunnable);
    }

    public void removeGameObject(GameObject gameObject) {
        objectsToRemove.add(gameObject);
        mainActivity.runOnUiThread(gameObject.onRemovedRunnable);
    }

    public void onUpdate(long elapsedMillis) {

        bajas.setText("Eliminados: "+  nBajas+ "  |  Vidas: "+ nVidas);

        int numGameObjects = gameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            synchronized (gameObjects) {
                gameObjects.get(i).onUpdate(elapsedMillis, this, gameObjects, i);
            }
        }

        synchronized (gameObjects) {
            while (!objectsToRemove.isEmpty()) {
                gameObjects.remove(objectsToRemove.remove(0));
            }
            while (!objectsToAdd.isEmpty()) {
                gameObjects.add(objectsToAdd.remove(0));
            }
        }
        if(nVidas<=0 || nBajas>=20) {
            fin = false;
            //stopGame();
            lanzar();
            //mainActivity.finish();
        }
    }

    public void onDraw() {
        theGameView.draw();
    }

    public boolean isRunning() {
        return theUpdateThread != null && theUpdateThread.isGameRunning();
    }

    public boolean isPaused() {
        return theUpdateThread != null && theUpdateThread.isGamePaused();
    }

    public Context getContext() {
        return theGameView.getContext();
    }

    public void lanzar(){//inicia una nueva actividad pasándole como parametro la puntuación
        if(b==true) {
            b =false;
            Intent i = new Intent(getContext(), resultado.class);
            i.putExtra("puntos", nBajas);
            i.putExtra("vidas", nVidas);
            getContext().startActivity(i);
            //pauseGame();
            mainActivity.finish();
        }
    }
}
