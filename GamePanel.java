import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
    final int SCREEN_WIDTH = 500;
    final int SCREEN_HEIGHT = 500;
    final int CELL_SIZE = 25;
    final int GAME_CELLS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (CELL_SIZE * CELL_SIZE);
    int x[] = new int[GAME_CELLS]; //Array for all possible snake x positions
    int y[] = new int[GAME_CELLS]; //Array for all possible snake y positions
    int DELAY = 85; //Timer delay/Game speed
    char direction = 'R';
    int applesEaten = 0;
    int appleX;
    int appleY;
    Timer timer;
    Random random;
    Image forestBackground;
    Image cityBackground;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.setFocusable(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

    }
}