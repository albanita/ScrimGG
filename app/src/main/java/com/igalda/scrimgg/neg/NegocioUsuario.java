package com.igalda.scrimgg.neg;
import com.igalda.scrimgg.dom.Equipo;
import com.igalda.scrimgg.dom.Usuario;
import com.igalda.scrimgg.pers.*;

import java.util.ArrayList;
import java.util.List;

public class NegocioUsuario {

    public NegocioUsuario(){}

    /**
     * da de alta un nuevo usuario en la base de datos si este no existe
     * @param u
     */
    public void altaUsuario(Usuario u){
        PersistenciaUsuario.altaUsuario(u);
    }

    /**
     *
     * @param uid
     * @return borra el usuario de la base de datos con el uid dado si este existe y devuelve true; devuelve false en caso contrario
     */
    public boolean bajaUsuario(String uid){
        return PersistenciaUsuario.bajaUsuario(uid);
    }

    /**
     * Modifica los datos de un usuario existente. Recibe como parámetro un objeto Usuario que contiene los campos a modificar ya modificados.
     * @param usuModif
     */
    public void modificarUsuario(Usuario usuModif) { PersistenciaUsuario.modificarUsuario(usuModif); }

    /**
     *
     * @return un listado con todos los usuarios de la base de datos
     */
    public List<Usuario> litadoUsuarios(){
        return PersistenciaUsuario.listaUsuarios();
    }

    /**
     *
     * @param uid
     * @return true si existe el usuario con el uid dado; false en caso contrario
     */
    public boolean existeUsuario(String uid){
        return PersistenciaUsuario.existeUsuario(uid);
    }

    /**
     *
     * @param uid
     * @return el usuario con el uid pasado como parametro, o null si este no existe en la base de datos
     */
    public Usuario buscarUsuario(String uid){ return PersistenciaUsuario.buscarUsuario(uid); }

    /**
     *
     * @param uid
     * @return el listado de los equipos del usuario actual
     */
    public List<Equipo> getEquipos(String uid)
    {
        List<Equipo> equipos = new ArrayList<Equipo>();
        List<String> ids = PersistenciaUsuario.getIdsEquipos(uid);
        for(String s: ids){
            Equipo equipo = PersistenciaEquipo.buscarEquipoPorID(s);
            equipos.add(equipo);
        }
        return equipos;
    }

    /**
     *
     * @param uid
     * @return el listado de de los equipos (invitaciones), del usuario con el id dado
     */
    public List<Equipo> getInvitaciones(String uid){
        List<Equipo> equipos = new ArrayList<Equipo>();
        List<String> ids = PersistenciaUsuario.getIdsInvitaciones(uid);
        for(String s: ids){
            Equipo equipo = PersistenciaEquipo.buscarEquipoPorID(s);
            equipos.add(equipo);
        }
        return equipos;
    }

    /**
     *
     * @param idUsuario
     * @param idEquipo
     * añade un nuevo id de equipo a la colección de equipos del usuario con el id dado
     */
    public void anadirEquipo(String idUsuario, String idEquipo){
        PersistenciaUsuario.anadirEquipo(idUsuario, idEquipo);
    }

    /**
     *
     * @param userID
     * @param teamID
     * @return borra el id de equipo de la colección de equipos del usuario con el id dado y devuelve true; false en caso contrario
     */
    public boolean deleteTeam(String userID, String teamID){
        return PersistenciaUsuario.deleteTeam(userID, teamID);
    }

    /**
     *
     * @param idUsuario
     * @param idEquipoInvitado
     * añade una nueva invitación al usuario con el id dado
     */
    public void anadirInvitacion(String idUsuario, String idEquipoInvitado){
        PersistenciaUsuario.anadirInvitacion(idUsuario, idEquipoInvitado);
    }

    /**
     *
     * @param userID
     * @param teamID
     * @return borra la invitación con el teamID dado del usuario con el userID dado y devuelve true; false en caso contrario
     */
    public boolean deleteInvitation(String userID, String teamID){
        return PersistenciaUsuario.deleteInvitation(userID, teamID);
    }

}
