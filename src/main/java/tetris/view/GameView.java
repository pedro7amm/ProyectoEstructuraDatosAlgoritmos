/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tetris.view;

import tetris.model.Board;
import tetris.model.Piece;

import javax.swing.*;
import java.awt.*;

// Clase GameView: Renderiza el juego en la interfaz gráfica
public class GameView extends JPanel {
    private final Board board; 
    private final int tileSize = 30; 

    // Constructor GameView
    public GameView(Board board) {
        this.board = board;
        setPreferredSize(new Dimension(board.getCols() * tileSize, board.getRows() * tileSize));
        setBackground(Color.BLACK); 
    }
    //Dibuja componentes gráficos de interfaz (Tablero, Piezas, Cuadriculas)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g); 
        drawFixedPieces(g); 
        drawCurrentPiece(g); 
        drawNextPiece(g); 
    }

    // Método para dibujar la cuadrícula del tablero
    private void drawGrid(Graphics g) {
        g.setColor(Color.GRAY); 
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                g.drawRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }
    }

    // Método para dibujar las piezas fijadas en el tablero
    private void drawFixedPieces(Graphics g) {
        int[][] grid = board.getGrid();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] != 0) { 
                    g.setColor(getColorForPiece(grid[row][col])); 
                    g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);

                    // Dibujar bordes negros alrededor de cada celda
                    g.setColor(Color.BLACK);
                    g.drawRect(col * tileSize, row * tileSize, tileSize, tileSize);
                }
            }
        }
    }

    // Método para dibujar la pieza en movimiento
    private void drawCurrentPiece(Graphics g) {
        Piece currentPiece = board.getCurrentPiece(); 
        if (currentPiece != null) {
            int[][] shape = currentPiece.getShape();
            for (int row = 0; row < shape.length; row++) {
                for (int col = 0; col < shape[row].length; col++) {
                    if (shape[row][col] != 0) {
                        int x = currentPiece.getX() + col; 
                        int y = currentPiece.getY() + row; 
                        g.setColor(getColorForPiece(shape[row][col])); 
                        g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);

                        // Dibujar bordes negros alrededor de cada celda de la pieza en movimiento
                        g.setColor(Color.BLACK);
                        g.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    }
                }
            }
        }
    }

    // Método para dibujar la siguiente pieza
    private void drawNextPiece(Graphics g) {
        Piece nextPiece = board.getNextPiece(); // Obtener la siguiente pieza
        if (nextPiece != null) {
            int[][] shape = nextPiece.getShape();
            
           
            int offsetX = board.getCols() + 1;
            int offsetY = 2;

            for (int row = 0; row < shape.length; row++) {
                for (int col = 0; col < shape[row].length; col++) {
                    if (shape[row][col] != 0) {
                        g.setColor(getColorForPiece(shape[row][col]));
                        g.fillRect((offsetX + col) * tileSize, (offsetY + row) * tileSize, tileSize, tileSize);

                        // Dibujar bordes negros alrededor de la pieza siguiente
                        g.setColor(Color.BLACK);
                        g.drawRect((offsetX + col) * tileSize, (offsetY + row) * tileSize, tileSize, tileSize);
                    }
                }
            }
        }
    }

    // Método auxiliar para obtener colores basados en el tipo de pieza
    private Color getColorForPiece(int pieceType) {
        switch (pieceType) {
            case 1:
                return Color.GREEN; // Pieza I
            case 2:
                return Color.GREEN; // Pieza O
            case 3:
                return Color.GREEN; // Pieza T
            case 4:
                return Color.GREEN; // Pieza L
            case 5:
                return Color.GREEN; // Pieza J
            case 6:
                return Color.GREEN; // Pieza S
            case 7:
                return Color.GREEN; // Pieza Z
            default:
                return Color.GREEN; 
        }
    }
}


