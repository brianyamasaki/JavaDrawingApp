package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class GRectangle extends GObject {

	private Rectangle rect;
	private Color fillColor;
	private Color strokeColor;
	private int strokeRadius;
	
	public GRectangle(int x, int y, int width, int height) {
		super();
		this.rect = new Rectangle(x, y, width, height);
		this.fillColor = Color.white;
		this.strokeColor = Color.darkGray;
		this.strokeRadius = 3;
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
		g2.setPaint(this.fillColor);
		g2.fillRect(drawRect.x, drawRect.y, drawRect.width, drawRect.height);
		g2.setPaint(this.strokeColor);
		g2.setStroke(new BasicStroke(this.strokeRadius));
		g2.drawRect(drawRect.x, drawRect.y, drawRect.width, drawRect.height);
	}

	@Override
	protected void resizeToRect(Rectangle rect) {
		this.rect = new Rectangle(rect);
		this.calcBoundingBox();
		// System.out.println("resizeToRect sets to " + this.rect + " and " + this.boundingRect);
	}

	public String toString() {
		return String.format("<Rectangle x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" />", rect.x, rect.y, rect.width, rect.height);
	}
}