import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar {
  private static final long serialVersionUID = 1L;
  private ActionListener listener;
  private int orientation;

  public ToolBar(ActionListener listener) {
    JButton button;
    this.listener = listener;

    this.orientation = VERTICAL;
    this.setOrientation(orientation);

    button = this.addButton("Hello", "HelloAction", "Hello There", "assets/rectangle.png");
    this.add(button);
    button = this.addButton("Goodbye", "GoodbyeAction", "Goodbye and Goodnight", "assets/rectangle.png");
    this.add(button);
  }

  private JButton addButton(String btnText, String actionCommand, String toolTipText, String url) {
    //Create and initialize the button.
    JButton button = new JButton();
    button.setActionCommand(actionCommand);
    button.setToolTipText(toolTipText);
    button.addActionListener(this.listener);
    button.setText(btnText);
    button.setIcon(new ImageIcon(url, btnText));
    return button;
  }
}