package com.igalda.scrimgg.act.buscadorEquipo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.R;

import com.igalda.scrimgg.neg.NegocioEquipo;
import com.igalda.scrimgg.dom.*;

import java.util.List;

public class BuscadorEquipo extends BaseActivity {

    private TextView txtNombreEquipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_buscador_equipo);
        View rootView = getLayoutInflater().inflate(R.layout.activity_buscador_equipo, frameLayout);

        this.txtNombreEquipo = (TextView) findViewById(R.id.txtNombreEquipo);
    }

    public void buscarEquipo(View view) {
        String nombreEquipo = this.txtNombreEquipo.getText().toString();
        NegocioEquipo ne = new NegocioEquipo();

        List<Equipo> equipos = ne.buscarEquipoPorNombre(nombreEquipo);

        if(!equipos.isEmpty()){
            Bundle bundle = new Bundle();
            int i = 0;
            for(Equipo equipo: equipos){
                bundle.putSerializable("Equipo"+i, equipo);
                i++;
            }
            bundle.putInt("tamano", i);

            Intent intent = new Intent(this, ListadoEquiposNombre.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "No existe ningun equipo con el nombre " + nombreEquipo, Toast.LENGTH_SHORT).show();
            this.txtNombreEquipo.setText("");
        }

    }
}