package com.igalda.scrimgg.pers;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.igalda.scrimgg.dom.*;
import com.igalda.scrimgg.dom.chat.Message;
import com.igalda.scrimgg.regActivity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersistenciaUsuario {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String DBOK = "DB Confirmation";
    private static String DBError = "DB ERROR";

    /**
     * da de alta un nuevo usuario en la base de datos si este no existe
     * @param u
     */
    public static void altaUsuario(Usuario u){
        Map<String, Object> usr = new HashMap<String, Object>();
        usr.put("cuentaRiot", u.getCuentaRiot());
        usr.put("email", u.getEmail());
        usr.put("expScrim", u.getExpScrim());
        usr.put("imgPath", u.getImgPath());
        usr.put("nick", u.getNickName());
        usr.put("nivelScrim", u.getNivelScrim());
        if(!existeUsuario(u.getId())){
            db.collection("users").document(u.getId()).set(usr).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "User added to the DB.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Unexpected error adding the user to the DB.");
                }
            });
        }
        else{
            Log.d(DBOK,"User alredy exist on DB.");
        }
    }

    /**
     *
     * @param uid
     * @return borra el usuario de la base de datos con el uid dado si este existe y devuelve true; devuelve false en caso contrario
     */
    public static boolean bajaUsuario(String uid){
        boolean res = false;
        if(existeUsuario(uid)){
            Task<Void> aux = db.collection("users").document(uid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "User succesfully deleted");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Unexpected error on deleting the user from the DB.");
                }
            });
            while (!aux.isComplete()){}
            if(aux.isSuccessful()){
                res = true;
            }
        }
        return res;
    }

    /**
     * Modifica los datos de un usuario existente. Recibe como parámetro un objeto Usuario que contiene los campos a modificar ya modificados.
     * @param usuModif
     */
    public static void modificarUsuario(Usuario usuModif){
        db.collection("users").document(usuModif.getId()).update(
                "cuentaRiot", usuModif.getCuentaRiot(),
                "email", usuModif.getEmail(),
                "expScrim", usuModif.getExpScrim(),
                "imgPath", usuModif.getImgPath(),
                "nick", usuModif.getNickName(),
                "nivelScrim", usuModif.getNivelScrim()
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DBOK, "User Updated succesfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(DBError, "Can't update the user");
            }
        });
    }

    /**
     *
     * @param uid
     * @return el usuario con el uid pasado como parametro, o null si este no existe en la base de datos
     */
    public static Usuario buscarUsuario(String uid){
        Usuario ret = null;
        Task<DocumentSnapshot> user = db.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
        while (!user.isComplete()) {
            // Empty
        }
        DocumentSnapshot result = user.getResult();

        ret = new Usuario(uid, (String) result.get("cuentaRiot"), (String) result.get("email"), Integer.parseInt(result.get("expScrim").toString()), (String) result.get("imgPath"), (String) result.get("nick"), Integer.parseInt(result.get("nivelScrim").toString()));
        return ret;
    }

    /**
     *
     * @return un listado con todos los usuarios de la base de datos
     */
    public static List<Usuario> listaUsuarios(){
        List<Usuario> listado = new ArrayList<Usuario>();
        Task<QuerySnapshot> aux = db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d(DBOK, "Users list returned");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(DBError, "An error ocurred while reading all the users");
            }
        });
        while (!aux.isComplete()){}
        if(!aux.getResult().isEmpty()){
            for(DocumentSnapshot ds: aux.getResult().getDocuments()){
                String cuentaRiot = (String) ds.get("cuentaRiot");
                String email = (String) ds.get("email");
                int expScrim = Integer.valueOf(ds.get("expScrim").toString()).intValue();
                String imgPath = (String) ds.get("imgPath");
                String nick = (String) ds.get("nick");
                int nivelScrim = Integer.valueOf(ds.get("nivelScrim").toString()).intValue();
                listado.add(new Usuario(ds.getId(), cuentaRiot, email,expScrim, imgPath, nick, nivelScrim));
            }
        }
        return listado;
    }

    /**
     *
     * @param uid
     * @return true si existe el usuario con el uid dado; false en caso contrario
     */
    public static boolean existeUsuario(String uid){
        boolean ret = false;
        Task<DocumentSnapshot> aux = db.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();

                    if(doc.exists()){
                        Log.d(DBOK, "User Exist.");
                    }else{
                        Log.d(DBOK, "User doesn't exist.");
                    }
                }else{
                    Log.d(DBError, "Failed with: ", task.getException());
                }
            }
        });
        while(!aux.isComplete()){}
        if(aux.getResult().exists()) {
            ret = true;
        }
        return ret;
    }

    /**
     *
     * @param idUsuario
     * @return el listado de los id's de eqipos del usuario con el id dado
     */
    public static  List<String> getIdsEquipos(String idUsuario){
        List<String> listado = new ArrayList<String>();
        if(existeUsuario(idUsuario)){
            Task<QuerySnapshot> aux = db.collection("users").document(idUsuario).collection("equipos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    Log.d(DBOK, "Success on reading the id's of the user's teams");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Error on reading the user's team id's");
                }
            });
            while (!aux.isComplete()){}
            if(!aux.getResult().isEmpty()){
                for(DocumentSnapshot ds: aux.getResult().getDocuments()){
                    String id = ds.getId();
                    listado.add(id);
                }
            }
        }
        else{
            Log.d(DBError, "The user dosn't exist");
        }
        return listado;
    }

    /**
     *
     * @param idUsuario
     * @return el listado de los id's de los equipos (invitaciones), del usuario con el id dado
     */
    public static List<String> getIdsInvitaciones(String idUsuario){
        List<String> listado = new ArrayList<String>();
        if(existeUsuario(idUsuario)){
            Task<QuerySnapshot> aux = db.collection("users").document(idUsuario).collection("invitaciones").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    Log.d(DBOK, "Success on reading the id's of the user's invitation teams");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Error on reading the user's invitation team id's");
                }
            });
            while (!aux.isComplete()){}
            if(!aux.getResult().isEmpty()){
                for(DocumentSnapshot ds: aux.getResult().getDocuments()){
                    String id = ds.getId();
                    listado.add(id);
                }
            }
        }
        else{
            Log.d(DBError, "The user dosn't exist");
        }
        return listado;
    }

    /**
     *
     * @param idUsuario
     * @param idEquipo
     * añade un nuevo id de equipo a la colección de equipos del usuario con el id dado
     */
    public static void anadirEquipo(String idUsuario, String idEquipo){
        Map<String, String> map = new HashMap<String, String>();
        map.put("idEquipo", idEquipo);
        db.collection("users").document(idUsuario).collection("equipos").document(idEquipo).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DBOK, "Team added to the player");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(DBError, "Unexpected error adding the team to the user on DB.");
            }
        });
    }

    /**
     *
     * @param userID
     * @param teamID
     * @return borra el id de equipo de la colección de equipos del usuario con el id dado y devuelve true; false en caso contrario
     */
    public static boolean deleteTeam(String userID, String teamID){
        boolean res = false;
        Task<Void> aux = db.collection("users").document(userID).collection("equipos").document(teamID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DBOK, "Team deleted from the user");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(DBError, "Error on deleting the team from the user");
            }
        });
        while (!aux.isComplete()){}
        if(aux.isSuccessful()){
            res = true;
        }
        return res;
    }

    /**
     *
     * @param idUsuario
     * @param idEquipoInvitado
     * añade una nueva invitación al usuario con el id dado
     */
    public static void anadirInvitacion(String idUsuario, String idEquipoInvitado){
        Map<String, String> map = new HashMap<String, String>();
        map.put("idEquipo", idEquipoInvitado);
        db.collection("users").document(idUsuario).collection("invitaciones").document(idEquipoInvitado).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DBOK, "Invitation added to the player");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(DBError, "Unexpected error adding the invitation to the user on DB.");
            }
        });
    }

    /**
     *
     * @param userID
     * @param teamID
     * @return borra la invitación con el teamID dado del usuario con el userID dado y devuelve true; false en caso contrario
     */
    public static boolean deleteInvitation(String userID, String teamID){
        boolean res = false;
        Task<Void> aux = db.collection("users").document(userID).collection("invitaciones").document(teamID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DBOK, "Invitation deleted from the user");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(DBError, "Error on deleting the invitation from the user");
            }
        });
        while (!aux.isComplete()){}
        if(aux.isSuccessful()){
            res = true;
        }
        return res;
    }
}
