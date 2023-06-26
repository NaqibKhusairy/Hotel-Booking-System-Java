package soalancgpt;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class output extends JFrame{
    
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openMenuItem,exitMenuItem;
    JTextArea outputArea;
    String Outputshow,nama,Ic,Phone,mail,roomtype,noroom,service,special,Pass,gend,DOB,negeri;
    int umur,id;
    double rm,ttlrm;
    String driver = "com.mysql.cj.jdbc.Driver";
    String url ="jdbc:mysql://localhost:3306?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String usr = "root";
    String password = "";  
    
    output(){
        setTitle("Output");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 870);
        setVisible(true);
        
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        
        openMenuItem = new JMenuItem("Form");
        fileMenu.add(openMenuItem);
        
        exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);
        
        outputArea = new JTextArea(390, 700);
        outputArea.setEditable(false);
        outputArea.setText("\t             Welcome to Database");
        add(outputArea);
        
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, usr, password);
            Statement stm = con.createStatement();
            
            String useDBQuery = "USE Hotel";
            stm.execute(useDBQuery);
            
            String selectQuery = "SELECT * FROM cus";
            ResultSet resultSet = stm.executeQuery(selectQuery);

            outputArea.setText("");
            if (!resultSet.isBeforeFirst()) {
                outputArea.setText("\t             Welcome to Database");
                JOptionPane.showMessageDialog(this, "No data found. Please fill the form first.");
            } else {
                while (resultSet.next()) {
                    id = resultSet.getInt("cus_id");
                    nama = resultSet.getString("nama");
                    Ic = resultSet.getString("Ic");
                    DOB = resultSet.getString("DOB");
                    umur = resultSet.getInt("umur");
                    gend = resultSet.getString("gend");
                    negeri = resultSet.getString("SOB");
                    Phone = resultSet.getString("Phone");
                    mail = resultSet.getString("mail");
                    roomtype = resultSet.getString("roomtype");
                    noroom = resultSet.getString("noroom");
                    service = resultSet.getString("service");
                    special = resultSet.getString("special");
                    rm = resultSet.getDouble("roomprice");
                    ttlrm = resultSet.getDouble("totalprice");
                    Pass = resultSet.getString("pass");

                    Outputshow = "Custumer Id : " + id + "\n";
                    Outputshow += "Name: " + nama + "\n";
                    Outputshow += "IC Number: " + Ic + "\n";
                    Outputshow += "Date of Birth: " + DOB + "\n";
                    Outputshow += "Age: " + umur + "\n";
                    Outputshow += "Gender: " + gend + "\n";
                    Outputshow += "Place of Birth: " + negeri + "\n";
                    Outputshow += "Phone Number: " + Phone + "\n";
                    Outputshow += "Email: " + mail + "\n";
                    Outputshow += "Room Type: " + roomtype + "\n";
                    Outputshow += "Number of Rooms: " + noroom + "\n";
                    Outputshow += "Services: " + service + "\n";
                    Outputshow += "Special Requirements: " + special + "\n";
                    Outputshow += "Room Price: " + rm + "\n";
                    Outputshow += "Total Price: " + ttlrm + "\n";
                    Outputshow += "Password: " + Pass + "\n";
                    Outputshow += "---------------------------------------\n";

                    outputArea.append(Outputshow);
                }
            }

            resultSet.close();
            stm.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output.this.dispose();
            }
        });
        
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                SoalanCGPT GPT = new SoalanCGPT();
                output.this.dispose();
            }
        });
    }

public static void main (String[] args) {
        output sd = new output();
    }
}
