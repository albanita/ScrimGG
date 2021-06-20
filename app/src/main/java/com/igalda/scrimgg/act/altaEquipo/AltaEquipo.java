package com.igalda.scrimgg.act.altaEquipo;

import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.LoadScreen;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.dom.Equipo;
import com.igalda.scrimgg.neg.Negocio;

public class AltaEquipo extends BaseActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    private static int MAXINDEX = 9999; // Máximo indice asignable al nombre de un equipo (Nom_Equipo#MAXINDICE)

    private Negocio n = Negocio.getNegocio();

    private EditText tbNomEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_alta_equipo);
        View rootView = getLayoutInflater().inflate(R.layout.activity_alta_equipo, frameLayout);
        tbNomEquipo = (EditText) findViewById(R.id.etNewTeamName);
    }

    public void altaEquipo(View view) {
        String nomEquipo = tbNomEquipo.getText().toString();
        // Se sustituyen los espacios en blanco por '_' para evitar fallos en la BD.
        String nomEquipoFix = nomEquipo.replace(' ', '_');


        // Este handler se encarga de mostrar un Toast en caso de error al escribir el nombre del equipo.
        Handler hand = new Handler();

        Equipo e;
        boolean creado = true;

        // A continuación, se establece el #NUM que aparece a continuación del nombre del equipo (ej. Mad_Lions#0000)
        for(int i = 0; i <= MAXINDEX; i++){ // ¡¡ El siguiente código no funcionará correctamente si se amplía MAXINDEX !!
            if (i<10 && !n.nEquipo().existeEquipo(nomEquipoFix+"#000"+i)){
                e = new Equipo(user.getUid(), nomEquipoFix+"#000"+i);
                n.nEquipo().altaEquipo(e);
                break;
            } else if (i<100 && !n.nEquipo().existeEquipo(nomEquipoFix+"#00"+i)){
                e = new Equipo(user.getUid(), nomEquipoFix+"#00"+i);
                n.nEquipo().altaEquipo(e);
                break;
            } else if (i<1000 && !n.nEquipo().existeEquipo(nomEquipoFix+"#0"+i)){
                e = new Equipo(user.getUid(), nomEquipoFix+"#0"+i);
                n.nEquipo().altaEquipo(e);
                break;
            } else if (i<10000 && !n.nEquipo().existeEquipo(nomEquipoFix+"#"+i)){
                e = new Equipo(user.getUid(), nomEquipoFix+"#"+i);
                n.nEquipo().altaEquipo(e);
                break;
            } else {
                displayToast("Nombre de equipo no disponible.");
                creado = false;
                break;
            }
        }
        if (creado){
            this.finish();
        }
    }
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }
}