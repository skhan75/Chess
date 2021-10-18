package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece{

  private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 } ;

  public Queen(final int piecePosition, final Alliance pieceAlliance) {
    super(piecePosition, PieceType.QUEEN, pieceAlliance);
  }

  @Override
  public Collection<Move> calculateLegalMoves(Board board) {
    final List<Move> legalMoves = new ArrayList<>();

    for(int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
      int candidateDestinationCoordinate = this.piecePosition;

      while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
        if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
          isEightColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
          break;
        }
        candidateDestinationCoordinate += candidateCoordinateOffset;
        if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
          final Tile candidateDestinaionTile = board.getTile(candidateDestinationCoordinate);
          if (!candidateDestinaionTile.isTileOccupied()) {
            legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
          } else {
            final Piece pieceAtDestination = candidateDestinaionTile.getPiece();
            final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
            if (this.pieceAlliance != pieceAlliance) { // enemy piece
              legalMoves.add(
                new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
            }
            break; // since bishop can't hop over pieces
          }
        }
      }
    }
    return ImmutableList.copyOf(legalMoves);
  }

  @Override
  public Queen movePiece(Move move) {
    return new Queen(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
  }

  @Override
  public String toString() {
    return PieceType.QUEEN.toString();
  }

  private static boolean isFirstColumnExclusion(final int currentPosition,
    final int candidateOffset) {
    return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1 ||
      candidateOffset == -9 || candidateOffset == 7);
  }

  private static boolean isEightColumnExclusion(final int currentPosition,
    final int candidateOffset) {
    return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == 1 ||
      candidateOffset == -7 || candidateOffset == 9);
  }
}
