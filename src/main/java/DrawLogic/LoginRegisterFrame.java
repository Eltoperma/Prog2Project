package DrawLogic;

import GameData.User;
import GameLogic.GameController;
import GameLogic.GameHandler;
import dataLogic.UserDataService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.util.List;

public class LoginRegisterFrame extends JFrame {
    public LoginRegisterFrame() {
        List<User> users = UserDataService.loadUsers();
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

        for (User user : users) {
            comboBoxModel.addElement(user.getUsername());
        }
        String ip = "";
        try{
            ip =InetAddress.getLocalHost().getHostAddress();
        }catch(Exception e){
            e.printStackTrace();
        }
        JComboBox<String> existingUsers = new JComboBox<>(comboBoxModel);
        JTextField usernameInput = new JTextField();
        JTextField ipInput = new JTextField(ip);
        JButton register = new JButton("Registrieren");
        JButton login = new JButton("Login");

        JCheckBox isClient= new JCheckBox("Spectate?");

        // Create main panel with BoxLayout (vertical)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Add spacing between elements
        int spacing = 10;

        // Create a panel for the buttons with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, spacing, 0));
        buttonPanel.add(register);
        buttonPanel.add(login);

        // Add components to main panel
        mainPanel.add(new JLabel("User"));
        mainPanel.add(existingUsers);
        mainPanel.add(usernameInput);
        mainPanel.add(Box.createVerticalStrut(spacing));
        mainPanel.add(new JLabel("IP"));
        mainPanel.add(ipInput);
        mainPanel.add(Box.createVerticalStrut(spacing));
        mainPanel.add(isClient);
        mainPanel.add(Box.createVerticalStrut(spacing));
        mainPanel.add(buttonPanel);

        // Add main panel to frame
        add(mainPanel);

        // Action listeners for buttons
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = usernameInput.getText();
                if (newUsername.isEmpty() || newUsername.equals("Username") ) {
                    JOptionPane.showMessageDialog(null, "Ungültiger Nutzername!");
                } else if(UserDataService.userExists(newUsername))
                {
                    JOptionPane.showMessageDialog(null, "Nutzername existiert bereits!");
                } else {
                    UserDataService.addUser(new User(newUsername));
                    comboBoxModel.addElement(newUsername);
                    JOptionPane.showMessageDialog(null, "Erfolgreich registriert!");
                }
            }
        });
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) existingUsers.getSelectedItem();
                if (selectedUser != null && !selectedUser.isEmpty()) {
                    GameController.getDataHandler().login(selectedUser);
                    GameController.getNetworkHandler().setHost(!isClient.isSelected());
                    GameController.getNetworkHandler().setIp(ipInput.getText());
                    dispose();
                    GameController.openGameWindow();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a user to login.");
                }
            }
        });

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }
}
