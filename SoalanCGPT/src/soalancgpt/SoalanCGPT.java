package soalancgpt;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoalanCGPT extends JFrame{

    JPanel panel,panel2,panel3;
    JLabel nameLabel, icLabel, phoneLabel, emailLabel, roomTypeLabel, floorLabel, norLabel, servicesLabel,
            specialReqLabel,lblPassword;
    JTextField nameField, icField, phoneField, emailField;
    JPasswordField txtPassword;
    JComboBox<String> floorComboBox;
    JRadioButton[]roomTypes;
    ButtonGroup roomTypeGroup;
    JList<String> numberList;
    JScrollPane numscroll,outputscroll;
    JCheckBox roomServiceCheckBox, cleaningCheckBox, laundryCheckBox, wifiCheckBox;
    JTextArea specialReqArea , outputArea;
    JButton submitButton, clearButton,cancelButton;
    JCheckBox acceptTermsCheckBox;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openMenuItem,exitMenuItem;
    String Outputshow,nama,Ic,Phone,mail,roomtype,noroom,service,special,Pass,gend,DOB,negeri;
    int user=0 , year,month,date,umur,from,numroom;
    Long icnum;
    double rm,ttlrm;
    String driver = "com.mysql.cj.jdbc.Driver";
    String url ="jdbc:mysql://localhost:3306?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String User = "root";
    String password = "";  
    
    SoalanCGPT(){
        try{
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(url, User, password);
            Statement stm = kon.createStatement();
            String createDBQuery = "CREATE DATABASE IF NOT EXISTS Hotel";
            stm.executeUpdate(createDBQuery);

            String useDBQuery = "USE Hotel";
            stm.execute(useDBQuery);
        
            String createTableQuery = "CREATE TABLE IF NOT EXISTS cus ("
                    + "cus_id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "nama VARCHAR(100), "
                    + "Ic VARCHAR(20), "
                    + "DOB VARCHAR(200), "
                    + "umur VARCHAR(200), "
                    + "gend VARCHAR(200), "
                    + "SOB VARCHAR(200), "
                    + "Phone VARCHAR(200), "
                    + "mail VARCHAR(200), "
                    + "roomtype VARCHAR(200), "
                    + "noroom VARCHAR(200), " 
                    + "service VARCHAR(200), "
                    + "special VARCHAR(200), "
                    + "roomprice DOUBLE, "
                    + "totalprice DOUBLE,"
                    + "pass VARCHAR(200))";
        stm.executeUpdate(createTableQuery);
        }
        catch(ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null,"create database not success"+e);
        }
        setTitle("Hotel Check In Check Out");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 870);
        setVisible(true);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        nameLabel = new JLabel("Full Name *");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);
        
        nameField.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent j)
            {
                char va = j.getKeyChar();
                if(!Character.isAlphabetic(va))
                {
                    if(va !='\b' && va !=' ' && va !='\n' && va !='\r' )
                   {
                        j.consume();
                        JOptionPane.showMessageDialog(SoalanCGPT.this, "Name only can be inserted Alphabetic ONLY", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        icLabel = new JLabel("IC Number *");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(icLabel, gbc);

        icField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(icField, gbc);

        icField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char icformat = e.getKeyChar();
                if (!Character.isDigit(icformat)) {
                    if(icformat !='\b' && icformat !='\n')
                    {
                        e.consume();
                        JOptionPane.showMessageDialog(SoalanCGPT.this, "IC Number can only contain digits.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        
        phoneLabel = new JLabel("Phone Number *");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(phoneLabel, gbc);

        phoneField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(phoneField, gbc);
        
        phoneField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char pformat = e.getKeyChar();
                if (!Character.isDigit(pformat)) {
                    if(pformat !='\b' && pformat !='\n')
                    {
                        e.consume();
                        JOptionPane.showMessageDialog(SoalanCGPT.this, "Phone Number format : 0123456789", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        emailLabel = new JLabel("Email *");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(emailField, gbc);

        lblPassword = new JLabel("Password *");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lblPassword, gbc);

        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(txtPassword, gbc);

        roomTypeLabel = new JLabel("Room Type *");
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(roomTypeLabel, gbc);

        roomTypes = new JRadioButton[4];
        roomTypes[0] = new JRadioButton("Standard Single");
        roomTypes[1] = new JRadioButton("Double Twin");
        roomTypes[2] = new JRadioButton("Triple Deluxe");
        roomTypes[3] = new JRadioButton("Family Suite");

        roomTypeGroup = new ButtonGroup();
        for (JRadioButton radioButton : roomTypes) {
            roomTypeGroup.add(radioButton);
            panel.add(radioButton, gbc);
        }
        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(roomTypes[0], gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        panel.add(roomTypes[1], gbc);

        gbc.gridx = 1;
        gbc.gridy = 10;
        panel.add(roomTypes[2], gbc);

        gbc.gridx = 1;
        gbc.gridy = 11;
        panel.add(roomTypes[3], gbc);

        floorLabel = new JLabel("Floor Level *");
        gbc.gridx = 0;
        gbc.gridy = 12;
        panel.add(floorLabel, gbc);

        String[] floors = {"Floor 1", "Floor 2", "Floor 3", "Floor 4", "Floor 5",
                "Floor 6", "Floor 7", "Floor 8", "Floor 9", "Floor 10"};
        floorComboBox = new JComboBox<>(floors);
        gbc.gridx = 1;
        gbc.gridy = 12;
        panel.add(floorComboBox, gbc);

        norLabel = new JLabel("Number of Rooms *");
        gbc.gridx = 0;
        gbc.gridy = 13;
        panel.add(norLabel, gbc);

        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        numberList = new JList<>(numbers);
        numberList.setVisibleRowCount(3);
        gbc.gridx = 1;
        gbc.gridy = 13;
        numscroll = new JScrollPane(numberList);
        panel.add(numscroll, gbc);

        servicesLabel = new JLabel("Room Services *");
        gbc.gridx = 0;
        gbc.gridy = 14;
        panel.add(servicesLabel, gbc);

        roomServiceCheckBox = new JCheckBox("Room Service");
        gbc.gridx = 1;
        gbc.gridy = 14;
        panel.add(roomServiceCheckBox, gbc);

        cleaningCheckBox = new JCheckBox("Cleaning");
        gbc.gridx = 1;
        gbc.gridy = 15;
        panel.add(cleaningCheckBox, gbc);

        laundryCheckBox = new JCheckBox("Laundry");
        gbc.gridx = 1;
        gbc.gridy = 16;
        panel.add(laundryCheckBox, gbc);

        wifiCheckBox = new JCheckBox("Wi-Fi");
        gbc.gridx = 1;
        gbc.gridy = 17;
        panel.add(wifiCheckBox, gbc);

        specialReqLabel = new JLabel("Special Requirements, if any");
        gbc.gridx = 0;
        gbc.gridy = 18;
        panel.add(specialReqLabel, gbc);

        specialReqArea = new JTextArea(4, 20);
        gbc.gridx = 1;
        gbc.gridy = 18;
        panel.add(specialReqArea, gbc);

        acceptTermsCheckBox = new JCheckBox("I accept the Terms and Conditions");
        gbc.gridx = 0;
        gbc.gridy = 19;
        gbc.gridwidth = 2;
        panel.add(acceptTermsCheckBox, gbc);

        panel2 = new JPanel(); 
        gbc.gridx = 0;
        gbc.gridy = 20;
        gbc.gridwidth = 2;
        panel.add(panel2, gbc);
        
        submitButton = new JButton("Submit Application"); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel2.add(submitButton, gbc);
        
        cancelButton = new JButton("Cancel Application"); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel2.add(cancelButton, gbc);

        clearButton = new JButton("Clear");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel2.add(clearButton, gbc);
        
        panel3=new JPanel();
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder
        (Color.white),"Cust Information"));
        gbc.gridx = 0;
        gbc.gridy = 21;
        gbc.gridwidth = 2;
        panel.add(panel3, gbc);
        
        outputArea = new JTextArea(5,30);
        outputArea.setEditable(false);
        outputscroll = new JScrollPane(outputArea);
        panel3.add(outputscroll);

        add(panel);
        
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        
        openMenuItem = new JMenuItem("View Data");
        fileMenu.add(openMenuItem);
        
        exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);
        
        setLocationRelativeTo(null);
        pack();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {

            try{
            nama = nameField.getText();
            Ic = icField.getText();
            icnum=Long.parseLong(Ic);
            
            if(icnum%2==0)
            {
                gend="Female";
            }
            else 
            {
                gend="Male";
            }
            year=Integer.parseInt(Ic.substring(0,2));
            month=Integer.parseInt(Ic.substring(2,4));
            date=Integer.parseInt(Ic.substring(4,6));
            from=Integer.parseInt(Ic.substring(6,8));
            
            if(year>=0 && year <=23)
            {
                year+=2000;
            }
            else
            {
                year+=1900;
            }
            
            umur=2023-year;
            DOB=date+"/"+month+"/"+year;
            
            if (from==1||from==21||from==22||from==23||from==24)
            {
                negeri="Johor";
            }
            else if (from==2||from==25||from==26||from==27)
            {
                negeri="Kedah";
            }
            else if (from==3||from==28||from==29)
            {
                negeri="Kelantan";
            } 
            else if (from==4||from==30) 
            {
		negeri="Melaka";
            } 
            else if (from==5||from==31||from==59) 
            {
                negeri="Negeri Sembilan";
            } 
            else if (from==6||from==32||from==33) 
            {
		negeri="Pahang";
            } 
            else if (from==7||from==34||from==35) 
            {
		negeri="Pulau Pinang";
            } 
            else if (from==8||from==36||from==37||from==38||from==39) 
            {
		negeri="Perak";
            } 
            else if (from==9||from==40) 
            {
		negeri="Perlis";
            } 
            else if (from==10||from==41||from==42||from==43||from==44) 
            {
		negeri="Selangor";
            } 
            else if (from==11||from==45||from==46) {
		negeri="Terengganu";
            } 
            else if (from==12||from==47||from==48||from==49) 
            {
		negeri="Sabah";
            } 
            else if (from==13||from==50||from==51||from==52||from==53) 
            {
		negeri="Sarawak";
            } 
            else if (from==14||from==54||from==55||from==56||from==57) 
            {
		negeri="Kuala Lumpur";
            } 
            else if (from==15||from==58) 
            {
		negeri="Labuan";
            } 
            else if (from==16) 
            {
		negeri="Putrajaya";
            } 
            else 
            {
		negeri="Negeri Tidak Diketahui";
            }
            
            Phone = phoneField.getText();
            mail = emailField.getText();
            roomtype = getSelectedRoomType();
            if(roomTypes[0].isSelected())
            {
                roomtype="Standart Single";
                rm=180.00;
            }
            else if(roomTypes[1].isSelected())
            {
                roomtype="Double Twin";
                rm=230.00;
            }
            else if(roomTypes[2].isSelected())
            {
                roomtype="Triple Deluxe";
                rm=280.00;
            }
            else if(roomTypes[3].isSelected())
            {
                roomtype="Family Suite";
                rm=330.00;
            }
            noroom = numberList.getSelectedValue();
            numroom=Integer.parseInt(noroom);
            service = "";
            if (roomServiceCheckBox.isSelected()) {
                service += "Room Service, ";
            }
            if (cleaningCheckBox.isSelected()) {
                service += "Cleaning, ";
            }
            if (laundryCheckBox.isSelected()) {
                service += "Laundry, ";
            }
            if (wifiCheckBox.isSelected()) {
                service += "Wi-Fi, ";
            }
            
            ttlrm=rm*numroom;
            
            service = service.substring(0, service.length() - 2);
            special = specialReqArea.getText();
            
            if(special.equals(""))
            {
                special="No Special Requirements";
            }
            
            Pass = new String(txtPassword.getPassword());
            
            nama=nama.toUpperCase();
            gend=gend.toUpperCase();
            negeri=negeri.toUpperCase();
            roomtype=roomtype.toUpperCase();
            service=service.toUpperCase();
            special=special.toUpperCase();

            Outputshow = "Name: " + nama + "\n";
            Outputshow += "IC Number: " + Ic + "\n";
            Outputshow += "Date Of Birth: " + DOB + "\n";
            Outputshow += "Age: " + umur + "\n";
            Outputshow += "Gender: " + gend + "\n";
            Outputshow += "State of Birth: " + negeri + "\n";
            Outputshow += "Phone Number: " + Phone + "\n";
            Outputshow += "Email: " + mail + "\n";
            Outputshow += "Room Type: " + roomtype + "\n";
            Outputshow += "Number of Rooms: " + noroom + "\n";
            Outputshow += "Room Services: " + service + "\n";
            Outputshow += "Special Requirements: " + special + "\n";
            Outputshow += "Price Per room : RM " + rm + "\n";
            Outputshow += "Total Price : RM " + ttlrm + "\n";
            Outputshow += "-------------------------------------------------\n";
            outputArea.setVisible(true);
            
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(url, User, password);
            Statement stm = kon.createStatement();
                
            String useDBQuery = "USE Hotel"; 
            stm.execute(useDBQuery);
                
            String selectQuery =
                    "SELECT Ic FROM cus WHERE Ic = "
                    + "?";
            PreparedStatement selectStatement = 
                    kon.prepareStatement(selectQuery);
            selectStatement.setString(1, Ic);
            ResultSet resultSet = selectStatement.executeQuery();
                
                if (resultSet.next()) {
                    try {
                        
                        String query = "SELECT * FROM cus WHERE Ic = '" + Ic + "' AND pass = '" + Pass + "'";
                        ResultSet result = selectStatement.executeQuery(query);
                        if (result.next()) {
                            String sql = "UPDATE cus SET nama='" + nama + "', Ic='" + Ic + "', DOB='" + DOB + "', umur='" + umur 
                                    + "', gend='" + gend + "', SOB='" + negeri + "', " + "Phone='" + Phone + "', mail='" + mail + "', roomtype='" + roomtype 
                                    + "', noroom='" + noroom + "', " + "service='" + service + "', special='" + special + "', roomprice='" + rm 
                                    + "', " + "totalprice='" + ttlrm + "', " + "pass='" + Pass + "' WHERE Ic='" + Ic + "'";
                            
                            stm.executeUpdate(sql);
                            kon.close();

                            if(user==0)
                            {
                                user+=1;
                            }
                            user=user;
                            outputArea.append("User " + user + "\n" + Outputshow);
                            JOptionPane.showMessageDialog(null, "Successful update");
                        } else {
                            JOptionPane.showMessageDialog(null, "Wrong password");
                        }
                        result.close();
                    
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    String sql = "INSERT INTO cus (nama, Ic, DOB,umur,"
                            + "gend, "
                            + "SOB, Phone, "
                            + "mail, roomtype, "
                            + "noroom,service, special,"
                            + "roomprice, totalprice, pass) "
                            + "VALUES ('" + nama + "', '" 
                            + Ic + "', '" + DOB + "', '"
                            + umur + "', '" + gend + "', '" 
                            + negeri + "', '" + Phone + "', '"
                            + mail + "', '" + roomtype + "', '"
                            + noroom + "', '" + service + "', '"
                            + special + "', '" + rm + "', '"
                            + ttlrm + "', '" + Pass + "')";
                    
                    stm.executeUpdate(sql);
                    
                    user += 1;
                    outputArea.append("User " + user + "\n" + Outputshow);
                    
                    JOptionPane.showMessageDialog(SoalanCGPT.this, "Form submitted successfully!");
                }
            }
            catch(HeadlessException | ClassNotFoundException | SQLException f){
                JOptionPane.showMessageDialog(null,"not success");
            }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(SoalanCGPT.this, "Please complete the Form", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            String getSelectedRoomType() {
            for (JRadioButton radioButton : roomTypes) {
                if (radioButton.isSelected()) {
                    return radioButton.getText();
            }
            }
                return null;
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                icField.setText("");
                phoneField.setText("");
                emailField.setText("");
                specialReqArea.setText("");
                floorComboBox.setSelectedIndex(0);
                numberList.clearSelection();
                roomServiceCheckBox.setSelected(false);
                cleaningCheckBox.setSelected(false);
                laundryCheckBox.setSelected(false);
                wifiCheckBox.setSelected(false);
                acceptTermsCheckBox.setSelected(false);
                roomTypeGroup.clearSelection();
                txtPassword.setText("");
                outputArea.setText("");
                outputArea.setVisible(false);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                Ic = icField.getText();
                Pass = new String(txtPassword.getPassword());
                
                Class.forName(driver);
                Connection kon = DriverManager.getConnection(url, User, password);
                Statement stm = kon.createStatement();

                String useDBQuery = "USE Hotel"; 
                stm.execute(useDBQuery);

                String selectQuery =
                        "SELECT Ic FROM cus WHERE Ic = "
                        + "?";
                PreparedStatement selectStatement = 
                        kon.prepareStatement(selectQuery);
                selectStatement.setString(1, Ic);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {try {
                        
                        String query = "SELECT * FROM cus WHERE Ic = '" + Ic + "' AND pass = '" + Pass + "'";
                        ResultSet result = selectStatement.executeQuery(query);
                        if (result.next()) {
                            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel?", "Confirmation", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.YES_OPTION) {
                                String deleteQuery = "DELETE FROM cus WHERE Ic = ?";
                                PreparedStatement deleteStatement = kon.prepareStatement(deleteQuery);
                                deleteStatement.setString(1, Ic);
                                deleteStatement.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Data "+Ic+" deleted successfully");
                                

                                nameField.setText("");
                                icField.setText("");
                                phoneField.setText("");
                                emailField.setText("");
                                specialReqArea.setText("");
                                floorComboBox.setSelectedIndex(0);
                                numberList.clearSelection();
                                roomServiceCheckBox.setSelected(false);
                                cleaningCheckBox.setSelected(false);
                                laundryCheckBox.setSelected(false);
                                wifiCheckBox.setSelected(false);
                                acceptTermsCheckBox.setSelected(false);
                                roomTypeGroup.clearSelection();
                                txtPassword.setText("");
                                outputArea.setText("");
                                outputArea.setVisible(false);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Wrong password");
                        }
                        result.close();
                    
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Data Not Found");
                    }
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "No data found with the provided Ic");
                    }

                    resultSet.close();
                    kon.close();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error occurred while deleting data");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SoalanCGPT.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoalanCGPT.this.dispose();
            }
        });
        
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output show = new output();
                SoalanCGPT.this.dispose();
            }
        });
    }
    
    public static void main(String[] args) {
        SoalanCGPT GPT = new SoalanCGPT();
    }
    
}

/*cb1.addItemListener(new ItemListener() {                                //add ItemListenet to cb1
            @Override
            public void itemStateChanged(ItemEvent e) {                         //use itemStateChanged
                if (cb1.getState() && e.getItemSelectable() == cb1) {           //if cb1 chacked , do
                    cb3.setState(true);                                         //set cb3 chacked
                    cb4.setState(false);                                        //set cb4 dischaked
                    block=cb3.getLabel();                                       //set block to label in cb3
                }
            }
        });

lb5.addMouseListener(new MouseListener(){                               //add MouseListener to lb5
            @Override
            public void mouseEntered(MouseEvent e) {                            //use mouseEntered
                lb5.setText("Matric ID was your identity number from "          //set text to lb5
                        + "\nyour campus, your matric ID should start "
                        + "\nwith 10DDTXXXXXX");
            }

            @Override
            public void mouseExited(MouseEvent e) {                             //use mouseExited
                lb5.setText("What is ID Matric");                               //set text to lb5
            }

            @Override
            public void mouseClicked(MouseEvent e) {                            //mouseClicked
            }

            @Override
            public void mousePressed(MouseEvent e) {                            //mousePressed
            }

            @Override
            public void mouseReleased(MouseEvent e) {                           //mouseReleased
            }
        });
*/
