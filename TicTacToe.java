// Tac Toe is fun!
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class TicTacToe extends Frame implements ActionListener {
    private Button[][] buttons = new Button[3][3];
    private Button newGameButton;
    private boolean isXTurn = true;
    private Label statusLabel;
    private Color bgColor = new Color(240, 240, 240);
    private Color buttonColor = new Color(255, 255, 255);
    private Color xColor = new Color(44, 62, 80);
    private Color oColor = new Color(231, 76, 60);
    private Color accentColor = new Color(52, 152, 219);

    public TicTacToe() {
        setTitle("Modern Tic Tac Toe");
        setSize(500, 550);
        setLayout(new BorderLayout());
        setBackground(bgColor);

        try {
            setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
        } catch (Exception e) {
           
        }

        Panel headerPanel = new Panel();
        headerPanel.setBackground(bgColor);
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        Label titleLabel = new Label("TIC TAC TOE");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        headerPanel.add(titleLabel);

        add(headerPanel, BorderLayout.NORTH);

        Panel gamePanel = new Panel();
        gamePanel.setLayout(new GridLayout(3, 3, 10, 10));
        gamePanel.setBackground(bgColor);
       

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new Button("");
                buttons[row][col].setFont(new Font("Segoe UI", Font.BOLD, 60));
                buttons[row][col].setBackground(buttonColor);
                buttons[row][col].setForeground(new Color(44, 62, 80));
                buttons[row][col].addActionListener(this);
             

                gamePanel.add(buttons[row][col]);
            }
        }

        Panel controlPanel = new Panel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setBackground(bgColor);
      

        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(bgColor);

        newGameButton = new Button("NEW GAME");
        newGameButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        newGameButton.setBackground(accentColor);
        newGameButton.setForeground(Color.WHITE);
        newGameButton.addActionListener(this);
        newGameButton.setPreferredSize(new Dimension(150, 40));

        buttonPanel.add(newGameButton);

        statusLabel = new Label("Player X's turn", Label.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        statusLabel.setForeground(new Color(44, 62, 80));

        controlPanel.add(statusLabel, BorderLayout.NORTH);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(gamePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            resetGame();
            return;
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (e.getSource() == buttons[row][col]) {
                    if (buttons[row][col].getLabel().equals("")) {
                        if (isXTurn) {
                            buttons[row][col].setLabel("X");
                            buttons[row][col].setForeground(xColor);
                            statusLabel.setText("Player O's turn");
                        } else {
                            buttons[row][col].setLabel("O");
                            buttons[row][col].setForeground(oColor);
                            statusLabel.setText("Player X's turn");
                        }
                        isXTurn = !isXTurn;
                        checkWinner();
                    }
                    return;
                }
            }
        }
    }

    private void checkWinner() {
        String winner = null;

        for (int row = 0; row < 3; row++) {
            if (!buttons[row][0].getLabel().equals("") &&
                buttons[row][0].getLabel().equals(buttons[row][1].getLabel()) &&
                buttons[row][0].getLabel().equals(buttons[row][2].getLabel())) {
                winner = buttons[row][0].getLabel();
                highlightWinningCells(row, 0, row, 1, row, 2);
                break;
            }
        }

        if (winner == null) {
            for (int col = 0; col < 3; col++) {
                if (!buttons[0][col].getLabel().equals("") &&
                    buttons[0][col].getLabel().equals(buttons[1][col].getLabel()) &&
                    buttons[0][col].getLabel().equals(buttons[2][col].getLabel())) {
                    winner = buttons[0][col].getLabel();
                    highlightWinningCells(0, col, 1, col, 2, col);
                    break;
                }
            }
        }

        if (winner == null) {
            if (!buttons[0][0].getLabel().equals("") &&
                buttons[0][0].getLabel().equals(buttons[1][1].getLabel()) &&
                buttons[0][0].getLabel().equals(buttons[2][2].getLabel())) {
                winner = buttons[0][0].getLabel();
                highlightWinningCells(0, 0, 1, 1, 2, 2);
            } else if (!buttons[0][2].getLabel().equals("") &&
                       buttons[0][2].getLabel().equals(buttons[1][1].getLabel()) &&
                       buttons[0][2].getLabel().equals(buttons[2][0].getLabel())) {
                winner = buttons[0][2].getLabel();
                highlightWinningCells(0, 2, 1, 1, 2, 0);
            }
        }

        boolean isDraw = true;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getLabel().equals("")) {
                    isDraw = false;
                    break;
                }
            }
            if (!isDraw) break;
        }

        if (winner != null) {
            statusLabel.setText("Player " + winner + " wins!");
            disableButtons();
        } else if (isDraw) {
            statusLabel.setText("It's a draw!");
        }
    }

    private void highlightWinningCells(int r1, int c1, int r2, int c2, int r3, int c3) {
        Color winColor = new Color(214, 234, 248);
        buttons[r1][c1].setBackground(winColor);
        buttons[r2][c2].setBackground(winColor);
        buttons[r3][c3].setBackground(winColor);
    }

    private void disableButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setLabel("");
                buttons[row][col].setEnabled(true);
                buttons[row][col].setBackground(buttonColor);
            }
        }
        isXTurn = true;
        statusLabel.setText("Player X's turn");
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.setVisible(true);
    }
}
