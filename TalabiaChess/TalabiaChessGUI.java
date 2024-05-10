import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//Imran and Liew
public class TalabiaChessGUI extends JFrame {
    private Main main;
    
    private JPanel startupPanel;
    private JPanel gameOverPanel;
    
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem saveMenuItem;
    private JMenuItem resumeMenuItem;
    private JMenuItem quitMenuItem;

    public TalabiaChessGUI(Main main) {
        setTitle("Talabia Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        this.main = main;

        // Create and set up the menu bar
        createMenuBar();
        // Initially, show the startup panel
        showStartupPanel();
    }

    public void setVisible(TalabiaChessGUI talabiaChessGUI)
    {
        talabiaChessGUI.setVisible(true);
    }
    private void createMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Menu");

        saveMenuItem = new JMenuItem("Save");
        resumeMenuItem = new JMenuItem("Resume");
        quitMenuItem = new JMenuItem("Quit");

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.saveGame();
            }
        });

        resumeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.loadGame();
            }
        });

        quitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStartupPanel();
            }
        });

        fileMenu.add(saveMenuItem);
        fileMenu.add(resumeMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(quitMenuItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }  

    public void showStartupPanel() {
        // Create the startup panel if it's not created yet
        if (startupPanel == null) {
            createStartupPanel();
        }

        // Set the content pane to display the startup panel
        setContentPane(startupPanel);
        revalidate(); // Ensure the changes are reflected
    }

    private void createStartupPanel() {
        startupPanel = new JPanel();
        startupPanel.setLayout(new BorderLayout());
    
        JLabel titleLabel = new JLabel("Talabia Chess");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
        // Create a panel for the buttons with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout());
    
        JButton newGameButton = new JButton("New Game");
        JButton loadGameButton = new JButton("Load Game");
        JButton quitGameButton = new JButton("Quit Game");
    
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.startNewGame();
            }
        });
    
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.loadGame();
            }
        });
    
        quitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.quitGame();
            }
        });
        
        // Add buttons to the button panel
        buttonPanel.add(newGameButton);
        buttonPanel.add(loadGameButton);
   
        buttonPanel.add(quitGameButton);
    
        // Add components to the startup panel
        startupPanel.add(titleLabel, BorderLayout.NORTH);
        startupPanel.add(buttonPanel, BorderLayout.CENTER);
    }
    public void showGameOverPanel() {
        // Create the startup panel if it's not created yet
        if (gameOverPanel == null) {
            createGameOverPanel();
        }
         // Set the content pane to display the startup panel
         setContentPane(gameOverPanel);
         revalidate(); // Ensure the changes are reflected
    }
    private void createGameOverPanel() {
        gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new BorderLayout());

        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 30));
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStartupPanel();
            }
        });
        
        // Add the game over label to the center of the panel
        buttonPanel.add(exitButton);
        gameOverPanel.add(gameOverLabel, BorderLayout.CENTER);
        gameOverPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void switchToGameView() {
        // Logic to switch to the game view
        // For example, set the content pane to display the game view
        setContentPane(main.getBoardView());
        revalidate(); // Ensure the changes are reflected
    }
}