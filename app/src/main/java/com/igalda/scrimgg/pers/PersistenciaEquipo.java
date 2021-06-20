package com.igalda.scrimgg.pers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.igalda.scrimgg.dom.*;
import com.igalda.scrimgg.neg.Negocio;
import com.igalda.scrimgg.neg.NegocioTorneo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersistenciaEquipo {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String DBOK = "DB Confirmation";
    private static String DBError = "DB ERROR";

    private static Negocio n = Negocio.getNegocio();

    /*
        ALTAS Y NEWS
     */

    // TODO : agregar el capitan a jugadores cuando se crea, con su rol.
    public static void altaEquipo(Equipo e) {
        Map<String, Object> equipToAdd = new HashMap<>();
        equipToAdd.put("capitan", e.getIdCapitan());
        equipToAdd.put("imgPath", e.getImgPath());
        equipToAdd.put("nombreEquipo", e.getTid().split("#")[0]);
        equipToAdd.put("tipo", e.getTipo());

        /*privacidadEquipo pe = e.getTipo();
        String tipo = "";
        if(pe == privacidadEquipo.INVITACION){
            tipo = "INVITACIÓN";
        }
        else if(pe == privacidadEquipo.PRIVADO){
            tipo = "PRIVADO";
        }
        else if(pe == privacidadEquipo.PUBLICO){
            tipo = "PUBLICO";
        }
        equipToAdd.put("tipo", tipo);*/

        if (!exist(e)) {
            Task<Void> equipAdd = db.collection("equipos").document(e.getTid()).set(equipToAdd).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "Equip added to the DB.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Unexpected error adding the equip to the DB.");
                }
            });
            while (!equipAdd.isComplete()) {
            }
        } else {
            Log.d(DBOK, "Equip alredy exist on DB.");
        }
    }

    public static void newLeague(Equipo e, Liga l) {
        Map<String, Object> equipToLeague = new HashMap<>();
        equipToLeague.put("idEquipo", e.getTid());
        equipToLeague.put("puntuacion", 0);

        Map<String, Object> leagueToEquip = new HashMap<>();
        leagueToEquip.put("idLiga", l.getId());
        if (exist(e) && !existLeague(e, l) && PersistenciaLiga.exists(l)) {
            Task<Void> leagueToAdd = db.collection("equipos").document(e.getTid()).collection("liga").document(l.getId()).set(leagueToEquip).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "League added to the equip " + e.getTid());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Unexpected error adding the league to the equip in the DB.");
                }
            });
            while (!leagueToAdd.isComplete()) {
            }

            Task<Void> equipToAdd = db.collection("ligas").document(l.getId()).collection("equipos").document(e.getTid()).set(equipToLeague).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "Equip added to the league " + l.getNombre());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Unexpected error adding the equip to the league in the DB.");
                }
            });
            while (!equipToAdd.isComplete()) {
            }
        } else {
            Log.d(DBOK, "Equip doesn't exist, league already on equip or league doesn't exist on the DB.");
        }
    }

    /**
     * Añade el usuario al equipo con su rol respectivamente, a su vez, añade el id del equipo a la coleccion equipos del usuario.
     */
    public static void newPlayer(Equipo e, Usuario u, roles r) {
        if (exist(e) && !existPlayer(e, u)) {
            Map<String, Object> playerToAdd = new HashMap<>();
            playerToAdd.put("idUsuario", u.getId());
            playerToAdd.put("rol", r);
            Task<Void> addPlayer = db.collection("equipos").document(e.getTid()).collection("jugadores").document(u.getId()).set(playerToAdd).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "Player with id " + u.getId() + " added to equip with id " + e.getTid());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Error setting the player to the equip");
                }
            });
            while (!addPlayer.isComplete()) {
            }
            Map<String, Object> equipToAddUser = new HashMap<>();
            equipToAddUser.put("idEquipo", e.getTid());
            Task<Void> addEquipToPlayer = db.collection("users").document(u.getId()).collection("equipos").document(e.getTid()).set(equipToAddUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "Equip: " + e.getTid() + " added to user with id " + u.getId() + " with nickname: " + u.getNickName());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Error setting the equip to the player");
                }
            });
            while (!addEquipToPlayer.isComplete()) {
            }
        } else {
            Log.d(DBOK, "Equip doesn't exist or player already on that team.");
        }
    }

    public static void newTournament(Equipo e, Torneo t){
        if(exist(e)&& NegocioTorneo.existeTorneo(t)){
            NegocioTorneo.addEquipo(t,e);
            Map<String, Object> addTournamentToEquip = new HashMap<>();
            addTournamentToEquip.put("idTorneo",t.getId());
            Task<Void> addTourTask = db.collection("equipos").document(e.getTid()).collection("torneos").document(t.getId()).set(addTournamentToEquip).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK,"Tournament " + t.getNombre()+  " added to equip " +e.getTid());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError,"Error setting the tournament to the equip.");
                }
            });
            while(!addTourTask.isComplete()){}
        }else{
            Log.d(DBOK,"Equip or tournament doesn't exist.");
        }
    }

    public static void newMatch(Equipo e, Match m) {
        //Todo: Dani se ha quedado aquí, hay que pensar como hacer el swich de los tipos de enfrentamiento.
    }

    /*
        FIN DE ALTAS Y NEWS
     */

    /*
        EXISTS
     */

    public static boolean exist(Equipo e) {
        boolean ret = false;
        String id = e.getTid();
        Task<DocumentSnapshot> equipDoc = db.collection("equipos").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();

                    if (doc.exists()) {
                        Log.d(DBOK, "Document Exist.");
                    } else {
                        Log.d(DBOK, "Document doesn't exist.");
                    }
                } else {
                    Log.d(DBError, "Failed with: ", task.getException());
                }
            }
        });
        while (!equipDoc.isComplete()) {
        }
        if (equipDoc.getResult().exists()) {
            ret = true;
        }
        return ret;
    }

    public static boolean existPlayer(Equipo e, Usuario u) {
        boolean ret = false;
        if (exist(e)) {
            Task<DocumentSnapshot> playerDoc = db.collection("equipos").document(e.getTid()).collection("jugadores").document(u.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) {
                            Log.d(DBOK, "Player exist in equip " + e.getTid());
                        } else {
                            Log.d(DBOK, "Player doesn't exist in equip " + e.getTid());
                        }
                    } else {
                        Log.d(DBError, "Failed with: " + task.getException());
                    }
                }
            });
            while (!playerDoc.isComplete()) {
            }
            if (playerDoc.getResult().exists()) {
                ret = true;
            }
        } else {
            Log.d(DBOK, "Equip doesn't exist on the DB.");
        }
        return ret;
    }

    public static boolean existLeague(Equipo e, Liga l) {
        boolean ret = false;
        if (exist(e)) {
            Task<DocumentSnapshot> leagueDoc = db.collection("equipos").document(e.getTid()).collection("liga").document(l.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) {
                            Log.d(DBOK, "League exist in equip " + e.getTid());
                        } else {
                            Log.d(DBOK, "League doesn't exist in equip " + e.getTid());
                        }
                    } else {
                        Log.d(DBError, "Failed with: " + task.getException());
                    }
                }
            });
            while (!leagueDoc.isComplete()) {
            }
            if (leagueDoc.getResult().exists()) {
                ret = true;
            }
        } else {
            Log.d(DBOK, "Equip doesn't exist on the DB");
        }
        return ret;
    }

    public static boolean existTournament(Equipo e, Torneo t){
        boolean ret = false;
        if(exist(e)){
            Task<DocumentSnapshot> tournamentDoc = db.collection("equipos").document(e.getTid()).collection("torneos").document(t.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                        if(doc.exists()){
                            Log.d(DBOK,"Tournament exist in equip " + e.getTid());
                        }else{
                            Log.d(DBOK,"Tournament doesn't exist in equip " + e.getTid());
                        }
                    }else{
                        Log.d(DBError,"Failed with: " + task.getException());
                    }
                }
            });
            while(!tournamentDoc.isComplete()){}
            if(tournamentDoc.getResult().exists()){
                ret = true;
            }
        }else{
            Log.d(DBOK,"Equip doesn't exist on the DB.");
        }
        return ret;
    }

    /*
        FIN DE EXISTS
     */

    /*
        BAJAS Y DELETES
     */

    public static boolean bajaEquipo(Equipo e) {//Todo: borrar TODAS las colleciones
        boolean ret = false;
        if (exist(e)) {
            List<Usuario> playersInEquip = PersistenciaEquipo.getPlayers(e);
            for (Usuario u : playersInEquip) {
                Log.d(DBOK, u.getNickName());
                PersistenciaUsuario.deleteTeam(u.getId(), e.getTid());
            }
            deleteAllPlayers(e);
            deleteAllLeagues(e);
            deleteAllTournaments(e);
            Task<Void> delDoc = db.collection("equipos").document(e.getTid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d(DBOK, "Equip deleted from DB.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Error deleting the equip from DB.");
                }
            });
            while (!delDoc.isComplete()) {
            }
            if (delDoc.isSuccessful()) {
                ret = true;
            }
        } else {
            Log.d(DBOK, "Equip doesn't exist on DB!");
        }
        return ret;
    }

    public static void deleteAllPlayers(Equipo e) {
        if (exist(e)) {
            Task<QuerySnapshot> delAllPlayers = db.collection("equipos").document(e.getTid()).collection("jugadores").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    Log.d(DBOK, "Getting players complete.");
                }
            });
            while (!delAllPlayers.isComplete()) {
            }
            for (QueryDocumentSnapshot aux : delAllPlayers.getResult()) {
                Task<Void> del = db.collection("equipos").document(e.getTid()).collection("jugadores").document(aux.getId()).delete();
                while (!del.isComplete()) {
                }
            }
        } else {
            Log.d(DBOK, "Equip doesn't exist.");
        }
    }

    /**
     * Realizamos una consulta en la base de datos, donde recogemos los ids de las ligas inscritas, tras eso,
     * con el objeto Liga vacío, asignamos el id del documento y llamamos al método de borrado de Liga dentro del equipo.
     */
    public static void deleteAllLeagues(Equipo e) {
        Liga l = new Liga();
        if (exist(e)) {
            Task<QuerySnapshot> dellAllLeagues = db.collection("equipos").document(e.getTid()).collection("liga").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    Log.d(DBOK, "Getting leagues complete.");
                }
            });
            while (!dellAllLeagues.isComplete()) {
            }
            for (QueryDocumentSnapshot aux : dellAllLeagues.getResult()) {
                l.setID(aux.getId());
                PersistenciaEquipo.deleteLeague(e, l);
            }
        } else {
            Log.d(DBOK, "Equip doesn't exist.");
        }
    }

    public static void deleteAllTournaments(Equipo e){
        Torneo t = new Torneo();
        if(exist(e)){
            Task<QuerySnapshot> dellAllTournaments = db.collection("equipos").document(e.getTid()).collection("torneos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    Log.d(DBOK,"Getting tournaments complete.");
                }
            });
            while(!dellAllTournaments.isComplete()){}
            for(QueryDocumentSnapshot aux : dellAllTournaments.getResult()){
                t.setId(aux.getId());
                deleteTournament(e,t);
            }
        }else{
            Log.d(DBOK,"Equip doesn't exist.");
        }
    }


    /**
     * Borramos la liga dentro del equipo, no se borra el equipo dentro de la liga porque es una información a mantener y si lo borramos podría generar nulos dentro del
     * historico de ligas.
     */
    public static void deleteLeague(Equipo e, Liga l) {
        if (existLeague(e, l)) {
            Task<Void> del = db.collection("equipos").document(e.getTid()).collection("liga").document(l.getId()).delete();
            while (!del.isComplete()) {
            }
        } else {
            Log.d(DBOK, "Equip doesn't exist on the DB or League doesn't exist on the equip " + e.getTid());
        }
    }

    public static void deleteTournament(Equipo e, Torneo t){
        if(existTournament(e,t)){
            Task<Void> del = db.collection("equipos").document(e.getTid()).collection("torneos").document(t.getId()).delete();
            while(!del.isComplete()){}
        }else{
            Log.d(DBOK,"Equip doesn't exist on the DB or Tournament doesn't exist on the equip.");
        }
    }

    /*
        FIN BAJAS Y DELETES
     */

    /*
        GETTERS Y BUSQUEDAS
     */

    public static List<Usuario> getPlayers(Equipo e) {
        List<Usuario> ret = new ArrayList<>();
        if (exist(e)) {
            Task<QuerySnapshot> collectionPlayers = db.collection("equipos").document(e.getTid()).collection("jugadores").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(DBOK, "Players get correctly.");
                    } else {
                        Log.d(DBError, "Error while getting the players.");
                    }
                }
            });
            while (!collectionPlayers.isComplete()) {
            }
            for (QueryDocumentSnapshot playersIDs : collectionPlayers.getResult()) {
                Log.d("Pruebas", playersIDs.getId());
                ret.add(n.nUsuario().buscarUsuario(playersIDs.getId()));
            }
        } else {
            Log.d(DBOK, "Equip doesn't exist on DB!");
        }
        return ret;
    }

    public static Equipo buscarEquipoPorID(String id) {
        Equipo test = new Equipo("", id, "", privacidadEquipo.PRIVADO);
        if (exist(test)) {
            Task<DocumentSnapshot> equipSnap = db.collection("equipos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Log.d(DBOK, "Equip got correctly.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Unexpected error getting the equip from the DB.");
                }
            });
            while (!equipSnap.isComplete()) {
            }
            DocumentSnapshot equipDoc = equipSnap.getResult();
            test.setIdCapitan((String) equipDoc.get("capitan"));
            test.setTid((String) equipDoc.get("nombreEquipo"));
            test.setImgPath((String) equipDoc.get("imgPath"));

            //test.setTipo((privacidadEquipo) equipDoc.get("tipo"));

            privacidadEquipo pe = privacidadEquipo.PUBLICO;
            String tipo = (String) equipDoc.get("tipo");
            if(tipo.equalsIgnoreCase("PRIVADO")){
                pe = privacidadEquipo.PRIVADO;
            }
            else if(tipo.equalsIgnoreCase("INVITACIÓN")){
                pe = privacidadEquipo.INVITACION;
            }
            test.setTipo(pe);

            return test;
        } else {
            return null;
        }
    }

    public static List<Equipo> buscarEquipoPorNombre(String nombre){
        List<Equipo> equipos = new ArrayList<Equipo>();
        Task<QuerySnapshot> aux = db.collection("equipos").whereEqualTo("nombreEquipo", nombre).get();
        while (!aux.isComplete()){}
        for(QueryDocumentSnapshot doc: aux.getResult()){
            Equipo e = PersistenciaEquipo.buscarEquipoPorID(doc.getId());
            equipos.add(e);
        }
        return equipos;
    }

    /*
        FIN GETTERS Y BUSQUEDAS
     */

    // devuelve la lista de id's de enfrentamientos del equipo con id dado todo: mejor borrar este metodo!!!!!!!!!!!!!!!
    public static List<String> getIdsEnfrentamientos(String idEquipo) {
        return null;
    }

    // devuelve la lista de id's de usuarios invitados del equipo con id dado
    public static List<String> getIdsUsuariosInvitados(String idEquipo) {
        return null;
    }

    public static List<String> getIdsLigas(String idEquipo) {
        return null;
    }

    public static List<String> getIdsUsuariosSolicitud(String idEquipo) {
        return null;
    }

    public static List<String> getIdsTorneos(String idEquipo) {
        return null;
    }

    public static List<Equipo> listaEquipos(){
        List<Equipo> equipos = new ArrayList<Equipo>();
        Task<QuerySnapshot> aux = db.collection("equipos").get();
        while(!aux.isComplete()){}
        if(!aux.getResult().isEmpty()){
            for(DocumentSnapshot ds: aux.getResult().getDocuments()){
                String id = (String) ds.getId();
                String idCapitan = (String) ds.get("capitan");
                String imgPath = (String) ds.get("imgPath");
                String nombre = (String) ds.get("nombreEquipo");
                String tipo = (String) ds.get("tipo");
                privacidadEquipo pe = privacidadEquipo.INVITACION;
                if(tipo.equalsIgnoreCase("privado")){
                    pe = privacidadEquipo.PRIVADO;
                }
                else if(tipo.equalsIgnoreCase("publico")){
                    pe = privacidadEquipo.PUBLICO;
                }
                equipos.add(new Equipo(idCapitan, id, imgPath, pe));
            }
        }
        return equipos;
    }

    // todo: Dani, revisa este metodo, que no estoy seguro de que está bien; lo he escrito yo en el sprint #1 --Alin
    /*public static List<Equipo> listaEquipos() {
        List<Equipo> equipos = new ArrayList<Equipo>();
        Map<String, Object> map = new HashMap<String, Object>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        map = (Map<String, Object>) db.collection("equipos").get();
        String[] idsEquipos = (String[]) map.values().toArray();
        for (int i = 0; i < idsEquipos.length; i++) {
            String[] splits = idsEquipos[i].split("#");
            String nombre = splits[0];
            equipos.add(PersistenciaEquipo.buscarEquipo(nombre));
        }

        return equipos;
    }

    // todo: Dani, revisa este metodo, que no estoy seguro de que está bien; lo he escrito yo en el sprint #1 --Alin
    public static boolean existeEquipo(String nombre) {
        Map<String, Object> map = null;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String id = nombre + "#" + nombre.hashCode();
        map = (Map<String, Object>) db.collection("equipos").document(id).get();
        if (map == null) {
            return false;
        } else {
            return true;
        }
    }*/


}
