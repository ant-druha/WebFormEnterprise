/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import problemDomain.Client;

/**
 *
 * @author Andrey
 */
public class ClientBroker {
    
    private Connection conn = null;

    public ClientBroker() {
        
        try {
            String driver = "oracle.jdbc.OracleDriver"; 
            Class.forName(driver); 

            //Шаг третий: Создание соединения 
            System.out.println("Connecting to database ..."); 
            String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl"; 
            String user = "webapp"; 
            String pass = "webapp"; 
            conn = DriverManager.getConnection(jdbcUrl, user, pass); 
        }
        catch (Exception e) {
            e.printStackTrace();
        }       
    }
    public boolean addClient(Client c) {
        boolean result = false;
        try {
            CallableStatement cs = conn.prepareCall("{? = call addClient(?,?,?,?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, c.getFname());
            cs.setString(3, c.getLname());
            cs.setString(4, c.getGender());
            cs.setString(5, c.getPhone());
            
            //cs.get
            
            cs.execute();
            
            Integer clientId;
            clientId = cs.getInt(1);
            if (clientId != null)
                result = true;
            cs.close();
            conn.close();
        }
        catch (SQLException e) {
            Logger.getLogger(ClientBroker.class.getName()).log(Level.SEVERE,null,e);
        }
        return result;
    }
    public boolean delClient(Client c) {
        
        boolean res = false;

        return res;
    }
    public ArrayList<Client> showClient(String fname, String lname, String gender, String phone) {
        
        ArrayList<Client> clientArr = new ArrayList<Client>();
        String stmt = null;
        PreparedStatement prst = null; 
        ResultSet rs;
try {
        
        // pl/sql function returning a table ? потом построить массив/список клиентов и передать его на страницу
        if (!fname.equals("") && !lname.equals("") && !gender.equals("") ) {
            
            stmt = "select client_id, first_name, last_name, email, phone_number, gender from clients "
                    + "where first_name = ? "
                    + "and last_name = ? "
                    + "and gender = ? ";
                    //+ "and phone_number = ?";
            prst = conn.prepareStatement(stmt);
            prst.setString(1, fname);
            prst.setString(2, lname);
            prst.setString(3, gender);
            //if (phone.equals("")) {
            //    phone = null;
            //}
            //prst.setString(4, phone);
        }
        else if (fname.equals("") && lname.equals("") && gender.equals("") && phone.equals("")) {
            stmt = "select client_id, first_name, last_name, email, phone_number, gender from clients";
            prst = conn.prepareStatement(stmt);
        }
            if (prst!=null) {                
                rs = prst.executeQuery();
                while (rs.next()) {
                    int clientId = rs.getInt("client_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String mail = rs.getString("email");
                    String pNum = rs.getString("phone_number");
                    String sex = rs.getString("gender");
                    //Client c = rs.get`
                    clientArr.add(new Client(clientId,firstName,lastName,sex,pNum,mail));
                }
            }
        }
        catch (SQLException e) {
            Logger.getLogger(ClientBroker.class.getName()).log(Level.SEVERE,null,e);
        }
        return clientArr;
    }
}
