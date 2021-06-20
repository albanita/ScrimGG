package com.igalda.scrimgg.act.infoLigasProfesionales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.dom.TournamentProfesional;
import com.igalda.scrimgg.neg.Negocio;

import java.util.ArrayList;
import java.util.List;

public class ListadoLigasProfesionales extends AppCompatActivity {

    private List<TournamentProfesional> ligas;
    private  RadioButton radInternacionales;
    private RadioButton radRegionales;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_ligas_profesionales);

        this.radInternacionales = findViewById(R.id.radInternacionales);
        this.radRegionales = findViewById(R.id.radRegionales);

        this.linearLayout = findViewById(R.id.listadoNombresLigas);
        this.ligas = Negocio.getNegocio().nLigaProfesional().getLigasProfesionales(new ArrayList<String>());
    }

    public void buscarLigasProfesionales(View view) {
        List<String> nombresLigas = new ArrayList<String>();
        List<TournamentProfesional> ligasProfesionales = new ArrayList<TournamentProfesional>();
        if(this.radInternacionales.isChecked() || this.radRegionales.isChecked()){
            if(this.radInternacionales.isChecked()){
                nombresLigas.add("Mid-");
            }
            else if(this.radRegionales.isChecked()){
                nombresLigas.add("Lec");
                nombresLigas.add("Lcs");
                nombresLigas.add("Lck");
                nombresLigas.add("Lpl");
            }

            for(TournamentProfesional tp: this.ligas){
                for(String nom: nombresLigas){
                    if(tp.getLeague().getName().toUpperCase().startsWith(nom.toUpperCase())){
                        ligasProfesionales.add(tp);
                    }
                }
            }

            this.linearLayout.removeAllViews();
            Context context = getApplicationContext();
            int i = 0;
            for(TournamentProfesional tp: ligasProfesionales)
            {
                TextView txt = new TextView(this);
                txt.setText(tp.getLeague().getName() + "\n");
                txt.setTextSize(20);
                txt.setGravity(Gravity.CENTER);
                txt.setTextColor(Color.parseColor("#FF007C"));
                txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("LigaProfesional", tp);
                        Intent intent = new Intent(context, InfoLigaProfesional.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                this.linearLayout.addView(txt);
            }
        }
    }
}