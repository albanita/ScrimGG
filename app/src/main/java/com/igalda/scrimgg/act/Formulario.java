package com.igalda.scrimgg.act;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.R;

public class Formulario extends BaseActivity {

    private TextView tvFormFieldName;
    private EditText etFormNewText;

    private Intent intent;

    private int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_formulario);
        View rootView = getLayoutInflater().inflate(R.layout.activity_formulario, frameLayout);

        intent = getIntent();
        String label = intent.getStringExtra("label");
        String advertencia = intent.getStringExtra("advert");
        String textSize = intent.getStringExtra("textSize");
        code = Integer.parseInt(intent.getStringExtra("code"));

        // Inicializamos las referencias a los campos del formulario
        tvFormFieldName = (TextView) this.findViewById(R.id.tvFormFieldName);
        etFormNewText = (EditText) this.findViewById(R.id.etFormNewText);

        // Establecemos el valor del TextView al obtenido del intent
        tvFormFieldName.setText(label);
        if(textSize != null){
            tvFormFieldName.setTextSize(Integer.parseInt(textSize));
        }

        TextView lbNota = (TextView) this.findViewById(R.id.tvFormWarningMsg);
        if (advertencia != null){
            // Si se ha incluido una advertencia en el intent, se muestra en el TextView correspondiente
            lbNota.setText(advertencia);
        } else {
            // Si no, se oculta el TextView.
            lbNota.setVisibility(View.GONE);
        }
    }

    // Al pulsar el boton Aceptar, se obtiene el nuevo valor introducido y se finaliza la actividad
    public void formSubmit(View view){
        String newText = etFormNewText.getText().toString();
        Log.d("newText", newText);
        if (!newText.equals(null) && !newText.equals("")){
            // Si se ha introducido texto en el campo
            intent.putExtra("nuevo", newText);
            setResult(code, intent);
        }
        this.finish();
    }

    public void formCancel(View view) {
        this.finish();
    }
}