
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;
import objects.*;

public class Board extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1;

    private Timer timer;
    private ArrayList<GObject> objects;
    private final int DELAY = 10;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        addMouseListener(new MAdapter());
        setBackground(Color.LIGHT_GRAY);
	    setFocusable(true);

        objects = new ArrayList<GObject>();

        objects.add(new GRectangle(50, 50, 100, 100));

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
        
        Toolkit.getDefaultToolkit().sync();
    }
    
    private void doDrawing(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;

        for (GObject obj : objects) {
            obj.draw(g2d);
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
            for (GObject obj : objects) {
                obj.mouseClick(e);
            }
            repaint();
    
        //   System.out.println("mouse clicked at " + e.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        //   System.out.println("mouse released at " + e.getPoint());
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        //   System.out.println("mouse entered Board");
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
        //   System.out.println("mouse exited Board");
        }
    }
}