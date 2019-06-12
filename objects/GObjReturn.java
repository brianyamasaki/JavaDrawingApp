package objects;

import java.awt.Rectangle;

public class GObjReturn {
  private Rectangle updateRect;
  private ClickMode clickMode;
  
  public GObjReturn() {
    this.clickMode = ClickMode.onNothing;
    this.updateRect = new Rectangle();
  }

  public void setUpdateRect(Rectangle rect) {
    this.updateRect = rect;
  }

  public Rectangle getUpdateRect() {
    return this.updateRect;
  }

  public void setClickMode(ClickMode clickMode) {
    this.clickMode = clickMode;
  }

  public ClickMode getClickMode() {
    return clickMode;
  }
}