package objects;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Color;

public class GObject {
	protected boolean isSelected;
	protected Rectangle boundingRect;
	protected final int dxySelectionSize = 10;
	protected final int dxyHalf = dxySelectionSize / 2;
	protected ArrayList<Rectangle> selectionList;
	
	public GObject() {
		this.isSelected = false;
	}

	protected void calcBoundingBox() {
		this.boundingRect = new Rectangle(0,0,0,0);
		this.calcSelectionList();
	}

	protected void calcSelectionList() {
		this.selectionList = new ArrayList<Rectangle>();
	}

	protected void setSelected(boolean toSelect) {
		this.isSelected = toSelect;
	}

	public void draw(Graphics2D g2) {
		
	}

	protected void drawSelection(Graphics2D g2) {
		g2.setXORMode(Color.white);
		for(Rectangle rect : this.selectionList) {
			g2.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
		g2.setPaintMode();
	}
	
	public void mouseClick(MouseEvent e) {
		
	}

	public String toString() {
		return "<Graphic Object />";
	}

}
