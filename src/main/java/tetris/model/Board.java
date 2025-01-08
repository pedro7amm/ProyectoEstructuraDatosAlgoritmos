/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// Archivo: model/Board.java
package tetris.model;

import java.util.Random;
import javax.swing.JOptionPane; 
import javax.swing.SwingUtilities;
import java.awt.Window;

import tetris.db.PartidaDAO;
import tetris.db.UserSession;
import tetris.view.MainMenu;
import tetris.view.GameWindow;
//import tetris.controller.GameController;

// Clase Board: Encargada de la lógica del juego (Piezas, Tablero, Puntaje)

public class Board { //Con sus atributos 
    private int[][] grid;    
    private Piece currentPiece;
    private Piece nextPiece;
    
    private int score;
    private Runnable scoreListener; 
    private Runnable returnToMainMenuCallback;
    private int clearedLines; 
    private GameWindow gameWindow;  
    private boolean gameOverShown = false;
    private boolean isGameSaved = false;



    // Tablero
    public Board() {
        this.gameWindow = gameWindow;
        grid = new int[20][10]; // Tablero vacío
        clearedLines = 0; // 
        spawnNewPiece();
    }
    // Método para obtener el número de filas
    public int getRows() {
        return grid.length;
    }

    // Método para obtener el número de columnas
    public int getCols() {
        return grid[0].length;
    }

    // Generar una nueva pieza
    public void spawnNewPiece() {
        currentPiece = (nextPiece != null) ? nextPiece : generateRandomPiece();
        nextPiece = generateRandomPiece();

        // Revisa la pieza en la posición inicial 
        if (!canPlacePiece(currentPiece, currentPiece.getX(), currentPiece.getY())) {
            System.out.println("Game Over!");
        }
    }

   private Piece generateRandomPiece() {
    Random rand = new Random();
    switch (rand.nextInt(7)) {  
        case 0: return new PieceI();  // Pieza I
        case 1: return new PieceO();  // Pieza O
        case 2: return new PieceJ();  // Pieza J
        case 3: return new PieceL();  // Pieza L
        case 4: return new PieceT();  // Pieza T
        case 5: return new PieceS();  // Pieza S
        case 6: return new PieceZ();  // Pieza Z
        default: return new PieceI();  
    }
}

    // Verificar si se puede colocar una pieza en una posición
    public boolean canPlacePiece(Piece piece, int x, int y) {
        int[][] shape = piece.getShape();
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] != 0) {
                    int newX = x + col;
                    int newY = y + row;

                    // Verificar límites y colisiones
                    if (newX < 0 || newX >= grid[0].length || newY < 0 || newY >= grid.length || grid[newY][newX] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    // Método para registrar el observador
    public void setScoreListener(Runnable scoreListener) {
        this.scoreListener = scoreListener;
    }

// Método para incrementar el puntaje
    private void addScore(int points) {
        score += points;
        if (scoreListener != null) {
            scoreListener.run(); 
    }
// Verificar si el puntaje alcanza o supera los puntos necesarios para el gane
    if (score >= 1000) {
        showWinMessage(); 
        stopGame(); 
    }
}

// Método para mostrar el mensaje de victoria
    private void showWinMessage() {
        JOptionPane.showMessageDialog(null, "FELICIDADES! Has ganado el juego!.", "VICTORIA", JOptionPane.INFORMATION_MESSAGE);
}
    private void stopGame() {
        if (!isGameSaved) {  // Verificar si la partida ya fue guardada
            isGameSaved = true;  // Marcar la partida como guardada
            PartidaDAO partidaDAO = new PartidaDAO();
            boolean partidaGuardada = partidaDAO.createPartida(score, score >= 1000, UserSession.getLoggedInUser());
            if (!partidaGuardada) {
                System.out.println("Error al guardar la partida en la base de datos.");
            } else {
                System.out.println("Partida guardada correctamente.");
            }
        }
    }



    // Fijar la pieza actual al tablero (Tratamiento de limites del tablero)
public void fixCurrentPiece() {
    int[][] shape = currentPiece.getShape();

    // Recorremos la forma de la pieza
    for (int row = 0; row < shape.length; row++) {
        for (int col = 0; col < shape[row].length; col++) {
            if (shape[row][col] != 0) {  
                
                // Calcular las posiciones en el tablero
                int finalX = currentPiece.getX() + col;
                int finalY = currentPiece.getY() + row;

                // Este aparatado revisa si las coordenadas están dentro de los límites del tablero
                if (finalX >= 0 && finalX < grid[0].length && finalY >= 0 && finalY < grid.length) {
                    // Fijar la pieza en el tablero
                    grid[finalY][finalX] = shape[row][col];
                } else {
                    // Tratamiento ante errores con los limites
                    System.out.println("Error: La pieza está fuera de los límites del tablero. Coordenadas: (" + finalX + ", " + finalY + ")");
                    
                }
            }
        }
    }

    addScore(25); // Incrementar el puntaje
}

    // Obtener Puntaje
    public int getScore() {
        return score;
}

// Mover la pieza hacia abajo
    public void moveDown() {
        if (canMoveDown()) {
            currentPiece.setPosition(currentPiece.getX(), currentPiece.getY() + 1); // Mover la pieza hacia abajo
        }
    }

    // Mover la pieza hacia la izquierda
    public void moveLeft() {
        if (canMoveLeft()) {
            currentPiece.setPosition(currentPiece.getX() - 1, currentPiece.getY());  // Mover la pieza hacia la izquierda
        }
    }

    // Mover la pieza hacia la derecha
    public void moveRight() {
        if (canMoveRight()) {
           currentPiece.setPosition(currentPiece.getX() + 1, currentPiece.getY()); // Mover la pieza hacia la derecha
        }
    }

    // Rotar la pieza actual
    public void rotateCurrentPiece() {
        currentPiece.rotate(); // Rotar la pieza
    }

    // Verificar si se puede mover hacia abajo
    public boolean canMoveDown() {
        return canPlacePiece(currentPiece, currentPiece.getX(), currentPiece.getY() + 1);
    }

    // Verificar si se puede mover hacia la izquierda
    public boolean canMoveLeft() {
        return canPlacePiece(currentPiece, currentPiece.getX() - 1, currentPiece.getY());
    }

    // Verificar si se puede mover hacia la derecha
    public boolean canMoveRight() {
        return canPlacePiece(currentPiece, currentPiece.getX() + 1, currentPiece.getY());
    }
// Método para eliminar las líneas completas
    public void clearLines() {
        for (int row = 0; row < grid.length; row++) {
            if (isLineFull(row)) {
                clearLine(row);   
                moveLinesDown(row); 
                clearedLines++;  // Incrementar el contador de líneas eliminadas
                if (gameWindow != null) {
                gameWindow.updateLinesCleared(clearedLines);
            }
        }
    }
    }
    
  // Obtener el número de líneas eliminadas
    public int getClearedLines() {
        return clearedLines;
    }
    
    private boolean isLineFull(int row) {
        for (int col = 0; col < grid[row].length; col++) {
            if (grid[row][col] == 0) {  
                return false;  // La línea no completa
            }
        }
        return true; //línea completa
    }

    private void clearLine(int row) { //Limpia la línea
        for (int col = 0; col < grid[row].length; col++) {
            grid[row][col] = 0;  
        }
    }

    private void moveLinesDown(int clearedLine) {
        for (int row = clearedLine; row > 0; row--) {
            for (int col = 0; col < grid[row].length; col++) {
                grid[row][col] = grid[row - 1][col]; 
            }
        }
    }

    public boolean isGameOver() {
        // Verifica si hay piezas en la fila superior
        for (int i = 0; i < grid[0].length; i++) {
            if (grid[0][i] != 0) {
                // Si el juego ha terminado, mostramos el mensaje
                if (!gameOverShown) {
                    showGameOverMessage();
                    gameOverShown = true;
                    stopGame();  // Llamar a stopGame para guardar la partida cuando se acaba
                }
                return true;
            }
        }
        return false;
    }


// Método para mostrar el mensaje de Game Over y pausar el juego
private void showGameOverMessage() {
    // Muestra un cuadro de diálogo con el mensaje de "Game Over"
    JOptionPane.showMessageDialog(null, "GAME OVER!", "GAME OVER", JOptionPane.ERROR_MESSAGE);
    System.out.println("Game Over!"); 

 
}

    // Retornar el estado del tablero
    public int[][] getGrid() {
        return grid;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public Piece getNextPiece() {
        return nextPiece;
    }
    
}


