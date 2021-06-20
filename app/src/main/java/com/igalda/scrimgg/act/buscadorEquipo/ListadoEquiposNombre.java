package com.igalda.scrimgg.act.buscadorEquipo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.dom.Equipo;

import java.util.ArrayList;
import java.util.List;

public class ListadoEquiposNombre extends AppCompatActivity {

    private List<Equipo> equipos = new ArrayList<Equipo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_equipos_nombre);

        Context context = getApplicationContext();
        LinearLayout ll = findViewById(R.id.listadoEquipos);

        Intent intent = getIntent();
        int tam = intent.getIntExtra("tamano", 0);
        for(int i=0; i<tam; i++){
            Equipo e = (Equipo) intent.getExtras().get("Equipo"+i);
            this.equipos.add(e);
        }

        for(Equipo e: this.equipos){
                TextView txt = new TextView(this);
                txt.setText(e.getTid() + "\n");
                txt.setTextSize(20);
                txt.setGravity(Gravity.CENTER);
                txt.setTextColor(Color.parseColor("#FF007C"));
                txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Equipo", e);
                        Intent intent = new Intent(context, ResultadoEquipo.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                ll.addView(txt);
        }
    }
}