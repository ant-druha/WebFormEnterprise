/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problemDomain;

/**
 *
 * @author Andrey
 */
public class Client {
    private int clientId;
    String fname, lname, gender, email, phone;

    public Client() {
    }

    public Client(String fname, String lname, String gender, String phone, String email) {
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
    }
    public Client(int clientId, String fname, String lname, String gender, String phone, String email) {
        this(fname, lname, gender, phone, email);
        this.clientId = clientId;   // very bad idea
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getClientId() {
    return clientId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public int getClientId(String phone) {
        return clientId;
    }
}
