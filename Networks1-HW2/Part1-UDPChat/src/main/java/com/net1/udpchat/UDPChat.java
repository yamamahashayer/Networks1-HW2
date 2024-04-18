package com.net1.udpchat;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

// Define a timer for automatically removing messages from the archive
public class UDPChat extends javax.swing.JFrame {

    private DatagramSocket socket;
    private DefaultListModel<String> logModel = new DefaultListModel<>();
    private ArrayList<Integer> colors = new ArrayList<>();
    private ArrayList<String> archivedMessages = new ArrayList<>();
    private boolean connectionStatus;
    private boolean running;
    private Timer archiveTimer;
    private HashMap<String, Timer> messageTimers = new HashMap<>();
    private HashMap<String, Long> expirationTimes = new HashMap<>(); // Store expiration times  
    private DefaultListModel<String> archivedMessagesModel = new DefaultListModel<>();
    JList<String> archivedMessagesList = new JList<>(archivedMessagesModel);

    public UDPChat() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userNameLabel = new javax.swing.JLabel();
        userNameField = new javax.swing.JTextField();
        sendingAreaScrollPane = new javax.swing.JScrollPane();
        sendingArea = new javax.swing.JTextArea();
        statusLabel = new javax.swing.JLabel();
        statusField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        connectPanel = new javax.swing.JPanel();
        localIpLabel1 = new javax.swing.JLabel();
        localPortLabel = new javax.swing.JLabel();
        localPortField = new javax.swing.JTextField();
        localIpField = new javax.swing.JTextField();
        remoteIpLabel = new javax.swing.JLabel();
        remotePortLabel = new javax.swing.JLabel();
        remoteIpField = new javax.swing.JTextField();
        remotePortField = new javax.swing.JTextField();
        testButton = new javax.swing.JButton();
        deleteMessageButton = new javax.swing.JButton();
        loggingAreaScrollPane = new javax.swing.JScrollPane();
        loggingArea = new javax.swing.JList<>(logModel);
        deleteConversationButton = new javax.swing.JButton();
        Archive = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("UDP Chat");
        setResizable(false);

        userNameLabel.setText("Username:");

        userNameField.setNextFocusableComponent(localIpField);
        userNameField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateTestButtonState();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateTestButtonState();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateTestButtonState();
            }
        });

        sendingArea.setColumns(20);
        sendingArea.setForeground(java.awt.Color.gray);
        sendingArea.setRows(5);
        sendingArea.setText("Enter Text Here");
        sendingArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sendingAreaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sendingAreaFocusLost(evt);
            }
        });
        sendingArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateSendButtonState();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateSendButtonState();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateSendButtonState();
            }
        });
        sendingAreaScrollPane.setViewportView(sendingArea);

        statusLabel.setText("Status:");

        statusField.setEditable(false);

        sendButton.setText("Send Message");
        sendButton.setEnabled(false);
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        localIpLabel1.setText("Local IP:");

        localPortLabel.setText("Local Port:");

        localPortField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateTestButtonState();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateTestButtonState();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateTestButtonState();
            }
        });

        localIpField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateTestButtonState();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateTestButtonState();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateTestButtonState();
            }
        });

        remoteIpLabel.setText("Remote IP:");

        remotePortLabel.setText("Remote Port:");

        remoteIpField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateTestButtonState();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateTestButtonState();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateTestButtonState();
            }
        });

        remotePortField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateTestButtonState();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateTestButtonState();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateTestButtonState();
            }
        });

        testButton.setText("Test Connection");
        testButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        testButton.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                testButtonFocusLost(evt);
            }
        });
        testButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout connectPanelLayout = new javax.swing.GroupLayout(connectPanel);
        connectPanel.setLayout(connectPanelLayout);
        connectPanelLayout.setHorizontalGroup(
            connectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connectPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(connectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(connectPanelLayout.createSequentialGroup()
                        .addComponent(testButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(connectPanelLayout.createSequentialGroup()
                        .addGroup(connectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(localIpLabel1)
                            .addComponent(localPortLabel)
                            .addComponent(remoteIpLabel)
                            .addComponent(remotePortLabel))
                        .addGap(18, 18, 18)
                        .addGroup(connectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(localPortField, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .addComponent(localIpField)
                            .addComponent(remoteIpField)
                            .addComponent(remotePortField))))
                .addContainerGap())
        );
        connectPanelLayout.setVerticalGroup(
            connectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connectPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(connectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(localIpLabel1)
                    .addComponent(localIpField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(connectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(localPortField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(localPortLabel))
                .addGap(46, 46, 46)
                .addGroup(connectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(remoteIpLabel)
                    .addComponent(remoteIpField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(connectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(remotePortLabel)
                    .addComponent(remotePortField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(testButton)
                .addContainerGap())
        );

        deleteMessageButton.setText("Delete Message");
        deleteMessageButton.setEnabled(false);
        deleteMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMessageButtonActionPerformed(evt);
            }
        });

        loggingArea.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        loggingArea.setToolTipText("");
        loggingArea.setMaximumSize(new java.awt.Dimension(62, 20));
        loggingArea.setMinimumSize(new java.awt.Dimension(62, 20));
        loggingArea.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                loggingAreaValueChanged(evt);
            }
        });
        loggingArea.getModel().addListDataListener(new javax.swing.event.ListDataListener() {
            public void intervalAdded(javax.swing.event.ListDataEvent e) {
                if (loggingArea.getModel().getSize() > 0) {
                    deleteConversationButton.setEnabled(true);
                }
            }

            public void intervalRemoved(javax.swing.event.ListDataEvent e) {
                if (loggingArea.getModel().getSize() == 0) {
                    deleteConversationButton.setEnabled(false);
                }
            }

            public void contentsChanged(javax.swing.event.ListDataEvent e) {
                if (loggingArea.getModel().getSize() > 0) {
                    deleteConversationButton.setEnabled(true);
                } else {
                    deleteConversationButton.setEnabled(false);
                }
            }
        });
        loggingAreaScrollPane.setViewportView(loggingArea);

        deleteConversationButton.setText("Delete Conversation");
        deleteConversationButton.setEnabled(false);
        deleteConversationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteConversationButtonActionPerformed(evt);
            }
        });

        Archive.setText("Archive");
        Archive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArchiveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(statusLabel)
                                .addGap(18, 18, 18)
                                .addComponent(statusField))
                            .addComponent(sendingAreaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(Archive, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(loggingAreaScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(userNameLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(userNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(deleteMessageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(deleteConversationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(connectPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userNameLabel)
                    .addComponent(userNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(loggingAreaScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deleteMessageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deleteConversationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(connectPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sendingAreaScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statusLabel)
                            .addComponent(statusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Archive, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        try {
            int destPort = Integer.parseInt(remotePortField.getText());
            InetAddress destIP = InetAddress.getByName(remoteIpField.getText());
            String dataMessage = sendingArea.getText();

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(timestamp);
            String finalMessage = time + " " + userNameField.getText() + ": " + dataMessage;

            colors.add(1);
            logModel.addElement(finalMessage);
            updateLoggingArea();
            sendingArea.setText("");
            sendData(destIP, destPort, finalMessage);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Remote IP or Remote Port!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(this, "Test Connection before start sending.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    private void testButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testButtonActionPerformed
        connectionStatus = false;
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }

        if (localPortField.getText().trim().isEmpty() || localIpField.getText().trim().isEmpty()
                || remoteIpField.getText().trim().isEmpty() || remotePortField.getText().trim().isEmpty()
                || userNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "You should enter all of text fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            try {
                int sourcePort = Integer.parseInt(localPortField.getText());
                String sourceIP = localIpField.getText();

                try {
                    socket = new DatagramSocket(sourcePort);
                    JOptionPane.showMessageDialog(this, "Connection Started...", "Success", JOptionPane.INFORMATION_MESSAGE);
                    statusField.setText("Connection Started...");
                    connectionStatus = true;
                    updateSendButtonState();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Connection Failed! Please Try Another Port.", "Error", JOptionPane.ERROR_MESSAGE);
                    statusField.setText("Connection Failed! Please Try Another Port.");
                }

                new Thread(() -> {
                    running = true;
                    while (running) {
                        try {
                            byte[] receivedData = new byte[2048];
                            DatagramPacket packet = new DatagramPacket(receivedData, receivedData.length);
                            socket.receive(packet);
                            String message = new String(packet.getData());

                            message = message.trim();

                            try {
                                int index = Integer.parseInt(message);
                                logModel.removeElementAt(index);
                                colors.remove(index);
                            } catch (NumberFormatException ex) {
                                if (message.equals("clear")) {
                                    logModel.removeAllElements();
                                    colors.clear();
                                } else {
                                    colors.add(0);
                                    logModel.addElement(message);
                                    updateLoggingArea();
                                    statusField.setText("Received from IP = " + packet.getAddress().toString().split("/")[1] + ", Port = " + packet.getPort());
                                }
                            }

                        } catch (Exception e) {
                            break;
                        }
                    }
                }).start();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Local IP or Local Port!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_testButtonActionPerformed

    private void deleteMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMessageButtonActionPerformed
        int destPort = Integer.parseInt(remotePortField.getText());
        try {
            InetAddress destIP = InetAddress.getByName(remoteIpField.getText());
            int[] selectedIndices = loggingArea.getSelectedIndices();
            if (selectedIndices.length > 0) {
                int index = selectedIndices[0];
                archiveTimer = null;
                String deletedMessage = logModel.getElementAt(index);
                moveMessageToArchive(deletedMessage);
                logModel.removeElementAt(index);
                colors.remove(index);
                sendIndex(destIP, destPort, String.valueOf(index));
            } else {
                // Handle case when no item is selected

            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_deleteMessageButtonActionPerformed


    private void deleteConversationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteConversationButtonActionPerformed
        int destPort = Integer.parseInt(remotePortField.getText());
        try {
            InetAddress destIP = InetAddress.getByName(remoteIpField.getText());

            // Move all messages to the archive
            for (int i = 0; i < logModel.size(); i++) {
                String deletedMessage = logModel.getElementAt(i);
                moveMessageToArchive(deletedMessage);
            }

            // Clear the logModel
            logModel.removeAllElements();

            // Clear the colors list
            colors.clear();

            // Send command to clear conversation
            sendIndex(destIP, destPort, "clear");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_deleteConversationButtonActionPerformed

    private void loggingAreaValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_loggingAreaValueChanged
        deleteMessageButton.setEnabled(!loggingArea.isSelectionEmpty());
    }//GEN-LAST:event_loggingAreaValueChanged

    private void sendingAreaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sendingAreaFocusGained
        if (sendingArea.getText().equals("Enter Text Here")) {
            sendingArea.setText("");
            sendingArea.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_sendingAreaFocusGained

    private void sendingAreaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sendingAreaFocusLost
        if (sendingArea.getText().trim().isEmpty()) {
            sendingArea.setForeground(Color.GRAY);
            sendingArea.setText("Enter Text Here");
            sendButton.setEnabled(false);
        }
    }//GEN-LAST:event_sendingAreaFocusLost

    private void testButtonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_testButtonFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_testButtonFocusLost

    private void ArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArchiveActionPerformed


        JFrame archiveFrame = new JFrame("Archived Messages");
        archiveFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JScrollPane archiveScrollPane = new JScrollPane();
      
         archivedMessagesList = new JList<>(archivedMessages.toArray(new String[0])); 
         archivedMessagesList.setModel(archivedMessagesModel);
        // Create JList for archived messages
        archiveScrollPane.setViewportView(archivedMessagesList);
        archiveFrame.getContentPane().add(archiveScrollPane);

        // Restore button
        JButton restoreButton = new JButton("Restore");
        archiveFrame.getContentPane().add(restoreButton, BorderLayout.SOUTH);
        initRestoreButton(restoreButton); // Initialize restoreButton after adding it to the frame

        // Restore all button
        JButton restoreAllButton = new JButton("Restore All");
        archiveFrame.getContentPane().add(restoreAllButton, BorderLayout.NORTH);
        initRestoreAllButton(restoreAllButton); // Initialize restoreAllButton after adding it to the frame

        archiveFrame.pack();
        archiveFrame.setLocationRelativeTo(this); // Center the archive frame relative to the main frame
        archiveFrame.setSize(600, 500);
        archiveFrame.setVisible(true);
    }//GEN-LAST:event_ArchiveActionPerformed
    private void initRestoreButton(JButton restoreButton) {
        restoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                int[] selectedIndices = archivedMessagesList.getSelectedIndices();
            if (selectedIndices.length > 0) {
                int index = selectedIndices[0];
                archiveTimer = null;
               String selectedMessage = archivedMessagesModel.getElementAt(index);
                
                if (selectedMessage != null) {
                    // Cancel the timer associated with the message
                Timer timer = messageTimers.remove(selectedMessage);
                if (timer != null) {
                    timer.cancel();
                }
                    // Remove the message from the archive model and update the list
                    archivedMessagesModel.removeElementAt(index);
                    archivedMessagesList.setModel(archivedMessagesModel);
                    
                    restoreMessage(selectedMessage);
                } else {
                    // Handle case when no item is selected
                    
                }
            }
            }
        });
    }

    private void initRestoreAllButton(JButton restoreAllButton) {
        restoreAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restoreAllMessages();
            }
        });
    }

    private void restoreMessage(String message) {
       
          int destPort = Integer.parseInt(remotePortField.getText());
        try {
            InetAddress destIP = InetAddress.getByName(remoteIpField.getText());
        colors.add(2);
        // Restore the message to the logModel
        logModel.addElement(message);
        updateLoggingArea();
       sendData(destIP, destPort, message);

        

        // Show a message dialog confirming the restoration of the message
        JOptionPane.showMessageDialog(null, "Message restored successfully.");

        //System.out.println("Restoring message: " + message);
        } catch (UnknownHostException ex) {
            Logger.getLogger(UDPChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void restoreAllMessages() {
        for (int i = 0; i < archivedMessagesModel.getSize(); i++) {
            int destPort = Integer.parseInt(remotePortField.getText());
        try {
            InetAddress destIP = InetAddress.getByName(remoteIpField.getText());
            String message = archivedMessagesModel.getElementAt(i);
            colors.add(2);
            // Restore the message to the logModel
            logModel.addElement(message);
            updateLoggingArea();
             sendData(destIP, destPort, message);
             
              archivedMessagesModel.removeElementAt(i);
                    archivedMessagesList.setModel(archivedMessagesModel);
             } catch (UnknownHostException ex) {
            Logger.getLogger(UDPChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        updateLoggingArea();

        // Clear the archive
        archivedMessagesModel.removeAllElements();
        for (Timer timer : messageTimers.values()) {
            timer.cancel();
        }
        messageTimers.clear();
        expirationTimes.clear();
   // Show a message dialog confirming the restoration of the message
        JOptionPane.showMessageDialog(null, "All messages restored. successfully.");
    }


    private void moveMessageToArchive(String message) {

        archivedMessages.add(message);
        archivedMessagesModel.addElement(message);
        if (archiveTimer == null) {
            archiveTimer = new Timer();
            archiveTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // Automatically remove messages from the archive after 2 minutes
                    archivedMessages.remove(message);
                    archivedMessagesModel.removeElement(message);
                    archivedMessagesList.setModel(archivedMessagesModel);
                    archiveTimer.cancel(); // Cancel the timer after clearing the archive
                    archiveTimer = null; // Reset the timer reference
                }
            },2 * 60* 1000); // 2 minutes           //}, 2 * 60 * 1000); // 2 minutes
        }
    }

    private void updateLoggingArea() {
        loggingArea.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (colors.get(index) == 0) {
                    renderer.setForeground(Color.RED);
                } else if (colors.get(index) == 1) {
                    renderer.setForeground(Color.BLUE);
                } else {
                    renderer.setForeground(Color.GREEN);
                }

                return renderer;
            }
        });
    }

    private void updateSendButtonState() {
        if (sendingArea.getText().trim().isEmpty()) {
            sendButton.setEnabled(false);
        } else if (connectionStatus && !sendingArea.getForeground().equals(Color.GRAY)) {
            sendButton.setEnabled(true);
        }
    }

    private void updateTestButtonState() {
        if (userNameField.getText().trim().isEmpty() || localIpField.getText().trim().isEmpty()
                || localPortField.getText().trim().isEmpty() || remoteIpField.getText().trim().isEmpty()
                || remotePortField.getText().trim().isEmpty()) {
            testButton.setEnabled(false);
        } else {
            testButton.setEnabled(true);
        }
    }

    public void sendData(InetAddress destIP, int destPort, String message) {
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, destIP, destPort);
        try {
            socket.send(sendPacket);
            statusField.setText("Sent to IP = " + sendPacket.getAddress().toString().split("/")[1] + ", Port = " + destPort);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendIndex(InetAddress destIP, int destPort, String index) {
        byte[] sendData = index.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, destIP, destPort);
        try {
            socket.send(sendPacket);
        } catch (IOException ex) {
            ex.printStackTrace();
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
                new UDPChat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Archive;
    private javax.swing.JPanel connectPanel;
    private javax.swing.JButton deleteConversationButton;
    private javax.swing.JButton deleteMessageButton;
    private javax.swing.JTextField localIpField;
    private javax.swing.JLabel localIpLabel1;
    private javax.swing.JTextField localPortField;
    private javax.swing.JLabel localPortLabel;
    public javax.swing.JList<String> loggingArea;
    private javax.swing.JScrollPane loggingAreaScrollPane;
    private javax.swing.JTextField remoteIpField;
    private javax.swing.JLabel remoteIpLabel;
    private javax.swing.JTextField remotePortField;
    private javax.swing.JLabel remotePortLabel;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextArea sendingArea;
    private javax.swing.JScrollPane sendingAreaScrollPane;
    public javax.swing.JTextField statusField;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JButton testButton;
    private javax.swing.JTextField userNameField;
    private javax.swing.JLabel userNameLabel;
    // End of variables declaration//GEN-END:variables
}
