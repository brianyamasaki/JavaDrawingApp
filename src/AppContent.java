package src;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class AppContent extends JPanel {

  private static final long serialVersionUID = 1L;
  private Board board;
  private ToolBar toolBar;

  public AppContent(ActionListener listener, AppState appState) {
    super(new BorderLayout());

    // create a toolbar
    this.toolBar = new ToolBar(listener, appState);
    // place toolbar on the left side of JPanel
    this.add(this.toolBar, BorderLayout.WEST);
    
    this.board = new Board(appState);
    this.add(this.board, BorderLayout.CENTER);

  }

  public void menuItemCommand(String part1, String part2) {
    this.board.menuItemCommand(part2);
  }
}