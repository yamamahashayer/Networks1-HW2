package com.net1.tcpchat;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.sql.*;
import java.text.*;

public class TCPServer extends javax.swing.JFrame {
    
    private ServerSocket listeningSocket;
    private int currentPort;
    private ArrayList<Socket> onlineUsers;
    private ArrayList<Thread> onlineThreads;
    private ArrayList<String> onlineNames;
    private ArrayList<String> databaseUsernames;
    private ArrayList<String> databasePasswords;
    private DefaultListModel<String> onlineUsersModel = new DefaultListModel<>();
    private DefaultListModel<String> logModel = new DefaultListModel<>();
    private ArrayList<String> sendersNames;
    private java.util.List<Integer> userColors;
 

    
    public TCPServer() {
        initComponents();
        setLocationRelativeTo(null);

        onlineUsers = new ArrayList<>();
        onlineThreads = new ArrayList<>();
        onlineNames = new ArrayList<>();
        databaseUsernames = new ArrayList<>();
        databasePasswords = new ArrayList<>();
        sendersNames = new ArrayList<>();
       userColors=new ArrayList<>();
       
         try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
            String line;
             while ((line =br.readLine()) != null){
                String[] items = line.split(","); 
             
               if (items.length >= 4){
                    databaseUsernames.add(items[0]);
                    databasePasswords.add(items[1]);
                    
                    try {
                        int color = Integer.parseInt(items[2]);
                     //  System.out.println("Color parsed successfully: " + color);
                        userColors.add(color);
                    } catch (NumberFormatException e) {
                     e.printStackTrace();
                   }
                    
                    
                }
               //br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
       
    
  
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statusLabel = new javax.swing.JLabel();
        statusField = new javax.swing.JTextField();
        startButton = new javax.swing.JButton();
        portLabel = new javax.swing.JLabel();
        portField = new javax.swing.JTextField();
        onlineUsersLabel = new javax.swing.JLabel();
        onlineListScrollPane = new javax.swing.JScrollPane();
        onlineList = new javax.swing.JList<>();
        loggingAreaScrollPane = new javax.swing.JScrollPane();
        loggingArea = new javax.swing.JList<>(logModel);
        adduser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TCP Server");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        statusLabel.setText("Status:");

        statusField.setEditable(false);

        startButton.setText("Start Listening");
        startButton.setEnabled(false);
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        portLabel.setText("Port:");

        portField.setNextFocusableComponent(startButton);
        portField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateStartButtonState();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateStartButtonState();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateStartButtonState();
            }
        });

        onlineUsersLabel.setText("Online Users:");

        onlineList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        onlineListScrollPane.setViewportView(onlineList);

        loggingArea.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        loggingArea.setToolTipText("");
        loggingAreaScrollPane.setViewportView(loggingArea);

        adduser.setText("Add user");
        adduser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adduserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(statusLabel)
                                .addGap(18, 18, 18)
                                .addComponent(statusField))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(startButton)
                                .addGap(18, 18, 18)
                                .addComponent(portLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(portField))
                            .addComponent(loggingAreaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(adduser, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(onlineListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlineUsersLabel))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adduser, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(portLabel)
                    .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startButton)
                    .addComponent(onlineUsersLabel))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(loggingAreaScrollPane)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statusLabel)
                            .addComponent(statusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(onlineListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        if (portField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Empty Fields!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int serverPort = Integer.parseInt(portField.getText());

            try {
                listeningSocket = new ServerSocket(serverPort);
                currentPort = serverPort;
                new Thread(() -> {
                    while (true) {
                        Socket connectionSocket;
                        int clientPort;
                        InetAddress clientIP;
                        String address;
                        try {
                            connectionSocket = listeningSocket.accept();
                            clientPort = connectionSocket.getPort();
                            clientIP = connectionSocket.getInetAddress();

                            BufferedReader in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                            String message = in.readLine();
                            DataOutputStream out = new DataOutputStream(connectionSocket.getOutputStream());
                            message = message.substring(5);

                            String[] namePass = message.split("#Password#");
                            boolean isValid = false;

                            for (int i = 0; i < databaseUsernames.size(); i++) {
                                if (namePass[0].equals(databaseUsernames.get(i)) && namePass[1].equals(databasePasswords.get(i))) {
                                    isValid = true;
                                    out.writeBytes("ok" + '\n');
                                    
                                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                    String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(timestamp);
                                    String log = "Logged in by " + namePass[0] + "." + " IP = " + clientIP.toString().split("/")[1] + ", Port = " + clientPort;
                                    logModel.addElement(time + " " + log);
                                    sendersNames.add("log");
                                    updateLoggingArea();
                                    statusField.setText(log);
                                    break;
                                    
                                } else if (i == (databaseUsernames.size() - 1)) {
                                    out.writeBytes("invalid" + '\n');
                                    connectionSocket.close();
                                }

                            }

                            if (isValid) {
                                onlineNames.add(namePass[0]);
                                address = clientIP.toString().split("/")[1] + ", " + clientPort + ",  " + namePass[0];
                                onlineUsersModel.addElement(address + "\n");
                                onlineList.setModel(onlineUsersModel);

                                onlineUsers.add(connectionSocket);
                                onlineThreads.add(onlineThread(connectionSocket));
                                onlineThreads.get(onlineThreads.size() - 1).start();

                                String list = "online:";
                                for (int i = 0; i < onlineUsers.size(); i++) {
                                    list += (onlineUsers.get(i).getInetAddress().toString().split("/")[1] + ", " + onlineUsers.get(i).getPort() + ",  " + onlineNames.get(i) + "-");
                                }

                                for (int i = 0; i < onlineUsers.size(); i++) {
                                    DataOutputStream outToClient = new DataOutputStream(onlineUsers.get(i).getOutputStream());
                                    outToClient.writeBytes(list + '\n');
                                }
                            }

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }
                }).start();
                statusField.setText("Connection Started...");
            } catch (IOException e) {
                if (currentPort == serverPort) {
                    statusField.setText("Connection Already Started!");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Connection Failed! Please Try Another Port.", "Error", JOptionPane.ERROR_MESSAGE);
                    statusField.setText("Connection Failed! Please Try Another Port.");
                }
            }
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        for (int i = 0; i < onlineUsers.size(); i++) {
            try {
                DataOutputStream outToClient = new DataOutputStream(onlineUsers.get(i).getOutputStream());
                outToClient.writeBytes("serverOffline" + '\n');
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_formWindowClosing

    private void adduserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adduserActionPerformed
   String username = JOptionPane.showInputDialog(null, "Enter Username:");
    String password = JOptionPane.showInputDialog(null, "Enter Password:");

    if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
        // Convert the entered username to lowercase
        username = username.toLowerCase();
        
        // Check if the username already exists (case-insensitive)
        boolean usernameExists = false;
        for (String existingUsername : databaseUsernames) {
            if (existingUsername.equalsIgnoreCase(username)) {
                usernameExists = true;
                break;
            }
        }
        
        if (!usernameExists) {
            // Username does not exist, so add it to the list and write to the file
            databaseUsernames.add(username);
            databasePasswords.add(password);
            Color userColor = getRandomColor();
            try {
                FileWriter userFile = new FileWriter("user.txt", true);
                userFile.append(username).append(",").append(password).append(",").append(userColor.getRGB() + "").append(",").append("..").append("\n");
                userFile.close();
            } catch (IOException e) {
                // Handle IO exception
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "User added successfully!");
        } else {
            // Username already exists, display an error message
            JOptionPane.showMessageDialog(null, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        // Display an error message if username or password is empty or canceled
        JOptionPane.showMessageDialog(null, "Username or password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
    }
         
        
    }//GEN-LAST:event_adduserActionPerformed

    
    private Color getRandomColor() {
        // Generate random RGB values for the color
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new Color(r, g, b);
    } 
    
    
    
    
    
    
    
    
    
    
    public Thread onlineThread(Socket connectionSocket) {
        return new Thread(() -> {
            while (true) {
                BufferedReader inFromClient;
                try {
                    inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                    String message = inFromClient.readLine();

                    if (message.startsWith("/print ")) {
                        message = message.substring(7);
                        logModel.addElement(message);
                        sendersNames.add(message.split(" ")[2]);
                        updateLoggingArea();
                        statusField.setText("Received from IP = " + connectionSocket.getInetAddress().toString().split("/")[1] + ", Port = " + connectionSocket.getPort());
                        
                    } else if (message.equalsIgnoreCase("logout")) {
                        try (connectionSocket) {
                            DataOutputStream killStream = new DataOutputStream(connectionSocket.getOutputStream());
                            killStream.writeBytes("kill");
                            int removeIndex = onlineUsers.indexOf(connectionSocket);
                            
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(timestamp);
                            String log = "Logged out by " + onlineNames.get(removeIndex) + " .";
                            logModel.addElement(time + " " + log);
                            sendersNames.add("log");
                            updateLoggingArea();
                            statusField.setText(log);

                            onlineNames.remove(removeIndex);
                            onlineUsers.remove(connectionSocket);
                            DefaultListModel<String> listModel = (DefaultListModel<String>) onlineList.getModel();
                            listModel.remove(removeIndex);
                            onlineList.setModel(listModel);
                            
                            String address = String.valueOf(connectionSocket.getPort()) + connectionSocket.getInetAddress();
                            for (Socket socket : onlineUsers) {
                                DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
                                outToClient.writeBytes("offline:" + address + ":" + removeIndex + "\n");
                            }
                            break;
                        }

                    }
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    private void updateLoggingArea() {
        loggingArea.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
               
        
        System.err.println("value :"+value); 
        String usernamePart=null;
       
        if (value instanceof String) {
        String stringValue = (String) value;
        String[] logParts = stringValue.split("\\s+");
        // Assuming the username part is after "Logged in by"
        for (int i = 0; i < logParts.length; i++) {
        if (logParts[i].equals("by")) {
            usernamePart = logParts[i + 1];
             usernamePart = usernamePart.replaceAll("\\.$", "");
            break;
        }
        }
        }

     System.err.println(" usernamePart:"+usernamePart);
 /////////////////////////////////////////////////////////////////////////       
     
    for (int i=0;i<databaseUsernames.size();i++){
        if (usernamePart.equals(databaseUsernames.get(i))) {
        renderer.setForeground(new Color(userColors.get(i)));
          
        }
             
    }
         return renderer;
     
            }
               
       });
  }   
            
      
    
         
    private void updateStartButtonState() {
        if (portField.getText().trim().isEmpty()) {
            startButton.setEnabled(false);
        } else {
            startButton.setEnabled(true);
        }
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception exp) {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        com.formdev.flatlaf.FlatIntelliJLaf.setup();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TCPServer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adduser;
    private javax.swing.JList<String> loggingArea;
    private javax.swing.JScrollPane loggingAreaScrollPane;
    private javax.swing.JList<String> onlineList;
    private javax.swing.JScrollPane onlineListScrollPane;
    private javax.swing.JLabel onlineUsersLabel;
    private javax.swing.JTextField portField;
    private javax.swing.JLabel portLabel;
    private javax.swing.JButton startButton;
    public javax.swing.JTextField statusField;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
}
