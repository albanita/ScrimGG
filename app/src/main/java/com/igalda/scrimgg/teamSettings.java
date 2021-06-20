package com.igalda.scrimgg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class teamSettings extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_team_settings);
        View rootView = getLayoutInflater().inflate(R.layout.activity_team_settings, frameLayout);
    }
}