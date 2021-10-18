package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.KingSideCastleMove;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlackPlayer extends Player {


  public BlackPlayer(Board board,
                    Collection<Move> whiteStandardLegalMoves,
                    Collection<Move> blackStandardLegalMoves) {
    super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
  }

  @Override
  public Collection<Piece> getActivePieces() {
    return this.board.getBlackPieces();
  }

  @Override
  public Alliance getAlliance() {
    return Alliance.BLACK;
  }

  @Override
  public Player getOpponent() {
    return this.board.blackPlayer();
  }

  @Override
  protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals,
    Collection<Move> opponentsLegals) {
    return null;
  }

}
