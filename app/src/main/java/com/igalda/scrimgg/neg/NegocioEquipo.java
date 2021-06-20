package com.igalda.scrimgg.neg;

import com.igalda.scrimgg.dom.*;
import com.igalda.scrimgg.pers.PersistenciaEquipo;
import com.igalda.scrimgg.pers.PersistenciaLiga;
import com.igalda.scrimgg.pers.PersistenciaTorneo;
import com.igalda.scrimgg.pers.PersistenciaUsuario;

import java.util.ArrayList;
import java.util.List;

public class NegocioEquipo {

    public NegocioEquipo(){}

    public void altaEquipo(Equipo e){
        PersistenciaEquipo.altaEquipo(e);
    }

    public boolean bajaEquipo(String nombre){
        //return PersistenciaEquipo.bajaEquipo(nombre);
        return false;
        //Todo: trabajamos con el objeto equipo directametne.
    }

    public Equipo buscarEquipoPorID(String id){
        return PersistenciaEquipo.buscarEquipoPorID(id);
    }

    public List<Equipo> buscarEquipoPorNombre(String nombre){
        return PersistenciaEquipo.buscarEquipoPorNombre(nombre);
    }

    public List<Equipo> listaEquipos(){
        return PersistenciaEquipo.listaEquipos();
    }

    public boolean existeEquipo(String idEqiopo){
        return PersistenciaEquipo.exist(new Equipo("", idEqiopo, "",privacidadEquipo.PRIVADO));
    }


    // todo: implementar!!!!!!!!!!!!!
    /*public List<Enfrentamiento> getEnfrentamientos(String idEquipo){
        return null;
    }*/

    public List<Usuario> getUsuariosInvitados(String idEquipo){
        List<Usuario> usuarios = new ArrayList<Usuario>();
        List<String> idsUsuarios = PersistenciaEquipo.getIdsUsuariosInvitados(idEquipo);
        for(String id: idsUsuarios){
            usuarios.add(PersistenciaUsuario.buscarUsuario(id));
        }
        return usuarios;
    }

    public List<Liga> getLigas(String idEquipo){
        List<Liga> ligas = new ArrayList<Liga>();
        List<String> idsLigas = PersistenciaEquipo.getIdsLigas(idEquipo);
        for(String id: idsLigas){
            ligas.add(PersistenciaLiga.buscarLiga(id));
        }
        return ligas;
    }

    public List<Usuario> getUsuariosSolicitud(String idEquipo){
        List<Usuario> usuarios = new ArrayList<Usuario>();
        List<String> ids = PersistenciaEquipo.getIdsUsuariosSolicitud(idEquipo);
        for(String id: ids){
            usuarios.add(PersistenciaUsuario.buscarUsuario(id));
        }
        return usuarios;
    }

    public List<Torneo> getTorneos(String idEquipo){
        List<Torneo> torneos = new ArrayList<Torneo>();
        List<String> ids = PersistenciaEquipo.getIdsTorneos(idEquipo);
        for(String id: ids){
            torneos.add(PersistenciaTorneo.buscarTorneo(id));
        }
        return torneos;
    }

    // devuelve todos los miembros del equipo con el id dado
    public List<Usuario> getPlayers(Equipo e){
        /*List<Usuario> miembros = new ArrayList<Usuario>();
        List<String> idsMiembros = PersistenciaEquipo.getIdsMiembros(idEquipo);
        for(String uid: idsMiembros){
            Usuario miembro = PersistenciaUsuario.buscarUsuario(uid);
            miembros.add(miembro);
        }
        return miembros;*/
        return PersistenciaEquipo.getPlayers(e);
    }

    public void anadirJugador(Equipo e, Usuario u, roles r){
        PersistenciaEquipo.newPlayer(e,u,r);
    }
}
