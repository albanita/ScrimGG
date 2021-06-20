package com.igalda.scrimgg.act.buscadorEnfrentamientos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.dom.Equipo;
import com.igalda.scrimgg.dom.Match;
import com.igalda.scrimgg.dom.Usuario;
import com.igalda.scrimgg.dom.roles;
import com.igalda.scrimgg.neg.Negocio;

import java.text.SimpleDateFormat;

public class VistaEnfrentamiento extends BaseActivity {


    private FirebaseAuth mAuth;
    private FirebaseUser usr;
    private Usuario usuario;

    private Match enfrentamiento;

    private Button btnEntrar;
    private RadioButton rBtn1;
    private RadioButton rBtn2;
    private RadioGroup rGroup;

    private Equipo azul;
    private Equipo rojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_vista_enfrentamiento);
        View rootView = getLayoutInflater().inflate(R.layout.activity_vista_enfrentamiento, frameLayout);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        this.enfrentamiento = (Match) bundle.getSerializable("Enfrentamiento");

        mAuth = FirebaseAuth.getInstance();
        this.usr = mAuth.getCurrentUser();
        this.usuario = Negocio.getNegocio().nUsuario().buscarUsuario(usr.getUid());

        TextView txtFecha = (TextView) findViewById(R.id.txtFechaEnfrentamiento);
        TextView txtEquipo1 = (TextView) findViewById(R.id.txtEquipo1);
        TextView txtEquipo2 = (TextView) findViewById(R.id.txtEquipo2);
        TextView txtEquipoGanador = (TextView) findViewById(R.id.txtEquipoGanador);

        btnEntrar = (Button) findViewById(R.id.btnEntrarEnfrentamiento);
        rBtn1 = (RadioButton) findViewById(R.id.radioBtn1);
        rBtn2 = (RadioButton) findViewById(R.id.radioBtn2);
        rGroup = (RadioGroup) findViewById(R.id.radioGroup);

        txtFecha.setText(txtFecha.getText() + " "+sdf.format(this.enfrentamiento.getFecha()));

        if(Negocio.getNegocio().nEquipo().existeEquipo(this.enfrentamiento.getIdAzul()) && Negocio.getNegocio().nEquipo().existeEquipo(this.enfrentamiento.getIdRojo())){
            this.azul = Negocio.getNegocio().nEquipo().buscarEquipoPorID(this.enfrentamiento.getIdAzul());
            this.rojo = Negocio.getNegocio().nEquipo().buscarEquipoPorID(this.enfrentamiento.getIdRojo());
            txtEquipo1.setText(txtEquipo1.getText()+" "+this.azul.getTid());
            txtEquipo2.setText(txtEquipo2.getText()+" "+this.rojo.getTid());
            txtEquipoGanador.setText(txtEquipoGanador.getText()+" "+this.enfrentamiento.getResultado());
            rBtn1.setText(txtEquipo1.getText().toString());
            rBtn2.setText(txtEquipo2.getText().toString());
        }
        if(!this.enfrentamiento.getResultado().equals("") &&
                (!Negocio.getNegocio().nEquipo().getPlayers(this.azul).contains(this.usuario) ||
                !Negocio.getNegocio().nEquipo().getPlayers(this.rojo).contains(this.usuario))
        ){
            // el usuario como capitan, no pertenece a ninguno de los equipos y el enfrentamiento tampoco tiene un resultado
            // entonces el usuario se puede unir al enfrentamiento

        }
        else{
            btnEntrar.setEnabled(false);
            desabilitarBotonesEquipos();
        }
    }

    private void desabilitarBotonesEquipos(){
        for(int i=0; i<rGroup.getChildCount(); i++){
            rGroup.getChildAt(i).setEnabled(false);
        }
    }

    public void unirmeAlEnfrentamiento(View view) {
        if(rBtn1.isChecked() || rBtn2.isChecked())
        {
            if(rBtn1.isChecked()){
                Negocio.getNegocio().nEquipo().anadirJugador(this.azul, this.usuario, roles.ADC);
            }
            else if(rBtn2.isChecked()){
                Negocio.getNegocio().nEquipo().anadirJugador(this.rojo, this.usuario, roles.ADC);
            }
            Toast.makeText(this, "Se te ha añadido al enfrentamiento del día " + new SimpleDateFormat("dd/mm/yyyy").format(this.enfrentamiento.getFecha()), Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            Toast.makeText(this, "Tienes que elegir un equipo de primero", Toast.LENGTH_SHORT).show();
        }
    }
}