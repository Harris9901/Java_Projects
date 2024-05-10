import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

//MVC-MODEL CLASS
public class Board {
    private Cell[][] cells;
    BoardView boardView;
    Main main;
    private boolean startOfMove;
    private boolean yellowTurn;
    private int turnCounter;
    private TalabiaChessGUI talabiaChessGUI;
    public boolean boardFlipped = false;

    public Board(BoardView boardView, Main main) {
        this.boardView = boardView;
        this.main = main;
        initializeCells();
        initializePieces();
        yellowTurn = true;
        startOfMove = true;
        turnCounter = 0;
    }

    // Imran
    public void clearBoard() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                cells[row][col].setPiece(null);
            }
        }
    }

    //Liew
    public void initializePieces() {
        // Blue pieces
        Image bluePlusImage = uploadImage("Assets\\blue_plus.png");
        Image blueHourglassImage = uploadImage("Assets\\blue_hg.png");
        Image blueTimeImage = uploadImage("Assets\\blue_time.png");
        Image blueSunImage = uploadImage("Assets\\blue_sun.png");

        // Set Blue Plus pieces on the board
        cells[0][0].setPiece(new Plus(0, 0, "Blue", bluePlusImage, this));
        cells[0][6].setPiece(new Plus(0, 6, "Blue", bluePlusImage, this));

        // Set Blue Hourglass pieces on the board
        cells[0][1].setPiece(new Hourglass(0, 1, "Blue", blueHourglassImage, this));
        cells[0][5].setPiece(new Hourglass(0, 5, "Blue", blueHourglassImage, this));

        // Set Blue Time pieces on the board
        cells[0][2].setPiece(new Time(0, 2, "Blue", blueTimeImage, this));
        cells[0][4].setPiece(new Time(0, 4, "Blue", blueTimeImage, this));

        // Set Blue Sun piece on the board
        cells[0][3].setPiece(new Sun(0, 3, "Blue", blueSunImage, this));

        // Yellow pieces
        Image yellowPlusImage = uploadImage("Assets\\yellow_plus.png");
        Image yellowHourGlassImage = uploadImage("Assets\\yellow_hg.png");
        Image yellowTimeImage = uploadImage("Assets\\yellow_time.png");
        Image yellowSunImage = uploadImage("Assets\\yellow_sun.png");

        // Set Yellow Plus pieces on the board
        cells[5][0].setPiece(new Plus(5, 0, "Yellow", yellowPlusImage, this));
        cells[5][6].setPiece(new Plus(5, 6, "Yellow", yellowPlusImage, this));

        // Set Yellow Hourglass pieces on the board
        cells[5][1].setPiece(new Hourglass(5, 1, "Yellow", yellowHourGlassImage, this));
        cells[5][5].setPiece(new Hourglass(5, 5, "Yellow", yellowHourGlassImage, this));

        // Set Yellow Time pieces on the board
        cells[5][2].setPiece(new Time(5, 2, "Yellow", yellowTimeImage, this));
        cells[5][4].setPiece(new Time(5, 4, "Yellow", yellowTimeImage, this));

        // Set Yellow Sun piece on the board
        cells[5][3].setPiece(new Sun(5, 3, "Yellow", yellowSunImage, this));

        // Blue Point pieces in row 2
        Image bluePointImage = uploadImage("Assets\\blue_arrow.png");
        for (int col = 0; col <= 6; col++) {
            cells[1][col].setPiece(new Point(2, col, "Blue", bluePointImage, this));
        }

        // Yellow Point pieces in row 5
        Image yellowPointImage = uploadImage("Assets\\yellow_arrow.png");
        for (int col = 0; col <= 6; col++) {
            cells[4][col].setPiece(new Point(4, col, "Yellow", yellowPointImage, this));
        }
    }

    // Liew
    public void initializeCells() {
        cells = new Cell[6][7];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                Cell cell = new Cell(row, col);
                cells[row][col] = cell;
            }
        }
    }

    // Liew
    // This method is called to initiate the dragging of a piece on the game board.
    public void startDragging(int row, int col, Piece piece) {
        startOfMove = false; // Set the startOfMove flag to false.
        if (isTurn(piece)) // Check if it's the current player's turn.
            indicateMoveForPiece(piece, row, col); // Indicate the potential move for the piece.
    }

    // Liew
    // This method is called to drop a piece at a specific row and column on the
    // game board.
    public void dropPieceAt(int row, int col, Piece piece) {
        if (isValidMove(row, col, piece) && isTurn(piece) && piece.move(row, col)) {
            // Check if the move is valid, it's the current player's turn, and the piece can
            // move to the destination.
            winCondition(piece, getPieceFromCell(row, col)); // Check if there's a win condition.
            removePieceFromCell(piece); // Remove the piece from its current position.
            setPieceAt(row, col, piece); // Set the piece at the new position.
            updateTurnCounter(); // Update the turn counter to switch to the next player's turn.
            flipBoard(); // Flip the game board if necessary.
            yellowTurn = !yellowTurn; // Toggle the player's turn (assuming yellow is one of the players).
        }
        setDefaultCellColor(); // Reset the cell color.
        boardView.update(); // Update the game board view.
        startOfMove = true; // Set the startOfMove flag back to true.
    }

    // Liew
    // Set a piece at a specific row and column on the game board.
    public void setPieceAt(int row, int col, Piece piece) {
        if (isValidCoordinate(row, col)) { // Check if the provided coordinates are valid.
            cells[row][col].setPiece(piece); // Set the provided piece at the specified cell.
            boardView.update(); // Update the game board view to reflect the change.
        }
    }

    // Liew
    // Remove a piece from its current cell on the game board.
    public void removePieceFromCell(Piece piece) {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                Cell cell = cells[row][col]; // Get the cell at the current coordinates.
                if (cell.getPiece() == piece) { // Check if the cell contains the provided piece.
                    cell.removePiece(); // Remove the piece from the cell.
                    boardView.update(); // Update the game board view to reflect the change.
                    return; // Exit the loop since the piece has been found and removed.
                }
            }
        }
    }

    // Liew
    // Get the piece located in a specific cell of the game board.
    public Piece getPieceFromCell(int row, int col) {
        return cells[row][col].getPiece(); // Retrieve and return the piece from the specified cell.
    }

    // Liew
    // Get a reference to a specific cell on the game board.
    public Cell getCell(int row, int col) {
        return cells[row][col]; // Retrieve and return the specified cell.
    }

    // Liew
    // Get the entire 2D array of cells representing the game board.
    public Cell[][] getCells() {
        return cells; // Return the entire 2D array of cells representing the game board.
    }

    // Liew
    // Set the default cell color for all cells on the game board to white.
    public void setDefaultCellColor() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                Cell cell = cells[row][col]; // Get a reference to the current cell.
                cell.setCellColor(Color.WHITE); // Set the cell's color to white.
            }
        }
    }

    // Liew
    // Check if it's the turn of the provided piece to make a move.
    private boolean isTurn(Piece piece) {
        String pieceColor = piece.getColor(); // Get the color of the provided piece.

        // Check if the piece's color matches the current player's turn.
        if ((pieceColor.equals("Yellow") && yellowTurn) || (pieceColor.equals("Blue") && !yellowTurn)) {
            return true; // It's the piece's turn to make a move.
        }

        return false; // It's not the piece's turn to make a move.
    }

    // Liew
    // Check if a piece at a specific row and column can be "eaten" by the provided
    // piece.
    private boolean isEdible(int row, int col, Piece piece) {
        String pieceColor = piece.getColor(); // Get the color of the provided piece.
        Piece targetPiece = getPieceFromCell(row, col); // Get the piece at the target cell.

        if (targetPiece != null) {
            String targetPieceColor = targetPiece.getColor(); // Get the color of the target piece.

            // Return true if the target piece's color is different from the provided
            // piece's color.
            return !pieceColor.equals(targetPieceColor);
        }

        // Return true if the target cell is empty (no piece to eat).
        return true;
    }

    // Harris and Imran
    // Manage the transformation of pieces based on the current turn counter.
    public void manageTransformation() {
        if (checkTwoTurns()) { // Check if two turns have passed since the last transformation.
            transformPieces(); // Perform the transformation of pieces.
            turnCounter = 0; // Reset the turn counter after transformation.
        } else {
            turnCounter++; // Increment the turn counter since a move has been made.
        }
        boardView.update(); // Update the game board view.
    }

    // Harris and Imran
    // Update the turn counter and check for transformation after each move.
    public void updateTurnCounter() {
        turnCounter++; // Increment the turn counter for the current move.

        // Check if four moves have been made since the last transformation.
        if (turnCounter >= 4) {
            manageTransformation(); // Trigger the transformation of pieces.
            turnCounter = 0; // Reset the turn counter after transformation.
        }
    }

    // Harris and Imran
    // Check if at least four turns have passed since the last transformation.
    private boolean checkTwoTurns() {
        return turnCounter >= 4;
    }

    // Harris and Imran
    private void transformPieces() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                Piece currentPiece = getPieceFromCell(row, col); // Get the piece at the current cell.

                // Check if the current piece is a Time piece.
                if (currentPiece instanceof Time) {
                    if (currentPiece.getColor().equals("Yellow")) {
                        // Transform yellow Time piece into a yellow Plus piece.
                        Image plusImage = uploadImage("Assets\\yellow_plus.png"); // Replace with actual image path.
                        Plus newPiece = new Plus(row, col, "Yellow", plusImage, this); // Create a new Plus piece.
                        setPieceAt(row, col, newPiece); // Set the new Plus piece at the same cell.
                    } else if (currentPiece.getColor().equals("Blue")) {
                        // Transform blue Time piece into a blue Plus piece.
                        Image plusImage = uploadImage("Assets\\blue_plus.png"); // Replace with actual image path.
                        Plus newPiece = new Plus(row, col, "Blue", plusImage, this); // Create a new Plus piece.
                        setPieceAt(row, col, newPiece); // Set the new Plus piece at the same cell.
                    }
                }
                // Check if the current piece is a Plus piece.
                else if (currentPiece instanceof Plus) {
                    if (currentPiece.getColor().equals("Yellow")) {
                        // Transform yellow Plus piece into a yellow Time piece.
                        Image timeImage = uploadImage("Assets\\yellow_time.png"); // Replace with actual image path.
                        Time newPiece = new Time(row, col, "Yellow", timeImage, this); // Create a new Time piece.
                        setPieceAt(row, col, newPiece); // Set the new Time piece at the same cell.
                    } else if (currentPiece.getColor().equals("Blue")) {
                        // Transform blue Plus piece into a blue Time piece.
                        Image timeImage = uploadImage("Assets\\blue_time.png"); // Replace with actual image path.
                        Time newPiece = new Time(row, col, "Blue", timeImage, this); // Create a new Time piece.
                        setPieceAt(row, col, newPiece); // Set the new Time piece at the same cell.
                    }
                }
            }
        }
    }

    // Liew
    public boolean isStartOfMove() {
        return startOfMove;
    }

    // Liew
    // Check for a win condition when a piece moves and interacts with another
    // piece.
    public void winCondition(Piece piece, Piece target) {
        if (target instanceof Sun && !piece.getColor().equals(target.getColor())) {
            // Check if the target piece is a Sun and has a different color from the moving
            // piece.
            String pieceColor = piece.getColor(); // Get the color of the moving piece.
            System.out.println("Game Over, winner is " + pieceColor + "!"); // Display the game over message.

            // You can perform additional game over actions here, like ending the game or
            // switching to a game over screen.
            switchToGameOverScreen(); // Example: Switch to the game over screen.
        }
    }

    // Liew
    private void switchToGameOverScreen() {
        talabiaChessGUI.showGameOverPanel();
    }

    // Liew
    private boolean isValidMove(int row, int col, Piece piece) {
        return isValidCoordinate(row, col) && isEdible(row, col, piece);
    }

    // Julian
    // Check if it's currently Yellow's turn to make a move.
    public boolean isYellowTurn() {
        return yellowTurn;
    }

    // Julian
    // Set the current player's turn to Yellow or not Yellow (Blue).
    public void setYellowTurn(boolean yellowTurn) {
        this.yellowTurn = yellowTurn;
    }

    // Harris and Imran
    // Get the current turn counter to keep track of the number of moves.
    public int getTurnCounter() {
        return turnCounter;
    }

    // Harris and Imran
    // Set the turn counter to a specific value.
    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    //// Liew
    // Upload an image from a given file name and return it as an Image object.
    public Image uploadImage(String fileName) {
        try {
            return ImageIO.read(getClass().getResource(fileName)); // Read and return the image.
        } catch (IOException e) {
            e.printStackTrace(); // Print an error message if there's an issue.
            return null; // Return null in case of an error.
        }
    }

    // Liew and Julian
    // Set the reference to the TalabiaChessGUI for interaction.
    public void setTalabiaChessGUI(TalabiaChessGUI talabiaChessGUI) {
        this.talabiaChessGUI = talabiaChessGUI;
    }

    // Liew
    // Get the piece at a specific row and column on the game board.
    public Piece getPiece(int row, int col) {
        if (isValidCoordinate(row, col)) {
            return cells[row][col].getPiece(); // Get and return the piece if the coordinates are valid.
        }
        return null; // Return null if the coordinates are out of bounds.
    }

    // Liew
    // Check if the provided coordinates are valid (within the bounds of the game
    // board).
    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < cells.length && y >= 0 && y < cells[0].length;
    }

    // Liew
    // Indicate valid move positions for the given piece at a specific row and
    // column.
    public void indicateMoveForPiece(Piece piece, int row, int col) {
        if (piece instanceof Point)
            indicateMoveForPoint(piece, row, col); // Indicate valid moves for a Point piece.
        else if (piece instanceof Hourglass)
            indicateMoveForHourglass(piece, row, col); // Indicate valid moves for an Hourglass piece.
        else if (piece instanceof Time)
            indicateMoveForTime(piece, row, col); // Indicate valid moves for a Time piece.
        else if (piece instanceof Plus)
            indicateMoveForPlus(piece, row, col); // Indicate valid moves for a Plus piece.
        else if (piece instanceof Sun)
            indicateMoveForSun(piece, row, col); // Indicate valid moves for a Sun piece.
    }

    // Harris,Imran,Liew and Julian
    // Indicate valid move positions for a Point piece at a specific row and column.
    private void indicateMoveForPoint(Piece piece, int row, int col) {
        Point pointPiece = (Point) piece; // Downcasting
        int forwardDirection = pointPiece.getEndOfBoard() ? 1 : -1;

        for (int i = 1; i <= 2; i++) {
            int newRow = pointPiece.getRow() + forwardDirection * i;
            int newCol = pointPiece.getCol();
            highlightCell(newRow, newCol); // Highlight the cell for a valid move.
        }
    }

    // Harris
    // Indicate valid move positions for an Hourglass piece at a specific row and
    // column.
    private void indicateMoveForHourglass(Piece piece, int row, int col) {
        // Highlight cells in a symmetrical pattern around the piece.
        highlightCell(row + 1, col + 2);
        highlightCell(row + 2, col + 1);
        highlightCell(row + 1, col - 2);
        highlightCell(row + 2, col - 1);
        highlightCell(row - 1, col + 2);
        highlightCell(row - 2, col + 1);
        highlightCell(row - 1, col - 2);
        highlightCell(row - 2, col - 1);
    }

    // Harris
    // Indicate valid move positions for a Time piece at a specific row and column.
    private void indicateMoveForTime(Piece piece, int row, int col) {
        for (int i = -5; i <= 5; i++) {
            for (int j = -5; j <= 5; j++) {
                if (i != 0 || j != 0) { // Skip the current position
                    if (Math.abs(i) == Math.abs(j)) { // Diagonal move
                        highlightCell(row + i, col + j); // Highlight the cell for a valid move.
                    }
                }
            }
        }
    }

    // Imran and Liew
    // Indicate valid move positions for a Plus piece at a specific row and column.
    public void indicateMoveForPlus(Piece piece, int row, int col) {
        // Highlight valid moves in all directions (horizontal and vertical).
        for (int i = row + 1; i < 6; i++) {
            highlightCell(i, col); // Highlight cells below the piece.
        }
        for (int i = row - 1; i >= 0; i--) {
            highlightCell(i, col); // Highlight cells above the piece.
        }
        for (int i = col + 1; i < 7; i++) {
            highlightCell(row, i); // Highlight cells to the right of the piece.
        }
        for (int i = col - 1; i >= 0; i--) {
            highlightCell(row, i); // Highlight cells to the left of the piece.
        }
    }

    // Imran
    // Indicate valid move positions for a Sun piece at a specific row and column.
    private void indicateMoveForSun(Piece piece, int row, int col) {
        // Highlight cells in a cross pattern around the piece.
        highlightCell(row - 1, col - 1);
        highlightCell(row - 1, col);
        highlightCell(row - 1, col + 1);
        highlightCell(row, col - 1);
        highlightCell(row, col + 1);
        highlightCell(row + 1, col - 1);
        highlightCell(row + 1, col);
        highlightCell(row + 1, col + 1);
    }

    // Harris and Imran
    // Highlight a specific cell by setting its color to RED.
    private void highlightCell(int row, int col) {
        if (isValidCoordinate(row, col)) {
            Cell cell = cells[row][col]; // Get the cell at the specified coordinates.
            cell.setCellColor(Color.RED); // Set the cell's color to RED.
        }
    }

    // Liew
    // Check for collision when a Point piece is moving.
    public boolean checkForPointCollision(int row0, int row, int col, boolean endOfBoard) {
        if (!endOfBoard) {
            if (cells[row0 - 1][col].getPiece() != null && (row0 - row) > 1) {
                // Check if there is a piece above the Point piece with enough space for
                // movement.
                return true; // Collision detected.
            }
        } else {
            if (cells[row0 + 1][col].getPiece() != null && (row - row0) > 1) {
                // Check if there is a piece below the Point piece with enough space for
                // movement.
                return true; // Collision detected.
            }
        }
        return false; // No collision detected.
    }

    // Liew and Julian
    // Flip the game board both vertically and horizontally.
    public void flipBoard() {
        boardFlipped = !boardFlipped; // Toggle the boardFlipped flag to indicate the board is flipped.

        // Vertical Rotation (Flip rows)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 7; col++) {

                // Swap the pieces between original and flipped cells
                Piece piece1 = cells[row][col].getPiece(); // Get the piece from the original cell.
                Piece piece2 = cells[5 - row][col].getPiece(); // Get the piece from the flipped cell.

                cells[row][col].setPiece(piece2); // Set the piece from the flipped cell to the original cell.
                cells[5 - row][col].setPiece(piece1); // Set the piece from the original cell to the flipped cell.
            }
        }

        // Horizontal Rotation (Flip columns)
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 3; col++) {

                // Swap the pieces between original and flipped cells
                Piece piece1 = cells[row][col].getPiece(); // Get the piece from the original cell.
                Piece piece2 = cells[row][6 - col].getPiece(); // Get the piece from the flipped cell.

                cells[row][col].setPiece(piece2); // Set the piece from the flipped cell to the original cell.
                cells[row][6 - col].setPiece(piece1); // Set the piece from the original cell to the flipped cell.
            }
        }

        boardView.update(); // Update the game board view to reflect the flipped state.
    }

    // Liew and Julian
    // Set the boardFlipped flag to control the board's orientation.
    public void setBoardFlipped(boolean flipped) {
        this.boardFlipped = flipped; // Set the boardFlipped flag to the provided value.
    }

}