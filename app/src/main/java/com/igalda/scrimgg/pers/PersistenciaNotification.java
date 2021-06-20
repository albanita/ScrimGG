package com.igalda.scrimgg.pers;

import android.annotation.SuppressLint;
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
import com.igalda.scrimgg.dom.notif.Notification;
import com.igalda.scrimgg.neg.Negocio;
import com.igalda.scrimgg.neg.NegocioNotification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("SimpleDateFormat")

public class PersistenciaNotification {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String DBOK = "DB Confirmation";
    private static String DBError = "DB ERROR";
    private static Negocio n = Negocio.getNegocio();

    /**
     * Añade una notificación a la BD.
     * @param n notificación a añadir a la BD.
     */
    public static void addNotification(Notification n){
        Map<String, Object> notifToAdd = new HashMap<>();
        notifToAdd.put("asunto", n.getTitle());
        notifToAdd.put("fecha", n.getDate());
        notifToAdd.put("texto", n.getText());

        if (!existNotif(n.getNid(), n.getUid())) {
            Task<Void> notifAdd = db.collection("users").document(n.getUid()).collection("notifications").document(n.getNid()).set(notifToAdd).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "Notification added to the DB.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Unexpected error adding the notification to the DB.");
                }
            });
            while (!notifAdd.isComplete()) {
            }
        } else {
            Log.d(DBOK, "Notification alredy exists on DB.");
        }
    }

    /**
     * Devuelve una notificación a partir de su id.
     * @param nid id de la notificación.
     * @param uid id del usuario al que pertenece la notificación.
     * @return Notificación solicitada.
     */
    public static Notification getNotification(String nid, String uid) {
        Notification ret = null;
        Task<DocumentSnapshot> notif = db.collection("users").document(uid).collection("notifications").document(nid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
        while (!notif.isComplete()) {
            // Empty
        }
        DocumentSnapshot result = notif.getResult();
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm-dd/MMM");
        Date date = new Date();
        try {
            date = sdf.parse((String) result.get("fecha"));
        } catch (ParseException ex){
            Log.d("Notif error", "Failed parsing notification date.");
        }
        ret = new Notification( nid, uid,
                (String) result.get("asunto"),
                (String) result.get("texto"),
                date);
        return ret;
    }

    /**
     * Devuelve un listado de notificaciones pendientes de un usuario.
     * @param uid ID del usuario.
     * @return Lista de 'Notificacion' pendientes.
     */
    public static List<Notification> listNotifications(String uid){
        List<Notification> ret = new ArrayList<>();
        if (n.nUsuario().existeUsuario(uid)) {
            Task<QuerySnapshot> colNotif = db.collection("users").document(uid).collection("notifications").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(DBOK, "Notifications get correctly.");
                    } else {
                        Log.d(DBError, "Error while getting the notifications.");
                    }
                }
            });
            while (!colNotif.isComplete()) {
            }
            for (QueryDocumentSnapshot notifIds : colNotif.getResult()) {
                Log.d("Pruebas", notifIds.getId());
                ret.add(n.nNotification().getNotification(notifIds.getId(), uid));
            }
        } else {
            Log.d(DBOK, "User doesn't exist on DB!");
        }
        return ret;
    }

    /**
     * Elimina una notificación de la BD.
     * @param nid ID de la notificación.
     */
    public static void deleteNotification(String nid, String uid){
        if(existNotif(nid, uid)){
            Task<Void> del = db.collection("users").document(uid).collection("notifications").document(nid).delete();
            while(!del.isComplete()){}
        }else{
            Log.d(DBOK,"Notification doesn't exist on the DB.");
        }
    }

    /**
     * Elimina todas las notificaciones de un usuario.
     * @param uid ID del usuario.
     */
    public static void wipeNotifications(String uid){
        List<Notification> lnotif = n.nNotification().listNotifications(uid);
        for(Notification not : lnotif){
            n.nNotification().deleteNotification(not.getNid(), not.getUid());
        }
    }

    /**
     * Comprueba si una notificación existe o no en la BD.
     * @param nid notificación a comprobar si existe o no.
     * @param uid usuario al que pertenece la notificación.
     * @return True si la notificación existe, false en caso contrario.
     */
    public static boolean existNotif(String nid, String uid) {
        boolean ret = false;
        Task<DocumentSnapshot> notifDoc = db.collection("users").document(uid).collection("notifications").document(nid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
        while (!notifDoc.isComplete()) {
            // Empty
        }
        if (notifDoc.getResult().exists()) {
            ret = true;
        }
        return ret;
    }

    /**
     * Genera un identificador para una nueva notificación.
     * @param uid Identificador del usuario.
     * @return Entero más bajo encontrado libre para ser identificador.
     */
    public static int generateNotificationId(String uid){
        List<Notification> ln = listNotifications(uid);
        List<Integer> ids = new ArrayList<>();
        int i = 0;
        for (Notification nt : ln){
            ids.add(Integer.parseInt(nt.getNid()));
        }
        for(i = 0; i < Integer.MAX_VALUE; i++){
            if(!ids.contains(i)) break;
        }
        return i;
    }
}
