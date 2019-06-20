package src.pals;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;

import src.AppState;
import src.objects.GObject;

public class ContextPalette extends JToolBar {
  private static final long serialVersionUID = 1L;
  private ActionListener listener;
  private int orientation;
  private AppState appState;
  private GroupLayout layout;
  private final int EDITBOX_HEIGHT = 25;
  private final int EDITBOX_WIDTH_MIN = 50;
  private final int EDITBOX_WIDTH_MAX = 100;
  private final int GAP = 3;

  public ContextPalette(ActionListener listener, AppState appState) {
    this.listener = listener;
    this.appState = appState;
    this.orientation = VERTICAL;
    this.setOrientation(orientation);
    this.layout = new GroupLayout(this);
    this.addBorderFields();
  }

  private void addBorderFields() {
    ArrayList<JComponent> components = new ArrayList<JComponent>();

    this.addSpinnerAndLabel(components, "X");
    this.addSpinnerAndLabel(components, "Y");
    this.addSpinnerAndLabel(components, "Width");
    this.addSpinnerAndLabel(components, "Height");
    this.layout.setVerticalGroup(
      this.layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
          .addComponent(components.get(0))
          .addComponent(components.get(1), GroupLayout.PREFERRED_SIZE, EDITBOX_HEIGHT, GroupLayout.PREFERRED_SIZE)
          .addComponent(components.get(2))
          .addComponent(components.get(3), GroupLayout.PREFERRED_SIZE, EDITBOX_HEIGHT, GroupLayout.PREFERRED_SIZE)
        )
        .addGap(GAP)
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
          .addComponent(components.get(4))
          .addComponent(components.get(5), GroupLayout.PREFERRED_SIZE, EDITBOX_HEIGHT, GroupLayout.PREFERRED_SIZE)
          .addComponent(components.get(6))
          .addComponent(components.get(7), GroupLayout.PREFERRED_SIZE, EDITBOX_HEIGHT, GroupLayout.PREFERRED_SIZE)
        )
      );
      this.layout.setHorizontalGroup(
        this.layout.createParallelGroup(GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGap(GAP)
            .addComponent(components.get(0))
            .addGap(GAP)
            .addComponent(components.get(1), EDITBOX_WIDTH_MIN, GroupLayout.DEFAULT_SIZE, EDITBOX_WIDTH_MAX)
            .addGap(GAP)
            .addComponent(components.get(2))
            .addGap(GAP)
            .addComponent(components.get(3), EDITBOX_WIDTH_MIN, GroupLayout.DEFAULT_SIZE, EDITBOX_WIDTH_MAX)
            .addGap(GAP)
          )
          .addGroup(layout.createSequentialGroup()
            .addGap(GAP)
            .addComponent(components.get(4))
            .addGap(GAP)
            .addComponent(components.get(5), EDITBOX_WIDTH_MIN, GroupLayout.DEFAULT_SIZE, EDITBOX_WIDTH_MAX)
            .addGap(GAP)
            .addComponent(components.get(6))
            .addGap(GAP)
            .addComponent(components.get(7), EDITBOX_WIDTH_MIN, GroupLayout.DEFAULT_SIZE, EDITBOX_WIDTH_MAX)
            .addGap(GAP)
          )
      );
      this.setLayout(this.layout);
  }

  private void addSpinnerAndLabel(ArrayList<JComponent> components, String strLabel) {
    JLabel label;
    JSpinner spinner;

    label = new JLabel(strLabel);
    spinner = new JSpinner();
    label.setLabelFor(spinner);
    components.add(label);
    components.add(spinner);
  }

  public void refreshPalette() {
    ArrayList<GObject> selectionList = appState.getSelectedObjects();
    if (selectionList.size() >= 1) {
      this.add(new JLabel("Next Step"));
      // this.add(selectionList.get(0).contextPalettePanel());
    }
    this.repaint();
  }
}