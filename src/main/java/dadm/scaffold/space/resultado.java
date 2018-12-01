package dadm.scaffold.space;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Space;
import android.widget.TextView;

import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;


public class resultado extends AppCompatActivity {
    private Button s,v;
    TextView puntuacion,sec;
    SharedPreferences shar;

    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        shar = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);

        Bundle bundle = getIntent().getExtras();
        int puntos = bundle.getInt("puntos");
        int vidas = bundle.getInt("vidas");
        sec = (TextView)findViewById(R.id.t);
        sec.setText("Vidas restantes" + vidas);
        puntuacion= (TextView)findViewById(R.id.pp);
        puntuacion.setText("Enemigos eliminados: "+String.valueOf(puntos));
        s = findViewById(R.id.sj);
        v = findViewById(R.id.vj);




        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          /*      SharedPreferences.Editor editor = shar.edit();
                editor.putString("Nombre", "anonimo");
                editor.commit();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();*/
            }


        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ScaffoldActivity.class));
                finish();
            }


        });

    }


}

