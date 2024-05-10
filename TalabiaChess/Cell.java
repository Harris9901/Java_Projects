import javax.swing.*;
import java.awt.*;
//Liew
// MVC - MODEL CLASS
public class Cell extends JPanel {
    private int row;
    private int col;
    private Piece piece; // Assuming you have a Piece class
    private Color cellColor;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        setDefaultColor();
        Color navyBlue = new Color(0, 0, 128);
        setBorder(BorderFactory.createLineBorder(navyBlue, 2));
    }

    public void setDefaultColor() {
        // Set the default color
        this.cellColor = Color.WHITE;
    }

    public void setCellColor(Color color) {
        this.cellColor = color;
        repaint();
    }

    /**
     * Sets the piece for the current position on the game board.
     */
    public void setPiece(Piece piece) {
        // Clear the current piece at the position
        this.piece = null;

        // Set the new piece for the position
        this.piece = piece;

        // If a piece is being set (not null), update its position and perform
        // additional actions
        if (piece != null) {
            // Set the row and column for the piece to match the current position
            piece.setRow(row);
            piece.setCol(col);

            // Perform any additional actions related to the piece or position (e.g.,
            // flipping)
            flipPointPiece();
        }

        // Request repainting to update the graphical representation of the game board
        repaint();
    }

    public Piece getPiece() {
        return piece;
    }

    public void removePiece() {
        setPiece(null);
    }

    public void checkEndOfBoard(Point piece) {
        int currentRow = piece.getRow();
        if (currentRow == 0) {
            piece.setEndOfBoard(true);
        } else if (currentRow == 5) {
            piece.setEndOfBoard(false);
        }
    }

    /**
     * Checks if the piece is an instance of Point and updates its image based on
     * the position on the board and color.
     */
    public void flipPointPiece() {
        // Check if the piece is an instance of Point
        if (piece instanceof Point) {
            // Obtain the board and its flipped state
            Board board = piece.getBoard();
            boolean boardFlipped = board.boardFlipped;

            // Obtain color, piece-specific details, and check if it's at the end of the
            // board
            String pieceColor = piece.getColor();
            Point pointPiece = (Point) piece;
            checkEndOfBoard(pointPiece);
            boolean endOfBoard = pointPiece.getEndOfBoard();

            // Check if the board is flipped
            if (boardFlipped) {
                // Board is flipped
                if (pieceColor.equals("Blue")) {
                    // Blue piece on a flipped board
                    if (!endOfBoard) {
                        // Blue piece not at the end of the board
                        Image newImage = board.uploadImage("Assets\\blue_arrow_rotated.png");
                        piece.setImage(newImage);
                    } else {
                        // Blue piece at the end of the board
                        Image newImage = board.uploadImage("Assets\\blue_arrow.png");
                        piece.setImage(newImage);
                    }
                } else {
                    // Yellow piece on a flipped board
                    if (!endOfBoard) {
                        // Yellow piece not at the end of the board
                        Image newImage = board.uploadImage("Assets\\yellow_arrow_rotated.png");
                        piece.setImage(newImage);
                    } else {
                        // Yellow piece at the end of the board
                        Image newImage = board.uploadImage("Assets\\yellow_arrow.png");
                        piece.setImage(newImage);
                    }
                }
            } else {
                // Board is not flipped
                if (pieceColor.equals("Blue")) {
                    // Blue piece on a non-flipped board
                    if (!endOfBoard) {
                        // Blue piece not at the end of the board
                        Image newImage = board.uploadImage("Assets\\blue_arrow.png");
                        piece.setImage(newImage);
                    } else {
                        // Blue piece at the end of the board
                        Image newImage = board.uploadImage("Assets\\blue_arrow_rotated.png");
                        piece.setImage(newImage);
                    }
                } else {
                    // Yellow piece on a non-flipped board
                    if (!endOfBoard) {
                        // Yellow piece not at the end of the board
                        Image newImage = board.uploadImage("Assets\\yellow_arrow.png");
                        piece.setImage(newImage);
                    } else {
                        // Yellow piece at the end of the board
                        Image newImage = board.uploadImage("Assets\\yellow_arrow_rotated.png");
                        piece.setImage(newImage);
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(cellColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        // Optionally draw the piece within the cell
        if (piece != null) {
            // Assuming Piece has a method to get its image
            Image pieceImage = piece.getImage(); // Adjust based on your implementation

            // Calculate the position to center the piece within the cell
            int x = (getWidth() - pieceImage.getWidth(null)) / 2;
            int y = (getHeight() - pieceImage.getHeight(null)) / 2;

            g.drawImage(pieceImage, x, y, null);
        }
    }
}