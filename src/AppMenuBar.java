package src;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

// import actions.ShowGridAction;

public class AppMenuBar extends JMenuBar {

  private static final long serialVersionUID = 1L;
  // menu strings
  public static final String app = "App";
  public static final String fileMenuTitle = "File";
  public static final String exitItem = "Exit";
  public static final String objectMenuTitle = "Object";
  public static final String createRectangle = "Create Rectangle";
  public static final String deleteObject = "Delete";
  public static final String optionMenuTitle = "Option";
  public static final String showGrid = "Show Grid";
  public static final String showGridAction = "ShowGrid";
  
  public static final String actionFormat = "%s.%s";
  public static final String actionFormatSplitRegex = "\\.";
  
  ActionListener listener;
  
  public AppMenuBar (ActionListener listener, AppState appState) {
    JMenu menu;

    this.listener = listener;

    menu = this.addMenu(AppMenuBar.fileMenuTitle, KeyEvent.VK_0, "App and File Commands");
    this.addMenuItem(menu, AppMenuBar.exitItem, KeyEvent.VK_X, "Exit the application", 
      String.format(AppMenuBar.actionFormat, AppMenuBar.app, AppMenuBar.exitItem));

    menu = this.addMenu(AppMenuBar.objectMenuTitle, KeyEvent.VK_1, AppMenuBar.createRectangle);
    this.addMenuItem(menu, AppMenuBar.createRectangle, KeyEvent.VK_R, "Create a Rectangle Object", 
      String.format(AppMenuBar.actionFormat, AppMenuBar.objectMenuTitle, AppMenuBar.createRectangle));
    menu.addSeparator();
    this.addMenuItem(menu, AppMenuBar.deleteObject, KeyEvent.VK_D, "Delete Selected Object",
      String.format(AppMenuBar.actionFormat, AppMenuBar.objectMenuTitle, AppMenuBar.deleteObject));

    menu = this.addMenu(AppMenuBar.optionMenuTitle, KeyEvent.VK_2, "Options");
    this.addActionMenuItem(menu, appState.getGridAction(),
      String.format(AppMenuBar.optionMenuTitle, AppMenuBar.app, AppMenuBar.showGridAction));
  }

  // create a menu
  public JMenu addMenu(String menuName, int keyEvent, String description) {
    JMenu menu;
    menu = new JMenu(menuName);
    menu.setMnemonic(keyEvent);
    menu.getAccessibleContext().setAccessibleDescription(description);
    this.add(menu);
    return menu;
  }
  

  public void addMenuItem(JMenu menu, String itemName, int keyEvent, String description, String actionCommand) {
    JMenuItem menuItem;

    menuItem = new JMenuItem(itemName, keyEvent);
    menuItem.getAccessibleContext().setAccessibleDescription(description);
    menuItem.addActionListener(this.listener);
    menuItem.setActionCommand(actionCommand);
    menu.add(menuItem);
  }

  public void addActionMenuItem(JMenu menu, Action action, String actionCommand) {
    JMenuItem menuItem;

    menuItem = new JMenuItem(action);
    menuItem.setActionCommand(actionCommand);
    menu.add(menuItem);
  }
}