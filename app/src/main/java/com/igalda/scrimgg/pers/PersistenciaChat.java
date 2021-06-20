package com.igalda.scrimgg.pers;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.igalda.scrimgg.dom.Usuario;
import com.igalda.scrimgg.dom.chat.Room;
import com.igalda.scrimgg.dom.chat.Message;
import com.igalda.scrimgg.neg.Negocio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("SimpleDateFormat")

public class PersistenciaChat {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser currentUser = mAuth.getCurrentUser();
    private static String DBOK = "DB Confirmation";
    private static String DBError = "DB ERROR";

    private static Negocio n = Negocio.getNegocio();

    // ---------------------------------------ROOMS----------------------------------------

    /**
     * Añade una sala de chat a la BD.
     * @param r Sala a añadir.
     */
    public static void addRoom(Room r) {
        Map<String, Object> roomToAdd = new HashMap<>();
        roomToAdd.put("lastMsg", r.getUltimoMensaje());
        roomToAdd.put("msgTime", r.getHoraUltimoMensaje());
        roomToAdd.put("msgCount", String.valueOf(0));

        if (!existRoom(r.getRid())){
            Task<Void> roomAdd = db.collection("chats").document(r.getRid()).set(roomToAdd).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "Room added to the DB.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Unexpected error adding the room to the DB.");
                }
            });
            while (!roomAdd.isComplete()) {
            }
        } else {
            Log.d(DBOK, "Room alredy exists on DB.");
        }
    }

    /**
     * Devuelve una sala de la BD a partir de su identificador.
     * @param rid Identificador de la sala.
     * @return Sala obtenida de la BD o null si no existe.
     */
    public static Room getRoom(String rid) {
        Room ret = null;
        Task<DocumentSnapshot> room = db.collection("chats").document(rid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
        while (!room.isComplete()) {
            // Empty
        }
        DocumentSnapshot result = room.getResult();
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm-dd/MMM");
        Date date = new Date();
        String uid = n.nChat().separateRid(rid, currentUser.getUid());
        Usuario user = n.nUsuario().buscarUsuario(uid);
        try {
            date = sdf.parse((String) result.get("msgTime"));
        } catch (ParseException ex){
            Log.d("Room error", "Failed parsing room date.");
        }
        ret = new Room(rid, user.getNickName(), user.getImgPath(), (String) result.get("lastMsg"), date);
        return ret;
    }

    /**
     * Obtiene un listado de salas de un usuario de la BD.
     * @param uid Usuario del que se obtienen las salas.
     * @return Listado con todas las salas del usuario.
     */
    public static List<Room> listRooms(String uid){
        List<Room> ret = new ArrayList<>();
        String rid;
        if (n.nUsuario().existeUsuario(uid)) {
            Task<QuerySnapshot> colRoom = db.collection("chats").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(DBOK, "Rooms get correctly.");
                    } else {
                        Log.d(DBError, "Error while getting the rooms.");
                    }
                }
            });
            while (!colRoom.isComplete()) {
            }
            for (QueryDocumentSnapshot roomIds : colRoom.getResult()) {
                rid = roomIds.getId();
                if(rid.contains(uid)){
                    Log.d("Pruebas", roomIds.getId());
                    ret.add(n.nChat().getRoom(rid));
                }
            }
        } else {
            Log.d(DBOK, "User doesn't exist in DB!");
        }
        return ret;
    }

    /**
     * Elimina una sala del conjunto de salas a partir de su identificador.
     * @param rid Identificador de la sala a eliminar.
     */
    public static void deleteRoom(String rid) {
        if(existRoom(rid)){
            Task<Void> del = db.collection("chats").document(rid).delete();
            while(!del.isComplete()){}
        }else{
            Log.d(DBOK,"Room doesn't exist in the DB.");
        }
    }

    /**
     * Elimina todas las salas de chat de un usuario.
     * @param uid Usuario del que eliminar sus salas.
     */
    public static void wipeRooms(String uid) {
        List<Room> lroom = n.nChat().listRooms(uid);
        for(Room rm : lroom){
            n.nChat().deleteRoom(rm.getRid());
        }
    }

    /**
     * Comprueba si una sala existe en la BD a partir de su identificador.
     * @param rid Identificador de la sala.
     * @return True si la sala existe, False en caso contrario.
     */
    public static boolean existRoom(String rid){
        boolean ret = false;
        Task<DocumentSnapshot> roomDoc = db.collection("chats").document(rid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
        while (!roomDoc.isComplete()) {
            // Empty
        }
        if (roomDoc.getResult().exists()) {
            ret = true;
        }
        return ret;
    }

    // --------------------------------------MESSAGES---------------------------------------

    /**
     * Añade un mensaje a una sala de chat.
     * @param m Mensaje a añadir.
     * @param rid Identificador de la sala de chat.
     */
    public static void addMessage(Message m, String rid) {
        Map<String, Object> msgToAdd = new HashMap<>();
        msgToAdd.put("nickname", m.getNombre());
        msgToAdd.put("text", m.getMensaje());
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm-dd/MMM");
        String date = sdf.format(m.getHora());
        msgToAdd.put("msgTime", date);
        msgToAdd.put("imgPath", m.getFotoPerfil());

        if (existRoom(rid)){
            Task<Void> msgAdd = db.collection("chats").document(rid).collection("messages").document(m.getMid()).set(msgToAdd).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "Message added to the DB.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Unexpected error adding the message to the DB.");
                }
            });
            while (!msgAdd.isComplete()) {}
            int counter = countMessages(rid);
            Task<Void> roomUpdate = db.collection("chats").document(rid).update("lastMsg", m.getMensaje(), "msgCount", String.valueOf(counter+1), "msgTime", date).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "Room's fields updated on the DB.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Unexpected error updating the room on the DB.");
                }
            });
            while (!roomUpdate.isComplete()) {}
        } else {
            Log.d(DBOK, "Room doesn't exist on DB.");
        }
    }

    /**
     * Devuelve un mensaje de una sala de chat a partir de su identificador.
     * @param rid Identificador de la sala de chat.
     * @param mid Identificador del mensaje.
     * @return Mensaje de la sala, o null si no existe.
     */
    public static Message getMessage(String rid, String mid) {
        Message ret = null;
        Task<DocumentSnapshot> msg = db.collection("chats").document(rid).collection("messages").document(mid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
        while (!msg.isComplete()) {
            // Empty
        }
        DocumentSnapshot result = msg.getResult();
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm-dd/MMM");
        Date date = new Date();
        try {
            date = sdf.parse((String) result.get("msgTime"));
            Log.d("msgTime", sdf.format(date));
        } catch (ParseException ex){
            Log.d("Room error", "Failed parsing message date.");
        }

        ret = new Message(mid, (String) result.get("nickname"), (String) result.get("text"), (String) result.get("imgPath"), date);
        return ret;
    }

    /**
     * Obtiene un listado con todos los mensajes de una sala de chat.
     * @param rid Identificador de la sala.
     * @return Listado de todos los mensajes de la sala.
     */
    public static List<Message> listMessages(String rid) {
        List<Message> ret = new ArrayList<>();
        if (n.nChat().existRoom(rid)) {
            Task<QuerySnapshot> colMsg = db.collection("chats").document(rid).collection("messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(DBOK, "Messages get correctly.");
                    } else {
                        Log.d(DBError, "Error while getting the messages.");
                    }
                }
            });
            while (!colMsg.isComplete()) {
            }
            for (QueryDocumentSnapshot msgIds : colMsg.getResult()) {
                Log.d("Pruebas", msgIds.getId());
                ret.add(n.nChat().getMessage(rid, msgIds.getId()));
            }
        } else {
            Log.d(DBOK, "Room doesn't exist in DB!");
        }
        return ret;
    }

    /**
     * Elimina un mensaje de una sala de chat.
     * @param mid Identificador del mensaje a eliminar.
     * @param rid Identificador de la sala a la que pertenece el mensaje.
     */
    public static void deleteMessage(String mid, String rid) {
        if(existRoom(rid)){
            Task<Void> del = db.collection("chats").document(rid).collection("messages").document(mid).delete();
            while(!del.isComplete()){/*Empty*/}
            int counter = countMessages(rid);
            Task<Void> reduceCounter = db.collection("chats").document(rid).update("msgCount", String.valueOf(counter-1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(DBOK, "Room Updated succesfully");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(DBError, "Can't update the Room");
                }
            });
            while(!reduceCounter.isComplete()){/*Empty*/}
        }else{
            Log.d(DBOK,"Room doesn't exist in the DB.");
        }
    }

    /**
     * Elimina todos los mensajes de una sala de chat.
     * @param rid Identificador de la sala de chat.
     */
    public static void wipeMessages(String rid) {
        List<Message> lmsg = n.nChat().listMessages(rid);
        for(Message msg : lmsg){
            n.nChat().deleteMessage(rid, msg.getMid());
        }
    }

    /**
     * Comprueba si existe un mensaje en una sala de chat.
     * @param rid Identificador de la sala de chat a la que pertenece el mensaje.
     * @param mid Identificador del mensaje a comprobar.
     * @return True si el mensaje existe, False en caso contrario.
     */
    public static boolean existMessage(String rid, String mid){
        boolean ret = false;
        Task<DocumentSnapshot> roomDoc = db.collection("chats").document(rid).collection("messages").document(mid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
        while (!roomDoc.isComplete()) {
            // Empty
        }
        if (roomDoc.getResult().exists()) {
            ret = true;
        }
        return ret;
    }

    /**
     * Devuelve el número de mensajes que hay en una sala de chat.
     * @param rid Identificador de la sala de chat
     * @return Número de mensajes en la sala.
     */
    public static int countMessages(String rid){
        int ret = 0;
        Task<DocumentSnapshot> msg = db.collection("chats").document(rid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
        while (!msg.isComplete()) {
            // Empty
        }
        DocumentSnapshot result = msg.getResult();
        ret = Integer.parseInt((String) result.get("msgCount"));
        return ret;
    }

    /**
     * Genera un identificador para un nuevo mensaje.
     * @param rid Identificador del usuario.
     * @return Entero más alto encontrado libre para ser identificador.
     */
    public static int generateMessageId(String rid){
        List<Message> lm = listMessages(rid);
        List<Integer> ids = new ArrayList<>();
        for (Message ms : lm){
            ids.add(Integer.parseInt(ms.getMid()));
        }
        int max = 0;
        if (ids.size() != 0){
            max = Collections.max(ids);
        }
        return max+1;
    }
}
