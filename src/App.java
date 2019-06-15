package src;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

public class App extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1;

    private static final String appName = "Draw Objects";
    private static final int initialWidth = 800;
    private static final int initialHeight = 600;
    public static int appWidth;
    public static int appHeight;
    private AppContent appContent;
    private AppState appState;
    
    public App() {
        // Returns the size when user resizes the Window
        this.addComponentListener(new CAdapter());
        App.appWidth = initialWidth;
        App.appHeight = initialHeight;
        this.initUI();
    }

    private void initUI() {

        this.appState = new AppState(this);
        this.appContent = new AppContent(this, this.appState);
        this.add(this.appContent);

        // set the window title
        this.setTitle(appName);
        // set the size of the window
        this.setSize(App.appWidth, App.appHeight);

        // this centers the window on the screen
        this.setLocationRelativeTo(null);
        // allows user to resize the window
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add a Menubar with menus
        this.setJMenuBar(new AppMenuBar(this, this.appState));
    }

    private static void resized(int width, int height) {
        App.appWidth = width;
        App.appHeight = height;
    }
    
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        String[] actionParts = action.split(AppMenuBar.actionFormatSplitRegex);
        if (actionParts[0].matches(AppMenuBar.app)) {
            // signifies this is an App command
            switch (actionParts[1]) {
                case AppMenuBar.exitItem:
                    System.exit(0);
                    break;
            }
        } else {
            this.appContent.menuItemCommand(actionParts[0], actionParts[1]);
        }
    }

    private class CAdapter extends ComponentAdapter {
        // catch when the user resizes the window
        @Override
        public void componentResized(ComponentEvent e) {
            // expecting paramString to be of the form "COMPONENT_RESIZED (350,160 800x600)""
            String[] tokens = e.paramString().split(" ");
            // now expecting tokens[2] to be "800x600)"
            String[] dimTokens = tokens[2].split("x");
            dimTokens[1] = dimTokens[1].substring(0, dimTokens[1].length()-1);
            // System.out.println("Component resized " + dimTokens[0] + "x" + dimTokens[1]);
            App.resized(Integer.parseInt(dimTokens[0], 10), Integer.parseInt(dimTokens[1], 10));
        }
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        EventQueue.invokeLater(() -> {
            App ex = new App();
            ex.setVisible(true);
        });
    }
}