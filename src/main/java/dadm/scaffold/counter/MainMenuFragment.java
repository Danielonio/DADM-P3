package dadm.scaffold.counter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;


public class MainMenuFragment extends BaseFragment implements View.OnClickListener {
    private RadioGroup grupo;
    int radioButtonID;
    RadioButton select;
    String respuesta="o";
    public MainMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_start).setOnClickListener(this);
        grupo =  view.findViewById(R.id.group);

        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {//cuando seleccionas un elemento del radioGroup el valor de respuesta se actualiza
                if(group.findViewById(checkedId)!=null)
                {
                    select = group.findViewById(checkedId);
                    respuesta = select.getText().toString();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

       if(respuesta.equals("Rojo")||respuesta.equals("o"))
        ((ScaffoldActivity)getActivity()).startGame( 0);
       else
       if(respuesta.equals("Azul"))
           ((ScaffoldActivity)getActivity()).startGame( 1);
    }
}
