/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import persistence.ClientBroker;
import problemDomain.Client;

/**
 *
 * @author Andrey
 */
public class ClientController {
    
    private ClientBroker cb = new ClientBroker();
    
    public boolean clientAction(String option, Client c){
        
        boolean result = false;
        
        if (option.equals("Add Client")) {
            result = addClient(c);
        }
                   
        
        return result;
    }
    
    public ArrayList<Client> showClients(String option, String fname, String lname, String gender, String phone) {
        ArrayList<Client> clientArr = null;
        
        if (option.equals("Show Client")) {
            
            clientArr = showClient(fname, lname, gender, phone);
            // return ??? новый метод -> clientsAction() ? тогда смысл опции option и submitAction 
            // в хендлере (придется проверять ифом)
        }
        return clientArr;
    }
    
    private boolean addClient(Client c) {
        boolean result = false;

        result = cb.addClient(c);
        
        return result;
    }
    
    private boolean delClient(Client c) {
        
        boolean res = false;
        
        cb.delClient(c);
        
        return res;
    }
    private ArrayList<Client> showClient(String fname, String lname, String gender, String phone) {
        
        ArrayList<Client> res = null;

        res = cb.showClient(fname, lname, gender, phone);
        
        return res;
    }
    
}
