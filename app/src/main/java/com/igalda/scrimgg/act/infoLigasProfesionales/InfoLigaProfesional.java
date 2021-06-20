package com.igalda.scrimgg.act.infoLigasProfesionales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.ImageDownloader;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.dom.TeamProfesional;
import com.igalda.scrimgg.dom.TournamentProfesional;
import com.igalda.scrimgg.dom.WinnerTypeProfesional;

import java.text.SimpleDateFormat;
import java.util.List;

public class InfoLigaProfesional extends AppCompatActivity {



    private TournamentProfesional tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_liga_profesional);

        Context context = getApplicationContext();
        Intent intent = getIntent();
        this.tp = (TournamentProfesional) intent.getExtras().get("LigaProfesional");

        TextView idTorneo = findViewById(R.id.txtIdTorneoProfesional);
        TextView fechaInicio = findViewById(R.id.txtFechaInicio);
        TextView fechaFin = findViewById(R.id.txtFechaFin);
        TextView idLiga = findViewById(R.id.txtIdLigaProfesional);
        TextView nombreLiga = findViewById(R.id.txtNombreLigaProfesional);
        TextView idGanador = findViewById(R.id.txtIdGanador);
        TextView tipoGanador = findViewById(R.id.txtTipoGanador);
        ImageView logoLiga = findViewById(R.id.logoLiga);
        LinearLayout ll = findViewById(R.id.listadoEquiposProfesionales);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        idTorneo.setText(idTorneo.getText() + tp.getId());
        if(tp.getBegin() != null){
            fechaInicio.setText(fechaInicio.getText() + sdf.format(tp.getBegin()));
        }
        else{
            fechaInicio.setText(fechaInicio.getText() + "");
        }
        if(tp.getEnd() != null){
            fechaFin.setText(fechaFin.getText() + sdf.format(tp.getEnd()));
        }
        else{
            fechaFin.setText(fechaFin.getText() + "");
        }
        idLiga.setText(idLiga.getText() + tp.getLeague().getId());
        nombreLiga.setText(nombreLiga.getText() + tp.getLeague().getName());
        idGanador.setText(idGanador.getText() + tp.getWinnerID());

        String urlImg = tp.getLeague().getImg_url();
        ImageDownloader.downloadTheImage(urlImg, logoLiga);

        WinnerTypeProfesional winnerType = tp.getWinnerType();
        if(winnerType == WinnerTypeProfesional.PLAYER){
            tipoGanador.setText(tipoGanador.getText() + "Jugador");
        }
        else if(winnerType == WinnerTypeProfesional.TEAM){
            tipoGanador.setText(tipoGanador.getText() + "Equipo");
        }

        List<TeamProfesional> equipos = tp.getTeams();
        for(TeamProfesional t: equipos)
        {
            TextView txt = new TextView(this);
            txt.setText(t.getName() + "\n");
            txt.setTextSize(20);
            txt.setGravity(Gravity.CENTER);
            txt.setTextColor(Color.parseColor("#FF007C"));
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("EquipoProfesional", t);
                    Intent intent = new Intent(context, VistaEquipoProfesional.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            ll.addView(txt);
        }
    }
}