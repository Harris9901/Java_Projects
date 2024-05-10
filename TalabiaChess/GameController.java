//MVC - CONTROLLER CLASS
//Liew
public class GameController {
    private Board board;

    public GameController(Board board) {
        this.board = board;
    }

    public void notifyDragging(int row, int col, Piece piece) {
        board.startDragging(row, col, piece);
    }

    public void notifyReleasing(int row, int col, Piece piece) {
        board.dropPieceAt(row, col, piece);
    }

    public Board getBoard() {
        return board;
    }
}