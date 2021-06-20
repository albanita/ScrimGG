package com.igalda.scrimgg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class showTeam extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_show_team);
        View rootView = getLayoutInflater().inflate(R.layout.activity_show_team, frameLayout);
    }

    public void goToSettings(View view) {
        Intent intent = new Intent(getApplicationContext(),teamSettings.class);
        startActivity(intent);
    }
}