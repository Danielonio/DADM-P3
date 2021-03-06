package dadm.scaffold.counter;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.engine.FramesPerSecondCounter;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameView;
import dadm.scaffold.input.JoystickInputController;
import dadm.scaffold.space.SpaceShipPlayer;
import dadm.scaffold.space.enemyShip;


public class GameFragment extends BaseFragment implements View.OnClickListener {
    private GameEngine theGameEngine;


    Handler handler = new Handler();
    int delay = 1000; //milliseconds

    private int ship;
    TextView bajas;

    public GameFragment() {
    }
    public static GameFragment newInstance(int a) {
        Bundle bundle = new Bundle();

       bundle.putInt("ship", a);

        GameFragment fragment = new GameFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
    void readBundle(Bundle b)
    {
        ship =b.getInt("ship");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        readBundle(getArguments());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        bajas=(TextView)getView().findViewById(R.id.bajas);

        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_play_pause).setOnClickListener(this);
        final ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout(){
                //Para evitar que sea llamado múltiples veces,
                //se elimina el listener en cuanto es llamado
                observer.removeOnGlobalLayoutListener(this);
                GameView gameView = (GameView) getView().findViewById(R.id.gameView);
                theGameEngine = new GameEngine(getActivity(), gameView,bajas);
                theGameEngine.setTheInputController(new JoystickInputController(getView()));
                if(ship==0)
                theGameEngine.addGameObject(new SpaceShipPlayer(theGameEngine,0));
                else
                    theGameEngine.addGameObject(new SpaceShipPlayer(theGameEngine,1));

                theGameEngine.addGameObject(new FramesPerSecondCounter(theGameEngine));
                theGameEngine.startGame();
            }
        });

        handler.postDelayed(new Runnable(){
            public void run(){
             theGameEngine.addGameObject(new enemyShip(theGameEngine));
                handler.postDelayed(this, delay);
            }
        }, delay);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_pause) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (theGameEngine.isRunning()){
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        theGameEngine.stopGame();
    }

    @Override
    public boolean onBackPressed() {
        if (theGameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
            return true;
        }
        return false;
    }

    private void pauseGameAndShowPauseDialog() {
        theGameEngine.pauseGame();
        if(theGameEngine.fin) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Juego pausado")

                    .setPositiveButton("Seguir jugando", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            theGameEngine.resumeGame();
                        }
                    })
                    .setNegativeButton("Menú principal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            theGameEngine.stopGame();
                            ((ScaffoldActivity) getActivity()).navigateBack();
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            theGameEngine.resumeGame();
                        }
                    })
                    .create()
                    .show();
        }

    }

    private void playOrPause() {

        ImageButton button = (ImageButton) getView().findViewById(R.id.btn_play_pause);
        if (theGameEngine.isPaused()) {
            theGameEngine.resumeGame();
            //button.setText(R.string.pause);
        }
        else {
            theGameEngine.pauseGame();
            //button.setText(R.string.resume);
        }
    }
}
