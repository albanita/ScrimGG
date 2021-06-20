package com.igalda.scrimgg.act.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.ImageDownloader;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.act.Formulario;
import com.igalda.scrimgg.dom.Usuario;
import com.igalda.scrimgg.neg.Negocio;

public class ModificarUsuario extends BaseActivity {

    // Variable booleana que indica si ha habido algún cambio
    private static boolean MODIF = false;

    private Negocio n = Negocio.getNegocio();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();

    private Usuario usr;

    private TextView lbNicknameModif;
    private TextView lbCuentaModif;
    private TextView lbEmailModif;
    private ImageView imFotoPerfilModif;

    private Intent prevAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_modificar_usuario);
        View rootView = getLayoutInflater().inflate(R.layout.activity_modificar_usuario, frameLayout);

        MODIF = false;

        lbNicknameModif = (TextView) findViewById(R.id.lbNicknameModif);
        lbCuentaModif = (TextView) findViewById(R.id.lbCuentaModif);
        lbEmailModif = (TextView) findViewById(R.id.lbEmailModif);
        imFotoPerfilModif = (ImageView) this.findViewById(R.id.imFotoPerfilModif);

        usr = n.nUsuario().buscarUsuario(currentUser.getUid());

        // Obtenemos los datos pasados a través del intent
        prevAct = getIntent();
        String foto = prevAct.getStringExtra("foto");
        String nickname = prevAct.getStringExtra("nick");
        String cuenta = prevAct.getStringExtra("cuenta");

        // Inicializamos los campos con los valores actuales pasados a través del intent
        if (foto != null && !foto.equals("")){
            ImageDownloader.downloadTheImage(foto, imFotoPerfilModif);
        } else {
            imFotoPerfilModif.setImageResource(R.drawable.base_profile_pic);
        }
        if (nickname != null){
            lbNicknameModif.setText(nickname);
        }
        if (cuenta != null && !cuenta.equals("")){
            lbCuentaModif.setText(cuenta);
        } else {
            lbCuentaModif.setText(R.string.accName_unspecified);
        }

        // Obtenemos e inicializamos los campos de datos privados del usuario
        // TODO : Añadir nombre, apellidos, telefono ???
        lbEmailModif.setText(usr.getEmail());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            String nuevoValor = data.getStringExtra("nuevo");
            if(requestCode == 0){ // Change username
                if (nuevoValor!=null){
                    lbNicknameModif.setText(nuevoValor);
                    MODIF = true;
                }
            } else if (requestCode == 1){ // Change riot account
                if (nuevoValor!=null){
                    lbCuentaModif.setText(nuevoValor);
                    MODIF = true;
                }
            } else if (requestCode == 2){ // Change email
                if (nuevoValor!=null){
                    lbEmailModif.setText(nuevoValor);
                    MODIF = true;
                }
            } else if (requestCode == 3){ // Change profile pic
                // TODO : implementar
            }
        }
    }

    public void cambiarNick(View view){
        // TODO : comprobar que no se haya cambiado el valor de este campo en el ultimo mes.
        Intent nextAct = new Intent(ModificarUsuario.this, Formulario.class);
        nextAct.putExtra("label", "Nuevo Nickname:");
        nextAct.putExtra("advert", "Advertencia:\nSólo puedes cambiar tu Nickname como máximo una vez al mes.");
        nextAct.putExtra("code", String.valueOf(0));
        this.startActivityForResult(nextAct, 0);
    }

    public void cambiarCuenta(View view){
        // TODO : comprobar que no se haya cambiado el valor de este campo en el ultimo mes.
        // TODO : diseñar mecanismo para comprobar que la cuenta pertenece al usuario.
        Intent nextAct = new Intent(ModificarUsuario.this, Formulario.class);
        nextAct.putExtra("label", "Nombre de la nueva cuenta de Riot:");
        nextAct.putExtra("advert", "Advertencia:\nSólo puedes cambiar tu Cuenta de Riot como máximo una vez al mes.");
        nextAct.putExtra("textSize", String.valueOf(15));
        nextAct.putExtra("code", String.valueOf(1));
        this.startActivityForResult(nextAct, 1);
    }

    public void cambiarEmail(View view){
        Intent nextAct = new Intent(ModificarUsuario.this, Formulario.class);
        nextAct.putExtra("label", "Nuevo email:");
        nextAct.putExtra("code", String.valueOf(2));
        this.startActivityForResult(nextAct, 2);
    }

    public void cambiarFoto(View view){
        // requestCode 3
        // TODO : implementar
    }

    public void guardarCambios(View view){
        if(MODIF){
            // Si ha habido modificacion/es
            // TODO : obtener path de la nueva imagen y asignarsela a usr
            usr.setNickName(lbNicknameModif.getText().toString());
            usr.setCuentaRiot(lbCuentaModif.getText().toString());
            usr.setEmail(lbEmailModif.getText().toString());

            // Persistimos el usuario modificado
            n.nUsuario().modificarUsuario(usr);

            // Devolvemos los cambios a la actividad anterior
            prevAct.putExtra("modif", MODIF);
            prevAct.putExtra("newfoto", usr.getImgPath());
            prevAct.putExtra("newnick", usr.getNickName());
            prevAct.putExtra("newcuenta", usr.getCuentaRiot());
            setResult(0, prevAct);
        }
        this.finish();
    }
}