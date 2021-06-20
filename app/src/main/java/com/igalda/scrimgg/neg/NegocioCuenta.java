package com.igalda.scrimgg.neg;

import com.igalda.scrimgg.dom.Cuenta;
import com.igalda.scrimgg.pers.PersistenciaCuenta;

public class NegocioCuenta {

    public NegocioCuenta(){}

    public Cuenta getCuenta(String idCuenta) { return PersistenciaCuenta.getCuenta(idCuenta); }

    public String getRankImg(String rank) { return PersistenciaCuenta.getRankImg(rank); }

}
