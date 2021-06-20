package com.igalda.scrimgg.neg;

import com.igalda.scrimgg.dom.TournamentProfesional;
import com.igalda.scrimgg.pers.PersistenciaLigaProfesional;

import java.util.ArrayList;
import java.util.List;

public class NegocioLigaProfesional {
    public NegocioLigaProfesional(){}
    /**
     * @param nombresLigas
     * @return una lista con los torneos de ligas profesionales cuyos nombres empiezan por los que se dan en la lista pasada como parametro; si a la lista se le pasa un "*" (asterísco),
     * o se deja vacía, cogerá todos los torneos de todas las ligas
     */
    public List<TournamentProfesional> getLigasProfesionales(List<String> nombresLigas)
    {
        return PersistenciaLigaProfesional.getLigasProfesionales(nombresLigas);
    }
}
