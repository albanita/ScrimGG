package com.igalda.scrimgg.pers;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.igalda.scrimgg.dom.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author albanita
 */
public class PersistenciaTorneo {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String DBOK = "DB Confirmation";
    private static String DBError = "DB ERROR";

    private static String torneos = "torneos";
    private static String equipos = "equipos";
    private static String enfrentamientos = "enfrentamientos";

    /**
     * añade un nuevo torneo que no existe en la base de datos
     * @param t
     */
    public static void altaTorneo(Torneo t){
        Map<String, Object> torneoAdd = new HashMap<String, Object>();
        torneoAdd.put("idTorneo", t.getId());
        torneoAdd.put("nombreTorneo", t.getNombre());
        if(!existeTorneo(t)){
            Task<Void> aux = db.collection(torneos).document(t.getId()).set(torneoAdd)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(DBOK, "The tournament has been added");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(DBError, "Error when adding a new tournament");
                        }
                    });
        }
        else{
            Log.d(DBError, "The tournament exists on the DB");
        }
    }

    /**
     * @param t
     * @return borra el torneo t dado y devuelve TRUE; FALSE en caso contrario
     */
    public static boolean bajaTorneo(Torneo t){
        boolean res = false;
        if(existeTorneo(t)){
            Task<Void> aux = db.collection(torneos).document(t.getId()).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(DBOK, "Success on deleteing the tournament");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(DBError, "Failure on deleteing the tournament");
                        }
                    });
            while (!aux.isComplete()){}
            if(aux.isSuccessful()){
                // todo: borrar el torneo de los equipos en los que está
                res = true;
            }
        }
        else{
            Log.d(DBError, "The tournament exists on the DB");
        }
        return res;
    }

    /**
     *
     * @param id
     * @return el torneo con el id dado o null si no existe en la base de datos
     */
    public static Torneo buscarTorneo(String id){
        Torneo t = null;
            Task<DocumentSnapshot> aux = db.collection(torneos).document(id).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Log.d(DBOK, "Tournament completly read from the DB");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(DBError, "Error while reading the tournament from the DB");
                        }
                    });
            while (!aux.isComplete()){}
            if(aux.getResult().exists()){
                DocumentSnapshot ds = aux.getResult();
                String nombre = (String) ds.get("nombreTorneo");
                t = new Torneo(id, nombre);
            }
        return t;
    }

    /**
     *
     * @return un listado con todos los torneos de la base de datos
     */
    public static List<Torneo> listaTorneos(){
        List<Torneo> misTorneos = new ArrayList<Torneo>();
        Task<QuerySnapshot> aux = db.collection(torneos).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(DBOK, "Success on getting all the tournaments");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(DBError, "Failure on getting all the tournaments");
                    }
                });
        while (!aux.isComplete()){}
        if(!aux.getResult().isEmpty()){
            for(DocumentSnapshot ds: aux.getResult().getDocuments()){
                String id = (String) ds.get("idTorneo");
                String nombre = (String) ds.get("nombreTorneo");
                misTorneos.add(new Torneo(id,nombre));
            }
        }
        return misTorneos;
    }

    /**
     *
     * @param t
     * @return TRUE si existe el torneo en la base de datos; FALSE en caso contrario
     */
    public static boolean existeTorneo(Torneo t){
        boolean res = false;
        Task<DocumentSnapshot> aux = db.collection(torneos).document(t.getId()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d(DBOK, "Succesfully checked wether the tournament exists on the DB");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(DBError, "Error while checking if the tournament exists on the DB");
                    }
                });
        while (!aux.isComplete()){}
        if(aux.getResult().exists()){
            res = true;
        }
        return  res;
    }


    /**
     * @param t
     * @return un map con el id del equipo junto con un booleano que indica si el equipo está eliminado o no del torneo
     */
    public static Map<String, Boolean> getEquipos(Torneo t){
        Map<String, Boolean> res = new HashMap<>();
        Task<QuerySnapshot> aux = db.collection(torneos).document(t.getId()).collection(equipos).get();
        while (!aux.isComplete()){}
        if(!aux.getResult().isEmpty()){
            for(DocumentSnapshot ds: aux.getResult().getDocuments()){
                Task<DocumentSnapshot> aux1 = db.collection(torneos).document(t.getId()).collection(equipos).document(ds.getId()).get();
                while (!aux1.isComplete()){}
                if(aux1.getResult().exists()){
                    String idEquipo = (String) ds.get("idEquipo");
                    Boolean eliminado = ds.getBoolean("eliminado");
                    if(PersistenciaEquipo.exist(new Equipo("", idEquipo, "", privacidadEquipo.PRIVADO))){
                        res.put(idEquipo, eliminado);
                    }
                }
            }
        }
        return res;
    }

    /**
     * añade el equipo e al torneo t
     * @param t
     * @param e
     */
    public static void addEquipo(Torneo t, Equipo e){
        Map<String, Object> teamAdd = new HashMap<String, Object>();
        teamAdd.put("idEquipo", e.getTid());
        teamAdd.put("eliminado", new Boolean(false));
        if(existeTorneo(t) && PersistenciaEquipo.exist(e) && !existEquipo(t,e)){
            Task<Void> aux = db.collection(torneos).document(t.getId()).collection(equipos).document(e.getTid()).set(teamAdd)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(DBOK, "Team added to the tournament");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(DBError, "Error while adding the team to the tournament");
                        }
                    });
        }
        else{
            Log.d(DBError, "The tournament or the team, does not exists on the DB");
        }
    }

    /**
     *
     * @param t
     * @param e
     * @return TRUE si existe el equipo e en el torneo t
     */
    public static boolean existEquipo(Torneo t, Equipo e){
        boolean res = false;
        if(existeTorneo(t) && PersistenciaEquipo.exist(e)){
            Task<DocumentSnapshot> aux = db.collection(torneos).document(t.getId()).collection(equipos).document(e.getTid()).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Log.d(DBOK, "Success on checking wether the tournament and the team exists on the DB");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(DBError, "Failure on checking wether the tournament and the team exists on the DB");
                        }
                    });
            while (!aux.isComplete()){}
            if(aux.getResult().exists()){
                res = true;
            }
        }
        else{
            Log.d(DBError, "The tournament or the team does not exists on the DB");
        }
        return res;
    }

    /**
     * actualiza el estado del equipo e (eliminado o no) en el torneo t
     * @param t
     * @param e
     * @param elimidado
     */
    public static void updateEquipo(Torneo t, Equipo e, boolean elimidado){
        if(existEquipo(t,e)){
            db.collection(torneos).document(t.getId()).collection(equipos).document(e.getTid()).update(
                    "idEquipo", e.getTid(),
                    "eliminado", elimidado
            )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(DBOK, "Update complete");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(DBError, "Error while making the update on the tournaments team");
                        }
                    });
        }
        else{
            Log.d(DBError, "The team does not exists in the tournament");
        }
    }

    /**
     * añade el enfrentamiento match dado, al torneo t dado
     * @param t
     * @param match
     */
    public static void addMatch(Torneo t, Match match){
            Map<String, Object> matchToAdd = new HashMap<String, Object>();
            matchToAdd.put("equipoAzul", match.getIdAzul());
            matchToAdd.put("equipoRojo", match.getIdRojo());
            matchToAdd.put("fecha", match.getFecha());
            matchToAdd.put("resultado", match.getResultado());

            if(existeTorneo(t) && !existMatch(t,match)){
                Task<Void> aux = db.collection(torneos).document(t.getId()).collection(enfrentamientos).document(match.getId()).set(matchToAdd)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(DBOK, "The new match has been aded to the tournament");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(DBError, "An error ocurred while adding the nue match to the tournament");
                            }
                        });
            }
        else{
            Log.d(DBError, "The pair match - tournament, exists on the DB");
        }
    }

    /**
     *
     * @param t
     * @return un listado de enfrentamientos del torneo t dado
     */
    public static List<Match> getMactches(Torneo t){
        List<Match> misEnfrentamientos = new ArrayList<Match>();
        if(existeTorneo(t)){
            Task<QuerySnapshot> aux = db.collection(torneos).document(t.getId()).collection(enfrentamientos).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            Log.d(DBOK, "Done reading all the matches");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(DBError, "Error while reading the matches");
                        }
                    });
            while(!aux.isComplete()){}
            if(!aux.getResult().isEmpty()){
                for(DocumentSnapshot ds: aux.getResult().getDocuments()){
                    String id = (String) ds.getId();
                    String equipoAzul = (String) ds.get("equipoAzul");
                    String equipoRojo = (String) ds.get("equipoRojo");
                    Date fechaHora = ds.getDate("fecha");
                    String ganador = (String) ds.get("resultado");
                    misEnfrentamientos.add(new Match(id, equipoAzul, equipoRojo, fechaHora, ganador, matchType.Torneo));
                }
            }
        }
        return misEnfrentamientos;
    }

    /**
     * @param t
     * @param m
     * @return TRUE si existe el enfrentamiento m en el torneo t
     */
    public static boolean existMatch(Torneo t, Match m){
        boolean res = false;
        if(existeTorneo(t)){
            Task<DocumentSnapshot> aux = db.collection(torneos).document(t.getId()).collection(enfrentamientos).document(m.getId()).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Log.d(DBOK, "The match might exist on the DB");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(DBError, "An error ocurred when checking if the match exist in the tournament");
                        }
                    });
            while (!aux.isComplete()){}
            if(aux.getResult().exists()){
                res = true;
            }
        }
        else{
            Log.d(DBError, "The tournament does not exists on the DB");
        }
        return res;
    }

    /**
     * actualiza el match dado en el torneo t
     * @param t
     * @param matchToUpdate
     */
    public static void updateMatch(Torneo t, Match matchToUpdate){
        if(existMatch(t,matchToUpdate)){
            db.collection(torneos).document(t.getId()).collection(enfrentamientos).document(matchToUpdate.getId()).update(
                    "equipoAzul", matchToUpdate.getIdAzul(),
                    "equipoRojo", matchToUpdate.getIdRojo(),
                    "fecha", matchToUpdate.getFecha(),
                    "resultado", matchToUpdate.getResultado()
            ).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "The match in the tournament ahs been updated");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "An error ocurred when updating the match in the tournament");
                }
            });
        }
        else{
            Log.d(DBError, "The match does not belong to the tournament");
        }
    }

    /**
     *
     * @param t
     * @param match
     * @return borra el match dado del torneo t y devuelve TRUE; FALSE en caso contrario
     */
    public static boolean deleteMatch(Torneo t, Match match){
        boolean res = false;
        if(existMatch(t,match)){
            Task<Void> aux = db.collection(torneos).document(t.getId()).collection(enfrentamientos).document(match.getId()).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(DBOK, "The match has been deleted from the tournament");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(DBError, "An error ocurred when deleteing the match from the tournament");
                        }
                    });
            while (!aux.isComplete()){}
            if(aux.isSuccessful()){
                res = true;
            }
        }
        else{
            Log.d(DBError, "This match is not in this league");
        }
        return res;
    }

    /**
     *
     * @param t
     * @param m
     * @return el equipo azul
     */
    public static Equipo getBlueTeam(Torneo t, Match m){
        Equipo e = null;
        if(existMatch(t,m)){
            e = PersistenciaEquipo.buscarEquipoPorID(m.getIdAzul());
        }
        return e;
    }

    /**
     *
     * @param t
     * @param m
     * @return el equipo rojo
     */
    public static Equipo getRedTeam(Torneo t, Match m){
        Equipo e = null;
        if(existMatch(t,m)){
            e = PersistenciaEquipo.buscarEquipoPorID(m.getIdRojo());
        }
        return e;
    }
}
