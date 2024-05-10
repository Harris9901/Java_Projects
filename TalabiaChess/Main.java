//Liew,Imran and Julian
// MVC - MODEL CLASS
public class Main {
    private Board board;
    private BoardView boardView;
    private GameController controller;
    private InputController inputController;
    private TalabiaChessGUI talabiaChessGUI;
    private String saveFileName = "savegame.txt";

    public Main() {
        talabiaChessGUI = new TalabiaChessGUI(this);
        talabiaChessGUI.setVisible(talabiaChessGUI);
    }

    public BoardView getBoardView() {
        return boardView;
    }

    public void setupGameComponents() {
        // Set up the game components (similar to your existing code)

        boardView.setBoard(board);
        controller = new GameController(board);
        inputController = new InputController(controller, board, boardView);

        // Pass the reference to TalabiaChessGUI to GameController
        board.setTalabiaChessGUI(talabiaChessGUI);
    }

    public void saveGame() {
        // Create and execute the SaveGameCommand
        SaveGameCommand saveCommand = new SaveGameCommand(controller, saveFileName);
        saveCommand.execute();
    }

    public void loadGame() {
        // Create and execute the LoadGameCommand
        // // Initialize board and boardView if not already done
        if (boardView == null) {
            boardView = new BoardView();
        }

        if (board == null) {
            board = new Board(boardView, this);
        }
        board.clearBoard();
        setupGameComponents();
        LoadGameCommand loadCommand = new LoadGameCommand(controller, this, saveFileName);

        loadCommand.execute();
        talabiaChessGUI.switchToGameView();
    }
    
    public void startNewGame() {
        // Create the main game components
        boardView = new BoardView();
        board = new Board(boardView, this);

        // Set up the game components
        setupGameComponents();

        // Logic to start a new game
        System.out.println("New Game Started");
        // You may want to switch to the game view here

        talabiaChessGUI.switchToGameView();
    }

    public void quitGame() {
        // Logic to quit the game
        System.out.println("Quit Game");
        // You may want to perform cleanup or exit the application
        System.exit(0);
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}