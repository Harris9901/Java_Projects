import java.awt.*;
//Harris
//MV-MODEL CLASS
public class Time extends Piece {

    public Time(int row, int col, String color, Image image, Board board) {
        super(row, col, color, board, image);
    }

    // Override of the abstract method in the Piece class to determine if the Time
    // piece can move to the specified coordinates
    @Override
    public boolean move(int row, int col) {
        int row0 = getRow();
        int col0 = getCol();
        int deltaX = Math.abs(row - row0);
        int deltaY = Math.abs(col - col0);

        // Check if the move is valid for the "Time" piece (diagonal movement)
        if (deltaX == deltaY) {
            int xDirection = Integer.compare(row, row0);
            int yDirection = Integer.compare(col, col0);

            // Check for pieces in the path
            for (int i = 1; i < deltaX; i++) {
                Board board = getBoard();
                int checkX = row0 + i * xDirection;
                int checkY = col0 + i * yDirection;

                if (board.getPieceFromCell(checkX, checkY) != null) {
                    // There is a piece in the path
                    return false;
                }
            }
            setRow(row);
            setCol(col);
            return true; // Valid move
        }
        return false; // Invalid move
    }
}