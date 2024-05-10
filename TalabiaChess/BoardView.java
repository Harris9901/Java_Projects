import javax.swing.*;
import java.awt.*;
//Liew
// MVC-VIEW CLASS
public class BoardView extends JPanel {
    private Board board;
    private Cell[][] cells;

    public BoardView() {
        setLayout(new GridLayout(6, 7)); // 6 rows, 7 columns for a 7x6 board
    }

    public void setBoard(Board board) {
        this.board = board;
        initializeBoardGUI();
    }


    // Initialize the graphical user interface (GUI) for the game board.
    public void initializeBoardGUI() {
        cells = board.getCells(); // Get the 2D array of cells from the game board.

        // Iterate through all rows and columns of the board.
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                Cell cell = cells[row][col]; // Get a reference to the current cell.
                add(cell); // Add the cell to the GUI for display.
            }
        }

        update(); // Update the GUI to reflect the current state of the game board.
    }

    public void update() {
        repaint();
    }

    public int getCellHeight() {
        return getHeight() / 6;
    }

    public int getCellWidth() {
        return getWidth() / 7;
    }

    public Dimension getGridSize() {
        return new Dimension(7, 6); // Rows x Columns
    }

    // Override the paintComponent method to render the game board and pieces.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the parent class's paintComponent method.

        // Iterate through each cell on the game board.
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                Piece piece = board.getPiece(i, j); // Get the piece at cell (i, j).
                if (piece != null) { // Check if there is a piece at this cell.
                    Image pieceImage = piece.getImage(); // Get the image of the piece.

                    // Calculate the dimensions and position for rendering the piece.
                    int cellWidth = getWidth() / 7; // Width of each cell on the board.
                    int cellHeight = getHeight() / 6; // Height of each cell on the board.
                    int x = i * cellWidth; // X-coordinate for rendering.
                    int y = j * cellHeight; // Y-coordinate for rendering.

                    // Draw the piece's image on the board at the calculated position.
                    g.drawImage(pieceImage, x, y, cellWidth, cellHeight, this);
                }
            }
        }
    }

}