import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//Liew
//MVC - CONTROLLER CLASS
public class InputController {
    private GameController controller;
    private BoardView boardView;
    private Board board;
    private Piece draggedPiece;

    public InputController(GameController controller, Board board, BoardView boardView) {
        this.controller = controller;
        this.board = board;
        this.boardView = boardView;
        setupListeners();
    }

    public void setupListeners() {
        boardView.addMouseListener(new PieceMouseListener());
    }

    public void manageInput(int row, int col) {
        // Create and execute a MoveCommand
        MoveCommand moveCommand = new MoveCommand(controller, board, draggedPiece, row, col);
        moveCommand.execute();
    }

    private class PieceMouseListener extends MouseAdapter {
        /**
         * Invoked when a mouse button is pressed on a component.
         */
        @Override
        public void mousePressed(MouseEvent e) {
            // Get the x and y coordinates of the mouse press
            int x = e.getX();
            int y = e.getY();

            // Identify the cell (row, col) where the mouse was pressed
            int row = y / boardView.getCellHeight();
            int col = x / boardView.getCellWidth();

            // Retrieve the piece at the pressed cell
            draggedPiece = board.getPieceFromCell(row, col);

            // If a piece is found at the pressed cell, inform the game controller that
            // dragging has started
            if (draggedPiece != null) {
                manageInput(row, col);
            }
        }

        /**
         * Invoked when a mouse button has been released on a component.
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            // Get the x and y coordinates of the mouse release
            int x = e.getX();
            int y = e.getY();

            // Identify the cell (row, col) where the mouse was released
            int row = y / boardView.getCellHeight();
            int col = x / boardView.getCellWidth();

            // If a piece was being dragged, inform the game controller about the release
            if (draggedPiece != null) {
                manageInput(row, col);

                // Reset the dragged piece
                draggedPiece = null;
            }
        }
    }
}