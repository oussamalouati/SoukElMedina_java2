/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iservices;

import java.sql.ResultSet;

/**
 *
 * @author INETEL
 */
public interface IproduitService {
      public ResultSet afficherTProduitCli(int idprod);
      public int nbprods();
      public ResultSet afficherProduit(int idprod); 
      
}
