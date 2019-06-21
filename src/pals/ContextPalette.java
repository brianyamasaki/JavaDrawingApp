package src.pals;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import src.AppState;
import src.Board;
import src.objects.GObject;

public class ContextPalette extends JToolBar {
  private static final long serialVersionUID = 1L;
  public static final String strSelection = "SELECTION";
  private Board board;
  private int orientation;
  private AppState appState;
  private GroupLayout layout;
  private final int EDITBOX_HEIGHT = 25;
  private final int EDITBOX_WIDTH_MIN = 50;
  private final int EDITBOX_WIDTH_MAX = 100;
  private final int GAP = 3;
  // indices for Components top level array
  private final int X = 0;
  private final int Y = 1;
  private final int WIDTH = 2;
  private final int HEIGHT = 3;
  private ArrayList<CompleteSpinner> boundingSpinners;

  public ContextPalette(Board board, AppState appState) {
    this.board = board;
    this.appState = appState;
    this.orientation = VERTICAL;
    this.setOrientation(orientation);

    this.layout = new GroupLayout(this);
    this.boundingSpinners = new ArrayList<CompleteSpinner>();
    this.addBorderFields();
  }

  private void addBorderFields() {
    this.boundingSpinners.add(new CompleteSpinner("X", this.board));
    this.boundingSpinners.add(new CompleteSpinner("Y", this.board));
    this.boundingSpinners.add(new CompleteSpinner("Width", this.board));
    this.boundingSpinners.add(new CompleteSpinner("Height", this.board));
    this.layout.setVerticalGroup(
      this.layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
          .addComponent(boundingSpinners.get(X).getLabel())
          .addComponent(boundingSpinners.get(X).getSpinner(), GroupLayout.PREFERRED_SIZE, EDITBOX_HEIGHT, GroupLayout.PREFERRED_SIZE)
          .addComponent(boundingSpinners.get(Y).getLabel())
          .addComponent(boundingSpinners.get(Y).getSpinner(), GroupLayout.PREFERRED_SIZE, EDITBOX_HEIGHT, GroupLayout.PREFERRED_SIZE)
        )
        .addGap(GAP)
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
          .addComponent(boundingSpinners.get(WIDTH).getLabel())
          .addComponent(boundingSpinners.get(WIDTH).getSpinner(), GroupLayout.PREFERRED_SIZE, EDITBOX_HEIGHT, GroupLayout.PREFERRED_SIZE)
          .addComponent(boundingSpinners.get(HEIGHT).getLabel())
          .addComponent(boundingSpinners.get(HEIGHT).getSpinner(), GroupLayout.PREFERRED_SIZE, EDITBOX_HEIGHT, GroupLayout.PREFERRED_SIZE)
        )
      );
      this.layout.setHorizontalGroup(
        this.layout.createParallelGroup(GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGap(GAP)
            .addComponent(boundingSpinners.get(X).getLabel())
            .addGap(GAP)
            .addComponent(boundingSpinners.get(X).getSpinner(), EDITBOX_WIDTH_MIN, GroupLayout.DEFAULT_SIZE, EDITBOX_WIDTH_MAX)
            .addGap(GAP)
            .addComponent(boundingSpinners.get(Y).getLabel())
            .addGap(GAP)
            .addComponent(boundingSpinners.get(Y).getSpinner(), EDITBOX_WIDTH_MIN, GroupLayout.DEFAULT_SIZE, EDITBOX_WIDTH_MAX)
            .addGap(GAP)
          )
          .addGroup(layout.createSequentialGroup()
            .addGap(GAP)
            .addComponent(boundingSpinners.get(WIDTH).getLabel())
            .addGap(GAP)
            .addComponent(boundingSpinners.get(WIDTH).getSpinner(), EDITBOX_WIDTH_MIN, GroupLayout.DEFAULT_SIZE, EDITBOX_WIDTH_MAX)
            .addGap(GAP)
            .addComponent(boundingSpinners.get(HEIGHT).getLabel())
            .addGap(GAP)
            .addComponent(boundingSpinners.get(HEIGHT).getSpinner(), EDITBOX_WIDTH_MIN, GroupLayout.DEFAULT_SIZE, EDITBOX_WIDTH_MAX)
            .addGap(GAP)
          )
      );
      this.setLayout(this.layout);
  }

  void setBoundSpinners(Rectangle boundRect) {
    CompleteSpinner cspin;
    int[] values = {boundRect.x, boundRect.y, boundRect.width, boundRect.height};
    Boolean enable = boundRect.width > 0 || boundRect.height > 0;

    for (int i = 0; i < 4; i++) {
      cspin = this.boundingSpinners.get(i);
      cspin.getSpinner().setEnabled(enable);
      cspin.getModel().setValue(values[i]);  
    }
  }

  public void refreshPalette() {
    ArrayList<GObject> selectionList = appState.getSelectedObjects();
    Rectangle boundRect;
    if (selectionList.size() >= 1) {
      GObject selectedObject = selectionList.get(0);
      boundRect = selectedObject.getBoundingRect();
      // System.out.println("Context Palette for object " + selectedObject.toString());
    } else {
      boundRect = new Rectangle();
    }
    this.setBoundSpinners(boundRect);

    this.repaint();
  }

  private class CompleteSpinner implements ChangeListener{
    private String strLabel;
    private JLabel label;
    private JSpinner spinner;
    private SpinnerNumberModel model;
    private Board board;

    public CompleteSpinner(String strLabel, Board board) {
      this.strLabel = strLabel;
      this.board = board;

      this.model = new SpinnerNumberModel(0,0,8000, 1);

      this.label = new JLabel(strLabel);
      this.spinner = new JSpinner(model);
      this.spinner.addChangeListener(this);
      this.spinner.setEnabled(false);
      this.label.setLabelFor(spinner);
    }

    public JLabel getLabel() {
      return this.label;
    }

    public JSpinner getSpinner() {
      return this.spinner;
    }

    public SpinnerNumberModel getModel() {
      return this.model;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
      // System.out.println(this.strLabel + ((JSpinner)e.getSource()).getModel().getValue());
      board.objectContextCommand(String.format("%s %s %d", ContextPalette.strSelection, strLabel, ((JSpinner)e.getSource()).getModel().getValue()));
    }
  }
}