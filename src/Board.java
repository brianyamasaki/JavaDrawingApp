package src;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
// import javax.swing.Timer;
import src.objects.*;

public class Board extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1;
    public static AppState appState;
    public static Board self;

    // private Timer timer;
    private ArrayList<GObject> objects;
    // private final int DELAY = 10;
    private final int gridInterval = 100;

    public Board(AppState appState) {
        super(new BorderLayout());

        Board.appState = appState;
        Board.self = this;
        appState.setDrawingPanel(this);
        this.initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        addMouseListener(new MAdapter());
        addMouseMotionListener(new MMAdapter());
        setBackground(Color.LIGHT_GRAY);
	    setFocusable(true);
        
        objects = new ArrayList<GObject>();

        objects.add(new GImage(100, 100, 400, 400));

        // timer = new Timer(DELAY, this);
        // timer.start();
    }

    public void menuItemCommand(String command) {
        switch(command) {
            case AppMenuBar.createRectangle:
                objects.add(new GRectangle(150, 150, 150, 150));
                break;
            case AppMenuBar.deleteObject:
                this.deleteSelected();
                break;
            case AppMenuBar.showGridAction:
                appState.setGrid(!appState.getGrid());
                break;
        }
        repaint();

    }

    private void deleteSelected() {
        this.objects.removeIf((obj) -> obj.isSelected());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (appState.getGrid()) {
            paintBackground((Graphics2D)g);
        }
        doDrawing((Graphics2D)g);
        
        Toolkit.getDefaultToolkit().sync();
    }
    
    private void paintBackground(Graphics2D g2) {
        int x = gridInterval;
        int y = gridInterval;
        float[] dashArray = {15, 20};
        BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1, dashArray, 1);
        g2.setStroke(stroke);
        while (x < App.appWidth) {
            g2.drawLine(x, 0, x, App.appHeight);
            x += gridInterval;
        }
        while (y < App.appHeight) {
            g2.drawLine(0, y, App.appWidth, y);
            y += gridInterval;
        }
    }

    private void doDrawing(Graphics2D g2) {
        
        // draw objects
        for (GObject obj : objects) {
            obj.draw(g2);
        }

        // draw selection dots
        ArrayList<GObject> selectedObjects = appState.getSelectedObjects();
        for (GObject obj : selectedObjects) {
            obj.drawSelection(g2);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        step();
    }

    private void step() {
        
        // spaceShip.move();
        
        // repaint(spaceShip.getX()-1, spaceShip.getY()-1, 
        //         spaceShip.getWidth()+2, spaceShip.getHeight()+2);     
    }

    public static GObjReturn pointInSelection(MouseEvent e) {
        GObjReturn objReturn = new GObjReturn();
        Point pt = e.getPoint();
        ArrayList<GObject> selectedObjects = appState.getSelectedObjects();
        for (GObject obj : selectedObjects) {
            if (obj.pointInObject(pt)) {
                objReturn.setClickMode(ClickMode.onObject);
                break;
            }
            if (obj.pointInSelection(pt) >= 0) {
                objReturn.setClickMode(ClickMode.onSelection);
                break;
            }
        }
        return objReturn;
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            // spaceShip.keyReleased(e);
            System.out.println("key released " + KeyEvent.getKeyText(e.getKeyCode()));
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // spaceShip.keyPressed(e);
            System.out.println("key pressed " + KeyEvent.getKeyText(e.getKeyCode()));
        }
    }

    private class MAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            GObjReturn objReturn;
            Rectangle rectUpdate = new Rectangle();
            Rectangle rectT;
            boolean isShiftDown = e.isShiftDown();
            objReturn = Board.pointInSelection(e);
            if (objReturn.getClickMode() != ClickMode.onNothing && !isShiftDown) {
                // simply clicked on already selected objects, do nothing and return
                return;
            }
            GObject obj = null;
            // iterate from front object to back object, 
            // therefore loop backwards in the list
            for (int i = objects.size() - 1;  i >= 0; i--) {
                objReturn = objects.get(i).mouseClick(e);
                rectT = objReturn.getUpdateRect();
                if (!rectT.isEmpty()) {
                    rectUpdate = rectUpdate.union(rectT);
                }
                if (objReturn.getClickMode() != ClickMode.onNothing && obj == null) {
                    obj = objects.get(i);
                    if (!isShiftDown) {
                        appState.clearAllSelectedObjects();
                    }
                    appState.addSelectedObject(obj);
                    obj.setSelected(true);
                }
            }
            if (obj == null) {
                appState.clearAllSelectedObjects();
            }
            if (!rectUpdate.isEmpty())
                repaint(rectUpdate);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            GObjReturn objReturn;
            Rectangle rectUpdate = new Rectangle();
            for (GObject obj : objects) {
                objReturn = obj.mouseReleased(e);
                rectUpdate = objReturn.getUpdateRect().union(rectUpdate);
            }
            if (!rectUpdate.isEmpty())
                repaint();
      }

    }

    private class MMAdapter extends MouseAdapter {

        @Override
        public void mouseDragged(MouseEvent e) {
            GObjReturn objReturn = new GObjReturn();
            Rectangle rectUpdate = new Rectangle();
            for (GObject obj : objects) {
                objReturn = obj.mouseDragged(e);
                rectUpdate = rectUpdate.union(objReturn.getUpdateRect());
            }
            if (!rectUpdate.isEmpty()) {
                // System.out.println("drag update rectangle is " + rectUpdate);
                repaint(rectUpdate);
            }
        }
    }
}