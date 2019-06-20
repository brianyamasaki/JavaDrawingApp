package src;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import src.objects.GObject;
import src.pals.ContextPalette;
import src.actions.ShowGridAction;

enum AppMode {
  Normal,
  DragRectangle,
  SelectPolygonPoint
};

public class AppState {
  private AppMode mode;
  private ArrayList<GObject> selectedObjects;
  private ShowGridAction showGridAction;
  private ContextPalette contextPalette;
  private static AppState self;

  public AppState(ActionListener listener) {
    this.mode = AppMode.Normal;
    this.selectedObjects = new ArrayList<GObject>();
    this.showGridAction = new ShowGridAction(listener, true);
    AppState.self = this;
  }

  public static AppState getRef() {
    return AppState.self;
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
    object.setSelected(false);
    this.selectedObjects.remove(object);
  }

  public void clearAllSelectedObjects() {
    for (GObject obj : this.selectedObjects) {
      obj.setSelected(false);
    }
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

  // contextPalette

  public void setContextPalette(ContextPalette contextPalette) {
    this.contextPalette = contextPalette;
  }

  public ContextPalette getContextPalette() {
    return this.contextPalette;
  }

}