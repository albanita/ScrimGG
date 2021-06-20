package com.igalda.scrimgg.act.buscadorEquipo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.ImageDownloader;
import com.igalda.scrimgg.R;

import com.igalda.scrimgg.dom.*;
import com.igalda.scrimgg.neg.Negocio;
import com.igalda.scrimgg.neg.NegocioEquipo;
import com.igalda.scrimgg.neg.NegocioUsuario;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


public class ResultadoEquipo extends BaseActivity {

    private Equipo equipo;
    private FirebaseAuth mAuth;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_resultado_equipo);
        View rootView = getLayoutInflater().inflate(R.layout.activity_resultado_equipo, frameLayout);

        mAuth = FirebaseAuth.getInstance();

        this.rellenarDatos();
    }

    private void rellenarDatos(){
        Intent intent = getIntent();
        this.equipo = (Equipo) intent.getExtras().get("Equipo");
        NegocioUsuario nu = new NegocioUsuario();
        NegocioEquipo ne = new NegocioEquipo();

        TextView txtNombreEquipo = (TextView) findViewById(R.id.lblNombreEquipo);
        TextView txtNombreCapitan = (TextView) findViewById(R.id.lblNombreCapitan);
        ImageView logo = (ImageView) findViewById(R.id.logoEquipo);

        if(!this.equipo.getImgPath().equals("")){
            ImageDownloader.downloadTheImage(this.equipo.getImgPath(), logo);
        }

        txtNombreEquipo.setText(txtNombreEquipo.getText().toString() + " " + this.equipo.getTid());
        String idCapitan = this.equipo.getIdCapitan();

        Usuario capitan = nu.buscarUsuario(idCapitan);
        txtNombreCapitan.setText(txtNombreCapitan.getText().toString() + " " + capitan.getNickName());

        this.usuario = nu.buscarUsuario(this.mAuth.getCurrentUser().getUid());
        if(ne.getPlayers(this.equipo).contains(this.usuario)){
            Button btnEntrarEquipo = (Button) findViewById(R.id.btnEntrarEquipo);
            btnEntrarEquipo.setEnabled(false);
        }
    }

    public void verMiembrosEquipo(View view) {
        NegocioEquipo ne = new NegocioEquipo();
        List<Usuario> miembros = ne.getPlayers(this.equipo);
        String nombresMiembros = "";
        for(int i=0; i<miembros.size(); i++){
            nombresMiembros += miembros.get(i).getNickName() + "\n";
        }

        Bundle bundle = new Bundle();
        bundle.putString("Nombre", "Miembros del equipo " + this.equipo.getTid());
        bundle.putString("Datos", nombresMiembros);

        Intent intent = new Intent(this, VistaDatosEquipo.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void verLigas(View view) {
        NegocioEquipo ne = new NegocioEquipo();
        List<Liga> ligas = ne.getLigas(this.equipo.getTid());
        String ligasEquipo = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");

        for(Liga l:ligas){
            ligasEquipo = "Nombre Liga: " + l.getNombre() + "\n";
            List<Equipo> equipos = Negocio.getNegocio().nLiga().getEquipos(l);
            Map<String, Integer> clasificacion = Negocio.getNegocio().nLiga().clasificacion(l.getId());

            ligasEquipo += "Equipos Participantes: \n";
            for(int j=0; j<equipos.size(); j++){
                int h = j+1;
                ligasEquipo += "    "+h+". "+equipos.get(j).getTid() + "\nPuntos: " + clasificacion.get(equipos.get(j).getTid()) +"\n###############################"+ "\n\n";
            }

            ligasEquipo += "Enfrentamientos de la liga: \n";
            List<Match> enfrentamientos = Negocio.getNegocio().nLiga().getMatches(l);
            for(Match m: enfrentamientos){
                ligasEquipo += "    ID Equipo azul: " + m.getIdAzul() + "\n";
                ligasEquipo += "    ID Equipo rojo: " + m.getIdRojo() + "\n";
                ligasEquipo += "    Fecha: " + sdf.format(m.getFecha()) + "\n";
                if(!m.getResultado().equals("")){
                    ligasEquipo += "    Id Equipo ganador: " + m.getResultado() + "\n";
                }
                ligasEquipo += "***************************************************\n\n";
            }

            ligasEquipo += "Fecha Inicio: " + sdf.format(l.getFechaInicio()) + "\n";
            ligasEquipo += "Fecha Final: " + sdf.format(l.getFechaFinal());
            ligasEquipo += "\n-------------------------------------------------------\n\n";
        }

        Bundle bundle = new Bundle();
        bundle.putString("Nombre", "Ligas del equipo " + this.equipo.getTid());
        bundle.putString("Datos", ligasEquipo);

        Intent intent = new Intent(this, VistaDatosEquipo.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void verTorneos(View view) {
        NegocioEquipo ne = new NegocioEquipo();

        List<Torneo> torneos = ne.getTorneos(this.equipo.getTid());
        String torneosEquipo = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");


        for(Torneo t: torneos){
            Map<String, Boolean> clasificacion = Negocio.getNegocio().nTorneo().getEquipos(t);
            torneosEquipo += "Nombre torneo: " + t.getNombre() + "\n";

            // equipos del torneo
            Object [] ids = clasificacion.keySet().toArray();
            torneosEquipo+="Equipos participantes:\n";
            for(Object i: ids){
                String id = (String) i;
                if(ne.existeEquipo(id)){
                    Equipo e = ne.buscarEquipoPorID(id);
                    boolean eliminado = clasificacion.get(id).booleanValue();
                    if(eliminado){
                        torneosEquipo+="Nombre equipo: " + e.getTid() + " ELIMINADO\n";
                    }
                    else{
                        torneosEquipo+="Nombre equipo: " + e.getTid() + " NO ELIMINADO\n";
                    }
                }
            }

            // enfrentamientos del torneo
            List<Match> enfrentamientos = Negocio.getNegocio().nTorneo().getMactches(t);
            for(Match m: enfrentamientos){
                torneosEquipo+="\nEnfrentamientos del torneo:\n";
                torneosEquipo+="    Equipo azul: " + Negocio.getNegocio().nTorneo().getBlueTeam(t,m).getTid() + "\n";
                torneosEquipo+="    Equipo rojo: " + Negocio.getNegocio().nTorneo().getRedTeam(t,m).getTid()+"\n";
                torneosEquipo+="    Fecha: " + sdf.format(m.getFecha()) + "\n";
                torneosEquipo+="    Resultado: " + m.getResultado()+"\n\n";
            }
        }

        Bundle bundle = new Bundle();
        bundle.putString("Nombre", "Torneos del equipo " + this.equipo.getTid());
        bundle.putString("Datos", torneosEquipo);

        Intent intent = new Intent(this, VistaDatosEquipo.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void unirmeAlEquipo(View view) {
        Negocio.getNegocio().nEquipo().anadirJugador(this.equipo, this.usuario, roles.ADC);
        Toast.makeText(this, "Se te ha añadido al equipo!", Toast.LENGTH_SHORT).show();
    }
}