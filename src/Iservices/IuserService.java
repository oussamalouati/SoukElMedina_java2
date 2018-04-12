/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iservices;

import entities.User;

/**
 *
 * @author INETEL
 */
public interface IuserService {
    public void insertUser(User user);
    public void modfierUser(User user);
    public void supprimerUser(int id);
    public String authentification(String username,String password);
    public User afficherUser(int id);
    
}
