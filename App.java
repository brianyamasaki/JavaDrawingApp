
import java.awt.EventQueue;
import javax.swing.JFrame;

public class App extends JFrame {
    private static final long serialVersionUID = 22;

    private static final String appName = "AppStarter";
    private static final int width = 800;
    private static final int height = 600;

    public App() {
        
        this.initUI();
    }
    
    private void initUI() {

        this.add(new Board());

        this.setTitle(appName);
        this.setSize(width, height);
        
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            App ex = new App();
            ex.setVisible(true);
        });
    }
}