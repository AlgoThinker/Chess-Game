package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtil;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Knight extends Piece {
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17,-15,10,-6,6,10,15,17};
    public Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();
        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES){
            int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if(isFirstColumnExclusion(this.piecePosition,currentCandidateOffset) ||
                    isSecondColumnExclusion(this.piecePosition,currentCandidateOffset)||
                    isSeventhColumnExclusion(this.piecePosition,currentCandidateOffset)||
                    isEighthColumnExclusion(this.piecePosition,currentCandidateOffset)){
                continue;
            }
            if(BoardUtil.isValidTileCoordinate(candidateDestinationCoordinate)){
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));
                }else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAlliance){
                        legalMoves.add(new AttackMove(board,this,
                                                           candidateDestinationCoordinate,pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtil.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);
    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtil.SECOND_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtil.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtil.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);
    }


}
