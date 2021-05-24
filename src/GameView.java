/*
 * Author: Anant Prakash
 * Revised: May 24th 2021
 *
 * Description: View module for 2048 Game MVC.
 */

package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

/**
 * The type Game view.
 */
public class GameView extends JFrame {
    private final JLabel score = new JLabel("Score 0");
    private final JLabel bestScore = new JLabel("Best Score 0");
    private final JLabel gameBoard = new JLabel("ERR", SwingConstants.CENTER);

    /**
     * Instantiates a new Game view.
     */
    public GameView() {
        JLabel title = new JLabel("2048");
        title.setBounds(100,60,300,70);
        title.setFont(new Font("SansSerif", Font.BOLD,80));
        title.setForeground(new Color(0x776E65));
        score.setText("Score 0");
        score.setForeground(new Color(0x776E65));
        score.setBounds(300,10,200,30);
        score.setFont(new Font("Serif", Font.BOLD, 20));
        bestScore.setText("Best Score 0");
        bestScore.setForeground(new Color(0x776E65));
        bestScore.setBounds(257,30,200,30);
        bestScore.setFont(new Font("Serif", Font.BOLD, 20));
        gameBoard.setOpaque(true);
        gameBoard.setBackground(new Color(187, 173, 160));
        gameBoard.setBounds(25,25,300,300);
        gameBoard.setFont(new Font("Serif", Font.BOLD, 33));
        gameBoard.setVerticalAlignment(SwingConstants.TOP);
        JLayeredPane gameBackground = new JLayeredPane();
        gameBackground.setBounds(25,150,350,350);
        gameBackground.setOpaque(true);
        gameBackground.setBackground(new Color(187, 173, 160, 126));
        gameBackground.add(gameBoard);
        this.setLayout(null);
        this.setTitle("2048");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,550);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(255, 247, 224));
        this.add(score);
        this.add(bestScore);
        this.add(title);
        this.add(gameBackground);
    }

    /**
     * Print ascii version of board, this method is unused because
     * GUI was made for bonus mark.
     *
     * @param board the 2048 board to print.
     */
    public void printASCII(int[][] board) {
        System.out.println("=============================");
        for (int i = 0; i < 4; i++) {
            System.out.println("|      |      |      |      |");
            for (int j = 0; j < 4; j++) {
                int currVal = board[i][j];
                if (currVal < 16) {
                    System.out.print("|  "+currVal + "   ");
                } else if (currVal < 128) {
                    System.out.print("|  " + currVal + "  ");
                } else if (currVal < 1024) {
                    System.out.print("| " + currVal + "  ");
                }
            }
            System.out.print("|");
            System.out.println();
            System.out.println("|      |      |      |      |");
            System.out.println("|---------------------------|");
        }
    }

    /**
     * Formats the board into an HTML table to JLabel can process the HTML
     * and generate a table for the board in the GUI.
     *
     * @param board       the board
     * @param winPossible Is the game still winnable?
     * @param isWinner    Has the game been won?
     * @return The html table code for the board in String format.
     */
    public String formatBoard(int[][] board, boolean winPossible, boolean isWinner) {
        StringBuilder html = new StringBuilder("<html><table cellspacing=\"0\" cellpadding=\"0\"><tbody>");
        for (int i = 0; i < 4; i++) {
            html.append("<tr>");
            for (int j = 0; j < 4; j++) {
                int currVal = board[i][j];
                if (currVal == 0) {
                    html.append("<td style=\"background-color:#cdc0b4;color:#cdc0b4;text-align:center;\">");
                    html.append("0000");
                } else {
                    if (currVal == 2) {
                        html.append("<td style=\"color:#776E65;background-color:#EEE4DA;text-align:center;\">");
                    } else if (currVal == 4) {
                        html.append("<td style=\"color:#776E65;background-color:#EDE0C8;text-align:center;\">");
                    } else if (currVal == 8) {
                        html.append("<td style=\"color:#F9F6F2;background-color:#F2B179;text-align:center;\">");
                    } else if (currVal == 16) {
                        html.append("<td style=\"color:#F9F6F2;background-color:#F59563;text-align:center;\">");
                    } else if (currVal == 32) {
                        html.append("<td style=\"color:#F9F6F2;background-color:#F67C5F;text-align:center;\">");
                    } else if (currVal == 64) {
                        html.append("<td style=\"color:#F9F6F2;background-color:#F65E3B;text-align:center;\">");
                    } else if (currVal == 128) {
                        html.append("<td style=\"color:#F9F6F2;background-color:#EDCF72;text-align:center;\">");
                    } else if (currVal == 256) {
                        html.append("<td style=\"color:#F9F6F2;background-color:#EDCC61;text-align:center;\">");
                    } else if (currVal == 512) {
                        html.append("<td style=\"color:#F9F6F2;background-color:#EDC850;text-align:center;\">");
                    } else if (currVal == 1024) {
                        html.append("<td style=\"color:#F9F6F2;background-color:#EDC53F;text-align:center;\">");
                    } else if (currVal == 2048) {
                        html.append("<td style=\"color:#F9F6F2;background-color:#EDC22E;text-align:center;\">");
                    }
                    if (currVal < 16) {
                        html.append("&nbsp&nbsp&nbsp;");
                    } else if (currVal < 128) {
                        html.append("&nbsp&nbsp;");
                    } else if (currVal < 1024) {
                        html.append("&nbsp;");
                    }
                    html.append(currVal);
                    if (currVal < 16) {
                        html.append("&nbsp&nbsp&nbsp");
                    } else if (currVal < 128) {
                        html.append("&nbsp&nbsp");
                    } else if (currVal < 1024) {
                        html.append("&nbsp");
                    }
                }
                html.append("</td");
            }
            html.append("<tr/>");
        }
        html.append("</tbody></table></html>");
        if (!winPossible) {
            showPopup("Game Over, Press OK to start a new game.");
            setScore(Integer.toString(0));
            return formatBoard(board, true, false);
        } else if (isWinner) {
            showPopup("Game Won! Press OK to start a new game.");
            setScore(Integer.toString(0));
            return formatBoard(board, true, false);
        }
        updateBoard(html.toString());
        return html.toString();
    }

    private void showPopup(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    private void updateBoard(String text) {
        gameBoard.setText(text);
    }

    /**
     * Sets the score label. Used to update the score
     * as the game goes on.
     *
     * @param score the score
     */
    public void setScore(String score) {
        this.score.setText("Score " + score);
    }

    /**
     * Sets best score JLabel text..
     *
     * @param score the score as string.
     */
    public void setBestScore(String score) {
        this.bestScore.setText("Best Score " + score);
    }

    /**
     * Sets the KeyListener to update components based on input from
     * the controller.
     *
     * @param listener the KeyListener instance.
     */
    public void setListener(KeyListener listener) {
        this.addKeyListener(listener);
    }
}
