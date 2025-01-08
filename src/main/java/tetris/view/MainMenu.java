package tetris.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tetris.model.Board;
import tetris.db.UserSession; // Asegúrate de importar la clase UserSession
import tetris.db.UserDAO; // Importar UserDAO

// Clase MainMenu: Encargada de Mostrar las opciones del programa
public class MainMenu extends JFrame {

    private UserDAO userDAO;

    public MainMenu() {
        // Inicializar UserDAO
        userDAO = new UserDAO();

        // Configuración de la ventana principal
        setTitle("Tetris Game");
        setSize(1200, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal con botones
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));  // Incrementado a 6 filas para el botón de login
        panel.setBackground(Color.BLACK);

        // Botones para las diferentes opciones
        JButton loginButton = new JButton("🔑 Iniciar sesión / Registrarse");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.GREEN);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        Font fontLogin = new Font("Monospaced", Font.PLAIN, 35);
        loginButton.setFont(fontLogin);

        JButton playButton = new JButton("🎮 Jugar");
        playButton.setBackground(Color.BLACK);
        playButton.setForeground(Color.GREEN);
        playButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        Font fontPlay = new Font("Monospaced", Font.BOLD, 40);
        playButton.setFont(fontPlay);

        JButton profileButton = new JButton("️🎅 Perfil");
        profileButton.setBackground(Color.BLACK);
        profileButton.setForeground(Color.GREEN);
        profileButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        Font fontProfile = new Font("Monospaced", Font.PLAIN, 35);
        profileButton.setFont(fontProfile);

        JButton optionsButton = new JButton("⚙ Opciones");
        optionsButton.setBackground(Color.BLACK);
        optionsButton.setForeground(Color.GREEN);
        optionsButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        Font fontOptions = new Font("Monospaced", Font.PLAIN, 35);
        optionsButton.setFont(fontOptions);

        JButton statisticsButton = new JButton("📊 Estadísticas");
        statisticsButton.setBackground(Color.BLACK);
        statisticsButton.setForeground(Color.GREEN);
        statisticsButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        Font fontStatistics = new Font("Monospaced", Font.PLAIN, 35);
        statisticsButton.setFont(fontStatistics);

        JButton exitButton = new JButton("Salir");
        exitButton.setBackground(Color.BLACK);
        exitButton.setForeground(Color.GREEN);
        exitButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        Font fontExit = new Font("Monospaced", Font.BOLD, 35);
        exitButton.setFont(fontExit);

        // Añadir los botones al panel
        panel.add(loginButton);
        panel.add(playButton);
        panel.add(profileButton);
        panel.add(optionsButton);
        panel.add(statisticsButton);
        panel.add(exitButton);

        // Agregar el panel a la ventana
        add(panel, BorderLayout.CENTER);

        // Acción para el botón Login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLoginRegister();
            }
        });

        // Acción para el botón Play
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        // Acción para el botón Profile
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showProfile();
            }
        });

        // Acción para el botón Options
        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showOptions();
            }
        });

        // Acción para el botón Statistics
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStatistics();
            }
        });

        // Acción para el botón Exit
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGame();  // Método para salir del programa
            }
        });

        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);  // Esto centra la ventana

        // Hacer visible la ventana
        setVisible(true);
    }

    // Método para manejar el inicio de sesión o registro
    private void handleLoginRegister() {
        // Pedir al usuario el nombre de usuario
        String username = JOptionPane.showInputDialog(this, "Ingresa tu nombre de usuario:");
        if (username != null && !username.trim().isEmpty()) {
            // Verificar si el usuario ya existe
            boolean userExists = userDAO.userExists(username);  // Usar UserDAO para verificar si el usuario existe
            if (userExists) {
                // Si el usuario existe, pedir contraseña
                String password = JOptionPane.showInputDialog(this, "Ingresa tu contraseña:");
                String storedPassword = userDAO.getPassword(username);  // Obtener la contraseña almacenada en la base de datos
                if (storedPassword != null && storedPassword.equals(password)) {
                    // Si la contraseña es correcta, loguearse
                    UserSession.setLoggedInUser(username);
                    JOptionPane.showMessageDialog(this, "¡Bienvenido de nuevo, " + username + "!");
                    return; // Regresar al menú principal
                } else {
                    JOptionPane.showMessageDialog(this, "Contraseña incorrecta.");
                }
            } else {
                // Si el usuario no existe, pedir registro
                String password = JOptionPane.showInputDialog(this, "Ingresa una contraseña para registrarte:");
                String email = JOptionPane.showInputDialog(this, "Ingresa tu correo electrónico:");
                if (password != null && email != null) {
                    // Registrar al nuevo usuario en la base de datos
                    if (userDAO.createUser(username, password, email)) {
                        UserSession.setLoggedInUser(username); // Iniciar sesión después de registrarse
                        JOptionPane.showMessageDialog(this, "¡Registro exitoso! Bienvenido, " + username);
                    } else {
                        JOptionPane.showMessageDialog(this, "Hubo un error al registrar al usuario.");
                    }
                }
            }
        }
    }

    // Método para cerrar el programa
    private void exitGame() {
        System.exit(0);  // Cierra la aplicación
    }

    // Método para iniciar el juego
    private void startGame() {
        String username = UserSession.getLoggedInUser(); // Obtener el usuario logueado
        if (username != null) {
            Board board = new Board();  // Crear el modelo (tablero de juego)
            System.out.println("Iniciar juego...");
            new GameWindow(board);  // Llamar a la ventana del juego, pasando el board
            dispose(); // Cierra el menú principal
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, inicia sesión antes de jugar.");
        }
    }

    private void returnToMainMenu() {
        dispose();
        SwingUtilities.invokeLater(() -> new MainMenu());
    }

    // Método para mostrar el perfil del jugador
    private void showProfile() {
        String username = UserSession.getLoggedInUser();  // Obtener el nombre de usuario de la sesión
        if (username != null) {
            System.out.println("Mostrar perfil...");
            new ProfileWindow(username);  // Pasar el nombre de usuario al constructor
        } else {
            JOptionPane.showMessageDialog(this, "Inicia sesión primero.");
        }
    }

    // Método para mostrar las opciones
    private void showOptions() {
        System.out.println("Mostrar opciones...");
        new OptionsWindow(); // Llamar a una clase OptionsWindow
    }

    // Método para mostrar las estadísticas
    private void showStatistics() {
        String username = UserSession.getLoggedInUser();  // Obtener el nombre de usuario de la sesión
        if (username != null) {
            System.out.println("Mostrar estadísticas...");
            new StatisticsWindow(username);  // Pasar el nombre de usuario al constructor
        } else {
            JOptionPane.showMessageDialog(this, "Inicia sesión primero.");
        }
    }

    public static void main(String[] args) {
        new MainMenu(); // Iniciar el menú principal
    }
}
