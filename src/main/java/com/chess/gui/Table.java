package com.chess.gui;

import com.chess.engine.board.BoardUtils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Table {

  private final JFrame gameFrame;
  private final BoardPanel boardPanel;

  private static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
  private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
  private static final Dimension TILE_PANEL_DIMENSION =  new Dimension(10, 10);

  public Table() {
    this.boardPanel = new BoardPanel();
    this.gameFrame = new JFrame("Simple Chess");
    this.gameFrame.setLayout(new BorderLayout());
    this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
    final JMenuBar tableMenuBar = createTableMenuBar();
    this.gameFrame.setJMenuBar(tableMenuBar);
    this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
    this.gameFrame.setVisible(true);
  }

  private JMenuBar createTableMenuBar() {
    final JMenuBar tableMenuBar = new JMenuBar();
    tableMenuBar.add(createFileMenu());
    return tableMenuBar;
  }

  private JMenu createFileMenu() {
    final JMenu fileMenu = new JMenu("File");
    final JMenuItem openPGN = new JMenuItem("Load PGN File");

    openPGN.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("open up that pgn file");
      }
    });
    fileMenu.add(openPGN);

    return fileMenu;
  }

  /* Represents Main Board */
  private class BoardPanel extends JPanel {
    final List<TilePanel> boardTiles;

    BoardPanel() {
      super(new GridLayout(8,8));
      this.boardTiles = new ArrayList<>();
      for(int i=0; i<BoardUtils.NUM_TILES; i++) {
        final TilePanel tilePanel = new TilePanel(this, i);
        this.boardTiles.add((tilePanel));
        add(tilePanel);
      }
      setPreferredSize(BOARD_PANEL_DIMENSION);
      validate();
    }

  }

  /* Represents Each Tile */
  private class TilePanel extends JPanel {
    private final int tileId;

    TilePanel(final BoardPanel boardPanel, final int tileId) {
      super(new GridBagLayout());
      this.tileId = tileId;
      setPreferredSize(TILE_PANEL_DIMENSION);
      assignTileColor();
      validate();
    }
  }

  private void assignTileColor() {

  }
}
