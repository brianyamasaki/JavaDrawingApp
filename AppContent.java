import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class AppContent extends JPanel {

  private static final long serialVersionUID = 1L;
  private Board board;
  private ToolBar toolBar;

  public AppContent(ActionListener listener) {
    super(new BorderLayout());

    this.toolBar = new ToolBar(listener);
    this.add(this.toolBar, BorderLayout.WEST);
    
    this.board = new Board();
    this.add(this.board, BorderLayout.CENTER);

  }

  public void menuItemCommand(String part1, String part2) {
    if (part1.equals(AppMenuBar.objectMenuTitle)) {
      this.board.menuItemCommand(part2);
    } 
  }
}