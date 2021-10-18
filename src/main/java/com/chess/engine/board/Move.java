package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Piece;

public abstract class Move {
  final Board board;
  final Piece movedPiece;
  final int destinationCoordinate;

  private Move(Board board, Piece movedPiece, int destinationCoordinate) {
    this.board = board;
    this.movedPiece = movedPiece;
    this.destinationCoordinate = destinationCoordinate;
  }

  public int getDestinationCoordinate() {
    return this.destinationCoordinate;
  }

  public Piece getMovedPiece() {
    return this.movedPiece;
  }

  public abstract Board execute();

  public static final class MajorMove extends Move {
   public MajorMove(final Board board, final Piece movedPiece, final int destinationCoordinate) {
      super(board, movedPiece, destinationCoordinate);
    }

    /* materialize a new board into existence */
    @Override
    public Board execute() {
      final Builder builder = new Builder();
      // set current pieces
      for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
        if(!this.movedPiece.equals(piece)) {
          builder.setPiece(piece);
        }
      }
      // set opponent pieces as it is
      for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces())
        builder.setPiece(piece);

      // move the moved piece
      builder.setPiece(this.movedPiece.movePiece(this));
      // now change the current move maker to opponent
      builder.setMoveMaker((this.board.currentPlayer().getOpponent().getAlliance()));

      return builder.build();
    }
  }

  public static final class AttackMove extends Move {
    final Piece attackedPiece;

    public AttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate,
      Piece attackedPiece) {
      super(board, movedPiece, destinationCoordinate);
      this.attackedPiece = attackedPiece;
    }

    @Override
    public Board execute() {
      return null;
    }
  }
}
