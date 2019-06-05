package toolbar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;

public class Toolbar {
  private final Color fillColor = Color.DARK_GRAY;
  private final Color strokeColor = Color.black;
  private final int strokeRadius = 4;
  private Rectangle toolbarRect;

  public Toolbar(int x, int y, int width, int height) {
    this.toolbarRect = new Rectangle(x, y, width, height);
  }

  public void draw(Graphics2D g2) {
		g2.setPaint(this.fillColor);
		g2.fillRect(toolbarRect.x, toolbarRect.y, toolbarRect.width, toolbarRect.height);
		g2.setPaint(this.strokeColor);
		g2.setStroke(new BasicStroke(this.strokeRadius));
		g2.drawRect(toolbarRect.x, toolbarRect.y, toolbarRect.width, toolbarRect.height);    
  }

  public Rectangle mouseClick(MouseEvent e) {
    return new Rectangle();
  }
}