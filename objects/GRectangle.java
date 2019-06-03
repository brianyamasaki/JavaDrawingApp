package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.event.MouseEvent;

public class GRectangle extends GObject {

	private Rectangle rect;
	private Color fillColor;
	private Color strokeColor;
	private int strokeRadius;
	
	public GRectangle(int x, int y, int width, int height) {
		super();
		this.rect = new Rectangle(x, y, width, height);
		this.fillColor = Color.yellow;
		this.strokeColor = Color.darkGray;
		this.strokeRadius = 3;
		this.calcBoundingBox();
	}

	@Override
	protected void calcBoundingBox() {
		this.boundingRect = new Rectangle(this.rect);
		this.calcSelectionList();
	}

	@Override
	protected void calcSelectionList() {
		this.selectionList = new ArrayList<Rectangle>();
		// top left
		this.selectionList.add(new Rectangle(
								this.boundingRect.x - dxyHalf, 
								this.boundingRect.y - dxyHalf, 
								this.dxySelectionSize, 
								this.dxySelectionSize)
		);
		// top right
		this.selectionList.add(new Rectangle(
								this.boundingRect.x + this.boundingRect.width - dxyHalf, 
								this.boundingRect.y - dxyHalf, 
								this.dxySelectionSize, 
								this.dxySelectionSize)
		);
		// bottom right
		this.selectionList.add(new Rectangle(
								this.boundingRect.x + this.boundingRect.width - dxyHalf, 
								this.boundingRect.y + this.boundingRect.height - dxyHalf, 
								this.dxySelectionSize, 
								this.dxySelectionSize)
		);
			// bottom left
		this.selectionList.add(new Rectangle(
								this.boundingRect.x - dxyHalf, 
								this.boundingRect.y + this.boundingRect.height - dxyHalf, 
								this.dxySelectionSize, 
								this.dxySelectionSize)
		);
	}
	
	@Override
	public void draw(Graphics2D g2) {
		int width = this.rect.width;
		int height = this.rect.height;
		g2.setPaint(this.fillColor);
		g2.fillRect(this.rect.x, this.rect.y, width, height);
		g2.setPaint(this.strokeColor);
		g2.setStroke(new BasicStroke(this.strokeRadius));
		g2.drawRect(this.rect.x, this.rect.y, width, height);
		if (this.isSelected) {
			drawSelection(g2);
		}
	}

	@Override
	public void mouseClick(MouseEvent e) {
		if (rect.contains(e.getPoint())) {
			this.setSelected(!this.isSelected);	
			// System.out.println("Rectangle selection changed to " + this.isSelected);
		}
	}
	
	public String toString() {
		return String.format("<MyRect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" />", rect.x, rect.y, rect.width, rect.height);
	}
}