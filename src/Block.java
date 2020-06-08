/**
 * Created by christopherlau on 6/16/17.
 */
public class Block {
    /** A block of type ROW (0), COL (1), or BLK (2) - a representation
     * of a list of numbers from 1 to 9.
     */
    Block(int type, int blockNum, Node[] nodeArray) {
        _type = type;
        _blockNum = blockNum;
        _nodes = nodeArray;

    }

    /** Return TRUE if num is in block, False if not. */
    boolean hasNum(int num) {
        for (Node node: _nodes) {
            if (node.num() == num) {
                return true;
            }
        }
        return false;
    }

    /** Return TRUE if num is in block, False if not. */
    boolean hasNode(int id) {
        for (Node node: _nodes) {
            if (node.id() == id) {
                return true;
            }
        }
        return false;
    }

    /** Search for duplicates within block. */
    boolean duplicate(int nodeNum) {
        int counter = 0;
        for (Node node: _nodes) {
            if (node.num() == nodeNum) {
                counter += 1;
            }
        }
        if (counter > 1) {
            return true;
        } else {
            return false;
        }
    }

    /** Return block number. */
    int blockNum() {
        return _blockNum;
    }

    /** Type of block: ROW (0), COL (1), or BLK (2). */
    private int _type;

    /** Array of nodes. */
    private Node[] _nodes;

    /** Block number. */
    private int _blockNum;

}
