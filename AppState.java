import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import objects.GObject;
import actions.ShowGridAction;

enum AppMode {
  Normal,
  DragRectangle,
  SelectPolygonPoint
};

public class AppState {
  private AppMode mode;
  private ArrayList<GObject> selectedObjects;
  private ShowGridAction showGridAction;

  public AppState(ActionListener listener) {
    this.mode = AppMode.Normal;
    this.selectedObjects = new ArrayList<GObject>();
    this.showGridAction = new ShowGridAction(listener, true);
  }

  // Mode methods
  public AppMode getMode() {
    return this.mode;
  }

  public void setMode(AppMode mode) {
    this.mode = mode;
  }

  // Object Selection methods
  public ArrayList<GObject> getSelectedObjects() {
    return this.selectedObjects;
  }

  public void addSelectedObject(GObject object) {
    this.selectedObjects.add(object);
  }

  public void removeSelectedObject(GObject object) {
    this.selectedObjects.remove(object);
  }

  public void clearAllSelectedObjects() {
    this.selectedObjects.clear();
  }

  // drawGrid methods
  public AbstractAction getGridAction() {
    return this.showGridAction;
  }

  public void setGrid(boolean newValue) {
    this.showGridAction.setBooleanValue(newValue);
  }

  public boolean getGrid() {
    return showGridAction.getBooleanValue();
  }

}