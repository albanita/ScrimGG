package com.igalda.scrimgg.act.infoLigasProfesionales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.ImageDownloader;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.dom.TeamProfesional;

public class VistaEquipoProfesional extends BaseActivity {

    private TeamProfesional team;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_vista_equipo_profesional);
        View rootView = getLayoutInflater().inflate(R.layout.activity_vista_equipo_profesional, frameLayout);

        Intent intent = getIntent();
        this.team = (TeamProfesional) intent.getExtras().get("EquipoProfesional");

        TextView idTeam = findViewById(R.id.txtIdProfesionalTeam);
        TextView acronym = findViewById(R.id.txtAcronym);
        TextView location = findViewById(R.id.txtLocation);
        TextView name = findViewById(R.id.txtTeamName);
        ImageView logo = findViewById(R.id.logoEquipoProfesional);

        idTeam.setText(idTeam.getText() + this.team.getId());
        acronym.setText(acronym.getText() + this.team.getAcronym());
        location.setText(location.getText() + this.team.getLocation());
        name.setText(name.getText() + this.team.getName());
        String imgUrl = this.team.getImgUrl();
        if(imgUrl != null){
            ImageDownloader.downloadTheImage(imgUrl, logo);
        }
    }
}