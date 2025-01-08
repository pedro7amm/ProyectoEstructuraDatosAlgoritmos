/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tetris.view;

import tetris.model.Board;
import tetris.controller.GameController;
import tetris.utils.TimerUtil;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;



// Clase GameWindow: Encargada de integrar modelo, vista y controlador
public class GameWindow extends JFrame {
    private JLabel nextPieceLabel;
    private JLabel scoreLabel;
    private JLabel timerLabel;
    private JLabel linesClearedLabel;
    private JButton pauseButton;

    private boolean isPaused = false;
    private TimerUtil gameTimer; 
    private int timeElapsed = 0; 
    private int score = 0; 
    

    public GameWindow(Board board) {
        setTitle("TETRIS Game");
        //setUndecorated(true);
        setSize(690, 660);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
         // Aplicar un borde negro alrededor de toda la ventana
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 5), 
            new EmptyBorder(5, 5, 5, 5) 
        ));
        contentPane.setBackground(Color.BLACK);
        setContentPane(contentPane);
        
        // Crear y agregar el tablero de juego
        GameView gameView = new GameView(board);
        add(gameView, BorderLayout.CENTER);

        // Crear el controlador y conectar el modelo y la vista
        GameController controller = new GameController(board, gameView);
        
        // Registrar el listener para puntaje
        board.setScoreListener(() -> SwingUtilities.invokeLater(() -> updateScore(board.getScore())));

        // Panel lateral para mostrar información adicional
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(6, 1, 10, 10));
        sidePanel.setBackground(Color.BLACK);

        // Mostrar la siguiente pieza
        nextPieceLabel = new JLabel("SIGUIENTE PIEZA:");
        nextPieceLabel.setForeground(Color.GREEN);
        nextPieceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font fontnextPiece = new Font("Monospaced", Font.BOLD, 17);
        nextPieceLabel.setFont(fontnextPiece);
        sidePanel.add(nextPieceLabel);

        // Mostrar el puntaje
        scoreLabel = new JLabel("PUNTAJE: 0");
        scoreLabel.setForeground(Color.GREEN);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font fontscore = new Font("Monospaced", Font.BOLD, 17);
        scoreLabel.setFont(fontscore);
        sidePanel.add(scoreLabel);

        // Mostrar el temporizador
        timerLabel = new JLabel("⏱ TIEMPO: 0s");
        timerLabel.setForeground(Color.GREEN);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font fonttimer = new Font("Monospaced", Font.BOLD, 35);
        timerLabel.setFont(fonttimer);
        sidePanel.add(timerLabel);
        
        // Mostrar las líneas eliminadas
        linesClearedLabel = new JLabel("LÍNEAS ELIMINADAS: 0");
        linesClearedLabel.setForeground(Color.GREEN);
        linesClearedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font fontlinesCleared = new Font("Monospaced", Font.BOLD, 17);
        linesClearedLabel.setFont(fontlinesCleared);
        sidePanel.add(linesClearedLabel);

        // Botón de pausa
        pauseButton = new JButton("⏸");
        pauseButton.setBackground(Color.BLACK);
        pauseButton.setForeground(Color.GREEN);
        pauseButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        Font fontpause = new Font("Monospaced", Font.BOLD, 40);
        pauseButton.setFont(fontpause);
        pauseButton.addActionListener(e -> togglePause(controller));
        sidePanel.add(pauseButton);
        
        // Botón de cerrar
        JButton closeButton = new JButton("❎");
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.GREEN);
        closeButton.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        Font fontclose = new Font("Monospaced", Font.BOLD, 40);
        closeButton.setFont(fontclose);
        closeButton.addActionListener(e -> returnToMainMenu());
        sidePanel.add(closeButton);
        

        // Agregar el panel lateral
        add(sidePanel, BorderLayout.EAST);

        // Configurar el temporizador del juego usando TimerUtil
        configureGameTimer(controller);

        // Iniciar el juego
        controller.startGame();
        //pack();  
        setLocationRelativeTo(null);
        setVisible(true);
    }
    // Método para regresar al menú principal
    private void returnToMainMenu() {
       
        dispose(); // Cerrar ventana de juego
        SwingUtilities.invokeLater(() -> new MainMenu()); 
}
    // Configura el temporizador del juego usando TimerUtil
    private void configureGameTimer(GameController controller) {
        gameTimer = new TimerUtil(1000); 
        gameTimer.startTimer(() -> {
            if (!isPaused) {
                timeElapsed++;
                SwingUtilities.invokeLater(() -> timerLabel.setText("⏱: " + timeElapsed + "s"));
            }
        });
    }

    // Cambiar estado de pausa/reanudar
    private void togglePause(GameController controller) {
        isPaused = !isPaused;
        pauseButton.setText(isPaused ? "▶" : "⏸");
        controller.setPaused(isPaused);

        // Detener o reanudar el temporizador
        if (isPaused) {
            gameTimer.stopTimer();
        } else {
            configureGameTimer(controller);
        }
    }

    // Actualizar la puntuación
    public void updateScore(int newScore) {
        score = newScore;
        scoreLabel.setText("PUNTAJE: " + score);
    }

    // Mostrar la siguiente pieza 
    public void updateNextPiece(String nextPieceInfo) {
        nextPieceLabel.setText("<html>Siguiente pieza:<br>" + nextPieceInfo + "</html>");
    }

// Método para actualizar el contador de líneas eliminadas
    public void updateLinesCleared(int linesCleared) {
        linesClearedLabel.setText("Líneas eliminadas: " + linesCleared);
    }
    
}
