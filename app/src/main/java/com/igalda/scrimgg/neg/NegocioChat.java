package com.igalda.scrimgg.neg;

import com.igalda.scrimgg.dom.chat.Room;
import com.igalda.scrimgg.dom.chat.Message;
import com.igalda.scrimgg.pers.PersistenciaChat;

import java.util.List;

public class NegocioChat {

    public NegocioChat(){}

    // ---------------------------------------ROOMS----------------------------------------

    /**
     * Añade una sala de chat a la BD.
     * @param r Sala a añadir.
     */
    public void addRoom(Room r){ PersistenciaChat.addRoom(r); }

    /**
     * Devuelve una sala de la BD a partir de su identificador.
     * @param rid Identificador de la sala.
     * @return Sala obtenida de la BD o null si no existe.
     */
    public Room getRoom(String rid) { return PersistenciaChat.getRoom(rid); }

    /**
     * Obtiene un listado de salas de un usuario de la BD.
     * @param uid Usuario del que se obtienen las salas.
     * @return Listado con todas las salas del usuario.
     */
    public List<Room> listRooms(String uid) { return PersistenciaChat.listRooms(uid); }

    /**
     * Elimina una sala del conjunto de salas a partir de su identificador.
     * @param rid Identificador de la sala a eliminar.
     */
    public void deleteRoom(String rid) { PersistenciaChat.deleteRoom(rid); }

    /**
     * Elimina todas las salas de chat de un usuario.
     * @param uid Usuario del que eliminar sus salas.
     */
    public void wipeRooms(String uid){ PersistenciaChat.wipeRooms(uid); }

    /**
     * Comprueba si una sala existe en la BD a partir de su identificador.
     * @param rid Identificador de la sala.
     * @return True si la sala existe, False en caso contrario.
     */
    public boolean existRoom(String rid) { return PersistenciaChat.existRoom(rid); }

    // --------------------------------------MESSAGES---------------------------------------

    /**
     * Añade un mensaje a una sala de chat.
     * @param m Mensaje a añadir.
     * @param rid Identificador de la sala de chat.
     */
    public void addMessage(Message m, String rid){ PersistenciaChat.addMessage(m, rid); }

    /**
     * Devuelve un mensaje de una sala de chat a partir de su identificador.
     * @param rid Identificador de la sala de chat.
     * @param mid Identificador del mensaje.
     * @return Mensaje de la sala, o null si no existe.
     */
    public Message getMessage(String rid, String mid) { return PersistenciaChat.getMessage(rid, mid); }

    /**
     * Obtiene un listado con todos los mensajes de una sala de chat.
     * @param rid Identificador de la sala.
     * @return Listado de todos los mensajes de la sala.
     */
    public List<Message> listMessages(String rid){ return PersistenciaChat.listMessages(rid); }

    /**
     * Elimina un mensaje de una sala de chat.
     * @param mid Identificador del mensaje a eliminar.
     * @param rid Identificador de la sala a la que pertenece el mensaje.
     */
    public void deleteMessage(String mid, String rid){ PersistenciaChat.deleteMessage(mid, rid); }

    /**
     * Elimina todos los mensajes de una sala de chat.
     * @param rid Identificador de la sala de chat.
     */
    public void wipeMessages(String rid){ PersistenciaChat.wipeMessages(rid); }

    /**
     * Comprueba si existe un mensaje en una sala de chat.
     * @param rid Identificador de la sala de chat a la que pertenece el mensaje.
     * @param mid Identificador del mensaje a comprobar.
     * @return True si el mensaje existe, False en caso contrario.
     */
    public boolean existMessage(String rid, String mid){ return PersistenciaChat.existMessage(rid, mid); }

    /**
     * Devuelve el número de mensajes que hay en una sala de chat.
     * @param rid Identificador de la sala de chat
     * @return Número de mensajes en la sala.
     */
    public int countMessages(String rid){ return PersistenciaChat.countMessages(rid); }

    /**
     * Genera un identificador para un nuevo mensaje.
     * @param rid Identificador del usuario.
     * @return Entero más alto encontrado libre para ser identificador.
     */
    public int generateMessageId(String rid){ return PersistenciaChat.generateMessageId(rid); }

    /**
     * Author: I. Jorquera.
     * Separates the room ID (rid) and returns the other user ID.
     * Parameter "rid" must contain "uid".
     * @param rid Room ID.
     * @param uid User ID.
     * @return An user ID.
     */
    public String separateRid(String rid, String uid){
        String [] split = rid.split("-");
        if(split[0].equals(uid)){
            return split[1];
        } else {
            return split[0];
        }
    }
}
