package com.igalda.scrimgg.neg;

import com.igalda.scrimgg.dom.notif.Notification;
import com.igalda.scrimgg.pers.PersistenciaNotification;

import java.util.ArrayList;
import java.util.List;

public class NegocioNotification {
    public NegocioNotification(){}

    /**
     * Añade una notificación a la BD.
     * @param n notificación a añadir a la BD.
     */
    public void addNotification(Notification n){ PersistenciaNotification.addNotification(n); }

    /**
     * Devuelve un listado de notificaciones pendientes de un usuario.
     * @param uid ID del usuario.
     * @return Lista de 'Notificacion' pendientes.
     */
    public List<Notification> listNotifications(String uid){ return PersistenciaNotification.listNotifications(uid); }

    /**
     * Elimina una notificación de la BD.
     * @param nid ID de la notificación.
     */
    public void deleteNotification(String nid, String uid){ PersistenciaNotification.deleteNotification(nid, uid); }

    /**
     * Elimina todas las notificaciones de un usuario.
     * @param uid ID del usuario.
     */
    public void wipeNotifications(String uid) { PersistenciaNotification.wipeNotifications(uid); }

    /**
     * Comprueba si una notificación existe o no en la BD.
     * @param nid notificación a comprobar si existe o no.
     * @param uid usuario al que pertenece la notificación.
     * @return True si la notificación existe, false en caso contrario.
     */
    public boolean existNotif(String nid, String uid){ return PersistenciaNotification.existNotif(nid, uid); }

    /**
     * Devuelve una notificación a partir de su id.
     * @param nid id de la notificación.
     * @param uid id del usuario al que pertenece la notificación.
     * @return Notificación solicitada.
     */
    public Notification getNotification(String nid, String uid){ return PersistenciaNotification.getNotification(nid, uid); }

    /**
     * Genera un identificador para una nueva notificación.
     * @param uid Identificador del usuario.
     * @return Entero más bajo encontrado libre para ser identificador.
     */
    public int generateNotificationId(String uid){ return PersistenciaNotification.generateNotificationId(uid); }



}
