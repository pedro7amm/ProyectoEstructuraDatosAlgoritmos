package tetris.view;

import tetris.db.Partida;
import tetris.db.PartidaDAO;
import tetris.db.Player;
import tetris.db.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class StatisticsWindow extends JFrame {
    private String username;

    public StatisticsWindow(String username) {
        this.username = username;
        setTitle("ESTADÍSTICAS DE " + username);
        setSize(1200, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        // Obtener estadísticas del jugador desde la base de datos
        UserDAO userDAO = new UserDAO();
        Player player = userDAO.getPlayer(username);

        // Mostrar estadísticas si el jugador existe
        if (player != null) {
            // Estilo general
            Font generalFont = new Font("Monospaced", Font.BOLD, 35);
            Color backgroundColor = Color.BLACK;
            Color foregroundColor = Color.GREEN;

            // Mostrar estadísticas del jugador
            JLabel statsLabel = new JLabel("🎯 PARTIDAS JUGADAS: " + player.getGamesPlayed(), SwingConstants.CENTER);
            statsLabel.setOpaque(true);
            statsLabel.setBackground(backgroundColor);
            statsLabel.setForeground(foregroundColor);
            statsLabel.setFont(generalFont);

            JLabel winsLabel = new JLabel("🏆 VICTORIAS: " + player.getGamesWon(), SwingConstants.CENTER);
            winsLabel.setOpaque(true);
            winsLabel.setBackground(backgroundColor);
            winsLabel.setForeground(foregroundColor);
            winsLabel.setFont(generalFont);

            add(statsLabel);
            add(winsLabel);

            // Buscar partidas por usuario
            JPanel searchPanel = new JPanel(new FlowLayout());
            searchPanel.setBackground(backgroundColor);

            JLabel searchLabel = new JLabel("🔎 Buscar partidas de un usuario: ");
            searchLabel.setFont(generalFont);
            searchLabel.setForeground(foregroundColor);

            JTextField searchText = new JTextField(20);
            searchText.setFont(generalFont);

            JButton searchButton = new JButton("Buscar");
            styleButton(searchButton, generalFont, backgroundColor, foregroundColor);
            searchButton.addActionListener(e -> searchPartidas(searchText.getText()));

            searchPanel.add(searchLabel);
            searchPanel.add(searchText);
            searchPanel.add(searchButton);
            add(searchPanel);

            // Ordenar partidas por fecha o puntaje
            JPanel sortPanel = new JPanel(new FlowLayout());
            sortPanel.setBackground(backgroundColor);

            JLabel sortLabel = new JLabel("🔀 Ordenar partidas por: ");
            sortLabel.setFont(generalFont);
            sortLabel.setForeground(foregroundColor);

            JButton sortByDateButton = new JButton("Fecha");
            styleButton(sortByDateButton, generalFont, backgroundColor, foregroundColor);
            sortByDateButton.addActionListener(e -> sortPartidas("fecha"));

            JButton sortByScoreButton = new JButton("Puntaje");
            styleButton(sortByScoreButton, generalFont, backgroundColor, foregroundColor);
            sortByScoreButton.addActionListener(e -> sortPartidas("puntaje"));

            sortPanel.add(sortLabel);
            sortPanel.add(sortByDateButton);
            sortPanel.add(sortByScoreButton);
            add(sortPanel);

            // Botón de cerrar
            JButton closeButton = new JButton("CERRAR");
            styleButton(closeButton, generalFont, backgroundColor, foregroundColor);
            closeButton.addActionListener(e -> dispose());
            add(closeButton);

        } else {
            JLabel errorLabel = new JLabel("No se encontraron estadísticas para este jugador.", SwingConstants.CENTER);
            errorLabel.setOpaque(true);
            errorLabel.setBackground(Color.BLACK);
            errorLabel.setForeground(Color.RED);
            Font fontError = new Font("Monospaced", Font.BOLD, 35);
            errorLabel.setFont(fontError);
            add(errorLabel);
        }

        setLocationRelativeTo(null);  // Centrar la ventana
        setVisible(true);
    }

    // Aplicar estilo uniforme a los botones
    private void styleButton(JButton button, Font font, Color background, Color foreground) {
        button.setFont(font);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setBorder(BorderFactory.createLineBorder(foreground));
    }

    // Buscar partidas por usuario
    private void searchPartidas(String searchUsername) {
        PartidaDAO partidaDAO = new PartidaDAO();
        LinkedList<Partida> partidas = partidaDAO.getPartidasByUser(searchUsername);
        if (partidas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron partidas para este usuario.");
        } else {
            showSecondaryWindow(partidas);
        }
    }

    // Ordenar las partidas
    private void sortPartidas(String sortBy) {
        PartidaDAO partidaDAO = new PartidaDAO();
        LinkedList<Partida> partidas = partidaDAO.getAllPartidasOrdered(sortBy);
        showSecondaryWindow(partidas);
    }

    // Mostrar partidas en una ventana secundaria
    private void showSecondaryWindow(LinkedList<Partida> partidas) {
        JFrame secondaryWindow = new JFrame("Resultados");
        secondaryWindow.setSize(800, 600);
        secondaryWindow.setLayout(new BorderLayout());

        StringBuilder sb = new StringBuilder("<html>");
        for (Partida p : partidas) {
            sb.append("Partida ID: ").append(p.getId())
                    .append(", Puntaje: ").append(p.getPuntaje())
                    .append(", Fecha: ").append(p.getFecha())
                    .append(", Ganada: ").append(p.isGanada() ? "Sí" : "No")
                    .append("<br>");
        }
        sb.append("</html>");

        JLabel partidasLabel = new JLabel(sb.toString(), SwingConstants.LEFT);
        partidasLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
        partidasLabel.setForeground(Color.WHITE);
        partidasLabel.setBackground(Color.BLACK);
        partidasLabel.setOpaque(true);

        JButton backButton = new JButton("VOLVER");
        backButton.setFont(new Font("Monospaced", Font.BOLD, 25));
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.GREEN);
        backButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        backButton.addActionListener(e -> secondaryWindow.dispose());

        secondaryWindow.add(partidasLabel, BorderLayout.CENTER);
        secondaryWindow.add(backButton, BorderLayout.SOUTH);
        secondaryWindow.setLocationRelativeTo(null);
        secondaryWindow.setVisible(true);
    }
}
