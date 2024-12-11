import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
    final int SCREEN_WIDTH = 500;
    final int SCREEN_HEIGHT = 500;
    final int CELL_SIZE = 25;
    final int GAME_CELLS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (CELL_SIZE * CELL_SIZE);
    final int DELAY = 75; //Timer delay/Game speed
    int x[] = new int[GAME_CELLS]; //Array for all possible snake x positions
    int y[] = new int[GAME_CELLS]; //Array for all possible snake y positions
    char direction = 'R';
    boolean running = false;
    int bodyParts = 4;
    int applesEaten = 0;
    int appleX;
    int appleY;
    Timer timer;
    Random random;
    Image forestBackground;
    Image cityBackground;

    public GamePanel() {
        random = new Random();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setLayout(null)   ;
        this.setFocusable(true);
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void newApple() {
        //Spawn new apple
        appleX = random.nextInt(SCREEN_WIDTH) / CELL_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT) / CELL_SIZE;
    }

    public void checkApple() {
        //Check for apple collisions
        if (x[0] == appleX && y[0] == appleY) {
            newApple();
            applesEaten++;
            bodyParts++;
        }
    }

    public void checkCollisions() {
        //Check for body collisions
        for (int i = bodyParts; i > 0; i--) {
            if(x[0] == x[i] && y[0] == y[i]) {
                running = false; //set state of game to not running
                timer.stop(); //stop timer upon collison
            }
        }

        //Check for border collisions
        if(x[0] < 0 || x[0] > SCREEN_WIDTH  ||  y[0] < 0 || y[0] > SCREEN_HEIGHT) {
            running = false; //set state of game to not running
            timer.stop(); //stop timer upon collison
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
    }
}