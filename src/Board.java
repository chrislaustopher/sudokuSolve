import java.util.ArrayList;

/**
 * Created by christopherlau on 6/16/17.
 */

public class Board {

    /** A board with 9 columns and 9 rows. */
    Board(int[] nodes) {
        _nodeArray = new Node[9][9];

        for (int ci = 0; ci < 9; ci++) {
            int i = ci;
            for (int ri = 0; ri < 9; ri++, i += 9) {
                _nodeArray[ci][ri] = new Node(i, ci, ri, nodes[i], this);
            }
        }

        createCols(); createRows(); createBlks();
        initializeBlocks();


    }

    /** Initialize all 3 types of blocks for each node. */
    void initializeBlocks() {
        for (int ci = 0; ci < 9; ci++) {
            for (int ri = 0; ri < 9; ri++) {
                Node node = _nodeArray[ci][ri];

                int colNum = blockNumForNode(_colArray, node);
                int rowNum = blockNumForNode(_rowArray, node);
                int blkNum = blockNumForNode(_blkArray, node);

                Block col = _colArray[colNum];
                Block row = _rowArray[rowNum];
                Block blk = _blkArray[blkNum];

                node.initializeCol(col);
                node.initializeRow(row);
                node.initializeBlk(blk);
            }
        }
    }

    /** Initialize _colArray with all nodes. */
    void createCols() {
        _colArray = new Block[9];
        for (int ci = 0; ci < 9; ci++) {
            _colArray[ci] = new Block(0, ci, _nodeArray[ci]);
        }
    }

    /** Initialize _rowArray with all nodes. */
    void createRows() {
        _rowArray = new Block[9];
        for (int ri = 0; ri < 9; ri++) {
            Node[] nodeArray = new Node[9];
            for (int ci = 0; ci < 9; ci++) {
                nodeArray[ci] = _nodeArray[ci][ri];
            }
            _rowArray[ri] = new Block(1, ri, nodeArray);
        }
    }

    /** Initialize _blkArray with all nodes. */
    void createBlks() {
        _blkArray = new Block[9];
        int[][] blks = new int[9][9];
        for (int bi = 0, i = 0; bi < 9; bi++) {
            ArrayList<Integer> blk = new ArrayList<Integer>(9);
            for (int ci = 0; ci < 3; ci++) {
                blk.add(ci + i);
                blk.add(ci + 9 + i);
                blk.add(ci + 18 + i);
            }
            for (int ind = 0; ind < 9; ind++) {
                blks[bi][ind] = (int) blk.get(ind);
            }
            if (bi % 3 == 2) {
                i += 21;
            } else {
                i += 3;
            }
        }
        for (int i = 0; i < 9; i++) {
            Node[] nodeArray = new Node[9];
            for (int ni = 0; ni < 9; ni++) {
                nodeArray[ni] = nodeForId(blks[i][ni]);
            }
            _blkArray[i] = new Block(3, i, nodeArray);
        }

    }

    /** Find Node for identifying number. */
    Node nodeForId(int id) {
        for (int ci = 0; ci < 9; ci++) {
            for (int ri = 0; ri < 9; ri++) {
                if (_nodeArray[ci][ri].id() == id) {
                    return _nodeArray[ci][ri];
                }
            }
        }
        return null;
    }

    /** Find block number for any given node in blockArray. */
    int blockNumForNode(Block[] blockArray, Node node) {
        for (int i = 0; i < 9; i++) {
            if (blockArray[i].hasNode(node.id())) {
                return i;
            }
        }
        return 0;
    }

    /** Print board. */
    void printBoard() {
        int[][] boardInt = new int[9][9];
        for (int ci = 0; ci < 9; ci++) {
            for (int ri = 0; ri < 9; ri++) {
                boardInt[ci][ri] = _nodeArray[ci][ri].num();
            }
        }
        System.out.println(" ----------------------- ");
        for (int ri = 0; ri < 9; ri++) {
            String line = "| ";
            for (int ci = 0; ci < 9; ci++) {
                line += boardInt[ci][ri];
                if (ci < 8) {
                    line += " ";
                    if (ci % 3 == 2 ) {
                        line += "| ";
                    }
                }
            }
            line += " |";
            System.out.println(line);
            if ((ri+1) % 3 == 0) {
                System.out.println(" ----------------------- ");
            }
        }
        System.out.println();
    }

    /** Function to run strategy over board once. */
    void performStrategy() {
        for (int ci = 0; ci < 9; ci++) {
            for (int ri = 0; ri < 9; ri++) {
                _nodeArray[ci][ri].strategy();
            }
        }
    }

    /** Check if Sudoku puzzle is solved. */
    boolean checkSolved() {
        for (int ci = 0; ci < 9; ci++) {
            for (int ri = 0; ri < 9; ri++) {
                if (!_nodeArray[ci][ri].confirmed()) {
                    return false;
                }
                if (_nodeArray[ci][ri].hasConflict()) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Check if Sudoku puzzle is valid currently. */
    boolean checkValid() {
        for (int ci = 0; ci < 9; ci++) {
            for (int ri = 0; ri < 9; ri++) {
                if (_nodeArray[ci][ri].hasConflict()) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Find guess Node. */
    Node findGuessNode() {
        for (int ci = 0; ci < 9; ci++) {
            for (int ri = 0; ri < 9; ri++) {
                if (!_nodeArray[ci][ri].confirmed()) {
                    return _nodeArray[ci][ri];
                }
            }
        }
        return null;
    }

    /** Make copy of board. */
    Board copyBoard() {
        int[] nodeCopyArray = new int[81];
        int arrayCounter = 0;
        for (int ri = 0; ri < 9; ri++) {
            for (int ci = 0; ci < 9; ci++) {
                nodeCopyArray[arrayCounter] = _nodeArray[ci][ri].num();
                arrayCounter += 1;
            }
        }
        return new Board(nodeCopyArray);
    }

    int nodesLeft() {
        int nodecounter = 0;
        for (int ci = 0; ci < 9; ci++) {
            for (int ri = 0; ri < 9; ri++) {
                if (!_nodeArray[ci][ri].confirmed()) {
                    nodecounter += 1;
                }
            }
        }
        return nodecounter;
    }

    void setNodeNum(int id, int num) {
        for (int ci = 0; ci < 9; ci++) {
            for (int ri = 0; ri < 9; ri++) {
                if (_nodeArray[ci][ri].id() == id && !_nodeArray[ci][ri].confirmed()) {
                    _nodeArray[ci][ri].setNum(num);
                }
            }
        }
    }

    void setNodeNum(int col, int row, int num) {
        _nodeArray[col][row].setNum(num);
    }

    /** Double Array representation of board. */
    private Node[][] _nodeArray;

    /** Col array. */
    private Block[] _colArray;

    /** Row array. */
    private Block[] _rowArray;

    /** Blk array. */
    private Block[] _blkArray;

}
