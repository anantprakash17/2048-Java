/*
 * Author: Anant Prakash
 * Revised: May 13th 2021
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

    /**
     * Shifts and merges the tiles in the given direction.
     *
     * @param direction the direction in which to shift/merge.
     */
    public void move(Direction direction) {
        boolean tileMoved = false;
        boolean tileMerged = false;
        if (direction == Direction.Left) {
            for (int k = 0; k < 4; k++) {
                int[] newLine = new int[4];
                Arrays.fill(newLine,0);
                Integer previous = null;
                int j = 0;
                for (int i = 0; i < 4 ; i++) {
                    if (board[k][i] != 0) {
                        if (previous == null) {
                            previous = board[k][i];
                        } else {
                            if (previous == board[k][i]) {
                                newLine[j] = board[k][i] * 2;
                                score = score + newLine[j];
                                tileMerged = true;
                                if(newLine[j] == 2048) {
                                    winner = true;
                                }
                                j++;
                                previous = null;
                            } else {
                                newLine[j] = previous;
                                j++;
                                previous = board[k][i];
                            }
                        }
                    }
                }
                if (previous != null) {
                    newLine[j] = previous;
                }
                if (!Arrays.equals(newLine, board[k])) {
                    tileMoved = true;
                }
                board[k] = newLine;
            }
            if (checkGameOver()) {
                winPossible = false;
            } else if (tileMerged || tileMoved){
                insertRandomTile();
            }
        } else if (direction == Direction.Right) {
            for (int k = 0; k < 4; k++) {
                int[] newLine = new int[4];
                Arrays.fill(newLine,0);
                Integer previous = null;
                int j = 3;
                for (int i = 3; i >= 0 ; i--) {
                    if (board[k][i] != 0) {
                        if (previous == null) {
                            previous = board[k][i];
                        } else {
                            if (previous == board[k][i]) {
                                newLine[j] = board[k][i] * 2;
                                score += newLine[j];
                                tileMerged = true;
                                if(newLine[j] == 2048) {
                                    winner = true;
                                }
                                j--;
                                previous = null;
                            } else {
                                newLine[j] = previous;
                                j--;
                                previous = board[k][i];
                            }
                        }
                    }
                }
                if (previous != null) {
                    newLine[j] = previous;
                }
                if (!Arrays.equals(board[k],newLine)) {
                    tileMoved = true;
                }
                board[k] = newLine;
            }
            if (checkGameOver()) {
                winPossible = false;
            }  else if (tileMerged || tileMoved){
                insertRandomTile();
            }
        } else if (direction == Direction.Up) {
            transpose();
            move(Direction.Left);
            transpose();
        } else if (direction == Direction.Down) {
            transpose();
            move(Direction.Right);
            transpose();
        }
    }

    private void insertRandomTile() {
        Random rand = new Random();
        int rand1;
        int rand2;
        while(containsEmptySpot()){
            rand1 = rand.nextInt(4);
            rand2 = rand.nextInt(4);
            if(board[rand1][rand2] != 0) {
                continue;
            }
            board[rand1][rand2] = rand.nextInt(10) == 9 ? 4 : 2;
            break;
        }
    }

    private boolean containsEmptySpot() {
        for (int[] i : board) {
            for (int j : i) {
                if (j == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private void transpose() {
        int[][] newBoard = new int[][] {new int[] {0,0,0,0},
                new int[] {0,0,0,0},
                new int[] {0,0,0,0},
                new int[] {0,0,0,0}};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newBoard[i][j] = board[j][i];
            }
        }
        board = newBoard;
    }

    private boolean checkGameOver() {
        boolean gameOver = true;
        for (int i = 0; i < 4; i ++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0 || board[i][j+1] == 0) {
                    return false;
                } else if (board[i][j] == board[i][j+1]) {
                    gameOver = false;
                }
            }
        }
        transpose();
        for (int i = 0; i < 4; i ++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0 || board[i][j+1] == 0) {
                    return false;
                } else if (board[i][j] == board[i][j+1]) {
                    gameOver = false;
                }
            }
        }
        transpose();
        return gameOver;
    }

    /**
     * Resets the board for a new game
     * and saves new high score if there is one.
     */
    public void resetBoard() {
        winPossible = true;
        winner = false;
        board = new int[4][4];
        if (score > bestScore) {
            bestScore = score;
        }
        score = 0;
        for(int[] i : board) {
            Arrays.fill(i, 0);
        }
        insertRandomTile();
        insertRandomTile();
    }
}

