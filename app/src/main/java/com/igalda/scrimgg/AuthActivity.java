package com.igalda.scrimgg;

import android.content.Intent;

import com.igalda.scrimgg.dom.*;
import com.igalda.scrimgg.neg.NegocioUsuario;
import com.igalda.scrimgg.pers.PersistenciaEquipo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.igalda.scrimgg.pers.PersistenciaLigaProfesional;
import com.igalda.scrimgg.pers.PersistenciaUsuario;

import java.util.List;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "AuthActivity.LogCat";
    private static final int TEXT_REQUEST = 1;
    private EditText emailET, passET;
    private TextView regTV;
    private SpannableString regSS = new SpannableString("¿Todavía no te has registrado? Haz click aquí.");
    private ClickableSpan regCS = new ClickableSpan() {
        @Override
        public void onClick(@NonNull View widget) {
            Intent regIntent = new Intent(AuthActivity.this, regActivity.class);
            startActivityForResult(regIntent, TEXT_REQUEST);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();
        emailET = findViewById(R.id.tietEmail);
        passET = findViewById(R.id.tiedPass);
        regSS.setSpan(regCS, 41, 45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        regTV = findViewById(R.id.tvAuto);
        regTV.setText(regSS);
        regTV.setMovementMethod(LinkMovementMethod.getInstance());
        regTV.setHighlightColor(Color.TRANSPARENT);
    }

    public void login(View view) {

        String email, password;
        email = emailET.getText().toString();
        password = passET.getText().toString();
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Log.d(TAG, "signInWithEmail:success");
                                Intent intent = new Intent(getApplicationContext(),principal.class);
                                startActivity(intent);


                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(AuthActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Tienes que rellenar todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                //TODO: autorrellenar el campo de email.
                emailET.setText(data.getStringExtra("email"));
            }
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, StartingAct.class);
        startActivity(intent);
    }

    public void PRUEBAS(View view) {
        /*Usuario u = new Usuario("PTK Sandytxu#0000", "PTK Sandytxu", "dansanfer.personal@gmail.com", 0, "img/default.jpg", "PTK Sandytxu", 1);
        Usuario u2 = new Usuario("PTK Hate#0000", "PTK Makelele", "ptkhate@gmail.com", 0, "img/default.jpg", "PTK Hate", 1);
        Usuario u3 = new Usuario("PTK Kaiser#0000", "PTK Kaiser", "ptkkaiser@gmail.com", 0, "img/default.jpg", "PTK Kaiser", 1);
        Equipo e = new Equipo(u.getId(), "PTK Inter de Logroño#0000", "PTK Inter de Logroño", "src/img/prueba.jpg", privacidadEquipo.PRIVADO);
*/

        /*if(PersistenciaEquipo.existPlayer(e,u)){
            Log.d("Pruebas","Player exist in equip.");
        }else{
            Log.d("Pruebas", "Player doesn't exist in the equip.");
        }*/
        //PersistenciaEquipo.altaEquipo(e);
        //  PersistenciaUsuario.altaUsuario(u);
        // PersistenciaUsuario.altaUsuario(u2);
        //PersistenciaUsuario.altaUsuario(u3);

        /*PersistenciaEquipo.newPlayer(e, u, roles.ADC);
       PersistenciaEquipo.newPlayer(e, u2, roles.SUPP);
       PersistenciaEquipo.newPlayer(e, u3, roles.JUNGL);*/
       /* List<Usuario> listPlayers = PersistenciaEquipo.getPlayers(e);
        for(Usuario aux : listPlayers){
            Log.d("Pruebas","Equip: " + e.getNombre() + " player: " + aux.getNickName());
        }*/
        //PersistenciaEquipo.bajaEquipo(e);
    }

    public void goToMain(View view) {
        Intent intent = new Intent(getApplicationContext(),principal.class);
        startActivity(intent);
    }
}
