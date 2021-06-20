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
public class PersistenciaLiga {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String DBOK = "DB Confirmation";
    private static String DBError = "DB ERROR";

    /**
     * da de alta una liga en la base de datos, si esta no existe
     * @param l
     */
    public static void altaLiga(Liga l){
        Map<String, Object> ligaAdd = new HashMap<String, Object>();
        ligaAdd.put("idLiga", l.getId());
        ligaAdd.put("nombreLiga", l.getNombre());
        ligaAdd.put("fechaInicio", l.getFechaInicio());
        ligaAdd.put("fechaFinal", l.getFechaFinal());
        // todo: ¿que hacemos con ganador?

        if(!exists(l)){
            Task<Void> league = db.collection("ligas").document(l.getId()).set(ligaAdd).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "League added to the DB");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Can't add the league to the database");
                }
            });
        }
        else{
            Log.d(DBOK, "The league alredy exists on the DB");
        }
    }

    /**
     *
     * @param liga
     * @return da de baja la liga pasada como parametro y devuelve TRUE; FALSE en caso contrario.
     * Además, borra del equipo de la liga, la misma liga
     */
    public static boolean bajaLiga(Liga liga){
        boolean res = false;
        if(exists(liga)) {
            Task<Void> aux = db.collection("ligas").document(liga.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "The league has been sucesfully removed");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Error while removing the league");
                }
            });
            while (!aux.isComplete()){}
            if(aux.isSuccessful()){
                List<Equipo> equipos = PersistenciaLiga.getEquipos(liga);
                for(Equipo e: equipos){
                    PersistenciaEquipo.deleteLeague(e, liga);
                }
                res = true;
            }
        }
        else{
            Log.d(DBError, "The league don't exist on the DB");
        }
        return res;
    }

    /**
     *
     * @param idLiga
     * @return la liga con el id pasado como parametro si existe; si no existe, devuelve null
     */
    public static Liga buscarLiga(String idLiga){
        Liga liga = null;
        Task<DocumentSnapshot> aux = db.collection("ligas").document(idLiga).get();
        while(!aux.isComplete()){}
        if(aux.getResult().exists()){
            DocumentSnapshot ds = aux.getResult();
            String nombreLiga = (String) ds.get("nombreLiga");
            Date fechaInicio = ds.getDate("fechaInicio");
            Date fechaFinal = ds.getDate("fechaFinal");
            liga = new Liga(idLiga, nombreLiga, fechaInicio, fechaFinal);
        }
        return liga;
    }

    /**
     *
     * @return un listado con todas las ligas de la base de datos
     */
    public static List<Liga> listaLigas(){
        List<Liga> ligas = new ArrayList<Liga>();
        Task<QuerySnapshot> aux = db.collection("ligas").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d(DBOK, "I have downloaded all the leagues from the database");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(DBError, "Error while reading the leagues from the databse");
            }
        });
        while (!aux.isComplete()){}
        if(!aux.getResult().isEmpty()){
            for(DocumentSnapshot ds: aux.getResult().getDocuments()){
                String id = (String) ds.getId();
                String nombreLiga = (String) ds.get("nombreLiga");
                Date fechaInicio = ds.getDate("fechaInicio");
                Date fechaFinal = ds.getDate("fechaFinal");
                ligas.add(new Liga(id, nombreLiga,fechaInicio,fechaFinal));
            }
        }
        return ligas;
    }

    /**
     * @param liga
     * @return true si existe la liga dada; false en caso contrario
     */
    public static boolean exists(Liga liga){
        boolean res = false;
        Task<DocumentSnapshot> aux = db.collection("ligas").document(liga.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d(DBOK, "The league exists on the DB");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(DBError, "Error while checking whether the league exists on the DB");
            }
        });
        while (!aux.isComplete()){}
        if(aux.getResult().exists()){
            res = true;
        }
        return res;
    }

    /**
     *
     * @param liga
     * @return el listado de equipos de una liga
     */
    public static List<Equipo> getEquipos (Liga liga) {
        List<Equipo> equipos = new ArrayList<Equipo>();
        Task<QuerySnapshot> aux = db.collection("ligas").document(liga.getId()).collection("equipos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d(DBOK, "I've got all the teams from the league");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(DBError, "Error while reading the teams from the league");
            }
        });
        while (!aux.isComplete()){}
        if(!aux.getResult().isEmpty()){
            for(DocumentSnapshot ds: aux.getResult().getDocuments()){
                String id = ds.getId();
                Equipo e = PersistenciaEquipo.buscarEquipoPorID(id);
                equipos.add(e);
            }
        }
        return equipos;
    }


    /**
     *
     * @param idLiga
     * @return map con los ids de los equipos de una liga y sus puntos.
     */
    public static HashMap<String, Integer> getEquiposPuntos (String idLiga) {
        HashMap<String, Integer> res = new HashMap<String, Integer>();
        Task<QuerySnapshot> aux = db.collection("ligas").document(idLiga).collection("equipos").get();
        while (!aux.isComplete()){ }
        if(!aux.getResult().isEmpty()){
            for(DocumentSnapshot ds: aux.getResult().getDocuments()){
                Task<DocumentSnapshot> aux1 = db.collection("ligas").document(idLiga).collection("equipos").document(ds.getId()).get();
                while (!aux1.isComplete()){}
                if(aux1.getResult().exists()){
                    String idEquipo = aux1.getResult().getId();
                    Integer puntos = Integer.valueOf(aux1.getResult().get("puntuacion").toString());
                    if(PersistenciaEquipo.exist(new Equipo("", idEquipo, "", privacidadEquipo.PRIVADO))){
                        res.put(idEquipo, puntos);
                    }
                }
            }
        }
        return res;
    }

    /**
     * Enfrentamientos (Match) de la liga
     * */

    /**
     * añade un enfrentamiento (Match) a la liga dada
     * @param liga
     * @param match
     */
    public static void addMatch(Liga liga, Match match){
        Map<String, Object> matchAdd = new HashMap<String, Object>();
        matchAdd.put("equipoAzul", match.getIdAzul());
        matchAdd.put("equipoRojo", match.getIdRojo());
        matchAdd.put("fechaHora", match.getFecha());
        matchAdd.put("ganador", "");
        if(exists(liga) && !existMatch(liga, match)){
            Task<Void> aux = db.collection("liga").document(liga.getId()).collection("enfrentamientos").document(match.getId()).set(matchAdd)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(DBOK, "The new match has been aded to the league");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(DBError, "An error ocurred while adding the nue match to the league");
                        }
                    });
        }
        else{
            Log.d(DBError, "The league does not exist on the DB");
        }
    }

    /**
     *
     * @param liga
     * @return un listado de enfrentamientos de la liga
     */
    public static List<Match> getMatches(Liga liga){
        List<Match> matches = new ArrayList<Match>();
        if(exists(liga)){
            Task<QuerySnapshot> aux = db.collection("ligas").document(liga.getId()).collection("enfrentamientos").get()
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
                    Date fechaHora = ds.getDate("fechaHora");
                    String ganador = (String) ds.get("ganador");
                    matches.add(new Match(id, equipoAzul, equipoRojo, fechaHora, ganador, matchType.Liga));
                }
            }
        }
        else{
            Log.d(DBError, "The league does not exist in the DB");
        }
        return matches;
    }

    /**
     *
     * @param liga
     * @param match
     * @return borra el enfrentamiento dado de la liga dada y devuelve true; false en caso contrario
     */
    public static boolean deleteMatch(Liga liga, Match match){
        boolean res = false;
        if(existMatch(liga, match)){
            Task<Void> aux = db.collection("ligas").document(liga.getId()).collection("enfrentamientos").document(match.getId()).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(DBOK, "The match has been deleted from the league");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(DBError, "An error ocurred when deleteing the match from the league");
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
     * actualiza el enfrentamiento dado de la liga dada
     * @param liga
     * @param matchToChange
     */
    public static void updateMatch(Liga liga, Match matchToChange){
        if(existMatch(liga, matchToChange)){
            db.collection("ligas").document(liga.getId()).collection("enfrentamientos").document(matchToChange.getId()).update(
                    "equipoAzul", matchToChange.getIdAzul(),
                    "equipoRojo", matchToChange.getIdRojo(),
                    "fechaHora", matchToChange.getFecha(),
                    "ganador", matchToChange.getResultado()
            ).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "The match in the league ahs been updated");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "An error ocurred when updating the match in the league");
                }
            });
        }
        else{
            Log.d(DBError, "The match does not belong to the league");
        }
    }

    /**
     *
     * @param liga
     * @param match
     * @return true si existe el enfrentamiento dado en la liga dada; false en caso contrario
     */
    public static boolean existMatch(Liga liga, Match match){
        boolean res = false;
        if(exists(liga)){
            Task<DocumentSnapshot> aux = db.collection("ligas").document(liga.getId()).collection("enfrentamientos").document(match.getId()).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Log.d(DBOK, "The match might exist on the DB");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(DBError, "An error ocurred when checking if the match exist in the league");
                        }
                    });
            while (!aux.isComplete()){}
            if(aux.getResult().exists()){
                res = true;
            }
        }
        else{
            Log.d(DBError, "The league does not exists on the DB");
        }
        return res;
    }

    /**
     * @param liga
     * @param match
     * @return el equipo azul
     */
    public static Equipo getBlueTeam(Liga liga, Match match){
        Equipo e = null;
        if(existMatch(liga, match)){
            e = PersistenciaEquipo.buscarEquipoPorID(match.getIdAzul());
        }
        return e;
    }

    /**
     * @param liga
     * @param match
     * @return el equipo rojo
     */
    public static Equipo getRedTeam(Liga liga, Match match){
        Equipo e = null;
        if(existMatch(liga, match)){
            e = PersistenciaEquipo.buscarEquipoPorID(match.getIdRojo());
        }
        return e;
    }

    /**
     * @param liga
     * @param match
     * @return el equipo ganador o null si aun no hay ningun equipo ganador
     */
    public static Equipo getWinningTeam(Liga liga, Match match){
        Equipo e = null;
        if(existMatch(liga, match) && !match.getResultado().equals("")){
            e = PersistenciaEquipo.buscarEquipoPorID(match.getResultado());
        }
        return e;
    }
}
