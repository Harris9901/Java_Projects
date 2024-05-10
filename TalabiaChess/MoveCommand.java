//Liew
// COMMAND PATTERN
public class MoveCommand implements Command {
    private GameController controller;
    private Board board;
    private Piece piece;
    private int row, col;

    public MoveCommand(GameController controller, Board board, Piece piece, int row, int col) {
        this.controller = controller;
        this.board = board;
        this.piece = piece;
        this.row = row;
        this.col = col;
    }

    @Override
    public void execute() {
        // Determine if it's the start or end of the move
        if (board.isStartOfMove()) {
            // Inform the controller that dragging has started
            controller.notifyDragging(row, col, piece);
        } else {
            // Inform the controller that dropping has occurred
            controller.notifyReleasing(row, col, piece);
        }
    }
}