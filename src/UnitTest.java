import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Created by christopherlau on 6/16/17.
 */
public class UnitTest {

    @Test
    public void testSolve() {
        int[] board1 = {4,8,0,1,3,0,0,6,0,
                3,0,0,0,0,4,2,0,0,
                9,0,5,0,0,8,1,4,0,
                6,0,0,0,7,0,8,2,0,
                0,3,7,2,0,9,5,0,0,
                2,4,1,0,5,6,0,0,9,
                0,6,0,9,8,7,3,0,2,
                0,0,8,5,6,0,4,0,1,
                0,9,0,0,1,2,0,7,8};
        //checkBoard(board1);

        int[] board2 = {4,0,9,5,0,0,6,0,1,
                        0,0,7,2,0,0,9,0,0,
                        0,0,5,0,0,1,0,0,2,
                        0,0,8,7,5,0,0,0,9,
                        0,7,0,0,9,0,0,3,0,
                        6,0,0,0,8,2,4,0,0,
                        9,0,0,8,0,0,7,0,0,
                        0,0,4,0,0,9,5,0,0,
                        7,0,2,0,0,6,8,0,3};
        //checkBoard(board2);

        int[] board3 = {3,2,0,5,1,0,0,0,0,
                        0,0,0,0,0,6,0,0,1,
                        6,0,0,0,0,0,0,0,0,
                        2,0,6,0,0,3,1,0,0,
                        5,0,4,1,7,2,6,0,3,
                        0,0,3,6,0,0,7,0,5,
                        0,0,0,0,0,0,0,0,4,
                        9,0,0,2,0,0,0,0,0,
                        0,0,0,0,6,9,0,5,7};
        //checkBoard(board3);

        int[] board4 = {0,0,0,8,0,2,0,0,6,
        0,0,9,0,0,0,5,0,0,
        5,0,0,0,0,0,0,0,8,
        0,9,0,0,6,5,3,0,1,
        0,3,0,0,8,0,0,7,0,
        1,0,5,2,3,0,0,6,0,
        3,0,0,0,0,0,0,0,9,
        0,0,2,0,0,0,1,0,0,
        9,0,0,4,0,8,0,0,0};

        checkBoard(board4);

    }

    public void checkBoard(int[] boardValues) {

        Board board = new Board(boardValues);
        board.printBoard();

        solveBoard(board);

    }

    Board solveBoard(Board board) {
        int previousNodesLeft = 1;
        int currentNodesLeft = 0;
        while (currentNodesLeft < previousNodesLeft) {
            previousNodesLeft = board.nodesLeft();
            board.performStrategy();
            currentNodesLeft = board.nodesLeft();
            if (!board.checkValid()) {
                return null;
            }
            if (previousNodesLeft != currentNodesLeft) {
                board.printBoard();
            }
        }
        if (!board.checkValid()) {
            return null;
        }
        if (board.checkSolved()) {
            board.printBoard();
            return board;
        }
        return makeGuess(board);
    }

    Board makeGuess(Board board) {
        Node guessNode = board.findGuessNode();
        ArrayList<Integer> guesses = new ArrayList<Integer>();
        guesses.addAll(guessNode.possible());
        if (guesses.size() < 2) {
            return null;
        }
        for (Integer number:guesses) {
            Board newBoard = board.copyBoard();
            newBoard.setNodeNum(guessNode.id(), number);
            Board childBoard =  solveBoard(newBoard);
            if (childBoard == null) {
                continue;
            } else {
                return childBoard;
            }
        }
        return null;
    }



}
