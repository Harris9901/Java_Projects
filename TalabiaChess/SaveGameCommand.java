import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//Julian
//Command Pattern
public class SaveGameCommand implements Command {
    private GameController controller;
    private String saveFileName;

    public SaveGameCommand(GameController controller, String saveFileName) {
        this.controller = controller;
        this.saveFileName = saveFileName;
    }

    // Override of the execute method from the Command interface
    @Override
    public void execute() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(saveFileName))) {
            // Save game state to the file
            saveGameState(writer);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (show a message, log, etc.)
        }
    }

    // Method to save the game state to the file
    private void saveGameState(PrintWriter writer) {
        Board board = controller.getBoard();

        // Save game state properties
        writer.println(String.format("BoardFlipped=%s", board.boardFlipped));
        writer.println(String.format("YellowTurn=%s", board.isYellowTurn()));
        writer.println(String.format("TurnCounter=%s", board.getTurnCounter()));

        // Save pieces
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                Piece piece = board.getPieceFromCell(row, col);
                if (piece != null) {
                    savePiece(writer, piece, row, col);
                }
            }
        }
    }

    // Method to save information about a specific piece to the file
    private void savePiece(PrintWriter writer, Piece piece, int row, int col) {
        // Check if the piece is an instance of the Point class
        if (piece instanceof Point) {
            Point pointPiece = (Point) piece;
            // Save information including endOfBoard for the Point piece
            writer.println(String.format("Piece=%s,Row=%s,Col=%s,Color=%s,EndofBoard=%s",
                    piece.getClass().getSimpleName(), row, col, piece.getColor(), pointPiece.getEndOfBoard()));
        }
        // If the piece is not an instance of the Point class
        else {
            // Save basic information for the piece
            writer.println(String.format("Piece=%s,Row=%s,Col=%s,Color=%s",
                    piece.getClass().getSimpleName(), row, col, piece.getColor()));
        }

    }
}