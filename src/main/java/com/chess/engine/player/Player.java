package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {
  protected final Board board;
  protected final King playerKing;
  protected final Collection<Move> legalMoves;
  private final boolean isInCheck;

  protected Player(final Board board,
    final Collection<Move> legalMoves,
    final Collection<Move> opponentMoves) {
    this.board = board;
    this.playerKing = establishKing();
    this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves,
      calculateKingCastles(legalMoves, opponentMoves)));
    this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(),
      opponentMoves).isEmpty();
  }

  /*
    Checks if destination coordinates of enemy moves is equal to current King's position
    @Returns Collection of moves that attacks the king
  */
  protected static Collection<Move> calculateAttacksOnTile(int piecePosition,
    Collection<Move> opponentMoves) {
    final List<Move> attackMoves = new ArrayList<>();

    for(final Move opponentMove : opponentMoves) {
      if(piecePosition == opponentMove.getDestinationCoordinate())
        attackMoves.add(opponentMove);
    }
    return ImmutableList.copyOf(attackMoves);
  }

  private King establishKing() {
    for(final Piece piece : getActivePieces()) {
      if(piece.getPieceType().isKing())
        return (King) piece;
    }
    throw new RuntimeException("Should not reach here! Not a valid board ");
  }

  public boolean isMoveLegal(final Move move) {
    return this.legalMoves.contains(move);
  }

  /* Returns if the King is in Check*/
  public boolean isInCheck() {
    return this.isInCheck;
  }

  /* Returns if the King is in Check and there are no escape moves
     This is not part of constructor initialization, because it has makeMove()
     which constructs a new board when ever the Player constructor is initialized
     which is not what we want */
  public boolean isInCheckMate() {
    return this.isInCheck && !hasEscapeMoves();
  }

  /* Return if there is a state when the King is not in check, but it doesn't have a move
     available where it won't be in check */
  public boolean isInStaleMate() {
    return !this.isInCheck && !hasEscapeMoves();
  }

  public boolean inCastled() {
    return false;
  }

  protected boolean hasEscapeMoves() {
    for(final Move move : this.legalMoves) {
      final MoveTransition transition = makeMove(move);
      if(transition.getMoveStatus().isDone())
        return true;
    }
    return false;
  }

  private Piece getPlayerKing() {
    return this.playerKing;
  }

  public Collection<Move> getLegalMoves() {
    return this.legalMoves;
  }

  /* Returns a new Board when we make a move, else if illegal then we return the same board*/
  public MoveTransition makeMove(final Move move) {

    if(!isMoveLegal(move))
      return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);

    // polymorphically execute a new move, and create a new board
    final Board transitionBoard = move.execute();

    // then check if that new king placement on the new board is not under attack from opponent
    final Collection<Move> kingAttacks =
      Player.calculateAttacksOnTile(
        // after we execute move, we are no longer current player
        transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
        // after we execute move, current player is the opponent
        transitionBoard.currentPlayer().getLegalMoves()
      );

    if(!kingAttacks.isEmpty())
      return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);

    return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
  }

  public abstract Collection<Piece> getActivePieces();
  public abstract Alliance getAlliance();
  public abstract Player getOpponent();
  protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals,
    Collection<Move> opponentsLegals);
}
