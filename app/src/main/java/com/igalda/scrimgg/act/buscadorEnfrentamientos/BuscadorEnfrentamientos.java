package com.igalda.scrimgg.act.buscadorEnfrentamientos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.neg.Negocio;
import com.igalda.scrimgg.neg.NegocioEnfrentamiento;
import com.igalda.scrimgg.dom.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BuscadorEnfrentamientos extends AppCompatActivity {

   // private List<String> enfrentamientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador_enfrentamientos);

        List<Torneo> torneos = Negocio.getNegocio().nTorneo().listaTorneos();

        List<Liga> ligas = Negocio.getNegocio().nLiga().listadoLigas();

        List<Match> enfrentamientosTorneos = new ArrayList<Match>();
        List<Match> enfrentamientosLigas = new ArrayList<Match>();

        for(Torneo t: torneos){
            List<Match> enfr = Negocio.getNegocio().nTorneo().getMactches(t);
            for(Match m : enfr){
                enfrentamientosTorneos.add(m);
            }
        }
        for(Liga l: ligas){
            List<Match> enfr = Negocio.getNegocio().nLiga().getMatches(l);
            for(Match m : enfr){
                enfrentamientosLigas.add(m);
            }
        }


        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        LinearLayout ll = findViewById(R.id.listadoEnfrentamientos);
        Context context = getApplicationContext();

        // preparar los enfrentamientos de los torneos:
        TextView tituloTorneos = new TextView(this);
        tituloTorneos.setText("Enfrentamientos de los torneos\n");
        tituloTorneos.setTextSize(20);
        tituloTorneos.setGravity(Gravity.CENTER);
        tituloTorneos.setTextColor(Color.parseColor("#FF007C"));
        ll.addView(tituloTorneos);

        for(Match m: enfrentamientosTorneos){
            TextView txt = new TextView(this);
            txt.setText(m.getId()+"\n");
            txt.setTextSize(20);
            txt.setGravity(Gravity.CENTER);
            txt.setTextColor(Color.WHITE);
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Enfrentamiento", m);

                    Intent intent = new Intent(context, VistaEnfrentamiento.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            ll.addView(txt);
        }

        // preparar los enfrentamientos de las ligas:
        TextView tituloLigas = new TextView(this);
        tituloLigas.setText("\nEnfrentamientos de las ligas\n");
        tituloLigas.setTextSize(20);
        tituloLigas.setGravity(Gravity.CENTER);
        tituloLigas.setTextColor(Color.WHITE);
        ll.addView(tituloLigas);
        for(Match m: enfrentamientosLigas){
            TextView txt = new TextView(this);
            txt.setText(m.getId()+"\n");
            txt.setTextSize(20);
            txt.setGravity(Gravity.CENTER);
            txt.setTextColor(Color.WHITE);
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Enfrentamiento", m);

                    Intent intent = new Intent(context, VistaEnfrentamiento.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            ll.addView(txt);
        }

    }
}