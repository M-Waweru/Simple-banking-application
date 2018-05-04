/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author mathe
 */
public class Login extends JFrame implements ActionListener {

    JLabel lblMessage, lblAccountNo, lblpwd;
    JTextField txtAccountNo;
    JButton btnLogin, btnRegister;
    JPasswordField pwdPassword;

    public Login() {
        setVisible(true);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");

        lblMessage = new JLabel("Login");
        lblMessage.setFont(new Font("Serif", Font.BOLD, 20));
        lblMessage.setBounds(200, 20, 100, 50);
        add(lblMessage);

        lblAccountNo = new JLabel("Account Name");
        lblAccountNo.setBounds(50, 70, 100, 50);
        add(lblAccountNo);

        lblpwd = new JLabel("Password");
        lblpwd.setBounds(50, 120, 100, 50);
        add(lblpwd);

        txtAccountNo = new JTextField();
        txtAccountNo.setBounds(180, 85, 200, 20);
        add(txtAccountNo);

        pwdPassword = new JPasswordField();
        pwdPassword.setBounds(180, 135, 200, 20);
        add(pwdPassword);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(200, 180, 100, 30);
        add(btnLogin);
        btnLogin.addActionListener(this);

        btnRegister = new JButton("Create an account");
        btnRegister.setBounds(175, 230, 150, 30);
        add(btnRegister);
        btnRegister.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {

            String accountNo = txtAccountNo.getText();
            String pwd = pwdPassword.getText();

            Account account1 = new Account(accountNo, pwd);
            
            if (account1.getLoginsuccess()){
                this.setVisible(false);
            }
        }
        if (e.getSource() == btnRegister) {
            new Registration().setVisible(true);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}
