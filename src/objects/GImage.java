package src.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import src.Board;

public class GImage extends GObject {
  protected Rectangle rect;
  protected String filename;
  protected Image image;
  protected BufferedImage bufferedImage;
  // protected ImageIcon imageIcon;

  public GImage(int x, int y, int width, int height) {
    super();
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    this.rect = new Rectangle(x, y, width, height);
    this.filename = "assets\\400x400.png";
    this.image = toolkit.getImage(filename);
    try {
      this.bufferedImage = ImageIO.read(new File(filename));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // this.imageIcon = new ImageIcon(filename);
    this.calcBoundingBox();
  }
   
	@Override
	public boolean pointInObject(Point pt) {
		return rect.contains(pt);
	}

	@Override
	protected void calcBoundingBox() {
		this.boundingRect = new Rectangle(this.rect);
		this.calcSelectionList();
	}

  @Override
  public void draw(Graphics2D g2) {
		Rectangle drawRect;
		if (this.dragMode == DragMode.NotDragging) {
			drawRect = this.rect;
		} else {
			drawRect = this.dragBoundingRect;
		}
    g2.drawImage(this.image, drawRect.x, drawRect.y, drawRect.width, drawRect.height, Board.self);
    // g2.drawRect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
  }

	@Override
	protected void resizeToRect(Rectangle rect) {
		this.rect = new Rectangle(rect);
		this.calcBoundingBox();
		// System.out.println("resizeToRect sets to " + this.rect + " and " + this.boundingRect);
	}

}