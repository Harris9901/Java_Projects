import java.awt.*;
//Julian,Liew,Harris
//MV-MODEL CLASS
public class Point extends Piece {

    private boolean endOfBoard = false;

    public Point(int row, int col, String color, Image image, Board board) {
        super(row, col, color, board, image);
    }

    // Getter method to retrieve the value of the endOfBoard flag
    public boolean getEndOfBoard() {
        return endOfBoard;
    }

    // Setter method to update the value of the endOfBoard flag
    public void setEndOfBoard(boolean endOfBoard) {
        this.endOfBoard = endOfBoard;
    }

    // Override of the abstract method in the Piece class to determine if the Point
    // piece can move to the specified coordinates
    @Override
    public boolean move(int row, int col) {
        int row0 = getRow();
        int col0 = getCol();

        // Check if the move is valid for the "Point" piece
        if (row < row0 && row0 - row <= 2 && Math.abs(col - col0) == 0
                && !getBoard().checkForPointCollision(row0, row, col, endOfBoard)
                && !endOfBoard) {
            setRow(row);
            setCol(col);
            return true; // Valid move
        } else if (row > row0 && row - row0 <= 2 && Math.abs(col - col0) == 0
                && !getBoard().checkForPointCollision(row0, row, col, endOfBoard)
                && endOfBoard) {
            setRow(row);
            setCol(col);
            return true; // Valid move
        }
        return false; // Invalid move
    }
}