/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tetris.model;

//Creación Pieza T
//Subclase de Piece
//Encargada de la forma de la Pieza

public class PieceT extends Piece {
    public PieceT(){
        shape = new int[][]{
            {0, 1, 0},
            {1, 1, 1}
        };
    }
    
}
