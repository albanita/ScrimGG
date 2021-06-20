package com.igalda.scrimgg.act.infoEquiposUsuario;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.act.buscadorEquipo.ResultadoEquipo;
import com.igalda.scrimgg.dom.Equipo;
import com.igalda.scrimgg.neg.Negocio;
import com.igalda.scrimgg.neg.NegocioUsuario;

import java.util.List;

public class InfoEquiposUsuario extends AppCompatActivity {

    private List<Equipo> equipos;

    private FirebaseAuth mAuth;
    private FirebaseUser usr;

    private Negocio negocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_equipos);

        NegocioUsuario nu = new NegocioUsuario();
        this.mAuth = FirebaseAuth.getInstance();
        this.usr = this.mAuth.getCurrentUser();

        this.equipos = this.negocio.getNegocio().nUsuario().getEquipos(this.usr.getUid());

        Context context = getApplicationContext();
        LinearLayout ll = findViewById(R.id.listadoEquipos);
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