/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.sql.*;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;

/**
 *
 * @author mathe
 */
public class CreateAccount {
    
    Connection conn = null;
    PreparedStatement prepstmt = null;
    ResultSet rs =null;
    
    static final String DRIVER = "com.mysql.jdbc.Driver"; 
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/banking_db";
    static final String USER = "root";
    static final String PASSWORD = "";
    
    private String id;
    private String accountNumber;
    private String fullName;
    private String emailAddress;
    private String gender;
    private String password;
    private String country;
    private String city;
    private String phoneNo;
    private double balance;
    
    public CreateAccount(String id, String name, String email, String gender, String country, String city, String phoneNo){
        JOptionPane.showMessageDialog(null, "Your account request has been submitted.","Alert",INFORMATION_MESSAGE);  
        this.id=id;
        this.fullName=name;
        this.emailAddress=email;
        this.gender=gender;
        this.country=country;
        this.city=city;
        this.phoneNo=phoneNo;
    }
    
    public void saveAccount(String accountNo, String pwd){
        this.accountNumber=accountNo;
        this.password=pwd;
        
        //Database connection
        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            prepstmt = conn.prepareCall("INSERT INTO `client`(`National ID`, `Client Account No`, "
                    + "`Client Name`, `Email address`, `Gender`, `Country`, `City`, `Phone number`) "
                    + "VALUES (?,?,?,?,?,?,?,?);"); 
            
            PreparedStatement prepstmt2 = conn.prepareCall("insert into account (`Account Number`, `Account Password`, `Account Balance`"
                    + ") values (?,?,?);");
            prepstmt2.setString(1, this.accountNumber);
            prepstmt2.setString(2, this.password);
            prepstmt2.setDouble(3, 0);
            prepstmt2.executeUpdate();
            
            prepstmt.setString(1, this.id);
            prepstmt.setString(2, this.accountNumber);
            prepstmt.setString(3, this.fullName);
            prepstmt.setString(4, this.emailAddress);
            prepstmt.setString(5, this.gender);
            prepstmt.setString(6, this.country);
            prepstmt.setString(7, this.city);
            prepstmt.setString(8, this.phoneNo);
            prepstmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Your account has been created and is ready for use","Alert",INFORMATION_MESSAGE);
            conn.close();
        } catch (ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex, "Alert", WARNING_MESSAGE);
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, ex, "Alert", WARNING_MESSAGE);
        }
    }
}
