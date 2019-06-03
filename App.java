
import java.awt.EventQueue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;

public class App extends JFrame {
    private static final long serialVersionUID = 22;

    private static final String appName = "AppStarter";
    private static final int initialWidth = 800;
    private static final int initialHeight = 600;
    private static int appWidth;
    private static int appHeight;

    public App() {
        this.addComponentListener(new CAdapter());
        App.appWidth = initialWidth;
        App.appHeight = initialHeight;
        this.initUI();
    }

    private void initUI() {

        this.add(new Board());

        // set the window title
        this.setTitle(appName);
        // set the size of the window
        this.setSize(App.appWidth, App.appHeight);

        // this centers the window on the screen
        this.setLocationRelativeTo(null);
        // allows user to resize the window
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void resized(int width, int height) {
        App.appWidth = width;
        App.appHeight = height;
    }

    private class CAdapter extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            // expecting paramString to be of the form "COMPONENT_RESIZED (350,160 800x600)""
            String[] tokens = e.paramString().split(" ");
            // now expecting tokens[2] to be "800x600)"
            String[] dimTokens = tokens[2].split("x");
            dimTokens[1] = dimTokens[1].substring(0, dimTokens[1].length()-1);
            System.out.println("Component resized " + dimTokens[0] + "x" + dimTokens[1]);
            App.resized(Integer.parseInt(dimTokens[1], 10), Integer.parseInt(dimTokens[1], 10));
        }
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            App ex = new App();
            ex.setVisible(true);
        });
    }
}