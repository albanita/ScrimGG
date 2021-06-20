 package com.igalda.scrimgg;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.igalda.scrimgg.R;

public class LoadScreen {

    Activity act;
    AlertDialog dial;

    public LoadScreen(Activity act){
        this.act = act;
    }

    public void startLoadScreen (){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.act);

        LayoutInflater inflater = this.act.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loadscreen, null));
        builder.setCancelable(true);

        this.dial = builder.create();
        this.dial.show();
    }

    public void stopLoadScreen(){
        this.dial.dismiss();
    }
}
