/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
/**
 *
 * @author mathe
 */
public class Accountservices extends JFrame implements ActionListener{
    JLabel lblMessage;
    JButton btnWithdraw, btnDeposit, btnCheckbalance, btnBack, btnTransfer, btnFullAccountdetails;
    
    private String accountNo;
    private double balance;
    
    public Accountservices(String accountNo){
        setVisible(true);
        setSize(500,400);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Bank services");
        
        lblMessage = new JLabel("Which operations would like to perform?");
        lblMessage.setFont(new Font("Serif", Font.BOLD, 20));
        lblMessage.setBounds(75, 40, 350, 30);
        add(lblMessage);
        
        btnWithdraw = new JButton("Withdraw");
        btnWithdraw.setBounds(175, 80, 150, 30);
        add(btnWithdraw);
        btnWithdraw.addActionListener(this);
        
        btnDeposit = new JButton("Deposit");
        btnDeposit.setBounds(175, 120, 150, 30);
        add(btnDeposit);
        btnDeposit.addActionListener(this);
        
        btnCheckbalance = new JButton("Check Balance");
        btnCheckbalance.setBounds(175, 160, 150, 30);
        add(btnCheckbalance);
        btnCheckbalance.addActionListener(this);
        
        btnBack = new JButton("Back");
        btnBack.setBounds(5, 10, 70, 30);
        add(btnBack);
        btnBack.addActionListener(this);
        
        btnTransfer = new JButton("Transfer Money");
        btnTransfer.setBounds(175, 200, 150, 30);
        add(btnTransfer);
        btnTransfer.addActionListener(this);
        
        this.accountNo=accountNo;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btnWithdraw){
            Account account1 = new Account(accountNo);
            String amount = JOptionPane.showInputDialog("Enter the amount you want to withdraw");
            account1.withdraw(Double.valueOf(amount));        
        }
        if (e.getSource()==btnDeposit){
            Account account1 = new Account(accountNo);
            String amount = JOptionPane.showInputDialog("Enter the amount you want to deposit");
            account1.deposit(Double.valueOf(amount));        
        }   
        if (e.getSource()==btnCheckbalance){
            Account account1 = new Account(accountNo);
            account1.checkBalance();
        }
        if (e.getSource()==btnBack){
            JOptionPane.showMessageDialog(null, "You are logging out of your account", "Attention", INFORMATION_MESSAGE);
            new Login().setVisible(true);
            this.setVisible(false);
        }
        if (e.getSource()==btnTransfer){
            Account account = new Account(accountNo);
            String receiver = JOptionPane.showInputDialog("Enter the Account name of the receiver");
            String amount = JOptionPane.showInputDialog("Enter the amount you want to send");
            account.transferMoney(accountNo, receiver, amount);
        }
    }
}
