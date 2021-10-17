package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Board {
  private final List<Tile> gameBoard;
  private final Collection<Piece> whitePieces;
  private final Collection<Piece> blackPieces;

  private Board(Builder builder) {
    this.gameBoard = createGameBoard(builder);
    this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
    this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);

    final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
    final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    for(int i=0; i<BoardUtils.NUM_TILES; i++) {
      final String tileText = prettyPrint(this.gameBoard.get(i));
      sb.append(String.format("%3s", tileText));
      if((i+1) % BoardUtils.NUM_TILES_PER_ROW == 0)
        sb.append("\n");
    }
    return sb.toString();
  }

  private static String prettyPrint(final Tile tile) {
    if(tile.isTileOccupied()) {
      return tile.getPiece().getPieceAlliance().isBlack() ? tile.toString().toLowerCase()
        : tile.toString();
    }
    return tile.toString();
  }

  private Collection<Move> calculateLegalMoves(final  Collection<Piece> pieces) {
    final List<Move> legalMoves = new ArrayList<>();
    for(final Piece piece : pieces) {
      legalMoves.addAll(piece.calculatedLegalMoves(this));
    }
    return ImmutableList.copyOf(legalMoves);
  }

  private static Collection<Piece> calculateActivePieces(final List<Tile> gameBoard,
    final Alliance alliance) {
    final List<Piece> activePieces = new ArrayList<>();

    for(final Tile tile : gameBoard) {
      if(tile.isTileOccupied()) {
        final Piece piece = tile.getPiece();
        if(piece.getPieceAlliance() == alliance) {
          activePieces.add(piece);
        }
      }
    }
    return ImmutableList.copyOf(activePieces);
  }

  public Tile getTile(final int tileCoordinate) {
    return gameBoard.get(tileCoordinate);
  }

  private static List<Tile> createGameBoard(final Builder buidler) {
    final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
    for(int i=0; i<BoardUtils.NUM_TILES; i++) {
      tiles[i] = Tile.createTile(i, buidler.boardConfig.get(i));
    }
    return ImmutableList.copyOf(tiles);
  }

  public static Board createStandardBoard() {
    final Builder builder = new Builder();

    // Black layout
    builder.setPiece(new Rook(0, Alliance.BLACK));
    builder.setPiece(new Knight(1, Alliance.BLACK));
    builder.setPiece(new Bishop(2, Alliance.BLACK));
    builder.setPiece(new Queen(3, Alliance.BLACK));
    builder.setPiece(new King(4, Alliance.BLACK));
    builder.setPiece(new Bishop(5, Alliance.BLACK));
    builder.setPiece(new Knight(6, Alliance.BLACK));
    builder.setPiece(new Rook(7, Alliance.BLACK));
    builder.setPiece(new Pawn(8, Alliance.BLACK));
    builder.setPiece(new Pawn(9, Alliance.BLACK));
    builder.setPiece(new Pawn(10, Alliance.BLACK));
    builder.setPiece(new Pawn(11, Alliance.BLACK));
    builder.setPiece(new Pawn(12, Alliance.BLACK));
    builder.setPiece(new Pawn(13, Alliance.BLACK));
    builder.setPiece(new Pawn(14, Alliance.BLACK));
    builder.setPiece(new Pawn(15, Alliance.BLACK));

    // White layout
    builder.setPiece(new Rook(48, Alliance.WHITE));
    builder.setPiece(new Knight(49, Alliance.WHITE));
    builder.setPiece(new Bishop(50, Alliance.WHITE));
    builder.setPiece(new Queen(51, Alliance.WHITE));
    builder.setPiece(new King(52, Alliance.WHITE));
    builder.setPiece(new Bishop(53, Alliance.WHITE));
    builder.setPiece(new Knight(54, Alliance.WHITE));
    builder.setPiece(new Rook(55, Alliance.WHITE));
    builder.setPiece(new Pawn(56, Alliance.WHITE));
    builder.setPiece(new Pawn(57, Alliance.WHITE));
    builder.setPiece(new Pawn(58, Alliance.WHITE));
    builder.setPiece(new Pawn(59, Alliance.WHITE));
    builder.setPiece(new Pawn(60, Alliance.WHITE));
    builder.setPiece(new Pawn(61, Alliance.WHITE));
    builder.setPiece(new Pawn(62, Alliance.WHITE));
    builder.setPiece(new Pawn(63, Alliance.WHITE));

    // White start with the move
    builder.setMoveMaker(Alliance.WHITE);

    return builder.build();
  }
  // Builder Pattern
  public static class Builder {
    Map<Integer, Piece> boardConfig;
    Alliance nextMoveMaker;

    public Builder() {

    }

    public Builder setPiece(final Piece piece) {
      this.boardConfig.put(piece.getPiecePosition(), piece);
      return this;
    }

    public Builder setMoveMaker(final Alliance nextMoveMaker) {
      this.nextMoveMaker = nextMoveMaker;
      return this;
    }

    public Board build() {
      return new Board(this);
    }
  }
}
