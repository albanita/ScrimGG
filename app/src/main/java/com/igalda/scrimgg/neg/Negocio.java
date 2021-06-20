package com.igalda.scrimgg.neg;

public final class Negocio {

    private NegocioEquipo nEqu;
    private NegocioEnfrentamiento nEnf;
    private NegocioTorneo nTor;
    private NegocioLiga nLig;
    private NegocioUsuario nUsr;
    private NegocioCuenta nCue;
    private NegocioNotification nNot;
    private NegocioLigaProfesional nlp;
    private NegocioChat nCht;

    // private NegocioXxx nXxx;
    // ...

    private static Negocio negocio;

    private Negocio(){
        this.nEqu = new NegocioEquipo();
        this.nEnf = new NegocioEnfrentamiento();
        this.nTor = new NegocioTorneo();
        this.nLig = new NegocioLiga();
        this.nUsr = new NegocioUsuario();
        this.nCue = new NegocioCuenta();
        this.nNot = new NegocioNotification();
        this.nlp = new NegocioLigaProfesional();
        this.nCht = new NegocioChat();

        // this.nXxx = new NegocioXxx();
        // ...
    }

    public static Negocio getNegocio(){ // getInstance
        if (negocio == null){
            negocio = new Negocio();
        }
        return negocio;
    }

    public NegocioEquipo nEquipo() {
        return nEqu;
    }

    public NegocioEnfrentamiento nEnfrentamiento() {
        return nEnf;
    }

    public NegocioTorneo nTorneo() {
        return nTor;
    }

    public NegocioLiga nLiga() {
        return nLig;
    }

    public NegocioUsuario nUsuario(){ return nUsr; }

    public NegocioCuenta nCuenta(){ return nCue; }

    public NegocioNotification nNotification(){ return nNot; }

    public NegocioLigaProfesional nLigaProfesional(){ return this.nlp; }

    public NegocioChat nChat(){ return this.nCht; }

    // public NegocioXxx nXxx(){ return this.nXxx; }
}
