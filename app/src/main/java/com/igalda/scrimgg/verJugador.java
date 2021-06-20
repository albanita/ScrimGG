package com.igalda.scrimgg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class verJugador extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ver_jugador);
        View rootView = getLayoutInflater().inflate(R.layout.activity_ver_jugador, frameLayout);
    }
}