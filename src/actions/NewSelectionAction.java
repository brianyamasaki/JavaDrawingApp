package src.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

public class NewSelectionAction extends AbstractAction {
  private static final long serialVersionUID = 1L;
  private ActionListener actionListener;


  public NewSelectionAction(ActionListener actionListener) {
    super();
    this.actionListener = actionListener;
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}