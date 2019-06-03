package objects;

import java.awt.Rectangle;

public class MyRect  {
  private Rectangle rect;

  public MyRect(int x1, int y1, int x2, int y2) {
    this.rect = new Rectangle(Math.min(x1, x2), 
      Math.min(y1, y2), 
      Math.abs(x1 - x2), 
      Math.abs(y1 - y2));
  }

  public int x() {
    return rect.x;
  }

  public int y() {
    return rect.y;
  }

  public int width() {
    return rect.width;
  }

  public int height() {
    return rect.height;
  }

}