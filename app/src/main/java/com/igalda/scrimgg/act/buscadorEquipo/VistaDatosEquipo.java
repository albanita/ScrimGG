package com.igalda.scrimgg.act.buscadorEquipo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.R;

public class VistaDatosEquipo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_datos_equipo);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = getIntent();

        TextView txtNombre = (TextView) findViewById(R.id.txtNombre);
        TextView txtDatos = (TextView) findViewById(R.id.txtDatos);

        Bundle bundle = intent.getExtras();
        String nombre = bundle.getString("Nombre");
        String datos = bundle.getString("Datos");

        txtNombre.setText(nombre);
        txtDatos.setText(datos);
    }
}