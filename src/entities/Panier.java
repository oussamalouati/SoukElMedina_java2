/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author INETEL
 */
public class Panier {

    Map<Produit, Integer> mapPan;

    public Map<Produit, Integer> getMapPan() {
        return mapPan;
    }

    public Panier() {
        this.mapPan = new HashMap<Produit, Integer>();
    }

    public void ajouterProduit(Produit prod, int qte) {
        mapPan.put(prod, qte);
        System.out.println("ajout dans panier avec succée");
    }

    public void supprimerArticle(Produit prod) {
        mapPan.remove(prod);
    }

    /*public void afficherpanier() {

        Set cles = mapPan.keySet();
        Iterator it = cles.iterator();
        while (it.hasNext()) {
            Produit cle = (Produit) it.next();
            int qte = mapPan.get(cle);
            System.out.println("produit:" + cle.getNom() + "qte:" + qte);

        }
    }*/

    public float calculerPanier() {
        float total = 0;
        Set cles = mapPan.keySet();
        Iterator it = cles.iterator();
        while (it.hasNext()) {
            Produit cle = (Produit) it.next();
            int qte = mapPan.get(cle);
            total += cle.getPrix() * qte;
        }
        return total;
    }

}
