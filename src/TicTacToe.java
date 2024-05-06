import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JLabel scoreLabel = new JLabel("Scores: X - 0 || O - 0");
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JButton[][] board = new JButton[3][3];
    JButton restartButton = new JButton("Restart");

    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;
    String winner = "";

    boolean gameOver = false;
    int turns = 0;
    int scoreX = 0;
    int scoreO = 0;

    public TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.DARK_GRAY);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        scoreLabel.setBackground(Color.DARK_GRAY);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel, BorderLayout.NORTH);
        textPanel.add(scoreLabel, BorderLayout.CENTER);
        frame.add(textPanel, BorderLayout.NORTH);
 
        boardPanel.setLayout(new GridLayout(3, 3));    
        boardPanel.setBackground(Color.DARK_GRAY);
        frame.add(boardPanel);

        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setBackground(Color.GRAY);
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        // Add restart button to the frame, initially invisible
        restartButton.setVisible(false);
        frame.add(restartButton, BorderLayout.SOUTH);

        // Initialize the game board
        initializeBoard();
    }  

    void initializeBoard() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);
                tile.setBackground(Color.DARK_GRAY);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                //tile.setText(currentPlayer);
                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText() == "") {
                            tile.setText(currentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer == playerX ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn.");        
                            }
                        }
                    }
                });
            }
        }
    }

    void resetGame() {
        // Clear the board
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setForeground(Color.white);
                board[r][c].setBackground(Color.DARK_GRAY);
            }
        }
        // Reset game variables
        currentPlayer = playerX;
        gameOver = false;
        turns = 0;
        textLabel.setText("Tic-Tac-Toe");
        // Hide the restart button
        restartButton.setVisible(false);
    }

    void checkWinner() {
        //horizontal
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText() == "") continue;
            if (board[r][0].getText() == board[r][1].getText() && board[r][1].getText() == board[r][2].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                if (currentPlayer == playerX) {
                    scoreX++;
                } else {
                    scoreO++;
                }        
                scoreLabel.setText("Scores: X - " + scoreX + " || O - " + scoreO);
                return;
            }
        }

        //vertical
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText() == "") continue;
            if (board[0][c].getText() == board[1][c].getText() && board[1][c].getText() == board[2][c].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                if (currentPlayer == playerX) {
                    scoreX++;
                } else {
                    scoreO++;
                }        
                scoreLabel.setText("Scores: X - " + scoreX + " || O - " + scoreO);
                return;
            }
        }  
        
        //diagonally
        if (board[0][0].getText() == board[1][1].getText() && board[1][1].getText() == board[2][2].getText() && board[0][0].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
           }
           gameOver = true;
           if (currentPlayer == playerX) {
                scoreX++;
            } else {
                scoreO++;
            }
            scoreLabel.setText("Scores: X - " + scoreX + " || O - " + scoreO);
            return;
        }

         //anti-diagonally
         if (board[0][2].getText() == board[1][1].getText() && board[1][1].getText() == board[2][0].getText() && board[0][2].getText() != "") {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;        
            if (currentPlayer == playerX) {
                scoreX++;
            } else {
                scoreO++;
            }
            scoreLabel.setText("Scores: X - " + scoreX + " || O - " + scoreO);
            return;
        }

        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.GREEN);
        tile.setBackground(Color.GRAY);
        textLabel.setText(currentPlayer + " is the winner!!");
        restartButton.setVisible(true);
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.ORANGE);
        tile.setBackground(Color.GRAY);
        textLabel.setText("Tie!!");
        restartButton.setVisible(true);
    }
}
