
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

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    private static final long serialVersionUID = 23;

    private Timer timer;
    // private SpaceShip spaceShip;
    private final int DELAY = 10;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        addMouseListener(new MAdapter());
        setBackground(Color.LIGHT_GRAY);
	      setFocusable(true);

        // spaceShip = new SpaceShip();

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

        // g2d.drawImage(spaceShip.getImage(), spaceShip.getX(), 
        //     spaceShip.getY(), this);
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
          System.out.println("mouse clicked at " + e.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
          System.out.println("mouse released at " + e.getPoint());
        }

        @Override
        public void mouseEntered(MouseEvent e) {
          System.out.println("mouse entered Board");
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
          System.out.println("mouse exited Board");
        }
    }
}