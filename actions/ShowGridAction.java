package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

public class ShowGridAction extends AbstractAction {
  private static final long serialVersionUID = 1L;

  private final String showGridValue = "ShowGridValue";
  public static final String showGridTitleFalse = "Show Grid";
  public static final String showGridTitleTrue = "Hide Grid";
  private ActionListener listener;

  public ShowGridAction(ActionListener listener, boolean showGrid) {
    super(showGridTitleTrue);
    this.listener = listener;
    this.setBooleanValue(showGrid);
    putValue(MNEMONIC_KEY, KeyEvent.VK_G);
  }

  private String getProperTitle(boolean showGrid) {
    return showGrid ? showGridTitleTrue : showGridTitleFalse;
  }

  public void actionPerformed(ActionEvent e) {
    this.listener.actionPerformed(e);
  }

  public void setBooleanValue(boolean newValue) {
    this.putValue(showGridValue, newValue);
    this.putValue(SHORT_DESCRIPTION, this.getProperTitle(newValue));
  }

  public boolean getBooleanValue() {
    boolean val = (boolean) getValue(showGridValue);
    return val;
  }
}