import java.awt.*;
//Imran
//MV-MODEL CLASS
public class Sun extends Piece {

    public Sun(int row, int col, String color, Image image, Board board) {
        super(row, col, color, board, image);
    }

     // Override of the abstract method in the Piece class to determine if the Sun piece can move to the specified coordinates
     @Override
     public boolean move(int row, int col) {
         int row0 = getRow();
         int col0 = getCol();
         
         // Check if the move is valid for the "Sun" piece (can move in any direction by one step)
         if (Math.abs(row - row0) <= 1 && Math.abs(col - col0) <= 1) {
             setRow(row);
             setCol(col);
             return true;  // Valid move
         }
         return false;  // Invalid move
     }
 }