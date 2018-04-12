/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author INETEL
 */
public class Magasin {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private String nom_magasin;
    private String description;
    private String adresse;
    private double latitude;
    private double longitude;
    private String Urlimg;
    private int proprietaire;

    public String getNom_magasin() {
        return nom_magasin;
    }

    public void setNom_magasin(String nom_magazin) {
        this.nom_magasin = nom_magazin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUrlimg() {
        return Urlimg;
    }

    public void setUrlimg(String Urlimg) {
        this.Urlimg = Urlimg;
    }

    public int getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(int proprietaire) {
        this.proprietaire = proprietaire;
    }

    

}
