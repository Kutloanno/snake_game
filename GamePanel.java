import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class GamePanel extends JPanel implements ActionListener {
    final int SCREEN_WIDTH = 500;
    final int SCREEN_HEIGHT = 500;
    final int CELL_SIZE = 25;
    final int GAME_CELLS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (CELL_SIZE * CELL_SIZE);
    final int DELAY = 75; //Timer delay/Game speed
    int x[] = new int[GAME_CELLS]; //Array for all possible snake x positions
    int y[] = new int[GAME_CELLS]; //Array for all possible snake y positions
    char direction = 'R';
    boolean running;
    boolean gameEnded = false;
    int bodyParts = 4;
    int applesEaten = 0;
    int appleX;
    int appleY;
    Timer timer;
    Random random;
    Image snakeRuinsBackground;
    Image gameOverBackground;
    Image blueApple;
    Image greenApple;
    Image redApple;
    int appleChoice;
    JLabel startLabel;
    JButton startButton;
    JButton exitButton;
    JButton restartButton;
    JRadioButton rainbowbutton;
    JRadioButton staticColourButton;
    Color bodyColor = new Color(240, 240, 240);
    boolean rainbowBody = false;
    GameFrame gameFrame;

    public GamePanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        snakeRuinsBackground = new ImageIcon("images/snakeRuins.jpg").getImage();
        gameOverBackground = new ImageIcon("images/darkSnakeBackground.jpg").getImage();

        blueApple = new ImageIcon("images/blueApple.png").getImage();
        greenApple = new ImageIcon("images/greenApple.png").getImage();
        redApple = new ImageIcon("images/redApple.png").getImage();

        random = new Random();

        startLabel = new JLabel();
        startLabel.setLayout(null);
        startLabel.setBounds(125, 120, 250, 250);
        startLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.WHITE));

        startButton = new JButton("Start");
        startButton.setBounds(50, 40, 150, 50);
        startButton.addActionListener(this);
        startButton.setFocusable(false);
        startButton.setContentAreaFilled(false);
        startButton.setFont(new Font("Fato", Font.BOLD, 32));
        startButton.setForeground(bodyColor);
        startButton.setMnemonic(KeyEvent.VK_S);
        startButton.setEnabled(true);

        exitButton = new JButton("Exit");
        exitButton.setBounds(50, 150,150, 50);
        exitButton.addActionListener(this);
        exitButton.setFocusable(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFont(new Font("Fato", Font.BOLD, 32));
        exitButton.setForeground(bodyColor);
        exitButton.setMnemonic(KeyEvent.VK_E);

        restartButton = new JButton("Restart");
        restartButton.setBounds(25, 10, 200, 60);
        restartButton.addActionListener(this);
        restartButton.setFocusable(false);
        restartButton.setContentAreaFilled(false);
        restartButton.setFont(new Font("Fato", Font.BOLD, 32));
        restartButton.setForeground(Color.RED);
        restartButton.setMnemonic(KeyEvent.VK_R);
        restartButton.setEnabled(false);
        restartButton.setVisible(false);
        

        rainbowbutton = new JRadioButton("Rainbow");
        rainbowbutton.setBounds(25, 100, 120, 50);
        rainbowbutton.setOpaque(false);
        rainbowbutton.addActionListener(this);
        rainbowbutton.setFocusable(false);
        rainbowbutton.setFont(new Font("Fato", Font.BOLD, 18));
        rainbowbutton.setForeground(bodyColor);
        rainbowbutton.setIcon(new ImageIcon("images/unchecked-mark.png"));
        rainbowbutton.setSelectedIcon(new ImageIcon("images/checked-mark.png"));
        rainbowbutton.setMnemonic(KeyEvent.VK_R);
        rainbowbutton.setEnabled(true);

        staticColourButton = new JRadioButton("Static");
        staticColourButton.setBounds(155, 100, 120, 50);
        staticColourButton.setBackground(null);
        staticColourButton.setFocusable(false);
        staticColourButton.setOpaque(false);
        staticColourButton.addActionListener(this);
        staticColourButton.setFont(new Font("Fato", Font.BOLD, 18));
        staticColourButton.setForeground(bodyColor);
        staticColourButton.setIcon(new ImageIcon("images/unchecked-mark.png"));
        staticColourButton.setSelectedIcon(new ImageIcon("images/checked-mark.png"));
        staticColourButton.setEnabled(true);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rainbowbutton);
        //buttonGroup.setBackground(null);
        buttonGroup.add(staticColourButton);

        InputMap panelInputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap panelActionMap = this.getActionMap();

        panelInputMap.put(KeyStroke.getKeyStroke("UP"), "upActionMapKey");
        panelInputMap.put(KeyStroke.getKeyStroke('w'), "upActionMapKey");
        panelInputMap.put(KeyStroke.getKeyStroke('W'), "upActionMapKey");
        panelActionMap.put("upActionMapKey", new KeyAction('U'));

        panelInputMap.put(KeyStroke.getKeyStroke("DOWN"), "downActionMapKey");
        panelInputMap.put(KeyStroke.getKeyStroke('s'), "downActionMapKey");
        panelInputMap.put(KeyStroke.getKeyStroke('S'), "downActionMapKey");
        panelActionMap.put("downActionMapKey", new KeyAction('D'));

        panelInputMap.put(KeyStroke.getKeyStroke("LEFT"), "leftActionMapKey");
        panelInputMap.put(KeyStroke.getKeyStroke('a'), "leftActionMapKey");
        panelInputMap.put(KeyStroke.getKeyStroke('A'), "leftActionMapKey");
        panelActionMap.put("leftActionMapKey", new KeyAction('L'));

        panelInputMap.put(KeyStroke.getKeyStroke("RIGHT"), "rightActionMapKey");
        panelInputMap.put(KeyStroke.getKeyStroke('d'), "rightActionMapKey");
        panelInputMap.put(KeyStroke.getKeyStroke('D'), "rightActionMapKey");
        panelActionMap.put("rightActionMapKey", new KeyAction('R'));

        this.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setBackground(Color.WHITE);
        this.setFocusTraversalKeysEnabled(false);
        this.setLayout(null);
        this.setFocusable(true);

        this.add(startLabel);
        startLabel.add(startButton);
        startLabel.add(exitButton);
        startLabel.add(restartButton);
        startLabel.add(rainbowbutton);
        startLabel.add(staticColourButton);
        
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
        Graphics2D g2D = (Graphics2D) g;

        
        // Enable anti-aliasing
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Enable anti-aliasing for text (optional)
        g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        

        if (!gameEnded) startMenu(g2D);
        draw(g2D); //Implement draw image
    }

    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        if (running) {
            //Draw Grid
            /*for (int i = 0; i <= SCREEN_HEIGHT / CELL_SIZE; i++) {
                g.setColor(Color.BLACK);
                g.drawLine(0, i * CELL_SIZE, SCREEN_WIDTH, i * CELL_SIZE); //Draw horizontal line
                g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, SCREEN_HEIGHT); //Draw vertical line
            }*/

           //Draw Background
           g.drawImage(snakeRuinsBackground, 0, 0, null);

            //Draw Apple
            switch(appleChoice) {
                case 1 -> g.drawImage(redApple, appleX, appleY, null);
                case 2 -> g.drawImage(greenApple, appleX, appleY, null);
                case 3 -> g.drawImage(blueApple, appleX, appleY, null);
            }

            //Draw Snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(144, 238, 144));
                    g.fillRect(x[i], y[i], CELL_SIZE, CELL_SIZE);
                }
                else {
                    if (rainbowBody) {
                        g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                        g.fillRect(x[i], y[i], CELL_SIZE, CELL_SIZE);
                    } else {
                        g.setColor(bodyColor);
                        g.fillRect(x[i], y[i], CELL_SIZE, CELL_SIZE);
                    }
                }
            }

            //Draw Score
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, 28));
            FontMetrics fontMetrics = g.getFontMetrics();
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - fontMetrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        } else if (gameEnded){
            gameOver(g);
            startLabel.setVisible(true);
            startLabel.setBorder(null);

            startButton.setVisible(false);
            startButton.setEnabled(false);

            rainbowbutton.setVisible(false);
            rainbowbutton.setEnabled(false);

            staticColourButton.setVisible(false);
            startButton.setEnabled(false);

            exitButton.setForeground(Color.RED);
            exitButton.setContentAreaFilled(true);
            exitButton.setBackground(Color.BLACK);

            restartButton.setEnabled(true);
            restartButton.setVisible(true);
            restartButton.setContentAreaFilled(true);
            restartButton.setBackground(Color.BLACK);
        }
    }

    public void gameOver(Graphics g) {
        //Graphics2D g2D = (Graphics2D) g;
        
        g.drawImage(gameOverBackground, 0, 0, null);

        g.setColor(Color.RED);
        g.setFont(new Font("Monospaced", Font.BOLD, 42));
        FontMetrics fontMetrics = g.getFontMetrics();
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - fontMetrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

        g.setFont(new Font("Monospaced", Font.BOLD, 62));
        FontMetrics fontMetrics1 = g.getFontMetrics();
        g.setColor(Color.BLACK);
        g.fillRect(75, 195, 350, 70);
        g.setColor(Color.RED);
        g.drawRect(75, 195, 350, 70);
        g.drawString("Game Over", (SCREEN_WIDTH - fontMetrics1.stringWidth("Game Over")) / 2, 250);

        //Draw Shortcuts
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        FontMetrics fontMetrics2 = g.getFontMetrics();
        g.drawString("Restart - Alt S : Exit - Alt E", (SCREEN_WIDTH - fontMetrics2.stringWidth("Restart - Alt S : Exit - Alt E")) / 2, 450);
    }

    public void startMenu(Graphics g) {
        g.drawImage(snakeRuinsBackground, 0, 0, null);
        
        
        g.setColor(bodyColor);
        g.setFont(new Font("`Monospaced", Font.BOLD, 60));
        FontMetrics fontMetrics = g.getFontMetrics();
        g.drawString("Welcome", (SCREEN_WIDTH - fontMetrics.stringWidth("Welcome")) / 2, g.getFont().getSize() + 30);

        //Draw Shortcuts
        g.setFont(new Font("Monospaced", Font.BOLD, 12));
        FontMetrics fontMetrics1 = g.getFontMetrics();
        g.drawString("Start - Alt S : Exit - Alt E : Rainbow - Alt R : Static - Alt R", (SCREEN_WIDTH - fontMetrics1.stringWidth("Start - Alt S : Exit - Alt E : Rainbow - Alt R : Static - Alt R")) / 2, 450);

        //g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        //g.fillOval(150, 260, 20, 20);
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
                endGame();
                return;
            }
        }

        //Check for border collisions
        if(x[0] < 0 || x[0] > SCREEN_WIDTH  ||  y[0] < 0 || y[0] > SCREEN_HEIGHT) {
            endGame();
        }
    }

    public void endGame() {
        gameEnded = true;
        running = false; //set state of game to not running
        timer.stop(); //stop timer upon collison
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
        if (ae.getSource() == startButton) {
            startGame();
            startLabel.setVisible(false);

        } else if (ae.getSource() == exitButton) {
            gameFrame.dispose();

        } else if (ae.getSource() == restartButton) {
            gameFrame.dispose();
            new GameFrame();

        } else if (ae.getSource() == staticColourButton) {
            bodyColor = JColorChooser.showDialog(null, "Snake Body Color", null);
            staticColourButton.setForeground(bodyColor);
            rainbowbutton.setForeground(bodyColor);
            startButton.setForeground(bodyColor);
            exitButton.setForeground(bodyColor);

        } else if (ae.getSource() == rainbowbutton) {
            if (rainbowbutton.isSelected()) {
                rainbowBody = true;
            }
        }
        
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        this.repaint();
    }
}