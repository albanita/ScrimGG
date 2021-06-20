package com.igalda.scrimgg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.igalda.scrimgg.dom.Champ;
import com.igalda.scrimgg.pers.PersistenciaChamp;

import java.util.List;

public class StartingAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
    }

    @Override
    public void onBackPressed(){

    }


    public void goToLog(View view) {
        Intent intent = new Intent(getApplicationContext(),AuthActivity.class);
        startActivity(intent);
    }

    public void goToReg(View view) {
        Intent intent = new Intent(getApplicationContext(),regActivity.class);
        startActivity(intent);
    }

    public void goToProbar(View view) {
        Intent intent = new Intent(getApplicationContext(),allChamps.class);
        startActivity(intent);
    }
}