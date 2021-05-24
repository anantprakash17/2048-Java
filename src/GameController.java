/*
 * Author: Anant Prakash
 * Revised: May 20th 2021
 *
 * Description: Controller module for 2048. All user interaction is handled
 * in this module.
 */

package src;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The type Game controller.
 */
public class GameController implements KeyListener {

    private final GameView view;
    private final GameModel model;

    /**
     * Instantiates a new Game controller.
     *
     * @param view  the view to control
     * @param model the model to control
     */
    public GameController(GameView view, GameModel model) {
        this.model = model;
        this.view = view;
        this.view.setListener(this);
        this.view.formatBoard(model.getBoard(), model.isWinPossible(), model.isWinner());
    }

    /**
     * Gets the board from the model and updates the view
     * based on if the game has been won or not, and if the game is still winnable.
     */
    public void printBoard() {
        if(!model.isWinPossible()) {
            if(model.getScore() > model.getBestScore()) {
                view.setBestScore(Integer.toString(model.getScore()));
            }
            model.resetBoard();
            view.formatBoard(model.getBoard(), false, false);
        } else if (model.isWinner()) {
            if(model.getScore() > model.getBestScore()) {
                view.setBestScore(Integer.toString(model.getScore()));
            }
            model.resetBoard();
            view.formatBoard(model.getBoard(), true, true);
        }
        view.formatBoard(model.getBoard(), model.isWinPossible(), model.isWinner());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    /*
     * Reads the keystrokes from the keyboard and moves the board
     * in the chosen direction.
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!model.isWinner() && model.isWinPossible()) {
            if (key == 37) {
                model.move(Direction.Left);
                view.setScore(Integer.toString(model.getScore()));
            } else if (key == 38) {
                model.move(Direction.Up);
                view.setScore(Integer.toString(model.getScore()));
            } else if (key == 39) {
                model.move(Direction.Right);
                view.setScore(Integer.toString(model.getScore()));
            } else if (key == 40) {
                model.move(Direction.Down);
                view.setScore(Integer.toString(model.getScore()));
            }
            printBoard();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
