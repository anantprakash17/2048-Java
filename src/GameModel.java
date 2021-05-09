/*
 * Author: Anant Prakash
 * Revised: May 8th 2021
 *
 * Description: Game Module for 2048 game. All game logic takes place here.
 */

package src;
import java.util.Arrays;
import java.util.Random;

/**
 * The type Game model.
 */
public class GameModel {
    private int[][] board;
    private boolean winner = false;
    private boolean winPossible = true;
    private int score;
    private int bestScore;

    /**
     * Instantiates a new Game model.
     */
    public GameModel() {
        score = 0;
        bestScore = 0;
        board = new int[4][4];
        for(int[] i : board) {
            Arrays.fill(i, 0);
        }
        insertRandomTile();
        insertRandomTile();
    }

    /**
     * Gets the game score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets boolean which is true if game is won.
     *
     * @return the boolean
     */
    public boolean isWinner() {
        return winner;
    }

    /**
     * Gets the boolean which holds if game is still winnable.
     *
     * @return the game status.
     */
    public boolean isWinPossible() {
        return winPossible;
    }

    /**
     * Gets the game board.
     *
     * @return 2d array containing the game board
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Gets best score of game.
     *
     * @return the best score
     */
    public int getBestScore() {
        return bestScore;
    }
