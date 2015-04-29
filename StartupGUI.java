package fishing;

import javax.swing.*;

/**
 * Created by Jayden on 4/29/2015.
 */
public class StartupGUI extends JFrame {
    public StartupGUI () {
        JFrame frame = new JFrame("Overdone Fishing!");
        frame.setSize(100, 50);
        frame.setLocationRelativeTo(null);
        // Centers the frame frame.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
