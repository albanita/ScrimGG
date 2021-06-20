package com.igalda.scrimgg.act.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.ImageDownloader;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.dom.Cuenta;
import com.igalda.scrimgg.dom.Usuario;
import com.igalda.scrimgg.neg.Negocio;

public class VerUsuario extends BaseActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private Negocio n = Negocio.getNegocio();

    private ImageView imFotoPerfil;
    private TextView lbNickname;
    private TextView lbNumNivel;
    private ProgressBar pbExperiencia;
    private TextView lbCuenta;
    private ImageView imRankSoloq;
    private TextView lbRankSoloq;
    private ImageView imRankFlexq;
    private TextView lbRankFlexq;

    private Usuario profileOwner;

    private Intent prevAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ver_usuario);
        View rootView = getLayoutInflater().inflate(R.layout.activity_ver_usuario, frameLayout);

        // Obtenemos el id del usuario al que pertenece el perfil y el objeto Usuario
        prevAct = getIntent();
        String idUser = prevAct.getStringExtra("user");
        profileOwner = n.nUsuario().buscarUsuario(idUser);
        Cuenta cuenta = n.nCuenta().getCuenta(profileOwner.getCuentaRiot());
        if (cuenta == null){
            cuenta = new Cuenta();
        }

        // Inicializamos las referencias a los componentes de la actividad
        imFotoPerfil = (ImageView) this.findViewById(R.id.imFotoPerfil);
        lbNickname = (TextView) this.findViewById(R.id.lbNickname);
        lbNumNivel = (TextView) this.findViewById(R.id.lbNumNivel);
        pbExperiencia = (ProgressBar) this.findViewById(R.id.pbExperiencia);
        lbCuenta = (TextView) this.findViewById(R.id.lbCuenta);
        imRankSoloq = (ImageView) this.findViewById(R.id.imRankSoloq);
        lbRankSoloq = (TextView) this.findViewById(R.id.lbRankSoloq);
        imRankFlexq = (ImageView) this.findViewById(R.id.imRankFlexq);
        lbRankFlexq = (TextView) this.findViewById(R.id.lbRankFlexq);

        // Inicializamos los valores de los componentes a los actuales del usuario al que pertenece el perfil
        initLabels(profileOwner, cuenta);
        initProgress(profileOwner);
        initImages(profileOwner, cuenta);

        // Comprobamos si el usuario actual es el mismo al que pertenece el perfil
        if (currentUser.getUid().equals(idUser)){
            // Cambiamos los valores de los strings de los botones
            Button btEquipos = (Button) this.findViewById(R.id.btEquipos);
            btEquipos.setText(R.string.btMisEquipos);
            Button btHistorial = (Button) this.findViewById(R.id.btHistorial);
            btHistorial.setText(R.string.btMiHistorial);
        } else {
            // Si no coinciden, no mostramos el botón "btModificarDatos"
            Button btModificarDatos = (Button) this.findViewById(R.id.btModificarDatos);
            btModificarDatos.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && requestCode == 0){ // Modifications
            boolean modif = data.getBooleanExtra("modif", false);
            String newNick = data.getStringExtra("newnick");
            String newFoto = data.getStringExtra("newfoto");
            String newCuenta = data.getStringExtra("newcuenta");
            if (modif){
                if (!newNick.equals(null)){
                    lbNickname.setText(newNick);
                    prevAct.putExtra("newnick", newNick);
                }
                if(!newFoto.equals(null)){
                    ImageDownloader.downloadTheImage(newFoto, imFotoPerfil);
                    prevAct.putExtra("newfoto", newFoto);
                }
                if(!newCuenta.equals(null)){
                    lbCuenta.setText(newCuenta);
                    prevAct.putExtra("newcuenta", newCuenta);
                }
            }
        }
    }

    // Lanza la actividad modificar_usuario
    public void modificarDatos(View view){
        // Creamos un intent y le añadimos los valores actuales del perfil
        Intent intent = new Intent(this, ModificarUsuario.class);

        intent.putExtra("foto", profileOwner.getImgPath());
        intent.putExtra("nick", profileOwner.getNickName());
        intent.putExtra("cuenta", profileOwner.getCuentaRiot());
        this.startActivityForResult(intent, 0);
    }

    // Lanza la actividad historial
    public void historial(View view){
        Intent intent = new Intent(this, null); // TODO : Crear actividad historial
        this.startActivity(intent);
    }

    // Lanza la actividad equipos
    public void equipos(View view){
        Intent intent = new Intent(this, null); // TODO : Crear actividad equipos
        this.startActivity(intent);
    }

    private void initLabels(Usuario user, Cuenta cuenta){
        lbNickname.setText(user.getNickName());

        if (user.getCuentaRiot().equals("")){
            lbCuenta.setText(R.string.accName_unspecified);
        } else {
            lbCuenta.setText(user.getCuentaRiot());
        }
        lbNumNivel.setText(String.valueOf(user.getNivelScrim()));

        if (cuenta.getRankSolo().equals("")){
            lbRankSoloq.setText(R.string.accRank_default);
        } else {
            lbRankSoloq.setText(cuenta.getRankSolo());
        }
        if (cuenta.getRankFlex().equals("")){
            lbRankFlexq.setText(R.string.accRank_default);
        } else {
            lbRankFlexq.setText(cuenta.getRankFlex());
        }


    }

    private void initImages(Usuario user, Cuenta cuenta){
        if(user.getImgPath().equals("")){
            imFotoPerfil.setImageResource(R.drawable.base_profile_pic);
        } else {
            ImageDownloader.downloadTheImage(user.getImgPath(), imFotoPerfil);
        }
        if(cuenta.getRankFlex().equals("")){
            imRankFlexq.setImageResource(R.drawable.unranked_acc_pic);
        } else {
            ImageDownloader.downloadTheImage(n.nCuenta().getRankImg(cuenta.getRankFlex()), imRankFlexq);
        }
        if(cuenta.getRankSolo().equals("")){
            imRankSoloq.setImageResource(R.drawable.unranked_acc_pic);
        } else {
            ImageDownloader.downloadTheImage(n.nCuenta().getRankImg(cuenta.getRankSolo()), imRankSoloq);
        }


    }

    private void initProgress(Usuario user){
        pbExperiencia.setMax(100);
        pbExperiencia.setProgress(user.getExpScrim());
    }
}