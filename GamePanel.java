import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
    final int SCREEN_WIDTH = 500;
    final int SCREEN_HEIGHT = 500;

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