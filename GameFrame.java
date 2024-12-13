import javax.swing.JFrame;

public class GameFrame extends JFrame {
    public GameFrame() {
        this.setSize(500, 500);
        this.add(new GamePanel(this));
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}