package src;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import src.pals.*;

public class AppContent extends JPanel {

  private static final long serialVersionUID = 1L;
  private Board board;
  private ToolBar toolBar;
  private ContextPalette contextPalette;

  public AppContent(ActionListener listener, AppState appState) {
    super(new BorderLayout());

    // create a toolbar
    this.toolBar = new ToolBar(listener, appState);
    // place toolbar on the left side of JPanel
    this.add(this.toolBar, BorderLayout.WEST);

    this.contextPalette = new ContextPalette(listener, appState);
    this.add(this.contextPalette, BorderLayout.EAST);
    appState.setContextPalette(this.contextPalette);
    
    this.board = new Board(appState);
    this.add(this.board, BorderLayout.CENTER);

  }

  public void menuItemCommand(String part1, String part2) {
    this.board.menuItemCommand(part2);
  }
}