import java.awt.*;
//Imran,Harris and Liew
//MV-MODEL CLASS
public class Plus extends Piece {

    public Plus(int row, int col, String color, Image image, Board board) {
        super(row, col, color, board, image);
    }

    // Override of the abstract method in the Piece class to determine if the Plus
    // piece can move to the specified coordinates
    @Override
    public boolean move(int row, int col) {
        int row0 = getRow();
        int col0 = getCol();
        int deltaRow = Math.abs(row - row0);
        int deltaCol = Math.abs(col - col0);

        // Check if the move is valid for the "Plus" piece (horizontal or vertical)
        if ((deltaRow == 0 && deltaCol > 0) || (deltaRow > 0 && deltaCol == 0)) {
            int stepRow = Integer.compare(row, row0);
            int stepCol = Integer.compare(col, col0);

            // Check for pieces in the path
            for (int i = row0 + stepRow, j = col0 + stepCol; i != row || j != col; i += stepRow, j += stepCol) {
                if (getBoard().getPieceFromCell(i, j) != null) {
                    // There is a piece in the path
                    return false;
                }
            }
            return true; // Valid move
        }

        // Invalid move
        return false;
    }
}