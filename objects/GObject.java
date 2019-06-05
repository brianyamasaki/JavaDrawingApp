package objects;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.util.ArrayList;
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

	protected DragMode mousePointSelection(MouseEvent e) {
		int i;
		int max = this.selectionList.size();
		Rectangle rect;
		assert(this.isSelected == true);
		for (i = 0; i < max; i++) {
			rect = this.selectionList.get(i);
			if (rect.contains(e.getPoint())) {
				break;
			}
		}
		if (i >= max)
			return DragMode.Object;
		else
			return this.mpSelectionHandle[i];
	}

	protected void setSelected(boolean toSelect) {
		this.isSelected = toSelect;
		this.dragMode = DragMode.NotDragging;
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

	protected void resizeToRect(Rectangle rect) {
	}

	protected Rectangle selectedBoundingBox() {
		return new Rectangle(
			this.boundingRect.x - dxyHalf, 
			this.boundingRect.y - dxyHalf, 
			this.boundingRect.width + dxySelectionSize, 
			this.boundingRect.height + dxySelectionSize);
	}

	// public Rectangle mousePressed(MouseEvent e) {
	// 	if (this.isSelected) {
	// 		this.dragStartPoint = e.getPoint();
	// 		this.dragMode = this.mousePointSelection(e);
	// 		if (this.dragMode != DragMode.NotDragging) {
	// 			return this.selectedBoundingBox();
	// 		} else if (this.boundingRect.contains(this.dragStartPoint)){
	// 			this.dragMode = DragMode.Object;
	// 			return this.selectedBoundingBox();
	// 		}
	// 	}
	// 	return new Rectangle();
	// }
	private Rectangle dragRectangle(int xDiff, int yDiff) {
		int left = this.boundingRect.x;
		int top = this.boundingRect.y;
		int width = this.boundingRect.width;
		int height = this.boundingRect.height;
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

	public Rectangle mouseDragged(MouseEvent e) {
		if (!this.isSelected) {
			return new Rectangle();
		} 
		if (this.dragMode == DragMode.NotDragging){
			// this is the first mouseDragged call
			this.dragMode = this.mousePointSelection(e);
			// store the mouse starting location.
			this.dragStartPoint = e.getPoint();
			this.dragBoundingRect = new Rectangle(this.boundingRect);
			// System.out.println("mouseDragged first call" + this.boundingRect);
		} else {
			// this is a subsequent mouseDragged call
			this.dragBoundingRect = this.dragRectangle(
				e.getX() - this.dragStartPoint.x, 
				e.getY() - this.dragStartPoint.y
				);
			// System.out.println("dragBoundingRect = " + this.dragBoundingRect);
			this.calcSelectionList();
			Rectangle rectUnion = this.boundingRect.union(this.dragBoundingRect);
			rectUnion.grow(dxySelectionSize, dxySelectionSize);
			return rectUnion;
		}
		return new Rectangle();
	}

	public Rectangle mouseReleased(MouseEvent e) {
		if (this.isSelected && this.dragMode != DragMode.NotDragging) {
			this.dragBoundingRect = this.dragRectangle(
				e.getX() - this.dragStartPoint.x, 
				e.getY() - this.dragStartPoint.y
				);
			this.dragMode = DragMode.NotDragging;
			// System.out.println("mouseReleased: dragBoundingRect " + this.dragBoundingRect);
			this.resizeToRect(this.dragBoundingRect);
			Rectangle rectUnion = this.boundingRect.union(this.dragBoundingRect);
			rectUnion.grow(dxySelectionSize, dxySelectionSize);
			return rectUnion;
		}
		return new Rectangle();
	}

	public Rectangle mouseClick(MouseEvent e) {
		return this.selectedBoundingBox();
	}

	public String toString() {
		return "<Graphic Object />";
	}

}
