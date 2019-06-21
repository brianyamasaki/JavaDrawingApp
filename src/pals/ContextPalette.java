package src.pals;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.EnumSet;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
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
  private final int EDITBOX_WIDTH_MAX = 70;
  private final int GAP_MIN = 3;
  // indices for Components top level array
  private final int X = 0;
  private final int Y = 1;
  private final int WIDTH = 2;
  private final int HEIGHT = 3;
  private ArrayList<CompleteSpinner> spinners;

  public ContextPalette(Board board, AppState appState) {
    this.board = board;
    this.appState = appState;
    this.orientation = VERTICAL;
    this.setOrientation(orientation);

    this.layout = new GroupLayout(this);
    this.spinners = new ArrayList<CompleteSpinner>();
    this.addBorderFields();
    this.setLayout();
  }

  private void addBorderFields() {
    this.spinners.add(new CompleteSpinner("X", this.board));
    this.spinners.add(new CompleteSpinner("Y", this.board));
    this.spinners.add(new CompleteSpinner("W", this.board));
    this.spinners.add(new CompleteSpinner("H", this.board));
  }

  void setLayout() {
    SequentialGroup seqGroup;
    ParallelGroup parallelGroup;

    seqGroup = this.layout.createSequentialGroup();
    this.addBorderGroupVertical(seqGroup);
    this.layout.setVerticalGroup(seqGroup);
    parallelGroup = this.layout.createParallelGroup(GroupLayout.Alignment.LEADING);
    this.layout.setHorizontalGroup(parallelGroup);
    this.addBorderGroupHorizontal(parallelGroup);
    this.setLayout(this.layout);
  }

  void addBorderGroupVertical(SequentialGroup seqGroup) {
    seqGroup
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addComponent(spinners.get(X).getLabel())
        .addComponent(spinners.get(X).getSpinner(), GroupLayout.PREFERRED_SIZE, EDITBOX_HEIGHT, GroupLayout.PREFERRED_SIZE)
        .addComponent(spinners.get(Y).getLabel())
        .addComponent(spinners.get(Y).getSpinner(), GroupLayout.PREFERRED_SIZE, EDITBOX_HEIGHT, GroupLayout.PREFERRED_SIZE)
      )
      .addGap(GAP_MIN)
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addComponent(spinners.get(WIDTH).getLabel())
        .addComponent(spinners.get(WIDTH).getSpinner(), GroupLayout.PREFERRED_SIZE, EDITBOX_HEIGHT, GroupLayout.PREFERRED_SIZE)
        .addComponent(spinners.get(HEIGHT).getLabel())
        .addComponent(spinners.get(HEIGHT).getSpinner(), GroupLayout.PREFERRED_SIZE, EDITBOX_HEIGHT, GroupLayout.PREFERRED_SIZE)
      );

  }
  
  void addBorderGroupHorizontal(ParallelGroup parallelGroup) {
    parallelGroup
      .addGroup(layout.createSequentialGroup()
        .addGap(GAP_MIN)
        .addComponent(spinners.get(X).getLabel())
        .addGap(GAP_MIN)
        .addComponent(spinners.get(X).getSpinner(), EDITBOX_WIDTH_MIN, GroupLayout.DEFAULT_SIZE, EDITBOX_WIDTH_MAX)
        .addGap(GAP_MIN)
        .addComponent(spinners.get(Y).getLabel())
        .addGap(GAP_MIN)
        .addComponent(spinners.get(Y).getSpinner(), EDITBOX_WIDTH_MIN, GroupLayout.DEFAULT_SIZE, EDITBOX_WIDTH_MAX)
        .addGap(GAP_MIN)
      )
      .addGroup(layout.createSequentialGroup()
        .addGap(GAP_MIN)
        .addComponent(spinners.get(WIDTH).getLabel())
        .addGap(GAP_MIN)
        .addComponent(spinners.get(WIDTH).getSpinner(), EDITBOX_WIDTH_MIN, GroupLayout.DEFAULT_SIZE, EDITBOX_WIDTH_MAX)
        .addGap(GAP_MIN)
        .addComponent(spinners.get(HEIGHT).getLabel())
        .addGap(GAP_MIN)
        .addComponent(spinners.get(HEIGHT).getSpinner(), EDITBOX_WIDTH_MIN, GroupLayout.DEFAULT_SIZE, EDITBOX_WIDTH_MAX)
        .addGap(GAP_MIN)
      );
  }

  void setBoundSpinners(Rectangle boundRect) {
    CompleteSpinner cspin;
    int[] values = {boundRect.x, boundRect.y, boundRect.width, boundRect.height};
    Boolean enable = boundRect.width > 0 || boundRect.height > 0;

    for (int i = 0; i < 4; i++) {
      cspin = this.spinners.get(i);
      cspin.getSpinner().setEnabled(enable);
      cspin.getModel().setValue(values[i]);  
    }
  }

  public void refreshPalette() {
    ArrayList<GObject> selectionList = appState.getSelectedObjects();
    Rectangle boundRect;

    if (selectionList.size() >= 1) {
      GObject selectedObject = selectionList.get(0);
      EnumSet<ContextPaletteGroup> palGroup = selectedObject.getContextProperties();
      // setup bounding box edit fields
      boundRect = selectedObject.getBoundingRect();
      if (palGroup.contains(ContextPaletteGroup.Image)) {
        // System.out.println("Selected an image");
      }
    } else {
      // nothing selected, so initialize variables to something valid;
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