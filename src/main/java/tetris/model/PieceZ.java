/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tetris.model;

//Creación Pieza Z
//Subclase de Piece 
//Encargada de la forma de la Pieza

public class PieceZ extends Piece {
    public PieceZ(){
        shape = new int[][]{
            {1, 1, 0},
            {0, 1, 1},
            {0, 0, 0}
        };
    }
    
}
