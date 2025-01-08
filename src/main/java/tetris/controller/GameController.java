/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tetris.controller;

import tetris.model.Board;
import tetris.model.Piece;
import tetris.view.GameView;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//Clase GameController: Encargada de manejar los eventos dej juego (Mover Piezas, Rotarlas, Control de Pausa)

public class GameController {

    private Board board; 
    private GameView gameView; 
    private Timer gameTimer; 
    private boolean isPaused = false; 

    public GameController(Board board, GameView gameView) {
        this.board = board;
        this.gameView = gameView;

        // Escucha de eventos de teclado
        addKeyListenerToView();

        // Configurar el temporizador para mover las piezas
        gameTimer = new Timer(500, e -> {
            if (!board.isGameOver()) {
                movePieceDown();
                gameView.repaint(); 
            }
        });
    }

    // Iniciar el juego
    public void startGame() {
        board.spawnNewPiece(); 
        gameTimer.start(); 
    }

    // Mover la pieza hacia abajo
    public void movePieceDown() {
        if (board.canMoveDown()) {
            board.moveDown();
        } else {
            board.fixCurrentPiece();
            board.clearLines();
            board.spawnNewPiece(); 
        }
    // Verificar si el juego ha terminado después de mover la pieza
    if (board.isGameOver()) {
        // Detener el temporizador
        gameTimer.stop(); 
    }
}

    // Mover la pieza hacia la izquierda
    public void movePieceLeft() {
        if (board.canMoveLeft()) {
            board.moveLeft();
        }
    }

    // Mover la pieza hacia la derecha
    public void movePieceRight() {
        if (board.canMoveRight()) {
            board.moveRight();
        }
    }

    // Rotar la pieza actual
    public void rotatePiece() {
       // Verificar si la rotación es válida 
    if (board.canPlacePiece(board.getCurrentPiece(), board.getCurrentPiece().getX(), board.getCurrentPiece().getY())) {
        board.rotateCurrentPiece();  
    }
}

   // Cambiar estado de pausa/reanudar
    public void togglePause() {
        setPaused(!isPaused); 
    }

    // Establecer estado de pausa directamente
    public void setPaused(boolean paused) {
        isPaused = paused;

        if (isPaused) {
            gameTimer.stop(); // Detener el temporizador
            System.out.println("Juego pausado.");
            
        } else {
            gameTimer.start(); // Reanudar el temporizador
            System.out.println("Juego reanudado.");
            gameView.requestFocusInWindow();
          
        }
    }
    // Obtener el estado actual de pausa
    public boolean isPaused() {
        return isPaused;
    }

    //KeyListener para manejar las teclas presionadas
    private void addKeyListenerToView() {
        gameView.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        movePieceLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        movePieceRight();
                        break;
                    case KeyEvent.VK_DOWN:
                        movePieceDown();
                        break;
                    case KeyEvent.VK_UP:
                        rotatePiece();
                        break;
                    case KeyEvent.VK_P: 
                        togglePause();
                        break;
                }
                gameView.repaint(); 
            }
        });

 // Verificación llamado eventos del teclado
    gameView.setFocusable(true);
    gameView.requestFocusInWindow();
}
}

