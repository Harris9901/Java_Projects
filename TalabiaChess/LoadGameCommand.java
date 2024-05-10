import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//Julian and Imran
// COMMAND PATTERN
public class LoadGameCommand implements Command {
    private GameController controller;
    private Main main;
    private String saveFileName;

    public LoadGameCommand(GameController controller, Main main, String saveFileName) {
        this.controller = controller;
        this.main = main;
        this.saveFileName = saveFileName;
    }

    @Override
    public void execute() {
        try (BufferedReader reader = new BufferedReader(new FileReader(saveFileName))) {
            // Load game state from the file
            loadGameState(reader);
            System.out.println("Game loaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (show a message, log, etc.)
        }
    }

    private void loadGameState(BufferedReader reader) throws IOException {
        Board board = controller.getBoard();

        // Initialize board cells only if the board is null
        if (board == null) {
            board = new Board(new BoardView(), main);
            board.initializeCells();
        }

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("=");
            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                switch (key) {
                    case "BoardFlipped":
                        boolean savedBoardFlipped = Boolean.parseBoolean(value);
                        board.setBoardFlipped(savedBoardFlipped);
                        break;
                    case "YellowTurn":
                        board.setYellowTurn(Boolean.parseBoolean(value));
                        break;
                    case "TurnCounter":
                        board.setTurnCounter(Integer.parseInt(value));
                        break;
                }
            } else if (line.contains("Piece=")) {
                loadPiece(line, board);
            }
        }

    }

    private void loadPiece(String line, Board board) throws IOException {
        String[] parts = line.substring("Piece=".length()).split(",");
        // Assuming you have four properties for a piece
        String pieceType = parts[0].trim();
        int row = Integer.parseInt(parts[1].split("=")[1].trim());
        int col = Integer.parseInt(parts[2].split("=")[1].trim());
        String color = parts[3].split("=")[1].trim();
        boolean endOfBoard = false;
        if (pieceType.equals("Point")) {
            endOfBoard = Boolean.parseBoolean(parts[4].split("=")[1].trim()); // Add this line
        } // Create and set the piece on the board
        createPiece(pieceType, row, col, color, endOfBoard, board);
    }

    private void createPiece(String pieceType, int row, int col, String color, boolean endOfBoard, Board board) {
        // Create and set the piece on the board
        switch (pieceType) {
            case "Plus":
                board.setPieceAt(row, col, new Plus(row, col, color,
                        board.uploadImage("Assets\\" + color.toLowerCase() + "_plus.png"), board));
                break;
            case "Hourglass":
                board.setPieceAt(row, col, new Hourglass(row, col, color,
                        board.uploadImage("Assets\\" + color.toLowerCase() + "_hg.png"), board));
                break;
            case "Time":
                board.setPieceAt(row, col, new Time(row, col, color,
                        board.uploadImage("Assets\\" + color.toLowerCase() + "_time.png"), board));
                break;
            case "Sun":
                board.setPieceAt(row, col, new Sun(row, col, color,
                        board.uploadImage("Assets\\" + color.toLowerCase() + "_sun.png"), board));
                break;
            case "Point":
                Point pointPiece = new Point(row, col, color,
                        board.uploadImage("Assets\\" + color.toLowerCase() + "_arrow.png"), board);
                board.setPieceAt(row, col, pointPiece);
                pointPiece.setEndOfBoard(endOfBoard); // Set the endOfBoard property
                break;
        }
    }
}