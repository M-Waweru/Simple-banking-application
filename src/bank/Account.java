/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mathe
 */
package bank;

import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;

public class Account {

    private String accountNumber;
    private String accountPassword;
    private double accountBalance;
    private double transactionAmount;
    private boolean login = false;

    Connection conn = null;
    PreparedStatement prepstmt = null;
    ResultSet rs = null;

    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/banking_db";
    static final String USER = "root";
    static final String PASSWORD = "";
    
    public Account(){};

    public Account(String accountNo) {
        this.accountNumber = accountNo;
    }

    public boolean getLoginsuccess() {
        return login;
    }

    public Account(String accountNumber, String accountPassword) {
        boolean isAccountNo = false;
        boolean isAccountpwd = false;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            String retrieveclients = "select * from account;";
            rs = stmt.executeQuery(retrieveclients);

            while (rs.next()) {
                if (accountNumber.equals(rs.getString("Account Number"))) {
                    isAccountNo=true;
                    if (accountPassword.equals(rs.getString("Account Password"))) {
                        isAccountpwd=true;
                        this.accountNumber = rs.getString("Account Number");
                        this.accountPassword = rs.getString("Account Password");
                        this.accountBalance = rs.getDouble("Account Balance");
                        JOptionPane.showMessageDialog(null, "Your account was found", "Success", INFORMATION_MESSAGE);
                        this.login = true;
                        new Accountservices(this.accountNumber).setVisible(true);
                        break;
                    }
                }
            }
            
            if (isAccountNo==false){
                JOptionPane.showMessageDialog(null, "Your account number may not exist or is wrong", "Alert", INFORMATION_MESSAGE);
            }
            if (isAccountpwd==false){
                JOptionPane.showMessageDialog(null, "Your password is incorrect", "Alert", INFORMATION_MESSAGE);
            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "Class Not Found", WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Problem encountered", WARNING_MESSAGE);
        }
    }

    public void withdraw(double amount) {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            prepstmt = conn.prepareCall("UPDATE `account` SET "
                    + "`Account Balance`=? WHERE `Account Number`=?");
            String retrieveclients = "select * from account;";
            rs = stmt.executeQuery(retrieveclients);

            while (rs.next()) {
                if (accountNumber.equals(rs.getString("Account Number"))) {
                    this.accountBalance = rs.getDouble("Account Balance");
                    this.transactionAmount = amount;

                    if (this.transactionAmount < accountBalance) {
                        accountBalance -= this.transactionAmount;
                        prepstmt.setDouble(1, accountBalance);
                        prepstmt.setString(2, accountNumber);
                        prepstmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Successful withdrawal");
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "You have insufficient balance");
                    }
                }
            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "Class Not Found", WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Problem encountered", WARNING_MESSAGE);
        }
    }

    public void deposit(double amount) {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            prepstmt = conn.prepareCall("UPDATE `account` SET "
                    + "`Account Balance`=? WHERE `Account Number`=?");
            String retrieveclients = "select * from account;";
            rs = stmt.executeQuery(retrieveclients);

            while (rs.next()) {
                if (accountNumber.equals(rs.getString("Account Number"))) {
                    this.accountBalance = rs.getDouble("Account Balance");
                    this.transactionAmount = amount;

                    this.transactionAmount = amount;
                    accountBalance += this.transactionAmount;
                    prepstmt.setDouble(1, accountBalance);
                    prepstmt.setString(2, accountNumber);
                    prepstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Successful deposit");
                    break;
                }
            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "Class Not Found", WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Problem encountered", WARNING_MESSAGE);
        }
    }
    
    public void transferMoney(String sender, String receiver, String amount){
        boolean transfersuccess = false;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            prepstmt = conn.prepareCall("UPDATE `account` SET "
                    + "`Account Balance`=? WHERE `Account Number`=?");
            String retrieveclients = "select * from account;";
            rs = stmt.executeQuery(retrieveclients);

            while (rs.next()) {
                if (sender.equals(rs.getString("Account Number"))) {
                    this.accountBalance = rs.getDouble("Account Balance");
                    if (this.accountBalance>Double.valueOf(amount)){
                        this.accountBalance-=Double.valueOf(amount);
                        prepstmt.setDouble(1, this.accountBalance);
                        prepstmt.setString(2, sender);
                        prepstmt.executeUpdate();
                        transfersuccess=true;
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "You have insufficient balance to send money", "Error", INFORMATION_MESSAGE);
                    }
                    break;
                }
            }
            
            while (rs.next()){
                if (transfersuccess==true){
                    if (receiver.equals(rs.getString("Account Number"))) {
                        this.accountBalance = rs.getDouble("Account Balance")+Double.valueOf(amount);
                        prepstmt.setDouble(1, this.accountBalance);
                        prepstmt.setString(2, receiver);
                        prepstmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "You have sent " + amount + " to " + 
                                receiver, "Transfer complete", WARNING_MESSAGE);
                    }
                }            
            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "Cannot connect to the database", WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Problem encountered in the database", WARNING_MESSAGE);
        }  
    }

    public void checkBalance() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            prepstmt = conn.prepareCall("UPDATE `account` SET "
                    + "`Account Balance`=? WHERE `Account Number`=?");
            String retrieveclients = "select * from account;";
            rs = stmt.executeQuery(retrieveclients);

            while (rs.next()) {
                if (accountNumber.equals(rs.getString("Account Number"))) {
                    this.accountBalance = rs.getDouble("Account Balance");
                    JOptionPane.showMessageDialog(null, "Account Number: " + this.accountNumber + "\nAccount Balance: " + this.accountBalance);
                    break;
                }
            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "Class Not Found", WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Problem encountered", WARNING_MESSAGE);
        }        
    }
}
