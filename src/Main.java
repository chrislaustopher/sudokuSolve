import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by christopherlau on 6/16/17.
 */
public final class Main {
    public static void main(String... args) {
        int n = 81;
        Scanner s = new Scanner(System.in);
        int boardValues[] = new int[n];
        for (int i = 0; i < n; i++) {
            boardValues[i] = s.nextInt();
        }
        Board board = new Board(boardValues);
        board.printBoard();

        Main m = new Main();
        m.solveBoard(board);
    }

    /** Create Board. */
    void createBoard() {
        int[] boardValues = {4,8,0,1,3,0,0,6,0,
                3,0,0,0,0,4,2,0,0,
                9,0,5,0,0,8,1,4,0,
                6,0,0,0,7,0,8,2,0,
                0,3,7,2,0,9,5,0,0,
                2,4,1,0,5,6,0,9,0,
                0,6,0,9,8,7,3,0,2,
                0,0,8,5,6,0,4,0,1,
                0,9,0,0,1,2,0,7,8};
        Board board = new Board(boardValues);
    }

    public void checkBoard(int[] boardValues) {

        Board board = new Board(boardValues);

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
                System.out.println("solving..");
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
