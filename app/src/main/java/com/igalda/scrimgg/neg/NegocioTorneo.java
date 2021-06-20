package com.igalda.scrimgg.neg;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.igalda.scrimgg.dom.Equipo;
import com.igalda.scrimgg.dom.Match;
import com.igalda.scrimgg.dom.Torneo;
import com.igalda.scrimgg.pers.PersistenciaEquipo;
import com.igalda.scrimgg.pers.PersistenciaTorneo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NegocioTorneo {
    public NegocioTorneo(){}

    /**
     * añade un nuevo torneo que no existe en la base de datos
     * @param t
     */
    public void altaTorneo(Torneo t){
        if(!PersistenciaTorneo.existeTorneo(t)){
            PersistenciaTorneo.altaTorneo(t);
        }
    }

    /**
     * @param t
     * @return borra el torneo t dado y devuelve TRUE; FALSE en caso contrario
     */
    public boolean bajaTorneo(Torneo t){
        if(PersistenciaTorneo.existeTorneo(t)){
            return PersistenciaTorneo.bajaTorneo(t);
        }
        else{
            return false;
        }
    }

    /**
     *
     * @param id
     * @return el torneo con el id dado o null si no existe en la base de datos
     */
    public Torneo buscarTorneo(String id){
        return PersistenciaTorneo.buscarTorneo(id);
    }

    /**
     *
     * @return un listado con todos los torneos de la base de datos
     */
    public List<Torneo> listaTorneos(){
        return PersistenciaTorneo.listaTorneos();
    }

    /**
     *
     * @param t
     * @return TRUE si existe el torneo en la base de datos; FALSE en caso contrario
     */
    public static boolean existeTorneo(Torneo t){
        return PersistenciaTorneo.existeTorneo(t);
    }

    /**
     * @param t
     * @return un map con el id del equipo junto con un booleano que indica si el equipo está eliminado o no del torneo
     */
    public  Map<String, Boolean> getEquipos(Torneo t){
        return PersistenciaTorneo.getEquipos(t);
    }

    /**
     * añade el equipo e al torneo t
     * @param t
     * @param e
     */
    public static void addEquipo(Torneo t, Equipo e){
        PersistenciaTorneo.addEquipo(t,e);
    }

    /**
     *
     * @param t
     * @param e
     * @return TRUE si existe el equipo e en el torneo t
     */
    public boolean existEquipo(Torneo t, Equipo e){
        return PersistenciaTorneo.existEquipo(t,e);
    }

    /**
     * actualiza el estado del equipo e (eliminado o no) en el torneo t
     * @param t
     * @param e
     * @param elimidado
     */
    public void updateEquipo(Torneo t, Equipo e, boolean elimidado){
        PersistenciaTorneo.updateEquipo(t,e,elimidado);
    }

    /**
     * añade el enfrentamiento match dado, al torneo t dado
     * @param t
     * @param match
     */
    public void addMatch(Torneo t, Match match){
        PersistenciaTorneo.addMatch(t,match);
    }

    /**
     *
     * @param t
     * @return un listado de enfrentamientos del torneo t dado
     */
    public List<Match> getMactches(Torneo t){
        return PersistenciaTorneo.getMactches(t);
    }

    /**
     * @param t
     * @param m
     * @return TRUE si existe el enfrentamiento m en el torneo t
     */
    public boolean existMatch(Torneo t, Match m){
        return PersistenciaTorneo.existMatch(t,m);
    }

    /**
     * actualiza el match dado en el torneo t
     * @param t
     * @param matchToUpdate
     */
    public void updateMatch(Torneo t, Match matchToUpdate){
        PersistenciaTorneo.updateMatch(t,matchToUpdate);
    }

    /**
     * @param t
     * @param match
     * @return borra el match dado del torneo t y devuelve TRUE; FALSE en caso contrario
     */
    public boolean deleteMatch(Torneo t, Match match){
        return PersistenciaTorneo.deleteMatch(t,match);
    }

    /**
     *
     * @param t
     * @param m
     * @return el equipo azul
     */
    public Equipo getBlueTeam(Torneo t, Match m){
        return PersistenciaTorneo.getBlueTeam(t,m);
    }

    /**
     *
     * @param t
     * @param m
     * @return el equipo rojo
     */
    public Equipo getRedTeam(Torneo t, Match m){
        return PersistenciaTorneo.getRedTeam(t,m);
    }
}
