package com.igalda.scrimgg.neg;

import com.igalda.scrimgg.dom.Equipo;
import com.igalda.scrimgg.dom.Liga;
import com.igalda.scrimgg.dom.Match;
import com.igalda.scrimgg.pers.PersistenciaEquipo;
import com.igalda.scrimgg.pers.PersistenciaLiga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NegocioLiga {
    public NegocioLiga(){}

    /**
     * da de alta una liga en la base de datos, si esta no existe
     * @param liga
     */
    public void altaLiga(Liga liga){
        PersistenciaLiga.altaLiga(liga);
    }

    /**
     *
     * @param liga
     * @return da de baja la liga pasada como parametro y devuelve TRUE; FALSE en caso contrario.
     * Además, borra del equipo de la liga, la misma liga
     */
    public boolean bajaLiga(Liga liga){
        return PersistenciaLiga.bajaLiga(liga);
    }

    /**
     *
     * @param idLiga
     * @return la liga con el id pasado como parametro si existe; si no existe, devuelve null
     */
    public Liga buscarLiga(String idLiga){
        return PersistenciaLiga.buscarLiga(idLiga);
    }

    /**
     *
     * @return un listado con todas las ligas de la base de datos
     */
    public List<Liga> listadoLigas(){
        return PersistenciaLiga.listaLigas();
    }

    /**
     * @param liga
     * @return true si existe la liga dada; false en caso contrario
     */
    public boolean exists(Liga liga){
        return PersistenciaLiga.exists(liga);
    }

    /**
     *
     * @param liga
     * @return el listado de equipos de una liga
     */
    public List<Equipo> getEquipos(Liga liga){
        return PersistenciaLiga.getEquipos(liga);
    }

    /**
     *
     * @param idLiga
     * @return map con los ids de los equipos de una liga, con el idLiga pasado como parametro, y sus puntos.
     */
    public Map<String, Integer> clasificacion(String idLiga){
        return PersistenciaLiga.getEquiposPuntos(idLiga);
    }

    // Todo : revisar
    /*
    // Devuelve el listado de enfrentamientos acontecidos en la liga.
    public List<Enfrentamiento> getEnfrentamientos(String idLiga){
        List<Enfrentamiento> listaEnfrentamiento = new ArrayList<>();
        List<String> ids = PersistenciaLiga.getIdsEnfrentamientos(idLiga);
        for(String s : ids){
            Enfrentamiento e = PersistenciaEnfrentamiento.buscarEnfrentamiento(s);
            listaEnfrentamiento.add(e);
        }
        return listaEnfrentamiento;
    }
    */

    /**
     * añade un enfrentamiento (Match) a la liga dada
     * @param liga
     * @param match
     */
    public void addMatch(Liga liga, Match match){
        PersistenciaLiga.addMatch(liga, match);
    }

    /**
     *
     * @param liga
     * @return un listado de enfrentamientos de la liga
     */
    public List<Match> getMatches(Liga liga){
        return PersistenciaLiga.getMatches(liga);
    }

    /**
     *
     * @param liga
     * @param match
     * @return borra el enfrentamiento dado de la liga dada y devuelve true; false en caso contrario
     */
    public boolean deleteMatch(Liga liga, Match match){
        return PersistenciaLiga.deleteMatch(liga, match);
    }

    /**
     * actualiza el enfrentamiento dado de la liga dada
     * @param liga
     * @param matchToChange
     */
    public void updateMatch(Liga liga, Match matchToChange){
        PersistenciaLiga.updateMatch(liga, matchToChange);
    }

    /**
     *
     * @param liga
     * @param match
     * @return true si existe el enfrentamiento dado en la liga dada; false en caso contrario
     */
    public boolean existMatch(Liga liga, Match match){
        return PersistenciaLiga.existMatch(liga, match);
    }

    /**
     * @param liga
     * @param match
     * @return el equipo azul
     */
    public Equipo getBlueTeam(Liga liga, Match match){
        return PersistenciaLiga.getBlueTeam(liga, match);
    }

    /**
     * @param liga
     * @param match
     * @return el equipo rojo
     */
    public Equipo getRedTeam(Liga liga, Match match){
        return PersistenciaLiga.getRedTeam(liga, match);
    }

    /**
     * @param liga
     * @param match
     * @return el equipo ganador o null si aun no hay ningun equipo ganador
     */
    public Equipo getWinningTeam(Liga liga, Match match){
        return PersistenciaLiga.getWinningTeam(liga, match);
    }
}
