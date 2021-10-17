package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece{
  private final static int[] CANDIDATE_MOVE_COORDINATE = { 8, 16, 7, 9 };

  Pawn(int piecePosition, Alliance pieceAlliance) {
    super(piecePosition, pieceAlliance);
  }

  @Override
  public Collection<Move> calculatedLegalMoves(Board board) {
    final List<Move> legalMoves = new ArrayList<>() ;

    for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
      final int candidateDestinationCoordinate =
        this.piecePosition + (this.getPieceAlliance().getDirection() * currentCandidateOffset);

      if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
        continue;

      if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
        // TODO -- deal with pawn promotions
        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
      } else if(currentCandidateOffset == 16 && this.isFirstMove()
            && (BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceAlliance.isBlack()) ||
            (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceAlliance.isWhite())) {
          final int behindCandidateDestinationCoordinate =
            this.piecePosition + (this.getPieceAlliance().getDirection() * 8);

          if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
            !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
            legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
          }
      } else if(currentCandidateOffset == 7 &&
            !((BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
            (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
          if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
            final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
            if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) { // attack move
              // TODO -- pawn promotion
              legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
            }
        }
      } else if(currentCandidateOffset == 9 &&
           !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
            (BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
          if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
            final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
            if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) { // attack move
              // TODO -- pawn promotion
              legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
            }
          }
      }
    }
    return null;
  }
}
