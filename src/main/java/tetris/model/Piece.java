/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Archivo: model/Piece.java
package tetris.model;

import java.util.ArrayList;
import java.util.List;


// Clase Piece: Encaragada de los movimientos de las piezas y posiciones 

//Forma y ubicación 
public abstract class Piece {
    protected int[][] shape; 
    protected int x;         
    protected int y;         
   
//Define la posición inicial de la pieza en el tablero 
    public Piece() {
        this.x = 3; 
        this.y = 0;
    }

    // Retorna la forma actual de la pieza
    public int[][] getShape() {
        return shape;
    }

    // Rotar la pieza en sentido horario 
    public void rotate() {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        shape = rotated;
    }

    // Posición actual
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

