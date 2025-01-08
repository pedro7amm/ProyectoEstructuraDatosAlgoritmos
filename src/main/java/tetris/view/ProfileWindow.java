package tetris.view;

import tetris.db.UserDAO;
import tetris.db.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileWindow extends JFrame {

    private JTextField emailField;
    private JTextField usernameField;
    private JLabel scoreLabel;
    private UserDAO userDAO;
    private String loggedUsername;

    public ProfileWindow(String username) {
        this.loggedUsername = username;  // Asignar el nombre de usuario a la propiedad
        setTitle("PERFIL DE JUGADOR");
        setSize(1200, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        // Inicializamos el DAO
        userDAO = new UserDAO();

        // Panel de perfil
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridLayout(3, 2));

        // Nombre de usuario (solo lectura)
        JLabel usernameLabel = new JLabel("NOMBRE DE USUARIO: ");
        usernameLabel.setForeground(Color.GREEN);
        usernameField = new JTextField(loggedUsername);  // Mostrar el nombre de usuario del jugador
        usernameField.setEditable(false);  // No editable
        panel.add(usernameLabel);
        panel.add(usernameField);

        // Email (editable)
        JLabel emailLabel = new JLabel("Correo Electrónico: ");
        emailLabel.setForeground(Color.GREEN);
        emailField = new JTextField();
        String userEmail = getEmailFromDatabase();  // Método para obtener el email desde la base de datos
        emailField.setText(userEmail);
        panel.add(emailLabel);
        panel.add(emailField);

        // Puntaje más alto
        scoreLabel = new JLabel("Puntaje más alto: " + getHighScoreFromDatabase(), SwingConstants.CENTER);
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(Color.BLACK);
        scoreLabel.setForeground(Color.GREEN);

        // Botón para editar perfil
        JButton editButton = new JButton("Editar Perfil");
        editButton.setBackground(Color.BLACK);
        editButton.setForeground(Color.GREEN);
        editButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProfile();
            }
        });

        // Botón para cerrar
        JButton closeButton = new JButton("CERRAR");
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.GREEN);
        closeButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Cierra la ventana
                // Aquí puedes añadir el código para volver al menú principal
            }
        });

        // Agregar elementos a la ventana
        add(panel);
        add(scoreLabel);
        add(editButton);
        add(closeButton);

        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);  // Esto centra la ventana
        setVisible(true);
    }

    // Método para obtener el correo electrónico desde la base de datos
    private String getEmailFromDatabase() {
        return userDAO.getEmail(loggedUsername);  // Aquí asumes que UserDAO tiene un método getEmail
    }

    // Método para obtener el puntaje más alto desde la base de datos
    private int getHighScoreFromDatabase() {
        return userDAO.getPlayer(loggedUsername).getHighestScore();  // Asumes que el método getPlayer retorna un objeto Player
    }

    // Método para editar el perfil (actualiza contraseña y correo)
    private void editProfile() {
        String newEmail = emailField.getText();
        String newPassword = JOptionPane.showInputDialog(this, "Ingresa la nueva contraseña:");

        if (newEmail != null && newPassword != null) {
            if (userDAO.updateUser(loggedUsername, newPassword, newEmail)) {
                JOptionPane.showMessageDialog(this, "Perfil actualizado con éxito.");
            } else {
                JOptionPane.showMessageDialog(this, "Hubo un error al actualizar el perfil.");
            }
        }
    }


}
