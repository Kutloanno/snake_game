import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
    final int SCREEN_WIDTH = 500;
    final int SCREEN_HEIGHT = 500;
    final int CELL_SIZE = 25;
    final int GAME_CELLS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (CELL_SIZE * CELL_SIZE);
    final int DELAY = 65; //Timer delay/Game speed
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
    Image blueApple;
    Image greenApple;
    Image redApple;
    int appleChoice;

    public GamePanel() {
        forestBackground = new ImageIcon("images/forestBackground.jpg").getImage();
        cityBackground = new ImageIcon("images/cityBackground.jpg").getImage();

        blueApple = new ImageIcon("images/blueApple.png").getImage();
        greenApple = new ImageIcon("images/greenApple.png").getImage();
        redApple = new ImageIcon("images/redApple.png").getImage();

        random = new Random();


        InputMap panelInputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap panelActionMap = this.getActionMap();

        panelInputMap.put(KeyStroke.getKeyStroke("UP"), "upActionMapKey");
        panelInputMap.put(KeyStroke.getKeyStroke('w'), "upActionMapKey");
        panelActionMap.put("upActionMapKey", new KeyAction('U'));

        panelInputMap.put(KeyStroke.getKeyStroke("DOWN"), "downActionMapKey");
        panelInputMap.put(KeyStroke.getKeyStroke('s'), "downActionMapKey");
        panelActionMap.put("downActionMapKey", new KeyAction('D'));

        panelInputMap.put(KeyStroke.getKeyStroke("LEFT"), "leftActionMapKey");
        panelInputMap.put(KeyStroke.getKeyStroke('l'), "leftActionMapKey");
        panelActionMap.put("leftActionMapKey", new KeyAction('L'));

        panelInputMap.put(KeyStroke.getKeyStroke("RIGHT"), "rightActionMapKey");
        panelInputMap.put(KeyStroke.getKeyStroke('r'), "rightActionMapKey");
        panelActionMap.put("rightActionMapKey", new KeyAction('R'));

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.setFocusable(true);
        startGame();
    }

    public void startGame() {
        newApple();
        appleChoice = 1;
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); //Overrode JPanel paintComponent
        draw(g); //Implement draw image
    }

    public void draw(Graphics g) {
        if (running) {
            //Draw Grid
            /*for (int i = 0; i <= SCREEN_HEIGHT / CELL_SIZE; i++) {
                g.setColor(Color.BLACK);
                g.drawLine(0, i * CELL_SIZE, SCREEN_WIDTH, i * CELL_SIZE); //Draw horizontal line
                g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, SCREEN_HEIGHT); //Draw vertical line
            }*/

           //Draw Background
           g.drawImage(forestBackground, 0, 0, null);

            //Draw Apple
            switch(appleChoice) {
                case 1 -> g.drawImage(redApple, appleX, appleY, null);
                case 2 -> g.drawImage(greenApple, appleX, appleY, null);
                case 3 -> g.drawImage(blueApple, appleX, appleY, null);
            }

            //Draw Snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) g.setColor(new Color(1, 150, 32));
                else {
                    g.setColor(new Color(144, 238, 144));
                    //g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                }
                g.fillRect(x[i], y[i], CELL_SIZE, CELL_SIZE);
            }

            //Draw Score
            g.setColor(Color.RED);
            g.setFont(new Font("Monospaced", Font.BOLD, 26));
            FontMetrics fontMetrics = g.getFontMetrics();
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - fontMetrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void gameOver(Graphics g) {

    }

    public void newApple() {
        //Spawn new apple
        appleX = random.nextInt(SCREEN_WIDTH / CELL_SIZE) * CELL_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / CELL_SIZE) * CELL_SIZE;
    }

    public void checkApple() {
        //Check for apple collisions
        if (x[0] == appleX && y[0] == appleY) {
            appleChoice = random.nextInt(1, 4);
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

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch(direction) {
            case 'U' -> y[0] -= CELL_SIZE;
            case 'D' -> y[0] += CELL_SIZE;
            case 'L' -> x[0] -= CELL_SIZE;
            case 'R' -> x[0] += CELL_SIZE;
        }
    }

    public class KeyAction extends AbstractAction {
        char way;

        public KeyAction(char way) {
            this.way = way;
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            switch (way) {
                case 'U' -> {if (direction != 'D') direction = 'U';}
                case 'D' -> {if (direction != 'U') direction = 'D';}
                case 'L' -> {if (direction != 'R') direction = 'L';}
                case 'R' -> {if (direction != 'L') direction = 'R';}
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        this.repaint();
    }
}