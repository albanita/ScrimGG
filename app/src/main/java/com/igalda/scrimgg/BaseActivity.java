package com.igalda.scrimgg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.igalda.scrimgg.act.altaEquipo.AltaEquipo;
import com.igalda.scrimgg.act.buscadorEnfrentamientos.BuscadorEnfrentamientos;
import com.igalda.scrimgg.act.buscadorEquipo.BuscadorEquipo;
import com.igalda.scrimgg.act.infoCualquierEquipoPublico.InfoCualquierEquipoPublico;
import com.igalda.scrimgg.act.infoEquiposUsuario.InfoEquiposUsuario;
import com.igalda.scrimgg.act.infoLigasProfesionales.ListadoLigasProfesionales;
import com.igalda.scrimgg.act.usuario.VerUsuario;
import com.igalda.scrimgg.dom.Usuario;
import com.igalda.scrimgg.neg.Negocio;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected FrameLayout frameLayout;
    private Context context;
    private Toolbar toolbar;
    public TextView txt_username, txt_cuenta;
    public ImageView img_menuOption, image_profile;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private Negocio n = Negocio.getNegocio();

    private DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        context=this;
        initView();
        frameLayout = (FrameLayout) findViewById(R.id.container);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        img_menuOption = findViewById(R.id.img_menuOption);


        img_menuOption.setBackgroundResource(R.drawable.ic_menu_home);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        image_profile = headerview.findViewById(R.id.image_profile);
        txt_username = headerview.findViewById(R.id.txt_username);
        txt_cuenta = headerview.findViewById(R.id.txt_cuenta);

        Usuario user = n.nUsuario().buscarUsuario(currentUser.getUid());
        txt_username.setText(user.getNickName());
        if(!user.getCuentaRiot().equals("")){
            txt_cuenta.setText(user.getCuentaRiot());
        } else {
            txt_cuenta.setText(R.string.accName_unspecified);
        }

        if(user.getImgPath().equals("")){
            image_profile.setImageResource(R.drawable.base_profile_pic);
        } else {
            ImageDownloader.downloadTheImage(user.getImgPath(), image_profile);
        }


        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        img_menuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.nav_home) {
            intent=new Intent(this, principal.class);
            startActivity(intent);

        } else if (id == R.id.nav_profile) {
            intent=new Intent(this, VerUsuario.class);
            intent.putExtra("user", currentUser.getUid());
            startActivityForResult(intent, 0);

        } else if (id == R.id.nav_team) {
            // TODO : arreglar. Falla.
            intent = new Intent(this, InfoEquiposUsuario.class);
            startActivity(intent);

        } else if (id == R.id.nav_ligasP) {
            intent = new Intent(this, ListadoLigasProfesionales.class);
            startActivity(intent);

        }else if(id == R.id.nav_infoEquipos){
            intent = new Intent(this, InfoCualquierEquipoPublico.class);
            startActivity(intent);

        } else if (id == R.id.nav_newteam){
            intent = new Intent(this, AltaEquipo.class);
            startActivity(intent);

        }else if(id == R.id.nav_busqEquip){
            intent = new Intent(this, BuscadorEquipo.class);
            startActivity(intent);

        }else if(id == R.id.nav_busqEnfr) {
            intent = new Intent(this, BuscadorEnfrentamientos.class);
            startActivity(intent);

        }else if (id == R.id.nav_cerrar) {
            currentUser = null;
            intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && requestCode == 0){
            String newNick = data.getStringExtra("newnick");
            String newFoto = data.getStringExtra("newfoto");
            String newCuenta = data.getStringExtra("newcuenta");
            if(!newNick.equals(null)){
                txt_username.setText(newNick);
            }
            if(!newFoto.equals(null)){
                // TODO : implementar
            }
            if(!newCuenta.equals(null)){
                // TODO : es necesario??
            }
        }
    }
}
