package src.objects;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.EnumSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import src.Board;
import src.pals.ContextPaletteGroup;

import java.awt.Color;

enum DragMode {
	TopLeft,
	TopRight,
	BottomRight,
	BottomLeft,
	LeftMiddle,
	TopMiddle,
	RightMiddle,
	BottomMiddle,
	NotDragging,
	Object
};

public class GObject {
	protected Rectangle boundingRect;
	protected final int dxySelectionSize = 12;
	protected final int dxyHalf = dxySelectionSize / 2;
	protected ArrayList<Rectangle> selectionList;
	protected DragMode[] mpSelectionHandle = {
		DragMode.TopLeft, 
		DragMode.TopRight,
		DragMode.BottomRight,
		DragMode.BottomLeft,
		DragMode.LeftMiddle,
		DragMode.TopMiddle,
		DragMode.RightMiddle,
		DragMode.BottomMiddle
	};
	protected boolean isSelected = false;
	protected DragMode dragMode = DragMode.NotDragging;
	protected Rectangle dragBoundingRect;
	protected Point dragStartPoint;
	
	public GObject() {
	}

	protected void calcBoundingBox() {
		this.boundingRect = new Rectangle();
		this.dragStartPoint = new Point();
		this.calcSelectionList();
	}

	public Rectangle getBoundingRect() {
		return this.boundingRect;
	}

	/**
	 * creates the list of selection markers based on the bounding 
	 * rectangle of the object
	 *
	 */
	protected void calcSelectionList() {
		this.selectionList = new ArrayList<Rectangle>();
		Rectangle boundingRect;

		if (this.dragMode == DragMode.NotDragging) {
			boundingRect = this.boundingRect;
		} else {
			boundingRect = this.dragBoundingRect;
		}
		
		if (boundingRect.height == 0 && boundingRect.width == 0) {
			return;
		}
		// top left
		this.selectionList.add(new Rectangle(
								boundingRect.x - dxyHalf, 
								boundingRect.y - dxyHalf, 
								this.dxySelectionSize, 
								this.dxySelectionSize)
		);
		// top right
		this.selectionList.add(new Rectangle(
								boundingRect.x + boundingRect.width - dxyHalf, 
								boundingRect.y - dxyHalf, 
								this.dxySelectionSize, 
								this.dxySelectionSize)
		);
		// bottom right
		this.selectionList.add(new Rectangle(
								boundingRect.x + boundingRect.width - dxyHalf, 
								boundingRect.y + boundingRect.height - dxyHalf, 
								this.dxySelectionSize, 
								this.dxySelectionSize)
		);
			// bottom left
		this.selectionList.add(new Rectangle(
								boundingRect.x - dxyHalf, 
								boundingRect.y + boundingRect.height - dxyHalf, 
								this.dxySelectionSize, 
								this.dxySelectionSize)
		);
		// left middle
		this.selectionList.add(new Rectangle(
								boundingRect.x - dxyHalf, 
								boundingRect.y + (boundingRect.height / 2) - dxyHalf, 
								this.dxySelectionSize, 
								this.dxySelectionSize)
		);
		// top middle
		this.selectionList.add(new Rectangle(
								boundingRect.x + (boundingRect.width / 2) - dxyHalf, 
								boundingRect.y - dxyHalf, 
								this.dxySelectionSize, 
								this.dxySelectionSize)
		);
		// right middle
		this.selectionList.add(new Rectangle(
								boundingRect.x + boundingRect.width - dxyHalf, 
								boundingRect.y + (boundingRect.height / 2) - dxyHalf, 
								this.dxySelectionSize, 
								this.dxySelectionSize)
		);
		// bottom middle
		this.selectionList.add(new Rectangle(
								boundingRect.x + (boundingRect.width / 2) - dxyHalf, 
								boundingRect.y + boundingRect.height - dxyHalf, 
								this.dxySelectionSize, 
								this.dxySelectionSize)
		);
	}
	/**
	 * Returns an index if pt is within any of the selection markers 
	 * @param  pt  a point to test against the selection markers
	 * @return  index of selection marker OR -1 if pt not within any selection markers
	 */
	public int pointInSelection(Point pt) {
		int iMax = this.selectionList.size();
		for (int i = 0; i < iMax; i++) {
			if (this.selectionList.get(i).contains(pt)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns how mouse location relates to this object
	 * @param  MouseEvent as passed by MouseListener
	 * @return  DragMode on where mouse location relates to object
	 */
	protected DragMode mousePointSelection(MouseEvent e) {
		Point pt = e.getPoint();
		int i = this.pointInSelection(pt);
		if (i < 0) {
			if (this.pointInObject(pt)) {
				return DragMode.Object;
			} else {
				this.setSelected(false);
				return DragMode.NotDragging;
			}
		} else
			return this.mpSelectionHandle[i];
	}

	public void setSelected(boolean toSelect) {
		this.isSelected = toSelect;
		this.dragMode = DragMode.NotDragging;
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	public void draw(Graphics2D g2, Board board) {
		
	}

	public void drawSelection(Graphics2D g2) {
		g2.setXORMode(Color.white);
		for(Rectangle rect : this.selectionList) {
			g2.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
		g2.setPaintMode();
	}

	protected void resizeToRect(Rectangle rect) {
	}

	public boolean pointInObject(Point pt) {
		return false;
	}

	protected Rectangle selectedBoundingBox() {
		return new Rectangle(
			this.boundingRect.x - dxyHalf, 
			this.boundingRect.y - dxyHalf, 
			this.boundingRect.width + dxySelectionSize, 
			this.boundingRect.height + dxySelectionSize);
	}

	private Rectangle dragRectangle(int xDiff, int yDiff, boolean isShiftDown) {
		int left = this.boundingRect.x;
		int top = this.boundingRect.y;
		int width = this.boundingRect.width;
		int height = this.boundingRect.height;
		if (isShiftDown) {
			int dxy = Math.min(Math.abs(xDiff), Math.abs(yDiff));
			System.out.println("input (" + xDiff + ", " + yDiff + ") dxy=" + dxy);
			xDiff = xDiff > 0 ? Math.min(xDiff, dxy) : Math.max(xDiff, -dxy);
			yDiff = yDiff > 0 ? Math.min(yDiff, dxy) : Math.max(yDiff, -dxy);
			System.out.println("output (" + xDiff + ", " + yDiff + ")");
		}
		switch (this.dragMode) {
			case TopLeft:
				left += xDiff;
				top += yDiff;
				width -= xDiff;
				height -= yDiff;
				break;
			case TopMiddle:
				top += yDiff;
				height -= yDiff;
				break;
			case TopRight:
				top += yDiff;
				width += xDiff;
				height -= yDiff;
				break;
			case RightMiddle:
				width += xDiff;
				break;
			case BottomRight:
				width += xDiff;
				height += yDiff;
				break;
			case BottomMiddle:
				height += yDiff;
				break;
			case BottomLeft:
				height += yDiff;
				left += xDiff;
				width -= xDiff;
				break;
			case LeftMiddle:
				left += xDiff;
				width -= xDiff;
				break;
			case Object:
				top += yDiff;
				left += xDiff;
			default:
				break;
		}
		return new Rectangle(left, top, width, height);
}

	public void contextCommand(String field, String strValue) {
		Rectangle rect = new Rectangle(this.boundingRect);
		int val = Integer.parseInt(strValue);
		switch(field) {
			case "X":
				rect.x = val;
				break;
			case "Y": 
				rect.y = val;
				break;
			case "Width":
				rect.width = val;
				break;
			case "Height":
				rect.height = val;
				break;
			default:
				break;
		}
		this.resizeToRect(rect);
}

	/* Mouse dragging starts with a mouseDragged event (not a mouseDown), 
	*  followed by more mouseDragged events and finishes with a 
	*  mouseReleased event.
	*/
	public GObjReturn mouseDragged(MouseEvent e) {
		GObjReturn objReturn = new GObjReturn();
		if (!this.isSelected) {
			return objReturn;
		}
		if (this.dragMode == DragMode.NotDragging){
			// this is the first mouseDragged call
			this.dragMode = this.mousePointSelection(e);
			// store the mouse starting location.
			this.dragStartPoint = e.getPoint();
			this.dragBoundingRect = new Rectangle(this.boundingRect);
		} else {
			// this is a subsequent mouseDragged call
			this.dragBoundingRect = this.dragRectangle(
				e.getX() - this.dragStartPoint.x, 
				e.getY() - this.dragStartPoint.y,
				e.isShiftDown()
				);
			this.calcSelectionList();
			Rectangle rectUnion = this.boundingRect.union(this.dragBoundingRect);
			rectUnion.grow(dxySelectionSize, dxySelectionSize);
			objReturn.setUpdateRect(rectUnion);
			return objReturn;
		}
		return objReturn;
	}

	public GObjReturn mouseReleased(MouseEvent e) {
		GObjReturn objReturn = new GObjReturn();
		if (this.isSelected && this.dragMode != DragMode.NotDragging) {
			this.dragBoundingRect = this.dragRectangle(
				e.getX() - this.dragStartPoint.x, 
				e.getY() - this.dragStartPoint.y,
				e.isShiftDown()
				);
			this.dragMode = DragMode.NotDragging;
			// System.out.println("mouseReleased: dragBoundingRect " + this.dragBoundingRect);
			this.resizeToRect(this.dragBoundingRect);
			Rectangle rectUnion = this.boundingRect.union(this.dragBoundingRect);
			rectUnion.grow(dxySelectionSize, dxySelectionSize);
			objReturn.setUpdateRect(rectUnion);
			return objReturn;
		} else {
			this.setSelected(false);
		}
		return objReturn;
	}

	public GObjReturn mouseClick(MouseEvent e) {
		ClickMode cm;
		Point pt = e.getPoint();
		GObjReturn objReturn = new GObjReturn();
		int i = this.pointInSelection(pt);
		if (i >= 0) {
			cm = ClickMode.onSelection;
		} else if (this.pointInObject(pt)) {
			cm = ClickMode.onObject;
		} else {
			cm = ClickMode.onNothing;
		}
		objReturn.setClickMode(cm);
		objReturn.setUpdateRect(this.selectedBoundingBox());
		return objReturn;
	}

	protected JSpinner addLabeledSpinner(JPanel panel, String labelString, int value, int min, int max) {
		JLabel label = new JLabel(labelString);
		panel.add(label);

		JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, 1));
		label.setLabelFor(spinner);
		panel.add(spinner);

		return spinner;
	}

	public EnumSet<ContextPaletteGroup> getContextProperties() {
		return EnumSet.of(ContextPaletteGroup.BoundingBox);
	}

	public String toString() {
		return "<Graphic_Object />";
	}

}
