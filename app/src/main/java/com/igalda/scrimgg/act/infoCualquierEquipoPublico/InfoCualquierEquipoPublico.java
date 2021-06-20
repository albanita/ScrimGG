package com.igalda.scrimgg.act.infoCualquierEquipoPublico;

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
import com.igalda.scrimgg.act.buscadorEquipo.ResultadoEquipo;
import com.igalda.scrimgg.dom.Equipo;
import com.igalda.scrimgg.dom.privacidadEquipo;
import com.igalda.scrimgg.neg.Negocio;

import java.util.List;

public class InfoCualquierEquipoPublico extends AppCompatActivity {

    private List<Equipo> equipos;
    private Negocio negocio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_cualquier_equipo);

        Context context = getApplicationContext();
        LinearLayout ll = findViewById(R.id.listadoEquipos);
        this.equipos = this.negocio.getNegocio().nEquipo().listaEquipos();
        for(Equipo e: this.equipos){
            if(e.getTipo() == privacidadEquipo.PUBLICO){
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
}