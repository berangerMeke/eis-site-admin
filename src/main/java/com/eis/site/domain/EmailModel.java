package com.eis.site.domain;

public class EmailModel {

    //Attributs

    public String destinataire;
    public String objet;
    public String message;
    public boolean Multipart; /*Email a plusieurs partir */
    public boolean Html;


    //Constructeur par defaut

    public EmailModel(){

    }

    //Constructeur d initialisation 

    public EmailModel(String destinataire,String objet,String message,boolean Multipart,boolean Html){

        this.destinataire = destinataire;
        this.objet = objet;
        this.message = message;
        this.Multipart = Multipart;
        this.Html = Html;
    }

}
