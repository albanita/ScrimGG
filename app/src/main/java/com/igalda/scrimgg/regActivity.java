package com.igalda.scrimgg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class regActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); // conexion con la BD
    private EditText nickET, emailET, passET;
    private String nickS, emailS, passS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        mAuth = FirebaseAuth.getInstance();
        nickET = findViewById(R.id.nickEditText);
        emailET = findViewById(R.id.emailEditText);
        passET = findViewById(R.id.passwordEditText);
    }

    public void registerClick(View view) {
        nickS = nickET.getText().toString();
        emailS = emailET.getText().toString();
        passS = passET.getText().toString();

        if (!nickS.isEmpty() && !emailS.isEmpty() && !passS.isEmpty()) {
            if (passS.length() >= 6) {
                registerUser();
            } else {
                Toast.makeText(this, "¡La contraseña es demasiado corta!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "¡Scrimmer, debes rellenar todos los datos!", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser() {
        Log.d("Confirmation", "Register clicked");
        mAuth.createUserWithEmailAndPassword(emailS, passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Confirmation","User added to the Auth");
                    Map<String, Object> map = new HashMap <>();
                    //Parámetros del propio usuario.
                    //Aquí completaremos las diferentes variables de los usuarios.
                    map.put("nick",nickS);
                    map.put("email",emailS);
                    map.put("cuentaRiot","");
                    map.put("imgPath","");
                    map.put("expScrim",0);
                    map.put("nivelScrim",1);
                    String id = mAuth.getCurrentUser().getUid();

                    //Con esta linea directamente creamos en la base de datos la colección Users.
                    db.collection("users").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Confirmation", "User added to the DB");
                            Intent replyintent = new Intent();
                            replyintent.putExtra("email",emailS);
                            setResult(RESULT_OK,replyintent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Confirmation","Error adding the user");
                        }
                    });
                   /*db.collection("users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                Log.d("Confirmation","User added to the DB");
                                Intent replyIntent = new Intent();
                                replyIntent.putExtra("email",emailS);
                                setResult(RESULT_OK,replyIntent);
                                finish();
                            }else{
                                Log.d("Error","Error adding user to BD");
                                Toast.makeText(regActivity.this,"No se ha podido registrar el usuario.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });*/
                    Log.d("Confirmation", "Me he saltado el mDB");
                } else {
                    Toast.makeText(regActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );

    }

    public void writeOnBD(View view) {/*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();*/
        Map<String,Object> map = new HashMap<>();
        map.put("nick","sandy");
        map.put("email","prueba@gmail.com");
       /* myRef.child("Users").push().setValue(map);*/
        db.collection("users").document("a").set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Confirmation","Added");
            }
        });


    /*.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("cONFIRMATION", "DocumentSnapshot added with ID: " +documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Confirmation","Error adding it", e );
            }
        });*/
            Log.d("Confirmation", "He lanzado la escritura");
    }

    public void goToMain(View view) {
        Intent intent = new Intent(getApplicationContext(),principal.class);
        startActivity(intent);
    }
}