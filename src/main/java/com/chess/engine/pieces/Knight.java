package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

  // candidate offsets
  private final static int[] CANDIDATE_MOVE_COORDINATES = { -17, -15, 10, -6, 6, 10, 15, 17 };

  Knight(final int piecePosition, final Alliance pieceAlliance) {
    super(piecePosition, pieceAlliance);
  }

  @Override
  public List<Move> calculatedLegalMoves(Board board) {
    int candidateDestinationCoordinate;
    final List<Move> legalMoves = new ArrayList<>();

    for(int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
      candidateDestinationCoordinate = this.piecePosition + currentCandidate;
      if(true) {
        final Tile candidateDestinaionTile = board.getTile(candidateDestinationCoordinate);
        if(!candidateDestinaionTile.isTileOccupied()) {
          legalMoves.add(new Move());
        } else {
          final Piece pieceAtDestination = candidateDestinaionTile.getPiece();
          final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
          if(this.pieceAlliance != pieceAlliance) { // enemy piece
            legalMoves.add(new Move());
          }
        }
      }
    }

    return ImmutableList.copyOf(legalMoves);
  }
}
